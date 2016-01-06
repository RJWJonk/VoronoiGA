
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s080440
 */
public class GameBoard {

    private ArrayList<Store> stores = new ArrayList<>();

    private ArrayList<Edge> triangulation = new ArrayList();

    private Set<Triangle> triangulation2 = new HashSet();

    public static void main(String[] args) {
        GameBoard gb = new GameBoard();
    }

    public GameBoard() {
//        Random r = new Random();

//        for (int i = 0; i < 6; i++) {
//            stores.add(new Store(r.nextInt(10), r.nextInt(10), i % 2));
//        }
//        calculateTriangulation();
    }

    private void calculateTriangulation() {
        if (stores.size() < 3) {
            return;
        }

        triangulation.clear();
        triangulation2.clear();

        stores.sort(new Comparator<Store>() {

            @Override
            public int compare(Store s1, Store s2) {
                return (int) (s1.x - s2.x);
            }
        });

        ArrayList<Store> todo = (ArrayList<Store>) stores.clone();

        //Initialize first 3 points
        int progress = 0;
        for (int i = 2; i < todo.size(); i++) {
            progress = i;
            Triangle t = new Triangle(todo.get(i), todo.get(i - 1), todo.get(i - 2));
            if (t.calculateArea() > 0.01) { //machine precision things..
                System.out.println("ADD");
                triangulation2.add(t);
                for (int j = i; j > 0; j--) {
                    t = new Triangle(todo.get(i), todo.get(j), todo.get(j - 1));
                    triangulation2.add(t);
                }
                break;
            }
        }

        for (int i = progress + 1; i < todo.size(); i++) {
            Store si = todo.get(i);
            for (int j= i-1; j>=1; j--){
            Store sj = todo.get(j);
            for (int k = j-1; k >= 0; k--) {
                Store sk = todo.get(k);
                Triangle t = new Triangle(si, sj, sk);
                boolean overlaps = false;
                for (Triangle tt : triangulation2) {
                    overlaps = overlaps || overlaps(t, tt);
                }
                if (!overlaps && t.calculateArea() > 0.001) {
                    triangulation2.add(t);
                }
            }
        }
        }

//        Triangle t = new Triangle(s1, s2, s3);
//        System.out.println(t.calculateArea());
//        if (t.calculateArea() > 0.01) { //machine precision things..
//            triangulation2.add(t);
//        } else {
//            System.out.println("NOT A TRIANGLE");
//        }
//        for (int i = 3; i < stores.size(); i++) {
//            Store si = todo.get(i);
//            for (int j = i - 1; j >= 0; j--) {
//                boolean intersect = false;
//
//                Store sj = todo.get(j);
//                Edge eij = null;
//
//                for (int k = 0; k < i; k++) {
//
//                    for (int l = k + 1; l <= i; l++) {
//
//                        Store sk = todo.get(k);
//                        Store sl = todo.get(l);
//
//                        Line2D line1 = new Line2D.Float(si.x, si.y, sj.x, sj.y);
//                        Line2D line2 = new Line2D.Float(sk.x, sk.y, sl.x, sl.y);
//                        if (sj == sk || sj == sl) {
//                            //System.out.println("ispoint");
//                        } else {
//                            if (findEdgeBetweenStores(sk, sl) != null) {
//                                intersect = (intersects(line1, line2) || overlaps(line1, line2)) || intersect;
//                            }
//                        }
//                    }
//                }
//                if (false == intersect) {
//                    triangulation.add(eij);
//                    //we know the point it connected to as sj
//                    ArrayList<Edge> edges = findEdgesOfStore(sj);
//                    for (Edge ejp : edges) {
//                        Store sp = ejp.s1 != sj ? s1 : s2;
//                        Edge eip = findEdgeBetweenStores(si, sp);
//                        if (eip != null) {
//                            //ejp = new Edge(sj,sp);
//                            //triangulation.add(ejp);
//                            //set the next, prev of si, sj and sp
//                            if (left_turn(si, sj, sp)) {
//                                eij.setNext(ejp);
//                                eij.setPrevious(eip);
//                                ejp.setNext(eip);
//                                ejp.setPrevious(eij);
//                                eip.setNext(eij);
//                                eip.setPrevious(ejp);
//                            } else {
//                                eij.setNext(eip);
//                                eij.setPrevious(ejp);
//                                ejp.setNext(eij);
//                                ejp.setPrevious(eip);
//                                eip.setNext(ejp);
//                                eip.setPrevious(eij);
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
        calculateTwins();
        flipEdges();

        //calculate triangulation based on incremental
        //print results
//        System.out.println("===Stores===");
//        for (Store s : stores) {
//            System.out.println(s.toString());
//        }
//
//        System.out.println("===Edges===");
//        for (Edge e : triangulation) {
//            System.out.println(e.s1.toString() + " -> " + e.s2.toString());
//        }
    }

