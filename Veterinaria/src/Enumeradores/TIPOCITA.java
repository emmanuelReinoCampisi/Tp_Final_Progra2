package Enumeradores;

public enum TIPOCITA {
    CONSULTA_GENERAL(30),
    VACUNACION(25),
    CONTROL(40),
    CIRUGIA(90),
    EMERGENCIA(120);

    private final int duracionMinutos;

    TIPOCITA(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }
}
