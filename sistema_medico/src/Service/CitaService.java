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
    private DoctorService doctorService;
    private PatientService patientService;

    public CitaService(DoctorService doctorService, PatientService patientService) {
        this.citas = new ArrayList<>();
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public void agendarCita(Doctor doctor, Patient patient, String especialidad, LocalDate fecha, LocalTime hora) {
        if (!esHorarioValido(hora)) {
            System.out.println("La hora ingresada está fuera del horario permitido (8:00 AM - 4:00 PM).");
            return;
        }

        if (!esDoctorDisponible(doctor, fecha, hora)) {
            System.out.println("El doctor ya tiene una cita a esa hora.");
            return;
        }

        if (!esPacienteDisponible(patient, fecha, hora)) {
            System.out.println("El paciente ya tiene una cita a esa hora.");
            return;
        }

        Cita nuevaCita = new Cita(doctor, patient, especialidad, fecha, hora);
        citas.add(nuevaCita);
        System.out.println("✅ Cita agendada con éxito:\n" + nuevaCita);
    }

    private boolean esHorarioValido(LocalTime hora) {
        return hora.isAfter(LocalTime.of(7, 59)) && hora.isBefore(LocalTime.of(16, 1));
    }

    private boolean esDoctorDisponible(Doctor doctor, LocalDate fecha, LocalTime hora) {
        return citas.stream()
                .noneMatch(c -> c.getDoctor().equals(doctor) && c.getFecha().equals(fecha) && c.getHora().equals(hora));
    }

    private boolean esPacienteDisponible(Patient patient, LocalDate fecha, LocalTime hora) {
        return citas.stream()
                .noneMatch(c -> c.getPaciente().equals(patient) && c.getFecha().equals(fecha) && c.getHora().equals(hora));
    }

    public void listarCitas() {
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }
        citas.forEach(System.out::println);
    }
}