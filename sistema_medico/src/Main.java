import Controller.CitaController;
import Controller.DoctorController;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DoctorController controller = new DoctorController();
        CitaController cController = new CitaController();

        //cController.agendarCita();
        //controller.addDoctorController();
        controller.getDoctorsController();
    }
}