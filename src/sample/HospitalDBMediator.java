package sample;

import java.sql.*;

public class HospitalDBMediator {
    private static final String TEST = "SELECT * FROM Doctors;";

    public static void test() {
        Connection conn = null;

        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/hospital_schema?" +
                            "user=admin&password=password");

            PreparedStatement preparedStatement = conn.prepareStatement(TEST);
            ResultSet result = preparedStatement.executeQuery();

            while(result.next())
            {
                System.out.println("Name: " + result.getString("name"));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
