import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
            Scanner scanner = new Scanner(System.in);
            Staff pm = new Staff();
            doctorManagement dm = new doctorManagement();






                while (true) { // Main loop
                    System.out.println("--------------------------------------");
                    System.out.println("Login As ");
                    System.out.println("1- Patient ");
                    System.out.println("2- Doctor ");
                    System.out.println("3- Staff ");
                    System.out.println("4- Exit");
                    System.out.println("--------------------------------------");
                    System.out.print("Enter your choice: ");
                    int choice1 = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice1) {
                        case 1: // Patient Menu
                            while (true) {
                                System.out.println("--------------------------------------");
                                System.out.println("Patient Menu ");
                                System.out.println("--------------------------------------");
                                System.out.println("1. Book An Appointment");
                                System.out.println("2. View Appointment Details");
                                System.out.println("3. Cancel An Appointment");
                                System.out.println("4. View My Diagnosis Or Prescription");
                                System.out.println("5. Pay My Bill");
                                System.out.println("6. Go Back To Main Menu");
                                System.out.println("7. Exit");
                                System.out.println("--------------------------------------");
                                System.out.print("Enter your choice: ");
                                int choice2 = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                switch (choice2) {
                                    case 1:
                                        System.out.print("Enter Your Name: ");
                                        String name = scanner.nextLine();
                                        System.out.println("Feature to check registration or book appointment is under development.");
                                        break;
                                    case 2:
                                        System.out.println("View Appointment Details is under development.");
                                        break;
                                    case 3:
                                        System.out.println("Cancel An Appointment is under development.");
                                        break;
                                    case 4:
                                        System.out.println("View Diagnosis or Prescription is under development.");
                                        break;
                                    case 5:
                                        System.out.println("Pay My Bill is under development.");
                                        break;
                                    case 6:
                                        break; // Go back to the main menu
                                    case 7:
                                        System.out.println("Exiting the system. Goodbye!");
                                        scanner.close();
                                        return;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                                if (choice2 == 6) break; // Break patient menu loop
                            }
                            break;

                        case 2: // Doctor Menu
                            while (true) {
                                System.out.println("--------------------------------------");
                                System.out.println("Doctor Menu ");
                                System.out.println("--------------------------------------");
                                System.out.println("1. View Personal Schedule");
                                System.out.println("2. View Assigned Patients");
                                System.out.println("3. Add Diagnosis or Prescription For Patient");
                                System.out.println("4. Update Personal Schedule");
                                System.out.println("5. Generate Billing Information");
                                System.out.println("6. Go Back To Main Menu");
                                System.out.println("7. Exit");
                                System.out.println("--------------------------------------");
                                System.out.print("Enter your choice: ");
                                int choice3 = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                switch (choice3) {
                                    case 1:
                                        System.out.println("View Personal Schedule is under development.");
                                        break;
                                    case 2:
                                        System.out.println("View Assigned Patients is under development.");
                                        break;
                                    case 3:
                                        System.out.println("Add Diagnosis or Prescription is under development.");
                                        break;
                                    case 4:
                                        System.out.println("Update Personal Schedule is under development.");
                                        break;
                                    case 5:
                                        System.out.println("Generate Billing Information is under development.");
                                        break;
                                    case 6:
                                        break; // Go back to the main menu
                                    case 7:
                                        System.out.println("Exiting the system. Goodbye!");
                                        scanner.close();
                                        return;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                                if (choice3 == 6) break; // Break doctor menu loop
                            }
                            break;

                        case 3: // Staff Menu
                            while (true) {
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
                                scanner.nextLine(); // Consume newline

                                switch (choice4) {
                                    case 1:
                                        System.out.print("Enter Patient Name: ");
                                        String patientName = scanner.nextLine();
                                        System.out.print("Enter Patient Age: ");
                                        int age = scanner.nextInt();
                                        System.out.print("Enter Patient ID: ");
                                        int id_p = scanner.nextInt();
                                        scanner.nextLine(); // Consume newline
                                        System.out.print("Enter Patient Phone Number: ");
                                        String phone = scanner.nextLine();
                                        System.out.print("Enter Patient Gender: ");
                                        String gender = scanner.nextLine();
                                        System.out.print("Enter Patient Health Issue: ");
                                        String healthIssue = scanner.nextLine();
                                        pm.insertPatient(patientName, age,id_p, phone, gender, healthIssue);
                                        System.out.println("Patient added successfully.");
                                        break;
                                    case 2:
                                        System.out.println("Update Patient Information is under development.");
                                        break;

                                    case 3:
                                        System.out.print("Enter Doctor ID: ");
                                        int id = scanner.nextInt();
                                        scanner.nextLine(); // Consume newline
                                        System.out.print("Enter Doctor Name: ");
                                        String doctorName = scanner.nextLine();
                                        System.out.print("Enter Doctor Specialization: ");
                                        String specialization = scanner.nextLine();
                                        System.out.print("Enter Doctor Phone Number: ");
                                        int number = scanner.nextInt();
                                        scanner.nextLine(); // Consume newline
                                        String[] availability = new String[2];
                                        System.out.print("Enter Doctor Availability Slot 1 (e.g., Monday 10AM): ");
                                        availability[0] = scanner.nextLine();
                                        System.out.print("Enter Doctor Availability Slot 2 (e.g., Tuesday 2PM): ");
                                        availability[1] = scanner.nextLine();
                                        dm.addDoctor(id, doctorName, specialization, availability, number);
                                        System.out.println("Doctor added successfully!");
                                        break;
                                    case 5:
                                        pm.DisplayDataOfPatient();



                                    case 13:
                                        break; // Go back to the main menu
                                    case 14:
                                        System.out.println("Exiting the system. Goodbye!");
                                        scanner.close();
                                        return;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                                if (choice4 == 13) break; // Break staff menu loop
                            }
                            break;

                        case 4:
                            System.out.println("Exiting the system. Goodbye!");
                            scanner.close();
                            return;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        }
