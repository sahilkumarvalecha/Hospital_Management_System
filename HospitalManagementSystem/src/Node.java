public class Node {

        String PatientName ;
        int PatientAge;
        int PatientID;
        String PatientPhoneNUM;
        String PatientGender;
        String PatientHealthIssue;
        String appointmentDetails;
        double billAmount;
        String diagnosis;
        String prescription;
        Node next;

        public Node(String PatientName, int PatientAge, int PatientID, String PatientPhoneNUM, String PatientGender, String PatientHealthIssue) {
            this.PatientName = PatientName;
            this.PatientAge = PatientAge;
            this.PatientPhoneNUM = PatientPhoneNUM;
            this.PatientID = PatientID;
            this.PatientGender = PatientGender;
            this.PatientHealthIssue = (PatientHealthIssue == null || PatientHealthIssue.isEmpty()) ? "unKnown" : PatientHealthIssue;
            this.appointmentDetails = "";
            this.billAmount = 0.0;
            this.diagnosis = null;
            this.prescription = null;
            this.next = null;
        }

    public Node(String doctorName, int doctorAge, String doctorSpecialization, int doctorID, String doctorContactNumber) {
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

