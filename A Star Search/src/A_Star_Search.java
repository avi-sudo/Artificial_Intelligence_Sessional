import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
public class A_Star_Search  {
    //File f1=new File("C:\\Users\\Dell\\IdeaProjects\\out1.txt");
   // FileWriter fr=null;
    //BufferedWriter br=null;
    //fr=new FileWriter(f1);
   // br=new BufferedWriter(fr);
    int minimum;
    ArrayList<State> openList;
    //PriorityQueue<State>openList;
   // ArrayList<State> closedList;
    HashMap<State,Integer> closedList;
    A_Star_Search(State s){
        this.openList=new ArrayList<>();
        //this.openList=new PriorityQueue<>();
        //this.closedList=new ArrayList<>();
        this.closedList = new HashMap<>();
        openList.add(s);

        closedList.clear();
    }
    State AStarSearchDisplacement() throws IOException {
        int i,f,expandedNode=1;

        int index=-1;
        State promisingState;
        while (!openList.isEmpty()){
            minimum=100000;

            for(i=0;i<openList.size();i++) {
                f = openList.get(i).g + openList.get(i).displacement();

                if (f < minimum) {

                    minimum = f;
                    index = i;
                }
            }

            promisingState=openList.get(index);
           // System.out.println(openList.size());
            if(promisingState.IsSuccess()){
                //br.write("Minimum cost is: "+minimum);
                System.out.println("Minimum cost is: "+minimum);
                //System.out.println("Expanded Nodes : "+expandedNode);
                //br.write("Explored Nodes : "+closedList.size());
                System.out.println("Explored Nodes : "+closedList.size());
                return promisingState;
            }

                openList.remove(promisingState);
                //closedList.add(promisingState);
                closedList.put(promisingState,1);
                ArrayList<State> generatedStates=promisingState.generate();

                for(int k=0;k<generatedStates.size();k++){
                    //if(!closedList.contains(generatedStates.get(k))){
                        if(closedList.get(generatedStates.get(k))==null){
                        generatedStates.get(k).parent=promisingState;
                        generatedStates.get(k).setG(generatedStates.get(k).getG()+1);
                        if(!openList.contains(generatedStates.get(k))){
                           // expanded++;
                            openList.add(generatedStates.get(k));
                            expandedNode+=1;

                        }
                    }
                }

        }
        return null;
    }
    State AStarSearchManhattan() throws IOException {
        int i,f,expandedNode=1;

        int index=-1;
        State promisingState;
        while (!openList.isEmpty()){
            minimum=100000;

            for(i=0;i<openList.size();i++) {

                f = openList.get(i).g + openList.get(i).manhattan();
                if (f < minimum) {
                    minimum = f;
                    index = i;
                }
            }
            promisingState=openList.get(index);

            if(promisingState.IsSuccess()){
                //System.out.println(promisingState.manhattan());
                System.out.println("Minimum cost is: "+minimum);
               // br.write("Minimum cost is: "+minimum);
                //System.out.println("Expanded Nodes : "+expandedNode);
                System.out.println("Explored Nodes : "+closedList.size());
                //br.write("Explored Nodes : "+closedList.size());
                return promisingState;
            }

                openList.remove(promisingState);
                //closedList.add(promisingState);
                closedList.put(promisingState,1);
                ArrayList<State>generatedStates=promisingState.generate();
                for(int k=0;k<generatedStates.size();k++){

                    //if(!closedList.contains(generatedStates.get(k))){
                        if(closedList.get(generatedStates.get(k))==null){
                        generatedStates.get(k).parent=promisingState;
                        generatedStates.get(k).g=generatedStates.get(k).g+1;

                        if(!openList.contains(generatedStates.get(k))){

                            openList.add(generatedStates.get(k));
                            expandedNode++;

                        }
                    }
                }

        }
        return null;
    }

}
