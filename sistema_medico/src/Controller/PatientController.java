package Controller;

import Model.Entity.Patient;
import Service.PatientService;

import java.time.LocalDate;
import java.util.Scanner;

public class PatientController {
    private PatientService patientService;
    private Scanner scanner;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
        this.scanner = new Scanner(System.in);
    }

    public void registrarPaciente() {
        System.out.print("Ingrese DUI del paciente: ");
        String dui = scanner.nextLine();

        Patient existente = patientService.buscarPorDui(dui);
        if(existente != null) {
            System.out.println("Paciente Encontrado: ");
            System.out.println(existente);
            return;
        }

        System.out.print("Registrando nuevo paciente... ");

        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Ingrese fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        Patient newPatient = patientService.registrarPacienteNuevo(nombre, apellido, dui, birthDate);
        System.out.println("Paciente registrado exitosamente.");
        System.out.println(newPatient);
    }

    public void listarPacientes() {
        patientService.listarPacientes();
    }
}