    private void calculateTwins() {
        for (int i = 0; i < triangulation.size(); i++) {
            for (int j = i + 1; j < triangulation.size(); j++) {
                Edge ei = triangulation.get(i);
                Edge ej = triangulation.get(j);
//                System.out.println("====***=====");
//                System.out.println(ei.s1);
//                System.out.println(ei.s2);
//                System.out.println(ej.s1);
//                System.out.println(ej.s2);
                if ((ei.s1 == ej.s1 && ei.s2 == ej.s2) || (ei.s2 == ej.s1 && ei.s1 == ej.s2)) {
                    System.out.println("settwin!");
                    ei.setTwin(ej);
                    ej.setTwin(ei);

                }
            }
        }
    }

    private boolean left_turn(Store a, Store b, Store c) {

        double temp = (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));

        return temp < 0;
    }

    public ArrayList<Edge> findEdgesOfStore(Store s) {
        ArrayList<Edge> result = new ArrayList<>();

        for (Edge e : triangulation) {
            if (e.s1 == s || e.s2 == s) {
                result.add(e);
            }
        }
        return result;
    }

    private Edge findEdgeBetweenStores(Store s1, Store s2) {
        for (Edge e : triangulation) {
            if ((e.s1 == s1 && e.s2 == s2) || (e.s2 == s1 && e.s1 == s2)) {
                return e;
            }
        }
        return null;
    }

    public boolean isLocalDelaunay(Edge e) {

        if (e.twin == null) {
            return true;
        }

        Store p = e.s1;
        Store q = e.s2;
        Store r = e.next.s1 == e.s1 || e.next.s1 == e.s2 ? e.next.s2 : e.next.s1;
        Store s = e.twin.next.s1 == e.s1 || e.twin.next.s1 == e.next.s2 ? e.twin.next.s2 : e.next.s1;

        return (signDet3(p, q, r) * signDet4(p, q, r, s) > 0);

    }

    public int signDet3(Store p, Store q, Store r) {
        if (left_turn(p, q, r)) {
            return (1);
        } else {
            return (-1);
        }
    }

    public int signDet4(Store p, Store q, Store r, Store s) {
        float pz = p.x * p.x + p.y * p.y;
        float qz = q.x * q.x + q.y * q.y;
        float rz = r.x * r.x + r.y * r.y;
        float sz = s.x * s.x + s.y * s.y;

        float x = det(1, p.x, 1, q.x) * det(r.y, rz, s.y, sz)
                - det(1, p.y, 1, q.y) * det(r.x, rz, s.x, sz)
                + det(1, pz, 1, qz) * det(r.x, r.y, s.x, s.y)
                + det(p.x, p.y, q.x, q.y) * det(1, rz, 1, sz)
                - det(p.x, pz, q.x, qz) * det(1, r.y, 1, s.y)
                + det(p.y, pz, q.y, qz) * det(1, r.x, 1, s.x);
        System.out.println(x);
        if (x > 0) {
            return (1);
        } else {
            return (-1);
        }
    }

    private float det(float a, float b, float c, float d) {
        return (a * d - b * c);
    }

    private void flipEdges() {
        calculateTwins();
        boolean changed = true;
        while (changed) {
            //System.out.println("hello");
            changed = false;
            for (int i = 0; i < triangulation.size(); i++) {
                Edge ei = triangulation.get(i);//need to be fix
                System.out.println("local? " + isLocalDelaunay(ei));
                if (!isLocalDelaunay(ei)) {
                    changed = true;
                    System.out.println("actual flipping");
                    edgeFlip(ei);

                }
            }
        }

        //todo: modifies the triangulation to the delaunay triangulation
    }

    private void edgeFlip(Edge e) {
        //todo: the mothed to do edge flip
        Store p = e.s1;
        Store q = e.s2;
        Store r = e.next.s1 == e.s1 ? e.next.s2 : e.next.s1;
        Store s = e.twin.next.s1 == e.s1 ? e.twin.next.s2 : e.next.s1;

        triangulation.remove(e);
        triangulation.remove(e.twin);
        Edge en = new Edge(r, s);
        en.twin = new Edge(s, r);
        triangulation.add(en);
        triangulation.add(en.twin);

        if (en != null) {
            Edge esp = new Edge(s, p);
            Edge epr = new Edge(p, r);
            Edge erq = new Edge(r, q);
            Edge eqs = new Edge(q, s);

            //set the next, prev of si, sj and sp
            if (left_turn(p, r, s)) {
                en.setNext(esp);
                en.setPrevious(epr);
                esp.setNext(epr);
                esp.setPrevious(en);
                epr.setNext(en);
                epr.setPrevious(esp);
            } else {
                en.setNext(epr);
                en.setPrevious(esp);
                esp.setNext(en);
                esp.setPrevious(epr);
                epr.setNext(esp);
                epr.setPrevious(esp);
            }
            if (left_turn(q, r, s)) {
                en.twin.setNext(erq);
                en.twin.setPrevious(eqs);
                erq.setNext(eqs);
                erq.setPrevious(en.twin);
                eqs.setNext(en.twin);
                eqs.setPrevious(erq);
            } else {
                en.twin.setNext(eqs);
                en.twin.setPrevious(erq);
                erq.setNext(en.twin);
                erq.setPrevious(eqs);
                eqs.setNext(erq);
                eqs.setPrevious(en.twin);
            }
        }
    }

    public Edge getEdge(Store s1, Store s2) {
        for (Edge e : triangulation) {
            if ((e.s1 == s1 && e.s2 == s2) || (e.s1 == s2 && e.s2 == s1)) {
                return e;
            }
        }
        return null;
    }

    public void addStore(int x, int y, int o) {
        Store s = new Store(x, y, o);
        addStore(s);
        calculateTriangulation();
        flipEdges();
    }

    //add a store to the data set and recalculate the triangulation
    public void addStore(Store s) {
        stores.add(s);
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public Set<Triangle> geTriangulation() {
        return triangulation2;
    }

    public Store getStoreAt(int x, int y) {
        for (Store s : stores) {
            if (s.x == x && s.y == y) {
                return s;
            }
        }
        return null;
    }

    private boolean overlaps(Line2D line1, Line2D line2) {
        double a1 = (line1.getY2() - line1.getY1()) / (line1.getX2() - line1.getX1());
        double a2 = (line2.getY2() - line2.getY1()) / (line2.getX2() - line2.getX1());

        if ((a1 == Double.NEGATIVE_INFINITY || a1 == Double.POSITIVE_INFINITY)
                && (a2 == Double.NEGATIVE_INFINITY || a2 == Double.POSITIVE_INFINITY)) {
            double bxl1 = line1.getY1() <= line1.getY2() ? line1.getY1() : line1.getY2();
            double bxr1 = line1.getY1() > line1.getY2() ? line1.getY1() : line1.getY2();
            double bxl2 = line2.getY1() <= line2.getY2() ? line2.getY1() : line2.getY2();
            double bxr2 = line2.getY1() > line2.getY2() ? line2.getY1() : line2.getY2();

            if (bxl2 < bxr1 && bxr2 > bxl1) {
                return true;
            }
        }

        double b1 = line1.getY1() - a1 * line1.getX1();
        double b2 = line2.getY1() - a2 * line2.getX1();

        if (a1 == a2 && b1 == b2) {
            double bxl1 = line1.getX1() <= line1.getX2() ? line1.getX1() : line1.getX2();
            double bxr1 = line1.getX1() > line1.getX2() ? line1.getX1() : line1.getX2();
            double bxl2 = line2.getX1() <= line2.getX2() ? line2.getX1() : line2.getX2();
            double bxr2 = line2.getX1() > line2.getX2() ? line2.getX1() : line2.getX2();

            if (bxl2 < bxr1 && bxr2 > bxl1) {
                return true;
            }
        }

        return false;
    }

    public float crossProduct(Store s1, Store s2, Store s3, Store s4) {
        float x1 = s2.x - s1.x;
        float y1 = s2.y - s1.y;
        float x2 = s4.x - s3.x;
        float y2 = s4.y - s3.y;

        return x1 * y2 - y1 * x2;
    }

    public boolean sameSide(Store p1, Store p2, Store a, Store b) {
        float cp1 = crossProduct(b, a, p1, a);
        float cp2 = crossProduct(b, a, p2, a);
        return cp1 * cp2 > 0;
    }

    public boolean pointInTriangle(Store p1, Triangle t) {
        if (sameSide(p1, t.s2, t.s0, t.s1) && sameSide(p1, t.s1, t.s2, t.s0) && sameSide(p1, t.s0, t.s1, t.s2)) {
            return true;
        } else {
            return false;
        }
    }

//    private boolean intersects(Line2D line1, Line2D line2) {
//        if (line1.intersectsLine(line2)) {
//            System.out.println("intersect found!");
//            System.out.println("L1:" + line1.getP1().getX() + "->" + line1.getP2().getX());
//            System.out.println("L2:" + line2.getP1().getX() + "->" + line2.getP2().getX());
//            if (line1.getP1().getX() == line2.getP2().getX() || line1.getP2().getX() == line2.getP2().getX()
//                    || line1.getP1().getX() == line2.getP1().getX() || line1.getP2().getX() == line2.getP2().getX()) {
//                System.out.println("overruled");
//                return true;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }
    private boolean intersects(Store s1, Store s2, Line2D.Float l2) {
        System.out.println("Intersection:");
        System.out.println("(" + s1.x + "," + s1.y + ")->(" + s2.x + "," + s2.y + ")");
        System.out.println("(" + l2.x1 + "," + l2.y1 + ")->(" + l2.x2 + "," + l2.y2 + ")");

        System.out.println(above(s1, l2));
        System.out.println(above(s2, l2));
        return above(s1, l2) * above(s2, l2) == -1 && l2.intersectsLine(new Line2D.Float(s1.x, s1.y, s2.x, s2.y));

    }

    private int above(Store s, Line2D.Float l) {
        double a = (l.getY2() - l.getY1()) / (l.getX2() - l.getX1());

        if (l.getX2() == l.getX1()) {
            return s.x < l.getX1() ? 1 : s.x > l.getX1() ? -1 : 0;
        }

        double b = l.getY1() - a * l.getX1();
        if (s.y > a * s.x + b + 0.5) {
            return 1;
        } else if (s.y < a * s.x + b - 0.5) {
            return -1;
        }
        return 0;
    }

    private boolean overlaps(Triangle t1, Triangle t2) {
        Line2D.Float line0 = new Line2D.Float(t1.s0.x, t1.s0.y, t1.s1.x, t1.s1.y);
        Line2D.Float line1 = new Line2D.Float(t1.s1.x, t1.s1.y, t1.s2.x, t1.s2.y);
        Line2D.Float line2 = new Line2D.Float(t1.s2.x, t1.s2.y, t1.s0.x, t1.s0.y);

//        if ((above(t2.s0, line0) * above(t2.s1, line0) == -1) || (above(t2.s1, line0) * above(t2.s2, line0) == -1) || (above(t2.s2, line0) * above(t2.s0, line0) == -1)
//                || (above(t2.s0, line1) * above(t2.s1, line1) == -1) || (above(t2.s1, line1) * above(t2.s2, line1) != 0) || (above(t2.s2, line1) * above(t2.s0, line1) == -1)
//                || (above(t2.s0, line2) * above(t2.s1, line2) == -1) || (above(t2.s1, line2) * above(t2.s2, line2) == -1) || (above(t2.s2, line2) * above(t2.s0, line2) == -1)) {
//            return true;
//        }
        if (intersects(t2.s0, t2.s1, line0) || intersects(t2.s1, t2.s2, line0) || intersects(t2.s2, t2.s0, line0)
                || intersects(t2.s0, t2.s1, line1) || intersects(t2.s1, t2.s2, line1) || intersects(t2.s2, t2.s0, line1)
                || intersects(t2.s0, t2.s1, line2) || intersects(t2.s1, t2.s2, line2) || intersects(t2.s2, t2.s0, line2)) {
            return true;
        }

        return false;
//        if (pointInTriangle(t1.s0, t2) || pointInTriangle(t1.s1, t2) || pointInTriangle(t1.s2, t2) ) {
//            return true;
//        }
//        return false;
    }

    //edges between stores
    public class Edge {

        public Store s1;
        public Store s2;

        public Edge next;
        public Edge prev;
        public Edge twin;

        private Edge(Store s1, Store s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        private void setNext(Edge e) {
            next = e;
        }

        private void setPrevious(Edge e) {
            prev = e;
        }

        private void setTwin(Edge e) {
            twin = e;
        }
    }

    public class Triangle {

        public Store s0;
        public Store s1;
        public Store s2;

        public Triangle t0;
        public Triangle t1;
        public Triangle t2;

        public Triangle(Store s0, Store s1, Store s2) {
            this.s0 = s0;
            this.s1 = s1;
            this.s2 = s2;
        }

        public float calculateArea() {
            double base = Math.sqrt((s1.x - s0.x) * (s1.x - s0.x) + (s1.y - s0.y) * (s1.y - s0.y));
            double base2 = Math.sqrt((s2.x - s0.x) * (s2.x - s0.x) + (s2.y - s0.y) * (s2.y - s0.y));
            double angle = Math.acos(((s1.x - s0.x) * (s2.x - s0.x) + (s1.y - s0.y) * (s2.y - s0.y)) / (base * base2));
            double height = Math.sqrt((s2.x - s0.x) * (s2.x - s0.x) + (s2.y - s0.y) * (s2.y - s0.y)) * Math.sin(angle);
            return (float) Math.abs(base * height / 2);
        }

        public Polygon toPolygon() {
            Polygon result = new Polygon();
            result.addPoint((int) s0.x, (int) s0.y);
            result.addPoint((int) s1.x, (int) s1.y);
            result.addPoint((int) s2.x, (int) s2.y);
            return result;
        }
    }

}
