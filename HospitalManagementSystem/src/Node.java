import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Node {
    int id;
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
     Node head;

    public Node(String PatientName, int PatientAge, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue) {
        this.id = 0;
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
        this.head =null;
    }

    public int getId() {
        return id;
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
    private int idCounter = 1;
    public PatientManagement() {
        this.head = null;
        this.last = null;
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
        Node newnode = new Node(PatientName,PatientAge,PatientPhoneNUM,PatientGender,PatientHealthIssue);
        newnode.id = idCounter++;
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
            FileWriter fileWriter = new FileWriter("patientData.txt");
            fileWriter.write("Patient ID: " +patient.id+ " , ");
            fileWriter.write("Patient age: " +patient.PatientAge+ " , ");
            fileWriter.write("Patient phone number: " +patient.PatientPhoneNUM+ " , ");
            fileWriter.write("Patient Gender: " +patient.PatientGender+ " , ");
            fileWriter.write("Patient health issue: " +patient.PatientHealthIssue);
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

    public void SearchPatient(String patientName) {
        if (isEmpty()) {
            System.out.println("The list is empty. Patient not found.");
            return;
        }

        Node current = head;
        boolean found = false;

        System.out.println("Searching for patient: " + patientName);
        while (current != null) {
            if (current.PatientName.equalsIgnoreCase(patientName)) { // Case-insensitive match
                found = true;
               break;
            }
            current = current.next; // Move to the next node
        }
        if (found){
            System.out.println("Patient found with name " +patientName);
            System.out.println("Patient Details:");
            System.out.println("Name: " + current.PatientName +
                    ", Age: " + current.PatientAge +
                    ", Phone: " + current.PatientPhoneNUM +
                    ", Gender: " + current.PatientGender +
                    ", Health Issue: " + current.PatientHealthIssue);
        }
        if (!found) {
            System.out.println("No patient found with name " +patientName);
        }
    }
    public void bookAppointment(String patientName, String appointmentDetails){
        Node curr = head;
        while (curr != null){
            if (curr.PatientName.equalsIgnoreCase(patientName)){
                curr.bookAppointment(appointmentDetails);
            }
            curr = curr.next;
        }
        System.out.println("No patient found with name: " +patientName);
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
    public void updatePatientInfo(int id, String name, int age, String gender, String healthIssue){
        Node curr = head;
        while (curr!=null ){
            if (curr.id == id){
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
                System.out.println("Patient details updated successfully! for ID: " +id);
                return;
            }
            curr = curr.next;
        }
        System.out.println("No patient found with ID: " +id);
    }
    public void deletePatient(int id){
        if (isEmpty()){
            System.out.println(" Patient List is Empty ");
            return;
        }
        if (head.id == id){
            head = head.next;
        }
        Node curr = head.next;
        Node prev = head;
        while (curr!= null){

            if (curr.next == null && curr.id == id){
                prev.next = null;
            }
            if (curr.id == id){
                prev.next = curr.next;
                curr = curr.next;
            }
            curr = curr.next;
            prev = prev.next;
        }
        System.out.println("No patient found with ID: " +id);

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
            System.out.print(" Patient ID: " + current.id);
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
}