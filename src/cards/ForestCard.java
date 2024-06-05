package cards;

import bedac.lukas.entity.Entity;
import bedac.lukas.entity.Zombie;
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
public class ForestCard extends Card {
    private BufferedImage image;
    private BufferedImage tileImage;

    /**
     * @param index - index, na ktorý sa zapíše vytvorená karta
     */
    public ForestCard(int index) {
        super(index);
        this.nastavObrazok();
    }

    /**
     * Slúži na vytvorenie karty bez zaradenia do pola
     */
    public ForestCard() {
        super();
        this.nastavObrazok();
    }

    @Override
    public void nastavObrazok() {
        try {
            this.image = ImageIO.read(new File("cards/ForestCard.png"));
            this.tileImage = ImageIO.read(new File("tiles/Forest.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawImage(Graphics2D g2) {
        g2.drawImage(this.image, this.getPositionX(), this.getPositionY(), CARD_SIZEX, CARD_SIZEY, null);
    }

    @Override
    public void drawTileImage(Graphics2D g2, int x, int y, int size) {
        g2.drawImage(this.tileImage, x, y, size, size, null);
    }

    @Override
    public String getNameOfCard() {
        return "Forest";
    }

    @Override
    public ArrayList<Entity> novyDen(ArrayList<Entity> enemies) {
        if (enemies.size() < 3) {
            enemies.add(new Zombie());
            return enemies;
        }
        return enemies;
    }

    /**
     * @param enemies - nepoužíva sa!
     */
    @Override
    public void prichodNaPolicko(ArrayList<Entity> enemies) {

    }
}
