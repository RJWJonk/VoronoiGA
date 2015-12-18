import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
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
        setTitle("TwitTwins");
        BufferedImage icon = null;
//        try {
//            icon = ImageIO.read(TwitTwinsGUI.class.getResourceAsStream("/Datafiles/TwitTwins_icon.png"));
//        } catch (IOException e) {
//        }
        // this.setIconImage(icon);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setIgnoreRepaint(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        mainPanel = new MainMenu();
        optionsPanel = new OptionsMenu();
        creditsPanel = new CreditsMenu();
        gamePanel = new GameBoard();

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
                    activePanel = new GameBoard();
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

    private class GameBoard extends JPanel {

        JButton backButton;

        Dimension preferred = new Dimension(600, 600);

        private GameBoard() {
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
        }

    }

}
