package Clases;

import org.json.JSONObject;

import java.util.Objects;


public abstract class Persona implements Identificable,JSONable{
    private String nombre;
    private int dni;
    private int edad;

    public Persona() {
    }

    public Persona(String nombre, int edad, int dni) {
        this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                ", dni=" + dni +
                ", edad=" + edad +
                ',';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return dni == persona.dni;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public int getId() {
        return this.dni;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("nombre", nombre);
        json.put("edad", edad);
        json.put("dni", dni);
        return json;
    }
}
