import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Apostador extends Thread {
    private int cuenta;
    private final int id;
    private int monto;
    private final int maximoPerdida;
    private final int maximoGanancia;
    private final Semaphore semaforo;
    private final static int MAX_ACCESOS = 10;
    private final String estrategia;
    private final String tipoApuesta;
    private final int[] fibonacciSequence = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89};
    private int currentIndex = 0;
    private int intentos;
    private int neto;  // Variable para registrar la ganancia/pérdida neta
    Ruleta resruleta = new Ruleta();
    estrategia estrategiaClass = new estrategia();

    public Apostador(int id, int cuentaInicial, int maximoPerdida, int maximoGanancia, Semaphore semaforo, String estrategia, String tipoApuesta) {
        this.cuenta = cuentaInicial;
        this.id = id;
        this.monto = 10; // Monto inicial de la apuesta
        this.maximoPerdida = maximoPerdida;
        this.maximoGanancia = maximoGanancia;
        this.semaforo = semaforo;
        this.estrategia = estrategia;
        this.tipoApuesta = tipoApuesta;
        this.intentos = 0;
        this.neto = 0; // Inicializar en 0
    }

    public int getCuenta() {
        return cuenta;
    }

    public int getIntentos() {
        return intentos;
    }

    public int getNeto() {
        return neto;
    }

    @Override
    public void run() {
        int cuentaInicial = cuenta; // Guardar el saldo inicial
        for (int i = 0; i < MAX_ACCESOS; i++) {
            if (cuenta <= -maximoPerdida || cuenta >= maximoGanancia || cuenta < monto) {
                break;
            }
            
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
                        cuenta = Apuesta.realizarApuestaPlena(apuestaNumero, resultado, monto, cuenta);
                        break;
                    case "ParImpar":
                        tipoApuestaInfo = "Par/Impar (Par: " + esPar + ")";
                        cuenta = Apuesta.realizarApuestaParImpar(esPar, resultado, monto, cuenta);
                        break;
                    case "Color":
                        tipoApuestaInfo = "Color (Rojo: " + esRojo + ")";
                        cuenta = Apuesta.realizarApuestaColor(esRojo, resultado, monto, cuenta);
                        break;
                    case "FaltaPasa":
                        tipoApuestaInfo = "Falta/Pasa (Falta: " + esFalta + ")";
                        cuenta = Apuesta.realizarApuestaFaltaPasa(esFalta, resultado, monto, cuenta);
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de apuesta no válida: " + tipoApuesta);
                }

                System.out.println("Hilo " + id + " - Realizando Apuesta: " + tipoApuestaInfo + " - Resultado Ruleta: " + resultado + " (" + color + "), Monto de apuesta: " + monto);

                // Aplicar estrategia
                switch (estrategia) {
                    case "Martingala":
                        monto = Math.min(estrategiaClass.estrategiaMartingala(monto, apuestaNumero == resultado), cuenta);
                        break;
                    case "MartingalaInversa":
                        monto = Math.min(estrategiaClass.estrategiaMartingalaInversa(monto, apuestaNumero == resultado), cuenta);
                        break;
                    case "DAlembert":
                        monto = Math.min(estrategiaClass.estrategiaDAlembert(monto, apuestaNumero == resultado), cuenta);
                        break;
                    case "Fibonacci":
                        monto = Math.min(estrategiaClass.estrategiaFibonacci(monto, apuestaNumero == resultado, fibonacciSequence, currentIndex), cuenta);
                        break;
                    case "Aleatoria":
                        monto = Math.min(estrategiaClass.estrategiaAleatoria(), cuenta);
                        break;
                    default:
                        throw new IllegalArgumentException("Estrategia no válida: " + estrategia);
                }

                intentos++; // Incrementar el contador de intentos
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaforo.release(); // Liberar acceso a ruleta
            }
        }
        neto = cuenta - cuentaInicial; // Calcular la ganancia/pérdida neta
        System.out.println("Hilo " + id + " finaliza con cuenta: " + cuenta + ", intentos: " + intentos + ", neto: " + neto);
    }
}
