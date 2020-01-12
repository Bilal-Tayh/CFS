import java.util.*;

class Geant extends Topology{

    Map<Integer,Set<Integer>> edjs;
    int v;
    HashFunction h1;
    HashFunction h;
    
    public Geant(int cells, int size)throws Exception{
        network = new Network(cells,9);
        h1 = new HashFunction();
        h = new HashFunction();
        v = size;
        edjs = new HashMap<Integer,Set<Integer>>();
        for(int i=0;i<40;i++){
            network.addSwitch(i,new int[]{});
            edjs.put(i,new HashSet<Integer>());
        }
        edjs.get(0).add(1);

        edjs.get(0).add(2);

        edjs.get(0).add(4);

        edjs.get(0).add(34);

        edjs.get(0).add(30);

        edjs.get(1).add(33);

        edjs.get(2).add(32);

        edjs.get(2).add(35);

        edjs.get(2).add(4);

        edjs.get(2).add(38);

        edjs.get(2).add(36);

        edjs.get(2).add(31);

        edjs.get(3).add(10);

        edjs.get(3).add(19);

        edjs.get(3).add(4);

        edjs.get(3).add(5);

        edjs.get(3).add(30);

        edjs.get(4).add(5);

        edjs.get(4).add(6);

        edjs.get(4).add(8);

        edjs.get(4).add(16);

        edjs.get(4).add(17);

        edjs.get(4).add(29);

        edjs.get(4).add(31);

        edjs.get(5).add(23);

        edjs.get(6).add(7);

        edjs.get(7).add(8);

        edjs.get(7).add(25);

        edjs.get(7).add(34);

        edjs.get(8).add(9);

        edjs.get(8).add(25);

        edjs.get(9).add(25);

        edjs.get(9).add(18);

        edjs.get(9).add(29);

        edjs.get(9).add(15);

        edjs.get(11).add(13);

        edjs.get(12).add(22);

        edjs.get(12).add(20);

        edjs.get(12).add(13);

        edjs.get(12).add(14);

        edjs.get(12).add(15);

        edjs.get(13).add(14);

        edjs.get(13).add(22);

        edjs.get(15).add(29);

        edjs.get(16).add(34);

        edjs.get(17).add(30);

        edjs.get(21).add(27);

        edjs.get(22).add(26);

        edjs.get(22).add(27);

        edjs.get(22).add(23);

        edjs.get(23).add(29);

        edjs.get(24).add(25);

        edjs.get(24).add(34);

        edjs.get(27).add(28);

        edjs.get(28).add(29);

        edjs.get(30).add(39);
                                
        edjs.get(32).add(34);

        edjs.get(33).add(34);

        edjs.get(35).add(36);

        edjs.get(36).add(37);

        edjs.get(38).add(39);

        
        edjs.get(1).add(0);

        edjs.get(2).add(0);

        edjs.get(4).add(0);

        edjs.get(34).add(0);

        edjs.get(30).add(0);

        edjs.get(33).add(1);

        edjs.get(32).add(2);

        edjs.get(35).add(2);

        edjs.get(4).add(2);

        edjs.get(38).add(2);

        edjs.get(36).add(2);

        edjs.get(31).add(2);

        edjs.get(10).add(3);

        edjs.get(19).add(3);

        edjs.get(4).add(3);

        edjs.get(5).add(3);

        edjs.get(30).add(3);

        edjs.get(5).add(4);

        edjs.get(6).add(4);

        edjs.get(8).add(4);

        edjs.get(16).add(4);

        edjs.get(17).add(4);

        edjs.get(29).add(4);

        edjs.get(31).add(4);

        edjs.get(23).add(5);

        edjs.get(7).add(6);

        edjs.get(8).add(7);

        edjs.get(25).add(7);

        edjs.get(34).add(7);

        edjs.get(9).add(8);

        edjs.get(25).add(8);

        edjs.get(25).add(9);

        edjs.get(18).add(9);

        edjs.get(29).add(9);

        edjs.get(15).add(9);

        edjs.get(13).add(11);

        edjs.get(22).add(12);

        edjs.get(20).add(12);

        edjs.get(13).add(12);

        edjs.get(14).add(12);

        edjs.get(15).add(12);

        edjs.get(14).add(13);

        edjs.get(22).add(13);

        edjs.get(29).add(15);

        edjs.get(34).add(16);

        edjs.get(30).add(17);

        edjs.get(27).add(21);

        edjs.get(26).add(22);

        edjs.get(27).add(22);

        edjs.get(23).add(22);

        edjs.get(29).add(23);

        edjs.get(25).add(24);

        edjs.get(34).add(24);

        edjs.get(28).add(27);

        edjs.get(29).add(28);

        edjs.get(39).add(30);
                                
        edjs.get(34).add(32);

        edjs.get(34).add(33);

        edjs.get(36).add(35);

        edjs.get(37).add(36);

        edjs.get(39).add(38);

    }



    boolean BFS( int src, int dest, int size, int pred[], int dist[],int flowId) 
    { 
    Queue<Integer> queue = new LinkedList<>(); 
    boolean[] visited = new boolean[v];
        for (int i = 0; i < v; i++) { 
            visited[i] = false; 
            dist[i] = v+1; 
            pred[i] = -1; 
        } 
        visited[src] = true; 
        dist[src] = 0; 
        queue.add(src); 
    
        // standard BFS algorithm 
        while (queue.size()!=0) { 
            int u = queue.peek(); 
            queue.remove(); 
            
            int start = h.hash(flowId,edjs.get(u).size());
            int[] aa = new int [edjs.get(u).size()];
            for(Integer adj : edjs.get(u)){
                aa[start%(edjs.get(u).size())] = adj;
                start++;
            }
            

            for(Integer adj : aa){
                if (visited[adj] == false) { 
                    visited[adj] = true; 
                    dist[adj] = dist[u] + 1; 
                    pred[adj] = u; 
                    queue.add(adj); 
    
                    // We stop BFS when we find 
                    // destination. 
                    if (adj == dest) 
                    return true; 
                } 
            }
        } 
    
        return false; 
    } 
    
    // utility function to print the shortest distance  
    // between source vertex and destination vertex 
    int[] printShortestDistance( int src, int dest,int flowId,int packetId,int w,boolean fold)
    { 
        int[] pred = new int[v];
        int[] dist = new int[v]; 
    
        if (BFS(src, dest, v, pred, dist,flowId) == false) 
        { 
            System.out.println("Given source and destination are not connected"); 
            return new int[]{}; 
        } 
    
        // vector path stores the shortest path 
        Set<Integer> path = new HashSet<Integer>(); 
        
        int crawl = dest; 
        path.add(crawl); 
        while (pred[crawl] != -1) { 
            path.add(pred[crawl]); 
            crawl = pred[crawl]; 
        } 

    
        int[] p = new int[path.size()];
        int i=0;
        for(Integer sw : path){

            p[i]=sw;
            i++;
            
        }

        network.addFlow( flowId, packetId, w,p,fold);
        return p;
    } 



    public int[] addFlow(int s,int d, int flowid, int packets,int w,boolean fold){
        int src = h1.hash(s,v);
        int dist = h1.hash(d,v);
        if(src == dist){
            int[] p = new int[1];
            p[0]=src;
            network.addFlow(flowid, packets, w,p,fold);
            return p;
        }
        else{
            return printShortestDistance(src, dist,flowid, packets,w,fold);
        }

    }
}













 
