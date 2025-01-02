import javax.swing.plaf.IconUIResource;
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

    public Node(int patientId, String PatientName, int PatientAge, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue) {
        this.patientId = patientId;
        this.PatientName = PatientName;
        this.PatientAge = PatientAge;
        this.PatientPhoneNUM = PatientPhoneNUM;
        this.PatientGender = PatientGender;
        this.PatientHealthIssue = (PatientHealthIssue == null || PatientHealthIssue.isEmpty()) ? "unKnown" : PatientHealthIssue;
        this.appointmentDetails = "";
        this.billAmount = 0.0;
        this.diagnosis = null;
        this.prescription = null;
        this.next = null;
    }


    public int getpatientId() {
        return patientId;
    }
    public void setBillAmount(double billAmount) {
        this.billAmount=billAmount;
    }

    public String getPatientHealthIssue() {
        return PatientHealthIssue;
    }

    public void setDiagnosis(String diagnosis) {

        this.diagnosis = diagnosis;
    }

    // Method to set Prescription (typically done by a doctor)
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
    public void viewDiagnosisOrPrescription(String patientName){
        if(this.PatientName.equalsIgnoreCase(patientName)){
            System.out.println("Diagnosis: " + (diagnosis !=null ? diagnosis : "unAvailable"));
            System.out.println("Prescription: " + (prescription!=null ? prescription : "unAvailable"));
        }else {
            System.out.println("No patient found with this name " +patientName);
        }
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

    public int getPatientIdCounter() {
        return patientIdCounter;
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
        Node newnode = new Node(patientIdCounter, PatientName, PatientAge, PatientPhoneNUM, PatientGender, PatientHealthIssue);
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
                writer.write("Patient Bill : " + current.billAmount+ " , ");
                writer.write("Prescription : " + current.prescription);
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
                    curr.billAmount = 0;
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
    }

    public void showBill(int patientId){

        Node curr = head;
        boolean found = false;
        while (curr != null) {
            if (curr.patientId == patientId) {
                System.out.println(" Name: " + curr.PatientName);
                System.out.println(" Current Bill: " + curr.billAmount);
                found = true;
                break;
            }
            curr = curr.next;
        }
        if (!found) {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
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

    /*  public void bookAppointment(int patientId, String appointmentDetails){
  =======
  >>>>>>> Stashed changes

      public void getPatientBillAmount(int Id) {
          if (isEmpty()) {
              System.out.println("The list is empty. Patient not found.");
              return;
          }

          Node current = head;
          boolean found = false;


          while (current != null) {
              if (current.patientId == Id) { // Case-insensitive match
                  found = true;
                 current.billAmount += 1000;
              }
              current = current.next; // Move to the next node
          }
              System.out.println("No patient found with Id" + Id);
      }


      public void bookAppointment(int patientId, String appointmentDetails){
  >>>>>>> 224688f32385563b21866c7afd96512baf6e4b8e
          Node curr = head;
          while (curr != null){
              if (curr.patientId == patientId){

  //                curr.bookAppointment(appointmentDetails);
              }
              curr = curr.next;
          }
          System.out.println("No patient found with name: " + patientId);
      }*/
 /*   public void viewAppointments(String name){
        Node curr = head;
        while (curr != null){
            if (curr.PatientName.equalsIgnoreCase(name)){
                curr.viewAppointment();
            }
            curr = curr.next;
        }
        System.out.println("No patient found with name: " +name);
    }
    public void cancelAppointment(String patientName){
        Node curr = head;
        while (curr != null){
            if (curr.PatientName.equalsIgnoreCase(patientName)){
                curr.cancelAppointment();
            }
            curr = curr.next;
        }
        System.out.println("No patient found with name: " +patientName);
    }*/
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
    public void deletePatient(int patientId){
        if (isEmpty()){
            System.out.println(" Patient List is Empty ");
            return;
        }
        if (head.patientId == patientId){
            head = head.next;
        }
        Node curr = head.next;
        Node prev = head;
        while (curr!= null){

            if (curr.next == null && curr.patientId == patientId){
                prev.next = null;
            }
            if (curr.patientId == patientId){
                prev.next = curr.next;
                curr = curr.next;
            }
            curr = curr.next;
            prev = prev.next;
        }
        System.out.println("No patient found with ID: " +patientId);

    }


    public void sortPatients() {

        head = quickSort(head);
    }

    private Node quickSort(Node start) {
        if (start == null || start.next == null) {
            return start;
        }

        Node[] partitioned = partition(start);
        Node leftSorted = quickSort(partitioned[0]);
        Node rightSorted = quickSort(partitioned[2]);

        return merge(leftSorted, partitioned[1], rightSorted);
    }

    private Node[] partition(Node head) {
        Node pivot = head;
        Node leftHead = null, leftTail = null;
        Node rightHead = null, rightTail = null;

        Node current = head.next;

        while (current != null) {
            if (current.PatientName.compareTo(pivot.PatientName) <= 0) {
                if (leftHead == null) {
                    leftHead = current;
                    leftTail = current;
                } else {
                    leftTail.next = current;
                    leftTail = current;
                }
            } else {
                if (rightHead == null) {
                    rightHead = current;
                    rightTail = current;
                } else {
                    rightTail.next = current;
                    rightTail = current;
                }
            }
            current = current.next;
        }

        if (leftTail != null) {
            leftTail.next = null;
        }
        if (rightTail != null) {
            rightTail.next = null;
        }

        pivot.next = null;
        return new Node[]{leftHead, pivot, rightHead};
    }

    private Node merge(Node left, Node pivot, Node right) {
        if (left == null) {
            pivot.next = right;
            return pivot;
        }

        Node tail = left;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = pivot;
        pivot.next = right;

        return left;
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
                startIndex = line.lastIndexOf(",", line.lastIndexOf(":") - 1) + 1;
                prescription = line.substring(startIndex).trim();

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
                Node newNode = new Node(patientId, name, patientAge, phone, gender, healthIssue);
                newNode.billAmount = billAmount;
                newNode.prescription = prescription;

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