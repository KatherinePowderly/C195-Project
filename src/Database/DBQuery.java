package Database;

import java.sql.*;

/** This class creates and returns the prepared statement object.*/
public class DBQuery {

    private static PreparedStatement statement;

    /** This method sets the Prepared Statement object
     * @param connection Database Connection
     * @param sqlStatement SQL Statement string
     * @throws SQLException Catches SQLException and prints stacktrace.
     */
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    /** This method returns the Prepared Statement object
     * @return Prepared Statement
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
