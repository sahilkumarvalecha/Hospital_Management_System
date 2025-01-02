import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        PatientManagement pm = new PatientManagement();
        doctorManagement dm = new doctorManagement();
        AppointmentManager am = new AppointmentManager(20);
        Staff st = new Staff();
        pm.loadFromFile();
        dm.loadFromFile();
        am.loadFromFile();
        int choice1;
        boolean isMainMenu = true; // Flag to control if we need to show main menu
        while (isMainMenu) {
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
            // Exception handling for invalid input
            try {
                choice1 = scanner.nextInt();

                if (choice1 < 1 || choice1 > 4) {
                    System.out.println("Invalid choice. Please try again.");
                } else {
                    switch (choice1) {
                        case 1:
                            int choice2 = 0;
                            do {
                                System.out.println("--------------------------------------");
                                System.out.println("Patient Menu ");
                                System.out.println("--------------------------------------");
                                System.out.println("1. Book An Appointment");
                                System.out.println("2. View Appointment Details");
                                System.out.println("3. Cancel An Appointment ");
                                System.out.println("4. View My Prescription ");
                                System.out.println("5. Pay My Bill");
                                System.out.println("6. Go Back To Main Menu");
                                System.out.println("7. Exit");
                                System.out.println("--------------------------------------");
                                System.out.print("Enter your choice: ");
                                try {
                                    choice2 = scanner.nextInt();
                                    scanner.nextLine();  // Consume the newline
                                    if (choice2 < 1 || choice2 > 7) {
                                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                                    } else {
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
                                                String specialization = dm.findSpecialization(dm.specializationKeywords, healthIssue);

                                                if (specialization == null || specialization.isEmpty()) {
                                                    System.out.println("No specialization found for the given health issue.");
                                                    break;
                                                }

                                                dm.displayDoctorAccToHealthIssue(healthIssue);

                                                System.out.print("Choose a doctor (Enter the corresponding number): ");
                                                int doctorOption = scanner.nextInt();

                                                Doctor selectedDoctor = null;
                                                int doctorIndex = 1;

                                                for (Doctor doctor : dm.doctors) {
                                                    if (doctor != null && doctor.specialization.equalsIgnoreCase(specialization)) {
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
                                                    for (Appointment appointment : am.appointments) {
                                                        if (appointment != null &&
                                                                appointment.doctorId == selectedDoctor.id &&
                                                                appointment.timeSlot.equalsIgnoreCase(timeSlot)) {
                                                            isSlotAvailable = false;
                                                            break;
                                                        }
                                                    }

                                                    if (!isSlotAvailable) {
                                                        System.out.println("This time slot is already taken. Please choose a different time.");
                                                        break;
                                                    } else {
                                                        am.appointments[am.appointmentCount++] = new Appointment(patientId, selectedDoctor.id, timeSlot);
                                                        am.writeAppointmentsInFile(patientId, selectedDoctor.id, timeSlot);
                                                        System.out.println("Appointment booked successfully with " + selectedDoctor.doctorName + " at " + timeSlot);
                                                        if (am.appointmentCount == am.appointments.length) {
                                                            am.resize();
                                                        }
                                                        pm.updatePatientBill(patientId, 1000);
                                                    }
                                                }

                                                break;


                                            case 2:
                                                System.out.print("Enter patient Id to view appointment details: ");
                                                int searchingPatientId = scanner.nextInt();
                                                for (Appointment appointment : am.appointments) {
                                                    if (appointment != null && appointment.patientId == searchingPatientId) {
                                                        System.out.println("You have a appointment with " +dm.searchDoctorById(appointment.doctorId).doctorName+ " at:" +appointment.timeSlot);
                                                        break;
                                                    }
                                                }

                                                break;
                                            case 3:
                                                System.out.print("Enter patient id to delete appointment: ");
                                                searchingPatientId = scanner.nextInt();
                                                am.cancelAppointment(searchingPatientId);
                                                am.updateFile();
                                                break;
                                            case 4:
                                                System.out.print("Enter patient id to View Prescription: ");
                                                searchingPatientId = scanner.nextInt();
                                                Node current = pm.SearchPatient(searchingPatientId);
                                                System.out.println("Patient Name: " + current.PatientName +
                                                        "\n Prescription: "+ current.prescription);
                                                break;
                                            case 5:
                                                System.out.print("Enter patient id to pay Bill: ");
                                                searchingPatientId = scanner.nextInt();
                                                pm.payMyBill(searchingPatientId);
                                                break;
                                            case 6:
                                                System.out.println("Going back to the main menu...");
                                                isMainMenu = true; // Set flag to true to return to main menu
                                                break; // Exit the patient menu loop
                                            case 7:
                                                System.out.println("Exiting the system. Goodbye!");
                                                scanner.close();
                                                System.exit(0);
                                                break;
                                            default:
                                                System.out.println("Invalid choice. Please try again.");
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input! Please enter a valid input.");
                                    scanner.nextLine(); // Clear the invalid input
                                }
                            } while (choice2 != 6); // Exit the loop if choice is 6
                            break; // Exit the patient menu and return to main menu
                        case 2:
                            int choice3 = 0;
                            do {
                                System.out.println("--------------------------------------");
                                System.out.println("Doctor Menu ");
                                System.out.println("--------------------------------------");
                                System.out.println("1. View Personal Schedule");
                                System.out.println("2. View Assigned Patients");
                                System.out.println("3. Check Patient");
                                System.out.println("4. Generate Billing Information");
                                System.out.println("5. Go Back To Main Menu");
                                System.out.println("6. Exit");
                                System.out.println("--------------------------------------");
                                System.out.print("Enter your choice: ");
                                try {
                                    choice3 = scanner.nextInt();
                                    scanner.nextLine();  // Consume the newline
                                    if (choice3 < 1 || choice3 > 7) {
                                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                                    } else {
                                        switch (choice3) {
                                            case 1:
                                                System.out.println("Enter Your Id To View Schedule");
                                                int searchingdocID = scanner.nextInt();
                                                dm.searchDoctorById(searchingdocID);
                                                System.out.println("Your Schedule Is: ");
                                                for(int i=0; i<dm.searchDoctorById(searchingdocID).availability.length;i++){
                                                    System.out.println(" "+dm.searchDoctorById(searchingdocID).availability[i]);
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Enter Your Id To View Schedule");
                                                searchingdocID = scanner.nextInt();
                                                am.searchAppointmentByDoctorId(searchingdocID);
                                                break;
                                            case 3:
                                                System.out.println("Enter Your Id To Checkup");
                                                searchingdocID = scanner.nextInt();
                                                scanner.nextLine();
                                                if(am.searchAppointmentByDocId(searchingdocID) == null) {
                                                    System.out.println();
                                                }
                                                else{
                                                    int foundPatientId = am.searchAppointmentByDocId(searchingdocID).patientId;
                                                    Node curr = pm.SearchPatient(foundPatientId);
                                                    System.out.println("Patient Found! \n" +
                                                            "Patient Name: " + curr.PatientName +
                                                            "\nPatient Health Issue:" + curr.PatientHealthIssue);
                                                    System.out.println("Enter Your Prescription: ");
                                                    String prescription = scanner.nextLine();
                                                    am.writePrescription(foundPatientId, searchingdocID,prescription);

                                                }
                                                break;
                                            case 4:

                                                break;
                                            case 5:
                                                System.out.println("Going back to the main menu...");
                                                isMainMenu = true; // Set flag to true to return to main menu
                                                break; // Exit the patient menu loop
                                            case 6:
                                                System.out.println("Exiting the system. Goodbye!");
                                                scanner.close();
                                                System.exit(0);
                                            default:
                                                System.out.println("Invalid choice. Please try again.");
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input! Please enter a valid input.");
                                    scanner.nextLine(); // Clear the invalid input
                                }
                            } while (choice3 != 5); // Exit the loop if choice is 6
                            break; // Exit the patient menu and return to main menu
                        case 3:
                            int choice4 = 0;
                            do {
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
                                System.out.println("8. View All Doctors");
                                System.out.println("9. Search For A Doctor");
                                System.out.println("10. Show Bill For Patient");
                                System.out.println("11. Go Back To Main Menu");
                                System.out.println("12. Exit");
                                System.out.println("--------------------------------------");
                                System.out.print("Enter your choice: ");
                                try {
                                    choice4 = scanner.nextInt();
                                    scanner.nextLine();  // Consume the newline
                                    if (choice4 < 1 || choice4 > 14) {
                                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                                    } else {
                                        switch (choice4) {
                                            case 1:
                                                System.out.print("Enter Patient Name: ");
                                                String patientName = scanner.nextLine();

                                                // Input and validate patient age
                                                int age = 0;
                                                while (true) {
                                                    try {
                                                        System.out.print("Enter Patient Age: ");
                                                        age = scanner.nextInt();
                                                        pm.validateAge(age); // Assuming this method throws an exception for invalid age
                                                        break; // Exit the loop if age is valid
                                                    } catch (Exception e) {
                                                        System.out.println(e.getMessage()); // Print validation error message
                                                        scanner.nextLine(); // Clear invalid input
                                                    }
                                                }

                                                // Input and validate patient phone number
                                                String phone = "";
                                                while (true) {
                                                    try {
                                                        System.out.print("Enter Patient Phone Number: ");
                                                        phone = scanner.next();
                                                        pm.validatePhoneNumber(phone); // Assuming this method throws an exception for invalid phone numbers
                                                        break; // Exit the loop if phone number is valid
                                                    } catch (Exception e) {
                                                        System.out.println(e.getMessage()); // Print validation error message
                                                    }
                                                }
                                                scanner.nextLine(); // Consume leftover newline

                                                System.out.print("Enter Patient Gender: ");
                                                String gender = scanner.nextLine();

                                                System.out.print("Enter Patient Health Issue: ");
                                                String healthIssue = scanner.nextLine();

                                                st.addNewPatient(patientName, age, phone, gender, healthIssue);
                                                System.out.println(" Patient added successfully! ");

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
                                                System.out.print("Enter Doctor Name: ");
                                                String doctorName = scanner.nextLine();
                                                scanner.nextLine();
                                                System.out.print("Enter Doctor specialization: ");
                                                String specialization = scanner.nextLine();
                                                System.out.print("Enter Doctor Phone Number: ");
                                                int number = scanner.nextInt();
                                                scanner.nextLine(); // Consume newline
                                                int slotCount = 0; // Initialize slot count
                                                boolean isValidSlotCount = false;
                                                while (!isValidSlotCount) {
                                                    System.out.print("How many slots you want to add in your Schedule? (Enter a number between 1 and 20): ");
                                                    slotCount = scanner.nextInt();
                                                    scanner.nextLine(); // Consume newline

                                                    if (slotCount >= 1 && slotCount <= 20) {
                                                        isValidSlotCount = true; // Exit the loop if the slot count is valid
                                                    } else {
                                                        System.out.println("Invalid choice. Please enter a number between 1 and 20.");
                                                    }
                                                }
                                                String[] availability = new String[slotCount];
                                                for (int i = 0; i < availability.length; i++) {
                                                    System.out.print("Enter Doctor Availability For Slots(e.g., Tuesday 2PM): ");
                                                    availability[i] = scanner.nextLine();
                                                    scanner.nextLine();
                                                }
                                                st.addNewDoctor(doctorName, specialization, availability, number);
                                                System.out.println("Doctor added successfully!");
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
                                                scanner.nextLine();
                                                availability = new String[slotCount];
                                                if (slotCount <= 0) {
                                                    System.out.print("Slot Count Can't be less than 0");
                                                    //send him back to the input for how many slots
                                                } else {

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
                                                System.out.print("Enter patient ID to book an appointment: ");
                                                patientId = scanner.nextInt();
                                                Node curr = pm.SearchPatient(patientId);
                                                if (curr == null) {
                                                    System.out.println("No patient found with this ID.");
                                                    break;
                                                }

                                                healthIssue = curr.getPatientHealthIssue();
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

                                                        am.writeAppointmentsInFile(patientId,selectedDoctor.id,timeSlot);
                                                        System.out.println("Appointment booked successfully with " + selectedDoctor.doctorName + " at " + timeSlot);
                                                    }
                                                    pm.updatePatientBill(patientId, 1000);
                                                    // b.updatePatientFile(patientId, curr.PatientName, curr.PatientAge, curr.PatientPhoneNUM, curr.PatientGender, curr.PatientHealthIssue);
                                                }
                                                break;
                                            case 8:
                                                dm.displayAllDoctors();
                                                break;
                                            case 9:
                                                System.out.print("Enter the specialization name to search for a doctor: ");
                                                specialization = scanner.nextLine();
                                                st.searchDoctor(specialization);
                                                break;
                                            case 10:
                                                System.out.print("Enter patient Id for checking bill: ");
                                                patientId = scanner.nextInt();
                                                st.showBillForPatient(patientId);
                                                break;
                                            case 11:
                                                System.out.println("Going back to the main menu...");
                                                isMainMenu = true; // Set flag to true to return to main menu
                                                break; // Exit the patient menu loop
                                            case 12:
                                                System.out.println("Exiting the system. Goodbye!");
                                                //  System.exit(0);
                                                scanner.close();
                                                System.exit(0);
                                            default:
                                                System.out.println("Invalid choice. Please try again.");
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input! Please enter a valid input.");
                                    scanner.nextLine(); // Clear the invalid input
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } while (choice4 != 11); // Exit the loop if choice is 6
                            break; // Exit the patient menu and return to main menu

                        case 4:
                            System.out.println("Exiting the system. Goodbye!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}