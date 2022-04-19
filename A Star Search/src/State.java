import java.util.ArrayList;
import java.util.Arrays;

public class State {
    //int size;
    int[][] startState;//=new int[4][4];
    int[][] goalState;//=new int[4][4];
    State parent;
    int g;

    public State(int[][] startStates, int[][] goalState,int a) {
        //this.startState = startStates;
        this.startState = new int[4][4] ;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                this.startState[i][j]=startStates[i][j];
            }
        }

        this.goalState = goalState;
        for(int u=0;u<4;u++){
            for(int z=0;z<4;z++){
                //System.out.print(this.startState[u][z]+" ");
            }
        }
        g=a;
        parent=null;
    }

    public State getParent() {
        return parent;
    }

    public int getG() {
        return g;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public void setG(int g) {
        this.g = g;
    }

    int distance(int x,int y,int a){
        int i,j,s=0;
        for ( i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                    if (this.startState[i][j] == a) {
                        s = (Math.abs(x - i) + Math.abs(y - j));
                    }
            }
        }
        return s;
    }
    int displacement(){
        int c=1;
        int i,j,h=0;
        for ( i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                //System.out.print(this.startState[i][j]+"T");
                if (this.startState[i][j] != 0) {
                    if (this.startState[i][j] != this.goalState[i][j]) {
                        //System.out.println(c);
                        h++;
                    }
                    c++;
                }
            }
        }
        //int k=h;
        //System.out.println(k+"AVI");
        return h;
    }
    int manhattan(){
        int i,j,h=0;
        for ( i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if(this.goalState[i][j]!=0) {
                    if (this.startState[i][j] != this.goalState[i][j]) {
                        h = h + distance(i, j, this.goalState[i][j]);
                    }
                }

            }
        }//System.out.println(h+"NAV");
        return h;
    }
    void print(){
        ArrayList<Integer>AL3=new ArrayList<>();
        int i,j=0;
        for ( i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (this.startState[i][j] == 0) {
                    System.out.print("0 ");
                } else
                    System.out.print(this.startState[i][j]+" ");
            }
            System.out.println();
        }
        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                AL3.add(this.startState[i][j]);
            }
        }

        for(j=0;j<AL3.size();j++){
            //System.out.print(AL3.get(j)+" ");
        }
        System.out.println();

    }

    ArrayList<State> generate(){
        ArrayList<State> AL=new ArrayList<>();
        int[][] temp=new int[4][4];
        int i,j=0;
        for ( i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                temp[i][j] = this.startState[i][j];
            }
        }

        for ( i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {

                if (temp[i][j] == 0){

                    if(i+1<4){
                        int t=temp[i][j];
                        temp[i][j]=temp[i+1][j];
                        temp[i+1][j]=t;
                        AL.add(new State(temp,goalState,g));
                        /*for (int ii = 0; ii < 4; ii++) {
                            for (int jj = 0; jj < 4; jj++) {
                                System.out.print(temp[ii][jj] + "A");

                            }
                        }System.out.println();*/
                        t=temp[i][j];
                        temp[i][j]=temp[i+1][j];
                        temp[i+1][j]=t;
                    }
                    if(i-1>=0){
                        int t=temp[i][j];
                        temp[i][j]=temp[i-1][j];
                        temp[i-1][j]=t;
                        AL.add(new State(temp,goalState,g));
                        t=temp[i][j];
                        temp[i][j]=temp[i-1][j];
                        temp[i-1][j]=t;
                    }
                    if(j+1<4){
                        int t=temp[i][j];
                        temp[i][j]=temp[i][j+1];
                        temp[i][j+1]=t;
                        AL.add(new State(temp,goalState,g));
                        t=temp[i][j];
                        temp[i][j]=temp[i][j+1];
                        temp[i][j+1]=t;
                    }
                    if(j-1>=0){
                        int t=temp[i][j];
                        temp[i][j]=temp[i][j-1];
                        temp[i][j-1]=t;
                        AL.add(new State(temp,goalState,g));
                        t=temp[i][j];
                        temp[i][j-1]=temp[i][j-1];
                        temp[i][j-1]=t;
                    }
                }
            }
        }
       // System.out.println(AL.size()+"ZAW");
        return AL;
    }
    boolean IsSolvable(){
        ArrayList<Integer>AL2=new ArrayList<>();
        int i,j,k,invCount=0;
        int zeroRowIdx=-1;

        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                //AL2.add(startState[i][j]);
                if(startState[i][j]!=0){
                    AL2.add(startState[i][j]);
                }
                else zeroRowIdx=i;
            }
        }
        for(k=0;k<AL2.size()-1;k++){
            for(int b=k+1;b<AL2.size();b++){
                if(AL2.get(k)>AL2.get(b)){
                    invCount++;
                }
            }

        }
        if((zeroRowIdx%2==0 && invCount%2!=0) ||(zeroRowIdx%2!=0 && invCount%2==0)){
            return true;
        }
        else
            return false;
    }
    boolean IsSolvable2(){
        ArrayList<Integer>AL2=new ArrayList<>();
        int i,j,k,invCount=0;
        int zeroRowIdx=-1;

        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                //AL2.add(startState[i][j]);
                if(goalState[i][j]!=0){
                    AL2.add(goalState[i][j]);
                }
                else zeroRowIdx=i;
            }
        }
        for(k=0;k<AL2.size()-1;k++){
            for(int b=k+1;b<AL2.size();b++){
                if(AL2.get(k)>AL2.get(b)){
                    invCount++;
                }
            }

        }
        if((zeroRowIdx%2==0 && invCount%2!=0) ||(zeroRowIdx%2!=0 && invCount%2==0)){
            return true;
        }
        else
            return false;
    }
    boolean IsSuccess() {
        int i, j = 0;
        boolean flag = true;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (this.startState[i][j] != this.goalState[i][j]) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    @Override
    public int hashCode() {
        //return Arrays.hashCode(this.startState);
        int v=0;
        String st="";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //v = (int) (v + this.startState[i][j])%10000007;
                if(this.startState[i][j]<10){
                    st=st+0+this.startState[i][j];
                }else {
                    st = st+this.startState[i][j];
                }
            }
        }
        v=st.hashCode();
        //System.out.println(st);
        return  v;
    }

    @Override
    public boolean equals(Object o){
        int[][] board1 = this.startState ;
        State state = (State) o ;
        int[][] board2 = state.startState ;

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(board1[i][j]!=board2[i][j]){
                    return false ;
                }
            }
        }
        return true;
    }

}

