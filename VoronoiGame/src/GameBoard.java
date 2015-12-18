import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

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
        Random r = new Random();

        for (int i = 0; i < 6; i++) {
            stores.add(new Store(r.nextInt(10), r.nextInt(10), i % 2));
        }
        calculateTriangulation();

        System.out.println("===Stores===");
        for (Store s : stores) {
            System.out.println(s.toString());
        }

        System.out.println("===Edges===");
        for (Edge e : triangulation) {
            System.out.println(e.s1.toString() + " -> " + e.s2.toString());
        }
    }

    private ArrayList<Edge> calculateTriangulation() {
        if (stores.size() < 3) {
            return null;
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

        e12.setNext(e23);
        e12.setPrevious(e31);
        e23.setNext(e31);
        e23.setPrevious(e12);
        e31.setNext(e12);
        e31.setPrevious(e23);

        //todo: calculate triangulation based on incremental
        return null;
    }

    private ArrayList<Edge> flipEdges() {

        //todo: return the delaunay triangulation
        return null;
    }

    public Edge getEdge(Store s1, Store s2) {
        for (Edge e : triangulation) {
            if ((e.s1 == s1 && e.s2 == s2) || (e.s1 == s2 && e.s2 == s1)) {
                return e;
            }
        }
        return null;
    }

    //stores are vertices
    private class Store {

        //coordinates of store
        private float x;
        private float y;

        //weight, once we add the feature
        private float weight;

        private int owner; //player 0 or player 1

        private Store(int x, int y, int owner) {
            this.x = x;
            this.y = y;
            this.owner = owner;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    //edges between stores
    private class Edge {

        private Store s1;
        private Store s2;

        private Edge next;
        private Edge prev;
        private Edge twin;

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
