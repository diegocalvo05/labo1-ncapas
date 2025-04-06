package Service;

import Model.Entity.Doctor;
import Model.Entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DoctorService {
    private List<Doctor> doctors;
    private List<String> specialities;
    private Scanner scanner;

    public DoctorService() {
        this.doctors = new ArrayList<>();
        this.specialities = new ArrayList<>(Arrays.asList("Dermatologo", "Cardiologo", "Neumologo", "Internista", "Neurologo"));
        this.scanner = new Scanner(System.in);
    }

    public Doctor searchByDui(String dui) {
        for (Doctor d : doctors) {
            if (d.getDui().equals(dui)) {
                return d;
            }
        }
        return null;
    }

    public Doctor addDoctor(String nombre, String apellido, LocalDate birthDate, String dui, LocalDate fechaReclutamiento, String especialidad) {
        Doctor newDoctor = new Doctor(nombre, apellido, birthDate, dui, fechaReclutamiento, especialidad);
        doctors.add(newDoctor);
        return newDoctor;
    }

    public void addDoctorMenu() {
        System.out.print("🔎 Ingrese el DUI del doctor: ");
        String dui = scanner.nextLine();

        Doctor existente = searchByDui(dui);

        if (existente != null) {
            System.out.println("✅ Doctor encontrado:");
            System.out.println(existente);
            return;
        }

        System.out.println("📝 Registrando nuevo doctor...");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Fecha de reclutamiento (YYYY-MM-DD): ");
        LocalDate fechaReclutamiento = LocalDate.parse(scanner.nextLine());

        System.out.println("Elija su especialidad (Digite el numero): ");
        for(int i = 0; i < specialities.size(); i++) {
            System.out.println(i + ". " + specialities.get(i));
        }
        String especialidad = specialities.get(Integer.parseInt(scanner.nextLine()));

        Doctor newDoctor = addDoctor(nombre, apellido, birthDate, dui, fechaReclutamiento, especialidad);
        doctors.add(newDoctor);

        System.out.println("✅ Paciente registrado exitosamente:");
        System.out.println(newDoctor.toString());
    }

    public void getAllDoctors() {
        if(doctors.isEmpty()) {
            System.out.println("No se encontraron doctores");
        }

        doctors.forEach(d -> System.out.println(d.getNombre()));
    }

    public void showSpecialities() {
        specialities.forEach(System.out::println);
    }

    public Doctor seleccionarDoctorPorEspecialidad(String especialidad) {
        System.out.println("Seleccione el numero de un doctor: ");

        List<Doctor> selectedDoctors = doctors.stream().filter(doctor -> doctor.getEspecialidad().equals(especialidad)).toList();

        for(int i = 0; i < selectedDoctors.size(); i++) {
            System.out.println(i + ". " + selectedDoctors.get(i));
        }

        return selectedDoctors.get(Integer.parseInt(scanner.nextLine()));
    }
}
