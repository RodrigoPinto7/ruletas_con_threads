import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;


public class Apostador extends Thread{
	private int cuenta;
    private final int id;
    private int monto;
    private int tempmonto;
    private final Semaphore semaforo;
    private final static int MAX_ACCESOS = 10;
    Ruleta resruleta = new Ruleta();
    estrategia nuevomonto = new estrategia();
    
    public Apostador(int id, int monto, Semaphore semaforo) {
        this.cuenta = 0;
        this.id = id;
        this.monto = monto;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        for (int i = 0; i < MAX_ACCESOS; i++) {
            int apuesta = ThreadLocalRandom.current().nextInt(0, 37); 
            try {
                semaforo.acquire(); // Solicitar acceso a ruleta
                int ruleta = Ruleta.simularRuletaNumero();//Todos apuesta a un numero
                System.out.println("Hilo " + id + " - Apuesta: " + apuesta + ", Ruleta: " + ruleta + ", Monto de apuesta: " + monto);
                if (apuesta == ruleta) {
                    cuenta += monto*36;
                    monto = 10; //monto inicial
                } else {
                    cuenta -= monto;
                    tempmonto = nuevomonto.nuevomonto(id, monto);
                    monto = tempmonto;
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


