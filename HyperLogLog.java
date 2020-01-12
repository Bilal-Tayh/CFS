/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * An implementation of the HyperLogLog algorithm:
 * 
 * http://algo.inria.fr/flajolet/Publications/FlFuGaMe07.pdf
 */
 
 
 
//  import com.google.common.hash.Hashing;
 
 //private static final HashFunction HASH = Hashing.murmur3_128();
 
public class HyperLogLog {
  private final byte[] buckets;

  // The current sum of 1 / (1L << buckets[i]). Updated as new items are added and used for
  // estimation
  private double currentSum;
  private int nonZeroBuckets = 0;
  HashFunction h;

  public HyperLogLog(int numberOfBuckets) {
    buckets = new byte[numberOfBuckets];
    currentSum = buckets.length;
    h = new HashFunction();
  }

  public HyperLogLog(int[] buckets) {
    this(buckets.length);

    currentSum = 0;
    for (int i = 0; i < buckets.length; i++) {
      int value = buckets[i];
      this.buckets[i] = (byte) value;
      currentSum += 1.0 / (1 << value);
    }
  }

  public void add(long value) {
    BucketAndHash bucketAndHash = BucketAndHash.fromHash(computeHash(value), buckets.length);
    int bucket = bucketAndHash.getBucket();

    int lowestBitPosition = Long.numberOfTrailingZeros(bucketAndHash.getHash()) + 1;

    int previous = buckets[bucket];

    if (previous == 0) {
      nonZeroBuckets++;
    }

    if (lowestBitPosition > previous) {
      currentSum -= 1.0 / (1L << previous);
      currentSum += 1.0 / (1L << lowestBitPosition);

      buckets[bucket] = (byte) lowestBitPosition;
    }
  }

  public long estimate() {
    double alpha = computeAlpha(buckets.length);
    double result = alpha * buckets.length * buckets.length / currentSum;

    if (result <= 2.5 * buckets.length) {
      // adjust for small cardinalities
      int zeroBuckets = buckets.length - nonZeroBuckets;
      if (zeroBuckets > 0) {
        result = buckets.length * Math.log(buckets.length * 1.0 / zeroBuckets);
      }
    }

    return Math.round(result);
  }

  public int[] buckets() {
    int[] result = new int[buckets.length];

    for (int i = 0; i < buckets.length; i++) {
      result[i] = buckets[i];
    }

    return result;
  }
  
  
  
  
  
    public static double computeAlpha(int numberOfBuckets) {
    double alpha;
    switch (numberOfBuckets) {
      case (1 << 4):
        alpha = 0.673;
        break;
      case (1 << 5):
        alpha = 0.697;
        break;
      case (1 << 6):
        alpha = 0.709;
        break;
      default:
        alpha = (0.7213 / (1 + 1.079 / numberOfBuckets));
    }
    return alpha;
  }
  
  
  
  public long computeHash(long k) {
    k ^= k >>> 33;
    k *= 0xff51afd7ed558ccdL;
    k ^= k >>> 33;
    k *= 0xc4ceb9fe1a85ec53L;
    k ^= k >>> 33;
    return k;
  }
}
