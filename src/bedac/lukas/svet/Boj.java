package bedac.lukas.svet;

import bedac.lukas.entity.Entity;
import bedac.lukas.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class Boj {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 680;
    private JFrame fightWindow;
    private BojPanel fightPanel;
    private ArrayList<Entity> enemies;
    private Player hrac;
    private int pohybX;
    private int pohybY;
    private int enemyDead;
    private JProgressBar enemyBar;
    private JProgressBar enemyBar2;
    private JProgressBar playerBar;

    /**
     * Nepoužíva sa - nefunkčná!
     * @param enemies
     * @param hrac
     */
    public Boj(ArrayList<Entity> enemies, Player hrac) {
        this.hrac = hrac;
        this.enemies = enemies;
        this.fightPanel = new BojPanel(this);
        this.fightWindow = new JFrame("Fight!");
        this.fightWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.fightWindow.setLocation(250, 200);
        this.fightWindow.setResizable(false);
        this.fightWindow.setFocusable(true);
        this.pohybX = 700;
        this.pohybY = 150;
        this.fightWindow.setContentPane(this.fightPanel);
        this.fightWindow.setBackground(Color.BLACK);
        this.fightWindow.setSize(new Dimension(WIDTH, HEIGHT));
        this.fightWindow.setVisible(true);
        this.fightPanel.startThread();
        this.fightWindow.pack();

    }

    public void kontrola() {
        if (this.hrac.getHp() <= 0) {
            this.enemyDead = 0;
            this.fightPanel.setBehBoja(false);
            //this.fightWindow.dispose();
        }
        for (Entity enemy : this.enemies) {
            if (enemy.getHp() <= 0) {
                this.enemyDead++;
            }
            if (this.enemyDead == this.enemies.size()) {
                this.enemyDead = 0;
                this.fightPanel.setBehBoja(false);
            }
//            if (this.enemyDead == this.enemies.size()) {
//                this.enemyDead = 0;
//                this.fightPanel.setBehBoja(false);
//                //this.fightWindow.dispose();
//            }
//            else {
//                if (enemy.getHP() <= 0) {
//                    this.enemyDead++;
//                }
//            }
        }
        if (this.hrac.getHp() < 71) {
            this.hrac.setHp(30); //heal po uspesnom boji
        }
    }

     public void drawImage(Graphics2D g2) {
        int i = 0;
         g2.drawImage(this.hrac.getImage(), 200, 250, 96, 96, null);
         this.playerBar = new JProgressBar();
         this.setBars(this.playerBar, 0);
         this.fightWindow.add(this.playerBar);
         this.fightPanel.repaint();;
         for (Entity enemy : this.enemies) {
             if (this.enemies.size() > 1) {
                 g2.drawImage(enemy.getImage(), (this.pohybX * (i + 150)) / 2, (this.pohybY * (i + 150)) / 2, 96, 96, null);
                 this.enemyBar = new JProgressBar();
                 this.setBars(this.enemyBar, 1);
                 this.fightWindow.add(this.enemyBar);
                 this.fightWindow.repaint();
                 i++;
             } else {
                 g2.drawImage(enemy.getImage(), this.pohybX, this.pohybY, 96, 96, null);
                 this.enemyBar2 = new JProgressBar();
                 this.setBars(this.enemyBar2, 2);
                 this.fightWindow.add(this.enemyBar2);
                 this.fightWindow.repaint();
                 i++;
             }
         }
     }
     public void drawDmgNumbers(Graphics2D g2) {
         Random random = new Random();
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }
         int x = random.nextInt(150, 450);
         int y = random.nextInt(50, 550);
         int xE = random.nextInt(500, 1000);
         int yE = random.nextInt(150, 450);
         g2.drawString(Integer.toString(this.hrac.getDamage()), x, y);
         this.fightWindow.repaint();
         for (Entity enemy : this.enemies) {
             g2.drawString(Integer.toString(enemy.getDamage()), xE, yE);
             this.fightWindow.repaint();
         }
     }
     public void Fight() {
        Random random = new Random();
        if (2 % 2 == 0) {

            var enemy = this.enemies.get(random.nextInt(this.enemies.size()));
            if (enemy.getHp() < 0) {
                return;
            }
            enemy.setHp(-this.hrac.getDamage());
        } else {
            for (Entity enemy : this.enemies) {
                this.hrac.setHp(-enemy.getDamage());
            }
        }
     }
     private void setBars(JProgressBar bar, int cislo ) {
        bar.setForeground(Color.GREEN);
         switch (cislo) {
             case 0 -> bar.setLocation(200, 350);
             case 1 -> bar.setLocation(this.pohybX, this.pohybY + 100);
             case 2 -> bar.setLocation(this.pohybX, this.pohybY + 400);
         }
        bar.setVisible(true);

//        this.playerBar = new JProgressBar();
//        this.playerBar.setForeground(Color.GREEN);
//        this.playerBar.setLocation(200, 350);
//
//        this.enemyBar = new JProgressBar();
//        this.enemyBar.setForeground(Color.GREEN);
//        this.enemyBar.setLocation(this.pohybX, this.pohybY);
//        this.enemyBar.setVisible(true);
//        this.enemyBar2 = new JProgressBar();
//        this.enemyBar2.setForeground(Color.GREEN);
//        this.enemyBar2.setLocation(this.pohybX,this.pohybY + 100);
//        this.enemyBar2.setVisible(true);
     }
}
