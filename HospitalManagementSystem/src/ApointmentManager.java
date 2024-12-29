import java.util.Scanner;

class Appointment {
    int patientId;
    int doctorId;
    String timeSlot; // Time slot for the appointment

    public Appointment(int patientId, int doctorId, String timeSlot) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.timeSlot = timeSlot;
    }
}

class AppointmentManager {
    public Appointment[] appointments;
    public int appointmentCount;

    public AppointmentManager(int maxAppointments) {
        appointments = new Appointment[maxAppointments];
        appointmentCount = 0;
    }

    public void allocateDoctorToPatient(int patientId, String healthIssue, Doctor[] doctors, String[][] specializationKeywords) {
        String specialization = findSpecialization(specializationKeywords, healthIssue);
        if (specialization == null) {
            System.out.println("No specialization found for the given health issue.");
            return;
        }

        Doctor[] matchingDoctors = new Doctor[doctors.length];
        int matchCount = 0;
        for (Doctor doctor : doctors) {
            if (doctor != null && doctor.specialization.equalsIgnoreCase(specialization)) {
                System.out.println((matchCount + 1) + ". " + doctor.doctorName + " (" + doctor.specialization + ")");
                matchingDoctors[matchCount++] = doctor;
            }
        }

        if (matchCount == 0) {
            System.out.println("No doctors available for the specified health issue.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number corresponding to the doctor you want to choose:");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > matchCount) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        Doctor selectedDoctor = matchingDoctors[choice - 1];

        System.out.println("Available time slots for Dr. " + selectedDoctor.doctorName + ":");
        for (String time : selectedDoctor.availability) {
            System.out.println("- " + time);
        }

        System.out.println("Enter your preferred time slot (e.g., Monday 10AM):");
        String timeSlot = scanner.next();

        // Check if the time slot is already taken
        for (Appointment appointment : appointments) {
            if (appointment != null &&
                    appointment.doctorId == selectedDoctor.id &&
                    appointment.timeSlot.equalsIgnoreCase(timeSlot)) {
                System.out.println("This time slot is already taken. Please choose a different time.");
                return;
            }
        }

        if (appointmentCount >= appointments.length) {
            System.out.println("Appointment list is full. Cannot book more appointments.");
            return;
        }

        appointments[appointmentCount++] = new Appointment(patientId, selectedDoctor.id, timeSlot);
        System.out.println("Appointment booked successfully with Dr. " + selectedDoctor.doctorName + " at " + timeSlot);
    }

    public void displayAppointments() {
        if (appointmentCount == 0) {
            System.out.println("No appointments booked yet.");
            return;
        }

        for (int i = 0; i < appointmentCount; i++) {
            System.out.println("Patient ID: " + appointments[i].patientId +
                    ", Doctor ID: " + appointments[i].doctorId +
                    ", Time Slot: " + appointments[i].timeSlot);
        }
    }

    private String findSpecialization(String[][] specializationKeywords, String healthIssue) {
        for (String[] keywords : specializationKeywords) {
            for (int i = 1; i < keywords.length; i++) {
                if (healthIssue.equalsIgnoreCase(keywords[i])) {
                    return keywords[0];
                }
            }
        }
        return null;
    }
}
