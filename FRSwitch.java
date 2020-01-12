import java.util.*;

public class FRSwitch { 
    // the bloom filter table....
    Set<Integer> bf;
    // the xor tabel.....
    CountTable ct;
    int id;
    // keep traking of the flows inside the switch
    
    
    
    
    
    
    public FRSwitch(int sId, int ctSize){
        int ctHashFunctionsNum =3;
        id= sId;
        bf = new HashSet<Integer>();
        ct = new CountTable(ctSize,ctHashFunctionsNum);
    }
    
    
    
    
    
    public void addFlow(int flowId, int packetsNum){
        boolean bfAlreadyExist = false;
        if(bf.contains(flowId)){
            bfAlreadyExist=true;
        }
        else{
            bf.add(flowId);
        }
        ct.addFlow(flowId,packetsNum,bfAlreadyExist);
        
        
    }

    
    
        
    public void removeFlow(int flowId, int packetsNum){
        ct.removeFlow(flowId,packetsNum);
    }
    
    
    
    public int getId(){
        return id;
    }
    
    

    
    
    
    public CountTable getCountTable(){
        return ct;
    }
    
    public void setCountTable(CountTable c){
        ct = c;
    }
    

}
