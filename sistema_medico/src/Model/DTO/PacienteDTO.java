package Model.DTO;
// DTO para pacientes
public class PacienteDTO {
    private String nombre;
    private String apellido;
    private String dui;
    private String fechaNacimiento;

    // Constructor
    public String getNombre(String nombre,String apellido,String dui,String fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dui = (dui==null || dui.isEmpty()) ? "00000000-0" : dui;
        this.fechaNacimiento = fechaNacimiento;
    }
    // Getters
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getDui() {return dui;}
    public String getFechaNacimiento() {return fechaNacimiento;}

}
