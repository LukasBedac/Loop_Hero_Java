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
public class Duch extends Lootable implements Entity {
    private int hp;
    private int damage;
    private BufferedImage image;
    private Predmet predmet;

    /**
     * Konštruktor slúži na vytvorenie ducha a inicializáciu HP, damageu, obrázku a predmetu
     */
    public Duch() {
        this.hp = 120;
        this.damage = 9;
        this.nastavObrazokEntity();
        this.predmet = super.vygenerujPredmet();

    }

    /**
     * Služi na vykreslenie ducha
     * @param g2 - parameter na graphics, aby sme mohli vykresliť ducha
     * @param x - x-ová pozícia ducha
     * @param y - y-onová pozícia ucha
     */
    @Override
    public void drawImage(Graphics2D g2, int x, int y) {
        g2.drawImage(this.image, x, y, 40, 40, null);
    }

    /**
     * Slúži na nastavenie obrázku atribútu
     */
    @Override
    public void nastavObrazokEntity() {
        try {
            this.image = ImageIO.read(new File("entity/Duch.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return vráti obrázok ducha
     */
    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * @return vráti názov duch
     */
    @Override
    public String getNazovEntity() {
        return "Duch";
    }

    /**
     * @return vráti počet HP ducha
     */
    @Override
    public int getHp() {
        return this.hp;
    }

    /**
     * Metóda na navýšenie damageu ducha po každom dni
     * @param dmg - slúži na navýšenie damageu ducha
     */
    @Override
    public void setDMG(int dmg) {
        this.damage += dmg;
    }

    /**
     * @return vráti damage ducha
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     * @return - vráti predmet z inventára ducha
     */
    @Override
    public Predmet getLoot() {
        return this.predmet;
    }

    /**
     * @param hp - vráti HP ducha
     */
    @Override
    public void setHp(int hp) {
        this.hp += hp;
    }

}
