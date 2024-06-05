package bedac.lukas.svet.mapSet;

import bedac.lukas.tiles.CampfireTile;
import bedac.lukas.tiles.RoadTile;
import bedac.lukas.tiles.Tile;
import cards.Card;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class GenerateInGameMap {
    private static final int STLPCE = 16;
    private static final int RIADKY = 8;
    private static final int TILE_SIZE = 60;

    private Mapy generujMapu;
    private Random random;
    private final Tile[][] inGameMap;
    private final int[] starterTile;
    private final ArrayList<Tile> calculatedMap;
    private boolean koniecMapy;
    private int pocetPolicok;

    /**
     * Slúži na vygenerovanie mapy v hre a inicializáciu všetkých potrebných častí
     * a konverzií
     */
    public GenerateInGameMap() {
        this.random = new Random(System.currentTimeMillis());
        this.calculatedMap = new ArrayList<>();
        this.generujMapu = new Mapy();
        boolean[][] mapa = new boolean[RIADKY][STLPCE];
        this.inGameMap = new Tile[RIADKY][STLPCE];
        this.starterTile = new int[2];
        mapa = this.generujMapu.getMapaBool();
        this.naplMapu(mapa);
        this.setCampfire();
    }

    /**
     * Slúži na nastavenie začiatočného polícka - campfire
     */
    private void setCampfire() {
        int tileSize = 60;
        int riadok = this.random.nextInt(RIADKY);
        this.random.setSeed(System.currentTimeMillis());
        int stlpec = this.random.nextInt(STLPCE);
        while (this.inGameMap[riadok][stlpec] == null) {
            riadok = this.random.nextInt(RIADKY);
            stlpec = this.random.nextInt(STLPCE);
        }
        if (this.inGameMap[riadok][stlpec] != null) {
            this.inGameMap[riadok][stlpec] = new CampfireTile(riadok, stlpec);
            this.inGameMap[riadok][stlpec].setPositionX((stlpec) * tileSize);
            this.inGameMap[riadok][stlpec].setPositionY((riadok) * tileSize);
            this.inGameMap[riadok][stlpec].setPouzitaKarta(true);
            this.calculatedMap.add(this.inGameMap[riadok][stlpec]);
            this.starterTile[0] = riadok * tileSize;
            this.starterTile[1] = stlpec * tileSize;
            this.generateCalculatedMap();
        }
    }

    /**
     * Slúži na vypočítanie mapy na pohyb
     */
    private void generateCalculatedMap() {
        int iterator = 0;
        this.koniecMapy = false;
        while (!this.koniecMapy) {
            if (this.kontrolaPolicka(1, 0, iterator)) {
                iterator++;
            } else if (this.kontrolaPolicka(-1, 0, iterator)) {
                iterator++;
            } else if (this.kontrolaPolicka(0, 1, iterator)) {
                iterator++;
            } else if (this.kontrolaPolicka(0, -1, iterator)) {
                iterator++;
            }
        }
    }

    /**
     * Slúži na kontrolu konca generovanej kalkulovanej mapy pre pohyb
     * @param riadok - slúži na iteráciu v poli podľa riadka
     * @param stlpec - slúži na iteráciu v poli podľa stĺpca
     * @param iterator - iterátor na získanie daného políčka
     * @return - vracia true/false či už je alebo nie je koniec mapy
     */
    private boolean kontrolaPolicka(int riadok, int stlpec, int iterator) {
        Tile temporaryTile = null;
        var zaciatok = this.calculatedMap.get(iterator);
        try {
            if (this.inGameMap[zaciatok.getRiadok() + riadok][zaciatok.getStlpec() + stlpec] != null) {
                temporaryTile = this.inGameMap[zaciatok.getRiadok() + riadok][zaciatok.getStlpec() + stlpec];
                if (temporaryTile  == this.calculatedMap.get(0) && this.calculatedMap.size() == this.pocetPolicok) {
                    this.koniecMapy = true;
                }
                if (!this.calculatedMap.contains(temporaryTile)) {
                    this.calculatedMap.add(temporaryTile);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    /**
     * @param mapa - referencia na pole boolean, podľa ktorého nastavujeme políčka
     */
    public void naplMapu(boolean[][] mapa) {
        int tileSize = 60;
        this.pocetPolicok = 0;
        for (int riadok = 0; riadok < mapa.length; riadok++) {
            for (int stlpec = 0; stlpec < mapa[riadok].length; stlpec++) {
                if (mapa[riadok][stlpec]) {
                    this.inGameMap[riadok][stlpec] = new RoadTile(riadok, stlpec);
                    this.inGameMap[riadok][stlpec].setPositionX((stlpec) * tileSize);
                    this.inGameMap[riadok][stlpec].setPositionY((riadok) * tileSize);
                    this.inGameMap[riadok][stlpec].setPouzitaKarta(false);
                    this.pocetPolicok += 1;
                }
            }
        }
    }

    /**
     * Metóda na vykreslenie celej mapy
     * @param g2 - graphics, vďaka ktorému vykreslujeme mapu
     */
    public void drawMap(Graphics2D g2) {
        for (int riadok = 0; riadok < this.inGameMap.length; riadok++) {
            for (int stlpec = 0; stlpec < this.inGameMap[riadok].length; stlpec++) {
                if (this.inGameMap[riadok][stlpec] != null) {
                    this.inGameMap[riadok][stlpec].drawImage(g2);
                }
            }
        }
    }

    /**
     * @return - vracia začiatočný riadok a stĺpec mapy
     */
    public int[] getStarterTile() {
        return this.starterTile;
    }

    /**
     * @return - vracia pole políčok - toto pole obsahuje už dopredu vypočítaný pohyb po mape
     * ktorú potrebujem vraciať vo viacerých častiach projektu, preto tu je porušené zapuzdrenie
     */
    public ArrayList<Tile> getCalculatedMap() {
        return new ArrayList<>(this.calculatedMap);
    }

    /**
     * Slúži na vykreslenie kliknutej mapy
     * @param g2 - graphics, vďaka ktorému vykreslujeme kliknutú kartu
     */
    public void cardClickedDraw(Graphics2D g2) {
        try {
            BufferedImage zvyraznenie = ImageIO.read(new File("Images/Zvyraznenie.png"));
            BufferedImage zvyraznenieC = ImageIO.read(new File("Images/ZvyraznenieC.png"));
            for (Tile policko : this.calculatedMap) {
                if (!policko.isPouzitaKarta()) {
                    g2.drawImage(zvyraznenie, policko.getPositionX(), policko.getPositionY(),
                            policko.getTileSize(), policko.getTileSize(), null);
                } else {
                    g2.drawImage(zvyraznenieC, policko.getPositionX(), policko.getPositionY(),
                            policko.getTileSize(), policko.getTileSize(), null);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param card - karta, ktorú ideme poslať políčku
     * @param x - x-ová súradnica, slúži na porovnávanie pozície
     * @param y - y-onová súradnica, slúži na porovnávanie pozície
     * @return - vracia boolean true/false, či bola alebo nebola karta poslaná políčku
     */
    public boolean forwardClickedCardToTile(Card card, int x, int y) {
        for (Tile policko : this.calculatedMap) {
            if (x >= policko.getPositionX() && x < policko.getPositionX() + TILE_SIZE && y >= policko.getPositionY() && y < policko.getPositionY() + TILE_SIZE
                && !policko.isPouzitaKarta()) {
                policko.tileUsedCard(card);
                return true;
            }
        }
        return false;
    }

    /**
     * @param novyDen - boolean či je alebo nie je nový deň
     */
    public void novyDen(boolean novyDen) {
        int pocetVygenerovanychEnemiesZaDen = 0;
        for (Tile policko : this.calculatedMap) {
            if (pocetVygenerovanychEnemiesZaDen < 6) {
                pocetVygenerovanychEnemiesZaDen += policko.novyDen(novyDen);
            } else {
                policko.updateEnemies(novyDen);
            }
        }
    }
}
