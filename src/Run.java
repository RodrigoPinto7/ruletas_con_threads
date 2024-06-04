import java.util.concurrent.Semaphore;

public class Run {
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(1); // Solo un acceso a la vez a la ruleta
        Apostador hilo1 = new Apostador(1, 10, semaforo, "Martingala", "ParImpar");
        Apostador hilo2 = new Apostador(2, 10, semaforo, "MartingalaInversa", "ParImpar");
        Apostador hilo3 = new Apostador(3, 10, semaforo, "DAlembert", "ParImpar");
        Apostador hilo4 = new Apostador(4, 10, semaforo, "Fibonacci", "ParImpar");
        Apostador hilo5 = new Apostador(5, 10, semaforo, "Aleatoria", "ParImpar");

        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
            hilo5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Todos los hilos han finalizado.");
    }
}



//DEFINIR BIEN MONTO DE APUESTA DE id 2 Y 3 
//PODEMOS CAMBIAR ESTRATEGIA DE APUESTA PARA CADA UNO TAMBIEN DADO QUE APOSTAR A UN NUMERO NADIE GANA