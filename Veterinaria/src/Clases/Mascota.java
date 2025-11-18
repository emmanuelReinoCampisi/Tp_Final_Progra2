package Clases;

import Enumeradores.ESPECIE;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public class Mascota {
    private int ID;
    private static int contador;
    private String nombre;
    private int edad;
    private ESPECIE especie;
    private String raza;
    private int dniDuenio;
    private HashSet<Cita> historialClinico;

    public Mascota() {
    }

    public Mascota(String nombre, int edad, ESPECIE especie, String raza, int dniDuenio) {
        this.ID = ++contador;
        this.nombre = nombre;
        this.edad = edad;
        this.especie = especie;
        this.raza = raza;
        this.dniDuenio = dniDuenio;
        this.historialClinico = new HashSet<>();
    }

    /// los segundos constructores son para agarrar del fromJSON
    public Mascota(int id,String nombre, int edad, ESPECIE especie, String raza, int dniDuenio) {
        this.ID = id;
        this.nombre = nombre;
        this.edad = edad;
        this.especie = especie;
        this.raza = raza;
        this.dniDuenio = dniDuenio;
        this.historialClinico = new HashSet<>();
    }

    public int getID() {
        return ID;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public ESPECIE getEspecie() {
        return especie;
    }

    public void setEspecie(ESPECIE especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getDniDuenio() {
        return dniDuenio;
    }

    public void setDniDuenio(int dniDuenio) {
        this.dniDuenio = dniDuenio;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    @Override
    public String toString() {
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String CYAN = "\u001B[36m";
        StringBuilder historial = new StringBuilder();

        historial.append(CYAN).append("  Historial Clínico: ").append(RESET);

        if (historialClinico == null || historialClinico.isEmpty()) {
            historial.append("Sin citas registradas.\n");
        } else {
            historial.append("\n");
            for (Cita c : historialClinico) {
                historial.append("    - ").append(c.toString()).append("\n");
            }
        }

        return String.format(
                BLUE + "Mascota:" + RESET + "\n" +
                        CYAN + "  ID: " + RESET + "%d\n" +
                        CYAN + "  Nombre: " + RESET + "%s\n" +
                        CYAN + "  Edad: " + RESET + "%d\n" +
                        CYAN + "  Especie: " + RESET + "%s\n" +
                        CYAN + "  Raza: " + RESET + "%s\n" +
                        CYAN + "  DNI Dueño: " + RESET + "%d\n",
                ID, nombre, edad, especie, raza, dniDuenio,historial.toString()
        );


    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mascota mascota = (Mascota) o;
        return ID == mascota.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }

    public String listarHistorialClinico(){
        StringBuilder sb = new StringBuilder(" ");
        Iterator<Cita> it = historialClinico.iterator();
        Cita cita = null;
        while(it.hasNext()){
            cita = it.next();
            sb.append(cita).append("\n");
        }

        return sb.toString();
    }
    public boolean agregarCita(Cita cita){
        boolean seAgrego = false;
        if(!historialClinico.contains(cita)){
            historialClinico.add(cita);
            seAgrego = true;
        } else {
            System.out.println("ExcepcionExistente");
        }

        return seAgrego;
    }

    public boolean eliminarCita(Cita cita){
        boolean seElimino = false;

        if(!historialClinico.isEmpty()){
            if(historialClinico.contains(cita)){
                historialClinico.remove(cita);
                seElimino = true;
            }else {
                System.out.println("ExcepcionInexistente");
            }
        } else {
            System.out.println("ExcepcionContenedorVacio");
        }
        return seElimino;
    }

    public JSONObject mascotaTOJson(){
        JSONObject mascotaJSON = new JSONObject();
        JSONArray citasJArray = new JSONArray();
        mascotaJSON.put("id_mascota",ID);
        mascotaJSON.put("nombre",nombre);
        mascotaJSON.put("edad",edad);
        mascotaJSON.put("especie",especie);
        mascotaJSON.put("raza",raza);
        mascotaJSON.put("dni_duenio",dniDuenio);
        for(Cita c: this.historialClinico){
            citasJArray.put(c.toJSON());
        }
        mascotaJSON.put("citas",citasJArray);
        return mascotaJSON;
    }

    public static Mascota mascotaFROMJson(JSONObject mascotaJSON){/// Uso un nuevo constructor donde no se incrementa
        Mascota mascota = null;                                   /// el id de manera automatica para evitar que se creen ids fantasmas
        int id_mascota = mascotaJSON.getInt("id_mascota");
        String nombre = mascotaJSON.getString("nombre");
        int edad = mascotaJSON.getInt("edad");
        ESPECIE especie = ESPECIE.valueOf(mascotaJSON.getString("especie"));
        String raza = mascotaJSON.getString("raza");
        int dni_duenio = mascotaJSON.getInt("dni_duenio");

        mascota = new Mascota(id_mascota,nombre,edad,especie,raza,dni_duenio);
        JSONArray citasArray = mascotaJSON.getJSONArray("citas");
        for(int i = 0; i<citasArray.length(); i++){
            JSONObject citaJSON = citasArray.getJSONObject(i);
            Cita c = null;
            Cita cita = c.fromJSON(citaJSON);
            mascota.agregarCita(cita);
        }

        return mascota;
    }
}
