package Database;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CustomersQuery {

    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM customers";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                Customer newCustomer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        null,
                        null,
                        resultSet.getInt("Division_ID")
                        );

                customers.add(newCustomer);
//                LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();
//
//                LocalTime createTime = resultSet.getTime("Create_Date").toLocalTime();
//
//                String createdBy = resultSet.getString("Created_By");
//
//                LocalDateTime lastUpdate = resultSet.getTimestamp("Last_Update").toLocalDateTime();
//
//                String updatedBy = resultSet.getString("Last_Updated_By");
//
//                int divisionId = resultSet.getInt("Division_ID");
            }
            return customers;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
