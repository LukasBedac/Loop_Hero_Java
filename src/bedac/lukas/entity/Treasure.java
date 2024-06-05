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
public class Treasure extends Lootable implements Entity {
    private int hp;
    private int damage;
    private BufferedImage image;
    private Predmet predmet;

    /**
     * Slúži na vytvorenie chestky a inicializáciu hp, damageu obrázka a predmetu
     */
    public Treasure() {
        this.hp = 30;
        this.damage = 17;
        this.nastavObrazokEntity();
        this.predmet = super.vygenerujPredmet();
    }

    /**
     * @return - vracia názov chestky
     */
    @Override
    public String getNazovEntity() {
        return "Chest";
    }

    /**
     * @return - vracia HP chestky
     */
    @Override
    public int getHp() {
        return this.hp;
    }

    /**
     * @param dmg - slúži na navýšenie damageu chestky
     */
    @Override
    public void setDMG(int dmg) {
        this.damage += dmg;
    }

    /**
     * @return - vracia damage chestky
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
        return this.predmet;
    }

    /**
     * @param hp - slúži na navýšenie HP chestky
     */
    @Override
    public void setHp(int hp) {
        this.hp += hp;
    }

    /**
     * Slúži na nastavenie obrázka chestky
     */
    @Override
    public void nastavObrazokEntity() {
        try {
            this.image = ImageIO.read(new File("entity/chest.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return - vráti obrázok chestky
     */
    @Override
    public BufferedImage getImage() {
        return null;
    }

    /**
     * @param g2 - grapfika na vykreslenie
     * @param x  - x-ová pozícia netity
     * @param y  - y-onová pozícia entity
     */
    @Override
    public void drawImage(Graphics2D g2, int x, int y) {
        g2.drawImage(this.image, x + 10, y + 10, 40, 40, null);
    }

}
