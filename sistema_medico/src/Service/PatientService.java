package Service;

import Model.Entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private List<Patient> patients;

    public PatientService() {
        this.patients = new ArrayList<>();
    }

    public Patient buscarPorDui(String dui){
        for (Patient p : patients) {
            if (p.getDui().equals(dui)) {
                return p;
            }
        }
        return null; // No va a devolver nada, si no lo encuentra
    }

    public Patient registrarPacienteNuevo(String nombre, String apellido, String dui, LocalDate birthDate) {
        if (!esMayorDeEdad(birthDate)) {
            System.out.println("El paciente es menir de edad. Se asignara DUI: 00000000-0");
            dui = "00000000-0";
        }
        Patient newPatient = new Patient(nombre, apellido, birthDate, dui);
        patients.add(newPatient);
        return newPatient;
    }

    public boolean esMayorDeEdad(LocalDate birthDate) {
        return LocalDate.now().minusYears(18).isAfter(birthDate);
    }

    public void listarPacientes() {
        if (patients.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }
        patients.forEach(System.out::println);
    }
}
