
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

    public Triangle triangulation;


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
        Store b3 = new Store(1000, 99, -1);


        Triangle b = new Triangle(b1, b2, b3);

        triangulation = b;

        if (stores.size() == 0) {
            return;
        }

        for (Store s : stores) {

            Triangle leaf = triangulation;

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

            Triangle new0;
            Triangle new1;
            Triangle new2;

            new0 = new Triangle(s, leaf.s0, leaf.s1);
            new1 = new Triangle(s, leaf.s1, leaf.s2);
            new2 = new Triangle(s, leaf.s2, leaf.s0);

            new0.n0 = new2;
            new0.n2 = new1;
            new1.n0 = new0;
            new1.n2 = new2;
            new2.n0 = new1;
            new2.n2 = new0;

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
                    if (!isLocalDelaunay(f, f.n0)) {
                        flip(f, f.n0);
                        for (Triangle fa : f.children) {
                            if (!overlapStores(fa, b)) {
                                flipping.push(fa);
                            }
                        }
                    } else if (!isLocalDelaunay(f, f.n1)) {
                        flip(f, f.n1);
                        for (Triangle fa : f.children) {
                            if (!overlapStores(fa, b)) {
                                flipping.push(fa);
                            }
                        }
                    } else if (!isLocalDelaunay(f, f.n0)) {
                        flip(f, f.n2);
                        for (Triangle fa : f.children) {
                            if (!overlapStores(fa, b)) {
                                flipping.push(fa);
                            }
                        }
                    }

                }
            }
        }


    }

    public void flip(Triangle t1, Triangle t2) {

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


    private boolean left_turn(Store a, Store b, Store c) {

        double temp = (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));

        return temp < 0;
    }




    public boolean isLocalDelaunay(Triangle t1, Triangle t2) {
        if (t1 == null || t2 == null) {
            return true;
        }

        Edge e = findCommonEdge(t1, t2);
        Edge e0 = findMissingEdge(t1, t2);
        
        

        if (e == null) {
            System.out.println("Null??");
            return true;
        }
        
//        int c = 0;
//        if (storeInTriangle(e.s1,triangulation)) c++;
//        if (storeInTriangle(e.s2,triangulation)) c++;
//        if (storeInTriangle(e0.s1,triangulation)) c++;
//        if (storeInTriangle(e0.s2,triangulation)) c++;
//        
//        if (c == 1 && (storeInTriangle(e.s1,triangulation)||storeInTriangle(e.s2,triangulation))) {
//            System.out.println("OKAY");
//            return false;
//        }
//        if (c == 1 && (storeInTriangle(e0.s1,triangulation)||storeInTriangle(e0.s2,triangulation))) {
//            //return true;
//        }
        

        Store p = e.s1;
        Store q = e.s2;
        Store r = e0.s1;
        Store s = e0.s2;

        return !(signDet3(p, q, r) * signDet4(p, q, r, s) > 0);

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
       // System.out.println(x);
        if (x > 0) {
            return (1);
        } else {
            return (-1);
        }
    }

    private double det(double a, double b, double c, double d) {
        return (a * d - b * c);
    }


    public void addStore(double x, double y, int o) {
        Store s = new Store(x, y, o);
        addStore(s);
        calculateTriangulation();
//        flipEdges();
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
            if (s.x - 0.1f < x && s.x + 0.1f > x && s.y - 0.1f < y && s.y + 0.1f > y) {
            //if (s.x == x && s.y == y) {
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
