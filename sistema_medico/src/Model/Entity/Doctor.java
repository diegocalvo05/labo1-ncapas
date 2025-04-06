package Model.Entity;

import java.time.LocalDate;
import java.util.Random;

public class Doctor extends Person {
    private LocalDate fechaReclutamiento;
    private String especialidad;
    private String codigo;

    // Constructor
    public Doctor(String nombre, String apellido, LocalDate birthDate, String dui, LocalDate fechaReclutamiento, String especialidad) {
        super(nombre, apellido, birthDate, dui);
        this.fechaReclutamiento = fechaReclutamiento;
        this.especialidad = especialidad;
        this.codigo = generateCodigo();
    }

    @Override
    public String toString() {
        return "Nombre: " + getNombre() + "\n Apellido: " + getApellido() + "\n Especialidad: " + especialidad;
    }

    //Codigo Doc
    private String generateCodigo() {
        Random random = new Random();
        char letra1 = (char) (random.nextInt(26) + 'A');
        char letra2 = (char) (random.nextInt(26) + 'A');
        char letra3 = (char) (random.nextInt(26) + 'A');
        int numero1 = random.nextInt(10);
        int numero2 = random.nextInt(10);
        int numero3 = random.nextInt(10);
        return "ZNH-" + numero1 + letra1 + numero2 + "-MD-" + numero3 + letra3 + "!";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDate getFechaReclutamiento() {
        return fechaReclutamiento;
    }

    public void setFechaReclutamiento(LocalDate fechaReclutamiento) {
        this.fechaReclutamiento = fechaReclutamiento;
    }
}
