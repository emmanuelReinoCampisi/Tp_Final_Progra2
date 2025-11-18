package Clases;

import Enumeradores.TURNO;
import org.json.JSONObject;

public class Empleado extends Persona {
    private String email;
    private String contrasenia;
    private TURNO turno;
    private boolean cuenta_activa;

    public Empleado() {
    }

    public Empleado(String nombre, int edad, int dni, String email, String contrasenia, TURNO turno) {
        super(nombre, edad, dni);
        this.email = email;
        this.contrasenia = contrasenia;
        this.turno = turno;
        this.cuenta_activa = true; /// al crear la cuenta se settea en true || Cambie esto porque estaba en false
    }

    public Empleado(String nombre, int edad, int dni, String email, String contrasenia, TURNO turno, boolean cuenta_activa) {
        super(nombre, edad, dni);
        this.email = email;
        this.contrasenia = contrasenia;
        this.turno = turno;
        this.cuenta_activa = cuenta_activa; /// al crear la cuenta se settea en true
    }
    public String getEmail() {
        return email;
    }


    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public TURNO getTurno() {
        return turno;
    }

    public void setTurno(TURNO turno) {
        this.turno = turno;
    }

    public boolean isCuenta_activa() {
        return cuenta_activa;
    }

    public void setCuenta_activa(boolean cuenta_activa) {
        this.cuenta_activa = cuenta_activa;
    }


    @Override
    public String toString() {
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String CYAN = "\u001B[36m";

        return String.format(
                BLUE + "Empleado:" + RESET + "\n" +
                        CYAN + "  Nombre: " + RESET + "%s\n" +
                        CYAN + "  DNI: " + RESET + "%d\n" +
                        CYAN + "  Edad: " + RESET + "%d\n" +
                        CYAN + "  Email: " + RESET + "%s\n" +
                        CYAN + "  Turno: " + RESET + "%s\n" +
                        CYAN + "  Estado: " + RESET + "%s\n"+
                        CYAN + "  Cargo: " + RESET + "Recepcionista\n",
                getNombre(), getDni(), getEdad(), email, turno, (cuenta_activa ? "Activo" : "Inactivo")
        );
    }


    @Override
    public JSONObject toJSON() {
        JSONObject empleadoJSON = new JSONObject();
        empleadoJSON.put("nombre",getNombre());
        empleadoJSON.put("edad",getEdad());
        empleadoJSON.put("dni",getDni());
        empleadoJSON.put("email",email);
        empleadoJSON.put("contrasenia",contrasenia);
        empleadoJSON.put("turno",turno);
        empleadoJSON.put("cuenta_activa",cuenta_activa);
        empleadoJSON.put("cargo","Recepcionista");
        return empleadoJSON;
    }

    @Override
    public Empleado fromJSON(JSONObject empleadoJSON) {
        Empleado empleado = null;
        String nombre = empleadoJSON.getString("nombre");
        int edad = empleadoJSON.getInt("edad");
        int dni = empleadoJSON.getInt("dni");
        String email = empleadoJSON.getString("email");
        String contrasenia = empleadoJSON.getString("contrasenia");
        TURNO turno = TURNO.valueOf(empleadoJSON.getString("turno"));
        boolean cuenta_activa = empleadoJSON.getBoolean("cuenta_activa");

        empleado = new Empleado(nombre,edad,dni,email,contrasenia,turno,cuenta_activa);
        return empleado;
    }


}
