package Controller;

import Model.Entity.Doctor;
import Service.CitaService;
import Service.DoctorService;
import Service.PatientService;

import java.time.LocalDate;
import java.util.Scanner;

public class CitaController {
    private CitaService citaService;
    private DoctorService doctorService;
    private PatientService patientService;
    private Scanner scanner;

    public CitaController() {
        this.doctorService = DoctorService.getInstance();
        this.patientService = PatientService.getInstance();
        this.citaService = new CitaService(doctorService, patientService);
        this.scanner = new Scanner(System.in);
    }

    public void agendarCitaController() {
        citaService.agendarCita();
    }

    public void listarCitasController() {
        citaService.listarCitas();
    }

    public void listarCitasPorDoctorController() {
        System.out.println("🔎 Buscar citas por doctor:");
        doctorService.getDoctors().forEach(d -> System.out.println("- " + d.getNombre()));

        System.out.print("Ingrese el nombre del doctor: ");
        String nombre = scanner.nextLine();

        Doctor doctor = doctorService.buscarDoctorPorNombre(nombre);
        if (doctor == null) {
            System.out.println("❌ Doctor no encontrado.");
            return;
        }

        citaService.showCitaByDoctor(doctor);
    }

    public void listarCitasPorFechaController() {
        System.out.print("📅 Ingrese la fecha (YYYY-MM-DD): ");
        try {
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            citaService.showCitaByDate(fecha);
        } catch (Exception e) {
            System.out.println("❌ Formato de fecha inválido.");
        }
    }

    public void editarCitaController() {
        citaService.updateCitaMenu();
    }
}