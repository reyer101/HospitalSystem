package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hospital Management Portal");

        HashMap<String, Integer> mDoctors = HospitalDBMediator.getDoctors();

        Font labelFont = new Font("Arial", 15);

        // Initialize controls
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button btnReport = new Button();
        btnReport.setText("Generate report");

        Button btnInsert = new Button();
        btnInsert.setText("Insert patient");

        TextField txtReport = new TextField();
        txtReport.setPromptText("Patient name");

        TextField txtName = new TextField();
        txtName.setPromptText("Name");

        TextField txtPID = new TextField();
        txtPID.setPromptText("Patient ID (ex. 1234)");

        TextField txtDiagnosis = new TextField();
        txtDiagnosis.setPromptText("Hypertension, diabetes, etc");

        TextField txtPrescription = new TextField();
        txtPrescription.setPromptText("Synthroid, Crestor, etc");

        ChoiceBox choiceDoctor = new ChoiceBox();
        choiceDoctor.setItems(FXCollections.observableArrayList(
                new ArrayList<String>(mDoctors.keySet())
        ));

        ChoiceBox choiceRoom = new ChoiceBox();
        choiceRoom.setItems(FXCollections.observableArrayList(
                HospitalDBMediator.getRooms())
        );

        Label lPID = new Label("Patient ID");
        lPID.setFont(labelFont);

        Label lPatientName = new Label("Patient name");
        lPatientName.setFont(labelFont);

        Label lDiagnosis = new Label("Diagnosis");
        lDiagnosis.setFont(labelFont);

        Label lPrescription = new Label("Prescription");
        lPrescription.setFont(labelFont);

        Label lDoctor = new Label("Doctor");
        lDoctor.setFont(labelFont);

        Label lRoom = new Label("Room");
        lRoom.setFont(labelFont);

        // Add controls to grid
        grid.add(btnReport, 0, 0);
        grid.add(btnInsert, 0, 9);

        grid.add(lPID, 0, 3);
        grid.add(lPatientName, 0, 4);
        grid.add(lDiagnosis, 0, 5);
        grid.add(lPrescription, 0, 6);
        grid.add(lDoctor, 0, 7);
        grid.add(lRoom, 0, 8);

        grid.add(txtReport, 1, 0);
        grid.add(txtPID, 1, 3);
        grid.add(txtName, 1, 4);
        grid.add(txtDiagnosis, 1, 5);
        grid.add(txtPrescription, 1, 6);
        grid.add(choiceDoctor, 1, 7);
        grid.add(choiceRoom, 1, 8);

        Scene scene = new Scene(grid, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}