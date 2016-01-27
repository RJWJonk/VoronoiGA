
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author s080440
 */
public class VoronoiGame extends JFrame {

//    private final int MENU_MAIN = 0;
//    private final int MENU_GAME = 1;
//    private final int MENU_HOWTO = 2;
//    private final int MENU_ALGO = 3;
//    private final int INPUT_MOUSE = 0;
//    private final int INPUT_KEYBOARD = 1;
    private JPanel activePanel;

    private JPanel mainPanel;
    private JPanel howtoPanel;
    private JPanel algoPanel;
    private JPanel gamePanel;

    public static void main(String[] args) {
        new VoronoiGame();
        //Test git commit
    }

    public VoronoiGame() {
        createGUI();
        setVisible(true);
        forceRepaints(100);
    }

    private void forceRepaints(int time) {
        Timer t = new Timer(time, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                VoronoiGame.this.update();
            }
        });
        t.start();
    }

    public void update() {
        repaint();
    }

    private void createGUI() {
        setResizable(false);
        setSize(600 + 6, 600 + 28);
        setTitle("Voronoi Game");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setIgnoreRepaint(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        mainPanel = new MainMenu();
        howtoPanel = new HowTo();
        algoPanel = new AlgoPanel();
        //gamePanel = new GameBoardPanel();

        activePanel = mainPanel;
        this.add(activePanel);

        //addKeyListener(this);
        repaint();
    }

    private class MainMenu extends JPanel {

        JButton howtoButton;
        JButton algoButton;
        JButton gameButton;

        Dimension preferred = new Dimension(600, 600);

        private MainMenu() {
            this.setLayout(null);
            this.setPreferredSize(preferred);

            howtoButton = new JButton("How to play");
            howtoButton.setBounds(250, 200, 100, 40);
            howtoButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    VoronoiGame.this.remove(activePanel);
                    activePanel = howtoPanel;
                    VoronoiGame.this.add(activePanel);
                    VoronoiGame.this.validate();
                    //VoronoiGame.this.setSize(606,628);
                }
            });
            algoButton = new JButton("Algorithm");
            algoButton.setBounds(250, 250, 100, 40);
            algoButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    activePanel.getParent().remove(activePanel);
                    VoronoiGame.this.activePanel = algoPanel;
                    VoronoiGame.this.add(activePanel);
                    VoronoiGame.this.validate();
                }
            });
            gameButton = new JButton("Start game");
            gameButton.setBounds(250, 300, 100, 40);
            gameButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    activePanel.getParent().remove(activePanel);
                    activePanel = new GameBoardPanel();
                    VoronoiGame.this.add(activePanel);
                    VoronoiGame.this.validate();
                }
            });
            this.add(howtoButton);
            this.add(algoButton);
            this.add(gameButton);
        }

    }

    private class HowTo extends JPanel {

        JButton backButton;

        String[] text = new String[]{
            "This game is played on a 16*16 gameboard where up to 2 players can\n "
            + "place their stores in turn. Every store generates an area around it\n "
            + "such that every point inside this area is closest to the store.\n\n "
            + "The goal of the game is to conquer more surface area than the opponent:\n "
            + "clients are indifferent and go to the nearest store available.\n "
            + "Both players are allowed to place 8 stores on the playing field, after\n "
            + "which the game ends."};

        Dimension preferred = new Dimension(600, 600);

        private HowTo() {
            this.setLayout(null);
            this.setPreferredSize(preferred);

            backButton = new JButton("Back");
            backButton.setBounds(250, 500, 100, 40);
            backButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    activePanel.getParent().remove(activePanel);
                    activePanel = mainPanel;
                    VoronoiGame.this.add(activePanel);
                    VoronoiGame.this.validate();
                }
            });
            this.add(backButton);
        }

        @Override
        public void paint(Graphics gr) {
            super.paint(gr);
            Graphics2D g = (Graphics2D) gr;
            Font font = new Font("Times New Roman", Font.BOLD, 15);
            g.setFont(font);
            int h = 0;
            for (String s : text[0].split("\n")) {
                g.drawString(s, 30, 120 + h * g.getFontMetrics().getHeight());
                h++;
            }
        }

    }

    private class AlgoPanel extends JPanel {

        JButton backButton;
        JButton nextButton;
        JButton prevButton;

        String[] text = new String[]{
            "              Geometric Concept\n \n "
            + "A Voronoi diagram is a partitioning of a plane into regions based on distance\n "
            + "to vertices (stores) that were placed on the plane. For every store placed on\n "
            + "the playing field, we define a region such that for every point in that region,\n "
            + "the store in that region is the closest store. These regions are called Voronoi\n "
            + "cells and what we calculate are exactly the areas of these Voronoi cells.",
            " Another interesting concept of this game is Delaunay triangulation, because the\n "
            + "Voronoi diagram of a set of stores is dual to its Delaunay triangulation.\n\n "
            + "A Delaunay triangulation for a set of stores in a plane is a specific triangulation\n "
            + "in which there are no points in the plane which is inside the circumcircle of any\n "
            + "triangle in the Delaunay triangulation. To avoid skinny triangles, Delaunay\n "
            + "triangulations maximize the minimum angle of all the angles of the triangles in the\n "
            + "triangulation. This is done by flipping edges that do not satisfy this property until\n "
            + "no such ‘illegal’ edges remain in the triangulation.",
            "              Underlying algorithms/data structures\n\n "
            + "We use:\n "
            + "✭ An ArrayList to store the stores;\n "
            + "✭ A tree to store the triangles, each node contains the triangle’s vertex,\n "
            + "neighbors (in case of leaf) and an ArrayList of its children.\n\n"
            + "To implement the Delaunay triangulation, a incremental construction with search\n "
            + "structure was used. At first, we set a big triangle on the plane which is bigger\n "
            + "than the gameboard and then triangulate the stores one by one in input order. To\n "
            + "transform the triangulation to a Delaunay triangulation, we implemented the edge\n "
            + "flipping function to check if the current neighbors contain no illegal edges and,\n "
            + "if so, flip the edges. Then we continue to check the new neighbors untill no\n "
            + "edges need to be flipped.",
            "\n\n After finished the Delaunay triangulation, the Voronoi diagram was implemented\n "
            + "to partition the plane. Firstly, we calculate the Delauney center of an arbitrary\n "
            + "leaf in the structure. We draw lines to the Delauney centers of its neighbouring\n "
            + "triangles and incrementally use the contruction of the Voronoi diagram to calculate\n "
            + "the area owned by each player."};

        int displayText = 0;

        Dimension preferred = new Dimension(600, 600);

        private AlgoPanel() {
            this.setLayout(null);
            this.setPreferredSize(preferred);

            backButton = new JButton("Back");
            backButton.setBounds(250, 500, 100, 30);
            backButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    VoronoiGame.this.remove(activePanel);
                    VoronoiGame.this.activePanel = mainPanel;
                    VoronoiGame.this.add(activePanel);
                    VoronoiGame.this.validate();
                }
            });
            this.add(backButton);

            nextButton = new JButton("Next >");
            nextButton.setBounds(330, 380, 100, 30);
            nextButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    displayText = (displayText + 1) % text.length;
                }
            });
            this.add(nextButton);

            prevButton = new JButton("< Previous");
            prevButton.setBounds(170, 380, 100, 30);
            prevButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    displayText = displayText > 0 ? (displayText - 1) : text.length - 1;
                }
            });
            this.add(prevButton);
        }

        @Override
        public void paint(Graphics gr) {
            super.paint(gr);
            Graphics2D g = (Graphics2D) gr;
            Font font = new Font("Times New Roman", Font.BOLD, 15);
            g.setFont(font);
            int h = 0;
            for (String s : text[displayText].split("\n")) {
                g.drawString(s, 30, 120 + h * g.getFontMetrics().getHeight());
                h++;
            }
        }

    }

    private class GameBoardPanel extends JPanel implements MouseMotionListener, MouseListener {

        private GameBoard board;

        JButton backButton;
        //gameboard offsets and size
        int bx = 150;
        int by = 150;
        int bs = 300;
        int size = 15; //max size 30
        int partsize = bs / size;

        //hovered spot
        int hx;
        int hy;

        //player turn
        int turn = 0;

        //player budgets
        int budget1 = 8;
        int budget2 = budget1;

        //player area
        double[] area = new double[2];

        Dimension preferred = new Dimension(600, 600);

        private GameBoardPanel() {

            this.setLayout(null);
            this.setPreferredSize(preferred);

            backButton = new JButton("Back");
            backButton.setBounds(250, 500, 100, 40);
            backButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    activePanel.getParent().remove(activePanel);
                    VoronoiGame.this.activePanel = mainPanel;
                    VoronoiGame.this.add(activePanel);
                    VoronoiGame.this.validate();
                }
            });
            this.add(backButton);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);

            board = new GameBoard();
        }

        @Override
        public void paint(Graphics gr) {
            super.paint(gr);
            Graphics2D g = (Graphics2D) gr;

            int height = 80;
            int width = 120;

            //draw player 1 score
            Font f = new Font("Times New Roman", Font.BOLD, 15);
            g.setFont(f);
            int x = 20;
            int y = 20;
            g.drawLine(x, y, x + width, y);
            g.drawLine(x, y, x, y + height);
            g.drawLine(x + width, y, x + width, y + height);
            g.drawLine(x, y + height, x + width, y + height);
            g.drawLine(x, y + height / 4, x + width, y + height / 4);
            g.drawLine(x + width / 2, y + height / 4, x + width / 2, y + height);
            g.drawString("Player 1", x + 40, y + 15);
            f = new Font("Times New Roman", Font.BOLD, 25);
            g.setFont(f);
            g.drawString("$" + budget1, x + 10, y + 55);
            g.drawString((int) (100 * area[0] / (area[0] + area[1])) + "%", x + 65, y + 55);

            //draw player 2 score
            f = new Font("Times New Roman", Font.BOLD, 15);
            g.setFont(f);
            x = 460;
            y = 20;
            g.drawLine(x, y, x + width, y);
            g.drawLine(x, y, x, y + height);
            g.drawLine(x + width, y, x + width, y + height);
            g.drawLine(x, y + height, x + width, y + height);
            g.drawLine(x, y + height / 4, x + width, y + height / 4);
            g.drawLine(x + width / 2, y + height / 4, x + width / 2, y + height);
            g.drawString("Player 2", x + 40, y + 15);
            f = new Font("Times New Roman", Font.BOLD, 25);
            g.setFont(f);
            g.drawString("$" + budget2, x + 10, y + 55);
            g.drawString((int) (100 * area[1] / (area[0] + area[1])) + "%", x + 65, y + 55);

            //draw gameboard
            width = bs;
            height = bs;
            for (int i = 0; i <= size; i++) {
                g.drawLine(bx, by + (height * i) / size, bx + width, by + (height * i) / size);
                g.drawLine(bx + (width * i) / size, by, bx + (width * i) / size, by + height);
            }
            g.setColor(new Color(200, 200, 0, 50));
            g.fillRect(bx - 10, by - 10, width + 20, height + 20);

            g.setColor(new Color(150, 0, 200, 150));
            Shape s = new Ellipse2D.Double(hx - 5, hy - 5, 10, 10);
            g.fill(s);

            for (Store store : board.getStores()) {
                if (store.owner == 0) {
                    g.setColor(new Color(255, 0, 0, 150));
                } else {
                    g.setColor(new Color(255, 0, 255, 150));
                }

                s = new Ellipse2D.Double(store.x - 5, store.y - 5, 10, 10);
                g.fill(s);
            }

            //draw triangulation
            g.setColor(new Color(0, 0, 255, 255));
            Triangle t = board.triangulation;
            if (t != null) {

                //g.draw(t.toPolygon());
                ArrayList<Triangle> todo = new ArrayList<>();
                todo.add(t);
                while (!todo.isEmpty()) {
                    t = todo.get(0);
                    todo.remove(t);
                    for (Triangle tn : t.children) {
                        if (tn.isLeaf()) {

                            Polygon p = tn.toPolygon();
                            Store sd = board.findCenter(tn);
//                            Store n0 = board.findCenter(tn.n0);
//                            Store n1 = board.findCenter(tn.n1);
//                            Store n2 = board.findCenter(tn.n2);
                            s = new Ellipse2D.Double(sd.x - 5, sd.y - 5, 10, 10);
                            //g.draw(p);
                            //g.draw(s);
//                            g.drawLine((int)n0.x, (int)n0.y, (int)sd.x, (int)sd.y);
//                            g.drawLine((int)n1.x, (int)n1.y, (int)sd.x, (int)sd.y);
//                            g.drawLine((int)n2.x, (int)n2.y, (int)sd.x, (int)sd.y);
                        } else {
                            todo.add(tn);
                        }
                    }
                }
            }

            BasicStroke stroke = new BasicStroke(2);
            g.setStroke(stroke);
            g.setColor(new Color(255, 0, 0, 50));
            //draw the voronoi
            area[0] = 0;
            area[1] = 0;
            Triangle leaf = board.triangulation;
            if (leaf != null) {
                while (!leaf.isLeaf()) {
                    leaf = leaf.children.get(0);
                }

                ArrayList<Triangle> todo = new ArrayList<>();
                HashSet<Triangle> done = new HashSet<>();
                HashMap<Store, ArrayList<Store>> polygonmap = new HashMap<>(32);

                board.getStores().sort((Store s1, Store s2) -> (int) ((s1.x - bx) * (s1.x - bx) + (s1.y - by) * (s1.y - by) - ((s2.x - bx) * (s2.x - bx) + (s2.y - by) * (s2.y - by))));
                if (!polygonmap.containsKey(board.getStores().get(0))) {
                    polygonmap.put(board.getStores().get(0), new ArrayList<>());
                }
                polygonmap.get(board.getStores().get(0)).add(new Store(bx+0.001d, by+0.001d, -1));

                
                board.getStores().sort((Store s1, Store s2) -> (int) ((s1.x - (bx + bs)) * (s1.x - (bx + bs)) + (s1.y - by) * (s1.y - by) - ((s2.x - (bx + bs)) * (s2.x - (bx + bs)) + (s2.y - by) * (s2.y - by))));
                if (!polygonmap.containsKey(board.getStores().get(0))) {
                    polygonmap.put(board.getStores().get(0), new ArrayList<>());
                }
                polygonmap.get(board.getStores().get(0)).add(new Store((bx + bs)-0.001d, by+0.001d, -1));
                
                
                board.getStores().sort((Store s1, Store s2) -> (int) ((s1.x - (bx + bs)) * (s1.x - (bx + bs)) + (s1.y - (by + bs)) * (s1.y - (by + bs)) - ((s2.x - (bx + bs)) * (s2.x - (bx + bs)) + (s2.y - (by + bs)) * (s2.y - (by + bs)))));
                if (!polygonmap.containsKey(board.getStores().get(0))) {
                    polygonmap.put(board.getStores().get(0), new ArrayList<>());
                }
                polygonmap.get(board.getStores().get(0)).add(new Store((bx + bs)-0.005d, (by + bs)-.005d, -1));
                
                
                board.getStores().sort((Store s1, Store s2) -> (int) ((s1.x - bx) * (s1.x - bx) + (s1.y - (by + bs)) * (s1.y - (by + bs)) - ((s2.x - bx) * (s2.x - bx) + (s2.y - (by + bs)) * (s2.y - (by + bs)))));
                if (!polygonmap.containsKey(board.getStores().get(0))) {
                    polygonmap.put(board.getStores().get(0), new ArrayList<>());
                }
                polygonmap.get(board.getStores().get(0)).add(new Store(bx+0.005d, (by + bs)-0.005d, -1));
                
                
                todo.add(leaf);

                //constructing and drawing voronoi diagram
                while (!todo.isEmpty()) {
                    Triangle tr = todo.get(0);
                    //System.out.println(tr);
                    todo.remove(0);
                    done.add(tr);
                    Store vor = board.findCenter(tr);
                    //g.fill(new Ellipse2D.Double(vor.x-10,vor.y-10,20,20));//draws voronoi vertices
                    Store vor0 = null, vor1 = null, vor2 = null;
                    Store vor0a = null, vor1a = null, vor2a = null;
                    int border0 = -1, border1 = -1, border2 = -1;
                    g.setColor(new Color(0, 0, 0, 60));
                    if (tr.n0 != null) {
                        vor0 = board.findCenter(tr.n0);
                        border0 = translateVoronoi(vor0, vor);
                        vor0a = new Store(vor.x, vor.y, -1);
                        if (translateVoronoi(vor0a, vor0) == -2) {
                            vor0a = null;
                        } else {
                            g.drawLine((int) vor0a.x, (int) vor0a.y, (int) vor0.x, (int) vor0.y);
                        }
                        if (!todo.contains(tr.n0) && !done.contains(tr.n0)) {
                            todo.add(tr.n0);
                        }

                    }
                    if (tr.n1 != null) {
                        vor1 = board.findCenter(tr.n1);
                        border1 = translateVoronoi(vor1, vor);
                        vor1a = new Store(vor.x, vor.y, -1);
                        if (translateVoronoi(vor1a, vor1) == -2) {
                            vor1a = null;
                        } else {
                            g.drawLine((int) vor1a.x, (int) vor1a.y, (int) vor1.x, (int) vor1.y);
                        }
                        if (!todo.contains(tr.n1) && !done.contains(tr.n1)) {
                            todo.add(tr.n1);
                        }

                    }
                    if (tr.n2 != null) {
                        vor2 = board.findCenter(tr.n2);
                        border2 = translateVoronoi(vor2, vor);
                        vor2a = new Store(vor.x, vor.y, -1);
                        if (translateVoronoi(vor2a, vor2) == -2) {
                            vor2a = null;
                        } else {
                            g.drawLine((int) vor2a.x, (int) vor2a.y, (int) vor2.x, (int) vor2.y);
                        }
                        if (!todo.contains(tr.n2) && !done.contains(tr.n2)) {
                            todo.add(tr.n2);
                        }

                    }

                    if (!polygonmap.containsKey(tr.s0)) {
                        polygonmap.put(tr.s0, new ArrayList<>());
                    }
                    if (vor0 != null && border0 != -2) {
                        polygonmap.get(tr.s0).add(vor0);
                    }
                    if (vor2 != null && border2 != -2) {
                        polygonmap.get(tr.s0).add(vor2);
                    }
                    if (vor0a != null && border0 != -2) {
                        polygonmap.get(tr.s0).add(vor0a);
                    }
                    if (vor2a != null && border2 != -2) {
                        polygonmap.get(tr.s0).add(vor2a);
                    }

                    if (!polygonmap.containsKey(tr.s1)) {
                        polygonmap.put(tr.s1, new ArrayList<>());
                    }
                    if (vor0 != null && border0 != -2) {
                        polygonmap.get(tr.s1).add(vor0);
                    }
                    if (vor1 != null && border1 != -2) {
                        polygonmap.get(tr.s1).add(vor1);
                    }
                    if (vor0a != null && border0 != -2) {
                        polygonmap.get(tr.s1).add(vor0a);
                    }
                    if (vor1a != null && border1 != -2) {
                        polygonmap.get(tr.s1).add(vor1a);
                    }

                    if (!polygonmap.containsKey(tr.s2)) {
                        polygonmap.put(tr.s2, new ArrayList<>());
                    }
                    if (vor1 != null && border1 != -2) {
                        polygonmap.get(tr.s2).add(vor1);
                    }
                    if (vor2 != null && border2 != -2) {
                        polygonmap.get(tr.s2).add(vor2);
                    }
                    if (vor1a != null && border1 != -2) {
                        polygonmap.get(tr.s2).add(vor1a);
                    }
                    if (vor2a != null && border2 != -2) {
                        polygonmap.get(tr.s2).add(vor2a);
                    }

//                    //areas (and filling with colour!)
//                    if (tr.s0.owner == 0) {
//                        g.setColor(new Color(255, 0, 0, 50));
//                        if (vor0a != null && border0 != -2) {
//                            area[0] += 1d / 8 * tr.s0.distanceTo(tr.s1) * vor0a.distanceTo(vor0);
//                            //System.out.println("Area0 + " + 1d / 8 * tr.s0.distanceTo(tr.s1) * vor0a.distanceTo(vor0));
//                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor0a.x, (int) vor0.x}, new int[]{(int) tr.s0.y, (int) vor0a.y, (int) vor0.y}, 3);
//                            g.fill(p);
//                        }
//                        if (vor2a != null && border2 != -2) {
//                            area[0] += 1d / 8 * tr.s0.distanceTo(tr.s2) * vor2a.distanceTo(vor2);
//                            //System.out.println("Area0 + " + 1d / 8 * tr.s0.distanceTo(tr.s2) * vor2a.distanceTo(vor2));
//                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor2a.x, (int) vor2.x}, new int[]{(int) tr.s0.y, (int) vor2a.y, (int) vor2.y}, 3);
//                            g.fill(p);
//                        }
//                    } else {
//                        g.setColor(new Color(0, 255, 0, 50));
//                        if (vor0a != null && border0 != -2) {
//                            area[1] += 1d / 8 * tr.s0.distanceTo(tr.s1) * vor0a.distanceTo(vor0);
//                            //System.out.println("Area1 + " + 1d / 8 * tr.s0.distanceTo(tr.s1) * vor0a.distanceTo(vor0));
//                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor0a.x, (int) vor0.x}, new int[]{(int) tr.s0.y, (int) vor0a.y, (int) vor0.y}, 3);
//                            g.fill(p);
//                        }
//                        if (vor2a != null && border2 != -2) {
//                            area[1] += 1d / 8 * tr.s0.distanceTo(tr.s2) * vor2a.distanceTo(vor2);
//                            //System.out.println("Area1 + " + 1d / 8 * tr.s0.distanceTo(tr.s2) * vor2a.distanceTo(vor2));
//                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor2a.x, (int) vor2.x}, new int[]{(int) tr.s0.y, (int) vor2a.y, (int) vor2.y}, 3);
//                            g.fill(p);
//                        }
//                    }
//
//                    if (tr.s1.owner == 0) {
//                        g.setColor(new Color(255, 0, 0, 50));
//                        if (vor1a != null && border1 != -2) {
//                            area[0] += 1d / 8 * tr.s1.distanceTo(tr.s2) * vor1a.distanceTo(vor1);
//                            //System.out.println("Area0 + " + 1d / 8 * tr.s1.distanceTo(tr.s2) * vor1a.distanceTo(vor1));
//                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor1a.x, (int) vor1.x}, new int[]{(int) tr.s1.y, (int) vor1a.y, (int) vor1.y}, 3);
//                            g.fill(p);
//                        }
//                        if (vor0a != null && border0 != -2) {
//                            area[0] += 1d / 8 * tr.s1.distanceTo(tr.s0) * vor0a.distanceTo(vor0);
//                            //System.out.println("Area0 + " + 1d / 8 * tr.s1.distanceTo(tr.s0) * vor0a.distanceTo(vor2));
//                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor0a.x, (int) vor0.x}, new int[]{(int) tr.s1.y, (int) vor0a.y, (int) vor0.y}, 3);
//                            g.fill(p);
//                        }
//                    } else {
//                        g.setColor(new Color(0, 255, 0, 50));
//                        if (vor1a != null && border1 != -2) {
//                            area[1] += 1d / 8 * tr.s1.distanceTo(tr.s2) * vor1a.distanceTo(vor1);
//                            //System.out.println("Area1 + " + 1d / 8 * tr.s1.distanceTo(tr.s2) * vor1a.distanceTo(vor1));
//                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor1a.x, (int) vor1.x}, new int[]{(int) tr.s1.y, (int) vor1a.y, (int) vor1.y}, 3);
//                            g.fill(p);
//                        }
//                        if (vor0a != null && border0 != -2) {
//                            area[1] += 1d / 8 * tr.s1.distanceTo(tr.s0) * vor0a.distanceTo(vor0);
//                            //System.out.println("Area1 + " + 1d / 8 * tr.s1.distanceTo(tr.s0) * vor0a.distanceTo(vor2));
//                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor0a.x, (int) vor0.x}, new int[]{(int) tr.s1.y, (int) vor0a.y, (int) vor0.y}, 3);
//                            g.fill(p);
//                        }
//                    }
//
//                    if (tr.s2.owner == 0) {
//                        g.setColor(new Color(255, 0, 0, 50));
//                        if (vor2a != null && border2 != -2) {
//                            area[0] += 1d / 8 * tr.s2.distanceTo(tr.s0) * vor2a.distanceTo(vor2);
//                            //System.out.println("Area0 + " + 1d / 8 * tr.s2.distanceTo(tr.s0) * vor2a.distanceTo(vor1));
//                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor2a.x, (int) vor2.x}, new int[]{(int) tr.s2.y, (int) vor2a.y, (int) vor2.y}, 3);
//                            g.fill(p);
//                        }
//                        if (vor1a != null && border1 != -2) {
//                            area[0] += 1d / 8 * tr.s2.distanceTo(tr.s1) * vor1a.distanceTo(vor1);
//                            //System.out.println("Area0 + " + 1d / 8 * tr.s2.distanceTo(tr.s1) * vor1a.distanceTo(vor2));
//                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor1a.x, (int) vor1.x}, new int[]{(int) tr.s2.y, (int) vor1a.y, (int) vor1.y}, 3);
//                            g.fill(p);
//                        }
//                    } else {
//                        g.setColor(new Color(0, 255, 0, 50));
//                        if (vor2a != null && border2 != -2) {
//                            area[1] += 1d / 8 * tr.s2.distanceTo(tr.s0) * vor2a.distanceTo(vor2);
//                            //System.out.println("Area1 + " + 1d / 8 * tr.s2.distanceTo(tr.s0) * vor2a.distanceTo(vor1));
//                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor2a.x, (int) vor2.x}, new int[]{(int) tr.s2.y, (int) vor2a.y, (int) vor2.y}, 3);
//                            g.fill(p);
//                        }
//                        if (vor1a != null && border1 != -2) {
//                            area[1] += 1d / 8 * tr.s2.distanceTo(tr.s1) * vor1a.distanceTo(vor1);
//                            //System.out.println("Area1 + " + 1d / 8 * tr.s2.distanceTo(tr.s1) * vor1a.distanceTo(vor2));
//                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor1a.x, (int) vor1.x}, new int[]{(int) tr.s2.y, (int) vor1a.y, (int) vor1.y}, 3);
//                            g.fill(p);
//                        }
//                    }//end areas
                    //System.out.println("END AREA: Area0 = " + area0 + " AND Area1 = " + area1);
//                    System.out.println(border0);
//                    System.out.println(border1);
//                    System.out.println(border2);
//                    //g.setColor(new Color(0, 0, 255, 150));
//                    if (border0 == 0 && border2 == 0) {
//                        if (tr.s0.owner == 0) {
//                            area0 += 1 / 2 * (tr.s0.x - bx) * Math.abs(vor0.y - vor2.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor0.x, (int) vor2.x}, new int[]{(int) tr.s0.y, (int) vor0.y, (int) vor2.y}, 3);
//                            g.setColor(new Color(255, 0, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        } else {
//                            area1 += 1 / 2 * (tr.s0.x - bx) * Math.abs(vor0.y - vor2.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor0.x, (int) vor2.x}, new int[]{(int) tr.s0.y, (int) vor0.y, (int) vor2.y}, 3);
//                            g.setColor(new Color(0, 255, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        }
//                    }
//                    if (border1 == 0 && border2 == 0) {
//                        if (tr.s2.owner == 0) {
//                            area0 += 1 / 2 * (tr.s2.x - bx) * Math.abs(vor1.y - vor2.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor1.x, (int) vor2.x}, new int[]{(int) tr.s2.y, (int) vor1.y, (int) vor2.y}, 3);
//                            g.setColor(new Color(255, 0, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        } else {
//                            area1 += 1 / 2 * (tr.s2.x - bx) * Math.abs(vor1.y - vor2.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor1.x, (int) vor2.x}, new int[]{(int) tr.s2.y, (int) vor1.y, (int) vor2.y}, 3);
//                            g.setColor(new Color(0, 255, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        }
//                    }
//                    if (border0 == 0 && border1 == 0) {
//                        if (tr.s1.owner == 0) {
//                            area0 += 1 / 2 * (tr.s1.x - bx) * Math.abs(vor0.y - vor1.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor0.x, (int) vor1.x}, new int[]{(int) tr.s1.y, (int) vor0.y, (int) vor1.y}, 3);
//                            g.setColor(new Color(255, 0, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        } else {
//                            area1 += 1 / 2 * (tr.s1.x - bx) * Math.abs(vor0.y - vor1.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor0.x, (int) vor1.x}, new int[]{(int) tr.s1.y, (int) vor0.y, (int) vor1.y}, 3);
//                            g.setColor(new Color(0, 255, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        }
//                    }
//
//                    if (border0 == 2 && border2 == 2) {
//                        if (tr.s0.owner == 0) {
//                            area0 += 1 / 2 * (bx+bs-tr.s0.x) * Math.abs(vor0.y - vor2.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor0.x, (int) vor2.x}, new int[]{(int) tr.s0.y, (int) vor0.y, (int) vor2.y}, 3);
//                            g.setColor(new Color(255, 0, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        } else {
//                            area1 += 1 / 2 * (bx+bs-tr.s0.x) * Math.abs(vor0.y - vor2.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor0.x, (int) vor2.x}, new int[]{(int) tr.s0.y, (int) vor0.y, (int) vor2.y}, 3);
//                            g.setColor(new Color(0, 255, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        }
//                    }
//                    if (border1 == 2 && border2 == 2) {
//                        if (tr.s2.owner == 0) {
//                            area0 += 1 / 2 * (bx+bs-tr.s2.x) * Math.abs(vor1.y - vor2.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor1.x, (int) vor2.x}, new int[]{(int) tr.s2.y, (int) vor1.y, (int) vor2.y}, 3);
//                            g.setColor(new Color(255, 0, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        } else {
//                            area1 += 1 / 2 * (bx+bs-tr.s2.x) * Math.abs(vor1.y - vor2.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor1.x, (int) vor2.x}, new int[]{(int) tr.s2.y, (int) vor1.y, (int) vor2.y}, 3);
//                            g.setColor(new Color(0, 255, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        }
//                    }
//                    if (border0 == 2 && border1 == 2) {
//                        if (tr.s1.owner == 0) {
//                            area0 += 1 / 2 * (bx+bs-tr.s1.x) * Math.abs(vor0.y - vor1.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor0.x, (int) vor1.x}, new int[]{(int) tr.s1.y, (int) vor0.y, (int) vor1.y}, 3);
//                            g.setColor(new Color(255, 0, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        } else {
//                            area1 += 1 / 2 * (bx+bs-tr.s1.x) * Math.abs(vor0.y - vor1.y);
//                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor0.x, (int) vor1.x}, new int[]{(int) tr.s1.y, (int) vor0.y, (int) vor1.y}, 3);
//                            g.setColor(new Color(0, 255, 0, 50));
//                            g.fill(p);
//                            g.fill(p);
//                        }
//                    }
                }//end of todo

                //draw polygons..
                Set<Store> stores = polygonmap.keySet();

                Color[] colors = new Color[]{new Color(255, 0, 0, 100), new Color(0, 255, 0, 100)};
                for (Store ss : stores) {
                    if (ss.owner != -1) {
                        g.setColor(colors[ss.owner]);
                        ArrayList<Store> slist = polygonmap.get(ss);
                        slist.sort((Store s1, Store s2) -> (int) ((s1.x * s1.x * s1.y) - (s2.x * s2.x * s2.y)));

                        //remove duplicate vertices
                        Store prev = null;
                        for (Iterator<Store> iter = slist.iterator(); iter.hasNext();) {
                            Store now = iter.next();
                            if (prev != null) {
                                if (now.y - 0.01d <= prev.y && prev.y <= now.y + 0.01d && now.x - 0.01d <= prev.x && prev.x <= now.x + 0.01d) {
                                    iter.remove();
                                }
                            }
                            prev = now;
                        }
                        slist.sort((Store s1, Store s2) -> (int) (s1.y - s2.y));

//                        if (slist.size() < 3) {
//                            System.out.println("ERROR LIST SIZE");
//                            break;
//                        }
                        Triangle triangle = null;
                        if (slist.size() >= 3) {
                            triangle = new Triangle(slist.get(0), slist.get(0), slist.get(1));
                        }
                        System.out.println("=====");
                        for (Store sss : slist) {
                            System.out.println(sss);
                        }

                        for (int i = 2; i < slist.size(); i++) {
                            Store left = triangle.s0.x < triangle.s1.x
                                    ? (triangle.s2.x < triangle.s0.x ? triangle.s2 : triangle.s0)
                                    : (triangle.s2.x < triangle.s1.x ? triangle.s2 : triangle.s1);
                            Store right = triangle.s0.x > triangle.s1.x
                                    ? (triangle.s2.x > triangle.s0.x ? triangle.s2 : triangle.s0)
                                    : (triangle.s2.x > triangle.s1.x ? triangle.s2 : triangle.s1);
                            Store middle = triangle.s0.x > left.x && triangle.s0.x < right.x ? triangle.s0
                                    : triangle.s1.x > left.x && triangle.s1.x < right.x ? triangle.s1 : triangle.s2;
                            Store news = slist.get(i);

                            Line2D l1, l2;

                            l1 = new Line2D.Double(left.x, left.y, right.x, right.y);
                            l2 = new Line2D.Double(middle.x, middle.y, news.x, news.y);
                            if (l1.intersectsLine(l2)) {
                                triangle = new Triangle(left, right, news);
                            }
                            l1 = new Line2D.Double(left.x, left.y, middle.x, middle.y);
                            l2 = new Line2D.Double(right.x, right.y, news.x, news.y);
                            if (l1.intersectsLine(l2)) {
                                triangle = new Triangle(left, middle, news);
                            }
                            l1 = new Line2D.Double(middle.x, middle.y, right.x, right.y);
                            l2 = new Line2D.Double(left.x, left.y, news.x, news.y);
                            if (l1.intersectsLine(l2)) {
                                triangle = new Triangle(middle, right, news);
                            }

                            area[ss.owner] += triangle.calculateArea();
                            //System.out.println(triangle.calculateArea());
                            g.fill(triangle.toPolygon());
                        }

                    }
                }

            }
//            System.out.println("==AREAS==");
//            System.out.println(area0);
//            System.out.println(area1);
            g.setStroke(new BasicStroke());
        }

        private int translateXVoronoi(Store s, Store ref) {
            double a = (ref.y - s.y) / (ref.x - s.x);

            if (s.x < bx) {
                s.y = s.y + a * (bx - s.x);
                s.x = bx;
                return 0;
            }
            if (s.x > bx + bs) {
                s.y = s.y + a * ((bx + bs) - s.x);
                s.x = (bx + bs);
                return 1;
            }
            return -1;
        }

        //x within bounds, stays within bounds!
        private int translateYVoronoi(Store s, Store ref) {
            int retval = -1;
            double a = (ref.x - s.x) / (ref.y - s.y);

            if (s.y < by) {
                s.x = s.x + a * (by - s.y);
                s.y = by;
                retval = 0;
            }
            if (s.y > by + bs) {
                s.x = s.x + a * ((by + bs) - s.y);
                s.y = (by + bs);
                retval = 1;
            }

            if (s.x < bx - 1 || s.x > bx + bs + 1) {
                retval = -2;
            }
            return retval;
        }

        private int translateVoronoi(Store s, Store ref) {
            int r1 = translateXVoronoi(s, ref);
            int r2 = translateYVoronoi(s, ref);
            if (r2 == -2) {
                return -2;
            }

            if (r2 == 0) {
                return 3;
            }
            if (r2 == 1) {
                return 1;
            }
            if (r2 == -1 && r1 == 0) {
                return 0;
            }
            if (r2 == -1 && r1 == 1) {
                return 2;
            }

            return -1;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //do nothing
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int mx = e.getX();
            int my = e.getY();
            if (mx >= bx - 5 && mx <= bx + bs + 5
                    & my >= by - 5 && my <= by + bs + 5) {
                hx = bx;
                hy = by;
                while (hx < mx - partsize / 2) {
                    hx += partsize;
                }
                while (hy < my - partsize / 2) {
                    hy += partsize;
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //do nothing
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (board.getStoreAt(hx, hy) == null) {
                Random random = new Random();
                if (turn == 0 && budget1 > 0) {
                    board.addStore(hx + (random.nextDouble() - 0.5f) / 999999, hy + (random.nextDouble() - 0.5f) / 999999, turn);
                    budget1 -= 1;
                } else if (turn == 1 && budget2 > 0) {
                    board.addStore(hx + (random.nextDouble() - 0.5f) / 999999, hy + (random.nextDouble() - 0.5f) / 999999, turn);
                    budget2 -= 1;
                }
                turn = (turn + 1) % 2;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //do nothing
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //do nothing
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //do nothing
        }

    }

}
