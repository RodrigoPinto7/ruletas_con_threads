import java.util.concurrent.ThreadLocalRandom;

public class Ruleta extends Thread {
    private static final int[] NUMEROS_RUEDA = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36
    };
    
    private static final String[] COLORES_RUEDA = {
        "Verde", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo",
        "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo"
    };

    public static int simularRuletaNumero() {
        int indice = ThreadLocalRandom.current().nextInt(0, NUMEROS_RUEDA.length);
        return NUMEROS_RUEDA[indice];
    }

    public static String simularRuletaColor(int numero) {
        if (numero < 0 || numero >= COLORES_RUEDA.length) {
            return "Verde";
        }
        return COLORES_RUEDA[numero];
    }

    public static boolean esPar(int numero) {
        return numero != 0 && numero % 2 == 0;
    }

    public static boolean esImpar(int numero) {
        return numero != 0 && numero % 2 != 0;
    }

    public static boolean esRojo(int numero) {
        return simularRuletaColor(numero).equals("Rojo");
    }

    public static boolean esNegro(int numero) {
        return simularRuletaColor(numero).equals("Negro");
    }

    public static boolean esFalta(int numero) {
        return numero >= 1 && numero <= 18;
    }

    public static boolean esPasa(int numero) {
        return numero >= 19 && numero <= 36;
    }
}
