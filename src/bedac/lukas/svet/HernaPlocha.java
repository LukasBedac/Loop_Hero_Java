package bedac.lukas.svet;


import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;


/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class HernaPlocha {
    private final int width = 1200;
    private final int height = 800;
    private final JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel menu;
    private JButton startBtn;
    private JButton quitBtn;
    private JButton continueBtn;
    private JComponent game;
    private JButton gameQuitBtn;
    private JButton gamePauseBtn;
    private JProgressBar dayProgressBar;
    private JComponent fightPanel;
    private BojPanel fight;
    private MainGame mainGame;
    private int progressBarValue;

    /**
     * @param title - slúži na udanie názvu hry
     */
    public HernaPlocha(String title) {
        this.mainFrame = new JFrame();
        this.mainGame = (MainGame)this.game;
        this.fight = (BojPanel)this.fightPanel;
        this.mainFrame.setContentPane(this.mainPanel);
        this.mainFrame.setTitle(title);
        this.mainFrame.setPreferredSize(new Dimension(this.width, this.height));
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setLocation(Toolkit.getDefaultToolkit().getScreenResolution() / 2, Toolkit.getDefaultToolkit().getScreenResolution() / 2);
        this.game.setDoubleBuffered(true);
        this.mainFrame.setResizable(false);
        this.mainFrame.setFocusable(true);
        this.mainFrame.setVisible(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("images/Icon.png");
        this.mainFrame.setIconImage(icon);
        this.setQuitButton();
        this.setStartButton();
        this.setGameQuitButton();

        CardLayout showMenu = (CardLayout)this.mainPanel.getLayout();
        showMenu.show(this.mainPanel, "Menu");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.mainFrame.pack();

        this.gamePauseBtn.addActionListener(e -> {
            if (this.mainGame.isThreadUp()) {
                this.mainGame.stopThread();
            } else {
                this.mainGame.resumeThread();
            }
        });
    }


    private void setQuitButton() {
        this.quitBtn.addActionListener(e ->  {
            System.exit(0);
        });
    }
    private void setStartButton() {
        this.startBtn.addActionListener(e ->  {
            CardLayout showGame = (CardLayout)this.mainPanel.getLayout();
            showGame.show(this.mainPanel, "Game");
            this.mainGame.startThread();
        });

    }
    private void setGameQuitButton() {
        this.gameQuitBtn.addActionListener(e ->  {
            this.mainGame.stopThread();
            CardLayout showMenuToGame = (CardLayout)this.mainPanel.getLayout();
            showMenuToGame.show(this.mainPanel, "Menu");
        });
    }

    /**
     * @param n - slúži na nastavenie hodnoty progress baru
     * @return - vracia boolean či je alebo nie je nový deň
     */
    public boolean setDayProgressBar (int n) {
        if (n % 10 == 0) {
            this.progressBarValue += 1;
            if (this.progressBarValue != 100) {
                this.dayProgressBar.setValue(this.progressBarValue);
                return false;
            } else {
                this.progressBarValue = 0;
                this.dayProgressBar.setValue(this.progressBarValue);
                return true;
            }
        }
        //this.dayProgressBar.setString(String.valueOf(n));
        return false;
    }
    private void createUIComponents() {
        this.game = new MainGame(this);
    }
    public JButton getGamePauseBtn() {
        return this.gamePauseBtn;
    }
}
