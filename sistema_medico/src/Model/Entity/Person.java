package Model.Entity;

import java.time.LocalDate;

public class Person {
    private String nombre;
    private String apellido;
    private String dui;
    private LocalDate birthDate;

    public Person(String nombre, String apellido, String dui, LocalDate birthDate) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dui = (dui == null || dui.isEmpty()) ? "00000000-0" : dui;
        this.birthDate = birthDate;
    }

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
