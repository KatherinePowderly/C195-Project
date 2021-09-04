package Main;

import Database.DBConnection;
import Database.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.startConnection();

        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

//        insert(statement);
//        update(statement);
//        delete(statement);
        getAll(statement);


        launch(args);
        DBConnection.closeConnection();
    }

    private static void insert(Statement statement) throws SQLException{
        // Raw SQL insert statement - when using variables, we need to add commas etc between each variable for the sql query
        String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) values ('US', '2021-08-24 00:00:00', 'admin', 'admin')";

        statement.execute(insertStatement);

        if (statement.getUpdateCount() > 0) {
            System.out.println("Rows affected: " + statement.getUpdateCount());
        } else {
            System.out.println("No change");
        }
    }

    private static void update(Statement statement) throws SQLException{
        // Update
        String updateStatement =  "UPDATE countries SET Country = 'Japan' WHERE Country = 'US'";

        statement.execute(updateStatement);

        if (statement.getUpdateCount() > 0) {
            System.out.println("Rows affected: " + statement.getUpdateCount());
        } else {
            System.out.println("No change");
        }
    }

    private static void delete(Statement statement) throws SQLException{
        // Delete
        String deleteStatement =  "DELETE FROM countries WHERE Country = 'Japan'";

        statement.execute(deleteStatement);

        if (statement.getUpdateCount() > 0) {
            System.out.println("Rows affected: " + statement.getUpdateCount());
        } else {
            System.out.println("No change");
        }
    }

    private static void getAll(Statement statement) throws SQLException{
        String selectStatement = "SELECT * FROM countries";
        statement.execute(selectStatement);
        ResultSet resultSet = statement.getResultSet();

        // Forward scroll resultSet
        while (resultSet.next()) {

            String separate = " | ";

            int countryId = resultSet.getInt("Country_Id");

            LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();

            LocalTime createTime = resultSet.getTime("Create_Date").toLocalTime();

            String createdBy = resultSet.getString("Created_By");

            LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();

            String updatedBy = resultSet.getString("Last_Updated_By");

            System.out.println(countryId + separate + createDate + separate + createTime + separate + createdBy + separate + lastUpdate + separate + updatedBy);
        }
    }
}
