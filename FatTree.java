import java.util.*;
import java.io.*; 

public class FatTree extends Topology{
     Pod[] podes;
     int[] core;
     int k;
     HashFunction hash1;
     HashFunction hash2;    
     HashFunction hash3;
     HashFunction hash4;
     HashFunction hash5;
     HashFunction hash6;
     
     
     
     
     
    public class Pod{
        int[] Aggr;
        int[] edge;
        
        public Pod(int podsNum) throws Exception{
            Aggr = new int[k/2];
            edge = new int[k/2];
            
        for(int t=0; t<Aggr.length;t++){
            int id = core.length + podsNum*k  + t;
            int[] neighbors = new int[k/2];
            int count =0;
            for(int i=(k/2)*t;i<(k/2)*(t+1);i++){
                neighbors[count] = i;
                count++;
            }
            network.addSwitch(id ,neighbors);
            Aggr[t] = id;
        }
        
        
        
         for(int t=0; t<edge.length;t++){
            int id = core.length + podsNum*k + Aggr.length  + t;
            network.addSwitch(id ,Aggr);
            edge[t] =id;
        }
        
        
        }
        
        public int getAggr(int i){
            return Aggr[i];
        }
        
        
        public int getEdge(int i){
            return edge[i];
        }
    }
     
     
     
     
     
     
     
     
     
     
    public FatTree(int cells, int ports) throws Exception{
        network = new Network(cells,5);
        hash1 = new HashFunction();
        hash2 = new HashFunction();
        hash3 = new HashFunction();
        hash4 = new HashFunction();
        hash5 = new HashFunction();
        hash6 = new HashFunction();
        k = 8;
        core = new int[(int)Math.pow(k/2,2)];
        for(int t=0; t<core.length;t++){
            network.addSwitch(t ,new int[]{});
            core[t] = t;
        }
        
        podes = new Pod[k];
        for(int i=0;i<k;i++){
            podes[i] = new Pod(i);
        }
        
    }
    
    
    
    
    
    
    public int[] addFlow(int s,int d, int flowid, int packets,int w,boolean fold){
        Random rn = new Random();
        int h1 = hash1.hash(s,k);
        int h2 = hash2.hash(d,k);
        int h3 = hash3.hash(s,(int)k/2);
        int h4 = hash4.hash(d,(int)k/2);
        int h5 = hash5.hash(s,(int)k/2);
        int h6 = hash6.hash(d,(int)k/2);
        if(h1 == h2){
            if(h3 == h4){
                int[] p = new int[]{podes[h2].getEdge(h4)};
                network.addFlow(flowid,packets,w,p,fold);
                return p;
            }
            else{
                int randAggr = h5;
                int[] p =new int[]{podes[h2].getEdge(h3),podes[h2].getAggr(randAggr),podes[h2].getEdge(h4)};
                network.addFlow(flowid,packets,w,p,fold);
                return p;
            }
        }
        else{
                int randAggr = h5;
                int randCore = randAggr*((int)k/2) + h6;
                int[] p = new int[]{podes[h1].getEdge(h3),podes[h1].getEdge(randAggr),core[randCore],podes[h2].getEdge(randAggr),podes[h2].getEdge(h4)};
                network.addFlow(flowid,packets,w,p,fold);
                return p;

                
                /*
                network.addFlow(flowid,packets,w,new int[]{podes[h1].getEdge(h3),podes[h1].getAggr(randAggr),core[randCore],podes[h2].getAggr(randAggr),podes[h2].getEdge(h4)});*/
        }
        
    }
    
    
    
    
    
    

}
