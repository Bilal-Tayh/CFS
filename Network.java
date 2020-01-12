/*


    this class simulates a Network,
    
    after constructing this class call addSwitch() function to add switches to the network.
    
    and after constructing the network if you want a packet to run in the network call addFlow().
    
    after you finish adding all the flows then you can call compine that compines all the flows that all the
    
    switches on the network catched


*/




import java.util.*;


class SwitchDontExistException extends Exception {

  public SwitchDontExistException(int id){
     super("switch id: "+ Integer.toString(id) +" doesnt exist");
  }

}

public class Network{ 
    Map<Integer,CFSswitch> networkCFSswitches;
    Map<Integer,CfsFRswitch> networkCfsFRswitches;
    
    int tSize;
    boolean compined;
    int Scounter;
    int PScounter;
    int Hcounter;
    HashFunction h;
    Set<Integer> aaa ;
    int maxPath;
    double[] b;

    public Network(int tableSize,int max){
        maxPath = max;
        aaa = new  HashSet<Integer>();
        h = new HashFunction();
        networkCFSswitches = new HashMap<Integer,CFSswitch>();
        networkCfsFRswitches = new HashMap<Integer,CfsFRswitch>();
        tSize = tableSize;
        compined = false;
        Scounter=0;
        PScounter=0;
        Hcounter=0;
        
        
        
        
        b = new double[maxPath];
        double[] a = new double[maxPath];
        
        for(int i = 0;i<maxPath;i++){
                a[i] = (double)i/(double)maxPath;
                b[i] = -1.0;
        }
        int count=0;
        while(count < maxPath){
            int maxIndex=-1;
            double maxValue=-1.0;
            for(int i = 0;i<maxPath;i++){
                if(a[i]!=-1.0){
                    if(maxIndex==-1){
                        maxIndex=i;
                        maxValue =1.0;
                        for(int j = 0;j<maxPath;j++){
                            if(b[j]!=-1.0){
                                double dist = Math.min( Math.min(Math.abs(1.0-b[j])+Math.abs(a[i]), Math.abs(1.0-a[i])+Math.abs(b[j])),Math.abs(b[j]-a[i]));
                                if(maxValue > dist){
                                    maxValue = dist;
                                }
                            }
                        }
                    }
                    else{
                        double currValue =1.0;
                        for(int j = 0;j<maxPath;j++){
                            if(b[j]!=-1.0){
                                 double dist = Math.min(Math.min(Math.abs(1.0-b[j])+Math.abs(a[i]), Math.abs(1.0-a[i])+Math.abs(b[j])),Math.abs(b[j]-a[i]));
                                if(currValue > dist){
                                    currValue = dist;
                                }
                            }
                        }
                        if(currValue > maxValue){
                            maxIndex=i;
                            maxValue = currValue;
                        }
                    
                    }
                }
            }
            for(int j = 0;j<maxPath;j++){
                if(b[j]==-1.0){
                   b[j] = a[maxIndex];
                   a[maxIndex] = -1.0;
                   break;
                }
            }
            count++;
            
        }
        
    }
    
    
    
    
/*
 * add switch to the network, the sId should be unique and no two switches should share the same switchesId
 * neighbors should contain sId of switches that aleady in the network.
 *
*/
   public void addSwitch(int sId ,int[] neighbors) throws SwitchDontExistException {
        Integer[] ns = Arrays.stream( neighbors ).boxed().toArray( Integer[]::new );
        networkCFSswitches.put(sId,new CFSswitch(sId,tSize,h));
        double p=0.1;
        networkCfsFRswitches.put(sId,new CfsFRswitch(sId,tSize,h,p));
   }
    


