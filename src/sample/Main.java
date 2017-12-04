package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application  {
    ChoiceBox choiceDoctor, choicePatient, choiceRoom;
    TextField txtName, txtPID, txtDiagnosis, txtPrescription;
    Button btnReport, btnInsert;
    Label lPID, lPatientName, lDiagnosis, lPrescription, lDoctor, lRoom;

    HashMap<String, Integer> mDoctors, mPatients;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hospital Management Portal");

        mPatients = HospitalDBMediator.getPatients();
        mDoctors = HospitalDBMediator.getDoctors();

        Font labelFont = new Font("Arial", 15);

        // Initialize controls
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        txtName = new TextField();
        txtName.setPromptText("Name");

        txtPID = new TextField();
        txtPID.setPromptText("Patient ID (ex. 1234)");

        txtDiagnosis = new TextField();
        txtDiagnosis.setPromptText("Hypertension, diabetes, etc");

        txtPrescription = new TextField();
        txtPrescription.setPromptText("Synthroid, Crestor, etc");

        choicePatient = new ChoiceBox();
        choicePatient.setItems(FXCollections.observableArrayList(
                new ArrayList<String>(mPatients.keySet())
        ));

        choiceDoctor = new ChoiceBox();
        choiceDoctor.setItems(FXCollections.observableArrayList(
                new ArrayList<String>(mDoctors.keySet())
        ));

        choiceRoom = new ChoiceBox();
        choiceRoom.setItems(FXCollections.observableArrayList(
                HospitalDBMediator.getRooms())
        );

        lPID = new Label("Patient ID");
        lPID.setFont(labelFont);

        lPatientName = new Label("Patient name");
        lPatientName.setFont(labelFont);

        lDiagnosis = new Label("Diagnosis");
        lDiagnosis.setFont(labelFont);

        lPrescription = new Label("Prescription");
        lPrescription.setFont(labelFont);

        lDoctor = new Label("Doctor");
        lDoctor.setFont(labelFont);

        lRoom = new Label("Room");
        lRoom.setFont(labelFont);

        btnReport = new Button();
        btnReport.setText("Generate report");
        btnReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String patient = Utils.getStringSelection(choicePatient);
                if(!patient.isEmpty()) {
                    Utils.generatePatientReport(mPatients.get(patient));
                } else {
                    // TODO: Do something when generate report clicked and no patient
                    System.out.println("No patient selected");
                }
            }
        });

        btnInsert = new Button();
        btnInsert.setText("Insert patient");
        btnInsert.setOnAction(e -> insertPatient());

        // Add controls to grid
        grid.add(btnReport, 0, 0);
        grid.add(btnInsert, 0, 9);

        grid.add(lPID, 0, 3);
        grid.add(lPatientName, 0, 4);
        grid.add(lDiagnosis, 0, 5);
        grid.add(lPrescription, 0, 6);
        grid.add(lDoctor, 0, 7);
        grid.add(lRoom, 0, 8);

        grid.add(txtPID, 1, 3);
        grid.add(txtName, 1, 4);
        grid.add(txtDiagnosis, 1, 5);
        grid.add(txtPrescription, 1, 6);

        grid.add(choicePatient, 1, 0);
        grid.add(choiceDoctor, 1, 7);
        grid.add(choiceRoom, 1, 8);

        Scene scene = new Scene(grid, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void insertPatient() {
        int pid, doctor, room;
        String patientName, diagnosis, prescription;
        try {
            pid = Integer.parseInt(txtPID.getText());
            patientName = txtName.getText();
            diagnosis = txtDiagnosis.getText();
            prescription = txtPrescription.getText();

            doctor = mDoctors.get(Utils.getStringSelection(choiceDoctor));
            room = Utils.getRoomNumber(Utils.getStringSelection(choiceRoom));

            if(patientName.isEmpty() || diagnosis.isEmpty() || prescription.isEmpty()) {
                throw new Exception("Input string empty");
            }

//            HospitalDBMediator.storePatient(pid, patientName, diagnosis,
//                    prescription, doctor, room);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}