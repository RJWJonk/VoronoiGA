
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.Iterator;
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

    public Triangle triangulation3;

    public static void main(String[] args) {
        GameBoard gb = new GameBoard();
    }

    public GameBoard() {

//        Store s0 = new Store(0, 0, 0);
//        Store s1 = new Store(0, 1, 0);
//        Store s2 = new Store(1, 1, 0);
//        Store s3 = new Store(1, 0, 0);
//        Triangle t1 = new Triangle(s0, s1, s2);
//        Triangle t2 = new Triangle(s2, s3, s0);
//        Store n0 = new Store(-1, 2, 0);
//        Store n1 = new Store(2, 0, 0);
//        Store n2 = new Store(0, -1, 0);
//        Triangle nn1 = new Triangle(n0, s1, s0);
//        Triangle nn2 = new Triangle(s2, s1, n0);
//        Triangle nn3 = new Triangle(s3, s2, n1);
//        Triangle nn4 = new Triangle(s3, n2, s0);
//        t1.n0 = nn1;
//        t1.n1 = nn2;
//        t1.n2 = t2;
//        t2.n0 = nn3;
//        t2.n1 = nn4;
//        t2.n2 = t1;
//        nn1.n1 = t1;
//        nn2.n0 = t1;
//        nn3.n0 = t2;
//        nn4.n2 = t2;
//
//        System.out.println("FLIP TEST");
//        System.out.println(t1.s0);
//        System.out.println(t1.s1);
//        System.out.println(t1.s2);
//        System.out.println(t1.n0);
//        System.out.println(t1.n1);
//        System.out.println(t1.n2);
//
//        System.out.println("AND");
//        System.out.println(t2.s0);
//        System.out.println(t2.s1);
//        System.out.println(t2.s2);
//        System.out.println(t2.n0);
//        System.out.println(t2.n1);
//        System.out.println(t2.n2);
//
//        flip(t1, t2);
//
//        t1 = t2.children.get(0);
//        t2 = t2.children.get(1);
//        System.out.println("FLIP TEST");
//        System.out.println(t1.s0);
//        System.out.println(t1.s1);
//        System.out.println(t1.s2);
//        System.out.println(t1.n0);
//        System.out.println(t1.n1);
//        System.out.println(t1.n2);
//
//        System.out.println("AND");
//        System.out.println(t2.s0);
//        System.out.println(t2.s1);
//        System.out.println(t2.s2);
//        System.out.println(t2.n0);
//        System.out.println(t2.n1);
//        System.out.println(t2.n2);
//
//        System.out.println("WITH NB");
//        System.out.println(nn1.n1);
//        System.out.println(nn2.n0);
//        System.out.println(nn3.n0);
//        System.out.println(nn4.n2);
    }

    private boolean overlapStores(Triangle t1, Triangle t2) {
        if (t1 == null || t2 == null) {
            return false;
        }
        return (t1.s0 == t2.s0 || t1.s0 == t2.s1 || t1.s0 == t2.s2
                || t1.s1 == t2.s0 || t1.s1 == t2.s1 || t1.s1 == t2.s2
                || t1.s2 == t2.s0 || t1.s2 == t2.s1 || t1.s2 == t2.s2);
    }

    private void calculateTriangulation() {
        Store b1 = new Store(-1000, 100, -1);
        Store b2 = new Store(300, 1000, -1);
        Store b3 = new Store(1000, 100, -1);

        //additional points for neighbours of initial triangle
        Store nb1 = new Store(-300000, 90000, -1);
        Store nb2 = new Store(90000, 90000, -1);
        Store nb3 = new Store(3000, -800000, -1);
        Store nbx1 = new Store(-400000, 1000000, -1);
        Store nbx2 = new Store(40000000, 1000000, -1);
        Store nbx3 = new Store(300000, - 10000000, -1);

        Triangle b = new Triangle(b1, b2, b3);
//        Triangle bb1 = new Triangle(b1, nb1, b2);
//        Triangle bb2 = new Triangle(b2, nb2, b3);
//        Triangle bb3 = new Triangle(b3, nb3, b1);
//        Triangle bb1x = new Triangle(b1, nbx1, nb1);
//        Triangle bb1y = new Triangle(nbx1, b2, nb1);
//        Triangle bb2x = new Triangle(b2, nbx2, nb2);
//        Triangle bb2y = new Triangle(nbx2, b3, nb2);
//        Triangle bb3x = new Triangle(b3, nbx3, nb3);
//        Triangle bb3y = new Triangle(nbx3, b1, nb3);

//        b.n0 = bb1;
//        bb1.n2 = b;
//        bb1.n1 = bb1x;
//        bb1.n0 = bb1y;
//        b.n1 = bb2;
//        bb2.n2 = b;
//        bb2.n1 = bb2x;
//        bb2.n0 = bb2y;
//        b.n2 = bb3;
//        bb3.n2 = b;
//        bb3.n1 = bb3x;
//        bb3.n0 = bb3y;
//        bb1.neighbours.add(b);
//        bb1.neighbours.add(bb1x);
//        bb1.neighbours.add(bb1y);
//        bb2.neighbours.add(b);
//        bb2.neighbours.add(bb2x);
//        bb2.neighbours.add(bb2y);
//        bb3.neighbours.add(b);
//        bb3.neighbours.add(bb3x);
//        bb3.neighbours.add(bb3y);
        triangulation3 = b;

        if (stores.size() == 0) {
            return;
        }

        for (Store s : stores) {

            Triangle leaf = triangulation3;

            while (!leaf.isLeaf()) {
                Triangle tt = leaf;
                leaf = null;
                for (Triangle t : tt.children) {
                    // System.out.println(pointInTriangle(s,t));
                    if (pointInTriangle(s, t)) {
                        leaf = t;
                        break;
                    }

                }
            }

//            System.out.println("LEAF:");
//            System.out.println(leaf.s0);
//            System.out.println(leaf.s1);
//            System.out.println(leaf.s2);
            Triangle new0;
            Triangle new1;
            Triangle new2;

            new0 = new Triangle(s, leaf.s0, leaf.s1);
            new1 = new Triangle(s, leaf.s1, leaf.s2);
            new2 = new Triangle(s, leaf.s2, leaf.s0);
//            new0.neighbours.add(new1);
//            new0.neighbours.add(new2);
//            new1.neighbours.add(new0);
//            new1.neighbours.add(new2);
//            new2.neighbours.add(new1);
//            new2.neighbours.add(new0);

            new0.n0 = new2;
            new0.n2 = new1;
            new1.n0 = new0;
            new1.n2 = new2;
            new2.n0 = new1;
            new2.n2 = new0;
//            for (Triangle n : leaf.neighbours) {
//                for (Triangle nn : n.neighbours) {
//                    if (nn == leaf && shareEdge(new0, n)) {
//                        n.neighbours.remove(leaf);
//                        n.neighbours.add(new0);
//                        new0.neighbours.add(n);
//                    } else if (nn == leaf && shareEdge(new1, n)) {
//                        n.neighbours.remove(leaf);
//                        n.neighbours.add(new1);
//                        new1.neighbours.add(n);
//                    } else if (nn == leaf && shareEdge(new2, n)) {
//                        n.neighbours.remove(leaf);
//                        n.neighbours.add(new2);
//                        new2.neighbours.add(n);
//                    }
//                }
//            }

            Triangle n;

            n = findNeighbour(new0.s1, leaf);
            new0.n1 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == leaf) {
                    n.n0 = new0;
                } else if (findNeighbour(n.s1, n) == leaf) {
                    n.n1 = new0;
                } else if (findNeighbour(n.s2, n) == leaf) {
                    n.n2 = new0;
                }
            }

            n = findNeighbour(new1.s1, leaf);
            new1.n1 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == leaf) {
                    n.n0 = new1;
                } else if (findNeighbour(n.s1, n) == leaf) {
                    n.n1 = new1;
                } else if (findNeighbour(n.s2, n) == leaf) {
                    n.n2 = new1;
                }
            }

            n = findNeighbour(new2.s1, leaf);
            new2.n1 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == leaf) {
                    n.n0 = new2;
                } else if (findNeighbour(n.s1, n) == leaf) {
                    n.n1 = new2;
                } else if (findNeighbour(n.s2, n) == leaf) {
                    n.n2 = new2;
                }
            }

