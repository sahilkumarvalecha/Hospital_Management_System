import java.io.*;
import java.util.Scanner;


public class Node { // Static variable to keep track of the ID across instances
    int patientId;
    String PatientName ;
    int PatientAge;
    String PatientPhoneNUM;
    String PatientGender;
    String PatientHealthIssue;
    String appointmentDetails;
    double  billAmount;
    String diagnosis;
    String prescription;
    Node next;

    public Node(int patientId, String PatientName, int PatientAge, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue, String prescription) {
        this.patientId = patientId;
        this.PatientName = PatientName;
        this.PatientAge = PatientAge;
        this.PatientPhoneNUM = PatientPhoneNUM;
        this.PatientGender = PatientGender;
        this.PatientHealthIssue = (PatientHealthIssue == null || PatientHealthIssue.isEmpty()) ? "unKnown" : PatientHealthIssue;
        this.appointmentDetails = "";
        this.billAmount = 0.0;
        this.diagnosis = null;
        this.prescription = prescription;
        this.next = null;
    }
    public String getPatientHealthIssue() {
        return PatientHealthIssue;
    }

    public void setDiagnosis(String diagnosis) {

        this.diagnosis = diagnosis;
    }
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}
class PatientManagement {
    Node head;
    Node last;
    double billAmount;
    String prescription;
    private int patientIdCounter = 1;

