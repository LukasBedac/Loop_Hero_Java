package bedac.lukas.svet;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class BojPanel extends JComponent implements Runnable {
    private Thread thread;
    private final JPanel fightPanel;
    private int iterator;
    private boolean behBoja;
    private int pohybX;
    private int pohybY;
    private Boj boj;

    /**
     * Nepoužíva sa!
     * @param boj
     */
    public BojPanel(Boj boj) {
        this.boj = boj;
        this.behBoja = true;
        this.fightPanel = new JPanel();
        this.fightPanel.setDoubleBuffered(true);
    }
    public void startThread() {
        this.thread = new Thread(this);
        this.thread.start();
        this.setBehBoja(true);
        this.run();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D)g;
        this.boj.drawImage(g2);
        if (this.behBoja) {
            this.boj.drawDmgNumbers(g2);
        }
    }

    @Override
    public void run() {
        while (this.behBoja) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.iterator += 1;
            this.boj.Fight();
            this.boj.kontrola();
            this.repaint();
        }
    }
    public boolean isBehBoja() {
        return this.behBoja;
    }
    public void setBehBoja(boolean behBoja) {
        this.behBoja = behBoja;
    }
}
