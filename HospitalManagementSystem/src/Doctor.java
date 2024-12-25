public class Doctor {
    int id;
    String doctorName;
    String specialization;
    String[] availability;
    int Number;
    Doctor(int id, String doctorName, String specialization, String[] availability, int number) {
        this.id = id;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.availability = availability;
        Number = number;
    }
    public void setDiagnosis(Node patient, String diagnosis){
        patient.setDiagnosis(diagnosis);
        System.out.println("Doctor " +doctorName+ " set diagnosis for " +patient.PatientName + " : " +diagnosis);
    }
    public void setPrescription(Node patient, String prescription){
        patient.setPrescription(prescription);
        System.out.println("Doctor " +doctorName+ " set prescription for " +patient.PatientName + " : " +prescription);
    }
}
class doctorManagement{
    Doctor[] doctors;
    int count;
    doctorManagement(){
        doctors = new Doctor[10];
        count = 0;
    }
    private boolean isUniqueId(int id){
        for (Doctor doc : doctors){
            if (doc!= null && doc.id == id){
                return false; // not unique
            }
        }
        return true; // id unique
    }
    private boolean isUniqueNumber(int number){
        for (Doctor doc : doctors){
            if (doc!= null && doc.Number == number){
                return false; // not unique
            }
        }
        return true; // number unique
    }

    public void resize(){

        Doctor[] temp = new Doctor[doctors.length + 10];
        for (int i=0; i<doctors.length; i++){
            temp[i] = doctors[i];
        }
        doctors = temp;
    }
    public void addDoctor(int id, String name, String specialization, String[] availability, int number){
        if (!isUniqueId(id)){
            System.out.println("Error: Doctor with ID " + id + " already exists.");
            return;
        }
        if (!isUniqueNumber(number)) {
            System.out.println("Error: Doctor with contact number " + number + " already exists.");
            return;
        }
        if (count==doctors.length){
            resize();
        }
        doctors[count++] = new Doctor(id, name, specialization, availability, number);
        doctors[count - 1].availability = availability; // Set availability directly
    }
    public void deleteDoctor(int id){
        for (int i=0; i< count; i++){
            if (doctors[i].id == id){
                for (int j=i; j<count-1; j++){
                    doctors[j] = doctors[j+1];
                }
                doctors[count-1] = null;
                count--;
                System.out.println("doctor deleted successfully");
                return;
            }
        }
        System.out.println("Doctor not found");
    }
    public void updateDoctor(int id, String name, String specialization, String[] availability ,int number){
        for (Doctor doctor:doctors){
            if (doctor != null && doctor.id == id){
                doctor.doctorName = name;
                doctor.specialization = specialization;
                doctor.availability = availability;
                doctor.Number = number;
                System.out.println("Doctor updated successfully!");
                return;
            }
            System.out.println("Doctor with ID " +id+ " not found ");
        }
    }
    public void searchDoctorBySpecialization(String specialization) {
        if (count == 0) {
            System.out.println("No Doctors are available");
            return;
        }
        boolean doctorFound = false;  // Track if any doctor is found
        for (Doctor doctor : doctors) {
            if (doctor.specialization.equalsIgnoreCase(specialization)){
                doctorFound = true;
                System.out.print(" Doctor name: " + doctor.doctorName +
                        ", Doctor specialization: " + doctor.specialization +
                        ", Doctor number: " + doctor.Number +
                        ", Doctor availability timing: ");
                for (int i = 0; i < doctor.availability.length; i++) {
                    System.out.print( i + ":" + doctor.availability[i]);
                }
                System.out.println();
            }
        }
        if(!doctorFound){
            System.out.println("No doctor found with specialization " +specialization);
        }
    }
    public void searchDoctorByAvailability(String dayAndTime) {
        boolean doctorFound = false;
        if (count == 0) {
            System.out.println("No Doctors are available");
            return;
        }
        for (Doctor doctor : doctors) {
            if (doctor != null){
                for (String timeSlot : doctor.availability){
                    if (timeSlot != null && timeSlot.equalsIgnoreCase(dayAndTime)){
                        System.out.print(" Doctor name: " + doctor.doctorName +
                                ", Doctor specialization: " + doctor.specialization +
                                ", Doctor number: " + doctor.Number +
                                ", Doctor availability timing: ");
                        for (int i = 0; i < doctor.availability.length; i++) {
                            System.out.print(" " + doctor.availability[i]);
                        }
                        doctorFound = true;
                    }
                }
            }
        }
        if (!doctorFound){
            System.out.println("No doctor available at: " +dayAndTime);
        }
    }
    public void displayAllDoctors() {
        if (count == 0 || doctors == null) {
            System.out.println("No Doctors are available");
            return;
        }
        boolean doctorPresent = false;
        for (Doctor doctor : doctors) {
            if (doctor != null) {
                doctorPresent = true;
                System.out.print(" Doctor name: " + doctor.doctorName +
                        ", Doctor specialization: " + doctor.specialization +
                        ", Doctor number: " + doctor.Number +
                        ", Doctor availability timing: ");
                if (doctor.availability != null) {
                    for (String time : doctor.availability) {
                        System.out.print(", " + time);
                    }
                } else {
                    System.out.print(" No availability info.");
                }
                System.out.println();
            }
        }
        if (!doctorPresent) {
            System.out.println("Encountered a null Doctor entry.");
        }
    }

    public void allocateDoctor(String specialization){
        if (count == 0){
            System.out.println("No doctor available");
            return;
        }
        for (Doctor doctor : doctors){
            if (doctor.specialization.equalsIgnoreCase(specialization)){
                System.out.print(" Assigned Doctor: \n" + " " +
                        " Doctor name: " +doctor.doctorName+
                        ", Doctor specialization: " +doctor.specialization+
                        ", Doctor number: " +doctor.Number+
                        ", Doctor availability timing: " );
                for (int i=0; i<doctor.availability.length; i++){
                    System.out.print(" " +doctor.availability[i]);
                }
                break;
            }
        }
    }

}