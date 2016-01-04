
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
        int size = 20; //max size 30
        int partsize = bs / size;

        //hovered spot
        int hx;
        int hy;

        //player turn
        int turn = 0;

        //player budgets
        int budget1 = 8;
        int budget2 = budget1;

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
            g.drawString("49%", x + 65, y + 55);

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
            g.drawString("51%", x + 65, y + 55);

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
            g.setColor(new Color(0, 255, 255, 255));
            for (GameBoard.Edge e: board.geTriangulation() ) {
                Shape l = new Line2D.Float(e.s1.x, e.s1.y, e.s2.x, e.s2.y);
                g.draw(l);
            }

            g.setColor(new Color(150, 0, 200, 150));
            Shape s = new Ellipse2D.Float(hx - 5, hy - 5, 10, 10);
            g.fill(s);

            for (Store store : board.getStores()) {
                if (store.owner == 0) {
                    g.setColor(new Color(255, 0, 0, 150));
                } else {
                    g.setColor(new Color(255, 0, 255, 150));
                }

                s = new Ellipse2D.Float(store.x - 5, store.y - 5, 10, 10);
                g.fill(s);
            }

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
                if (turn == 0 && budget1 > 0) {
                    board.addStore(hx, hy, turn);
                    budget1 -= 1;
                } else if (turn == 1 && budget2 > 0) {
                    board.addStore(hx, hy, turn);
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
