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
        this.doctorService = new DoctorService();
        this.patientService = new PatientService();
        this.citaService = new CitaService(doctorService, patientService);
        this.scanner = new Scanner(System.in);
    }

    public void agendarCitaController() {
        citaService.agendarCita();
    }

    public void listarCitasController() {
        citaService.listarCitas();
    }

    public void showCitaByDoctorController(Doctor doctor) {
        citaService.showCitaByDoctor(doctor);
    }

    public void showCitaByDateController(LocalDate date) {
        citaService.showCitaByDate(date);
    }

    public void updateCitaController() {
        citaService.updateCitaMenu();
    }
}
