package Controller;

import Service.PatientService;

public class PatientController {
    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    public void registrarPaciente() {
        patientService.registrarNuevoPaciente();
    }

    public void listarPacientes() {
        patientService.listarPacientes();
    }
}
