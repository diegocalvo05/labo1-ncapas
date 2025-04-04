package Model.Entity;

import java.time.LocalDate;

public class Patient extends Person {
    public Patient(String nombre, String apellido, String dui, LocalDate birthDate) {
        super(nombre, apellido, dui, birthDate);
    }
}
