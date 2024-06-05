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
public class Slime implements Entity {
    private int hp;
    private int damage;
    private BufferedImage image;

    /**
     * Slúži na vytvorenie slima a inicializáciu hp, dmgu a obrázku
     */
    public Slime() {
        this.hp = 60;
        this.damage = 4;
        this.nastavObrazokEntity();
    }

    /**
     * @return - vracia názov slima
     */
    @Override
    public String getNazovEntity() {
        return "Slime";
    }

    /**
     * @return - vvracia HP slima
     */
    @Override
    public int getHp() {
        return this.hp;
    }

    /**
     * @param dmg - slúži na navýšenie damageu slima
     */
    @Override
    public void setDMG(int dmg) {
        this.damage += dmg;
    }

    /**
     * @return - vracia damage slima
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     * @return - vracia predmet z inventára
     */
    @Override
    public Predmet getLoot() {
        return null;
    }

    /**
     * @param hp - slúži na navýšenie HP slima
     */
    @Override
    public void setHp(int hp) {
        this.hp = this.hp + hp;
    }

    /**
     * Slúži na nastavenie obrázka slima
     */
    @Override
    public void nastavObrazokEntity() {
        try {
            this.image = ImageIO.read(new File("entity/Slime.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return - vracia obrázok slima
     */
    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * @param g2 - grapfika na vykreslenie
     * @param x  - x-ová pozícia netity
     * @param y  - y-onová pozícia entity
     */
    @Override
    public void drawImage(Graphics2D g2, int x, int y) {
        g2.drawImage(this.image, x, y, 30, 30, null);
    }

}
