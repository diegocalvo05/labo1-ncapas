package Entity;

import Java.time.LocalDate;
import Java.time.LocalTime;

public class Cita {
    private Doctor doctor;
    private Patient patient;
    private String especialidad;
    private LocalDate fecha;
    private LocalTime hora;
    private boolean atendida;

    public Cita(Doctor doctor, Patient patient, String especialidad, LocalDate fecha, LocalTime hora) {
        this.doctor = doctor;
        this.patient = patient;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.atendida = false; // Default value
    }

    public Doctor getDoctor() {return doctor;}
    public Patient getPatient() {return patient;}
    public String getEspecialidad() {return especialidad;}
    public LocalDate getFecha() {return fecha;}
    public LocalTime getHora() {return hora;}
    public boolean isAtendida() {return atendida;}

    public void markAsAtendida() {
        this.atendida = true;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "Doctor=" + doctor.getNombre() + " " + doctor.getApellido() +
                ", Paciente=" + patient.getNombre() + " " + patient.getApellido() +
                ", Especialidad='" + especialidad + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", atendida=" + atendida +
                '}';
    }
}