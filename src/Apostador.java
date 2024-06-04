import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Apostador extends Thread {
    private int cuenta;
    private final int id;
    private int monto;
    private final Semaphore semaforo;
    private final static int MAX_ACCESOS = 10;
    private final String estrategia;
    private final String tipoApuesta;
    private final int[] fibonacciSequence = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89};
    private int currentIndex = 0;
    Ruleta resruleta = new Ruleta();
    estrategia estrategiaClass = new estrategia();

    public Apostador(int id, int monto, Semaphore semaforo, String estrategia, String tipoApuesta) {
        this.cuenta = 0;
        this.id = id;
        this.monto = monto;
        this.semaforo = semaforo;
        this.estrategia = estrategia;
        this.tipoApuesta = tipoApuesta;
    }

    @Override
    public void run() {
        for (int i = 0; i < MAX_ACCESOS; i++) {
            int apuestaNumero = ThreadLocalRandom.current().nextInt(0, 37);
            boolean esPar = ThreadLocalRandom.current().nextBoolean();
            boolean esRojo = ThreadLocalRandom.current().nextBoolean();
            boolean esFalta = ThreadLocalRandom.current().nextBoolean();

            try {
                semaforo.acquire(); // Solicitar acceso a ruleta
                int resultado = Ruleta.simularRuletaNumero();
                String color = Ruleta.simularRuletaColor(resultado);

                String tipoApuestaInfo = "";
                switch (tipoApuesta) {
                    case "Plena":
                        tipoApuestaInfo = "Plena al número " + apuestaNumero;
                        Apuesta.realizarApuestaPlena(apuestaNumero, resultado, monto, cuenta);
                        break;
                    case "ParImpar":
                        tipoApuestaInfo = "Par/Impar (Par: " + esPar + ")";
                        Apuesta.realizarApuestaParImpar(esPar, resultado, monto, cuenta);
                        break;
                    case "Color":
                        tipoApuestaInfo = "Color (Rojo: " + esRojo + ")";
                        Apuesta.realizarApuestaColor(esRojo, resultado, monto, cuenta);
                        break;
                    case "FaltaPasa":
                        tipoApuestaInfo = "Falta/Pasa (Falta: " + esFalta + ")";
                        Apuesta.realizarApuestaFaltaPasa(esFalta, resultado, monto, cuenta);
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de apuesta no válida: " + tipoApuesta);
                }

                System.out.println("Hilo " + id + " - Realizando Apuesta: " + tipoApuestaInfo + ", Monto de apuesta: " + monto + " - Resultado Ruleta: " + resultado + " (" + color + ")");

                // Aplicar estrategia
                switch (estrategia) {
                    case "Martingala":
                        monto = estrategiaClass.estrategiaMartingala(monto, apuestaNumero == resultado);
                        break;
                    case "MartingalaInversa":
                        monto = estrategiaClass.estrategiaMartingalaInversa(monto, apuestaNumero == resultado);
                        break;
                    case "DAlembert":
                        monto = estrategiaClass.estrategiaDAlembert(monto, apuestaNumero == resultado);
                        break;
                    case "Fibonacci":
                        monto = estrategiaClass.estrategiaFibonacci(monto, apuestaNumero == resultado, fibonacciSequence, currentIndex);
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
