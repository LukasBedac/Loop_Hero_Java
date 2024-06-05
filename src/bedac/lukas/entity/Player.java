package bedac.lukas.entity;

import bedac.lukas.controls.PohybPostavy;
import bedac.lukas.predmety.Predmet;
import bedac.lukas.svet.Boj;
import bedac.lukas.svet.MainGame;
import bedac.lukas.svet.ZaloznyBoj;
import bedac.lukas.svet.mapSet.GenerateInGameMap;
import cards.Card;
import cards.CemeteryCard;
import cards.ForestCard;
import cards.TreasureCard;
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
public class Player implements Entity {
    private ArrayList<Predmet> predmety;
    private int positionX;
    private int positionY;
    private int HP;
    private int damage;
    private BufferedImage image;
    private final PohybPostavy movement;
    private boolean aktivnyPohyb;
    private Card clickedCard;
    private boolean aktivnyBoj;
    private ArrayList<Card> cards;
    private int indexAktivnehoPolicka;

    /**
     * Slúži na vytvorenie a iniciiálizáciu inventára kariet, predmetov
     * nastavenie hp, damageu, obrázku, začiatočného pohybu postavy a začiatočných kariet
     * @param x - x-ová pozícia hráča
     * @param y -y-onová pozícia hráča
     */
    public Player(int x, int y) {
        this.HP = 100;
        this.damage = 15;
        this.nastavObrazokEntity();
        this.positionX = x;
        this.positionY = y;
        this.setAktivnyPohyb(true);
        this.movement = new PohybPostavy(this);
        this.cards = new ArrayList<>();
        this.predmety = new ArrayList<>();
        this.nastavZaciatocneKarty();
    }

    /**
     * @return - vracia obrázok hráča
     */
    @Override
    public String getNazovEntity() {
        return "Hrac";
    }

    /**
     * @return - vracia HP hráča
     */
    @Override
    public int getHp() {
        return this.HP;
    }

    /**
     * @param dmg - slúži na navýšenie damageu hráča
     */
    @Override
    public void setDMG(int dmg) {

    }

    /**
     * @param hp - slúži na navýšenie HP hráča
     */
    @Override
    public void setHp(int hp) {
        this.HP += hp;
    }

