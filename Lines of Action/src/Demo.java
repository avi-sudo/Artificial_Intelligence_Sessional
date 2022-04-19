import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of the board: ");
        int size = sc.nextInt();
        int[][] a = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || i == (size - 1)) {
                    if (j != 0 && j != (size - 1)) {
                        a[i][j] = 1;
                    }
                } else if (i != 0 && i != (size - 1)) {
                    if (j == 0 || j == (size - 1)) {
                        a[i][j] = 2;
                    }
                } else {
                    a[i][j] = 0;
                }
            }
        }
        State s = new State(a, size);
        s.print();
        System.out.println("Enter the option: ");
        System.out.println("1.Human vs Human");
        System.out.println("2.AI vs Human");
        int option = sc.nextInt();
        if (option == 1) {
            int player = 1;
            //int Blackplayer=1;
            //int Whiteplayer=2;
            while (true) {
                System.out.println("Enter the Move of Player " + player);
                int x1 = sc.nextInt();
                int y1 = sc.nextInt();
                if (s.board[x1][y1] != player) {
                    System.out.println("Retry");
                    continue;
                }
                //s.print();
                Coordinate c1 = new Coordinate(x1, y1);
                ArrayList<Coordinate> AL = s.generateMoves(x1, y1);
                System.out.println("All possible coordinates of moves: ");
                for (int i = 0; i < AL.size(); i++) {
                    AL.get(i).print();
                }
                if (AL.size() == 0) {
                    System.out.println("There is no legal move");
                    System.out.println("Player " + player + " can't move");
                    if (player == 1) player = 2;
                    else player = 1;
                    continue;
                }
                int x2 = sc.nextInt();
                int y2 = sc.nextInt();
                Coordinate c3 = new Coordinate(x2, y2);
                if (AL.contains(c3)) {
                    s.move(c1,c3);
                    s.print();
                } else {
                    System.out.println("Retry");
                    continue;
                }
                int w=s.isEndgame(s.board,player);
                if (w==1) {
                    System.out.println("Player 1 has won the game");
                    break;
                }else if(w==2){
                    System.out.println("Player 2 has won the game");
                    break;
                }
                if (player == 1) {
                    player = 2;
                } else player = 1;
            }
        }else{
            Agent ai=new Agent();
            int player=1;
            long TimeLimit;
            if(s.s==8){
                 TimeLimit=2000;
            }else {
                 TimeLimit=1000;
            }
            //AI player=1(black)
            //human player=2(white)
            while (true) {
                if(player==1){
                    System.out.println("Move of the AI Agent: ");
                    ArrayList<Coordinate>List=ai.getBestMove(s,player,TimeLimit);
                    s.move(List.get(0),List.get(1));
                    s.print();
                    System.out.print("Src Checker: ");List.get(0).print();
                    System.out.print("Destination Checker: ");List.get(1).print();
                    int w=s.isEndgame(s.board,player);
                    if(w==1){
                        System.out.println("Player 1 has won the game");
                        break;
                    }else if(w==2){
                        System.out.println("Player 2 has won the game");
                        break;
                    }
                }else {
                    System.out.println("Enter the Move of Player " + player);
                    int x1 = sc.nextInt();
                    int y1 = sc.nextInt();
                    if (s.board[x1][y1] != player) {
                        System.out.println("Retry");
                        continue;
                    }
                    //s.print();
                    Coordinate c1 = new Coordinate(x1, y1);
                    ArrayList<Coordinate> AL = s.generateMoves(x1, y1);
                    System.out.println("All possible coordinates of moves: ");
                    for (int i = 0; i < AL.size(); i++) {
                        AL.get(i).print();
                    }
                    if (AL.size() == 0) {
                        System.out.println("Player " + player + " can't move");
                        if (player == 1) player = 2;
                        else player = 1;
                        continue;
                    }
                    int x2 = sc.nextInt();
                    int y2 = sc.nextInt();
                    Coordinate c3 = new Coordinate(x2, y2);
                    if (AL.contains(c3)) {
                        s.move(c1,c3);
                        s.print();
                        System.out.println();
                    } else {
                        System.out.println("Retry");
                        continue;
                    }
                    int w=s.isEndgame(s.board,player);
                    if (w==1) {
                        System.out.println("Player 1 has won the game");
                        break;
                    }else if(w==2){
                        System.out.println("Player 2 has won the game");
                        break;
                    }
                }
                if (player == 1) {
                    player = 2;
                } else player = 1;
            }
        }
    }
}
