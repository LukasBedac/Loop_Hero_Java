package bedac.lukas.entity;

import bedac.lukas.predmety.Predmet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public interface Entity {
    /**
     * @return - vracia názov entity
     */
    String getNazovEntity();

    /**
     * @return - vracia HP entity
     */
    int getHp();

    /**
     * @param dmg - slúži na navýšenie damageu entity
     */
    void setDMG(int dmg);

    /**
     * @return vráti damage entity
     */
    int getDamage();

    /**
     * @return - vráti predmet z inventára entity
     */
    Predmet getLoot();

    /**
     * @param hp - slúži na navýšenie HP entity
     */
    void setHp(int hp);

    /**
     * nastavuje obrázok entity
     */
    void nastavObrazokEntity();

    /**
     * @return - vracia obrázok entity
     */
    BufferedImage getImage();

    /**
     * Slúži na vykreslenie obrázku entity
     * @param g2 - grapfika na vykreslenie
     * @param x - x-ová pozícia netity
     * @param y - y-onová pozícia entity
     */
    void drawImage(Graphics2D g2, int x, int y);

}
