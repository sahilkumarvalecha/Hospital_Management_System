import java.io.*;

public class Node {
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

    public Node(String PatientName, int PatientAge, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue) {
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
    public void bookAppointment(String appointmentDetails){
        this.appointmentDetails = appointmentDetails;
        System.out.println("Appointment booked for " +PatientName+ " on " +appointmentDetails);
    }
    public void viewAppointment(){
        if (this.appointmentDetails.isEmpty()){
            System.out.println("No appointments scheduled");
        }else{
            System.out.println("Appointment Details: " +appointmentDetails);
        }
    }
    public void cancelAppointment(String patientName){
        if (this.PatientName.equalsIgnoreCase(patientName) && this.appointmentDetails != null){
            appointmentDetails = "";
            System.out.println("Appointment cancelled for " +patientName);
        }else {
            System.out.println("No appointment found for " +patientName);
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
    public void payBill(double billAmount){
        this.billAmount = billAmount;
        System.out.println("Bill of " +billAmount+ " paid by " + PatientName);
    }

}

class PatientManagement{
    Node  head;

    public PatientManagement() {
        this.head = null;
    }
    public boolean isEmpty(){
        if(head == null){
            return  true;
        }
        return false;
    }
    public void insertPatient(String PatientName, int PatientAge, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue){
        Node newnode = new Node(PatientName,PatientAge,PatientPhoneNUM,PatientGender,PatientHealthIssue);
        if(isEmpty()){
            head = newnode;
        }
        else{
            newnode.next = head;
            head=newnode;
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
                if (!found) {
                    System.out.println("Patient(s) found with the name: " + patientName);
                    found = true;
                }
                System.out.println("Details:");
                System.out.println("Name: " + current.PatientName +
                        ", Age: " + current.PatientAge +
                        ", Phone: " + current.PatientPhoneNUM +
                        ", Gender: " + current.PatientGender +
                        ", Health Issue: " + current.PatientHealthIssue);
            }
            current = current.next; // Move to the next node
        }

        if (!found) {
            System.out.println("Patient not found.");
        }
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

        System.out.println("Patient List With Sorted:");
        while (current != null) {
            System.out.println(
                    "Name: " + current.PatientName +
                            ", Age: " + current.PatientAge +
                            ", Phone: " + current.PatientPhoneNUM +
                            ", Gender: " + current.PatientGender +
                            ", Health Issue: " + current.PatientHealthIssue);

            current = current.next; // Move to the next node
        }
    }
    public void savePatientsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Node current = head;
            while (current != null) {
                writer.write(current.PatientName + "," + current.PatientAge + "," + current.PatientPhoneNUM + "," + current.PatientGender + "," + current.PatientHealthIssue);
                writer.newLine();
                current = current.next;
            }
            System.out.println("Patient data saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving patient data.");
            e.printStackTrace();
        }
    }
    private boolean isNumeric(String str) {
        return str.matches("\\d+"); // Matches only digits
    }

    // Load patients from a file
    public void loadPatientsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] patientData = line.split(",");
                if (patientData.length == 5 && isNumeric(patientData[2])) {
                    insertPatient(patientData[0], Integer.parseInt(patientData[1]), patientData[2], patientData[3], patientData[4]);
                }else {
                    System.out.println("Skipping invalid data: " + line);
                }
            }
            System.out.println("Patient data loaded from file.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading patient data.");
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            System.out.println("Data format in file is incorrect.");
            e.printStackTrace();
        }
    }
}