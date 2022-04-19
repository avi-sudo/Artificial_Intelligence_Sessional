
import java.util.*;

public class State {
    int [][]board;
    int s;

    public State(int[][] board,int s) {
        this.s=s;
        this.board = new int[s][s];
        for(int i=0;i<s;i++){
            for(int j=0;j<s;j++){
                this.board[i][j]=board[i][j];
            }
        }
    }

    void print(){
        System.out.print(" |"+"\t");
        for(int i=0;i<this.s;i++){
            System.out.print(i+"\t");
        }
        System.out.println();
        System.out.println("----------------------------------");
        for(int i=0;i<this.s;i++){
            System.out.print(i+"|\t");
            for(int j=0;j<this.s;j++){
                System.out.print(this.board[i][j]+"\t");
            }System.out.println();
        }
        System.out.println("-----------------------------------");

    }
    int getBlackCount(){
        int count=0;
        for(int i=0;i<this.s;i++){
            for(int j=0;j<this.s;j++){
                if(this.board[i][j]==1){
                    count++;
                }
            }
        }
        return count;
    }
    int getWhiteCount(){
        int count=0;
        for(int i=0;i<this.s;i++){
            for(int j=0;j<this.s;j++){
                if(this.board[i][j]==2){
                    count++;
                }
            }
        }
        return count;
    }
    boolean isLegalMove(int x1,int y1,int x2,int y2,int type){
        int n=this.board[x1][y1];
        int t=x1;
        int t1=x1;
        int t2=x1;
        int t3=x1;
        //type 1=horizontal;type2=vertical;type3=LtoR(DtoU) diagonal;type4=UtoD diagonal
        if(x2<0 || x2>(this.s-1) || y2<0 || y2>(this.s-1)){
            return false;
        }
        if(type==1){
            if(y2>y1){
                for(int j=y1+1;j<y2;j++) {
                    if (this.board[x1][j] != 0 && this.board[x1][j] != n) {
                        return false;
                    }
                }
                if(this.board[x2][y2]==n){
                    return false;
                }else{
                    return true;
                }

            }else if(y2<y1){
                for(int j=y2+1;j<y1;j++) {
                    if (this.board[x1][j] != 0 && this.board[x1][j] != n) {
                        return false;
                    }
                }
                if(this.board[x2][y2]==n){
                    return false;
                }else return true;

            }
        }
        else if(type==2){
            if(x2>x1){
                for(int j=x1+1;j<x2;j++) {
                    if (this.board[j][y1] != 0 && this.board[j][y1] != n) {
                        return false;
                    }
                }
                if(this.board[x2][y2]==n){
                    return false;
                }else{
                    return true;
                }

            }else if(x2<x1){
                for(int j=x2+1;j<x1;j++) {
                    if (this.board[j][y1] != 0 && this.board[j][y1] != n) {
                        return false;
                    }
                }
                if(this.board[x2][y2]==n){
                    return false;
                }else return true;

            }
        }
        else if(type==3){
            if(y2>y1){
                for(int j=y1+1;j<y2;j++){
                    t--;
                    if(this.board[t][j]!=0 && this.board[t][j]!=n){
                        return false;
                    }
                }
                if(this.board[x2][y2]==n){
                    return false;
                }else return true;
            }
            else if(y2<y1){
                for(int j=y1-1;j>y2;j--){
                    t1++;
                    if(this.board[t1][j]!=0 && this.board[t1][j]!=n){
                        return false;
                    }
                }
                if(this.board[x2][y2]==n){
                    return false;
                }else return true;
            }
        }
        else if(type==4){
            if(y2>y1){
                for(int j=y1+1;j<y2;j++){
                    t2++;
                    if(this.board[t2][j]!=0 && this.board[t2][j]!=n){
                        return false;
                    }
                }
                if(this.board[x2][y2]==n){
                    return false;
                }else return true;
            }
            else if(y2<y1){
                for(int j=y1-1;j>y2;j--){
                    t3--;
                    if(this.board[t3][j]!=0 && this.board[t3][j]!=n){
                        return false;
                    }
                }
                if(this.board[x2][y2]==n){
                    return false;
                }else return true;
            }
        }

        return true;
    }
    ArrayList<Coordinate> generateMoves(int x1,int y1){
        int n=this.board[x1][y1];
        ArrayList<State>AL=new ArrayList<>();
        ArrayList<Coordinate>AL1=new ArrayList<>();
        int[][] temp=new int[this.s][this.s];
        for(int j=0;j<this.s;j++){
            for(int k=0;k<this.s;k++){
                temp[j][k]=this.board[j][k];
            }
        }
        int count=0;
        //type1:
        for(int i=0;i<this.s;i++){
            if(this.board[x1][i]!=0){
                count++;
            }
        }
        int y2=y1+count;
        int y3=y1-count;
        if(isLegalMove(x1,y1,x1,y2,1)){
            AL1.add(new Coordinate(x1,y2));
        }
        if(isLegalMove(x1,y1,x1,y3,1)){
            AL1.add(new Coordinate(x1,y3));
        }
        //type2:
        int c1=0;
        for(int i=0;i<this.s;i++){
            if(this.board[i][y1]!=0){
                c1++;
            }
        }
        int x2=x1+c1;
        int x3=x1-c1;
        if(isLegalMove(x1,y1,x2,y1,2)){
            AL1.add(new Coordinate(x2,y1));
        }
        if(isLegalMove(x1,y1,x3,y1,2)){
            AL1.add(new Coordinate(x3,y1));
        }
        //type3:
        int c2=1;
        int a=x1+1,b=y1-1;
        while(a<this.s && b>=0){
            if(this.board[a][b]!=0){
                c2++;
            }
            a++;
            b--;
        }
        int d=x1-1,e=y1+1;
        while(d>=0 && e<this.s){
            if(this.board[d][e]!=0){
                c2++;
            }
            d--;
            e++;
        }
        int x4=x1-c2;
        int y4=y1+c2;
        int x5=x1+c2;
        int y5=y1-c2;
        if(isLegalMove(x1,y1,x4,y4,3)){
            AL1.add(new Coordinate(x4,y4));
        }
        if(isLegalMove(x1,y1,x5,y5,3)){
            AL1.add(new Coordinate(x5,y5));
        }
        //type4:
        int c3=1;
        int f=x1-1,g=y1-1;
        while(f>=0 && g>=0){
            if(this.board[f][g]!=0){
                c3++;
            }
            f--;
            g--;
        }
        int h=x1+1,k=y1+1;
        while(h<this.s && k<this.s){
            if(this.board[h][k]!=0){
                c3++;
            }
            h++;
            k++;
        }
        int x6=x1-c3;
        int y6=y1-c3;
        int x7=x1+c3;
        int y7=y1+c3;
        if(isLegalMove(x1,y1,x6,y6,4)){
            AL1.add(new Coordinate(x6,y6));
        }
        if(isLegalMove(x1,y1,x7,y7,4)){
            AL1.add(new Coordinate(x7,y7));
        }
        return AL1;
    }
    void move(Coordinate c1,Coordinate c2){
        int x1=c1.x;
        int y1=c1.y;
        int x2=c2.x;
        int y2=c2.y;
        int n=this.board[x1][y1];
        //int t=this.board[x2][y2];
        this.board[x2][y2]=n;
        this.board[x1][y1]=0;
    }
    void reverseMove(Coordinate c1,Coordinate c2,int n,int t){
        int x1=c1.x;
        int y1=c1.y;
        int x2=c2.x;
        int y2=c2.y;
        this.board[x1][y1]=n;
        this.board[x2][y2]=t;
    }
    HashMap<Coordinate,ArrayList<Coordinate>> allPossibleMoves(int[][]temp,int player){
        HashMap<Coordinate,ArrayList<Coordinate>>hmap=new HashMap<>();
        for(int i=0;i<this.s;i++){
            for(int j=0;j<this.s;j++){
                if(temp[i][j]==player){
                    hmap.put(new Coordinate(i,j),generateMoves(i,j));
                }
            }
        }
        return hmap;
    }
    int isEndgame(int[][] temp,int player){
        boolean flag=false;
        boolean flag2=false;
        int BlackCount=getBlackCount();
        int WhiteCount=getWhiteCount();
        ArrayList<Integer>L=bfs(temp,1);
        if(L.size()==BlackCount){
            flag=true;
        }
        ArrayList<Integer>L2=bfs(temp,2);
        if(L2.size()==WhiteCount){
            flag2=true;
        }
        if(flag==true&&flag2==true){
            return player;
        }
        else if(flag==true){
            return 1;
        }else if(flag2==true){
            return 2;
        }
        else return 0;
    }
    ArrayList<Integer>bfs(int[][] temp,int n){
        boolean[][] visited=new boolean[this.s][this.s];
        for(int i=0;i<this.s;i++){
            for(int j=0;j<this.s;j++){
                visited[i][j]=false;
            }
        }
        Queue<Coordinate> queue=new LinkedList<>();
        ArrayList<Integer>L=new ArrayList<>();
        boolean f=false;
        for(int i=0;i<this.s;i++){
            for(int j=0;j<this.s;j++){
                if(temp[i][j]==n){
                    Coordinate c2=new Coordinate(i,j);
                    queue.add(c2);
                    visited[i][j]=true;
                    f=true;
                    break;
                }
            }if(f==true){ break;}
        }

        while(queue.isEmpty()==false) {
            Coordinate c1 = queue.poll();
            int row=c1.x;
            int col=c1.y;
            L.add(temp[row][col]);
            Coordinate cd1=getNeighbours(row,col+1,visited,temp,n);
            if(cd1!=null){
                queue.add(cd1);
            }
            Coordinate cd2=getNeighbours(row,col-1,visited,temp,n);
            if(cd2!=null){
                queue.add(cd2);
            }
            Coordinate cd3=getNeighbours(row+1,col,visited,temp,n);
            if(cd3!=null){
                queue.add(cd3);
            }
            Coordinate cd4=getNeighbours(row-1,col,visited,temp,n);
            if(cd4!=null){
                queue.add(cd4);
            }
            Coordinate cd5=getNeighbours(row+1,col+1,visited,temp,n);
            if(cd5!=null){
                queue.add(cd5);
            }
            Coordinate cd6=getNeighbours(row-1,col+1,visited,temp,n);
            if(cd6!=null){
                queue.add(cd6);
            }
            Coordinate cd7=getNeighbours(row+1,col-1,visited,temp,n);
            if(cd7!=null){
                queue.add(cd7);
            }
            Coordinate cd8=getNeighbours(row-1,col-1,visited,temp,n);
            if(cd8!=null){
                queue.add(cd8);
            }
        }
        return L;
    }
    boolean indexbound(int row,int col){
        if(row<0 || row>(this.s-1) || col<0 || col>(this.s-1)){// || visited[row][col]==true){
            return false;
        }else return true;
    }
    Coordinate getNeighbours(int row,int col,boolean[][] visited,int[][] temp,int n){
        if(indexbound(row,col)) {
            if (temp[row][col] == n) {
                if (visited[row][col] == false) {
                    visited[row][col]=true;
                    return (new Coordinate(row,col));
                }
            }
        }
        return null;
    }

