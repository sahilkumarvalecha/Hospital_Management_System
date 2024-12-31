import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PatientManagement pm = new PatientManagement();
        doctorManagement dm = new doctorManagement();
        AppointmentManager am = new AppointmentManager(20);
      //  Billing b = new Billing();
        Staff st = new Staff();
        pm.loadFromFile();
        dm.loadFromFile();
        am.loadFromFile();
        System.out.println("--------------------------------------");
        System.out.println("Welcome to SPS Hospital");
        System.out.println("--------------------------------------");
        System.out.println("Login As ");
        System.out.println("1- Patient ");
        System.out.println("2- Doctor ");
        System.out.println("3- Staff ");
        System.out.println("4- Exit");
        System.out.println("--------------------------------------");
        System.out.print("Enter your choice: ");
        int choice1 = scanner.nextInt();
        while (true) {
            switch (choice1) {
                case 1:
                    while (true) {
                        System.out.println("--------------------------------------");
                        System.out.println("Patient Menu ");
                        System.out.println("--------------------------------------");
                        System.out.println("1. Book An Appointment");
                        System.out.println("2. View Appointment Details");
                        System.out.println("3. Cancel An Appointment ");
                        System.out.println("4. View My Diagnosis Or Prescription ");
                        System.out.println("5. Pay My Bill");
                        System.out.println("6. Go Back To Main Menu");
                        System.out.println("7. Exit");
                        System.out.println("--------------------------------------");
                        System.out.print("Enter your choice: ");
                        int choice2 = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice2) {
                            case 1:
                                System.out.print("Enter patient ID to book an appointment: ");
                                int patientId = scanner.nextInt();
                                Node curr = pm.SearchPatient(patientId);
                                if (curr == null) {
                                    System.out.println("No patient found with this ID.");
                                    break;
                                }

                                String healthIssue = curr.getPatientHealthIssue();
                                dm.displayDoctorAccToHealthIssue(healthIssue);

                                System.out.print("Choose a doctor (Enter the corresponding number): ");
                                int doctorOption = scanner.nextInt();

                                Doctor selectedDoctor =null;
                                int doctorIndex = 1;

                                for (Doctor doctor : dm.doctors) {
                                    if (doctor != null && doctor.specialization.trim().equalsIgnoreCase(dm.findSpecialization(dm.specializationKeywords, healthIssue))) {
                                        if (doctorIndex == doctorOption) {
                                            selectedDoctor = doctor;
                                            break;
                                        }
                                        doctorIndex++;
                                    }
                                }

                                if (selectedDoctor == null) {
                                    System.out.println("Invalid doctor selection.");
                                    break;
                                }

                                System.out.println("Available time slots for " + selectedDoctor.doctorName + ":");
                                for (int i = 0; i < selectedDoctor.availability.length; i++) {
                                    System.out.println((i + 1) + ". " + selectedDoctor.availability[i]);
                                }

                                System.out.print("Enter the number corresponding to your preferred time slot: ");
                                int timeSlotOption = scanner.nextInt();

                                if (timeSlotOption < 1 || timeSlotOption > selectedDoctor.availability.length) {
                                    System.out.println("Invalid time slot selection.");
                                    break;
                                }

                                String timeSlot = selectedDoctor.availability[timeSlotOption - 1];

                                // Check and book the appointment
                                if (am != null) {
                                    boolean isSlotAvailable = true;

                                    // Check if the time slot is already taken
                                    for (Appointment appointment : am.appointments)  {
                                        if (appointment != null &&
                                                appointment.doctorId == selectedDoctor.id &&
                                                appointment.timeSlot.equalsIgnoreCase(timeSlot)) {
                                            isSlotAvailable = false;
                                            break;
                                        }
                                    }

                                    if (!isSlotAvailable) {
                                        System.out.println("This time slot is already taken. Please choose a different time.");
                                    } else {
                                            am.appointments[am.appointmentCount++] = new Appointment(patientId, selectedDoctor.id, timeSlot);

                                            try {
                                                am.writeAppointmentsInFile(patientId,selectedDoctor.id,timeSlot);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            System.out.println("Appointment booked successfully with " + selectedDoctor.doctorName + " at " + timeSlot);
                                    }

                                   // b.updatePatientFile(patientId, curr.PatientName, curr.PatientAge, curr.PatientPhoneNUM, curr.PatientGender, curr.PatientHealthIssue);
                                }

                                break;
                            case 2:
                                System.out.print("Enter patient Id to view appointment details: ");
                                int searchingPatientId = scanner.nextInt();
                                for (Appointment appointment : am.appointments) {
                                    if (appointment != null && appointment.patientId == searchingPatientId) {
                                        System.out.println("You have a appointment with " +dm.searchDoctorById(appointment.doctorId)+ " at:" +appointment.timeSlot);
                                        break;
                                    }
                                }

                                break;
                            case 3:
                                System.out.println("Enter patient id to delete appointment: ");
                                searchingPatientId = scanner.nextInt();
                                am.cancelAppointment(searchingPatientId);
                                break;
                            case 4:
//
                                break;

                            case 5:

                                break;
                            case 6:
                                System.out.println("Going back to the main menu...");
                                break;
                            case 7:
                                System.out.println("Exiting the system. Goodbye!");
                                scanner.close();
                                return;
//                            System.out.print("Enter the filename to load patients from: ");
//                            pm.loadPatientsFromFile("filename.txt");
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                        if (choice2 == 6) break;
                    }
                    break;
                case 2:
                    System.out.println("--------------------------------------");
                    System.out.println("Doctor Menu ");
                    System.out.println("--------------------------------------");
                    System.out.println("1. View Personal Schedule");
                    System.out.println("2. View Assigned Patients");
                    System.out.println("3. Add Diagnosis or prescription For Patient");
                    System.out.println("4. Update Personal Schedule");
                    System.out.println("5. Generate Billing Information");
                    System.out.println("6. Go Back To Main Menu");
                    System.out.println("7. Exit");
                    System.out.println("--------------------------------------");
                    System.out.print("Enter your choice: ");
                    int choice3 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice3) {
                        case 1:
                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                        case 5:

                            break;
                        case 6:
                            System.out.println("Going back to the main menu...");
                            break;
                        case 7:
                            System.out.println("Exiting the system. Goodbye!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    if (choice3 == 6) break;
                    break;
                case 3:
                    System.out.println("--------------------------------------");
                    System.out.println("Staff Menu ");
                    System.out.println("--------------------------------------");
                    System.out.println("1. Add New Patient");
                    System.out.println("2. Update Patient Information");
                    System.out.println("3. Add New Doctor");
                    System.out.println("4. Update Doctor Information");
                    System.out.println("5. View All Patients");
                    System.out.println("6. Search For A Patient");
                    System.out.println("7. Assign Doctor To Patient");
                    System.out.println("8. Update Doctor Schedule");
                    System.out.println("9. View All Doctors");
                    System.out.println("10. Search For A Doctor");
                    System.out.println("11. Generate Bill For Patient");
                    System.out.println("12. View All Billing Records");
                    System.out.println("13. Go Back To Main Menu");
                    System.out.println("14. Exit");
                    System.out.println("--------------------------------------");
                    System.out.print("Enter your choice: ");
                    int choice4 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice4) {

                        case 1:
                            System.out.print("Enter Patient Name: ");
                            String patientName = scanner.nextLine();
                            System.out.print("Enter Patient Age: ");
                            int age = scanner.nextInt();
                            System.out.print("Enter Patient Phone Number: ");
                            String phone = scanner.next();
                            scanner.nextLine(); // Consume newline
                            System.out.print("Enter Patient Gender: ");
                            String gender = scanner.nextLine();
                            System.out.print("Enter Patient Health Issue: ");
                            String healthIssue = scanner.nextLine();
//                            pm.createFile();

                            st.addNewPatient(patientName, age, phone, gender, healthIssue);
                            System.out.println("Patient added successfully with ID: " + pm.getPatientIdCounter());

                            break;
                        case 2:
                            System.out.print("Enter patient Id to update: ");
                            int id = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enter New patient Name: ");
                            String newName = scanner.nextLine();
                            System.out.println("Enter New patient age: ");
                            int newAge = scanner.nextInt();
                            System.out.println("Enter New patient gender: ");
                            String newGender = scanner.nextLine();
                            System.out.println("Enter New patient health Issue: ");
                            String newHealthIssue = scanner.nextLine();
                            st.updatePatientInfo(id, newName, newAge, newGender, newHealthIssue);
                            break;
                        case 3:
                            System.out.print("Enter Doctor ID: ");
                            int ID = scanner.nextInt();
                            System.out.print("Enter Doctor Name: ");
                            String doctorName = scanner.next();
                            scanner.nextLine();
                            System.out.print("Enter Doctor specialization: ");
                            String specialization = scanner.nextLine();
                            System.out.print("Enter Doctor Phone Number: ");
                            int number = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            System.out.print("How many slots you want to add in your Schedule? ");
                            int slotCount = scanner.nextInt();
                            if(slotCount <=0){
                                System.out.print("Slot Count Can't be less than 0");
                                //send him back to the input for how many slots
                            }
                            else if(slotCount >=20){
                                System.out.print("Slot Can't Be Greater than 20");
                                //send him back to the input for how many slots
                            }
                            else {
//                                dm.createFile();
                                String[] availability = new String[slotCount];
                                for (int i = 0; i < availability.length; i++) {
                                    System.out.print("Enter Doctor Availability For Slots(e.g., Tuesday 2PM): ");
                                    availability[i] = scanner.next();
                                    scanner.nextLine();
                                }
                                st.addNewDoctor(ID, doctorName, specialization, availability, number);
                                System.out.println("Doctor added successfully!");
                            }
                            break;
                        case 4:
                            System.out.print("Enter Doctor Id to update: ");
                            int docId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enter New patient Name: ");
                            String docName = scanner.nextLine();
                            System.out.println("Enter Doctor Specialization: ");
                            String docInfo = scanner.nextLine();
                            System.out.println("Enter Doctor Availability: ");
                            String docSchedule = scanner.nextLine();
                            System.out.println("How many slots you want to add in your Schedule?");
                            slotCount = scanner.nextInt();
                            String[] availability = new String[slotCount];
                            if(slotCount<=0){
                                System.out.print("Slot Count Can't be less than 0");
                                //send him back to the input for how many slots
                            }
                            else {

                                for (int i = 0; i < availability.length; i++) {
                                    System.out.print("Enter Doctor Availability For Slots(e.g., Tuesday 2PM): ");
                                    availability[i] = scanner.nextLine();
                                }

                            }
                            System.out.println("Enter Doctor Contact Number: ");
                            int phoneNum = scanner.nextInt();
                            st.updateDoctorInfo(docId, docName, docInfo, availability, phoneNum);
                            System.out.println("Doctor added successfully!");

                            break;
                        case 5:
                            pm.DisplayDataOfPatient();
                            break;
                        case 6:
                            System.out.print("Enter the Id of the patient you want to search : ");
                            int patientId = scanner.nextInt();
                            st.searchPatient(patientId);
                            break;
                        case 7:
                            System.out.println("Enter Patient Id:");
                            patientId = scanner.nextInt();


                            break;
                        case 8:

                            break;
                        case 9:
                            dm.displayAllDoctors();
                            break;
                        case 10:
                            System.out.print("Enter the specialization name to search for a doctor: ");
                            specialization = scanner.nextLine();
                            st.searchDoctor(specialization);
                            break;
                        case 11:

                            break;
                        case 12:

                            break;
                        case 13:
                            System.out.println("Going back to the main menu...");
                            break;
                        case 14:
                            System.out.println("Exiting the system. Goodbye!");
                            //  System.exit(0);
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    if (choice4 == 13) break;
                    break;

                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
