
import java.awt.Polygon;
import java.util.ArrayList;

public class Triangle {

        public Store s0;
        public Store s1;
        public Store s2;

        public Triangle n0;
        public Triangle n1;
        public Triangle n2;

        public ArrayList<Triangle> children;
        public ArrayList<Triangle> neighbours;

        public Triangle(Store s0, Store s1, Store s2) {
            this.s0 = s0;
            this.s1 = s1;
            this.s2 = s2;
            children = new ArrayList<>();
            neighbours = new ArrayList<>();
        }

        public double calculateArea() {
            double base = Math.sqrt((s1.x - s0.x) * (s1.x - s0.x) + (s1.y - s0.y) * (s1.y - s0.y));
            double base2 = Math.sqrt((s2.x - s0.x) * (s2.x - s0.x) + (s2.y - s0.y) * (s2.y - s0.y));
            double angle = Math.acos(((s1.x - s0.x) * (s2.x - s0.x) + (s1.y - s0.y) * (s2.y - s0.y)) / (base * base2));
            double height = Math.sqrt((s2.x - s0.x) * (s2.x - s0.x) + (s2.y - s0.y) * (s2.y - s0.y)) * Math.sin(angle);
            return Math.abs(base * height / 2);
        }

        public Polygon toPolygon() {
            Polygon result = new Polygon();
            result.addPoint((int) s0.x, (int) s0.y);
            result.addPoint((int) s1.x, (int) s1.y);
            result.addPoint((int) s2.x, (int) s2.y);
            return result;
        }

        public boolean isLeaf() {
            return children.isEmpty();
        }
    }