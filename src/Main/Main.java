package Main;

import Database.DBConnection;
import Database.DBQuery;
import com.mysql.cj.result.SqlDateValueFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

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

//        insert(connection);
//        delete(connection);
//        getAll(connection);
        getBySearch(connection);
//        updateFields(connection);

        launch(args);
        DBConnection.closeConnection();
    }

    private static void insert(Connection connection) throws SQLException{

        String input;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter a country: ");
        input = keyboard.nextLine();

        // escaping if user types in country with single quotes 'Country'
        if (input.contains("'")){
            input = input.replace("'", "\\'");
        }

        // Raw SQL insert statement - when using variables, we need to add commas etc between each variable for the sql query
        String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) VALUES (?, ?, ?, ?)";


        DBQuery.setPreparedStatement(connection, insertStatement);

        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        String createDate = "2021-09-04 00:00:00";
        String createdBy = "admin";
        String lastUpdateBy = "admin";

        // key-value mapping

        preparedStatement.setString(1, input);
        preparedStatement.setString(2, createDate);
        preparedStatement.setString(3, createdBy);
        preparedStatement.setString(4, lastUpdateBy);

        try {
            preparedStatement.execute();

            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void delete(Connection connection) throws SQLException{
        // Delete
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter a country to delete: ");
        String country = keyboard.nextLine();

        // Raw SQL insert statement - when using variables, we need to add commas etc between each variable for the sql query
        String updateStatement =  "DELETE FROM countries WHERE Country = ?";

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        // key-value mapping

        preparedStatement.setString(1, country);

        try {
            preparedStatement.execute();

            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void getAll(Connection connection) throws SQLException{
        String searchStatement = "SELECT * FROM countries";

        DBQuery.setPreparedStatement(connection, searchStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                String separate = " | ";

                int countryId = resultSet.getInt("Country_Id");

                String country = resultSet.getString("Country");

                LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();

                LocalTime createTime = resultSet.getTime("Create_Date").toLocalTime();

                String createdBy = resultSet.getString("Created_By");

                LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();

                String updatedBy = resultSet.getString("Last_Updated_By");

                System.out.println(countryId + separate + country + separate + createDate + separate + createTime + separate + createdBy + separate + lastUpdate + separate + updatedBy);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getBySearch(Connection connection) throws SQLException{
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter a country to search by: ");
        String country = keyboard.nextLine();

        String searchStatement = "SELECT * FROM countries WHERE Country = ?";


        DBQuery.setPreparedStatement(connection, searchStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, country);


        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                String separate = " | ";

                int countryId = resultSet.getInt("Country_Id");

                String countryName = resultSet.getString("Country");

                LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();

                LocalTime createTime = resultSet.getTime("Create_Date").toLocalTime();

                String createdBy = resultSet.getString("Created_By");

                LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();

                String updatedBy = resultSet.getString("Last_Updated_By");

                System.out.println(countryId + separate + countryName + separate + createDate + separate + createTime + separate + createdBy + separate + lastUpdate + separate + updatedBy);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateFields(Connection connection) throws SQLException {
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter a country to update: ");
        String country = keyboard.nextLine();

        System.out.print("Enter new country: ");
        String newCountry = keyboard.nextLine();

        System.out.print("Enter user: ");
        String user = keyboard.nextLine();

        // Raw SQL insert statement - when using variables, we need to add commas etc between each variable for the sql query
        String updateStatement =  "UPDATE countries SET Country = ?, Created_By = ? WHERE Country = ?";

        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        // key-value mapping

        preparedStatement.setString(1, newCountry);
        preparedStatement.setString(2, user);
        preparedStatement.setString(3, country);

        try {
            preparedStatement.execute();

            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
