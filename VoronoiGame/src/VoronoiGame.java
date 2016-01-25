
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
import java.util.HashSet;
import java.util.Random;
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

    private final int MENU_MAIN = 0;
    private final int MENU_GAME = 1;
    private final int MENU_OPTIONS = 2;
    private final int MENU_CREDITS = 3;

    private final int INPUT_MOUSE = 0;
    private final int INPUT_KEYBOARD = 1;

    private JPanel activePanel;

    private JPanel mainPanel;
    private JPanel optionsPanel;
    private JPanel creditsPanel;
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
        optionsPanel = new OptionsMenu();
        creditsPanel = new CreditsMenu();
        //gamePanel = new GameBoardPanel();

        activePanel = mainPanel;
        this.add(activePanel);

        //addKeyListener(this);
        repaint();
    }

    private class MainMenu extends JPanel {

        JButton optionsButton;
        JButton creditsButton;
        JButton gameButton;

        Dimension preferred = new Dimension(600, 600);

        private MainMenu() {
            this.setLayout(null);
            this.setPreferredSize(preferred);

            optionsButton = new JButton("Options");
            optionsButton.setBounds(250, 200, 100, 40);
            optionsButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    VoronoiGame.this.remove(activePanel);
                    activePanel = optionsPanel;
                    VoronoiGame.this.add(activePanel);
                    VoronoiGame.this.validate();
                    //VoronoiGame.this.setSize(606,628);
                }
            });
            creditsButton = new JButton("Credits");
            creditsButton.setBounds(250, 250, 100, 40);
            creditsButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    activePanel.getParent().remove(activePanel);
                    VoronoiGame.this.activePanel = creditsPanel;
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
            this.add(optionsButton);
            this.add(creditsButton);
            this.add(gameButton);
        }

    }

    private class OptionsMenu extends JPanel {

        JButton backButton;

        Dimension preferred = new Dimension(600, 600);

        private OptionsMenu() {
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

    }

    private class CreditsMenu extends JPanel {

        JButton backButton;

        Dimension preferred = new Dimension(600, 600);

        private CreditsMenu() {
            this.setLayout(null);
            this.setPreferredSize(preferred);

            backButton = new JButton("Back");
            backButton.setBounds(250, 500, 100, 40);
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
        }

    }

    private class GameBoardPanel extends JPanel implements MouseMotionListener, MouseListener {

        private GameBoard board;

        JButton backButton;
        //gameboard offsets and size
        int bx = 150;
        int by = 150;
        int bs = 300;
        int size = 10; //max size 30
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
        double area0 = 1;
        double area1 = 1;

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
            g.drawString((int) (100 * area0 / (area0 + area1)) + "%", x + 65, y + 55);

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
            g.drawString((int) (100 * area1 / (area0 + area1)) + "%", x + 65, y + 55);

            //draw gameboard
            width = bs;
            height = bs;
            for (int i = 0; i <= size; i++) {
                g.drawLine(bx, by + (height * i) / size, bx + width, by + (height * i) / size);
                g.drawLine(bx + (width * i) / size, by, bx + (width * i) / size, by + height);
            }
            g.setColor(new Color(200, 200, 0, 50));
            g.fillRect(bx - 10, by - 10, width + 20, height + 20);

            //draw triangulation
            g.setColor(new Color(0, 0, 0, 255));
            BasicStroke stroke = new BasicStroke(3);
            g.setStroke(stroke);
            for (GameBoard.Triangle t : board.geTriangulation()) {
                Polygon p = t.toPolygon();
                g.draw(p);
            }
            g.setStroke(new BasicStroke());

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

            g.setColor(new Color(0, 0, 255, 255));
            GameBoard.Triangle t = board.triangulation3;
            if (t != null) {

                //g.draw(t.toPolygon());
                ArrayList<GameBoard.Triangle> todo = new ArrayList<>();
                todo.add(t);
                while (!todo.isEmpty()) {
                    t = todo.get(0);
                    todo.remove(t);
                    for (GameBoard.Triangle tn : t.children) {
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

            stroke = new BasicStroke(2);
            g.setStroke(stroke);
            g.setColor(new Color(255, 0, 0, 50));
            //draw the voronoi
            area0 = 0;
            area1 = 0;
            GameBoard.Triangle leaf = board.triangulation3;
            if (leaf != null) {
                while (!leaf.isLeaf()) {
                    leaf = leaf.children.get(0);
                }

                ArrayList<GameBoard.Triangle> todo = new ArrayList<>();
                HashSet<GameBoard.Triangle> done = new HashSet<>();

                todo.add(leaf);

                while (!todo.isEmpty()) {
                    GameBoard.Triangle tr = todo.get(0);
                    //System.out.println(tr);
                    todo.remove(0);
                    done.add(tr);
                    Store vor = board.findCenter(tr);
                    Store vor0 = null, vor1 = null, vor2 = null;
                    Store vor0a = null, vor1a = null, vor2a = null;
                    if (tr.n0 != null) {
                        vor0 = board.findCenter(tr.n0);
                        translateVoronoi(vor0, vor);
                        vor0a = new Store(vor.x, vor.y, -1);
                        translateVoronoi(vor0a, vor0);
                        if (!todo.contains(tr.n0) && !done.contains(tr.n0)) {
                            todo.add(tr.n0);
                        }
                        g.drawLine((int) vor0a.x, (int) vor0a.y, (int) vor0.x, (int) vor0.y);
                    }
                    if (tr.n1 != null) {
                        vor1 = board.findCenter(tr.n1);
                        translateVoronoi(vor1, vor);
                        vor1a = new Store(vor.x, vor.y, -1);
                        translateVoronoi(vor1a, vor1);
                        if (!todo.contains(tr.n1) && !done.contains(tr.n1)) {
                            todo.add(tr.n1);
                        }
                        g.drawLine((int) vor1a.x, (int) vor1a.y, (int) vor1.x, (int) vor1.y);
                    }
                    if (tr.n2 != null) {
                        vor2 = board.findCenter(tr.n2);
                        translateVoronoi(vor2, vor);
                        vor2a = new Store(vor.x, vor.y, -1);
                        translateVoronoi(vor2a, vor2);
                        if (!todo.contains(tr.n2) && !done.contains(tr.n2)) {
                            todo.add(tr.n2);
                        }
                        g.drawLine((int) vor2a.x, (int) vor2a.y, (int) vor2.x, (int) vor2.y);
                    }

//                    if ((vor0 != null && vor0.x > bx + bs) || (vor1 != null && vor1.x > bx + bs) || (vor2 != null && vor2.x > bx + bs)) {
//                        System.out.println("voru problem!");
//                    }
                    //areas
                    if (tr.s0.owner == 0) {
                        g.setColor(new Color(255, 0, 0, 50));
                        if (vor0 != null) {
                            area0 += 1d / 8 * tr.s0.distanceTo(tr.s1) * vor0a.distanceTo(vor0);
                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor0a.x, (int) vor0.x}, new int[]{(int) tr.s0.y, (int) vor0a.y, (int) vor0.y}, 3);
                            g.fill(p);
                        }
                        if (vor2 != null) {
                            area0 += 1d / 8 * tr.s0.distanceTo(tr.s2) * vor2a.distanceTo(vor2);
                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor2a.x, (int) vor2.x}, new int[]{(int) tr.s0.y, (int) vor2a.y, (int) vor2.y}, 3);
                            g.fill(p);
                        }
                    } else {
                        g.setColor(new Color(0, 255, 0, 50));
                        if (vor0 != null) {
                            area1 += 1d / 8 * tr.s0.distanceTo(tr.s1) * vor0a.distanceTo(vor0);
                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor0a.x, (int) vor0.x}, new int[]{(int) tr.s0.y, (int) vor0a.y, (int) vor0.y}, 3);
                            g.fill(p);
                        }
                        if (vor2 != null) {
                            area1 += 1d / 8 * tr.s0.distanceTo(tr.s2) * vor2a.distanceTo(vor2);
                            Polygon p = new Polygon(new int[]{(int) tr.s0.x, (int) vor2a.x, (int) vor2.x}, new int[]{(int) tr.s0.y, (int) vor2a.y, (int) vor2.y}, 3);
                            g.fill(p);
                        }
                    }

                    if (tr.s1.owner == 0) {
                        g.setColor(new Color(255, 0, 0, 50));
                        if (vor1 != null) {
                            area0 += 1d / 8 * tr.s1.distanceTo(tr.s2) * vor1a.distanceTo(vor1);
                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor1a.x, (int) vor1.x}, new int[]{(int) tr.s1.y, (int) vor1a.y, (int) vor1.y}, 3);
                            g.fill(p);
                        }
                        if (vor0 != null) {
                            area0 += 1d / 8 * tr.s1.distanceTo(tr.s0) * vor0a.distanceTo(vor2);
                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor0a.x, (int) vor0.x}, new int[]{(int) tr.s1.y, (int) vor0a.y, (int) vor0.y}, 3);
                            g.fill(p);
                        }
                    } else {
                        g.setColor(new Color(0, 255, 0, 50));
                        if (vor1 != null) {
                            area1 += 1d / 8 * tr.s1.distanceTo(tr.s2) * vor1a.distanceTo(vor1);
                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor1a.x, (int) vor1.x}, new int[]{(int) tr.s1.y, (int) vor1a.y, (int) vor1.y}, 3);
                            g.fill(p);
                        }
                        if (vor0 != null) {
                            area1 += 1d / 8 * tr.s1.distanceTo(tr.s0) * vor0a.distanceTo(vor2);
                            Polygon p = new Polygon(new int[]{(int) tr.s1.x, (int) vor0a.x, (int) vor0.x}, new int[]{(int) tr.s1.y, (int) vor0a.y, (int) vor0.y}, 3);
                            g.fill(p);
                        }
                    }

                    if (tr.s2.owner == 0) {
                        g.setColor(new Color(255, 0, 0, 50));
                        if (vor2 != null) {
                            area0 += 1d / 8 * tr.s2.distanceTo(tr.s0) * vor2a.distanceTo(vor1);
                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor2a.x, (int) vor2.x}, new int[]{(int) tr.s2.y, (int) vor2a.y, (int) vor2.y}, 3);
                            g.fill(p);
                        }
                        if (vor1 != null) {
                            area0 += 1d / 8 * tr.s2.distanceTo(tr.s1) * vor1a.distanceTo(vor2);
                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor1a.x, (int) vor1.x}, new int[]{(int) tr.s2.y, (int) vor1a.y, (int) vor1.y}, 3);
                            g.fill(p);
                        }
                    } else {
                        g.setColor(new Color(0, 255, 0, 50));
                        if (vor2 != null) {
                            area1 += 1d / 8 * tr.s2.distanceTo(tr.s0) * vor2a.distanceTo(vor2);
                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor2a.x, (int) vor2.x}, new int[]{(int) tr.s2.y, (int) vor2a.y, (int) vor2.y}, 3);
                            g.fill(p);
                        }
                        if (vor1 != null) {
                            area1 += 1d / 8 * tr.s2.distanceTo(tr.s1) * vor1a.distanceTo(vor1);
                            Polygon p = new Polygon(new int[]{(int) tr.s2.x, (int) vor1a.x, (int) vor1.x}, new int[]{(int) tr.s2.y, (int) vor1a.y, (int) vor1.y}, 3);
                            g.fill(p);
                        }
                    }
                }
            }
