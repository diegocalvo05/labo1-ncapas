package Service;

import Model.Entity.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PatientService {
    private static PatientService instance;
    private List<Patient> patients;
    private Scanner scanner;
    private static final Pattern DUI_PATTERN = Pattern.compile("^\\d{8}-\\d$");// Regex para validarrr el formato del dui

    private PatientService() {
        this.patients = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static PatientService getInstance() {
        if (instance == null) {
            instance = new PatientService();
        }

        return instance;
    }
// validando el formato del dui
    public static String validarDUI(String dui) {
        if (dui == null || !DUI_PATTERN.matcher(dui).matches()) {
            throw new IllegalArgumentException("Formato de DUI inválido. Use ########-# (8 dígitos, guión, 1 dígito)");
        }
        return dui;
    }

    public static LocalDate validarFecha(String fechaStr) {
        try {
            return LocalDate.parse(fechaStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD");
        }
    }

    public Patient buscarPorDui(String dui) {
        for (Patient p : patients) {
            if (p.getDui().equals(dui)) {
                return p;
            }
        }
        return null;
    }

    public Patient registrarPacienteNuevo(String nombre, String apellido, String dui, LocalDate birthDate) {
        if (!esMayorDeEdad(birthDate)) {
            System.out.println("🔒 El paciente es menor de edad. Se asignará DUI: 00000000-0");
            dui = "00000000-0";
        }
        return new Patient(nombre, apellido, birthDate, dui);
    }

    public void registrarNuevoPaciente() {
        System.out.print("🔎 Ingrese el DUI del paciente (########-#): ");
        String dui = validarDUI(scanner.nextLine());

        Patient existente = buscarPorDui(dui);
        if (existente != null) {
            System.out.println("✅ Paciente encontrado:");
            System.out.println(existente);
            return;
        }

        System.out.println("📝 Registrando nuevo paciente...");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        LocalDate birthDate;
        while (true) {
            try {
                System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
                birthDate = validarFecha(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }

        Patient nuevo = registrarPacienteNuevo(nombre, apellido, dui, birthDate);
        patients.add(nuevo);

        System.out.println("✅ Paciente registrado exitosamente:");
        System.out.println(nuevo.toString());
    }

    private boolean esMayorDeEdad(LocalDate birthDate) {
        return LocalDate.now().minusYears(18).isAfter(birthDate);
    }

    public void listarPacientes() {
        if (patients.isEmpty()) {
            System.out.println("⚠️ No hay pacientes registrados.");
            return;
        }
        patients.forEach(p -> System.out.println(p.toString() + "\n"));
    }

    public List<Patient> buscarPacientesPorNombreYFecha(String nombre, LocalDate fechaNacimiento) {
        List<Patient> encontrados = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getNombre().equalsIgnoreCase(nombre) && p.getBirthDate().equals(fechaNacimiento)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }
}