    ArrayList<Coordinate> getPositions(int n){
        ArrayList<Coordinate>AL=new ArrayList<>();
        for(int i=0;i<this.s;i++){
            for(int j=0;j<this.s;j++){
                if(this.board[i][j]==n){
                    AL.add(new Coordinate(i,j));
                }
            }
        }
        return AL;
    }
    double connectedCount(Coordinate c,int n){
        double count=0;
        int x=c.x;
        int y=c.y;
        count+=neighbourCount(x,y+1,n);
        count+=neighbourCount(x,y-1,n);
        count+=neighbourCount(x+1,y,n);
        count+=neighbourCount(x-1,y,n);
        count+=neighbourCount(x+1,y+1,n);
        count+=neighbourCount(x+1,y-1,n);
        count+=neighbourCount(x-1,y+1,n);
        count+=neighbourCount(x-1,y-1,n);
        return count;
    }
    double neighbourCount(int x,int y,int n){
        double c=0;
        if(indexbound(x,y)){
            if(this.board[x][y]==n){
                c++;
            }
        }
        return c;
    }
    Coordinate centreOfMass(int n){
        ArrayList<Coordinate>L=getPositions(n);
        int size=L.size();
        double sumX=0;
        double sumY=0;
        for(int i=0;i<size;i++){
            sumX+=L.get(i).x;
            sumY+=L.get(i).y;
        }
        double avgX=Math.abs(sumX/size);
        double avgY=Math.abs(sumY/size);
        return (new Coordinate((int)avgX,(int)avgY));
    }
    double avgDistance(int n){
        ArrayList<Coordinate>L=getPositions(n);
        Coordinate c1=centreOfMass(n);
        double distance=0;
        for(int i=0;i<L.size();i++){
            double d=Math.pow((c1.x-L.get(i).x),2)+Math.pow((c1.y-L.get(i).y),2);
            distance+=Math.sqrt(d);
        }
        double AvgDistance=(distance/L.size());
        return AvgDistance;
    }
    double quadCount(int n){
        double score=0;
        int count=0;
        for(int i=0;i<this.s-1;i++){
            for(int j=0;j<this.s-1;j++){
                if(this.board[i][j]==n) count++;
                if(this.board[i][j+1]==n) count++;
                if(this.board[i+1][j]==n) count++;
                if(this.board[i+1][j+1]==n) count++;
                if(count>=3){
                    score++;
                }count=0;
            }
        }
        return score;
    }
    int[][] getPieceSquareTable(int size){
        if(size==8){
             int[][] pieceSquareTable =
            {
                    {-80, -25, -20, -20, -20, -20, -25, -80},
                    {-25,  10,  10,  10,  10,  10,  10,  -25},
                    {-20,  10,  25,  25,  25,  25,  10,  -20},
                    {-20,  10,  25,  50,  50,  25,  10,  -20},
                    {-20,  10,  25,  50,  50,  25,  10,  -20},
                    {-20,  10,  25,  25,  25,  25,  10,  -20},
                    {-25,  10,  10,  10,  10,  10,  10,  -25},
                    {-80, -25, -20, -20, -20, -20, -25, -80}

            };
             return pieceSquareTable;
        }
        else {
            int[][] pieceSquareTable =
                    {
                            {-80, -25, -20, -20, -25, -80},
                            {-25,  10,  10,  10,  10,  -25},
                            {-20,  10,  25,  25,  10,  -20},
                            {-20,  10,  25,  25,  10,  -20},
                            {-25,  10,  10,  10,  10,  -25},
                            {-80, -25, -20,  -20, -25, -80}

                    };
            return pieceSquareTable;
        }

    }
    double numberOfMoves(int n){
        double c=0;
        HashMap<Coordinate, ArrayList<Coordinate>> hmap =allPossibleMoves(this.board,n);
        for (Map.Entry<Coordinate, ArrayList<Coordinate>> e : hmap.entrySet()){
            c+=e.getValue().size();
        }
        return c;
    }
}
