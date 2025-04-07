package Controller;

import Service.DoctorService;

public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController() {
        this.doctorService = DoctorService.getInstance();
    }

    public void addDoctorController() {
        doctorService.addDoctorMenu();
    }

    public void getDoctorsController() {
        doctorService.getAllDoctors();
    }

}
