package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HospitalDBMediator {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost/" +
            "hospital_schema?user=admin&password=password";
    private static final String QUERY_DOCTORS = "SELECT * FROM Doctors;";
    private static final String QUERY_ROOMS = "SELECT * FROM Rooms;";
    private static final String QUERY_PATIENTS = "SELECT * FROM Patients;";
    private static final String INSERT_PATIENTS = "insertPatients(?, ?, ?, ?, ?, ?)";

    public static ArrayList<String> getPatients() {
        ArrayList<String> retval = new ArrayList<>();
        Connection conn = null;

        try {
            conn =  DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_PATIENTS);
            ResultSet result = preparedStatement.executeQuery();

            conn.commit();

            while(result.next())
            {
                retval.add(result.getString(2));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return retval;
    }

    public static HashMap<String, Integer> getDoctors() {
        HashMap<String, Integer> retval = new HashMap<>();
        Connection conn = null;

        try {
            conn =  DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_DOCTORS);
            ResultSet result = preparedStatement.executeQuery();

            conn.commit();

            while(result.next())
            {
                String doc = result.getString(2)
                        + " (" + result.getInt(3) + ")";
                retval.put(doc, result.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return retval;
    }

    public static ArrayList<String> getRooms() {
        ArrayList<String> retval = new ArrayList<>();
        Connection conn = null;

        try {
            conn =  DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_ROOMS);
            ResultSet result = preparedStatement.executeQuery();

            conn.commit();

            while(result.next())
            {
                String room = String.format(result.getInt(
                        1) + " (%s)", result.getInt(2));
                retval.add(room);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return retval;
    }

    public static void storePatient(int pid, String name, String diagnosis
            , String prescription, int doctor, int room) {

        Connection conn = null;

        // Insert prescription and diagnosis into database first..
        // Use randomly generated ID's

        Random rand = new Random();
        int randID = rand.nextInt(999) + 1;

        try {
            conn =  DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PATIENTS);
            preparedStatement.setInt(1, pid);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, randID);
            preparedStatement.setInt(4, randID);
            preparedStatement.setInt(5, doctor);
            preparedStatement.setInt(6, room);
            preparedStatement.execute();

            conn.commit();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}