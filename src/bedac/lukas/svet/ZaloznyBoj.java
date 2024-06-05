package bedac.lukas.svet;

import bedac.lukas.entity.Entity;
import bedac.lukas.entity.Player;
import cards.CemeteryCard;
import cards.ForestCard;
import cards.TreasureCard;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class ZaloznyBoj {

    private final Player player;
    private final ArrayList<Entity> enemies;
    private MainGame game;

    /**
     * Slúži na inicializáciu boja
     * @param player - referencia hráča
     * @param enemies - referencia enemies z daného políčka
     * @param game - referencia hernej plochy
     */
    public ZaloznyBoj(Player player, ArrayList<Entity> enemies, MainGame game) {
        this.player = player;
        this.enemies = enemies;
        this.game = game;
        this.fight();
    }

    /**
     * Priebeh boja v hre
     */
    private void fight() {

        Random random = new Random();
        boolean hracNaRade = true;
        for (Entity enemy : this.enemies) {
            System.out.println("-----------------------------------------------");
            System.out.println("Po prichode na policko sme nasli nepriatela");
            System.out.println("Typ nepriatela: " + enemy.getNazovEntity());
            System.out.println("Pocet HP: " + enemy.getHp());
            System.out.println("-----------------------------------------------");
            while (this.player.getHp() > 0 && enemy.getHp() > 0) {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                double critChance = random.nextDouble();
                double dodge = random.nextDouble();
                if (hracNaRade) {
                    System.out.println("----------------");
                    System.out.println("---HRAC UTOCI---");
                    if (critChance > 0.7) {
                        System.out.printf("Hrac udeluje damage %s\n " , this.player.getDamage() * 2);
                        enemy.setHp(-this.player.getDamage() * 2);
                        System.out.printf("Pocet HP nepriatela %s\n", enemy.getHp());
                        System.out.println("----------------");

                    } else {
                        System.out.printf("Hrac udeluje damage %s\n" , this.player.getDamage());
                        enemy.setHp(-this.player.getDamage());
                        System.out.printf("Pocet HP nepriatela %s\n", enemy.getHp());
                        System.out.println("----------------");

                    }
                } else {
                    System.out.println("----------------");
                    System.out.printf("---%S UTOCI---", enemy.getNazovEntity().toUpperCase());
                    System.out.println();
                    if (dodge > 0.3) {
                        System.out.printf("Hrac sa vyhol utoku %s \n", enemy.getNazovEntity());
                        System.out.printf("Hracove HP: %s\n", this.player.getHp());
                        System.out.println("----------------");
                    } else {
                        System.out.printf("%s udeluje damage %s \n", enemy.getNazovEntity(), enemy.getDamage());
                        this.player.setHp(-enemy.getDamage());
                        System.out.printf("Hracove HP: %s\n", this.player.getHp());
                        System.out.println("----------------");
                    }
                }
                hracNaRade = !hracNaRade;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        if (this.player.getHp() <= 0) {
            System.out.println("Z tohto boja vysiel nepriatel vitazne, v boji sme padli");
            System.out.println("-----------------------------------------------");
            System.out.println();
            JOptionPane.showMessageDialog( this.game, "Prehra!", "Prehra!", JOptionPane.INFORMATION_MESSAGE);
            try {
                Thread.sleep(200000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        } else {
            System.out.println("Vyhravame boj a pokracujeme dalej vo vyprave");
            System.out.println("-----------------------------------------------");
            System.out.println();

            for (Entity enemy : this.enemies) {
                if (enemy.getLoot() != null) {
                    this.player.pridajPredmet(enemy.getLoot());
                    JOptionPane.showConfirmDialog(this.game, "Našli sme následovný loot:\n" + enemy.getLoot().getNazov(), "Loot", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
            }
            this.enemies.clear();
        }
        double chance = ThreadLocalRandom.current().nextDouble();
        if (chance >= 0.75) {
            this.player.pridajKartu(new TreasureCard());
            System.err.println("Ziskal si kartu: Treasure");
        } else if (chance >= 0.5) {
            this.player.pridajKartu(new CemeteryCard());
            System.err.println("Ziskal si kartu: Cemetery");
        } else if (chance >= 0.25) {
            this.player.pridajKartu(new ForestCard());
            System.err.println("Ziskal si kartu: Forest");
        }

    }
}
