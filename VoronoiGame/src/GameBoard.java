
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
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

        stores.sort(new Comparator<Store>() {

            @Override
            public int compare(Store s1, Store s2) {
                return (int) (s1.x - s2.x);
            }
        });

        ArrayList<Store> todo = (ArrayList<Store>) stores.clone();

        //Initialize first 3 points
        Store s1 = todo.get(0);
        Store s2 = todo.get(1);
        Store s3 = todo.get(2);

        Edge e12 = new Edge(s1, s2);
        triangulation.add(e12);
        Edge e23 = new Edge(s2, s3);
        triangulation.add(e23);
        Edge e31 = new Edge(s3, s1);
        triangulation.add(e31);

        if (left_turn(s1, s2, s3)) {
            e12.setNext(e23);
            e12.setPrevious(e31);
            e23.setNext(e31);
            e23.setPrevious(e12);
            e31.setNext(e12);
            e31.setPrevious(e23);
        } else {
            e12.setNext(e31);
            e12.setPrevious(e23);
            e23.setNext(e12);
            e23.setPrevious(e31);
            e31.setNext(e23);
            e31.setPrevious(e12);
        }

        for (int i = 4; i <= stores.size(); i++) {
            Store si = todo.get(i - 1);
            for (int j = i - 1; j > 0; j--) {
                boolean intersect = false;

                Store sj = todo.get(j - 1);
                Edge eij = new Edge(si, sj);

                for (int k = 1; k < i - 1; k++) {

                    for (int l = k + 1; l <= i - 1; l++) {

                        Store sk = todo.get(k - 1);
                        Store sl = todo.get(l - 1);

                        Line2D line1 = new Line2D.Float(si.x, si.y, sj.x, sj.y);
                        Line2D line2 = new Line2D.Float(sk.x, sk.y, sl.x, sl.y);
                        if ( sj == sk || sj == sl ) {
                            //System.out.println("ispoint");
                        } else {
                            intersect = line1.intersectsLine(line2) || intersect;
                        }
                    }
                }
                if (false == intersect) {
                    triangulation.add(eij);
                    //we know the point it connected to as sj
                    ArrayList<Edge> edges = findEdgesOfStore(sj);
                    for (Edge ejp : edges) {
                        Store sp = ejp.s1 == sj ? s2 : s1;
                        Edge eip = findEdgeBetweenStores(si, sp);
                        if (eip != null) {
                            //set the next, prev of si, sj and sp
                            if (left_turn(si, sj, sp)) {
                                eij.setNext(ejp);
                                eij.setPrevious(eip);
                                ejp.setNext(eip);
                                ejp.setPrevious(eij);
                                eip.setNext(eij);
                                eip.setPrevious(ejp);
                            } else {
                                eij.setNext(eip);
                                eij.setPrevious(ejp);
                                ejp.setNext(eij);
                                ejp.setPrevious(eip);
                                eip.setNext(ejp);
                                eip.setPrevious(eij);
                            }
                        }
                    }

                }
            }

            //how to determine if the former points are visible to point i(thought about intersections) 
        }
        calculateTwins();

        //todo: calculate triangulation based on incremental
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
                if ((ei.s1 == ej.s1 && ei.s2 == ej.s2) && (ei.s2 == ej.s1 && ei.s1 == ej.s2)) {
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

    private void flipEdges() {

        //todo: modifies the triangulation to the delaunay triangulation
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
    
        public ArrayList<Edge> geTriangulation() {
        return triangulation;
    }

    public Store getStoreAt(int x, int y) {
        for (Store s : stores) {
            if (s.x == x && s.y == y) {
                return s;
            }
        }
        return null;
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
}
