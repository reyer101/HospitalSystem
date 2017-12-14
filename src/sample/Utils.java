package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.UUID;

public class Utils {
    public static String getStringSelection(ChoiceBox choiceBox) {
        String retval = "";
        Object obj = choiceBox.getSelectionModel().getSelectedItem();

        if(obj != null) {
            retval = obj.toString();
        }
        return retval;
    }

    public static int getRoomNumber  (String roomString) throws NumberFormatException {
        return Integer.parseInt(roomString.split(" ")[0]);
    }

    public static String getDoctorName(String doctorString) {
        String retval = "";
        if(!doctorString.isEmpty()) {
            String[] split = doctorString.split(" ");
            retval = split[0] + " " + split[1];
        }
        return retval;
    }

    public static void generatePatientReport(int pid) {
        BufferedWriter writer;
        HashMap<String, String> patientHistory
                = HospitalDBMediator.getPatientHistory(pid);

        String filename = "Report_" + patientHistory.get(
                "name").replace(" ", "_") + "_" +
                patientHistory.get("pid") + ".csv";

        String patientReport = "Name: %s, PID: %s, Diagnosis: %s," +
                "Prescription: %s, Doctor ID: %s, Room: %s";

        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(String.format(patientReport, patientHistory.get("name"),
                    patientHistory.get("pid"), patientHistory.get("diagnosis"),
                    patientHistory.get("prescription"), patientHistory.get("doctor"),
                    patientHistory.get("room"))
            );

            writer.close();
        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Inserting Patient");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}