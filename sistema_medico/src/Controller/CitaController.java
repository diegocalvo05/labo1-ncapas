package Controller;

import Service.CitaService;

public class CitaController {
    private CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    public void agendarCita() {
        citaService.agendarCita();
    }

    public void listarCitas() {
        citaService.listarCitas();
    }
}
