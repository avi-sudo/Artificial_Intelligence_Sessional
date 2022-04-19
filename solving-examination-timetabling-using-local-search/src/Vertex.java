import java.util.ArrayList;
import java.util.Comparator;

public class Vertex implements Comparable<Vertex>{
    int name;
    int colour;
    boolean isVisited=false;
    ArrayList<Vertex>Adjacent=new ArrayList<>();

    public Vertex(int name)
    {
        this.name = name;
    }
    public int getDegree()
    {
        return this.Adjacent.size();
    }

    public static Comparator<Vertex> degreeComparator = new Comparator<Vertex>() {

        public int compare(Vertex s1, Vertex s2) {
            int vertex1 = s1.getDegree();
            int vertex2 = s2.getDegree();


            return vertex2-vertex1;

            //descending order

        }};
    public static Comparator<Vertex> colourComparator = new Comparator<Vertex>() {

        public int compare(Vertex s1, Vertex s2) {
            int vertex1 = s1.colour;
            int vertex2 = s2.colour;


            return vertex1-vertex2;

            //ascending order

        }};
    @Override
    public int compareTo(Vertex comparestu) {
        int compareage=(comparestu).getDegree();
        /* For Ascending order*/
        //return this.studentage-compareage;

        /* For Descending order do like this */
        return compareage-this.getDegree();
    }

    @Override
    public String toString() {
        return ""+this.name;
    }
}