//            System.out.println("==AREAS==");
//            System.out.println(area0);
//            System.out.println(area1);
            g.setStroke(new BasicStroke());
        }

        private void translateXVoronoi(Store s, Store ref) {
            double a = (ref.y - s.y) / (ref.x - s.x);

            if (s.x < bx) {
                s.y = s.y + a * (bx - s.x);
                s.x = bx;
            }
            if (s.x > bx + bs) {
                s.y = s.y + a * ((bx + bs) - s.x);
                s.x = (bx + bs);
            }
        }

        //x within bounds, stays within bounds!
        private void translateYVoronoi(Store s, Store ref) {

            double a = (ref.x - s.x) / (ref.y - s.y);

            if (s.y < by) {
                s.x = s.x + a * (by - s.y);
                s.y = by;
            }
            if (s.y > by + bs) {
                s.x = s.x + a * ((by + bs) - s.y);
                s.y = (by + bs);
            }

            if (s.x < bx || s.x > bx + bs) {
                s = null;
            }

//            double a = (ref.y - s.y) / (ref.x - s.x);
//
//            if (s.y < by) {
//                double dx = -(by - s.y) / a;
//                s.y = by;
//                s.x += dx;
//            }
//            if (s.y > by + bs) {
//                double dx = (s.y - (by + bs)) / a;
//                s.y = by + bs;
//                s.y += dx;
//            }
//
//            if (s.x < bx || s.x > bx + bs) {
//                //not in the playing field
//                s = null;
//            }
//            if (s.y < by) {
//                s.x = s.x + a * (by - s.y);
//                s.y = by;
//            }
//            if (s.y > by + bs) {
//                s.x = s.x + a * ((by + bs) - s.y);
//                s.y = (by + bs);
//            }
        }

        private void translateVoronoi(Store s, Store ref) {
            translateXVoronoi(s, ref);
            translateYVoronoi(s, ref);
        }

        private ArrayList<Point> calculatePolygon(Store s) {
            Polygon result = new Polygon();
            ArrayList<Line2D.Double> lines = new ArrayList<>();

            ArrayList<GameBoard.Edge> consideredEdges = new ArrayList();

            //find all lines
            for (GameBoard.Edge e : board.findEdgesOfStore(s)) {
                //do not consider twins again!
                if (!consideredEdges.contains(e)) {
                    if (e.twin != null) {
                        consideredEdges.add(e.twin);
                    }

                    GameBoard.Edge en = e.next;
                    double xcc = (e.s1.x + e.s2.x) / 2;
                    double ycc = (e.s1.y + e.s2.y) / 2;
                    double ac;
                    double xcn = (en.s1.x + en.s2.x) / 2;
                    double ycn = (en.s1.y + en.s2.y) / 2;
                    double an;

                    if (e.s2.x != e.s1.x && e.s2.y != e.s1.y && en.s2.x != en.s1.x && en.s2.y != en.s1.y) {
                        ac = (e.s2.y - e.s1.y) / (e.s2.x - e.s1.x);
                        ac = -1.0f / ac; //perpendicular
                        double bc = ycc - ac * xcc;

                    } else if (e.s2.y == e.s1.y) {

                    } else if (e.s2.x == e.s1.x) {

                    }

                }
            }

            if (lines.size() == 0) {
                result.addPoint(0, 0);
                result.addPoint(1, 1);
                result.addPoint(0, 1);
            }
            for (Line2D.Double l : lines) {
                //result.addPoint(0, 0);
                result.addPoint((int) l.x1, (int) l.y1);
                result.addPoint((int) l.x2, (int) l.y2);
            }

//            System.out.println(lines.get(0).x1);
//            System.out.println(lines.get(0).y1);
//            System.out.println(lines.get(0).x2);
//            System.out.println(lines.get(0).y2);
            if (lines.size() > 0) {
                //return lines.get(0);
            } else {
                //return null;
            }

            return null;
        }

