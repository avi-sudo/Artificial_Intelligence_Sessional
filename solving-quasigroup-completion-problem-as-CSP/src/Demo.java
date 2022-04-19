import java.io.File;
import java.io.IOException;
import java.util.*;

public class Demo {
    static int nodeCount=0;
    static int failCount=0;
    static int row=0;
    public static void main(String[] args) throws IOException{
        LinkedList<Integer>L=new LinkedList<>();
        File f=new File("d-10-01.txt.txt");
        Scanner sc=new Scanner(f);
        String line2=sc.nextLine();
        String l1=line2.replace("N="," ").replace(";"," ").trim();
        int n=Integer.parseInt(l1);
        row=n;
        while(sc.hasNext()){
            line2 = sc.nextLine();
            if (line2.startsWith("s")||line2.startsWith("[")) {
                continue;
            }
            else {
                String line3 = line2.replace("|", " ");
                String line4 = line3.replace("]", " ").replace(";", " ");
                String[] st = line4.trim().split(", ");
                for (int i = 0; i < st.length; i++) {
                    L.add(Integer.parseInt(st[i]));
                }
            }
        }
        int[][] array=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                array[i][j]=L.poll();
            }
        }
        Graph g=new Graph(n*n,array);
        for(int i=0;i<g.AL.size();i++){
            for(int j=i+n;j<n*n;j+=n){
                g.add_edge(g.AL.get(i),g.AL.get(j));
            }
        }
        for(int i=0;i<n;i++){
            for(int k=(i*n);k<((i+1)*n)-1;k++) {
                for (int j = k + 1; j < ((i + 1) * n); j++) {
                    g.add_edge(g.AL.get(k),g.AL.get(j));
                }
            }
        }
        g.updateDomain();
        Scanner sc1=new Scanner(System.in);
        System.out.println("Enter the choice:");
        System.out.println("1.Backtracking");
        System.out.println("2.Forward Checking");
        System.out.println("3.MAC");
        String st=sc1.nextLine();
        if(st.equalsIgnoreCase("1")) {
            boolean flag = BacktrackingSearch(g);
            if (flag) {
                g.ArrayUpdate();
                g.print(n);
                System.out.println("NodeCount: "+nodeCount + "\n" + "FailCount: "+failCount);
            } else {
                System.out.println("Failed to complete");
            }
        }
        else if(st.equalsIgnoreCase("2")) {
            boolean flag2 = ForwardChecking(g);
            if (flag2) {
                g.ArrayUpdate();
                g.print(n);
                System.out.println("NodeCount: "+nodeCount + "\n" + "FailCount: "+failCount);
            } else {
                System.out.println("Failed to complete");
            }
        }
        else if(st.equalsIgnoreCase("3")){
            boolean flag3 = MAC(g);
            if (flag3) {
                g.ArrayUpdate();
                g.print(n);
                System.out.println("NodeCount: "+nodeCount + "\n" + "FailCount: "+failCount);
            } else {
                System.out.println("Failed to complete");
            }
        }
    }
    public static boolean Recursives(Graph gr){
        HashMap<Coordinate,Integer>hm3=gr.getUnassignedNodes(2,row);
        if(hm3.size()==0){
            return true;
        }
        //SDF
        HashMap<Coordinate,Integer>hm1=gr.sortedNodes(hm3);
        //Coordinate c1=(Coordinate)hm1.keySet().toArray()[0];
        //Domddeg
        ArrayList<Coordinate>List=new ArrayList<>();
        for (Map.Entry<Coordinate, Integer> en : hm1.entrySet()){
            List.add(en.getKey());
        }
        //Coordinate c1=gr.domddeg(List);
        //Brelaz
        Coordinate c1=gr.brelaz(List);
        int v=0;
        ArrayList<Integer>dlist=gr.DomainCount(c1,row);
        for(int i=0;i<dlist.size();i++) {
            v=dlist.get(i);
            if (gr.isConsistent(c1,v)) {
                ArrayList<Coordinate>L=gr.deleteDomain(c1,v,row);
                if(gr.isDomainEmpty(c1)){
                    return false;
                }
                c1.value = v;
                nodeCount++;
                boolean flag = Recursives(gr);
                if(flag) {
                    return true;
                }
                c1.value = 0;
                failCount++;
            }
        }
        return false;
    }
    public static boolean BacktrackingSearch(Graph gr){
        return Recursive(gr);
    }
    public static boolean Recursive(Graph gr){
        HashMap<Coordinate,Integer>hm3=gr.getUnassignedNodes(1,row);
        if(hm3.size()==0){
            return true;
        }
        //SDF
        HashMap<Coordinate,Integer>hm1=gr.sortedNodes(hm3);
        Coordinate c1=(Coordinate)hm1.keySet().toArray()[0];
        //Domddeg
        ArrayList<Coordinate>List=new ArrayList<>();
        for (Map.Entry<Coordinate, Integer> en : hm1.entrySet()){
            List.add(en.getKey());
        }
        //Coordinate c1=gr.domddeg(List);
        //Brelaz
       //Coordinate c1=gr.brelaz(List);
        //dynamic degree
        //Coordinate c1=gr.getMinDynamicDegree(List);
        int v=0;
        int l=c1.Domain.size();
        //ArrayList<Integer>dlist=gr.DomainCount(c1,row);
        for(int i=0;i<row;i++) {
             v=i+1;
             //v=dlist.get(i);
            if (gr.isConsistent(c1,v)) {
                c1.value = v;
                nodeCount++;
                boolean flag = Recursive(gr);
                if (flag) {
                    return true;
                }
                c1.value = 0;
                failCount++;
            }
        }
        return false;
    }
    public static boolean ForwardChecking(Graph gr){
        return Recursive2(gr);
    }
    public static boolean Recursive2(Graph gr){
        HashMap<Coordinate,Integer>hm3=gr.getUnassignedNodes(2,row);
        if(hm3.size()==0){
            return true;
        }
        //SDF
        HashMap<Coordinate,Integer>hm1=gr.sortedNodes(hm3);
        Coordinate c1=(Coordinate)hm1.keySet().toArray()[0];
        //Domddeg
        ArrayList<Coordinate>List=new ArrayList<>();
        for (Map.Entry<Coordinate, Integer> en : hm1.entrySet()){
            List.add(en.getKey());
        }
        //Coordinate c1=gr.domddeg(List);
        //max dynamic degree
        //Coordinate c1=gr.getMinDynamicDegree(List);
        //Brelaz
        //Coordinate c1=gr.brelaz(List);
        int v=0;
        int l=c1.Domain.size();

        ArrayList<Integer>dlist=gr.DomainCount(c1,row);
        for(int i=0;i<dlist.size();i++) {
            v=dlist.get(i);
            //v=c1.Domain.get(i);
            if (gr.isConsistent(c1,v)) {
                ArrayList<Coordinate>L=gr.deleteDomain(c1,v,row);
                if(gr.isDomainEmpty(c1)){
                    return false;
                }
                c1.value = v;
                nodeCount++;
                boolean flag = Recursive2(gr);
                if(flag) {
                    return true;
                }
                c1.value = 0;
                //gr.restoreDomain(c1,v,L);
                failCount++;
            }
        }
        return false;
    }
    public static boolean MAC(Graph gr){
        return Recursive3(gr);
    }
    public static boolean Recursive3(Graph gr){
        HashMap<Coordinate,Integer>hm3=gr.getUnassignedNodes(3,row);
        //System.out.println(hm3.size());
        if(hm3.size()==0){
            return true;
        }
        //SDF
        HashMap<Coordinate,Integer>hm1=gr.sortedNodes(hm3);
        Coordinate c1=(Coordinate)hm1.keySet().toArray()[0];
        //Domddeg
        ArrayList<Coordinate>List=new ArrayList<>();
        for (Map.Entry<Coordinate, Integer> en : hm1.entrySet()){
            List.add(en.getKey());
        }
      // Coordinate c1=gr.domddeg(List);
        //max dynamic degree
        //Coordinate c1=gr.getMinDynamicDegree(List);
        //Brelaz
       //Coordinate c1=gr.brelaz(List);
        int v=0;
        int l=c1.Domain.size();

        ArrayList<Integer>dlist=gr.DomainCount(c1,row);
        for(int i=0;i<dlist.size();i++) {
            v=dlist.get(i);
            //gr.domUpdate(row);
            c1.value=v;
            gr.domainUpdate(c1,v,row);
            if (gr.arcConsistency(c1)) {
                nodeCount++;
                boolean flag = Recursives(gr);
                if(flag) {
                    return true;
                }
                failCount++;
            }
            c1.value=0;
            gr.domUpdate(row);
        }
        return false;
    }

}
