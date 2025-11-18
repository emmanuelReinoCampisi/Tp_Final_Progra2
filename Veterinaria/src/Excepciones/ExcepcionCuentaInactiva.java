package Excepciones;

public class ExcepcionCuentaInactiva extends RuntimeException {
    public ExcepcionCuentaInactiva(String message) {
        super(message);
    }
}
