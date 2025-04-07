package Service;

import Model.Entity.Cita;
import Model.Entity.Doctor;
import Model.Entity.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CitaService {
    private List<Cita> citas;
    private DoctorService doctorService;
    private PatientService patientService;
    private Scanner scanner;

    public CitaService(DoctorService doctorService, PatientService patientService) {
        this.citas = new ArrayList<>();
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.scanner = new Scanner(System.in);
    }

    public static LocalTime validarHora(String horaStr) {
        try {
            LocalTime hora = LocalTime.parse(horaStr);
            if (hora.isBefore(LocalTime.of(8, 0))) {
                throw new IllegalArgumentException("La hora no puede ser antes de 08:00");
            }
            if (hora.isAfter(LocalTime.of(16, 0))) {
                throw new IllegalArgumentException("La hora no puede ser después de 16:00");
            }
            return hora;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:MM (24h)");
        }
    }

    public Cita makeCita() {
        List<String> specialities = doctorService.getSpecialities();

        System.out.println("📚 Especialidades disponibles:");
        for (int i = 0; i < specialities.size(); i++) {
            System.out.println(i + ". " + specialities.get(i));
        }

        System.out.print("Seleccione el número de la especialidad: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index < 0 || index >= specialities.size()) {
            System.out.println("❌ Selección inválida.");
            return null;
        }

        String especialidad = specialities.get(index);
        Doctor doctor = doctorService.seleccionarDoctorPorEspecialidad(especialidad);
        if (doctor == null) {
            System.out.println("❌ No hay doctores disponibles en esta especialidad.");
            return null;
        }

        System.out.print("Ingrese el nombre del paciente: ");
        String nombre = scanner.nextLine();
        LocalDate fechaNacimiento;
        while (true) {
            try {
                System.out.print("Ingrese la fecha de nacimiento del paciente (YYYY-MM-DD): ");
                fechaNacimiento = PatientService.validarFecha(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }

        List<Patient> posibles = patientService.buscarPacientesPorNombreYFecha(nombre, fechaNacimiento);
        if (posibles.isEmpty()) {
            System.out.println("❌ Paciente no encontrado. Regístrelo primero.");
            return null;
        }

        Patient patient = posibles.size() == 1 ? posibles.get(0) : seleccionarPacienteDeLista(posibles);
        if (patient == null) return null;

        LocalDate fecha;
        while (true) {
            try {
                System.out.print("Ingrese la fecha de la cita (YYYY-MM-DD): ");
                fecha = PatientService.validarFecha(scanner.nextLine());
                if (fecha.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("No se pueden agendar citas en el pasado");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }

        LocalTime hora;
        while (true) {
            try {
                System.out.print("Ingrese la hora de la cita (HH:MM) entre 08:00 y 16:00: ");
                hora = validarHora(scanner.nextLine());
                if (fecha.equals(LocalDate.now()) && hora.isBefore(LocalTime.now())) {
                    throw new IllegalArgumentException("No se pueden agendar citas en horas pasadas del día actual");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }

        if (!esHorarioValido(hora)) {
            System.out.println("❌ La hora ingresada está fuera del horario permitido (08:00 AM - 04:00 PM).");
            return null;
        }

        if (fecha.isBefore(LocalDate.now()) ||
                (fecha.equals(LocalDate.now()) && hora.isBefore(LocalTime.now()))) {
            System.out.println("❌ No se pueden agendar citas en el pasado.");
            return null;
        }

        if (!esDoctorDisponible(doctor, fecha, hora)) {
            System.out.println("❌ El doctor ya tiene una cita en ese horario.");
            return null;
        }

        if (!esPacienteDisponible(patient, fecha, hora)) {
            System.out.println("❌ El paciente ya tiene una cita en ese horario.");
            return null;
        }

        return new Cita(doctor, patient, especialidad, fecha, hora);
    }

    private Patient seleccionarPacienteDeLista(List<Patient> pacientes) {
        System.out.println("Se encontraron varios pacientes:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println(i + ". " + pacientes.get(i));
        }
        System.out.print("Seleccione el número correspondiente: ");
        int seleccion = Integer.parseInt(scanner.nextLine());
        if (seleccion < 0 || seleccion >= pacientes.size()) {
            System.out.println("❌ Selección inválida.");
            return null;
        }
        return pacientes.get(seleccion);
    }

    public void agendarCita() {
        Cita newCita = makeCita();
        if (newCita != null) {
            citas.add(newCita);
            System.out.println("✅ Cita agendada con éxito:");
            System.out.println(newCita);
        }
    }

    private boolean esHorarioValido(LocalTime hora) {
        return hora.isAfter(LocalTime.of(7, 59)) && hora.isBefore(LocalTime.of(16, 1));
    }

    private boolean esDoctorDisponible(Doctor doctor, LocalDate fecha, LocalTime hora) {
        return citas.stream().noneMatch(c ->
                c.getDoctor().equals(doctor) &&
                        c.getFecha().equals(fecha) &&
                        seSuperpone(c.getHora(), hora));
    }

    private boolean esPacienteDisponible(Patient patient, LocalDate fecha, LocalTime hora) {
        return citas.stream().noneMatch(c ->
                c.getPatient().equals(patient) &&
                        c.getFecha().equals(fecha) &&
                        seSuperpone(c.getHora(), hora));
    }

    private boolean seSuperpone(LocalTime existente, LocalTime nueva) {
        return !nueva.isBefore(existente) && nueva.isBefore(existente.plusHours(1));
    }

    public void listarCitas() {
        if (citas.isEmpty()) {
            System.out.println("📭 No hay citas registradas.");
            return;
        }
        System.out.println("📅 Citas registradas:");
        citas.forEach(c -> System.out.println(c.toString()));
    }

    public void showCitaByDoctor(Doctor doctor) {
        List<Cita> filtered = citas.stream()
                .filter(c -> c.getDoctor().equals(doctor)).toList();

        if (filtered.isEmpty()) {
            System.out.println("📭 No hay citas registradas con " + doctor.getNombre());
            return;
        }

        System.out.println("📅 Citas con " + doctor.getNombre() + ":");
        filtered.forEach(c -> System.out.println(c.toString() + "\n"));
    }

    public void showCitaByDate(LocalDate date) {
        List<Cita> filtered = citas.stream()
                .filter(c -> c.getFecha().equals(date)).toList();

        if (filtered.isEmpty()) {
            System.out.println("📭 No hay citas registradas para " + date);
            return;
        }

        System.out.println("📅 Citas registradas para: " + date);
        filtered.forEach(c -> System.out.println(c.toString() + "\n"));
    }

    public void updateCita(Cita original, Cita actualizada) {
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).equals(original)) {
                citas.set(i, actualizada);
                break;
            }
        }
    }

    public void updateCitaMenu() {
        if (citas.isEmpty()) {
            System.out.println("📭 No hay citas para editar.");
            return;
        }

        System.out.println("Seleccione la cita a editar:");
        for (int i = 0; i < citas.size(); i++) {
            System.out.println(i + ". " + citas.get(i));
        }

        int seleccion = Integer.parseInt(scanner.nextLine());
        if (seleccion < 0 || seleccion >= citas.size()) {
            System.out.println("❌ Selección inválida.");
            return;
        }

        Cita original = citas.get(seleccion);
        Cita nueva = makeCita();
        if (nueva != null) {
            updateCita(original, nueva);
            System.out.println("✅ Cita actualizada con éxito.");
        }
    }

    public void cancelarCitaPorPacienteYFecha(String nombrePaciente, LocalDate fecha) {
    }
}
