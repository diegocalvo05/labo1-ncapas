package Model.DTO;

import java.time.LocalDateTime;
import java.util.Random;
//DTO para doctores
public class DoctorDTO {
    private String nombre;
    private String apellido;
    private String dui;
    private LocalDate fechaNacimiento;
    private LocalDate fechaReclutamiento;
    private String especialidad;
    private String codigo;
    // Constructor
    public DoctorDTO(String nombre,String apellido, String dui, LocalDate fechaNacimiento, LocalDate fechaReclutamiento, String especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dui = dui;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaReclutamiento = fechaReclutamiento;
        this.especialidad = especialidad;
        this.codigo = generateCodigo();
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
    // Getters
        public String getNombre() {return nombre;}
        public String getApellido() {return apellido;}
        public String getDui() {return dui;}
        public LocalDate getFechaNacimiento() {return fechaNacimiento;}
        public LocalDate getFechaReclutamiento() {return fechaReclutamiento;}
        public String getEspecialidad() {return especialidad;}
        public String getCodigo() {return codigo;}

}