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
public class CampfireTile extends Tile {
    private BufferedImage image;
    public CampfireTile(int riadok, int stlpec) {
        super.setRiadok(riadok);
        super.setStlpec(stlpec);
        this.nastavObrazok();
    }

    @Override
    public void nastavObrazok() {
        try {
            this.image = ImageIO.read(new File("tiles/CampFire.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void prichodNaPolicko() {
    }

    @Override
    public int novyDen(boolean novyDen) {
        return 0;
    }

    @Override
    public void updateEnemies(boolean novyDen) {

    }


    public String getTileName() {
        return "Campfire";
    }

    @Override
    public ArrayList<Entity> getEnemies() {
        return null;
    }

    @Override
    public void drawImage(Graphics2D g2) {
        g2.drawImage(this.image, this.getPositionX(), this.getPositionY(), this.getTileSize(), this.getTileSize(), null);
    }

    @Override
    public void tileUsedCard(Card card) {

    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }
}
