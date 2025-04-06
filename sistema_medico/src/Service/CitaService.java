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
        this.doctorService = new DoctorService();
        this.patientService = new PatientService();
        this.scanner = new Scanner(System.in);
    }

    public Cita makeCita() {
        System.out.println("📚 Especialidades disponibles:");
        doctorService.showSpecialities();//Se cambiara dependiendo lo que hagas
        System.out.print("Seleccione la especialidad: ");
        String especialidad = scanner.nextLine();

        Doctor doctor = doctorService.seleccionarDoctorPorEspecialidad(especialidad);// Se cambiara dependiendo lo que hagas
        if (doctor == null) {
            System.out.println("No hay doctores disponibles en esta especialidad.");
            return null;
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
            return null;
        }

        // Validar que la cita no sea en el pasado y sea en el futuro
        if (fecha.isBefore(LocalDate.now()) || (fecha.equals(LocalDate.now()) && hora.isBefore(LocalTime.now()))) {
            System.out.println("No se pueden agendar citas en el pasado.");
            return null;
        }

        // Validar disponibilidad del doctor
        if (!esDoctorDisponible(doctor, fecha, hora)) {
            System.out.println("El doctor ya tiene una cita en ese horario.");
            return null;
        }

        // Validar disponibilidad del paciente
        if (!esPacienteDisponible(patient, fecha, hora)) {
            System.out.println("El paciente ya tiene una cita en ese horario.");
            return null;
        }

        // Crear y agregar la cita

        return new Cita(doctor, patient, especialidad, fecha, hora);
    }

    public void agendarCita() {
        Cita newCita = makeCita();
        citas.add(newCita);
        System.out.println("✅ Cita agendada con éxito:");
        System.out.println(newCita);
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
        citas.forEach(c -> System.out.println(c.toString()));
    }

    public void showCitaByDoctor(Doctor doctor) {
        List<Cita> filteredCitas = citas.stream().filter(c -> c.getDoctor().equals(doctor)).toList();

        if (filteredCitas.isEmpty()) {
            System.out.println("📭 No hay citas registradas con " + doctor.getNombre());
            return;
        }

        System.out.println("📅 Citas registradas:");
        filteredCitas.forEach(c -> System.out.println(c.toString() + "\n\n\n"));
    }

    public void showCitaByDate(LocalDate date) {
        List<Cita> filteredCitas = citas.stream().filter(c -> c.getFecha().equals(date)).toList();

        if (filteredCitas.isEmpty()) {
            System.out.println("📭 No hay citas registradas para " + date.toString());
            return;
        }

        System.out.println("📅 Citas registradas para: " + date);
        filteredCitas.forEach(c -> System.out.println(c.toString() + "\n\n\n"));
    }

    public void updateCita(Cita cita, Cita newCita) {
        for(int i = 0; i < citas.size(); i++) {
            if(citas.get(i).equals(cita)) {
                citas.set(i, newCita);
            }
        }
    }

    public void updateCitaMenu() {
        System.out.println("Seleccione la cita a editar: ");

        for(int i = 0; i < citas.size(); i++) {
            System.out.println(i + ". " + citas.get(i).toString());
        }

        Cita selectedCita = citas.get(Integer.parseInt(scanner.nextLine()));
        Cita updatedCita = makeCita();

        updateCita(selectedCita, updatedCita);
    }
}