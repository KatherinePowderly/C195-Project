package Database;

import java.sql.*;

public class DBQuery {

    private static PreparedStatement statement;

    // Create Statement Object
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    // Getter - Return statement object
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
