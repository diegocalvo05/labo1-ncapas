package Controller;

import Service.PatientService;

public class PatientController {
    private PatientService patientService;

    public PatientController() {
        this.patientService = PatientService.getInstance();
    }

    public void registrarPaciente() {
        patientService.registrarNuevoPaciente();
    }

    public void listarPacientes() {
        patientService.listarPacientes();
    }
}


