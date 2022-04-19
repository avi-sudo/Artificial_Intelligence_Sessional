import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static javafx.application.Platform.exit;
import java.util.concurrent.TimeUnit;
public class Demo {
    public static void main(String[] args) throws IOException {
        int i, j;
        ArrayList<State>reverse=new ArrayList<>();
        int[][] startState = new int[4][4];
        int[][] goalState =new int[4][4];
        File f = new File("C:\\Users\\Dell\\IdeaProjects\\p5.txt");
        PrintStream out =new PrintStream(new FileOutputStream("C:\\Users\\Dell\\IdeaProjects\\out5.txt"));
        System.setOut(out);
       // File f1=new File("C:\\Users\\Dell\\IdeaProjects\\out1.txt");
        //FileWriter br=null;
        //BufferedWriter br=null;
        //br=new FileWriter(f1);
        //br=new BufferedWriter(fr);
        Scanner sc = new Scanner(f);
        int n=sc.nextInt();
        //while (sc.hasNext()) {

            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {

                    goalState[i][j] = sc.nextInt();
                }
            }
            for(int k=1;k<n;k++) {
                 //System.out.println("Test Case "+k+":");
                for (i = 0; i < 4; i++) {
                    for (j = 0; j < 4; j++) {

                        startState[i][j] = sc.nextInt();
                    }
                }
                State s=new State(startState,goalState,0);
                boolean b=s.IsSolvable();
                boolean b2=s.IsSolvable2();

                if(b==true && b2==true){
                    A_Star_Search star=new A_Star_Search(s);
                    System.out.println("Enter the choice:");
                    System.out.println("1.Displacement Heuristic");
                    System.out.println("2.Manhattan Heuristic");
                    Scanner sc1=new Scanner(System.in);
                    String choice=sc1.nextLine();
                    if(choice.equalsIgnoreCase("1")){
                        long startTime=System.currentTimeMillis();
                        State FinalState= star.AStarSearchDisplacement();
                        long endTime=System.currentTimeMillis();
                        double elapsedTime=(endTime-startTime)/1000.0;
                       // br.write("Elapsed time in seconds is : "+elapsedTime);
                        System.out.println("Elapsed time in seconds is : "+elapsedTime);
                        while(FinalState!=null){
                            //FinalState.print();
                            reverse.add(FinalState);
                            FinalState=FinalState.parent;
                            //System.out.println();
                        }
                        for(int t=reverse.size()-1;t>=0;t--){
                            reverse.get(t).print();
                        }
                        reverse.clear();
                    }
                    else if(choice.equalsIgnoreCase("2")){
                        long startTime=System.currentTimeMillis();
                        State FinalState= star.AStarSearchManhattan();
                        long endTime=System.currentTimeMillis();
                        double elapsedTime=(endTime-startTime)/1000.0;
                        //br.write("Elapsed time in seconds is : "+elapsedTime);
                        System.out.println("Elapsed time in seconds is : "+elapsedTime);
                        // State FinalState= star.AStarSearchManhattan();
                        while(FinalState!=null){
                            //FinalState.print();
                            reverse.add(FinalState);
                            FinalState=FinalState.parent;
                            // System.out.println();
                        }
                        for(int t=reverse.size()-1;t>=0;t--){
                            reverse.get(t).print();
                        }
                        reverse.clear();
                    }
                    else exit();
                }
                else{
                    //br.write("No solution");
                    System.out.println("No solution");
                }



            }
        //}
        /*while (sc.hasNext()) {
            System.out.println("NAvid");
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {

                    goalState[i][j] = sc.nextInt();
                }
            }
        }*/


    }
}
