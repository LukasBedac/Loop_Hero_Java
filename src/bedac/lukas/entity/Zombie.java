package bedac.lukas.entity;

import bedac.lukas.predmety.Predmet;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class Zombie extends Lootable implements Entity  {
    private int hp;
    private int damage;
    private BufferedImage image;
    private Predmet predmet;

    /**
     * Slúži na vytvorenie zombie a inicializáciu hp, dmageu, brázka a predmetu
     */
    public Zombie() {
        this.hp = 160;
        this.damage = 7;
        this.nastavObrazokEntity();
        this.predmet = super.vygenerujPredmet();

    }

    /**
     * @param g2 - grapfika na vykreslenie
     * @param x  - x-ová pozícia netity
     * @param y  - y-onová pozícia entity
     */
    @Override
    public void drawImage(Graphics2D g2, int x, int y) {
        g2.drawImage(this.image, x, y, 40, 40, null);
    }

    /**
     * Slúži na nastavenie obrázka zombie
     */
    @Override
    public void nastavObrazokEntity() {
        try {
            this.image = ImageIO.read(new File("entity/Zombie.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return - vracia obrázok zombie
     */
    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * @return - vracia názov zombie
     */
    @Override
    public String getNazovEntity() {
        return "Zombie";
    }

    /**
     * @return - vracia HP zombie
     */
    @Override
    public int getHp() {
        return this.hp;
    }

    /**
     * @param dmg - slúži na navýšenie damageu zombie
     */
    @Override
    public void setDMG(int dmg) {
        this.damage += dmg;
    }

    /**
     * @return - vracia damage zombie
     */
    @Override
    public int getDamage() {
        return 9;
    }

    /**
     * @return - vracia predmet z inventára
     */
    @Override
    public Predmet getLoot() {
        return this.predmet;
    }

    /**
     * @param pocetHP - slúži na navýšenie HP zombie
     */
    @Override
    public void setHp(int pocetHP) {
        this.hp += pocetHP;
    }

}
