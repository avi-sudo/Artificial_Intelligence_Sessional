import java.util.*;

public class Graph {
    int v;//total vertices

    ArrayList <Vertex> AL;

    public Graph(int v) {
        this.v = v;
        this.AL=new ArrayList<>();
        for(int i=1;i<=v;i++){
            Vertex b=new Vertex(i);
            this.AL.add(b);
        }
    }
    public void add_edge(Vertex u,Vertex v){
        u.Adjacent.add(v);
        v.Adjacent.add(u);
    }
    public boolean isEdge(Vertex u,Vertex v){
        for(int j=0;j<u.Adjacent.size();j++){
            if(u.Adjacent.get(j).equals(v)){
                return true;
            }
        }
        return false;
    }
    public void colouring(ArrayList<Vertex>L){
        Collections.sort(L,Vertex.degreeComparator);
        //Collections.shuffle(L,new Random());
        /*for(Vertex q:L) {
            System.out.println("After sort" + q);
        }*/
        for(int i=0;i<L.size();i++){
            L.get(i).colour=-1;
        }
        L.get(0).colour=0;
        boolean available[]=new boolean[this.v];
        Arrays.fill(available,true);
        for(int j=1;j<L.size();j++){
            for(int it=0;it<L.get(j).Adjacent.size();it++){
                if(L.get(j).Adjacent.get(it).colour!=-1){
                    available[L.get(j).Adjacent.get(it).colour]=false;
                }
            }
            int c;
            for(c=0;c<this.v;c++){
                if(available[c]){
                    break;
                }
            }
            L.get(j).colour=c;
            Arrays.fill(available,true);
        }
        /*for(int k=0;k<L.size();k++){
            System.out.println("vertex "+L.get(k)+":colour "+L.get(k).colour);
        }*/
        int maximum=-10;
        for(int i=0;i<L.size();i++){
            if(L.get(i).colour>maximum){
                maximum=L.get(i).colour;
            }
        }
        maximum+=1;
        System.out.println("Total Timeslots: "+maximum);

    }
    public void colouringDsatur(ArrayList<Vertex>L,ArrayList<Vertex>temp){
        for(int i=0;i<L.size();i++){
            L.get(i).colour=-1;
        }

        //Collections.sort(L,Vertex.degreeComparator);
        L.get(0).colour=0;
        boolean available[]=new boolean[this.v];
        Arrays.fill(available,true);
        int j;
        ArrayList<Vertex>non_coloured=new ArrayList<>();
        for(int u=1;u<L.size();u++){
            non_coloured.add(L.get(u));
        }
        //System.out.println(non_coloured.size());
        while(non_coloured.size()>0){
            //System.out.println(non_coloured.size());
            j=getHighestSaturation(temp);

            //System.out.print(L.get(j).Adjacent.size());
            for(int it=0;it<temp.get(j).Adjacent.size();it++){
                if(temp.get(j).Adjacent.get(it).colour!=-1){
                    available[temp.get(j).Adjacent.get(it).colour]=false;
                }
            }
            int c;
            for(c=0;c<this.v;c++){
                if(available[c]){
                    break;
                }
            }
            temp.get(j).colour=c;
            non_coloured.remove(temp.get(j));
            //System.out.println(non_coloured.size());
            Arrays.fill(available,true);
        }
        /*for(int k=0;k<L.size();k++){
            System.out.println("vertex "+L.get(k)+":colour "+L.get(k).colour);
        }*/
        int maximum=-10;
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).colour>maximum){
                maximum=temp.get(i).colour;
            }
        }
        maximum+=1;
        System.out.println("Total Timeslots: "+maximum);
    }
    public static int getHighestSaturation(ArrayList<Vertex>L){

        int maxSaturation=0;
        int maxSaturationIndex=0;
       // boolean flag=false;
        for(int i=0;i<L.size();i++){

            if(L.get(i).colour==-1){
                ArrayList<Boolean>ColourList=new ArrayList<>();
                for(int w=0;w<L.size();w++){
                    ColourList.add(false);
                }
                int count=0;
                for(int j=0;j<L.get(i).Adjacent.size();j++){
                    if(L.get(i).Adjacent.get(j).colour!=-1){
                       if(ColourList.get(L.get(i).Adjacent.get(j).colour)==false){
                           ColourList.add(L.get(i).Adjacent.get(j).colour,true);
                       }
                        //count++;
                    }
                }
                for(int y=0;y<ColourList.size();y++){
                    if(ColourList.get(y)==true){
                        count++;
                    }
                }
                int tempSaturation=count;
                //System.out.println(count);
                if(tempSaturation>maxSaturation){
                    maxSaturation=tempSaturation;
                    maxSaturationIndex=i;
                    //flag=true;
                }
                else if(tempSaturation==maxSaturation && L.get(i).getDegree()>=L.get(maxSaturationIndex).getDegree()){
                    maxSaturationIndex=i;
                    //flag=true;
                }
                else if(tempSaturation==0){

                    maxSaturationIndex = i;

                }
            }
        }
        //System.out.println(maxSaturationIndex);
        return maxSaturationIndex;
    }

}