    public PatientManagement() {
        this.head = null;
        this.last = null;
        this.patientIdCounter = patientIdInitilizer();
        this.billAmount = 0;
        this.prescription = null;
        loadFromFile();
    }
    private int patientIdInitilizer() {
        File myFile = new File("patientData.txt");
        int maxId = 0;
        if (!myFile.exists()) {
            return 1;
        }
        try (Scanner sc = new Scanner(myFile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                for (String part : parts) {
                    if (part.trim().startsWith("Patient ID:")) {
                        String idStr = part.split(":")[1].trim();
                        int id = stringToInt(idStr);
                        maxId = Math.max(maxId, id); // Keep track of the highest ID
                    }
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return maxId + 1;
    }
    private int stringToInt(String str) {
        int result = 0;
        boolean isNegative = false;
        int i = 0;

        // Check for a negative sign
        if (str.charAt(0) == '-') {
            isNegative = true;
            i++;
        }

        // Convert each character to its numeric value
        for (; i < str.length(); i++) {
            char ch = str.charAt(i);

            // Validate the character
            if (ch < '0' || ch > '9') {
                throw new NumberFormatException("Invalid character in number: " + ch);
            }

            result = result * 10 + (ch - '0');
        }

        return isNegative ? -result : result;
    }
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    public void createFile() {
        File myFile = new File("patientData.txt");
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

    public void insertPatient(String PatientName, int PatientAge, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue) {
        Node newnode = new Node(patientIdCounter, PatientName, PatientAge, PatientPhoneNUM, PatientGender, PatientHealthIssue, null);
        patientIdCounter++;
        if (isEmpty()) {
            head = newnode;
            last = newnode;
        } else {
            last.next = newnode;
            last = newnode;
        }
        writeInFile(newnode);

    }

    public void writeInFile(Node patient) {
        try {
            FileWriter fileWriter = new FileWriter("patientData.txt", true);

            fileWriter.write("Patient ID: " + patient.patientId + " , ");
            fileWriter.write("Patient name: " + patient.PatientName + " , ");
            fileWriter.write("Patient age: " + patient.PatientAge + " , ");
            fileWriter.write("Patient phone number: " + patient.PatientPhoneNUM + " , ");
            fileWriter.write("Patient Gender: " + patient.PatientGender + " , ");
            fileWriter.write("Patient health issue: " + patient.PatientHealthIssue + " , ");
            fileWriter.write("Patient Bill : " + patient.billAmount + " , ");
            fileWriter.write("Prescription: " + patient.prescription);

            fileWriter.write("\n");
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error writing patient data to file.");
            e.printStackTrace();
        }
    }


    public void readFromFile() {
        File myfile = new File("patientData.txt");
        try (Scanner sc = new Scanner(myfile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public void updatePatientBill(int patientId, int amount) {
        Node curr = head;
        while (curr != null) {
            if (curr.patientId == patientId) {
                curr.billAmount += amount;
                updateFile();
                break;
            }
            curr = curr.next;
        }

    }

    public void updateFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("patientData.txt"))) {
            Node current = head;
            while (current != null) {
                writer.write("Patient ID: " + current.patientId + " , ");
                writer.write("Patient name: " + current.PatientName + " , ");
                writer.write("Patient age: " + current.PatientAge + " , ");
                writer.write("Patient phone number: " + current.PatientPhoneNUM + " , ");
                writer.write("Patient Gender: " + current.PatientGender + " , ");
                writer.write("Patient health issue: " + current.PatientHealthIssue + " , ");
                writer.write("Patient Bill : " + current.billAmount);

                writer.newLine();
                current = current.next;
            }
        } catch (IOException e) {
            System.out.println("Error writing patient data to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void payMyBill(int patientId) {
        Node curr = head;
        boolean found = false;
        while (curr != null) {
            if (curr.patientId == patientId) {
                found = true;
                System.out.println(" Name: " + curr.PatientName);
                System.out.println("Current Bill: " + curr.billAmount);
                if (curr.billAmount == 0) {
                    System.out.println("Your bill is zero! you haven't booked appointment yet");
                    return;
                }
                System.out.println("Do you want to pay the bill now? 1. Yes  2. No");

                // Take user input
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();

                if (choice == 1) {
                    curr.billAmount = 0.0;
                    System.out.println("Bill paid successfully! ");
                    updateFile();
                } else if (choice == 2) {
                    System.out.println("Payment cancelled ");
                } else {
                    System.out.println("Wrong choice! ");
                }
                break;
            }

            curr = curr.next;
        }
        if (!found) {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
        updateFile();
    }

    public Node SearchPatient(int patientId) {
        if (isEmpty()) {
            System.out.println("The list is empty. Patient not found.");
            return null;
        }

        Node current = head;
        boolean found = false;

        System.out.println("Searching for patient..... ");
        while (current != null) {
            if (current.patientId == patientId) { // Case-insensitive match
                found = true;
                return current;
            }
            current = current.next; // Move to the next node
        }
        if (found) {
            System.out.println("Patient found with Id " + patientId);
            System.out.println("Patient Details:");
            System.out.println("Name: " + current.PatientName +
                    ", Age: " + current.PatientAge +
                    ", Phone: " + current.PatientPhoneNUM +
                    ", Gender: " + current.PatientGender +
                    ", Health Issue: " + current.PatientHealthIssue);
            return current;
        }
        if (!found) {
            return null;
        }
        System.out.println("Patient not found with Id " + patientId);
        return null;
    }
    public void validateAge(int age) throws Exception {
        if (age < 0 || age > 120) {
            throw new Exception("Invalid age: must be between 0 and 120");
        }
    }
    public void validatePhoneNumber(String phoneNumber) throws Exception {
        int length = 0;
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (c < '0' || c > '9') {
                throw new Exception("Invalid phone number: contains non-numeric characters");
            }
            length++;
        }
        if (length != 10) {
            throw new Exception("Invalid phone number: must be a 10-digit number");
        } System.out.println("Valid phone number: " + phoneNumber);
    }
    public void validatePhoneNumber(int phoneNumber) throws Exception {
        // Convert the phone number to a string to check the number of digits
        String phoneNumberStr = Integer.toString(phoneNumber);

        // Check if the phone number has exactly 10 digits
        if (phoneNumberStr.length() != 10) {
            throw new Exception("Invalid phone number: must be a 10-digit number");
        }

        // Check if the phone number contains only digits
        for (int i = 0; i < phoneNumberStr.length(); i++) {
            char c = phoneNumberStr.charAt(i);
            if (c < '0' || c > '9') {
                throw new Exception("Invalid phone number: contains non-numeric characters");
            }
        }

        System.out.println("Valid phone number: " + phoneNumber);
    }
    public void updatePatientInfo(int patientId, String name, int age, String gender, String healthIssue){
        Node curr = head;
        while (curr!=null ){
            if (curr.patientId == patientId){
                if (name != null){
                    curr.PatientName = name;
                }
                if (age>0) {
                    curr.PatientAge = age;
                }
                if (gender != null){
                    curr.PatientGender = gender;
                }
                if (healthIssue != null){
                    curr.PatientHealthIssue = healthIssue;
                }
                System.out.println("Patient details updated successfully! for ID: " +patientId);
                return;
            }
            curr = curr.next;
        }
        System.out.println("No patient found with ID: " +patientId);
    }
    public void DisplayDataOfPatient() {
        if (isEmpty()) {
            System.out.println("No patients in the list.");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.print("Patient ID: " + current.patientId);
            System.out.print(", Name: " + current.PatientName);
            System.out.print(", Age: " + current.PatientAge);
            System.out.print(", Phone Number: " + current.PatientPhoneNUM);
            System.out.print(", Gender: " + current.PatientGender);
            System.out.print(", Health Issue: " + current.PatientHealthIssue);
            System.out.print(", Bill Amount: " + current.billAmount);
            System.out.print(", Prescription: " + (current.prescription != null ? current.prescription : "None"));
            System.out.println();
            current = current.next;
        }
    }

    public void loadFromFile() {
            try {
                File file = new File("patientData.txt");

                if (!file.exists()) {
                    System.out.println("No previous patient data found.");
                    return;
                }
                // Clear the list before loading
                head = null;
                last = null;

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;

                while ((line = br.readLine()) != null) {
                    // Manually extracting the data by looking for the position of ':'
                    int startIndex, endIndex;
                    String id = "", name = "", age = "", phone = "", gender = "", healthIssue = "", bill = "", prescription = "";

                    // Extract patient ID
                    startIndex = line.indexOf(":") + 1;
                    endIndex = line.indexOf(",", startIndex);
                    id = line.substring(startIndex, endIndex).trim();

                    // Extract Name
                    startIndex = line.indexOf(":", endIndex) + 1;
                    endIndex = line.indexOf(",", startIndex);
                    name = line.substring(startIndex, endIndex).trim();

                    // Extract Age
                    startIndex = line.indexOf(":", endIndex) + 1;
                    endIndex = line.indexOf(",", startIndex);
                    age = line.substring(startIndex, endIndex).trim();

                    // Extract Phone
                    startIndex = line.indexOf(":", endIndex) + 1;
                    endIndex = line.indexOf(",", startIndex);
                    phone = line.substring(startIndex, endIndex).trim();

                    // Extract Gender
                    startIndex = line.indexOf(":", endIndex) + 1;
                    endIndex = line.indexOf(",", startIndex);
                    gender = line.substring(startIndex, endIndex).trim();

                    // Extract Health Issue
                    startIndex = line.indexOf(":", endIndex) + 1;
                    endIndex = line.indexOf(",", startIndex);
                    healthIssue = line.substring(startIndex, endIndex).trim();

                    // Extract Bill Amount (Last value after last comma)
                    startIndex = line.lastIndexOf(":") + 1;
                    bill = line.substring(startIndex).trim();

                    // Extract Prescription
                    startIndex = line.indexOf(":", endIndex) + 1;
                    endIndex = line.indexOf(",", startIndex);
                    prescription = line.substring(startIndex, endIndex).trim();
                    if (endIndex == -1) {  // If no comma, take till the end of the line
                        prescription = line.substring(startIndex).trim();
                    } else {
                        prescription = line.substring(startIndex, endIndex).trim();
                    }

                    // Manually converting strings to integers and double
                    int patientId = 0;
                    for (int i = 0; i < id.length(); i++) {
                        patientId = patientId * 10 + (id.charAt(i) - '0');
                    }

                    int patientAge = 0;
                    for (int i = 0; i < age.length(); i++) {
                        patientAge = patientAge * 10 + (age.charAt(i) - '0');
                    }

                    double billAmount = 0;
                    boolean isDecimal = false;
                    double decimalFactor = 0.1;
                    for (int i = 0; i < bill.length(); i++) {
                        char c = bill.charAt(i);
                        if (c == '.') {
                            isDecimal = true;
                        } else if (c >= '0' && c <= '9') {
                            if (isDecimal) {
                                billAmount += (c - '0') * decimalFactor;
                                decimalFactor /= 10;
                            } else {
                                billAmount = billAmount * 10 + (c - '0');
                            }
                        }
                    }

                    // Create a new patient node and add it to the list
                    Node newNode = new Node(patientId, name, patientAge, phone, gender, healthIssue, prescription);
                    newNode.billAmount = billAmount;

                    if (isEmpty()) {
                        head = newNode;
                        last = newNode;
                    } else {
                        last.next = newNode;
                        last = newNode;
                    }
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error reading from file: " + e.getMessage());
                e.printStackTrace();
            }
        }



    }