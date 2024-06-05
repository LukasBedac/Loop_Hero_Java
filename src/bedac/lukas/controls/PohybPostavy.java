package bedac.lukas.controls;

import bedac.lukas.entity.Player;
import bedac.lukas.svet.mapSet.GenerateInGameMap;


/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class PohybPostavy {
    private final Player hrac;
    private int iterator;

    private boolean zapniBoj;

    /**
     * Konštruktor slúži na inicizalizáciu hráča a iterátora
     * @param hrac - vráti hráča, pre ktorého as generuje pohyb
     */
    public PohybPostavy(Player hrac) {
        this.hrac = hrac;
        this.iterator = 1;
    }

    /**
     * Metóda slúži na určenie pohybu postavy do daného smeru, popričom sa tu settuje pohyb v hráčovi na true
     * a vraciam si index aktuálneho políčka
     * @param mapa - patameter na získanie mapy
     * @return vracia string určenie smeru na pohyb postavy
     */
    public String playerMovement(GenerateInGameMap mapa) {
        var pohyb = mapa.getCalculatedMap();
        int pohybX = 0;
        int pohybY = 0;
        if (this.iterator % pohyb.size() == 0) {
            this.iterator = 0;
        }
        if (this.hrac.isAktivnyPohyb()) {
            pohybX = this.hrac.getPositionX() - pohyb.get(this.iterator).getPositionX();
            pohybY = this.hrac.getPositionY() - pohyb.get(this.iterator).getPositionY();
            if (pohybX == 0 && pohybY == 0) {
                if (pohyb.get(this.iterator).getEnemies() != null && pohyb.get(this.iterator).getEnemies().size() > 0 && !this.hrac.isAktivnyBoj()) {
                    this.hrac.setAktivnyBoj(true);
                    this.hrac.setIndexAktivnehoPolicka(this.iterator);
                }
                this.iterator++;
            }
            if (pohybX < 0 && pohybY == 0) {
                return "right";
            } else if (pohybX > 0 && pohybY == 0) {
                return "left";
            } else if (pohybX == 0 && pohybY < 0) {
                return "down";
            } else if (pohybX == 0 && pohybY > 0) {
                return "up";
            }
        }
        return "";

//            if (!(this.hrac.getPositionX() - pohyb.get(i).getPositionX() == 0) ) {
//                return "right";
//            }
//        Tile aktualny = mapa.getTile(this.hrac.getPositionY() / 60, this.hrac.getPositionX() / 60);
//        String direction;
//            if (mapa.getTile(aktualny.getPositionY() / 60, (aktualny.getPositionX() - 60) / 60) == null
//                    && mapa.getTile((aktualny.getPositionY() + 60) / 60, aktualny.getPositionX() / 60) == null
//                    && mapa.getTile((aktualny.getPositionY() - 60) / 60, aktualny.getPositionX() / 60) == null) {
//                direction = "down";
//            } else if (mapa.getTile(aktualny.getPositionY() / 60, (aktualny.getPositionX() + 60) / 60) == null
//                    && mapa.getTile((aktualny.getPositionY() + 60) / 60, aktualny.getPositionX() / 60) == null
//                    && mapa.getTile((aktualny.getPositionY() - 60) / 60, aktualny.getPositionX() / 60) == null) {
//                direction = "up";
//            } else if (mapa.getTile((aktualny.getPositionY() + 60) / 60, aktualny.getPositionX() / 60) == null
//                    && mapa.getTile(aktualny.getPositionY() / 60, (aktualny.getPositionX() + 60) / 60) == null
//                    && mapa.getTile(aktualny.getPositionY() / 60, (aktualny.getPositionX() - 60) / 60) == null) {
//                direction = "left";
//            } else {
//                direction = "right";
//            }
//            return direction;//
//            }
    }

//    private void zapniBoj(ArrayList<Entity> enemies) {
//        this.zapniBoj = true;
//        while (this.zapniBoj) {
//           this.zapniBoj = this.hrac.boj(this.hrac, enemies);
//        }
//    }
}
