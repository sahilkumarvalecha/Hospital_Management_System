import java.io.*;
import java.util.ArrayList;

public class Filing {
    public void saveLikedListToFile(String filename, Node head) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Node current = head;
            while (current != null) {
                writer.write(current.PatientName + "," + current.PatientAge + "," +current.PatientID+ " "+ current.PatientPhoneNUM + "," + current.PatientGender + "," + current.PatientHealthIssue);
                writer.newLine();
                current = current.next;
            }
            System.out.println("Patient data saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving patient data.");
            e.printStackTrace();
        }
    }


    public ArrayList<String> loadPatientsFromFile(String filename) {

        ArrayList<String> fileLines = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading patient data.");
            e.printStackTrace();
        }


        return fileLines;


    }



}

