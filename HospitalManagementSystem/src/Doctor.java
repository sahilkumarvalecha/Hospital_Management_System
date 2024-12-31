
import java.io.*;
import java.util.Scanner;


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
    String[][] specializationKeywords = {
            // Dermatologist
            {"Dermatologist", "flu", "skin", "rash", "eczema", "acne", "psoriasis", "allergy", "hives", "dermatitis", "itching", "scars", "blemishes"},
            // Cardiologist
            {"Cardiologist", "heart", "chest pain", "cardio", "arrhythmia", "palpitations", "shortness of breath", "hypertension", "blood pressure", "heart attack", "angina", "tachycardia"},
            // Orthopedist
            {"Orthopedist", "bone", "fracture", "joint pain", "arthritis", "back pain", "dislocation", "spine", "cartilage", "tendon", "scoliosis", "osteoporosis"},
            // Neurologist
            {"Neurologist", "brain", "headache", "migraine", "seizure", "epilepsy", "numbness", "stroke", "dizziness", "memory loss", "neuropathy", "paralysis", "tremor", "vertigo"},
            // Gastroenterologist
            {"Gastroenterologist", "stomach", "abdomen", "nausea", "vomiting", "diarrhea", "constipation", "acid reflux", "ulcer", "indigestion", "bloating", "liver", "pancreas", "gastritis"},
            // Pulmonologist
            {"Pulmonologist", "lung", "breathing", "cough", "asthma", "bronchitis", "pneumonia", "shortness of breath", "chest tightness", "wheezing", "respiratory", "tuberculosis", "sleep apnea"},
            // Endocrinologist
            {"Endocrinologist", "hormone", "thyroid", "diabetes", "insulin", "metabolism", "growth", "pituitary", "adrenal", "hypothyroidism", "hyperthyroidism", "obesity", "fatigue", "glucose"},
            // Pediatrician
            {"Pediatrician", "child", "infant", "baby", "newborn", "fever", "vaccination", "growth", "development", "pediatric", "rash", "infection", "nutrition", "cough"},
            // Ophthalmologist
            {"Ophthalmologist", "eye", "vision", "blurred vision", "glasses", "contact lens", "cataract", "glaucoma", "eye strain", "red eyes", "dry eyes", "itchy eyes", "conjunctivitis"},
            // Dentist
            {"Dentist", "teeth", "tooth", "cavity", "gum", "bleeding gums", "toothache", "braces", "orthodontics", "dentures", "mouth", "oral hygiene", "root canal", "wisdom tooth"},
            // Psychiatrist
            {"Psychiatrist", "mental health", "anxiety", "depression", "stress", "panic", "bipolar", "schizophrenia", "therapy", "trauma", "insomnia", "addiction", "psychosis", "ptsd"},
            // Urologist
            {"Urologist", "urine", "bladder", "kidney", "prostate", "urinary", "infection", "painful urination", "stones", "incontinence"},
            // Gynecologist
            {"Gynecologist", "pregnancy", "period", "menstruation", "menopause", "fertility", "ovaries", "uterus", "contraception", "hormonal imbalance", "pcos", "cyst", "labor"},
            // Oncologist
            {"Oncologist", "cancer", "tumor", "chemotherapy", "radiation", "lump", "malignant", "benign", "oncology", "breast cancer", "leukemia", "lymphoma", "metastasis", "biopsy"},
            // ENT Specialist
            {"ENT Specialist", "ear", "nose", "throat", "hearing", "sinus", "tonsils", "voice", "allergies", "snoring", "vertigo", "hoarseness", "nasal congestion", "tinnitus"},
            // Nephrologist
            {"Nephrologist", "kidney", "renal", "dialysis", "urinary tract", "nephrology", "edema", "proteinuria", "hematuria", "creatinine", "electrolytes", "kidney failure", "transplant"},
            // General Physician
            {"General Physician", "fever", "cold", "flu", "body ache", "fatigue", "infection", "weakness", "checkup", "general health", "headache", "cough", "pain"},
            // Rheumatologist
            {"Rheumatologist", "arthritis", "joint pain", "inflammation", "stiffness", "autoimmune", "lupus", "fibromyalgia", "gout", "connective tissue", "swelling", "rheumatoid"},
            // Allergist
            {"Allergist", "allergy", "asthma", "sneezing", "rash", "itching", "hay fever", "eczema", "food allergy", "dust", "pollen", "hives", "wheezing"},
            // Surgeon
            {"Surgeon", "operation", "surgery", "appendix", "hernia", "biopsy", "tumor", "incision", "post-surgery", "recovery", "laparoscopy", "trauma"}
    };

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

    public  void resize(){

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
        saveToFile(doctors,count);
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
        saveToFile(doctors,count);
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
    public static String findSpecialization(String[][] specializationKeywords, String patientInput) {
        // Convert patient input to lowercase
        String lowerCaseInput = patientInput.toLowerCase();

        // Loop through each specialization row
        for (int i = 0; i < specializationKeywords.length; i++) {
            String specialization = specializationKeywords[i][0]; // First column is specialization
            for (int j = 1; j < specializationKeywords[i].length; j++) {
                String keyword = specializationKeywords[i][j].toLowerCase();
                if (lowerCaseInput.contains(keyword)) {
                    return specialization; // Match found
                }
            }
        }

        return null; // No match found
    }
    public void displayDoctorAccToHealthIssue(String healthIssue) {
        String docAccToIssue = findSpecialization(specializationKeywords,healthIssue);
        if(docAccToIssue == null){
            System.out.println("No Doctors Available To Treat This Issue");
            return;
        }
        if (count == 0) {
            System.out.println("No Doctors are available");
            return;
        }
        int k =1;
        boolean doctorFound = false;// Track if any doctor is found
        System.out.println("Below Doctor Is Available For Appointment: ");
        for (Doctor doctor : doctors) {
            if (doctor != null && doctor.specialization.trim().equalsIgnoreCase(docAccToIssue.trim())){
                doctorFound = true;
                System.out.print(k +") Doctor name: " + doctor.doctorName +
                        ", Doctor specialization: " + doctor.specialization +
                        ", Doctor number: " + doctor.Number );
                k++;
                System.out.println();
            }
        }
        if(!doctorFound){
            System.out.println("No doctor found to treat " + healthIssue);

        }
    }
    public String searchDoctorById(int Id) {
        if (count == 0) {
            return "No Doctors are available";
        }
        boolean doctorFound = false;  // Track if any doctor is found
                for (Doctor doctor : doctors) {
            if (doctor.id == Id){
                return doctor.doctorName;
            }
        }
        return "No doctor found with Id " + Id;
    }
    public void searchDoctorBySpecialization(String specialization) {
        if (count == 0) {
            System.out.println("No Doctors are available");
            return;
        }
        boolean doctorFound = false;  // Track if any doctor is found
        int k =0;
        for (Doctor doctor : doctors) {
            if (doctor.specialization.equalsIgnoreCase(specialization)){
                doctorFound = true;
                System.out.print("Below Doctor Is Available For Appointment: ");
                System.out.println( k + ") Doctor name: " + doctor.doctorName +
                        ", Doctor specialization: " + doctor.specialization +
                        ", Doctor number: " + doctor.Number +
                        ", Doctor availability timing: ");
                for (int i = 0; i < doctor.availability.length; i++) {
                    System.out.print( i + ":" + doctor.availability[i]);
                }
                System.out.println();
            }
            k++;
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
    public void createFile(){
        File myFile = new File("doctorData.txt");
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
    // Save all doctor details to a file
    public static void saveToFile(Doctor[] doctors, int count) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("doctorData.txt"))) {
            for (int i = 0; i < count; i++) {
                Doctor doctor = doctors[i];
                if (doctor != null) {
                    writer.write("ID:" + doctor.id + ", Name:" + doctor.doctorName +
                            ", Specialization:" + doctor.specialization +
                            ", Number:" + doctor.Number +
                            ", Availability:" + String.join(";", doctor.availability));
                    writer.newLine();
                }
            }
            System.out.println("Doctor data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving doctor data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void loadFromFile() {
        File file = new File("doctorData.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] fields = line.split(", ");
                int id = Integer.parseInt(fields[0].split(":")[1].trim());
                String name = fields[1].split(":")[1].trim();
                String specialization = fields[2].split(":")[1].trim();
                int number = Integer.parseInt(fields[3].split(":")[1].trim());
                String[] availability = fields[4].split(":")[1].trim().split(";");

                // Add the doctor directly to the array
                if (count == doctors.length) {
                    resize();
                }
                doctors[count++] = new Doctor(id, name, specialization, availability, number);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading doctor data: " + e.getMessage());
        }
    }


}
