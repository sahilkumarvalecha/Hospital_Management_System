import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PatientManagement pm = new PatientManagement();
        doctorManagement dm = new doctorManagement();


        System.out.println("Login As ");
        System.out.println("1- Patient ");
        System.out.println("2- Doctor ");
        System.out.println("3- Staff ");
        System.out.println("4- Exit");
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
                                System.out.print("Enter patient name to book an appointment: ");
                                String name = scanner.nextLine();
                                System.out.println("Enter appointment details: ");
                                String appointmentDetails = scanner.nextLine();

                                pm.bookAppointment(name, appointmentDetails);
                                break;

                            case 2:
                                System.out.print("Enter patient name to view appointment details: ");
                                String patientName = scanner.nextLine();
                                pm.viewAppointments(patientName);
                                break;
                            case 3:
                                System.out.print("Enter patient name to cancel an appointment: ");
                                String pName = scanner.nextLine();
                                pm.cancelAppointment(pName);
                                break;
                            case 4:
//
                                break;

                            case 5:
//                            System.out.print("Enter the filename to save the patients: ");
//                            pm.savePatientsToFile("filename.txt");
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
                            System.out.println("11. Go Back To Main Menu");
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
                            pm.insertPatient(patientName, age, phone, gender, healthIssue);
                            System.out.println("Patient added successfully.");
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
                            pm.updatePatientInfo(id, newName, newAge, newGender, newHealthIssue);
                            break;
                        case 3:
                            System.out.print("Enter Doctor ID: ");
                            int ID = scanner.nextInt();
                            System.out.print("Enter Doctor Name: ");
                            String doctorName = scanner.nextLine();
                            System.out.print("Enter Doctor specialization: ");
                            String specialization = scanner.nextLine();
                            System.out.print("Enter Doctor Phone Number: ");
                            int number = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            String[] availability = new String[2];

                            System.out.print("Enter Doctor Availability Slot 1 (e.g., Monday 10AM): ");
                            availability[0] = scanner.nextLine();

                            System.out.print("Enter Doctor Availability Slot 2 (e.g., Tuesday 2PM): ");
                            availability[1] = scanner.nextLine();

                            dm.addDoctor(ID, doctorName, specialization, availability, number);
                            System.out.println("Doctor added successfully!");

                            break;
                        case 4:

                            break;
                        case 5:
                            pm.sortPatients();
                            pm.DisplayDataOfPatient();
                            break;
                        case 6:
                            System.out.print("Enter the name of the patient you want to search : ");
                            String searchName = scanner.nextLine();
                            pm.SearchPatient(searchName);
                            break;
                        case 7:

                            break;
                        case 8:

                            break;
                        case 9:

                            break;
                        case 10:

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
