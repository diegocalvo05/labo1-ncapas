package Service;

import Model.Entity.Cita;
import Model.Entity.Doctor;
import Model.Entity.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CitaService {
    private List<Cita> citas;
    private DoctorService doctorService;//Se cambiara por el nombre que le pongas al servicio
    private PatientService patientService;
    private Scanner scanner;

    public CitaService(DoctorService doctorService, PatientService patientService) {
        this.citas = new ArrayList<>();
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.scanner = new Scanner(System.in);
    }

    public void agendarCita() {
        System.out.println("📚 Especialidades disponibles:");
        doctorService.mostrarEspecialidades();//Se cambiara dependiendo lo que hagas
        System.out.print("Seleccione la especialidad: ");
        String especialidad = scanner.nextLine();

        Doctor doctor = doctorService.seleccionarDoctorPorEspecialidad(especialidad);// Se cambiara dependiendo lo que hagas
        if (doctor == null) {
            System.out.println("No hay doctores disponibles en esta especialidad.");
            return;
        }

        System.out.print("Ingrese el DUI del paciente: ");
        String pacienteDui = scanner.nextLine();
        Patient patient = patientService.buscarPorDui(pacienteDui);

        System.out.print("Ingrese la fecha de la cita (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());

        System.out.print("Ingrese la hora de la cita (HH:MM) entre 08:00 y 16:00: ");
        LocalTime hora = LocalTime.parse(scanner.nextLine());

        // Validación de hora permitida
        if (!esHorarioValido(hora)) {
            System.out.println("La hora ingresada está fuera del horario permitido (08:00 AM - 04:00 PM).");
            return;
        }

        // Validar que la cita no sea en el pasado y sea en el futuro
        if (fecha.isBefore(LocalDate.now()) || (fecha.equals(LocalDate.now()) && hora.isBefore(LocalTime.now()))) {
            System.out.println("No se pueden agendar citas en el pasado.");
            return;
        }

        // Validar disponibilidad del doctor
        if (!esDoctorDisponible(doctor, fecha, hora)) {
            System.out.println("El doctor ya tiene una cita en ese horario.");
            return;
        }

        // Validar disponibilidad del paciente
        if (!esPacienteDisponible(patient, fecha, hora)) {
            System.out.println("El paciente ya tiene una cita en ese horario.");
            return;
        }

        // Crear y agregar la cita
        Cita nuevaCita = new Cita(doctor, patient, especialidad, fecha, hora);
        citas.add(nuevaCita);
        System.out.println("✅ Cita agendada con éxito:");
        System.out.println(nuevaCita);
    }

    private boolean esHorarioValido(LocalTime hora) {
        return hora.isAfter(LocalTime.of(7, 59)) && hora.isBefore(LocalTime.of(16, 1));
    }

    private boolean esDoctorDisponible(Doctor doctor, LocalDate fecha, LocalTime hora) {
        return citas.stream()
                .noneMatch(c -> c.getDoctor().equals(doctor)
                        && c.getFecha().equals(fecha)
                        && seSuperpone(c.getHora(), hora));
    }

    private boolean esPacienteDisponible(Patient patient, LocalDate fecha, LocalTime hora) {
        return citas.stream()
                .noneMatch(c -> c.getPatient().equals(patient)
                        && c.getFecha().equals(fecha)
                        && seSuperpone(c.getHora(), hora));
    }

    // revisa si dos horas se superponen considerando duración de 1h
    private boolean seSuperpone(LocalTime existente, LocalTime nueva) {
        return !nueva.isBefore(existente) && nueva.isBefore(existente.plusHours(1));
    }

    public void listarCitas() {
        if (citas.isEmpty()) {
            System.out.println("📭 No hay citas registradas.");
            return;
        }
        System.out.println("📅 Citas registradas:");
        citas.forEach(System.out::println);
    }
}