    public void addFlow(int flowId, int packetID, int w, int[] switchesId, boolean fold){
    if(!aaa.contains(flowId)){
           aaa.add(flowId);
    }
        
        if(fold){
            for(int i=0; i<switchesId.length; i++){
                    networkCFSswitches.get(switchesId[i]).S = i;
                    networkCfsFRswitches.get(switchesId[i]).S = i;
                    if(w>=0){
                        boolean oneLength = false;
                        if(switchesId.length==1){
                            oneLength = true;
                        }
                            networkCFSswitches.get(switchesId[i]).addFlowF(flowId,packetID,w,oneLength);
                            networkCfsFRswitches.get(switchesId[i]).addFlowF(flowId,packetID,w,oneLength);

                    }
            }
        }
        else{
            for(int i=0; i<switchesId.length; i++){
                    networkCFSswitches.get(switchesId[i]).S = b[i];
                    networkCfsFRswitches.get(switchesId[i]).S = b[i];
                    if(w>=0){
                        boolean oneLength = false;
                        if(switchesId.length==1){
                            oneLength = true;
                        }
                            networkCFSswitches.get(switchesId[i]).addFlowG(flowId,packetID,w,oneLength);
                            networkCfsFRswitches.get(switchesId[i]).addFlowG(flowId,packetID,w,oneLength);

                    }
            }
        }
    
    }
    
    
      public CFSswitch getCFSswitch(int id) throws SwitchDontExistException {
         if(networkCFSswitches.containsKey(id)){
             return networkCFSswitches.get(id);
         }
         else{
            throw new SwitchDontExistException(id);
         }
    }
    
    

    
    
    
    public CFSswitch[] getCFSswitchs() {
        CFSswitch[] s = new CFSswitch[networkCFSswitches.size()];
        int i=0;
        for(int sId : networkCFSswitches.keySet()){
            s[i] = networkCFSswitches.get(sId);
            i++;
        }
        return s;
    }

    
    
    public int[] getCFSswitchsIds() {
        int[] s = new int[networkCFSswitches.size()];
        int i=0;
        for(int sId : networkCFSswitches.keySet()){
            s[i] = sId;
            i++;
        }
        return s;
    }
    
    public int switchesNum(){
        return networkCFSswitches.size();
    }
    
    
    
    

    


   Set<Integer> aa = new HashSet<Integer>();
    Set<Integer> bb = new HashSet<Integer>();
    public void compine(){
    
        if(compined){
            return;
        }

        
        for(int i=0; i<networkCFSswitches.size();i++){
        
        
            CFSswitch ps = networkCFSswitches.get(i);
            while(ps.min_heap.size()>0){
                CFSswitch.Triple t = ps.min_heap.poll();
                if(!aa.contains(t.second)){
                    aa.add(t.second);
                }
                
            }
        }


       
        for(int i=0; i<networkCfsFRswitches.size();i++){
        
            CfsFRswitch sss = networkCfsFRswitches.get(i);
            while(sss.min_heap.size()>0){
                CfsFRswitch.Triple t = sss.min_heap.poll();
                if(!bb.contains(t.second)){
                    bb.add(t.second);
                    for(int iii=0; iii<networkCfsFRswitches.size();iii++){
                        networkCfsFRswitches.get(iii).removeFlow(t.second,1);
                    }
                }
            }
        }
        HflowRadar();
        for(int fl : decoded1.keySet()){
            if(!bb.contains(fl))
                bb.add(fl);
                
        }
        compined = true;
        PScounter=aa.size();
        Hcounter = bb.size();
    }
    
    
    
    
    

    
    
    
    

    Map<Integer,Integer> decoded1 = new HashMap<Integer,Integer>();
    public int HflowRadar(){
//         System.out.println("Decoding.....\n");
        Decoder de = new Decoder();
        FRSwitch[] switches = new FRSwitch[networkCfsFRswitches.size()];
        
        int coun=0;
        for(CfsFRswitch h : networkCfsFRswitches.values()){
            switches[coun]=h.flowRadar;
            coun++;
        }
        
        Map<Integer,int[][]> F = de.flowDecode(switches);
//         System.out.println("Done\n");
       for(int ss : F.keySet()){
           
            int[][] w = F.get(ss);;
            for(int y=0;y<w.length;y++){
                if(!decoded1.containsKey(w[y][0])){
                    decoded1.put(w[y][0],w[y][1]);
                }
            }
       }
       return decoded1.size();
    }

}




