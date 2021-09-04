package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    private static PreparedStatement statement;

    // Create Statement Object
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    // Getter - Return statement object
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }
}
