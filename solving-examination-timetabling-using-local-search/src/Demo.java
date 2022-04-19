import java.io.*;
import java.util.*;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String []args) throws IOException,FileNotFoundException {
        ArrayList<Vertex> temp = new ArrayList<>();
        Scanner s=new Scanner(System.in);
        File f,f1=null;
        //File fu=new File("kfu-s-93.sol");
        File fu=new File("output.sol");
        PrintWriter pw=new PrintWriter(fu);
        System.out.println("1.car91");
        System.out.println("2.car92");
        System.out.println("3.kfu93");
        System.out.println("4.tre92");
        System.out.println("5.yor83");
        int r=s.nextInt();
        if(r==1){
            f = new File("car-s-91.stu");
            f1 = new File("car-s-91.crs");
        }else if(r==2){
            f = new File("car-f-92.stu");
            f1 = new File("car-f-92.crs");
        }else if(r==3){
            f = new File("kfu-s-93.stu");
            f1 = new File("kfu-s-93.crs");
        }else if(r==4){
            f = new File("tre-s-92.stu");
            f1 = new File("tre-s-92.crs");
        }else if(r==5){
             f = new File("yor-f-83.stu");
             f1 = new File("yor-f-83.crs");
        }else return;

        Scanner sc = new Scanner(f);
        Scanner sc2 = new Scanner(f1);
        Scanner sc1 = new Scanner(f);
        int v = 0;

        String line = sc2.nextLine();
        while (line != null) {
            if (line.trim().length() > 0) {
                v++;//total courses
            }

            try {
                line = sc2.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        Graph g = new Graph(v);
        System.out.println("Total Courses: " + g.v);
        int a = 0;
        String line2 = sc1.nextLine();
        while (line2 != null) {
            if (line2.trim().length() > 0) {
                a++;//total students
            }

            try {
                line2 = sc1.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        System.out.println("Total students: " + a);
        for (int i = 0; i < a; i++) {
            String[] st = sc.nextLine().split(" ");
            for (int j = 0; j < st.length - 1; j++) {
                int idx1 = Integer.parseInt(st[j]);
                for (int k = j + 1; k < st.length; k++) {
                    int idx2 = Integer.parseInt(st[k]);
                    if (!g.isEdge(g.AL.get(idx1 - 1), g.AL.get(idx2 - 1))) {
                        g.add_edge(g.AL.get(idx1 - 1), g.AL.get(idx2 - 1));
                    }
                }
            }

        }
        for (int i = 0; i < v; i++) {
            temp.add(g.AL.get(i));
        }
        //Collections.sort(g.AL,Vertex.degreeComparator);

        //Collections.shuffle(g.AL,new Random());


        System.out.println("Enter the choice: ");
        System.out.println("1.Scheme-1");
        System.out.println("2.Scheme-2");
        int t=s.nextInt();
        if(t==1) {
            g.colouring(g.AL);
        }else {
            Collections.sort(g.AL,Vertex.degreeComparator);
            g.colouringDsatur(g.AL,temp);
        }
        HashMap<Vertex,Integer> minMap=new HashMap<>();
        for(int l=0;l<g.v;l++){
            minMap.put(g.AL.get(l),g.AL.get(l).colour);
        }
        double avgPenalty=averagePenalty(minMap,temp, a, f);
        double mini=avgPenalty;
        //System.out.println("Initial penalty: "+mini);
        double minimum=10000.0;
        HashMap<Vertex,Integer>hmap=new HashMap<>();

        while(minimum!=mini) {
            minimum=mini;
            for (int k = 0; k < g.v; k++) {

                //ArrayList<Vertex> bfsList = BFS(g,g.AL.get(k));

                if (g.AL.get(k).Adjacent.size() != 0) {
                    ArrayList<Vertex> bfsList = BFS(g,g.AL.get(k),g.AL.get(k).Adjacent.get(0));
                    HashMap<Vertex,Integer> newTable = kempeChain(minMap,bfsList,g.AL.get(k), g.AL.get(k).Adjacent.get(0));

                    //System.out.println("Average Penalty: "+avgPenalty);
                    double d = averagePenalty(newTable,temp, a, f);
                    if (d < mini) {
                        mini = d;
                        hmap=newTable;
                    }
                }
            }
            minMap=hmap;


        }
        System.out.println("Average Penalty: "+minimum);
        /*for(Vertex h:g.AL){
            pw.write(h+" "+h.colour+"\r\n");
        }*/
        for (Map.Entry<Vertex, Integer> e : minMap.entrySet()) {
            pw.write(e.getKey() + " " + e.getValue()+"\r\n");
        }

        pw.close();
        zeroPenalty(temp, a, f);
    }
    public static ArrayList<Vertex> BFS(Graph g,Vertex s,Vertex d){
        int c1=s.colour;
        int c2=d.colour;
        for(int y=0;y<g.v;y++){
            g.AL.get(y).isVisited=false;
        }
        ArrayList<Vertex>t=new ArrayList<>();
        LinkedList<Vertex>queue=new LinkedList<>();

        s.isVisited=true;
        queue.add(s);
        while(queue.size()>0){
            Vertex p=queue.poll();
            t.add(p);
            for(int i=0;i<p.Adjacent.size();i++){
                if(p.Adjacent.get(i).isVisited==false){
                    p.Adjacent.get(i).isVisited=true;
                    if(p.Adjacent.get(i).colour==c1 || p.Adjacent.get(i).colour==c2){
                        queue.add(p.Adjacent.get(i));
                    }
                }
            }
        }

        return t;
    }
    public static HashMap<Vertex, Integer> kempeChain(HashMap<Vertex,Integer>table,ArrayList<Vertex>bfsList,Vertex s,Vertex d){
        int colour1=s.colour;
        int colour2=d.colour;

        for(int i=0;i<bfsList.size();i++){
            if(table.get(bfsList.get(i))==colour1){
                int r=table.remove(bfsList.get(i));
                table.put(bfsList.get(i),colour2);

            }
            else if(table.get(bfsList.get(i))==colour2){

                int e=table.remove(bfsList.get(i));
                table.put(bfsList.get(i),colour1);

            }
        }

        return table;
    }

    public static double averagePenalty(HashMap<Vertex,Integer>Table,ArrayList<Vertex>temp,int a,File f) throws FileNotFoundException {
        Scanner sc3=new Scanner(f);
        int penalty=0;
        ArrayList<Vertex> temporary=new ArrayList<>();
        for(int i=0;i<a;i++){
            String []st=sc3.nextLine().split(" ");
            for(int j=0;j<st.length;j++) {
                int idx3 = Integer.parseInt(st[j]);
                temporary.add(temp.get(idx3 - 1));
                temporary.get(j).colour=Table.get(temporary.get(j));
            }


            Collections.sort(temporary,Vertex.colourComparator);

            for(int k=0,m=1;k<temporary.size()-1;k++,m++){

                //for(int k=0;k<temporary.size()-1;k++) {
                    //for (int m = k + 1; m < temporary.size(); m++) {
                        //if((temporary.get(m).colour-temporary.get(k).colour)==1){
                        if ((Table.get(temporary.get(m)) - Table.get(temporary.get(k))) == 1) {
                            penalty += 16;
                            //System.out.println(penalty);
                        }
                        // else if((temporary.get(m).colour-temporary.get(k).colour)==2){
                        else if ((Table.get(temporary.get(m)) - Table.get(temporary.get(k))) == 2) {
                            penalty += 8;
                        }
                        //else if((temporary.get(m).colour-temporary.get(k).colour)==3){
                        else if ((Table.get(temporary.get(m)) - Table.get(temporary.get(k))) == 3) {
                            penalty += 4;
                        }
                        // else if((temporary.get(m).colour-temporary.get(k).colour)==4){
                        if ((Table.get(temporary.get(m)) - Table.get(temporary.get(k))) == 4) {
                            penalty += 2;
                        }
                        //else if((temporary.get(m).colour-temporary.get(k).colour)==5){
                        if ((Table.get(temporary.get(m)) - Table.get(temporary.get(k))) == 5) {
                            penalty += 1;
                        } else {
                            penalty += 0;
                        }
                    }
               // }
            temporary.clear();
        }
        double avgPenalty=0.0;
        avgPenalty=(penalty/(a*1.00));
        return avgPenalty;

    }
    public static void zeroPenalty(ArrayList<Vertex>temp,int a,File f) throws FileNotFoundException{
        Scanner sc4=new Scanner(f);
        ArrayList<Vertex>temporary1=new ArrayList<>();
        int maxtimeslot=-10;
        for(int i=0;i<a;i++) {
            String[] st = sc4.nextLine().split(" ");
            for (int j = 0; j < st.length; j++) {
                int idx3 = Integer.parseInt(st[j]);
                temporary1.add(temp.get(idx3 - 1));
            }
            Collections.sort(temporary1,Vertex.colourComparator);
            for(int k=0,m=1;k<temporary1.size()-1;k++,m++){
                temporary1.get(m).colour=temporary1.get(k).colour+6;
            }

            for(int w=0;w<temporary1.size();w++){
                if(temporary1.get(w).colour>maxtimeslot){
                    maxtimeslot=temporary1.get(w).colour;
                }
            }
            temporary1.clear();
        }
        maxtimeslot+=1;
        System.out.println("Total timeslots when penalty is zero: "+maxtimeslot);

    }

}


