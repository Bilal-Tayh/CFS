import java.util.*; 
public class CfsFRswitch { 

    public class Triple{
        public double first;
        public int second;
        public int third;
        public Triple(){
            first = 0.0;
            second = 0;
            third = 0;
        }
        public Triple(double a,int b,int c){
            first = a;
            second = b;
            third = c;
        }
    
    }


    int sid;
    PriorityQueue<Triple> min_heap;
    Set<Integer> aa ;
    Set<Integer> in ;
    int max;
    HashFunction h;
    double S=0.0;
    FRSwitch flowRadar;
    HyperLogLog hll;

    
    
    
    public CfsFRswitch(int Id, int cells,HashFunction H,double p){
            h = H;
            aa = new  HashSet<Integer>();
            in = new  HashSet<Integer>();
            min_heap = new PriorityQueue<Triple>((int)Math.ceil(cells*p), new Comparator<Triple>() {
            public int compare(Triple t1, Triple t2) {
                if ((t1.first - t2.first)>0){
                    return 1;
                }
                else if((t1.first - t2.first)<0){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        sid = Id;
        max = (int)Math.ceil(cells*(1-p));
        flowRadar = new FRSwitch(Id,(int)Math.ceil(cells*(1-p)));
        hll=new HyperLogLog(3000);
    }
    
    
    
    public void addFlowG(int flowId, int packetId, int w,boolean oneSwitchLength){
        hll.add(flowId);
        if(aa.contains(flowId)){
            return;
        }
        
        aa.add(flowId);
        Random generator = new Random(Math.abs(h.h(flowId)));
        double rand = generator.nextDouble();
        double p = rand;
        p= 1 -  Math.min(Math.min(Math.abs(1.0-S)+Math.abs(p), Math.abs(1.0-p)+Math.abs(S)),Math.abs(S-p));
        int c=0;
        if(oneSwitchLength){
            p=1.1;
        }
        flowRadar.addFlow(flowId,1);
        
        
        if(min_heap.size()<max || p > min_heap.peek().first){
            if(min_heap.size() == max){
                Triple t1 = min_heap.poll();
                in.remove(t1.second);
            }
            Triple t = new Triple(p, flowId, packetId);  
            min_heap.add(t);
            in.add(flowId);
        }
    }

     
     public int check(int flow){
         if(in.contains(flow)){
                return 1;
            }
            return 0;
     }
     


        public void addFlowF(int flowId, int packetId, int w,boolean oneSwitchLength){
            
            double SP = 1.0/Math.pow(2,S);
            
            hll.add(flowId);
            if(aa.contains(flowId)){
                return;
            }
            
            aa.add(flowId);
            flowRadar.addFlow(flowId,1);
            
            Random generator = new Random(flowId);
            double rand = generator.nextDouble();
            double p = rand;
            
            for(int i=0;i<S;i++){
                if(i==0){
                    if(p>0.5){
                        p = 1.0 - p;
                    }
                }
                else{
                    if(p>0.25){
                        p = 0.5 - p;
                    }
                        p*=2;
                }
            }
            int c=0;
            if(S==0){
                p=Math.max(p,1.0-p)/2.0;
            }

            
            if(oneSwitchLength){
                p=1.1;
            }      
            if(min_heap.size()<max || p > min_heap.peek().first){
                if(min_heap.size() == max){
                    Triple t1 = min_heap.poll();
                    in.remove(t1.second);
                }
                Triple t = new Triple(p, flowId, packetId);  
                min_heap.add(t);
                in.add(flowId);
            }
     }
    
    
    
    
    
    
    
    public void removeFlow(int flowId, int packetsNum){
        if(aa.contains(flowId)){
                flowRadar.removeFlow(flowId,packetsNum);
                aa.remove(flowId);
        }
    
    }
    

    
    public int getId(){
        return sid;
    }
    

    
    
    
    public int flowsNum(){
        return min_heap.size();
    }
    
    
       public double getN(){
        return hll.estimate();
    }


   
    

}
