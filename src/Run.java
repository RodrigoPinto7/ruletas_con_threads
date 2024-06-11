import java.util.concurrent.Semaphore;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Workbook;
import jxl.write.*;

public class Run {
    private static final int MONTECARLO_ITERATIONS = 1000;
    private static final int MONTO_INICIAL = 1000;
    private static final int MAXIMO_PERDIDA = 1000;
    private static final int MAXIMO_GANANCIA = 2000;

    public static void main(String[] args) {
        String[] estrategias = {"Martingala", "MartingalaInversa", "DAlembert", "Fibonacci", "Aleatoria"};
        String[] tiposApuesta = {"Plena", "ParImpar", "Color", "FaltaPasa"};
        
        List<Object[]> registros = new ArrayList<>();
        registros.add(new Object[]{"Iteracion", "Estrategia", "TipoApuesta", "CuentaFinal"});

        for (int i = 0; i < MONTECARLO_ITERATIONS; i++) {
            Semaphore semaforo = new Semaphore(1);
            int id = 1;

            for (String estrategia : estrategias) {
                for (String tipoApuesta : tiposApuesta) {
                    Apostador apostador = new Apostador(id++, MONTO_INICIAL, MAXIMO_PERDIDA, MAXIMO_GANANCIA, semaforo, estrategia, tipoApuesta);
                    apostador.start();
                    try {
                        apostador.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    registros.add(new Object[]{i + 1, estrategia, tipoApuesta, apostador.getCuenta()});
                }
            }
        }

        escribirEnExcel(registros, "ResultadosMontecarlo.xls");
        System.out.println("Todos los hilos han finalizado y los resultados se han escrito en ResultadosMontecarlo.xls.");
    }

    private static void escribirEnExcel(List<Object[]> registros, String archivo) {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(archivo));
            WritableSheet sheet = workbook.createSheet("Resultados", 0);

            for (int i = 0; i < registros.size(); i++) {
                Object[] registro = registros.get(i);
                for (int j = 0; j < registro.length; j++) {
                    if (registro[j] instanceof String) {
                        sheet.addCell(new Label(j, i, (String) registro[j]));
                    } else if (registro[j] instanceof Integer) {
                        sheet.addCell(new jxl.write.Number(j, i, (Integer) registro[j]));
                    }
                }
            }

            workbook.write();
            workbook.close();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
    }
}
