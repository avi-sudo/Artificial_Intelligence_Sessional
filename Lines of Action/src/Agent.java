import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Agent {
    public Agent() {
    }
    ArrayList<Coordinate> getBestMove(State s, int player,long TimeLimit) {
        long startTime = System.currentTimeMillis();
        double Maxscore=-10000;
        double alpha=-1000000;
        ArrayList<Coordinate>L=new ArrayList<>();
        HashMap<Coordinate, ArrayList<Coordinate>> hmap =s.allPossibleMoves(s.board,player);
        for(int k=1;k<10;k++) {
            for (Map.Entry<Coordinate, ArrayList<Coordinate>> e : hmap.entrySet()) {
                for(int i=0;i<e.getValue().size();i++){
                    int n=s.board[e.getKey().x][e.getKey().y];
                    int t=s.board[e.getValue().get(i).x][e.getValue().get(i).y];
                    s.move(e.getKey(),e.getValue().get(i));
                    double score = minimax(s, k, alpha, 1000000, 2,startTime,TimeLimit);
                    s.reverseMove(e.getKey(), e.getValue().get(i), n, t);
                    if (score > Maxscore) {
                        Maxscore = score;
                        L.clear();
                        L.add(e.getKey());
                        L.add(e.getValue().get(i));
                    }
                    alpha=getMax(score,alpha);
                }
            }
            long currentTime = System.currentTimeMillis();
            long elapsedTime = (currentTime - startTime);
            if(elapsedTime>=TimeLimit){
                return L;
            }
        }
        return L;
    }
    double minimax(State s,int depth,double alpha,double beta,int player,long startTime,long TimeLimit){
        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - startTime);
        if(depth==0 || elapsedTime>=TimeLimit){
            return heuristic(s);//heuristic value of that state
        }
        int w=s.isEndgame(s.board,player);
        if(w==player){
            return 100000;
        }else if(w!=0){
            return -100000;
        }
        if(player==1){
            double maxEval=-100000;
            //for each child
            boolean flag=false;
            HashMap<Coordinate, ArrayList<Coordinate>> hmap =s.allPossibleMoves(s.board,1);
            for (Map.Entry<Coordinate, ArrayList<Coordinate>> e : hmap.entrySet()) {
                for (int i = 0; i < e.getValue().size(); i++) {
                    int n = s.board[e.getKey().x][e.getKey().y];
                    int t = s.board[e.getValue().get(i).x][e.getValue().get(i).y];
                    s.move(e.getKey(), e.getValue().get(i));
                    double eval = minimax(s, depth - 1, alpha, beta,2,startTime,TimeLimit);
                    s.reverseMove(e.getKey(),e.getValue().get(i),n,t);
                    maxEval = getMax(eval, maxEval);
                    alpha = getMax(alpha, eval);
                    if (beta <= alpha) {
                        flag=true;
                        break;
                    }
                }if(flag){
                    break;
                }
            }
            return maxEval;
        }else{
            double minEval=100000;
            //for each child
            boolean flag2=false;
            HashMap<Coordinate, ArrayList<Coordinate>> hmap =s.allPossibleMoves(s.board,2);
            for (Map.Entry<Coordinate, ArrayList<Coordinate>> e : hmap.entrySet()) {
                for (int i = 0; i < e.getValue().size(); i++) {
                    int n = s.board[e.getKey().x][e.getKey().y];
                    int t = s.board[e.getValue().get(i).x][e.getValue().get(i).y];
                    s.move(e.getKey(), e.getValue().get(i));
                    double eval = minimax(s, depth - 1, alpha, beta,1,startTime,TimeLimit);
                    s.reverseMove(e.getKey(),e.getValue().get(i),n,t);
                    minEval = getMin(eval, minEval);
                    beta = getMin(beta, eval);
                    if (beta <= alpha) {
                        flag2=true;
                        break;
                    }
                }
                if(flag2) {
                    break;
                }
            }
            return minEval;
        }
    }
    double getMax(double a,double b){
        if(a>b){
            return a;
        }else{
            return b;
        }
    }
    double getMin(double a,double b){
        if(a<b){
            return a;
        }else{
            return b;
        }
    }
    double heuristic(State s){
        double EvalScore=heuristic1(s)+heuristic2(s)+heuristic3(s)+heuristic4(s)+heuristic5(s);
        return EvalScore;

    }
    double heuristic1(State s){
        int[][] temp=s.getPieceSquareTable(s.s);
        ArrayList<Coordinate>L=s.getPositions(1);
        double Blackscore=0;
        for(int i=0;i<L.size();i++){
            Blackscore+=temp[L.get(i).x][L.get(i).y];
        }
        ArrayList<Coordinate>L2=s.getPositions(2);
        double Whitescore=0;
        for(int i=0;i<L2.size();i++){
            Whitescore+=temp[L2.get(i).x][L2.get(i).y];
        }
        return (Blackscore-Whitescore);
    }
    double heuristic2(State s){
        return (s.quadCount(1)-s.quadCount(2));
    }
    double heuristic3(State s){
        return (s.avgDistance(2)-s.avgDistance(1));
    }
    double heuristic4(State s) {
        ArrayList<Coordinate> L = s.getPositions(1);
        ArrayList<Coordinate> L2= s.getPositions(2);
        double BlackScore=0;
        double WhiteScore=0;
        for (int i = 0; i < L.size(); i++) {
            BlackScore+=s.connectedCount(L.get(i),1);
        }
        for (int i = 0; i < L2.size(); i++) {
            WhiteScore+=s.connectedCount(L2.get(i),2);
        }
        return (BlackScore-WhiteScore);
    }
    double heuristic5(State s){
        double BlackScore=s.numberOfMoves(1);
        double WhiteScore=s.numberOfMoves(2);
        return (BlackScore-WhiteScore);
    }
}
