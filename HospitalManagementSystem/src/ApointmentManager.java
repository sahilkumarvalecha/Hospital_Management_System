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
    public String toString() {
        return "Doctor ID:" + doctorId + ", Patient ID:" + patientId + ", timeSlot:" + timeSlot;
    }
}

class AppointmentManager {
    PatientManagement pm = new PatientManagement();
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
    public void writeAppointmentsInFile(int patientId,int doctorId, String timeSlot){
        if (appointmentCount == 0) {
            System.out.println("No appointments booked yet.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointmentData.txt", true))) {

            writer.write("Doctor ID:" + doctorId +
                    ", Patient ID:" + patientId +
                    ", timeSlot:" + timeSlot);
            writer.newLine();

            System.out.println("Appointment Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving appointment data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayAppointments() {
        if (appointmentCount == 0) {
            System.out.println("No appointments booked yet.");
            return;
        }

        for (int i = 0; i < appointmentCount; i++) {
            System.out.println(", " +appointments[i]);
        }
    }
    public void cancelAppointment(int patientID) {
        boolean appointmentFound = false;

        // Load appointments from file
        //  loadFromFile();

        // Find and cancel the appointment from the array
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i] != null && appointments[i].patientId == patientID) {
                appointmentFound = true;
                // Shift the array to remove the canceled appointment
                for (int j = i; j < appointmentCount - 1; j++) {
                    appointments[j] = appointments[j + 1];
                }

                appointments[--appointmentCount] = null; // Reduce the count
                System.out.println("Appointment canceled for Patient ID: " + patientID);
                updatePatientBill(patientID);
                break;
            }else {
                System.out.println("Patient not found in system!");
            }

        }
        if (!appointmentFound) {
            System.out.println("No appointment found for Patient ID: " + patientID);
        }
    }
    public void searchAppointmentByDoctorId(int docID) {
        boolean appointmentFound = false;

        if (appointmentCount == 0) {
            System.out.println("No appointments available.");
            return;
        }

        System.out.println("Appointments for Doctor ID: " + docID);
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i] != null && appointments[i].doctorId == docID) {
                System.out.println(appointments[i]);
                appointmentFound = true;
            }
        }

        if (!appointmentFound) {
            System.out.println("No appointments found for Doctor ID: " + docID);
        }
    }
    public Appointment searchAppointmentByDocId(int docID) {
        boolean appointmentFound = false;

        if (appointmentCount == 0) {
            System.out.println("No appointments available.");
            return null;
        }
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i] != null && appointments[i].doctorId == docID) {
                appointmentFound = true;
                return appointments[i];
            }
        }

        if (!appointmentFound) {
            System.out.println("No appointments found for Doctor ID: " + docID);
            return null;
        }
        return null;
    }
    public void writePrescription(int patientId, int doctorId, String prescription) {
        pm.loadFromFile();
        Node patient = pm.SearchPatient(patientId);

        if (patient == null) {
            System.out.println("Patient with ID " + patientId + " not found.");
            return;
        }
        patient.setPrescription(prescription);

        pm.updateFile();
        deleteAppointment(patientId);
        updateFile();
    }
    public  void resize(){

        Appointment[] temp = new Appointment[appointments.length+10];
        for (int i=0; i<appointments.length; i++){
            temp[i] = appointments[i];
        }
        appointments = temp;
    }
    private void deleteAppointment(int patientId) {
        boolean appointmentFound = false;

        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i] != null && appointments[i].patientId == patientId) {
                appointmentFound = true;

                // Shift the array to remove the canceled appointment
                for (int j = i; j < appointmentCount - 1; j++) {
                    appointments[j] = appointments[j + 1];
                }
                appointments[--appointmentCount] = null;
                System.out.println("Appointment deleted for Patient ID: " + patientId);
                break;
            }
        }

        if (!appointmentFound) {
            System.out.println("No appointment found for Patient ID: " + patientId);
        } else {
            // Update the appointments file
            updateFile();
        }
    }
    public void updatePatientBill(int patientId){
        pm.loadFromFile();
        Node curr = pm.head;
        boolean patientFound = false;
        while (curr != null){
            if (curr.billAmount > 0) {
                if (curr.patientId == patientId) {
                    curr.billAmount -= 1000;
                    patientFound = true;
                    break;
                }
                curr = curr.next;
            }else {
                curr.billAmount = 0;
            }
        }
        if (!patientFound) {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
        if (patientFound){
            pm.updateFile();
        }
    }

    public void updateFile() {
        // Step 3: Rewrite the file with the remaining appointments
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointmentData.txt"))) {
            for (int i = 0; i < appointmentCount; i++) {
                if (appointments[i] != null) {
                    writer.write(appointments[i].toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating appointment data: " + e.getMessage());
        }
    }
    public void loadFromFile() {
        try {
            File f = new File("appointmentData.txt");
            if (!f.exists()) {
                f.createNewFile();
            } else {
                FileReader fr = new FileReader(f);
                BufferedReader s = new BufferedReader(fr);

                String line;
                while ((line = s.readLine()) != null) {
                    // Split line by commas and trim excess spaces
                    String[] appointmentData = line.split(",");

                    if (appointmentData.length < 3) {
                        System.out.println("Invalid appointment data format.");
                        continue;  // Skip invalid data
                    }

                    // Parse Doctor ID (manual validation)
                    int doctorId = 0;
                    String[] doctorIdParts = appointmentData[0].split(":");
                    if (doctorIdParts.length > 1) {
                        String doctorIdString = doctorIdParts[1].trim();
                        for (int i = 0; i < doctorIdString.length(); i++) {
                            doctorId = doctorId * 10 + (doctorIdString.charAt(i) - '0');
                        }
                    }

                    // Parse Patient ID (manual validation)
                    int patientId = 0;
                    String[] patientIdParts = appointmentData[1].split(":");
                    if (patientIdParts.length > 1) {
                        String patientIdString = patientIdParts[1].trim();
                        for (int i = 0; i < patientIdString.length(); i++) {
                            patientId = patientId * 10 + (patientIdString.charAt(i) - '0');
                        }
                    }

                    // Parse Time Slot
                    String timeSlot = appointmentData[2].split(":")[1].trim();

                    // Create a new Appointment object and add to the array
                    if (appointmentCount < appointments.length) {
                        appointments[appointmentCount++] = new Appointment(patientId, doctorId, timeSlot);
                    } else if(appointmentCount == appointments.length){
                        resize();
                    }
                }
                s.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading appointment data: " + e.getMessage());
        }
    }
}