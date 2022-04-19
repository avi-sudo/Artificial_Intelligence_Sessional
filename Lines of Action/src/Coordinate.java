import java.util.Objects;

public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    void print(){
        System.out.print(this.x+","+this.y);
        System.out.println();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x,this.y);
    }

    @Override
    public boolean equals(Object obj) {
        Coordinate c=(Coordinate)obj;
        if(this.x!=c.x) return false;
        if(this.y!=c.y) return false;
        return true;
    }
}
