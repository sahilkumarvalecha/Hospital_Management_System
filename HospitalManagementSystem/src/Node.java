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
    double billAmount;
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

    public String getPatientHealthIssue() {
        return PatientHealthIssue;
    }


    public void bookAppointment(String appointmentDetails){
        this.appointmentDetails = appointmentDetails;
        System.out.println("Appointment booked for " +PatientName+ " on " +appointmentDetails);
        billAmount  += 1000;
    }
    public void viewAppointment(){
        if (this.appointmentDetails == null){
            System.out.println("No appointments scheduled for patient name: " +PatientName);
        }else{
            System.out.println("Appointment Details for " +PatientName+ " : " +appointmentDetails);
        }
    }
    public void cancelAppointment(){
        if (this.appointmentDetails == null){
            System.out.println("No appointment to cancel for : " +PatientName);
        }else {
            System.out.println("Appointments for " +PatientName+ " on " +appointmentDetails+ " has been cancelled.");
            appointmentDetails = null;
        }
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
    public void payBill(double amountPaid){
        if(amountPaid <=0){
            System.out.println("Invalid payment amount. please enter a valid amount.");
            return;
        }
        if (amountPaid >= this.billAmount){
            System.out.println("Bill of " +this.billAmount+ " fully paid by " +PatientName+ ".");
            this.billAmount = 0.0; //bill is now fully paid
        }else {
            this.billAmount -= amountPaid;
            System.out.println("Partial payment of " +amountPaid+ " from amount " +this.billAmount+ " received from " +PatientName+ ".");
            System.out.println("Remaining balance is " +this.billAmount);
        }
    }
}
class PatientManagement{
    Node head;
    Node last;
    private int patientIdCounter = 1;

    public PatientManagement() {
        this.head = null;
        this.last = null;
        this.patientIdCounter = patientIdInitilizer();
    }

    public int getPatientIdCounter() {
        return patientIdCounter;
    }

    private int patientIdInitilizer(){
        File myFile = new File("patientData.txt");
        int maxId = 0;
        if(!myFile.exists()){
            return 1;
        }
        try (Scanner sc = new Scanner(myFile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                for (String part : parts) {
                    if (part.trim().startsWith("Patient ID:")) {
                        String idStr = part.split(":")[1].trim();
                        int id = Integer.parseInt(idStr);
                        maxId = Math.max(maxId, id); // Keep track of the highest ID
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return maxId+1;
    }

    public boolean isEmpty(){
        if(head == null){
            return  true;
        }
        return false;
    }

    public void createFile(){
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
    public void insertPatient(String PatientName, int PatientAge, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue){
        Node newnode = new Node(patientIdCounter, PatientName,PatientAge,PatientPhoneNUM,PatientGender,PatientHealthIssue);
        patientIdCounter++;
        if(isEmpty()){
            head = newnode;
            last = newnode;
        }
        else{
            last.next = newnode;
            last = newnode;
        }
        writeInFile(newnode);

    }

    public void writeInFile(Node patient) {
        try{
            FileWriter fileWriter = new FileWriter("patientData.txt", true);
            fileWriter.write("Patient ID: " +patient.patientId+ " , ");
            fileWriter.write("Patient name: " +patient.PatientName+ " , ");
            fileWriter.write("Patient age: " +patient.PatientAge+ " , ");
            fileWriter.write("Patient phone number: " +patient.PatientPhoneNUM+ " , ");
            fileWriter.write("Patient Gender: " +patient.PatientGender+ " , ");
            fileWriter.write("Patient health issue: " +patient.PatientHealthIssue);
            fileWriter.write("\n");

            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error writing patient data to file.");
            e.printStackTrace();
        }
    }


    public void readFromFile(){
        File myfile = new File("patientData.txt");
        try(Scanner sc = new Scanner(myfile)) {
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
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
        if (found){
            System.out.println("Patient found with Id " +patientId);
            System.out.println("Patient Details:");
            System.out.println("Name: " + current.PatientName +
                    ", Age: " + current.PatientAge +
                    ", Phone: " + current.PatientPhoneNUM +
                    ", Gender: " + current.PatientGender +
                    ", Health Issue: " + current.PatientHealthIssue);
            return current;
        }
        if (!found) {
            System.out.println("No patient found with Id" + patientId);
            return null;
        }
        return null;
    }
    public void bookAppointment(int patientId, String appointmentDetails){
        Node curr = head;
        while (curr != null){
            if (curr.patientId == patientId){

//                curr.bookAppointment(appointmentDetails);
            }
            curr = curr.next;
        }
        System.out.println("No patient found with name: " + patientId);
    }
    public void viewAppointments(String name){
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
    public void DisplayDataOfPatient(){
        if (isEmpty()) {
            System.out.println("No patients in the list.");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.print(" Patient ID: " + current.patientId);
            System.out.print(", Patient Name: " + current.PatientName);
            System.out.print(", Patient Age: " + current.PatientAge);
            System.out.print(", Patient Phone Number: " + current.PatientPhoneNUM);
            System.out.print(", Patient Gender: " + current.PatientGender);
            System.out.print(", Patient Health Issue: " + current.PatientHealthIssue);
            System.out.print(", Appointment Details: " + current.appointmentDetails);
            System.out.print(", Bill Amount: " + current.billAmount);
            System.out.println(" ");

            current = current.next;  // Move to the next node
        }
    }
    public void loadFromFile() {
        File file = new File("patientData.txt");
        if (!file.exists()) {
            System.out.println("No previous patient data found.");
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].split(":")[1].trim());
                String name = data[1].split(":")[1].trim();
                int age = Integer.parseInt(data[2].split(":")[1].trim());
                String phone = data[3].split(":")[1].trim();
                String gender = data[4].split(":")[1].trim();
                String healthIssue = data[5].split(":")[1].trim();

                // Create a new node without writing back to the file
                Node newNode = new Node(id, name, age, phone, gender, healthIssue);
                if (isEmpty()) {
                    head = newNode;
                    last = newNode;
                } else {
                    last.next = newNode;
                    last = newNode;
                }

                // Update the patientIdCounter
                patientIdCounter = Math.max(patientIdCounter, id + 1);
            }
            System.out.println("Patient data loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error reading from file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

