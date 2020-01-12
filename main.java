import java.util.*;
import java.io.*;

public class main
{
    static BufferedWriter writer;
     
	public static void main(String [] args)throws Exception
	{	
            
             int flowsNum =30000000;
            int cells = Integer.parseInt(args[2]);
            
            boolean fold=true;
            
            

            double phi = 0.001;
            
            int rounds = 1;

            File file = new File(args[0]);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) (4.4448E8)];
            fis.read(data);
            InputStream is = new ByteArrayInputStream(data);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            
            int r=0;
            if(Integer.parseInt(args[1])==0){
                    writer = new BufferedWriter(new FileWriter("./fattree_results.txt", true));
                }
                else{
                    writer = new BufferedWriter(new FileWriter("./geant_results.txt", true));
                }
//             writer = new BufferedWriter(new FileWriter("./chicago"+ Integer.toString(Integer.parseInt(args[2])) +".txt", true));
            while(r < rounds)
            {
                int counter=0;
                long PacketsNum=0;
                Topology ft;
                if(Integer.parseInt(args[1])==0){
                    ft=new FatTree(cells,8);
                }
                else{
                    ft=new Geant(cells,40);
                }
                String st;
                String st1 = br.readLine();
                st = st1;
                
                Map<Integer, Integer> flows = new HashMap<Integer, Integer>();
                Map<Integer, Integer> Count = new HashMap<Integer, Integer>();
            while(counter<flowsNum){
                st = st1;


                if(st.length()<20){
                st = br.readLine();
                }


                if(st==null || (st1 = br.readLine())==null){
                fis.read(data);
                        is = new ByteArrayInputStream(data);
                        br = new BufferedReader(new InputStreamReader(is));
                st = br.readLine();
                st1 = br.readLine();
                }


                if(st.length()<20){
                st = br.readLine();
                }


                        int i = 2;
                        int source=0;
                        int dist =0;
                        int w=0;
                        if(st.charAt(i) == '6')
                        {
                            i+=2;
                            
                            while(st.charAt(i)!=' ')
                            {
                                if(Character.isDigit(st.charAt(i))){
                                    if(source == 0)
                                    {
                                        source = st.charAt(i) - '0';
                                    }
                                    else
                                    {
                                        source*=10;
                                        source+=st.charAt(i) - '0';
                                    }
                                }
                                i++;
                            }
                            i+=3;
                            
                            while(st.charAt(i)!=' ')
                            {
                                if(Character.isDigit(st.charAt(i))){
                                    if(dist == 0)
                                    {
                                        dist = st.charAt(i) - '0';
                                    }
                                    else
                                    {
                                        dist*=10;
                                        dist+=st.charAt(i) - '0';
                                    }
                                }
                                i++;
                            }
                            
                            while(i<st.length() && !Character.isDigit(st.charAt(i)))
                            {
                                i++;
                            }
                            
                            while(i<st.length() && Character.isDigit(st.charAt(i)))
                            {
                                if(w == 0)
                                {
                                    w = st.charAt(i) - '0';
                                }
                                else
                                {
                                    w*=10;
                                    w+=st.charAt(i) - '0';
                                }
                                i++;
                            }
                        }
                        else
                        {
                            i++;
                            
                            while(Character.isDigit(st.charAt(i)) || st.charAt(i)=='.')
                            {
                                if(st.charAt(i)!='.'){
                                    if(source == 0)
                                    {
                                        source = st.charAt(i) - '0';
                                    }
                                    else
                                    {
                                        source*=10;
                                        source+=st.charAt(i) - '0';
                                    }
                                }
                                i++;
                            }
                                            
                            i+=3;
                            
                            while(Character.isDigit(st.charAt(i)) || st.charAt(i)=='.')
                            {
                                if(st.charAt(i)!='.'){
                                    if(dist == 0)
                                    {
                                        dist = st.charAt(i) - '0';
                                    }
                                    else
                                    {
                                        dist*=10;
                                        dist+=st.charAt(i) - '0';
                                    }
                                }
                                i++;
                            }
                            
                            
                            while(i<st.length() &&!Character.isDigit(st.charAt(i)))
                            {
                                i++;
                            }
                            
                            while(i<st.length() && Character.isDigit(st.charAt(i)))
                            {
                                if(w == 0)
                                {
                                    w = st.charAt(i) - '0';
                                }
                                else
                                {
                                    w*=10;
                                    w+=st.charAt(i) - '0';
                                }
                                i++;
                            }
                        }
                    
                        source = Math.abs(source);
                        dist = Math.abs(dist);
                        w = Math.abs(w);
                        int newid = source;
                        int dist1 = dist;
                        while(dist1>1){
                            int n = dist1%10;
                            dist1/=10;
                            newid*=10;
                            newid+=n;
                        }
                        
                        newid = Math.abs(newid);
                        w=1;
                        if(flows.containsKey(newid))
                        {
//                             flows.replace(newid,Math.abs(flows.get(newid)+w));
//                             Count.replace(newid,Math.abs(Count.get(newid)+1));
                            
                        }
                        else
                        {
                            
                            flows.put(newid,w);
                            Count.put(newid,1);
                            ft.addFlow(source,dist,newid, 1, w,fold);
                        }
                        
                        st = st1;
                        counter+=1;
                        PacketsNum+=w;
        }
        
       
       
       
       int found=0;
       int foundFR=0;
      
        ft.network.compine();
        
        
        
        
        int Hqs = ft.network.Hcounter;
        int qps = ft.network.PScounter; 
         writer.append(args[2]+" Optimal:\n");
         if(Integer.parseInt(args[1])==0){
          writer.append(Double.toString((double)(cells*80)/((double)flows.size())));
          }
          else{
            writer.append(Double.toString((double)(cells*40)/((double)flows.size())));
          }
          
        
          writer.append("\n");
         writer.append("CFS flow Coverage:\n");
          writer.append(Double.toString((double)qps/(double)flows.size()));
          writer.append("\n");
          writer.append("CFS-FR flow Coverage:\n");
          writer.append(Double.toString((double)Hqs/(double)flows.size()));
          writer.append("\n");
          writer.append("\n");

       writer.flush();
            r++;
        }
	fis.close();
	writer.close();
    }
    
}