//            if (shareEdge(new0, leaf.n0)) {
//                new0.n1 = leaf.n0;
//            }
//            if (shareEdge(new0, leaf.n1)) {
//                new0.n1 = leaf.n1;
//            }
//            if (shareEdge(new0, leaf.n2)) {
//                new0.n1 = leaf.n2;
//            }
//            if (shareEdge(new1, leaf.n0)) {
//                new1.n1 = leaf.n0;
//            }
//            if (shareEdge(new1, leaf.n1)) {
//                new1.n1 = leaf.n1;
//            }
//            if (shareEdge(new1, leaf.n2)) {
//                new1.n1 = leaf.n2;
//            }
//            if (shareEdge(new2, leaf.n0)) {
//                new2.n1 = leaf.n0;
//            }
//            if (shareEdge(new2, leaf.n1)) {
//                new2.n1 = leaf.n1;
//            }
//            if (shareEdge(new2, leaf.n2)) {
//                new2.n1 = leaf.n2;
//            }
            leaf.children.add(new0);
            leaf.children.add(new1);
            leaf.children.add(new2);

            if (stores.size() > 0) {
                //flipping
                Stack<Triangle> flipping = new Stack();
                flipping.push(new2);
                flipping.push(new1);
                flipping.push(new0);

                while (!flipping.isEmpty()) {
                    Triangle f = flipping.pop();
                    Store fs = findCenter(f);
                    double r = (fs.x - f.s0.x) * (fs.x - f.s0.x) + (fs.y - f.s0.y) * (fs.y - f.s0.y);
//                    if (overlapStores(f, b)) {
//                        r = Double.MAX_VALUE;
//                    }
                    r = Math.sqrt(r);
//                    for (Triangle n : f.neighbours) {
//                        if (inCircle(fs, r, n)) {
//                            System.out.println("FLIPPING NOW");
//                            flip(f, n);
//                            for (Triangle fa : f.children) {
//                                if (fa != triangulation3) {
//                                    flipping.push(fa);
//                                }
//                            }
//                        }
//                    }
                    if (inCircle(fs, r, f.n0)) {
                        System.out.println("FLIPPING NOW");
                        flip(f, f.n0);
                        for (Triangle fa : f.children) {
                            if (!overlapStores(fa, b)) {
                                System.out.println("PROPAGATE");
                                flipping.push(fa);
                            }
                        }
                    } else if (inCircle(fs, r, f.n1)) {
                        System.out.println("FLIPPING NOW");
                        flip(f, f.n1);
                        for (Triangle fa : f.children) {
                            if (!overlapStores(fa, b)) {
                                System.out.println("PROPAGATE");
                                flipping.push(fa);
                            }
                        }
                    } else if (inCircle(fs, r, f.n2)) {
                        System.out.println("FLIPPING NOW");
                        flip(f, f.n2);
                        for (Triangle fa : f.children) {
                            if (!overlapStores(fa, b)) {
                                System.out.println("PROPAGATE");
                                flipping.push(fa);
                            }
                        }
                    }

                }
            }
        }

