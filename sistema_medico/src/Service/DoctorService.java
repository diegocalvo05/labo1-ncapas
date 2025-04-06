package Service;

import Model.Entity.Doctor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DoctorService {
    private static DoctorService instance;
    private List<Doctor> doctors;
    private List<String> specialities;
    private Scanner scanner;

    private DoctorService() {
        this.doctors = new ArrayList<>();
        this.specialities = new ArrayList<>(Arrays.asList("Dermatologo", "Cardiologo", "Neumologo", "Internista", "Neurologo"));
        this.scanner = new Scanner(System.in);
    }

    public static DoctorService getInstance() {
        if (instance == null) {
            instance = new DoctorService();
        }
        return instance;
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

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("⚠️ Formato inválido. Use el formato YYYY-MM-DD.");
            }
        }
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

        LocalDate birthDate = readDate("Fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate fechaReclutamiento = readDate("Fecha de reclutamiento (YYYY-MM-DD): ");

        System.out.println("Elija su especialidad (Digite el número): ");
        for (int i = 0; i < specialities.size(); i++) {
            System.out.println((i + 1) + ". " + specialities.get(i));
        }

        int opcionEspecialidad = -1;
        while (opcionEspecialidad < 1 || opcionEspecialidad > specialities.size()) {
            try {
                System.out.print("Especialidad: ");
                opcionEspecialidad = Integer.parseInt(scanner.nextLine());
                if (opcionEspecialidad < 1 || opcionEspecialidad > specialities.size()) {
                    System.out.println("⚠️ Opción fuera de rango.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, ingrese un número válido.");
            }
        }

        String especialidad = specialities.get(opcionEspecialidad - 1);
        Doctor newDoctor = addDoctor(nombre, apellido, birthDate, dui, fechaReclutamiento, especialidad);

        System.out.println("✅ Doctor registrado exitosamente:");
        System.out.println("Código del Doctor: " + newDoctor.getCodigo());
        System.out.println(newDoctor.toString());
    }

    public void getAllDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No se encontraron doctores");
            return;
        }

        doctors.forEach(System.out::println);
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public Doctor seleccionarDoctorPorEspecialidad(String especialidad) {
        List<Doctor> selectedDoctors = doctors.stream()
                .filter(doctor -> doctor.getEspecialidad().equalsIgnoreCase(especialidad))
                .toList();

        if (selectedDoctors.isEmpty()) {
            System.out.println("❌ No se encontraron doctores con esa especialidad.");
            return null;
        }

        System.out.println("👨‍⚕️ Doctores disponibles con especialidad '" + especialidad + "':");
        for (int i = 0; i < selectedDoctors.size(); i++) {
            Doctor d = selectedDoctors.get(i);
            System.out.println(i + ". " + d.getNombre() + " " + d.getApellido() + " - " + d.getCodigo());
        }

        int index = -1;
        while (true) {
            System.out.print("Ingrese una opción válida: ");
            String input = scanner.nextLine();
            try {
                index = Integer.parseInt(input);
                if (index >= 0 && index < selectedDoctors.size()) {
                    break;
                } else {
                    System.out.println("⚠️ Opción fuera de rango.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Debe ingresar un número válido.");
            }
        }

        return selectedDoctors.get(index);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public Doctor buscarDoctorPorNombre(String nombre) {
        for (Doctor d : doctors) {
            if (d.getNombre().equalsIgnoreCase(nombre)) {
                return d;
            }
        }
        return null;
    }
}
