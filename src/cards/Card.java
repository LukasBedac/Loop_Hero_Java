package cards;

import bedac.lukas.entity.Entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public abstract class Card {
    private int positionX;
    private int positionY;
    public static final int CARD_SIZEX = 110;
    public static final int CARD_SIZEY = 130;

    /**
     * @param index - index na ktoré políčko sa zapíše karta
     */
    public Card(int index) {
        this.positionX = index * CARD_SIZEX + 50;
        this.positionY = 550;
    }

    /**
     * Konštruktor na nakliknutú kartu, ktorá stačí len aby existovala
     */
    public Card() {
        
    }

    /**
     * Nastavuje obrázok
     */
    public abstract void nastavObrazok();

    /**
     * @param enemies - nepoužíva sa!
     */
    public abstract void prichodNaPolicko(ArrayList<Entity> enemies);

    /**
     * @param g2 - slúži na vykreslenie obrázka
     */
    public abstract void drawImage(Graphics2D g2);

    /**
     * @param g2 - potrebné na vykreslenie obrázka
     * @param x - x-ová súradnca
     * @param y - y-onová súradnica
     * @param size - velkosť obrázka
     */
    public abstract void drawTileImage(Graphics2D g2, int x, int y, int size);

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }
    public abstract String getNameOfCard();

    /**
     * @param enemies - slúži na získanie nepriateľov z pola pre ďalšiu potrebnú prácu s nimi
     * @return - vráti upravené pole
     */
    public abstract ArrayList<Entity> novyDen(ArrayList<Entity> enemies);
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

}
