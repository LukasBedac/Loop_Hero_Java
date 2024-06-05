package bedac.lukas.tiles;

import bedac.lukas.entity.Entity;
import bedac.lukas.entity.Slime;
import cards.Card;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class RoadTile extends Tile {
    private ArrayList<Entity> enemies;
    private BufferedImage image;
    private Card usedCard;
    //presunut kartu sem a dorobit algoritmus do cesty na prekreslenia + vymazat Cemetery,Tresure a Forest tiles
    private int positionX;
    private int positionY;

    public RoadTile(int riadok, int stlpec) {
        super.setRiadok(riadok);
        super.setStlpec(stlpec);
        this.nastavObrazok();
        this.positionX = stlpec * 60;
        this.positionY = riadok * 60;
        this.enemies = new ArrayList<>();
        if (ThreadLocalRandom.current().nextDouble() > 0.85) {
            this.enemies.add(new Slime());
        }
    }

    @Override
    public void nastavObrazok() {
        try {
            this.image = ImageIO.read(new File("tiles/RoadTile.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void prichodNaPolicko() {

    }

    @Override
    public int novyDen(boolean novyDen) {

        if (this.usedCard != null && novyDen) {
            this.enemies = this.usedCard.novyDen(this.enemies);
        } else {
            if (novyDen && ThreadLocalRandom.current().nextDouble() > 0.7 && this.enemies.size() < 4) {
                this.enemies.add(new Slime());
                this.updateEnemies(novyDen);
                return 1;
            }
        }
        return 0;
    }

    public void updateEnemies(boolean novyDen) {
        if (novyDen) {
            for (Entity enemy : this.enemies) {
                enemy.setHp(10);
                enemy.setDMG(1);
            }
        }
    }

    @Override
    public String getTileName() {
        return "Road";
    }

    @Override
    public void drawImage(Graphics2D g2) {
        super.drawImage(g2);
        if (this.usedCard != null) {
            this.usedCard.drawTileImage(g2, this.getPositionX(), this.getPositionY(), this.getTileSize());
        }
        if (!this.enemies.isEmpty()) {
            int i = 0;
            for (Entity enemy : this.enemies) {
                    if (i == 2) {
                        enemy.drawImage(g2, this.positionX + (i * 10), this.positionY + (i * 15));
                        return;
                    }
                    enemy.drawImage(g2, this.positionX + (i * 30), this.positionY);
                    i++;
            }
        }
    }

    @Override
    public void tileUsedCard(Card card) {
        this.usedCard = card;
        this.setPouzitaKarta(true);
    }

    @Override
    public BufferedImage getImage () {
        return this.image;
    }
    public ArrayList<Entity> getEnemies() {
        return this.enemies;
    }
}
