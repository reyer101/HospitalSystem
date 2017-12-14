package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HospitalDBMediator {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost/" +
            "hospital_schema?user=admin&password=password";
    private static final String QUERY_DOCTORS = "CALL get_doctors()";
    private static final String QUERY_ROOMS = "CALL get_rooms();";
    private static final String QUERY_PATIENTS = "CALL get_patients()";
    private static final String QUERY_PATIENT = "CALL get_patient_by_id(?)";
    private static final String INSERT_DIAGNOSIS = "CALL add_diagnoses(?);";
    private static final String INSERT_PRESCRIPTION = "CALL add_prescription(?);";
    private static final String INSERT_PATIENTS = "CALL add_patient(?, ?, ?, ?, ?, ?)";
    private static final String GET_NEXT_ID = "{ ? = call autoDiagID()}";

    public static HashMap<String, Integer> getPatients() {
        HashMap<String, Integer> retval = new HashMap<>();
        Connection conn = null;

        try {
            conn =  DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_PATIENTS);
            ResultSet result = preparedStatement.executeQuery();

            conn.commit();

            while(result.next())
            {
                retval.put(String.format(result.getString(
                        2) + " (%d)", result.getInt(1)),
                        result.getInt(1));
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

        try {
            conn =  DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);

            CallableStatement dStat = conn.prepareCall(GET_NEXT_ID);
            dStat.registerOutParameter(1, Types.INTEGER);
            dStat.execute();

            int nextID = dStat.getInt(1);

            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DIAGNOSIS);
            preparedStatement.setString(1, diagnosis);
            preparedStatement.execute();

            preparedStatement = conn.prepareStatement(INSERT_PRESCRIPTION);
            preparedStatement.setString(1, prescription);
            preparedStatement.execute();

            preparedStatement = conn.prepareStatement(INSERT_PATIENTS);
            preparedStatement.setInt(1, pid);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, nextID);
            preparedStatement.setInt(4, nextID);
            preparedStatement.setInt(5, doctor);
            preparedStatement.setInt(6, room);
            preparedStatement.execute();

            conn.commit();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

            if(ex.getErrorCode() == 1062) {
                Utils.showError("A patient with that pid already exists...");
            }
            return;
        }

        Utils.showMessage("Patient inserted successfully");
    }

    public static HashMap<String, String> getPatientHistory(int pid) {
        HashMap<String, String> retval = new HashMap<>();
        Connection conn = null;

        try {
            conn =  DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);

            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_PATIENT);
            preparedStatement.setInt(1, pid);
            ResultSet result = preparedStatement.executeQuery();

            conn.commit();

            while(result.next())
            {
                retval.put("pid", Integer.toString(result.getInt(1)));
                retval.put("name", result.getString(2));
                retval.put("diagnosis", Integer.toString(result.getInt(3)));
                retval.put("prescription", Integer.toString(result.getInt(4)));
                retval.put("doctor", Integer.toString(result.getInt(5)));
                retval.put("room", Integer.toString(result.getInt(6)));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return retval;
    }
}