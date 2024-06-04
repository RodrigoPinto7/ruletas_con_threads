import java.util.concurrent.Semaphore;

public class Run {
	public static void main(String[] args) {
		
		
        Semaphore semaforo = new Semaphore(1); // Solo un acceso a la vez a la ruleta
        Apostador hilo1 = new Apostador(1, 10, semaforo); //Todos comienzan con monto inicial 10
        Apostador hilo2 = new Apostador(2, 10, semaforo); 
        Apostador hilo3 = new Apostador(3, 10, semaforo); 
        
        hilo1.start();
        hilo2.start();
        hilo3.start();
        
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Todos los hilos han finalizado.");
    }
}


//DEFINIR BIEN MONTO DE APUESTA DE id 2 Y 3 
//PODEMOS CAMBIAR ESTRATEGIA DE APUESTA PARA CADA UNO TAMBIEN DADO QUE APOSTAR A UN NUMERO NADIE GANA