//
//        triangulation.clear();
//        triangulation2.clear();
//
//        stores.sort(new Comparator<Store>() {
//
//            @Override
//            public int compare(Store s1, Store s2) {
//                return (int) (s1.x - s2.x);
//            }
//        });
//
//        ArrayList<Store> todo = (ArrayList<Store>) stores.clone();
//
//        //Initialize first 3 points
//        int progress = 0;
//        for (int i = 2; i < todo.size(); i++) {
//            progress = i;
//            Triangle t = new Triangle(todo.get(i), todo.get(i - 1), todo.get(i - 2));
//            if (t.calculateArea() > 0.01) { //machine precision things..
//                System.out.println("ADD");
//                triangulation2.add(t);
//                for (int j = i; j > 0; j--) {
//                    t = new Triangle(todo.get(i), todo.get(j), todo.get(j - 1));
//                    triangulation2.add(t);
//                }
//                break;
//            }
//        }
//
//        for (int i = progress + 1; i < todo.size(); i++) {
//            Store si = todo.get(i);
//            for (int j = i - 1; j >= 1; j--) {
//                Store sj = todo.get(j);
//                for (int k = j - 1; k >= 0; k--) {
//                    Store sk = todo.get(k);
//                    Triangle t = new Triangle(si, sj, sk);
//                    boolean overlaps = false;
//                    for (Triangle tt : triangulation2) {
//                        overlaps = overlaps || overlaps(t, tt);
//                    }
//                    if (!overlaps && t.calculateArea() > 0.001) {
//                        triangulation2.add(t);
//                    }
//                }
//            }
//        }
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
//        calculateTwins();
//        flipEdges();
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

    public void flip(Triangle t1, Triangle t2) {

//        System.out.println("FLIP");
//        System.out.println(t2.s0);
//        System.out.println(t2.s1);
//        System.out.println(t2.s2);
//        System.out.println("===");
//        System.out.println(t2.n0.s0);
//        System.out.println(t2.n0.s1);
//        System.out.println(t2.n0.s2);
//        System.out.println("==");
//        System.out.println(t2.n0.s0);
//        System.out.println(t2.n1.s1);
//        System.out.println(t2.n2.s2);
//        System.out.println("==");
//        System.out.println(t2.n0.s0);
//        System.out.println(t2.n1.s1);
//        System.out.println(t2.n2.s2);
        Edge missing = findMissingEdge(t1, t2);
        Edge common = findCommonEdge(t1, t2);
        Triangle new1 = new Triangle(missing.s1, common.s1, missing.s2);
        Triangle new2 = new Triangle(missing.s2, common.s2, missing.s1);
        boolean changed = makeAntiClockWise(new1) && makeAntiClockWise(new2);
        makeAntiClockWise(new1);
        makeAntiClockWise(new2);

        if (changed) {
            Triangle temp = new1;
            new1 = new2;
            new2 = temp;
        }

        new1.n2 = new2;
        new2.n2 = new1;

        Triangle n;

        int count = 0;

        if (storeInTriangle(new1.s0, t1)) {
            // System.out.println("OKAY?");
            n = findNeighbour(new1.s0, t1);
            new1.n0 = n;
//            System.out.println(n.s0);
//            System.out.println(n.s1);
//            System.out.println(n.s2);
//            System.out.println(t1.s0);
//            System.out.println(t1.s1);
//            System.out.println(t1.s2);
            if (n != null) {
                if (findNeighbour(n.s0, n) == t1) {
                    count++;
                    n.n0 = new1;
                }
                if (findNeighbour(n.s1, n) == t1) {
                    count++;
                    n.n1 = new1;
                }
                if (findNeighbour(n.s2, n) == t1) {
                    count++;
                    n.n2 = new1;
                }
            }
        } else if (storeInTriangle(new1.s0, t2)) {
            //    System.out.println("NOT OKAY?");
            n = findNeighbour(new1.s0, t2);
            new1.n0 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == t2) {
                    count++;
                    n.n0 = new1;
                }
                if (findNeighbour(n.s1, n) == t2) {
                    count++;
                    n.n1 = new1;
                }
                if (findNeighbour(n.s2, n) == t2) {
                    count++;
                    n.n2 = new1;
                }
            }
        }

        //    System.out.println("QUARTERWAY COUNT IS " + count);
        if (storeInTriangle(new2.s0, t1)) {
            //        System.out.println("NOT OKAY?");
            n = findNeighbour(new2.s0, t1);
            new2.n0 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == t1) {
                    count++;
                    n.n0 = new2;
                }
                if (findNeighbour(n.s1, n) == t1) {
                    count++;
                    n.n1 = new2;
                }
                if (findNeighbour(n.s2, n) == t1) {
                    count++;
                    n.n2 = new2;
                }
            }
        } else if (storeInTriangle(new2.s0, t2)) {
            //       System.out.println("OKAY?");
            n = findNeighbour(new2.s0, t2);
//            System.out.println(n.s0);
//            System.out.println(n.s1);
//            System.out.println(n.s2);
//            System.out.println(t2.s0);
//            System.out.println(t2.s1);
//            System.out.println(t2.s2);
            new2.n0 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == t2) {
                    count++;
                    n.n0 = new2;
                }
                if (findNeighbour(n.s1, n) == t2) {
                    count++;
                    n.n1 = new2;
                }
                if (findNeighbour(n.s2, n) == t2) {
                    count++;
                    n.n2 = new2;
                }
            }
        }

        //   System.out.println("HALFWAY COUNT IS " + count);
        if (findNeighbour(new1.s1, t1) != t2) {
            n = findNeighbour(new1.s1, t1);
            new1.n1 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == t1) {
                    count++;
                    n.n0 = new1;
                }
                if (findNeighbour(n.s1, n) == t1) {
                    count++;
                    n.n1 = new1;
                }
                if (findNeighbour(n.s2, n) == t1) {
                    count++;
                    n.n2 = new1;
                }
            }
        } else {
            n = findNeighbour(new1.s1, t2);
            new1.n1 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == t2) {
                    count++;
                    n.n0 = new1;
                }
                if (findNeighbour(n.s1, n) == t2) {
                    count++;
                    n.n1 = new1;
                }
                if (findNeighbour(n.s2, n) == t2) {
                    count++;
                    n.n2 = new1;
                }
            }
        }

        //   System.out.println("THREE QUARTER WAY COUNT IS " + count);
        if (findNeighbour(new2.s1, t1) != t2) {
            n = findNeighbour(new2.s1, t1);
            new2.n1 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == t1) {
                    count++;
                    n.n0 = new2;
                }
                if (findNeighbour(n.s1, n) == t1) {
                    count++;
                    n.n1 = new2;
                }
                if (findNeighbour(n.s2, n) == t1) {
                    count++;
                    n.n2 = new2;
                }
            }
        } else {
            n = findNeighbour(new2.s1, t2);
            new2.n1 = n;
            if (n != null) {
                if (findNeighbour(n.s0, n) == t2) {
                    count++;
                    n.n0 = new2;
                }
                if (findNeighbour(n.s1, n) == t2) {
                    count++;
                    n.n1 = new2;
                }
                if (findNeighbour(n.s2, n) == t2) {
                    count++;
                    n.n2 = new2;
                }
            }
        }

        //   System.out.println("COUNT IS " + count);
        t1.children.add(new1);
        t1.children.add(new2);
        t2.children.add(new1);
        t2.children.add(new2);

    }

    public Triangle findNeighbour(Store s, Triangle t) {
        if (!storeInTriangle(s, t)) {
            System.out.println("Well..");
            return null;
        }
//        System.out.println("FIND NEIGHBOUR");
//        System.out.println(t.s0);
//        System.out.println(t.s1);
//        System.out.println(t.s2);
//        System.out.println("===");
//        System.out.println(t.n0.s0);
//        System.out.println(t.n0.s1);
//        System.out.println(t.n0.s2);
//        System.out.println("==");
//        System.out.println(t.n0.s0);
//        System.out.println(t.n1.s1);
//        System.out.println(t.n2.s2);
//        System.out.println("==");
//        System.out.println(t.n0.s0);
//        System.out.println(t.n1.s1);
//        System.out.println(t.n2.s2);

        return s == t.s0 ? t.n0 : (s == t.s1 ? t.n1 : t.n2);

    }

    public boolean makeAntiClockWise(Triangle t) {
        if (left_turn(t.s0, t.s1, t.s2)) {
            return false;
        } else {
            Store temp = t.s0;
            t.s0 = t.s2;
            t.s2 = temp;
            return true;
        }
    }

    public void flip2(Triangle t1, Triangle t2) {
        Store swap1 = !storeInTriangle(t2.s0, t1) ? t2.s0 : !storeInTriangle(t2.s1, t1) ? t2.s1 : t2.s2;
        Store prev1 = swap1 == t2.s0 ? t2.s2 : swap1 == t2.s1 ? t2.s0 : t2.s1;
        Edge edge1 = findOppositeEdge(prev1, t1);

        Triangle new1;
//        if (!left_turn(swap1, edge1.s1, edge1.s2)) {
        new1 = new Triangle(swap1, edge1.s1, edge1.s2);
//        } else {
//            new1 = new Triangle(swap1, edge1.s2, edge1.s1);
//        }

        Store swap2 = !storeInTriangle(t1.s0, t2) ? t1.s0 : !storeInTriangle(t1.s1, t2) ? t1.s1 : t1.s2;
        Store prev2 = swap2 == t1.s0 ? t1.s2 : swap2 == t1.s1 ? t1.s0 : t1.s1;
        Edge edge2 = findOppositeEdge(prev2, t2);

        Triangle new2;

//        if (!left_turn(swap2, edge2.s1, edge2.s2)) {
        new2 = new Triangle(swap2, edge2.s1, edge2.s2);
//        } else {
//            new2 = new Triangle(swap2, edge2.s2, edge2.s1);
//        }

        new1.n2 = new2;
        new2.n2 = new1;

        int count = 0;

        if (shareEdge(t2.n0, new1)) {
            count++;

            new1.n0 = t2.n0;
            if (t2.n0.n0 == t2) {
                t2.n0.n0 = new1;
            }
            if (t2.n0.n1 == t2) {
                t2.n0.n1 = new1;
            }
            if (t2.n0.n2 == t2) {
                t2.n0.n2 = new1;
            }
        } else if (shareEdge(t2.n1, new1)) {
            count++;
            new1.n0 = t2.n1;
            if (t2.n1.n0 == t2) {
                t2.n1.n0 = new1;
            }
            if (t2.n1.n1 == t2) {
                t2.n1.n1 = new1;
            }
            if (t2.n1.n2 == t2) {
                t2.n1.n2 = new1;
            }
        } else if (shareEdge(t2.n2, new1)) {
            count++;
            new1.n0 = t2.n2;
            if (t2.n2.n0 == t2) {
                t2.n2.n0 = new1;
            }
            if (t2.n2.n1 == t2) {
                t2.n2.n1 = new1;
            }
            if (t2.n2.n2 == t2) {
                t2.n2.n2 = new1;
            }
        }

        if (shareEdge(t1.n0, new2)) {
            count++;
            new2.n0 = t1.n0;
            if (t1.n0.n0 == t1) {
                t1.n0.n0 = new2;
            }
            if (t1.n0.n1 == t1) {
                t1.n0.n1 = new2;
            }
            if (t1.n0.n2 == t1) {
                t1.n0.n2 = new2;
            }
        } else if (shareEdge(t1.n1, new2)) {
            count++;
            new2.n0 = t1.n1;
            if (t1.n1.n0 == t1) {
                t1.n1.n0 = new2;
            }
            if (t1.n1.n1 == t1) {
                t1.n1.n1 = new2;
            }
            if (t1.n1.n2 == t1) {
                t1.n1.n2 = new2;
            }
        } else if (shareEdge(t1.n2, new2)) {
            count++;
            new2.n0 = t1.n2;
            if (t1.n2.n0 == t1) {
                t1.n2.n0 = new2;
            }
            if (t1.n2.n1 == t1) {
                t1.n2.n1 = new2;
            }
            if (t1.n2.n2 == t1) {
                t1.n2.n2 = new2;
            }
        }

        if (shareEdge(t1.n0, new1)) {
            count++;
            new1.n1 = t1.n0;
            if (t1.n0.n0 == t1) {
                t1.n0.n0 = new1;
            }
            if (t1.n0.n1 == t1) {
                t1.n0.n1 = new1;
            }
            if (t1.n0.n2 == t1) {
                t1.n0.n2 = new1;
            }
        } else if (shareEdge(t1.n1, new1)) {
            count++;
            new1.n1 = t1.n1;
            if (t1.n1.n0 == t1) {
                t1.n1.n0 = new1;
            }
            if (t1.n1.n1 == t1) {
                t1.n1.n1 = new1;
            }
            if (t1.n1.n2 == t1) {
                t1.n1.n2 = new1;
            }
        } else if (shareEdge(t1.n2, new1)) {
            count++;
            new1.n1 = t1.n2;
            if (t1.n2.n0 == t1) {
                t1.n2.n0 = new1;
            }
            if (t1.n2.n1 == t1) {
                t1.n2.n1 = new1;
            }
            if (t1.n2.n2 == t1) {
                t1.n2.n2 = new1;
            }
        }

        if (shareEdge(t2.n0, new2)) {
            count++;
            new2.n1 = t2.n0;
            if (t2.n0.n0 == t2) {
                t2.n0.n0 = new2;
            }
            if (t2.n0.n1 == t2) {
                t2.n0.n1 = new2;
            }
            if (t2.n0.n2 == t2) {
                t2.n0.n2 = new2;
            }
        } else if (shareEdge(t2.n1, new2)) {
            count++;
            new2.n1 = t2.n1;
            if (t2.n1.n0 == t2) {
                t2.n1.n0 = new2;
            }
            if (t2.n1.n1 == t2) {
                t2.n1.n1 = new2;
            }
            if (t2.n1.n2 == t2) {
                t2.n1.n2 = new2;
            }
        } else if (shareEdge(t2.n2, new2)) {
            count++;
            new2.n1 = t1.n2;
            if (t2.n2.n0 == t1) {
                t2.n2.n0 = new2;
            }
            if (t2.n2.n1 == t1) {
                t2.n2.n1 = new2;
            }
            if (t2.n2.n2 == t1) {
                t2.n2.n2 = new2;
            }
        }

        //System.out.println("TESTING COUNT: " + count);
        t1.children.add(new1);
        t1.children.add(new2);
        t2.children.add(new1);
        t2.children.add(new2);
    }

    public Edge findMissingEdge(Triangle t1, Triangle t2) {
        ArrayList<Store> s1 = new ArrayList<>();
        s1.add(t1.s0);
        s1.add(t1.s1);
        s1.add(t1.s2);
        s1.remove(t2.s0);
        s1.remove(t2.s1);
        s1.remove(t2.s2);
        ArrayList<Store> s2 = new ArrayList<>();
        s2.add(t2.s0);
        s2.add(t2.s1);
        s2.add(t2.s2);
        s2.remove(t1.s0);
        s2.remove(t1.s1);
        s2.remove(t1.s2);

        return new Edge(s1.get(0), s2.get(0));
    }

    public Edge findCommonEdge(Triangle t1, Triangle t2) {
        Edge e = findMissingEdge(t1, t2);
        ArrayList<Store> s = new ArrayList<>();
        s.add(t2.s0);
        s.add(t2.s1);
        s.add(t2.s2);
        if (!s.contains(t1.s0)) {
            s.add(t1.s0);
        }
        if (!s.contains(t1.s1)) {
            s.add(t1.s1);
        }
        if (!s.contains(t1.s2)) {
            s.add(t1.s2);
        }
        s.remove(e.s1);
        s.remove(e.s2);
        return new Edge(s.get(0), s.get(1));
    }

    public Edge findOppositeEdge(Store s, Triangle t) {
        if (s == t.s0) {
            return new Edge(t.s1, t.s2);
        }
        if (s == t.s1) {
            return new Edge(t.s2, t.s0);
        }
        if (s == t.s2) {
            return new Edge(t.s0, t.s1);
        }
        return null;
    }

    public boolean storeInTriangle(Store s, Triangle t) {
        return s == t.s0 || s == t.s1 || s == t.s2;
    }

    public boolean inCircle(Store center, double r, Triangle t) {
        if (t == null) {
            //System.out.println("null");
            return false;
        }
//        System.out.println("%===INCIRCLE===%");
//        System.out.println(center);
//        System.out.println(t.s0);
//        System.out.println(t.s1);
//        System.out.println(t.s2);
        double d0 = (t.s0.x - center.x) * (t.s0.x - center.x) + (t.s0.y - center.y) * (t.s0.y - center.y);
        double d1 = (t.s1.x - center.x) * (t.s1.x - center.x) + (t.s1.y - center.y) * (t.s1.y - center.y);
        double d2 = (t.s2.x - center.x) * (t.s2.x - center.x) + (t.s2.y - center.y) * (t.s2.y - center.y);
        d0 = Math.sqrt(d0);
        d1 = Math.sqrt(d1);
        d2 = Math.sqrt(d2);
//        System.out.println("====");
//        System.out.println(r);
//        System.out.println(d0);
//        System.out.println(d1);
//        System.out.println(d2);
        double d = d0 < d1 ? (d2 < d0 ? d2 : d0) : (d2 < d1 ? d2 : d1);
//        System.out.println(d);
//        System.out.println(d < r * 0.999);
//        System.out.println(r);
//        System.out.println(d);
        return d < r * 0.999;
    }

    public Store findCenter(Triangle t) {

        //line 1
        double cx1 = (t.s1.x + t.s0.x) / 2;
        double cy1 = (t.s1.y + t.s0.y) / 2;
        double a1 = (t.s1.y - t.s0.y) / (t.s1.x - t.s0.x);
        a1 = -1 / a1;
        double b1 = cy1 - a1 * cx1;

        //line 2
        double cx2 = (t.s2.x + t.s1.x) / 2;
        double cy2 = (t.s2.y + t.s1.y) / 2;
        double a2 = (t.s2.y - t.s1.y) / (t.s2.x - t.s1.x);
        a2 = -1 / a2;
        double b2 = cy2 - a2 * cx2;

        double x = (b2 - b1) / (a1 - a2);
        double y = a1 * x + b1;

        return new Store(x, y, -1);
    }

    public boolean shareEdge(Triangle t1, Triangle t2) {
//        if (t2 == null) {
//            return true;
//        }
        //if (t1 == null) return false;

        int c = 0;
        c += t1.s0 == t2.s0 || t1.s0 == t2.s1 || t1.s0 == t2.s2 ? 1 : 0;
        c += t1.s1 == t2.s0 || t1.s1 == t2.s1 || t1.s1 == t2.s2 ? 1 : 0;
        c += t1.s2 == t2.s0 || t1.s2 == t2.s1 || t1.s2 == t2.s2 ? 1 : 0;

        return c == 2;
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
        double pz = p.x * p.x + p.y * p.y;
        double qz = q.x * q.x + q.y * q.y;
        double rz = r.x * r.x + r.y * r.y;
        double sz = s.x * s.x + s.y * s.y;

        double x = det(1, p.x, 1, q.x) * det(r.y, rz, s.y, sz)
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

    private double det(double a, double b, double c, double d) {
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

    public double crossProduct(Store s1, Store s2, Store s3, Store s4) {
        double x1 = s2.x - s1.x;
        double y1 = s2.y - s1.y;
        double x2 = s4.x - s3.x;
        double y2 = s4.y - s3.y;

        return x1 * y2 - y1 * x2;
    }

    //determines wether p1 is on the same side as p2 compared to the line ab
    public boolean sameSide(Store p1, Store p2, Store a, Store b) {
        double cp1 = crossProduct(b, a, p1, a);
        double cp2 = crossProduct(b, a, p2, a);
        return cp1 * cp2 > 0;
    }

    public boolean onLine(Store p1, Store a, Store b) {
        //System.out.println(crossProduct(b, a, p1, a));
        return crossProduct(b, a, p1, a) < 0.001 && crossProduct(b, a, p1, a) > -0.001 && ((a.x <= p1.x && p1.x <= b.x) || (b.x <= p1.x && p1.x <= a.x));
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
    private boolean intersects(Store s1, Store s2, Line2D.Double l2) {
        System.out.println("Intersection:");
        System.out.println("(" + s1.x + "," + s1.y + ")->(" + s2.x + "," + s2.y + ")");
        System.out.println("(" + l2.x1 + "," + l2.y1 + ")->(" + l2.x2 + "," + l2.y2 + ")");

        System.out.println(above(s1, l2));
        System.out.println(above(s2, l2));
        return above(s1, l2) * above(s2, l2) == -1 && l2.intersectsLine(new Line2D.Double(s1.x, s1.y, s2.x, s2.y));

    }

    private int above(Store s, Line2D.Double l) {
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
        Line2D.Double line0 = new Line2D.Double(t1.s0.x, t1.s0.y, t1.s1.x, t1.s1.y);
        Line2D.Double line1 = new Line2D.Double(t1.s1.x, t1.s1.y, t1.s2.x, t1.s2.y);
        Line2D.Double line2 = new Line2D.Double(t1.s2.x, t1.s2.y, t1.s0.x, t1.s0.y);

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

}