//        private ArrayList<Line2D.Float> calculatePolygon(Store s) {
//            Polygon result = new Polygon();
//            ArrayList<Line2D.Float> lines = new ArrayList<>();
//
//            ArrayList<GameBoard.Edge> consideredEdges = new ArrayList();
//
//            //find all lines
//            for (GameBoard.Edge e : board.findEdgesOfStore(s)) {
//                //do not consider twins again!
//                if (!consideredEdges.contains(e)) {
//                    if (e.twin != null) {
//                        consideredEdges.add(e.twin);
//                    }
//
//                    float xc = (e.s1.x + e.s2.x) / 2;
//                    float yc = (e.s1.y + e.s2.y) / 2;
//                    float a;
//
//                    if (e.s2.x != e.s1.x && e.s2.y != e.s1.y) {
//                        a = (e.s2.y - e.s1.y) / (e.s2.x - e.s1.x);
//                        a = -1.0f / a; //perpendicular
//                        float b = yc - a * xc;
//                        //x,y = 150 and 450
//
//                        //determine where it hits borders of the board
//                        Point pl = null, pr = null, pt = null, pb = null;
//
//                        float yhit_left = a * 150 + b;
//                        float yhit_right = a * 450 + b;
//                        float xhit_top = (150 - b) / a;
//                        float xhit_bot = (450 - b) / a;
//                        if (150 <= yhit_left && yhit_left <= 450) {
//                            pl = new Point(150, (int) yhit_left);
//                        }
//                        if (150 <= yhit_right && yhit_right <= 450) {
//                            pr = new Point(450, (int) yhit_right);
//                        }
//                        if (150 <= xhit_top && xhit_top <= 450) {
//                            pt = new Point((int) xhit_top, 150);
//                        }
//                        if (150 <= xhit_bot && xhit_bot <= 450) {
//                            pb = new Point((int) xhit_bot, 450);
//                        }
//                        Line2D.Float l = findLine(pl, pr, pt, pb);
//                        lines.add(l);
//                    } else if (e.s2.y == e.s1.y) {
//                        lines.add(new Line2D.Float(new Point((int) xc, 150), new Point((int) xc, 450)));
//                    } else if (e.s2.x == e.s1.x) {
//                        lines.add(new Line2D.Float(new Point(150, (int) yc), new Point(450, (int) yc)));
//                    }
//
//                }
//            }
//
//            if (lines.size() == 0) {
//                result.addPoint(0, 0);
//                result.addPoint(1, 1);
//                result.addPoint(0, 1);
//            }
//            for (Line2D.Float l : lines) {
//                //result.addPoint(0, 0);
//                result.addPoint((int) l.x1, (int) l.y1);
//                result.addPoint((int) l.x2, (int) l.y2);
//            }
//
////            System.out.println(lines.get(0).x1);
////            System.out.println(lines.get(0).y1);
////            System.out.println(lines.get(0).x2);
////            System.out.println(lines.get(0).y2);
//            if (lines.size() > 0) {
//                //return lines.get(0);
//            } else {
//                //return null;
//            }
//
//            return lines;
//        }
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
                    board.addStore(hx + (random.nextDouble() - 0.5f) / 100, hy + (random.nextDouble() - 0.5f) / 100, turn);
                    budget1 -= 1;
                } else if (turn == 1 && budget2 > 0) {
                    board.addStore(hx + (random.nextDouble() - 0.5f) / 100, hy + (random.nextDouble() - 0.5f) / 100, turn);
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

        private Line2D.Double findLine(Point pl, Point pr, Point pt, Point pb) {
            if (pl != null) {
                if (pr != null) {
                    return new Line2D.Double(pl, pr);
                } else if (pt != null) {
                    return new Line2D.Double(pl, pt);
                } else {
                    return new Line2D.Double(pl, pb);
                }
            } else if (pr != null) {
                if (pt != null) {
                    return new Line2D.Double(pr, pt);
                } else {
                    return new Line2D.Double(pr, pb);
                }
            } else {
                return new Line2D.Double(pt, pb);
            }
        }

    }

}
