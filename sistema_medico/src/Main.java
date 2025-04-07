import Controller.CitaController;
import Controller.DoctorController;
import Controller.PatientController;
import Service.CitaService;
import Service.DoctorService;
import Service.PatientService;

import java.util.Scanner;

public class Main {

    private static void mostrarbotonbonito() {

        System.out.println("\t\t\t  █░░░░░░░░░░░░░ █   🌍 MUNDO SALVA   █ ░░░░░░░░░░░░░░░█");
        System.out.println("\t\t\t  █░░░░░░░░░░░░░ █       VIDAS 💖     █ ░░░░░░░░░░░░░░░█");

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Controladores
        PatientController patientController = new PatientController();
        DoctorController doctorController = new DoctorController();
        CitaController citaController = new CitaController();

        int opcion;

        do {
            System.out.println("\n MENÚ PRINCIPAL - SISTEMA DE CITAS MÉDICAS ");
            System.out.println(" 1. Registrar nuevo paciente");
            System.out.println(" 2. Listar pacientes");
            System.out.println(" 3. Registrar nuevo doctor");
            System.out.println(" 4. Listar doctores");
            System.out.println(" 5. Agendar nueva cita");
            System.out.println(" 6. Listar todas las citas");
            System.out.println(" 7. Filtrar citas por doctor");
            System.out.println(" 8. Filtrar citas por fecha");
            System.out.println(" 9. Editar una cita");
            System.out.println("10. Boton que No sirve de nada, pero se ve bonito.");
            System.out.println(" 0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor ingrese un número válido.");
                opcion = -1;
                continue;
            }

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
                case 7:
                    citaController.listarCitasPorDoctorController();
                    break;
                case 8:
                    citaController.listarCitasPorFechaController();
                    break;
                case 9:
                    citaController.editarCitaController();
                    break;
                case 10:
                    mostrarbotonbonito();
                    break;
                case 0:
                    System.out.println("Cerrando el sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 0);

        scanner.close();
    }
}