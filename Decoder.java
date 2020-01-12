import java.util.*;


public class Decoder { 
    
    public Decoder(){}


    
    int[][] singleDecode(FRSwitch s){
        CountTable ct = s.getCountTable();
        return singleDecodeHelper(s);
    }
    
    
    private int[][] deepCopy(int[][] A) {
    int[][] B = new int[A.length][A[0].length];
    for (int x = 0; x < A.length; x++) {
      for (int y = 0; y < A[0].length; y++) {
          B[x][y] = A[x][y];
      }
    }
    return B;
  }
  
  
  
  
  
  
  
  
  

    
    
    
    
    int[][] singleDecodeHelper(FRSwitch s){
        CountTable ct = s.getCountTable();
        
        Map<Integer,Integer> flowset = new HashMap<Integer,Integer>();
        boolean stuck = false;

        while(!stuck){
            stuck = true;
            for(int j=0;j<ct.Size();j++){
                if(ct.getIndex(j,1) == 1){
                    stuck=false;
                    int id =(ct.getIndex(j,0));
                    int packets = ct.getIndex(j,2);
                    flowset.put(id,packets);
                    ct.removeFlow(id,packets);
                }
            }
        }
        
        s.setCountTable(ct);
        
        
        
        int[][] result = new int[flowset.size()][2];
        int c=0;
        for(int fId: flowset.keySet()){
            s.bf.remove(fId);
            result[c][0] = fId;
            result[c][1] = flowset.get(fId);
            c++;
        }

        if(result == null){return new int[0][2];}
        return result;
    }
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
Map<Integer,int[][]> flowDecode(FRSwitch[] switches){
                  
    Map<Integer,int[][]> S = new HashMap<Integer,int[][]>();
     
         
        for(int i =0; i < switches.length; i++){
            int[][] a = singleDecode(switches[i]);
            S.put(switches[i].getId(),a);
             
        }
    
            
            

        boolean finish = false;
        while(!finish){
            boolean change = false;
            finish = true;
            for(int i =0; i < switches.length; i++){
                for(int j =0; j < switches.length; j++){
                    if(i==j){
                        continue;
                    }
                    
                    int[][] Ai =  S.get(switches[i].getId());
                    int[][] Aj = S.get(switches[j].getId());
                    
                    
                    
                    for(int k=0;k<Aj.length;k++){
                            boolean flowInBf = switches[i].bf.contains(Aj[k][0]);

                            if(flowInBf){

                                 int[][] AiTable = new int[Ai.length + 1][2];

                            
                                int t=0;
                                while(t<Ai.length){
                                    AiTable[t][0] =  Ai[t][0];
                                    AiTable[t][1] =  Ai[t][1];
                                    t++;

                                    
                                }
                                
                                AiTable[t][0] = Aj[k][0];
                                AiTable[t][1] = Aj[k][1];
                                
                                
                                
                                switches[i].removeFlow(Aj[k][0],Aj[k][1]);
                                switches[i].bf.remove(Aj[k][0]);
                                S.replace(switches[i].getId(), AiTable);
                               
                                change = true;
                            }

                    }
                }
                
                
            }
                
            for(int l =0; (l < switches.length )&& (change); l++){
                int[][] result = singleDecode(switches[l]);
                if(result.length==0){
                    continue;
                }
                finish = false;
                int[][] a =  S.get(switches[l].getId());
                int[][] compined = new int[result.length+a.length][2];
                int mm=0;
                for(int kk=0;kk<compined.length;kk++){
                    if(kk < a.length){
                        compined[kk] = a[kk];
                    }
                    else{
                        compined[kk] = result[mm];
                        mm++;
                    }
                }
                
                S.replace(switches[l].getId(),compined);
            }
        }
             return S;
    
    }
    
    
    
    
//     void ConLinearEqu(int [][] flows,FRSwitch s){
//         int [][] table = singleDecodeHelper(s,s.getCountTable().getIncodedTable(),false);
//         int [][] Fl =  singleDecode(s);
//         int [] updateFlows = new int[flows.length - Fl.length ];
//         
//         if(Fl.length != flows.length){
//             int t=0;
//             for(int j=0;j<flows.length;j++){
//                 if(flows[j][1] ==0){
//                     updateFlows[t] = flows[j][0];
//                     t++;
//                 }
//             }
//         }
//         else{
//             return;
//         }
//         
//         
//         
//         
//         CountTable ct = s.getCountTable();
//         int[][] M = new int[table.length][updateFlows.length];
//         int[] b = new int[table.length];
//         
//             for(int j=0;j< updateFlows.length;j++){
//             
//                 int[] hashed =  ct.hashFlow(updateFlows[j]);
//                 
//                 for(int i: hashed){
//                     M[i][j] += 1;
// 
//             }
//         }
//          System.out.println(Arrays.deepToString(M));
//         
//         
//         for(int y=0;y<table.length;y++){
//             b[y] = table[y][2];
//         }
//     
//     }
    
    
    
}



















