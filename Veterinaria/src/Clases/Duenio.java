package Clases;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Duenio extends Persona  {
    private long telefono;
    private String direccion;
    private ArrayList<Mascota> mascotas;

    public Duenio() {
    }

    public Duenio(String nombre, int edad, int dni, long telefono, String direccion) {
        super(nombre, edad, dni);
        this.telefono = telefono;
        this.direccion = direccion;
        this.mascotas = new ArrayList<>();
    }


    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String listarMascotas(){
        StringBuilder sb = new StringBuilder(" ");

        for(Mascota m: mascotas){
            sb.append(m.toString()).append("\n");
        }
        return sb.toString();
    }


    public String listarMascotasEspecifica(String nombreMascota){
        StringBuilder sb = new StringBuilder(" ");

        for(Mascota m: mascotas){
            if(m.getNombre().equalsIgnoreCase(nombreMascota)){
                sb.append(m).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String CYAN = "\u001B[36m";

        return String.format(
                BLUE + "Dueño:" + RESET + "\n" +
                        CYAN + "  Nombre: " + RESET + "%s\n" +
                        CYAN + "  DNI: " + RESET + "%d\n" +
                        CYAN + "  Edad: " + RESET + "%d\n" +
                        CYAN + "  Teléfono: " + RESET + "%d\n" +
                        CYAN + "  Dirección: " + RESET + "%s\n" +
                        CYAN + "  Mascotas:\n" + RESET + "%s",
                getNombre(), getDni(), getEdad(), telefono, direccion, listarMascotas()
        );
    }



        public boolean agregarMascota(Mascota mascota){
        boolean seAgrego = false;
        if(!mascotas.contains(mascota)){
            mascotas.add(mascota);
            seAgrego = true;
        } else {
            System.out.println("ExcepcionExistente");
        }
        return seAgrego;
    }

    public boolean eliminarMascota(Mascota mascota) {
        boolean seElimino = false;

        if (!mascotas.isEmpty()) {
            if (mascotas.contains(mascota)) {
                mascotas.remove(mascota);
                seElimino = true;
            } else {
                System.out.println("ExcepcionInexistente");
            }
        } else {
            System.out.println("Excepciones.ExcepcionColeccionVacia");
        }
        return seElimino;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject duenioJSON = new JSONObject();
        duenioJSON.put("nombre",getNombre());
        duenioJSON.put("edad",getEdad());
        duenioJSON.put("dni",getDni());
        duenioJSON.put("telefono",telefono);
        duenioJSON.put("direccion",direccion);
        JSONArray mascotasJSON = new JSONArray();
        for(Mascota m: mascotas){
            JSONObject mascoJSON = m.mascotaTOJson();
            mascotasJSON.put(mascoJSON);
        }
        duenioJSON.put("mascotas",mascotasJSON);
        return duenioJSON;
    }

    @Override
    public  Duenio fromJSON(JSONObject duenioJSON) {
        Duenio duenio = null;

        String nombre = duenioJSON.getString("nombre");
        int edad = duenioJSON.getInt("edad");
        int dni = duenioJSON.getInt("dni");
        int telefono = duenioJSON.getInt("telefono");
        String direccion = duenioJSON.getString("direccion");

        duenio = new Duenio(nombre,edad,dni,telefono,direccion);

        JSONArray mascotasJSON = duenioJSON.getJSONArray("mascotas");
        for(int i =0; i<mascotasJSON.length(); i++){
            JSONObject mascotaJSON = mascotasJSON.getJSONObject(i);
            duenio.agregarMascota(Mascota.mascotaFROMJson(mascotaJSON));
        }
        return duenio;
    }


}
