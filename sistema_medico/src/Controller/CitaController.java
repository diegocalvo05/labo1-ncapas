package Controller;

import Model.Entity.Doctor;
import Service.CitaService;

import java.time.LocalDate;

public class CitaController {
    private CitaService citaService;

    public CitaController() {
        this.citaService = new CitaService();
    }

    public void agendarCitaController() {
        citaService.agendarCita();
    }

    public void listarCitasController() {
        citaService.listarCitas();
    }

    public void showCitaByDoctorController(Doctor doctor) { citaService.showCitaByDoctor(doctor); }

    public void showCitaByDateController(LocalDate date) { citaService.showCitaByDate(date); }

    public void updateCitaController() {
        citaService.updateCitaMenu();
    }
}
