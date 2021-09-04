package Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    private static Statement statement;

    // Create Statement Object
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    // Getter - Return statement object
    public static Statement getStatement(){
        return statement;
    }
}
