package Controller;

import Service.CitaService;

public class CitaController {
    private CitaService citaService;

    public CitaController() {
        this.citaService = new CitaService();
    }

    public void agendarCita() {
        citaService.agendarCita();
    }

    public void listarCitas() {
        citaService.listarCitas();
    }
}
