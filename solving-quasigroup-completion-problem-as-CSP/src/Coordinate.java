import java.util.ArrayList;

public class Coordinate {
    int x;
    int y;
    ArrayList<Coordinate>Adjacent;
    int value;
    ArrayList<Integer>Domain;
    int forwardDegree;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (value != that.value) return false;
        if (!Adjacent.equals(that.Adjacent)) return false;
        return Domain.equals(that.Domain);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + value;
        return result;
    }

    public Coordinate(int x, int y, int value, ArrayList<Integer>Domain) {
        this.x = x;
        this.y = y;
        this.value=value;
        this.Adjacent=new ArrayList<>();
        this.Domain=Domain;
        forwardDegree=0;
    }
    public int getDegree(){
        return this.Adjacent.size();
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                ", value=" + value +
                '}';
    }
}
