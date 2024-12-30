import java.io.*;
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
    public void createFile(){
        File myFile = new File("appointmentData.txt");
        try {
            if (myFile.createNewFile()) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }
    public void writeAppointmentsInFile(int patientId,int doctorId, String timeSlot) throws IOException {
        if (appointmentCount == 0) {
            System.out.println("No appointments booked yet.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointmentData.txt", true))) {
            for (int i = 0; i < appointmentCount; i++) {
                Appointment appointment = appointments[i];
                if (appointment != null) {
                    writer.write("Doctor ID:" + doctorId +
                            ", Patient ID:" + patientId +
                            ", timeSlot:" + timeSlot);
                    writer.newLine();
                }
            }
            System.out.println("Appointment Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving appointment data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void loadFromFile() {
        File file = new File("appointmentData.txt");
        if (!file.exists()) {
            System.out.println("No previous appointment data found.");
            return;
        }
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println("Reading line: " + line); // Debug log
                String[] fields = line.split(", ");
                int doctorId = Integer.parseInt(fields[0].split(":")[1].trim());
                int patientId = Integer.parseInt(fields[1].split(":")[1].trim());
                String timeSlot = fields[2].split(":")[1].trim();

                appointments[appointmentCount++] = new Appointment(patientId, doctorId, timeSlot);
                System.out.println("Loaded appointment: Patient ID " + patientId + ", Doctor ID " + doctorId + ", Time Slot " + timeSlot); // Debug log
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading appointment data: " + e.getMessage());
            e.printStackTrace();
        }
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
}
