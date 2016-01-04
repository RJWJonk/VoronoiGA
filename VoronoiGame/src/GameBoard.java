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

       for (int i=4; i<=stores.size(); i++){
        Store si = todo.get(i-1);
            for (int j = i - 1; j > 0 ; j--) {
                boolean intersect = false;
                
                Store sj = todo.get(j-1);
                Edge eij = new Edge(si, sj);
                
                for (int k = 1; k < i-1; k++){
                    
                    for (int l = k+1; l <= i-1; l++){
                    
                        Store sk = todo.get(k-1);
                        Store sl = todo.get(l-1);
                        
                        
                        Line2D line1 = new Line2D.Float(si.x, si.y, sj.x, sj.y);
                        Line2D line2 = new Line2D.Float(sk.x, sk.y, sl.x, sl.y);
                        if (((sj.x == sk.x) && (sj.y == sk.y)) || ((sj.x == sl.x) && (sj.y == sl.y))){
                           //System.out.println("ispoint");
                        } else {
                            intersect = line1.intersectsLine(line2);
                        }
                    }
                }
                if (false == intersect) {
                    triangulation.add(eij);
                }
            }
        
        
        
        //how to determine if the former points are visible to point i(thought about intersections) 
        }
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
    
    public void addStore(int x, int y, int o) {
        Store s = new Store(x, y, o);
        addStore(s);
    }
    
    //add a store to the data set and recalculate the triangulation
    public void addStore(Store s) {
        stores.add(s);
    }
    
    public ArrayList<Store> getStores() {
        return stores;
    }
    
    public Store getStoreAt(int x, int y) {
        for (Store s : stores) {
            if (s.x == x && s.y == y) return s;
        }
        return null;
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
