package bedac.lukas.tiles;

import bedac.lukas.entity.Entity;
import cards.Card;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public abstract class Tile {
    private static final int TILE_SIZE = 60;
    private int positionX;
    private int positionY;
    private int riadok;
    private int stlpec;
    private BufferedImage image;
    private boolean pouzitaKarta;
    private Card usedCard;

    private ArrayList<Entity> enemies;

    /**
     * Konštruktor karty, ktorý nastavuje obrázok
     */
    public Tile() {
        this.nastavObrazok();
    }

    /**
     * Slúži na identifikáciu príchodu na políčko
     */
    public abstract void prichodNaPolicko();

    /**
     * @return -vracia x-ovú súradnicu
     */
    public int getPositionX() {
        return this.positionX;
    }

    /**
     * @param positionX - parameter na setnutie x-ovej súradnice
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * @return - -vracia y-onovú súradnicu
     */
    public int getPositionY() {
        return this.positionY;
    }

    /**
     * @param novyDen - boolean či je alebo nie je nový deň
     * @return - vracia hodnotu 1 ak bol spawnutý enemy
     */
    public abstract int novyDen(boolean novyDen);

    /**
     * @param novyDen - boolean či je alebo nie je nový deň
     */
    public abstract void updateEnemies(boolean novyDen);

    /**
     * @param positionY  - parameter na setnutie y-onovej súradnice
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * @return - vracia obrázok základneho políčka
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Slúži na nastavenie obrázka políčok
     */
    public void nastavObrazok() {

    }

    /**
     * @param g2 - slúži na vykreslenie obrázkov
     */
    public void drawImage(Graphics2D g2) {
        try {
            this.image = ImageIO.read(new File("tiles/RoadTile.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(this.image, this.getPositionX(), this.getPositionY(), this.getTileSize(), this.getTileSize(), null);

    }

    /**
     * @return - vracia velkosť poolíčka
     */
    public int getTileSize() {
        return TILE_SIZE;
    }

    /**
     * @param card - vracia použtú kartu políčka
     */
    public abstract void tileUsedCard(Card card);

    /**
     * @return - vracia názov políčka
     */
    abstract String getTileName();

    /**
     * @return - vracia riadok
     */
    public int getRiadok() {
        return this.riadok;
    }

    /**
     * @return - vracia stĺpec
     */
    public int getStlpec() {
        return this.stlpec;
    }

    /**
     * @param riadok - nastavuje sa na tento daný riadok
     */
    public void setRiadok(int riadok) {
        this.riadok = riadok;
    }

    /**
     * @param stlpec nastavuje sa na tento daný stĺpec
     */
    public void setStlpec(int stlpec) {
        this.stlpec = stlpec;
    }

    /**
     * @return - vracia či je použitá kara v boolean hodnote
     */
    public boolean isPouzitaKarta() {
        return this.pouzitaKarta;
    }

    /**
     * @param pouzitaKarta - nastavuje použitú kartu
     */
    public void setPouzitaKarta(boolean pouzitaKarta) {
        this.pouzitaKarta = pouzitaKarta;
    }

    /**
     * @return - vracia pouťitú kartu
     */
    public Card getUsedCard() {
        return this.usedCard;
    }

    /**
     * @param usedCard - nastavuje použitú kartu
     */
    public void setUsedCard(Card usedCard) {
        this.usedCard = usedCard;
    }

    /**
     * @return - vracia nepriateľov z polícka
     */
    public abstract ArrayList<Entity> getEnemies();
}

