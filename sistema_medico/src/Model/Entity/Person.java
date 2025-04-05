package Model.Entity;

import java.time.LocalDate;

public class Person {
    private String nombre;
    private String apellido;
    private LocalDate birthDate;
    private String dui;

    public Person(String nombre, String apellido, LocalDate birthDate, String dui) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.birthDate = birthDate;
        this.dui = dui;
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
