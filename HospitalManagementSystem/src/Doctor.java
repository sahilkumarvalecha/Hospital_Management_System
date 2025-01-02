import java.io.*;
import java.util.Arrays;
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
    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", doctorName='" + doctorName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", availability=" + Arrays.toString(availability) +
                ", Number=" + Number +
                '}';
    }
}
class doctorManagement{
    Doctor[] doctors;
    int count;
    private int doctorIdCounter = 1;
    int maxId;
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
        maxId = 0;
        this.doctorIdCounter = doctorIdInitilizer();
    }

    private int doctorIdInitilizer() {
        File myFile = new File("doctorData.txt");
        if (!myFile.exists()) {
            return 1; // Start from 1 if file doesn't exist
        }
        boolean isFileLoaded = false;
        try (Scanner sc = new Scanner(myFile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                for (String part : parts) {
                    if (part.trim().startsWith("ID:")) {
                        String idStr = part.split(":")[1].trim();
                        int id = stringToInt(idStr);
                        maxId = Math.max(maxId, id); // Track highest ID
                    }
                }
                isFileLoaded = true;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!isFileLoaded) {
            loadFromFile();
        }
        return maxId + 1; // Increment the last ID
    }
    private int stringToInt(String str) {
        int result = 0;
        boolean isNegative = false;
        int i = 0;

        // Check for a negative sign
        if (str.charAt(0) == '-') {
            isNegative = true;
            i++;
        }

        // Process each character
        for (; i < str.length(); i++) {
            char ch = str.charAt(i);

            // Ensure the character is a digit
            if (ch < '0' || ch > '9') {
                throw new NumberFormatException("Invalid character in number: " + ch);
            }

            result = result * 10 + (ch - '0');
        }

        return isNegative ? -result : result;
    }
    public  void resize(){

        Doctor[] temp = new Doctor[doctors.length + 10];
        for (int i=0; i<doctors.length; i++){
            temp[i] = doctors[i];
        }
        doctors = temp;
    }
    public void addDoctor(String name, String specialization, String[] availability, int number) {
        loadFromFile();
        if (count == doctors.length) {
            resize();
        }
        doctors[count++] = new Doctor(doctorIdCounter, name, specialization, availability, number);
        doctorIdCounter++;
        saveToFile();
    }
    public void updateDoctor(int id, String name, String specialization, String[] availability ,int number){
        loadFromFile();
        for (Doctor doctor:doctors){
            if (doctor != null && doctor.id == id){
                doctor.doctorName = name;
                doctor.specialization = specialization;
                doctor.availability = availability;
                doctor.Number = number;
                System.out.println("Doctor updated successfully!");
                saveToFile();
                return;
            }
        }
        System.out.println("Doctor with ID " +id+ " not found ");
    }
    public static void heapSort(Doctor[] doctors) {
        int n = doctors.length;

        // Build the max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(doctors, n, i);
        }

        // Extract elements from the heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Swap the root (largest) with the last element
            Doctor temp = doctors[0];
            doctors[0] = doctors[i];
            doctors[i] = temp;

            // Heapify the reduced heap
            heapify(doctors, i, 0);
        }
    }

    private static void heapify(Doctor[] doctors, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // Left child
        int right = 2 * i + 2; // Right child

        // Check if the left child is not null and larger than root
        if (left < n && doctors[left] != null && doctors[largest] != null &&
                compareStrings(doctors[left].doctorName, doctors[largest].doctorName) > 0) {
            largest = left;
        }

        // Check if the right child is not null and larger than the largest so far
        if (right < n && doctors[right] != null && doctors[largest] != null &&
                compareStrings(doctors[right].doctorName, doctors[largest].doctorName) > 0) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            // Swap
            Doctor swap = doctors[i];
            doctors[i] = doctors[largest];
            doctors[largest] = swap;

            // Recursively heapify the affected subtree
            heapify(doctors, n, largest);
        }
    }


    // Custom string comparison function
    private static int compareStrings(String s1, String s2) {
        int minLength = Math.min(s1.length(), s2.length());
        for (int i = 0; i < minLength; i++) {
            if (s1.charAt(i) < s2.charAt(i)) {
                return -1;
            } else if (s1.charAt(i) > s2.charAt(i)) {
                return 1;
            }
        }
        // If strings are identical up to the length of the shorter one
        if (s1.length() < s2.length()) {
            return -1;
        } else if (s1.length() > s2.length()) {
            return 1;
        }
        return 0;
    }
    public static String toLowerCase(String input) {
        char[] result = new char[input.length()];

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            // Check if the character is uppercase (A-Z)
            if (ch >= 'A' && ch <= 'Z') {
                // Convert to lowercase by adding 32 (ASCII difference)
                result[i] = (char) (ch + 32);
            } else {
                // Assign the character as-is
                result[i] = ch;
            }
        }

        return new String(result); // Convert the char array back to a String
    }
    public static String findSpecialization(String[][] specializationKeywords, String patientInput) {
        // Convert patient input to lowercase
        String lowerCaseInput = toLowerCase(patientInput);

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
    public boolean displayDoctorAccToHealthIssue(String healthIssue) {
        // Find the specialization based on the health issue
        boolean doctorFound = false; // To track if any doctor is found

        String docAccToIssue = findSpecialization(specializationKeywords, healthIssue);

        if (docAccToIssue == null) {
            System.out.println("No Doctors Available To Treat This Issue");
            return false;
        }
        if (count == 0) {
            System.out.println("No Doctors are available");
            return false;
        }

        // Track doctors already displayed to avoid duplicates
        boolean[] displayed = new boolean[count];

        int k = 1; // Display numbering

        System.out.println("Below Doctor(s) Are Available For Appointment: ");
        for (int i = 0; i < count; i++) {
            Doctor doctor = doctors[i];
            if (doctor != null && doctor.specialization.trim().equalsIgnoreCase(docAccToIssue.trim()) && !displayed[i]) {
                doctorFound = true;
                displayed[i] = true; // Mark this doctor as displayed
                System.out.println(k + ") Doctor name: " + doctor.doctorName +
                        ", Doctor specialization: " + doctor.specialization +
                        ", Doctor number: " + doctor.Number);
                k++;
            }
        }

        if (!doctorFound) {
            System.out.println("No doctor found to treat " + healthIssue);
            return false;
        }
        return true;
    }
    public Doctor searchDoctorById(int Id) {
        if (count == 0) {
            return null;
        }
        boolean doctorFound = false;  // Track if any doctor is found
        for (Doctor doctor : doctors) {
            if (doctor.id == Id){
                return doctor;
            }
        }
        return null;
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
    public void displayAllDoctors() {
        if (count == 0 || doctors == null) {
            System.out.println("No Doctors are available");
            return;
        }
        boolean doctorPresent = false;
        heapSort(doctors);
        for (Doctor doctor : doctors) {
            if (doctor != null) {
                doctorPresent = true;
                System.out.println(doctor);
                System.out.println();
            }
        }
        if (!doctorPresent) {
            System.out.println("Encountered a null Doctor entry.");
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
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("doctorData.txt"))) {
            for (Doctor doctor : doctors){
                if (doctor != null) {
                    writer.write("ID:" + doctor.id + ", Name:" + doctor.doctorName +
                            ", Specialization:" + doctor.specialization +
                            ", Number:" + doctor.Number +
                            ", Availability:" + String.join(",", doctor.availability));
                    writer.newLine();
                }
            }
            System.out.println("Doctor data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving doctor data: " + e.getMessage());
        }
    }
    public void loadFromFile() {
        try {
            File f = new File("doctorData.txt");
            if (!f.exists()) {
                f.createNewFile();
            } else {
                FileReader fr = new FileReader(f);
                BufferedReader s = new BufferedReader(fr);

                String line;
                while ((line = s.readLine()) != null) {
                    // Split line by commas and trim excess spaces
                    String[] doctorData = line.split(",");

                    if (doctorData.length < 5) {
                        System.out.println("Invalid doctor data format.");
                        continue;  // Skip invalid data
                    }

                    // Parse ID (with manual validation)
                    int id = 0;
                    String[] idParts = doctorData[0].split(":");
                    if (idParts.length > 1) {
                        String idString = idParts[1].trim();
                        for (int i = 0; i < idString.length(); i++) {
                            id = id * 10 + (idString.charAt(i) - '0');
                        }
                    }

                    // Parse Name (considering spaces in the name)
                    String name = doctorData[1].split(":")[1].trim();

                    // Parse Specialization
                    String specialization = doctorData[2].split(":")[1].trim();

                    // Parse Number (with manual validation)
                    int number = 0;
                    String[] numberParts = doctorData[3].split(":");
                    if (numberParts.length > 1) {
                        String numberString = numberParts[1].trim();
                        for (int i = 0; i < numberString.length(); i++) {
                            number = number * 10 + (numberString.charAt(i) - '0');
                        }
                    }
                    // Parse Availability
                    String[] availability = doctorData[4].split(":")[1].trim().split(";");

                    // Create a new Doctor object and add to the array
                    if (count == doctors.length) {
                        resize();
                    }
                    doctors[count++] = new Doctor(id, name, specialization, availability, number);
                }
                s.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading doctor data: " + e.getMessage());
        }
    }
}