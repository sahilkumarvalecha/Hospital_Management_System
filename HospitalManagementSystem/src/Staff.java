public class Staff {
    PatientManagement pm;
    doctorManagement dm;
    Staff(){
        pm = new PatientManagement();
        dm = new doctorManagement();
    }
    public void addNewPatient(String name, int age, String phone, String gender, String healthIssue){
        pm.insertPatient(name, age, phone, gender, healthIssue);
    }
    public void updatePatientInfo(int patientId, String name, int age, String gender, String healthIssue){
        pm.updatePatientInfo(patientId, name, age, gender, healthIssue);
    }
    public void addNewDoctor(String name, String specialization, String[] availability, int number){
        dm.addDoctor(name, specialization, availability, number);
    }
    public void updateDoctorInfo(int id, String name, String specialization, String[] availability ,int number){
        dm.updateDoctor(id, name, specialization, availability, number);
    }
    public void searchPatient(int patientId){
        pm.SearchPatient(patientId);
    }
    public void viewAllDoctors(){
        dm.displayAllDoctors();
    }
    public void searchDoctor(String specialization){
        dm.searchDoctorBySpecialization(specialization);
    }

}