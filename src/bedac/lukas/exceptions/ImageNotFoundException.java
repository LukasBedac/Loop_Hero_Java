package bedac.lukas.exceptions;


/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 */
public class ImageNotFoundException extends RuntimeException {
    /**
     * Slúži na vyhodenie chyby pri nenájdený obrázka
     * Myslím, že sa nepoužva
     */
    public ImageNotFoundException() {
        super("Obrázok nenájdený, zlá cesta k obrázku alebo obrázok chýba");
    }
}
