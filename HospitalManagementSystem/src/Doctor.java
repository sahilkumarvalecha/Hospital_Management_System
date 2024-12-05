public class Doctor {
    int id;
    String doctorName;
    String specialization;
    String[] availability;
    int Number;
    Doctor(int id, String doctorName, String specialization, int number) {
        this.id = id;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.availability = new String[2];
        Number = number;
    }
}
class doctorManagement{
    Doctor[] doctors;
    int count;
    doctorManagement(){
        doctors = new Doctor[1];
        count = 0;
    }
    public void resize(){

        Doctor[] temp = new Doctor[doctors.length + 1];
        for (int i=0; i<doctors.length; i++){
            temp[i] = doctors[i];
        }
        doctors = temp;
    }
    public void addDoctor(int id, String name, String specialization, String[] availability, int number){
        if (count==doctors.length){
            resize();
        }
        doctors[count++] = new Doctor(id, name, specialization, number);
        doctors[count - 1].availability = availability; // Set availability directly
    }
    public void deleteDoctor(int id){
        for (int i=0; i< doctors.length; i++){
            if (doctors[i].id == id){
                for (int j=i; j< doctors.length; j++){
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
        for (Doctor doctor : doctors) {
            if (doctor.specialization.equalsIgnoreCase(specialization)){
                System.out.print(" Doctor name: " + doctor.doctorName +
                        ", Doctor specialization: " + doctor.specialization +
                        ", Doctor number: " + doctor.Number +
                        ", Doctor availability timing: ");
                for (int i = 0; i < doctor.availability.length; i++) {
                    System.out.print(" " + doctor.availability[i]);
                }
            }
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
    public void displayAllDoctors(){
        if (count == 0){
            System.out.println("No Doctors are available");
            return;
        }
        for (Doctor doctor : doctors){
            System.out.print(" Doctor name: " +doctor.doctorName+
                    ", Doctor specialization: " +doctor.specialization+
                    ", Doctor number: " +doctor.Number+
                    ", Doctor availability timing: " );
            for (int i=0; i<doctor.availability.length; i++){
                System.out.print(" " +doctor.availability[i]);
            }
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
            }
        }
    }

}