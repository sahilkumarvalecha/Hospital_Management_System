import java.io.*;

public class Node {


    String PatientName ;
    int PatientAge;
    int PatientPhoneNUM;
    String PatientGender;
    String PatientHealthIusse;
    Node next;

    public Node(String PatientName, int PatientAge, int PatientPhoneNUM, String PatientGender, String PatientHealthIusse) {
       this.PatientName = PatientName;
        this.  PatientAge = PatientAge;
        this.  PatientPhoneNUM = PatientPhoneNUM;
        this. PatientGender = PatientGender;
        this.  PatientHealthIusse = PatientHealthIusse;
        this.next = null;
    }

}

class PatientManagement{
    Node  head;

    public PatientManagement() {
        this.head = null;
    }
     public boolean IsEmpty(){
        if(head == null){
           return  true;
        }
        return false;
     }
    public void insertPatient(String PatientName, int PatientAge, int PatientPhoneNUM, String PatientGender, String PatientHealthIusse){
        Node newnode = new Node(PatientName,PatientAge,PatientPhoneNUM,PatientGender,PatientHealthIusse);
        if(IsEmpty()){
            head = newnode;
        }
        else{
            newnode.next = head;
               head=newnode;
        }

    }

    public void SearchPatient(String patientName) {
        if (IsEmpty()) {
            System.out.println("The list is empty. Patient not found.");
            return;
        }

        Node current = head;
        boolean found = false;

        System.out.println("Searching for patient: " + patientName);
        while (current != null) {
            if (current.PatientName.equals(patientName)) { // Case-insensitive match
                if (!found) {
                    System.out.println("Patient(s) found with the name: " + patientName);
                    found = true;
                }
                System.out.println("Details:");
                System.out.println("Name: " + current.PatientName +
                        ", Age: " + current.PatientAge +
                        ", Phone: " + current.PatientPhoneNUM +
                        ", Gender: " + current.PatientGender +
                        ", Health Issue: " + current.PatientHealthIusse);
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
        if (IsEmpty()) {
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
                            ", Health Issue: " + current.PatientHealthIusse);

                           current = current.next; // Move to the next node
        }
    }
    public void savePatientsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Node current = head;
            while (current != null) {
                writer.write(current.PatientName + "," + current.PatientAge + "," + current.PatientPhoneNUM + "," + current.PatientGender + "," + current.PatientHealthIusse);
                writer.newLine();
                current = current.next;
            }
            System.out.println("Patient data saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving patient data.");
            e.printStackTrace();
        }
    }

    // Load patients from a file
    public void loadPatientsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] patientData = line.split(",");
                insertPatient(patientData[0], Integer.parseInt(patientData[1]), Integer.parseInt(patientData[2]), patientData[3], patientData[4]);
            }
            System.out.println("Patient data loaded from file.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading patient data.");
            e.printStackTrace();
        }
    }
}

