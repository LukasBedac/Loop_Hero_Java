package bedac.lukas.svet;

import bedac.lukas.entity.Player;
import bedac.lukas.svet.mapSet.GenerateInGameMap;
import cards.Card;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class MainGame extends JComponent implements Runnable {
    private boolean novyDen;
    private final GenerateInGameMap mapa;
    private boolean isThreadUp;
    private boolean zvyrazni;

    private Player player;
    private Thread thread;
    private Card clickedCard;
    private HernaPlocha plocha;

    /**
     * Slúži na nastavenie komponentov hernej plochy a nastavenie tejto triedy
     * @param plocha - referencia na hernú plochu pre nastavenie kkomponentov
     */
    public MainGame(HernaPlocha plocha) {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MainGame.this.clickedCard == null && MainGame.this.player.getCardonClick(e.getX(), e.getY()) != null) {
                    MainGame.this.clickedCard = MainGame.this.player.getCardonClick(e.getX(), e.getY());
                    MainGame.this.player.setAktivnyPohyb(false);
                    MainGame.this.zvyrazni = true;
                } else {
                    if (MainGame.this.clickedCard != null) {
                        var zmeniloSa = MainGame.this.mapa.forwardClickedCardToTile(MainGame.this.clickedCard, e.getX(), e.getY());
                        if (zmeniloSa) {
                            MainGame.this.player.zmazKartu();
                        }
                        MainGame.this.clickedCard = null;
                        //MainGame.this.resumeThread();
                        MainGame.this.player.setAktivnyPohyb(true);
                        MainGame.this.zvyrazni = false;
                    }
                }
            }
        });
        this.plocha = plocha;
        this.isThreadUp = false;
        this.novyDen = false;
        this.mapa = new GenerateInGameMap();
        this.player = new Player(this.mapa.getStarterTile()[1], this.mapa.getStarterTile()[0]);
    }

    /**
     * Slúži na vykreslenie potrebných vecí
     * @param g the <code>Graphics</code> object to protect
     *
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 =  (Graphics2D)g;
        this.mapa.novyDen(this.novyDen);
        this.mapa.drawMap(g2);
        if (this.zvyrazni) {
            this.mapa.cardClickedDraw(g2);
        }
        this.player.drawImage(g2, this.player.getPositionX(), this.player.getPositionY());

    }

    /**
     * Slúži na beh celej hry
     * Kód na fps - https://www.youtube.com/@RyiSnow
     * Minulý rok som okopíroval od neho tento beh hry, pretože som to sám nevedel spraviť
     * Tento rok som si to zobral znova, aby mi bežala hra pekne ako potrebujem
     */
    @Override
    public void run() {
        int fps = 60;
        int progressBarCounter = 0;
        double drawInterval = 1000000000 / fps; //kreslíme každých 0,017 sekúnd
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (this.isThreadUp) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                this.repaint();
                if (this.clickedCard == null) {
                    progressBarCounter += (progressBarCounter <= 100) ? 1 : -100;
                    this.novyDen = this.plocha.setDayProgressBar(progressBarCounter);
                }
                this.player.aktivujPohyb(this.mapa);
                if (this.player.isAktivnyBoj()) {
                    this.stopThread();
                    this.player.aktivujPohyb(this.mapa);
                }
                delta -= 1; //odlišný zápis len kvôli checkstylu, -- mu vadí
                drawCount++;
                //System.out.println(fps);
            }

            if (timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
        if (!this.isThreadUp && this.player.isAktivnyBoj()) {
            this.player.zaloznyBoj(this.mapa, this);
            this.resumeThread();
            this.run();
        }

    }

    /**
     * Slúži na štart threadu
     */
    public void startThread() {
        this.isThreadUp = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Slúži na pokračovanie threadu
     */
    public void resumeThread() {
        this.isThreadUp = true;
        //this.thread.start();
    }

    /**
     * Slúži na stop threadu
     */
    public void stopThread() {
        this.isThreadUp = false;
        //this.thread.interrupt();
    }

    /**
     * @return - vracia boolean či je thread aktívny alebo nie
     */
    public boolean isThreadUp() {
        return this.isThreadUp;
    }

    /**
     * @return - vracia hráča
     */
    public Player getPlayer() {
        return this.player;
    }
}
