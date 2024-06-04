import java.util.concurrent.ThreadLocalRandom;

public class Ruleta extends Thread{
	
	 // Definimos los números de la ruleta y sus colores
    private static final int[] NUMEROS_RUEDA = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36
    };
    
    private static final String[] COLORES_RUEDA = {
        "Verde", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo",
        "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Rojo", "Negro", "Rojo", "Negro", "Rojo", "Negro", "Rojo"
    };
    
    public static int simularRuletaNumero() {
    	
        int indice = ThreadLocalRandom.current().nextInt(0, NUMEROS_RUEDA.length);
        int numero = NUMEROS_RUEDA[indice];
        //String color = COLORES_RUEDA[indice];
        
        return numero;
    }
    
	public static String simularRuletaColor() {
	    	
	        int indice = ThreadLocalRandom.current().nextInt(0, NUMEROS_RUEDA.length);
	        //int numero = NUMEROS_RUEDA[indice];
	        String color = COLORES_RUEDA[indice];
	        
	        return color;
	    }
}
