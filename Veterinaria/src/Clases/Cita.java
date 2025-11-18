package Clases;

import Enumeradores.ESPECIE;
import Enumeradores.ESTADOCITA;
import Enumeradores.TIPOCITA;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Cita implements Identificable,JSONable {
    private int idCita;
    private static int contador = 1000;
    private LocalDate fecha;
    /// Se cambio "LocalDateTime fecha" a LocalDate y LocalTime.
    private LocalTime horario;
    /// LocalDate almacena un dia, LocalTime un horario.
    private TIPOCITA motivo;
    /// hay motivos predefinidos mas generales
    private int mascota_id;
    private ESTADOCITA estadoCita;
    private static int veterinario_dni;
    private String diagnostico;


    /// Constructor general
    public Cita(LocalDate fecha, LocalTime horario, TIPOCITA motivo, ESTADOCITA estadoCita, int mascota_id, int veterinario_dni) {
        this.idCita = ++contador;
        this.fecha = fecha;
        this.horario = horario;
        this.motivo = motivo;
        this.mascota_id = mascota_id;
        this.estadoCita = estadoCita;
        this.veterinario_dni = veterinario_dni;
    }

    /// Constructor para el fromJSON
    public Cita(int id, LocalDate fecha, LocalTime horario, TIPOCITA motivo, ESTADOCITA estadoCita, int mascota_id, int veterinario_dni) {
        this.idCita = id;
        this.fecha = fecha;
        this.horario = horario;
        this.motivo = motivo;
        this.mascota_id = mascota_id;
        this.estadoCita = estadoCita;
        this.veterinario_dni = veterinario_dni;
    }

    public Cita() {
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setMascota_id(int mascota_id) {
        this.mascota_id = mascota_id;
    }

    public void setVeterinario_id(int veterinario_dni) {
        this.veterinario_dni = veterinario_dni;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public ESTADOCITA getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(ESTADOCITA estadoCita) {
        this.estadoCita = estadoCita;
    }

    public TIPOCITA getMotivo() {
        return motivo;
    }

    public void setMotivo(TIPOCITA motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    @Override
    public int getId() {
        return this.idCita;
    }

    public LocalTime getFinCita() {
        return horario.plusMinutes(motivo.getDuracionMinutos()); // aca sabemos a que hora estimada termina la cita
    }

    public int getMascota_id() {
        return mascota_id;
    }

    public int getVeterinario_dni() {
        return veterinario_dni;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return idCita == cita.idCita;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCita);
    }


    @Override
    public String toString() {
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String CYAN = "\u001B[36m";

        return String.format(
                BLUE + "Cita:" + RESET + "\n" +
                        CYAN + "  ID: " + RESET + "%d\n" +
                        CYAN + "  Fecha: " + RESET + "%s\n" +
                        CYAN + "  Hora: " + RESET + "%s\n" +
                        CYAN + "  Motivo: " + RESET + "%s\n" +
                        CYAN + "  Estado: " + RESET + "%s\n" +
                        CYAN + "  Mascota ID: " + RESET + "%d\n" +
                        CYAN + "  Veterinario DNI: " + RESET + "%d\n" +
                        CYAN + "  Diagnóstico: " + RESET + "%s\n",
                idCita, fecha, horario, motivo, estadoCita, mascota_id, veterinario_dni, (diagnostico != null ? diagnostico : "Pendiente")
        );
    }


    @Override
    public JSONObject toJSON() {
        JSONObject citaJSON = new JSONObject();
        citaJSON.put("id_cita", idCita);
        citaJSON.put("fecha", fecha);
        citaJSON.put("horario", horario);
        citaJSON.put("motivo", motivo);
        citaJSON.put("estado_cita", estadoCita);
        citaJSON.put("id_mascota", mascota_id); // Se paso solamente el ID de la mascota para evitar un bucle donde citaTOJson llame a mascotaTOJson para que esta llame a citaTOJson nuevamente
        citaJSON.put("dni_veterinario", veterinario_dni);
        citaJSON.put("diagnostico", diagnostico);
        return citaJSON;
    }


    @Override
    public  Cita fromJSON(JSONObject jsonObject) {
        Cita c = null;
        int idCitaJ = jsonObject.getInt("id_cita");
        LocalDate fechaJ = LocalDate.parse(jsonObject.getString("fecha"));
        LocalTime horarioJ = LocalTime.parse(jsonObject.getString("horario"));
        TIPOCITA motivoJ = TIPOCITA.valueOf(jsonObject.getString("motivo"));
        ESTADOCITA estadoCitaJ = ESTADOCITA.valueOf(jsonObject.getString("estado_cita"));
        int mascota_idJ = jsonObject.getInt("id_mascota");
        int veterinario_dniJ = jsonObject.getInt("dni_veterinario");
        String diagnosticoJ = jsonObject.optString("diagnostico", null);

         c = new Cita(idCitaJ,fechaJ,horarioJ,motivoJ,estadoCitaJ,mascota_idJ,veterinario_dni);
        return c;


    }

}