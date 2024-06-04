
import java.util.concurrent.ThreadLocalRandom;


public class estrategia {
    public int estrategiaMartingala(int monto, boolean gano) {
        return gano ? 10 : monto * 2; // Reiniciar a 10 si gana, duplicar si pierde
    }

    public int estrategiaMartingalaInversa(int monto, boolean gano) {
        return gano ? monto * 2 : 10; // Duplicar si gana, reiniciar a 10 si pierde
    }

    public int estrategiaDAlembert(int monto, boolean gano) {
        return gano ? Math.max(10, monto - 1) : monto + 1; // Reducir si gana, aumentar si pierde
    }

    public int estrategiaFibonacci(int monto, boolean gano, int[] fibSequence, int currentIndex) {
        if (gano) {
            currentIndex = Math.max(0, currentIndex - 2); // Volver dos pasos atrás
        } else {
            currentIndex = Math.min(fibSequence.length - 1, currentIndex + 1); // Avanzar un paso
        }
        return fibSequence[currentIndex];
    }

    public int estrategiaAleatoria() {
        return ThreadLocalRandom.current().nextInt(1, 101); // Apostar monto aleatorio entre 1 y 100
    }
}

