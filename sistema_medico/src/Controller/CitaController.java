package Controller;

import Service.CitaService;
import Service.DoctorService;
import Service.PatientService;
import Model.Entity.Doctor;
import Model.Entity.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class CitaController {
    private CitaService citaService;
    private DoctorService doctorService;
    private PatientService patientService;
    private Scanner scanner;

    public CitaController(CitaService citaService, DoctorService doctorService, PatientService patientService) {
        this.citaService = citaService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.scanner = new Scanner(System.in);
    }

    public void agendarCita() {
        System.out.println("🩺 Seleccione la especialidad:");
        doctorService.mostrarEspecialidades();
        String especialidad = scanner.nextLine();

        Doctor doctor = doctorService.seleccionarDoctorPorEspecialidad(especialidad);
        if (doctor == null) {
            System.out.println("No hay doctores disponibles en esta especialidad.");
            return;
        }

        System.out.println("Ingrese el DUI del paciente:");
        String pacienteDui = scanner.nextLine();
        Patient paciente = patientService.buscarOPacienteNuevo(pacienteDui);

        System.out.println("Ingrese la fecha de la cita (YYYY-MM-DD):");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());

        System.out.println("Ingrese la hora de la cita (HH:MM) entre 08:00 y 16:00:");
        LocalTime hora = LocalTime.parse(scanner.nextLine());

        citaService.agendarCita(doctor, paciente, especialidad, fecha, hora);
    }

    public void listarCitas() {
        citaService.listarCitas();
    }
}
