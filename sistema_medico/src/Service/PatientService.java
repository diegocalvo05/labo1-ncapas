package Service;

import Model.Entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientService {
    private static PatientService instance;
    private List<Patient> patients;
    private Scanner scanner;

    private PatientService() {
        this.patients = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static PatientService getInstance() {
        if (instance == null) {
            instance = new PatientService();
        }

        return instance;
    }

    public Patient buscarPorDui(String dui) {
        for (Patient p : patients) {
            if (p.getDui().equals(dui)) {
                return p;
            }
        }
        return null;
    }

    public Patient registrarPacienteNuevo(String nombre, String apellido, String dui, LocalDate birthDate) {
        if (!esMayorDeEdad(birthDate)) {
            System.out.println("🔒 El paciente es menor de edad. Se asignará DUI: 00000000-0");
            dui = "00000000-0";
        }
        Patient newPatient = new Patient(nombre, apellido, birthDate, dui);
        return newPatient;
    }

    public void registrarNuevoPaciente() {
        System.out.print("🔎 Ingrese el DUI del paciente: ");
        String dui = scanner.nextLine();

        Patient existente = buscarPorDui(dui);
        if (existente != null) {
            System.out.println("✅ Paciente encontrado:");
            System.out.println(existente);
            return;
        }

        System.out.println("📝 Registrando nuevo paciente...");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        Patient nuevo = registrarPacienteNuevo(nombre, apellido, dui, birthDate);
        patients.add(nuevo);

        System.out.println("✅ Paciente registrado exitosamente:");
        System.out.println(nuevo.toString());
    }

    private boolean esMayorDeEdad(LocalDate birthDate) {
        return LocalDate.now().minusYears(18).isAfter(birthDate);
    }

    public void listarPacientes() {
        if (patients.isEmpty()) {
            System.out.println("⚠️ No hay pacientes registrados.");
            return;
        }
        patients.forEach(p -> System.out.println(p.toString() + "\n"));
    }
}
