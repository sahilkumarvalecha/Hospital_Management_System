import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PatientManagement pm = new PatientManagement();
        doctorManagement dm = new doctorManagement();


        System.out.println("1- Patient management: ");
        System.out.println("2- Doctor management: ");
        System.out.println("3- Exit");
        int choice1 = scanner.nextInt();
        while (true) {
            switch(choice1){
                case 1:
                    System.out.println("\nPatient Management System Menu:");
                    System.out.println("1. Add a Patient");
                    System.out.println("2. Search for a Patient");
                    System.out.println("3. Sort Patients by Name");
                    System.out.println("4. Display All Patients");
                    System.out.println("5. Save Patients to File");
                    System.out.println("6. Load Patients from File");
                    System.out.println("7. Exit");
                    System.out.print("Enter your choice: ");
                    int choice2 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice2) {
                        case 1:
                            System.out.print("Enter Patient Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter Patient Age: ");
                            int age = scanner.nextInt();
                            System.out.print("Enter Patient Phone Number: ");
                            String phone = scanner.next();
                            scanner.nextLine(); // Consume newline
                            System.out.print("Enter Patient Gender: ");
                            String gender = scanner.nextLine();
                            System.out.print("Enter Patient Health Issue: ");
                            String healthIssue = scanner.nextLine();

                            pm.insertPatient(name, age, phone, gender, healthIssue);
                            System.out.println("Patient added successfully.");
                            break;

                        case 2:
                            System.out.print("Enter the name of the patient to  Liner search: ");
                            String searchName = scanner.nextLine();
                            pm.SearchPatient(searchName);
                            break;

                        case 3:
                            pm.sortPatients();
                            System.out.println("Patients sorted by name.");
                            pm.DisplayDataOfPatient();
                            break;

                        case 4:
                            pm.DisplayDataOfPatient();
                            break;

                        case 5:
                            System.out.print("Enter the filename to save the patients: ");
                            pm.savePatientsToFile("filename.txt");
                            break;

                        case 6:
                            System.out.print("Enter the filename to load patients from: ");

                            pm.loadPatientsFromFile("filename.txt");
                            break;

                        case 7:
                            System.out.println("Exiting the system. Goodbye!");
                            scanner.close();
                            return;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                case 2:
                    System.out.println("\nDoctor Management System Menu:");
                    System.out.println("1. Add a Doctor");
                    System.out.println("2. Delete a Doctor");
                    System.out.println("3. Update Doctor details");
                    System.out.println("4. Search Doctor By Specialization");
                    System.out.println("5. Search Doctor By Availability");
                    System.out.println("6. Display All Doctors");
                    System.out.println("7. Allocate doctor to patient");
                    System.out.println("8. Exit");
                    System.out.print("Enter your choice: ");
                    int choice3 = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice3){
                        case 1:
                            System.out.print("Enter Doctor ID: ");
                            int ID = scanner.nextInt();
                            System.out.print("Enter Doctor Name: ");
                            String name = scanner.nextLine();
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

                            dm.addDoctor(ID, name, specialization, availability, number);
                            System.out.println("Doctor added successfully!");
                            break;
                    }
            }




        }
    }
}