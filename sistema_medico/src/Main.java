import Controller.CitaController;
import Controller.DoctorController;
import Controller.PatientController;
import Service.CitaService;
import Service.DoctorService;
import Service.PatientService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        PatientController patientController = new PatientController();
        DoctorController doctorController = new DoctorController();
        CitaController citaController = new CitaController();

        int opcion;

        do {
            System.out.println("\n MENÚ PRINCIPAL - SISTEMA DE CITAS MÉDICAS ");
            System.out.println("1. Registrar nuevo paciente");
            System.out.println("2. Listar pacientes");
            System.out.println("3. Registrar nuevo doctor");
            System.out.println("4. Listar doctores");
            System.out.println("5. Agendar nueva cita");
            System.out.println("6. Listar citas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    patientController.registrarPaciente();
                    break;
                case 2:
                    patientController.listarPacientes();
                    break;
                case 3:
                    doctorController.addDoctorController();
                    break;
                case 4:
                    doctorController.getDoctorsController();
                    break;
                case 5:
                    citaController.agendarCitaController();
                    break;
                case 6:
                    citaController.listarCitasController();
                    break;
                case 0:
                    System.out.println("Cerrando el sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 0);
    }
}