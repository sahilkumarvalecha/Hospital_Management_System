import java.util.ArrayList;

public class Staff {

    Node head;
    Node doctorHead;

    public  Staff() {
        this.head = null;
        this.doctorHead =null;
    }
    public boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isDoctorListEmpty() {
        return doctorHead == null;
    }


    public void insertPatient(String PatientName, int PatientAge, int PatientID, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue) {
        Node newnode = new Node(PatientName, PatientAge, PatientID, PatientPhoneNUM, PatientGender, PatientHealthIssue);
        if (isEmpty()) {
            head = newnode;
        } else {
            newnode.next = head;
            head = newnode;
        }

        Filing filing = new Filing();
        filing.saveLikedListToFile("patient.txt", head);

    }

    public void updatePatient(int PatientID, String newName, int newAge, String newPhoneNUM, String newGender, String newHealthIssue) {
        if (isEmpty()) {
            System.out.println("The patient list is empty. No records to update.");
            return;
        }

        Node current = head;
        boolean found = false;

        // Traverse the linked list to find the patient by ID
        while (current != null) {
            if (current.PatientID == PatientID) {
                // Update the patient's details
                current.PatientName = newName;
                current.PatientAge = newAge;
                current.PatientPhoneNUM = newPhoneNUM;
                current.PatientGender = newGender;
                current.PatientHealthIssue = newHealthIssue;

                System.out.println("Patient details updated successfully.");
                found = true;
                break;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("Patient with ID " + PatientID + " not found.");
        } else {
            // Save the updated list to the file
            Filing filing = new Filing();
            filing.saveLikedListToFile("patient.txt", head);
        }
    }

    public void addDoctor(String doctorName, int doctorAge, String doctorSpecialization, int doctorID, String doctorContactNumber) {
        Node newNode = new Node(doctorName, doctorAge, doctorSpecialization, doctorID, doctorContactNumber);
        if (isEmpty()) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        System.out.println("Doctor added: " + doctorName);
    }

    public void DisplayDataOfPatient() {

        Filing filing = new Filing();
        ArrayList<String> file = filing.loadPatientsFromFile("patient.txt");
        for (String line : file) {
            String[] patientData = line.split(",");

            Node newnode = new Node(patientData[0], Integer.parseInt(patientData[1], patientData[2]), patientData[3], patientData[4]);
            if (isEmpty()) {
                head = newnode;
            } else {
                newnode.next = head;
                head = newnode;
            }


            System.out.println("Patient data loaded from file.");

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
                                ", id : " + current.PatientID +
                                ", Gender: " + current.PatientGender +
                                ", Health Issue: " + current.PatientHealthIssue);

                current = current.next; // Move to the next node
            }
        }

    }   public void sortPatients() {
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

    public void assignDoctorToPatient(String patientName, String doctorName) {
        if (isEmpty()) {
            System.out.println("The patient list is empty. Cannot assign a doctor.");
            return;
        }

        if (isDoctorListEmpty()) {
            System.out.println("The doctor list is empty. Cannot assign a doctor.");
            return;
        }

        Node patient = head;
        Node doctor = doctorHead;
        boolean patientFound = false;
        boolean doctorFound = false;

        // Find the patient
        while (patient != null) {
            if (patient.PatientName.equalsIgnoreCase(patientName)) {
                patientFound = true;
                break;
            }
            patient = patient.next;
        }

        // Find the doctor
        while (doctor != null) {
            if (doctor.doctorName.equalsIgnoreCase(doctorName)) {
                doctorFound = true;
                break;
            }
            doctor = doctor.next;
        }

        if (patientFound && doctorFound) {
            patient.assignedDoctor = doctorName; // Assign the doctor's name to the patient
            System.out.println("Doctor " + doctorName + " has been assigned to patient " + patientName + ".");
        } else {
            if (!patientFound) {
                System.out.println("Patient " + patientName + " not found.");
            }
            if (!doctorFound) {
                System.out.println("Doctor " + doctorName + " not found.");
            }
        }
    }
    public void updateDoctorSchedule(String doctorName, String newSchedule) {
        if (isDoctorListEmpty()) {
            System.out.println("The doctor list is empty. No records to update.");
            return;
        }

        Node current = doctorHead;
        boolean found = false;

        // Traverse the doctor linked list to find the doctor by name
        while (current != null) {
            if (current.doctorName.equalsIgnoreCase(doctorName)) {
                current.schedule = newSchedule; // Update the doctor's schedule
                System.out.println("Doctor's schedule updated successfully.");
                found = true;
                break;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("Doctor with name " + doctorName + " not found.");
        }
    }
    public void displayDoctors() {
        if (isDoctorListEmpty()) {
            System.out.println("No doctors in the list.");
            return;
        }

        Node current = doctorHead;
        System.out.println("Doctor List:");
        while (current != null) {
            System.out.println(
                    "Name: " + current.doctorName +
                            ", Age: " + current.doctorAge +
                            ", Specialization: " + current.doctorSpecialization +
                            ", ID: " + current.doctorID +
                            ", Contact: " + current.doctorContactNumber +
                            ", Schedule: " + (current.schedule != null ? current.schedule : "No schedule assigned")
            );
            current = current.next; // Move to the next doctor
        }
    }
    public void searchDoctor(String doctorName) {
        if (isDoctorListEmpty()) {
            System.out.println("The doctor list is empty. Doctor not found.");
            return;
        }

        Node current = doctorHead;
        boolean found = false;

        System.out.println("Searching for doctor: " + doctorName);
        while (current != null) {
            if (current.doctorName.equalsIgnoreCase(doctorName)) { // Case-insensitive match
                if (!found) {
                    System.out.println("Doctor(s) found with the name: " + doctorName);
                    found = true;
                }
                System.out.println("Details:");
                System.out.println("Name: " + current.doctorName +
                        ", Age: " + current.doctorAge +
                        ", Specialization: " + current.doctorSpecialization +
                        ", ID: " + current.doctorID +
                        ", Contact: " + current.doctorContactNumber +
                        ", Schedule: " + (current.schedule != null ? current.schedule : "No schedule assigned"));
            }
            current = current.next; // Move to the next doctor
        }

        if (!found) {
            System.out.println("Doctor with name " + doctorName + " not found.");
        }
    }
    public void generateBillForPatient(int patientID, double treatmentCost, double medicationCost, double otherCharges) {
        if (isEmpty()) {
            System.out.println("The patient list is empty. Cannot generate a bill.");
            return;
        }

        Node current = head;
        boolean patientFound = false;

        // Traverse the linked list to find the patient by ID
        while (current != null) {
            if (current.PatientID == patientID) {
                patientFound = true;
                double totalBill = treatmentCost + medicationCost + otherCharges;

                // Save the bill details to a file or print it out
                System.out.println("Generating bill for patient " + current.PatientName);
                System.out.println("Treatment Cost: $" + treatmentCost);
                System.out.println("Medication Cost: $" + medicationCost);
                System.out.println("Other Charges: $" + otherCharges);
                System.out.println("Total Bill: $" + totalBill);

                // Saving the bill to a file (optional)
                Billing billing = new Billing();
                billing.saveBillToFile(patientID, current.PatientName, totalBill);

                break;
            }
            current = current.next;
        }

        if (!patientFound) {
            System.out.println("Patient with ID " + patientID + " not found.");
        }
    }
    public void viewAllBillingRecords() {
        Billing billing = new Billing();
        ArrayList<String> billingRecords = billing.loadBillingRecordsFromFile();

        if (billingRecords.isEmpty()) {
            System.out.println("No billing records found.");
            return;
        }

        System.out.println("Billing Records:");
        for (String record : billingRecords) {
            String[] recordDetails = record.split(",");
            System.out.println("Patient ID: " + recordDetails[0] +
                    ", Name: " + recordDetails[1] +
                    ", Total Bill: $" + recordDetails[2]);
        }
    }

}
