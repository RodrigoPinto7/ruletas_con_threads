public class Apuesta {
    
    public static int realizarApuestaPlena(int apuesta, int resultado, int monto, int cuenta) {
        if (apuesta == resultado) {
            cuenta += monto * 35;
        } else {
            cuenta -= monto;
        }
        return cuenta;
    }

    public static int realizarApuestaParImpar(boolean esPar, int resultado, int monto, int cuenta) {
        if (esPar == Ruleta.esPar(resultado)) {
            cuenta += monto;
        } else {
            cuenta -= monto;
        }
        return cuenta;
    }

    public static int realizarApuestaColor(boolean esRojo, int resultado, int monto, int cuenta) {
        if (esRojo == Ruleta.esRojo(resultado)) {
            cuenta += monto;
        } else {
            cuenta -= monto;
        }
        return cuenta;
    }

    public static int realizarApuestaFaltaPasa(boolean esFalta, int resultado, int monto, int cuenta) {
        if (esFalta == Ruleta.esFalta(resultado)) {
            cuenta += monto;
        } else {
            cuenta -= monto;
        }
        return cuenta;
    }
}

