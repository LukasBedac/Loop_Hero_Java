package bedac.lukas.predmety;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class Predmet {
    private final String nazov;

    /**
     * @param nazov - slúži na pomenovanie opredmetu
     */
    public Predmet(String nazov) {
        this.nazov = nazov;

    }
    public String getNazov() {
        return this.nazov;
    }
}
