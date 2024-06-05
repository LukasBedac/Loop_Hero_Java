package bedac.lukas.entity;

import bedac.lukas.predmety.Predmet;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public abstract class Lootable {
    /**
     * Slúži na vygenerovanie predmetu entity
     * @return - vracia vygenerovaný predmet entity
     */
    public Predmet vygenerujPredmet() {
        int random = ThreadLocalRandom.current().nextInt(0,3);
        Predmet predmet = null;
        switch (random) {
            case 0 -> predmet = new Predmet("Mec");
            case 1 -> predmet = new Predmet("Luk");
            case 2 -> predmet = new Predmet("Sekera");
        }
        return predmet;
    }

}

