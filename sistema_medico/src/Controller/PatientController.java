package Controller;

import Service.PatientService;

public class PatientController {
    private final PatientService patientService;

    public PatientController() {
        this.patientService = new PatientService();
    }

    public void registrarPaciente() {
        patientService.registrarNuevoPaciente();
    }

    public void listarPacientes() {
        patientService.listarPacientes();
    }
}

