import javafx.util.Pair;

import java.text.Collator;
import java.util.*;

public class Graph {
    double v;//total vertices
    int[][] vertexArray;
    ArrayList<Coordinate> AL;

    public Graph(double v,int[][] vertexArray) {
        this.v = v;
        this.vertexArray=vertexArray;
        this.AL=new ArrayList<>();
        double n=Math.sqrt(v);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++) {
                if(this.vertexArray[i][j]!=0) {
                    ArrayList<Integer>DL=new ArrayList<>();
                    DL.add(vertexArray[i][j]);
                    Coordinate b = new Coordinate(i, j, this.vertexArray[i][j],DL);
                    this.AL.add(b);
                }else{
                    ArrayList<Integer> DL = new ArrayList<>();
                    for(int y=1;y<=n;y++) {
                        DL.add(y);
                    }
                    Coordinate b = new Coordinate(i, j, this.vertexArray[i][j],DL);
                    this.AL.add(b);

                }
            }
        }
    }
    public void add_edge(Coordinate u,Coordinate v){
        u.Adjacent.add(v);
        v.Adjacent.add(u);
    }
    public boolean isEdge(Coordinate u,Coordinate v){
        for(int j=0;j<u.Adjacent.size();j++){
            if(u.Adjacent.get(j).equals(v)){
                return true;
            }
        }
        return false;
    }
    void updateDomain(){
        for(int i=0;i<this.AL.size();i++){
            for(int j=0;j<this.AL.get(i).Adjacent.size();j++){
                int v=this.AL.get(i).Adjacent.get(j).value;
                if(v!=0){
                    this.AL.get(i).Domain.remove((Integer)v);
                }
            }
        }
    }
    HashMap<Coordinate,Integer> getUnassignedNodes(int type,int n){
        HashMap<Coordinate,Integer>hmap=new HashMap<>();

        for(int i=0;i<this.AL.size();i++){
            if(this.AL.get(i).value==0){
                if(type!=1) {
                    ArrayList<Integer>dlist=DomainCount(this.AL.get(i),n);
                }
                hmap.put(this.AL.get(i),this.AL.get(i).Domain.size());
            }
        }
        return hmap;
    }
    void domainUpdate(Coordinate c,int v,int n){
        c.Domain.clear();
        c.Domain.add(v);
        for(int i=0;i<this.AL.size();i++){
            if(this.AL.get(i).value==0 && c!=this.AL.get(i)){
                ArrayList<Integer>dlist=DomainCount(this.AL.get(i),n);
            }
        }
    }
    HashMap<Coordinate,Integer> sortedNodes(HashMap<Coordinate,Integer>hmap){
        //HashMap<Coordinate,Integer>temp=new HashMap<>();
        List<Map.Entry<Coordinate, Integer> > list =
                new LinkedList<Map.Entry<Coordinate, Integer> >(hmap.entrySet());

        // Sort the list
        Collections.sort(list,new Comparator<Map.Entry<Coordinate, Integer> >() {
            public int compare(Map.Entry<Coordinate, Integer> o1,
                               Map.Entry<Coordinate, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Coordinate, Integer> temp = new LinkedHashMap<Coordinate, Integer>();
        for (Map.Entry<Coordinate, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    boolean isConsistent(Coordinate c,int value){
        for(int i=0;i<c.Adjacent.size();i++){
            if(c.Adjacent.get(i).value==value){
                return false;
            }
        }
        return true;
    }
    void print(int n){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(this.vertexArray[i][j]+" ");
            }
            System.out.println();
        }
    }
    void ArrayUpdate(){
        for(int i=0;i<this.AL.size();i++){
            Coordinate c1=this.AL.get(i);
            this.vertexArray[c1.x][c1.y]=c1.value;
        }
    }
    boolean isCompleteArray(int[][]array,int n){
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                if (array[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    ArrayList<Coordinate> deleteDomain(Coordinate c,int v,int n){
        ArrayList<Coordinate>L=new ArrayList<>();
        for(int i=0;i<c.Adjacent.size();i++){
            ArrayList<Integer>dl=DomainCount(c.Adjacent.get(i),n);
            if(dl.contains(v)){
                L.add(c.Adjacent.get(i));
                dl.remove((Integer)v);
                c.Adjacent.get(i).Domain=dl;
            }
        }
        return L;
    }
    void restoreDomain(Coordinate c,int v,ArrayList<Coordinate>L){
        for(int i=0;i<c.Adjacent.size();i++){
            if(L.contains(c.Adjacent.get(i))) {
                c.Adjacent.get(i).Domain.add(v);
            }

        }
    }
    boolean isDomainEmpty(Coordinate c){
        for(int i=0;i<c.Adjacent.size();i++){
            if(c.Adjacent.get(i).Domain.size()==0){
                return true;
            }
        }
        return false;
    }
    ArrayList<Integer> DomainCount(Coordinate c,int n){
        ArrayList<Integer>dlist=new ArrayList<Integer>();
        for(int i=1;i<=n;i++){
            dlist.add(i);
        }
        for(int i=0;i<c.Adjacent.size();i++){
            int v=c.Adjacent.get(i).value;
            if(v!=0){
                dlist.remove((Integer)v);
            }
        }
        c.Domain=dlist;
        //System.out.println(c.Domain.size()+"::::"+dlist.size());
        return dlist;
    }
    int getDynamicDegree(Coordinate c){
        int count=0;
        for(int i=0;i<c.Adjacent.size();i++){
            int v=c.Adjacent.get(i).value;
            if(v==0){
                count++;
            }
        }
        c.forwardDegree=count;
        return count;
    }
    Coordinate getMinDynamicDegree(ArrayList<Coordinate>List){
        int minimum=10000;
        Coordinate c=null;
        for(int i=0;i< List.size();i++){
            int ct=getDynamicDegree(List.get(i));
            if(ct<minimum){
                minimum=ct;
                c=List.get(i);
            }
        }
        return c;
    }
    Coordinate domddeg(ArrayList<Coordinate>List){
        double minimum=10000;
        Coordinate c=null;
        for(int i=0;i<List.size();i++){
            int domainSize=List.get(i).Domain.size();
            int forwardDegree=getDynamicDegree(List.get(i));
            if(forwardDegree!=0) {
                double ratio = ((domainSize * 1.00) / forwardDegree);
                if (ratio < minimum) {
                    minimum = ratio;
                    c = List.get(i);
                }
            }else{
                c=List.get(i);
                return c;
            }
        }
        return c;
    }
    Coordinate brelaz(ArrayList<Coordinate>List){
        Coordinate c=List.get(0);
        int d=getDynamicDegree(c);
        for(int i=1;i<List.size();i++){
            if(List.get(i).Domain.size()==c.Domain.size()){
                int dg=getDynamicDegree(List.get(i));
                if(dg>d){
                    c=List.get(i);
                }
            }
        }
        return c;
    }
    boolean compare(ArrayList<Integer>L1,ArrayList<Integer>L2) {
        boolean removed = false;
        if (L2.size() == 1) {
            int v = L2.get(0);
            if (L1.contains(v)) {
                L1.remove((Integer)v);
                removed = true;
            }
        }
        return removed;
    }
    boolean arcConsistency(Coordinate c){
        boolean flag=true;
        ArrayList<Pair<Coordinate,Coordinate>> pairList=new ArrayList<>();
        for(int i=0;i<c.Adjacent.size();i++) {
           if (c.Adjacent.get(i).Domain.size() != 0) {
                pairList.add(new Pair(c, c.Adjacent.get(i)));
                for (int j = 0; j < c.Adjacent.get(i).Adjacent.size(); j++) {
                    if (c != c.Adjacent.get(i).Adjacent.get(j) && c.Adjacent.get(i).Adjacent.get(j).Domain.size() != 0) {
                        pairList.add(new Pair(c.Adjacent.get(i), c.Adjacent.get(i).Adjacent.get(j)));
                    }
                }
            }
        }
        while (pairList.size()!=0){
            Pair<Coordinate,Coordinate>p=pairList.remove(0);
            if(compare(p.getValue().Domain,p.getKey().Domain)){
                if(p.getValue().Domain.size()==0){
                    return false;
                }else{
                    for(int k=0;k<p.getValue().Adjacent.size();k++){
                        if(c!=p.getValue().Adjacent.get(k) && p.getValue().Adjacent.get(k).Domain.size()!=0){
                            pairList.add(new Pair(p.getValue(),p.getValue().Adjacent.get(k)));
                        }
                    }
                }
            }
        }
        return true;
    }
    void domUpdate(int n){
        for(int i=0;i<this.AL.size();i++){
            if(this.AL.get(i).value==0){
                this.AL.get(i).Domain.clear();
                ArrayList<Integer>dlist=DomainCount(this.AL.get(i),n);
            }
        }
    }

}
