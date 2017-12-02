package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HospitalDBMediator {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost/" +
            "hospital_schema?user=admin&password=password";
    private static final String QUERY_DOCTORS = "SELECT * FROM Doctors;";
    private static final String QUERY_ROOMS = "SELECT * FROM Rooms;";
    private static final String QUERY_PATIENTS = "SELECT * FROM Patients;";

    public static ArrayList<String> getPatients() {
        ArrayList<String> retval = new ArrayList<>();
        Connection conn = null;

        try {
            conn =  DriverManager.getConnection(CONNECTION_URL);

            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_PATIENTS);
            ResultSet result = preparedStatement.executeQuery();

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

            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_DOCTORS);
            ResultSet result = preparedStatement.executeQuery();

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

            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_ROOMS);
            ResultSet result = preparedStatement.executeQuery();

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
}