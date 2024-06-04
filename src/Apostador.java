import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Apostador extends Thread {
    private int cuenta;
    private final int id;
    private int monto;
    private int tempmonto;
    private final Semaphore semaforo;
    private final static int MAX_ACCESOS = 10;
    private final String estrategia;
    private final int[] fibonacciSequence = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89};
    private int currentIndex = 0;
    Ruleta resruleta = new Ruleta();
    estrategia estrategiaClass = new estrategia();

    public Apostador(int id, int monto, Semaphore semaforo, String estrategia) {
        this.cuenta = 0;
        this.id = id;
        this.monto = monto;
        this.semaforo = semaforo;
        this.estrategia = estrategia;
    }

    @Override
    public void run() {
        for (int i = 0; i < MAX_ACCESOS; i++) {
            int apuesta = ThreadLocalRandom.current().nextInt(0, 37);
            try {
                semaforo.acquire(); // Solicitar acceso a ruleta
                int ruleta = Ruleta.simularRuletaNumero();
                boolean gano = (apuesta == ruleta);
                System.out.println("Hilo " + id + " - Apuesta: " + apuesta + ", Ruleta: " + ruleta + ", Monto de apuesta: " + monto);

                if (gano) {
                    cuenta += monto * 36;
                } else {
                    cuenta -= monto;
                }

                // Aplicar estrategia
                switch (estrategia) {
                    case "Martingala":
                        monto = estrategiaClass.estrategiaMartingala(monto, gano);
                        break;
                    case "MartingalaInversa":
                        monto = estrategiaClass.estrategiaMartingalaInversa(monto, gano);
                        break;
                    case "DAlembert":
                        monto = estrategiaClass.estrategiaDAlembert(monto, gano);
                        break;
                    case "Fibonacci":
                        monto = estrategiaClass.estrategiaFibonacci(monto, gano, fibonacciSequence, currentIndex);
                        break;
                    case "Aleatoria":
                        monto = estrategiaClass.estrategiaAleatoria();
                        break;
                    default:
                        throw new IllegalArgumentException("Estrategia no válida: " + estrategia);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaforo.release(); // Liberar acceso a ruleta
            }
        }
        System.out.println("Hilo " + id + " finaliza con cuenta: " + cuenta);
    }
}



