package bedac.lukas.svet.mapSet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;

/**
 * 26/12/2022 - 16:31
 *
 * @author Lukas
 * - mapa ma rozmer 8:16 [riadky, stlpce]
 */
public class Mapy {
    private final Random randomGen;
    private static final int STLPCE = 16;
    private static final int RIADKY = 8;
    private final boolean[][] mapaBool;
    private final int[][] mapaInt;

    /**
     * Slúži na vytvorenie a inicializáciu potrebnú na generovanie mapy
     */
    public Mapy() {
        this.mapaBool = new boolean[RIADKY][STLPCE];
        this.mapaInt = new int[RIADKY][STLPCE];
        this.randomGen = new Random();
        this.generujMapu();
    }

    /**
     * Generuje mapu podľa súboru
     */
    public void generujMapu() {
        int cisloMapy = this.randomGen.nextInt(1, 3);
        try {
            InputStream mapa = this.getClass().getResourceAsStream("Mapa" + cisloMapy + ".txt");
            //InputStream mapa = this.getClass().getResourceAsStream("Mapa1.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(mapa)));
//            switch (cisloMapy) {
//                case 2:
//                    InputStream mapa2 = this.getClass().getResourceAsStream("\\Mapa2.txt");
//                    if (mapa2 != null) {
//                        buffer = new BufferedReader(new InputStreamReader(mapa2));
//                    }
//                    break;
//                default:
//                    InputStream mapaD = this.getClass().getResourceAsStream("\\Mapa1.txt");
//                    if (mapaD != null) {
//                        buffer = new BufferedReader(new InputStreamReader(mapaD));
//                    }
//            }
            int riadky = 0;
            int stlpce = 0;

            while (stlpce < STLPCE && riadky < RIADKY) {
                if (buffer != null) {
                    String riadok = buffer.readLine();
                    while (stlpce < STLPCE) {
                        String[] pomocnyArray = riadok.split(" ");
                        int cislaMapy = Integer.parseInt(pomocnyArray[stlpce]);
                        this.mapaInt[riadky][stlpce] = cislaMapy;
                        stlpce++;
                    }
                }
                if (stlpce == STLPCE) {
                    stlpce = 0;
                    riadky++;
                }
            }
            buffer.close();
            this.naplMapuBool();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Premení mapu na mapu booleanov
     */
    public void naplMapuBool() {
        for (int i = 0; i < RIADKY; i++) {
            for (int j = 0; j < STLPCE; j++) {
                if (this.mapaInt[i][j] == 1) {
                    this.mapaBool[i][j] = true;
                }
            }
        }
    }

    /**
     * @return - vracia pole boolean, je potrebné na vytvorenie políčok
     */
    public boolean[][] getMapaBool() {
        return this.mapaBool;
    }
}
