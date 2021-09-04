package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql";
    private static final String ipAddress = "://wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06qNV";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String username = "U06qNV";
    private static Connection conn = null;

    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);

            conn = DriverManager.getConnection(jdbcURL, username, "53688842548");

            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return conn;
    }

    public static void closeConnection() {

        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