    /**
     * Slúži na nastavenie obrázku hráča
     */
    @Override
    public void nastavObrazokEntity() {
        try {
            this.image = ImageIO.read(new File("entity/Player.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return - vracia obrázok hráča
     */
    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Nepoužívané
     * @param mapa - parameter na referenciu mapy
     * @return - vracia boolean či je alebo nie je boj
     */
    public boolean boj(GenerateInGameMap mapa) {
        ArrayList<Entity> enemies = mapa.getCalculatedMap().get(this.indexAktivnehoPolicka).getEnemies();
        Boj boj = new Boj(enemies, this);
        this.aktivnyBoj = false;
        return false;
    }

    /**
     * @param mapa - parameter pre získanie referencie na mapu
     * @param game - parameter pre získanie referencie na hru
     */
    public void zaloznyBoj(GenerateInGameMap mapa, MainGame game) {
        ArrayList<Entity> enemies = mapa.getCalculatedMap().get(this.indexAktivnehoPolicka).getEnemies();
        ZaloznyBoj boj = new ZaloznyBoj(this, enemies, game);
        this.aktivnyBoj = false;
    }

    /**
     * @param g2 - grapfika na vykreslenie
     * @param x  - x-ová pozícia netity
     * @param y  - y-onová pozícia entity
     */
    @Override
    public void drawImage(Graphics2D g2, int x, int y) {
        g2.drawImage(this.image, x, y, 48, 48, null);
        for (Card card : this.cards) {
            card.drawImage(g2);
        }
    }

    /**
     * @param positionX - slúži na posunutie hráča o danú x-ovú pozíciu
     */
    public void moveHorizontally(int positionX) {
        this.positionX += positionX;
    }

    /**
     * @param positionY - slúži na posunutie hráča o danú y-onovú pozíciu
     */
    public void moveVertically(int positionY) {
        this.positionY += positionY;
    }

    /**
     * @return - vracia x-ovú pozíciu
     */
    public int getPositionX() {
        return this.positionX;
    }

    /**
     * @param positionX - slúži na nastavenie x-ovej pozície
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * @return - vracia y-onovú pozíciu
     */
    public int getPositionY() {
        return this.positionY;
    }

    /**
     * @param positionY - slúži na nastavenie y-onovej pozície
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * @param mapa - slúži na získanie referencie na mapu
     */
    public void aktivujPohyb(GenerateInGameMap mapa) {
        switch (this.movement.playerMovement(mapa)) {
            case "up" -> this.moveVertically(-1);
            case "down" -> this.moveVertically(1);
            case "right" -> this.moveHorizontally(1);
            case "left" -> this.moveHorizontally(-1);
        }
    }

    /**
     * Nastavuje začiatočné karty pre hráča pri zapnutí hry
     */
    private void nastavZaciatocneKarty() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            int generatedNumber = random.nextInt(0, 3);
            switch (generatedNumber) {
                case 0 -> this.cards.add(new ForestCard(i)) ;
                case 1 -> this.cards.add(new CemeteryCard(i));
                case 2 -> this.cards.add(new TreasureCard(i));
            }
        }
    }

    /**
     * @param card - pridávaná karta do pola kariet
     */
    public void pridajKartu(Card card) {
        if (!(this.cards.size() > 9)) {
            this.cards.add(card);
            this.cards = this.preusporiadajKarty();
        }
    }

    /**
     * Slúži na zmazanie karty z pola
     */
    public void zmazKartu() {
        if (this.clickedCard != null) {
            this.cards.remove(this.clickedCard);
            this.cards = this.preusporiadajKarty();
        }
    }

    /**
     * @param x - slúži na získanie x-ovej pozície karty
     * @param y - slúži na získanie y-onovej pozície karty
     * @return - vracia novú vygenerovanú kartu podľa názvu
     */
    public Card getCardonClick(int x, int y) {
        for (Card card : this.cards) {
            if (x >= card.getPositionX() && x < card.getPositionX() + Card.CARD_SIZEX &&
                    y >= card.getPositionY() && y < card.getPositionY() + Card.CARD_SIZEY) {
                this.clickedCard = card;
                var name = card.getNameOfCard();
                switch (name) {
                    case "Forest":
                        return new ForestCard();
                    case "Cemetery":
                        return new CemeteryCard();
                    case "Treasure":
                        return new TreasureCard();
                }
            }
        }
        return null;
    }

    /**
     * Slúži na preusporidanie kariet
     * @return - vráti pole kariet
     */
    private ArrayList<Card> preusporiadajKarty() {
        ArrayList<Card> tempCards = new ArrayList<>();
        int iterator = 0;
        for (Card card : this.cards) {
            if (card != null) {
                tempCards.add(card);
                tempCards.get(iterator).setPositionX(iterator * Card.CARD_SIZEX + 50);
                tempCards.get(iterator).setPositionY(550);
                iterator++;
            }
        }
            return tempCards;
    }

    /**
     * @return - vracia stav pohybu
     */
    public boolean isAktivnyPohyb() {
        return this.aktivnyPohyb;
    }

    /**
     * @param aktivnyPohyb - nastavuje pohyb na katívny/neaktívny
     */
    public void setAktivnyPohyb(boolean aktivnyPohyb) {
        this.aktivnyPohyb = aktivnyPohyb;
    }

    /**
     * @return - vracia damage hráča
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * @return - vracia predmety z inventára
     */
    @Override
    public Predmet getLoot() {
        for (Predmet predmet : this.predmety) {
            return predmet;
        }
        return null;
    }

    /**
     * Nepoužíva sa
     * @param damage - nastavuje damage hráčovi
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * @return - vracia či je boj aktívny alebo nie
     */
    public boolean isAktivnyBoj() {
        return this.aktivnyBoj;
    }

    /**
     * @param aktivnyBoj - nastavuje boj na aktívny/neaktívny
     */
    public void setAktivnyBoj(boolean aktivnyBoj) {
        this.aktivnyBoj = aktivnyBoj;
    }

    /**
     * @return - vracia index aktuálneho políčka, na ktorom je hráč
     */
    public int getIndexAktivnehoPolicka() {
        return this.indexAktivnehoPolicka;
    }

    /**
     * @param indexAktivnehoPolicka - predstavuje index políčka, na ktorom hráč stojí
     */
    public void setIndexAktivnehoPolicka(int indexAktivnehoPolicka) {
        this.indexAktivnehoPolicka = indexAktivnehoPolicka;
    }

    /**
     * @param predmet - pridávaný predmet do inventára
     */
    public void pridajPredmet(Predmet predmet) {
        this.predmety.add(predmet);
    }
}
