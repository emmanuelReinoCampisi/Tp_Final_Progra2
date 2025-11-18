package Clases;

import Enumeradores.ESPECIE;
import Enumeradores.ESTADOCITA;
import Enumeradores.TURNO;
import Excepciones.ExcepcionColeccionVacia;
import Excepciones.ExcepcionNoExistente;
import Excepciones.ExcepcionYaExistente;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Veterinario extends Empleado  {
    private String matricula;
    private ArrayList <ESPECIE> especialidades;
    private HashSet<Cita> citas;

    public Veterinario() {
    }

    public Veterinario(String nombre, int edad, int dni, String email, String contrasenia, TURNO turno, String matricula) {
        super(nombre, edad, dni, email, contrasenia, turno);
        this.matricula = matricula;
        this.especialidades = new ArrayList<>();
        this.citas = new HashSet<>();
    }

    public Veterinario(String nombre, int edad, int dni, String email, String contrasenia, TURNO turno,boolean cuenta_activa, String matricula) {
        super(nombre, edad, dni, email, contrasenia, turno, cuenta_activa);
        this.matricula = matricula;
        this.especialidades = new ArrayList<>();
        this.citas = new HashSet<>();
    }
    public void asignarCita(Cita cita){
        citas.add(cita);
    }
    public String getMatricula() {
        return matricula;
    }



    @Override
    public String toString() {
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String CYAN = "\u001B[36m";

        return String.format(
                BLUE + "Veterinario:" + RESET + "\n" +
                        CYAN + "  Nombre: " + RESET + "%s\n" +
                        CYAN + "  DNI: " + RESET + "%d\n" +
                        CYAN + "  Edad: " + RESET + "%d\n" +
                        CYAN + "  Email: " + RESET + "%s\n" +
                        CYAN + "  Turno: " + RESET + "%s\n" +
                        CYAN + "  Matrícula: " + RESET + "%s\n" +
                        CYAN + "  Especialidades: " + RESET + "%s\n" +
                        CYAN + "  Estado: " + RESET + "%s\n" +
                        CYAN + "  Cargo: " + RESET + "Veterinario\n",
                getNombre(), getDni(), getEdad(), getEmail(), getTurno(), matricula, especialidades, (isCuenta_activa() ? "Activo" : "Inactivo")
        );
    }



    public boolean agregarEspecialidad(ESPECIE espec) throws ExcepcionYaExistente {
        boolean seAgrego = false;
        if(!especialidades.contains(espec)){
            especialidades.add(espec);
            seAgrego = true;
        } else {
            throw new ExcepcionYaExistente("Especialidad ya registrada");
        }

        return seAgrego;
    }

    public boolean eliminarEspecialidad(ESPECIE espec)throws ExcepcionNoExistente, ExcepcionColeccionVacia{
        boolean seElimino = false;

        if(!especialidades.isEmpty()){
            if(especialidades.contains(espec)){
                especialidades.remove(espec);
                seElimino = true;
            }else {
                throw new ExcepcionNoExistente("El veterinario no cuenta con esta especialidad");
            }
        }else {
            throw new ExcepcionColeccionVacia("El veterinario no cuenta con ninguna especialidad");
        }

        return seElimino;
    }

    public void cancelarCita(Cita cita){
        Iterator<Cita> it = citas.iterator();
        while (it.hasNext()) {
            Cita c = it.next();
            if(c.equals(cita)){
                c.setEstadoCita(ESTADOCITA.CANCELADA);
            }
        }
    }

    public String listarCitasPendientes(){
        String mensaje = "";
        Iterator<Cita> it = citas.iterator();
        while (it.hasNext()) {
            Cita c = it.next();
            if(c.getEstadoCita() == ESTADOCITA.PENDIENTE){
                mensaje += c.toString()+"\n";
            }
        }

        return mensaje;
    }

    public String listarHistorialCitas(){
        String mensaje = "";
        Iterator<Cita> it = citas.iterator();
        while (it.hasNext()) {
            Cita c = it.next();
                mensaje += c.toString()+"\n";

        }

        return mensaje;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject empleadoJSON = new JSONObject();
        JSONArray especialidadesARRAY = new JSONArray();
        JSONArray citasARRAY = new JSONArray();
        for (ESPECIE e: especialidades){
            especialidadesARRAY.put(e.toString());
        }
        for(Cita c: citas){
            citasARRAY.put(c.toJSON());
        }
        empleadoJSON.put("nombre",getNombre());
        empleadoJSON.put("edad",getEdad());
        empleadoJSON.put("dni",getDni());
        empleadoJSON.put("email",getEmail());
        empleadoJSON.put("contrasenia",getContrasenia());
        empleadoJSON.put("turno",getTurno());
        empleadoJSON.put("cuenta_activa",isCuenta_activa());
        empleadoJSON.put("cargo","Veterinario");
        empleadoJSON.put("matricula",matricula);
        empleadoJSON.put("especialidades",especialidadesARRAY);
        empleadoJSON.put("citas",citasARRAY);
        return empleadoJSON;
    }

    @Override
    public Veterinario fromJSON(JSONObject veterinarioJSON) {
        Veterinario veterinario = null;

        String nombre = veterinarioJSON.getString("nombre");
        int edad = veterinarioJSON.getInt("edad");
        int dni = veterinarioJSON.getInt("dni");
        String email = veterinarioJSON.getString("email");
        String contrasenia = veterinarioJSON.getString("contrasenia");
        TURNO turno = TURNO.valueOf(veterinarioJSON.getString("turno"));
        boolean cuenta_activa = veterinarioJSON.getBoolean("cuenta_activa");
        String matricula = veterinarioJSON.getString("matricula");

        veterinario = new Veterinario(nombre,edad,dni,email,contrasenia,turno,cuenta_activa,matricula);

        JSONArray especialidadesArray = veterinarioJSON.getJSONArray("especialidades");
        for(int i = 0; i<especialidadesArray.length();i++){
            ESPECIE especie = ESPECIE.valueOf(especialidadesArray.getString(i));
            try{
                veterinario.agregarEspecialidad(especie);
            } catch (ExcepcionYaExistente e){
                e.printStackTrace();
            }
        }

        JSONArray citasArray = veterinarioJSON.getJSONArray("citas");
        for (int i = 0;i<citasArray.length(); i++){
            JSONObject citaJSON = citasArray.getJSONObject(i);
            Cita c = null;
            Cita cita = c.fromJSON(citaJSON);
            veterinario.asignarCita(cita);
        }
        return  veterinario;
    }
}
