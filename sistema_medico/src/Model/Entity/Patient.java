package Model.Entity;

import java.time.LocalDate;

public class Patient extends Person {
    public Patient(String nombre, String apellido, LocalDate birthDate, String dui) {
        super(nombre, apellido, birthDate, dui);
    }

    public String toString() {
        return "Nombre: " + getNombre() + "\nApellido: " + getApellido() + "\nDUI: " + getDui();
    }
}
