package Database;

import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class contains SQL operations made on the Customers Collection.*/
public class CustomersQuery {

    /** This method gets all Customers and First-Level-Division Objects joined by the Division ID
     * @return ObservableList Returns list of Customers
     * @throws SQLException Catches SQLException, prints stacktrace, and error message.
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries AS co ON co.Country_ID=d.COUNTRY_ID;";

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
                        resultSet.getString("Division"),
                        resultSet.getString("Country"),
                        resultSet.getInt("Division_ID")
                        );

                customers.add(newCustomer);
            }
            return customers;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /** This method creates a new Customer
     * @param name String value of Customer Name
     * @param address String value of Customer Address
     * @param postalCode String value of Customer Postal Code
     * @param phone String value of Customer Phone Number
     * @param division String value of Division Name
     * @return Boolean Returns true if the customer was successfully created and false if the customer creation failed
     * @throws SQLException Catches SQLException, prints stacktrace, and error message.
     */
    public static boolean createCustomer(String name, String address, String postalCode, String phone, String division) throws SQLException {

        Division newDivision = DivisionQuery.getDivisionId(division);

        String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postalCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, newDivision.getDivisionId());

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /** This method updates an existing Customer by Customer ID
     * @param customerId Int value of Customer ID
     * @param name String value of Customer Name
     * @param address String value of Customer Address
     * @param postalCode String value of Customer Postal Code
     * @param phone String value of Customer Phone Number
     * @param division String value of Division Name
     * @return Boolean Returns true if the customer was successfully updated and false if the customer update failed
     * @throws SQLException Catches SQLException, prints stacktrace, and error message.
     */
    public static boolean updateCustomer(int customerId, String name, String address, String postalCode, String phone, String division) throws SQLException {
        Division newDivision = DivisionQuery.getDivisionId(division);

        String insertStatement = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postalCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, newDivision.getDivisionId());
        preparedStatement.setInt(6, customerId);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /** This method deletes an existing Customer
     * @param customerId Int of Customer ID
     * @return Boolean Returns true if the customer was successfully deleted and false if the customer deletion failed
     * @throws SQLException Catches SQLException, prints stacktrace, and error message.
     */
    public static boolean deleteCustomer(int customerId) throws SQLException {
        String insertStatement = "DELETE from customers WHERE Customer_Id=?";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setInt(1, customerId);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

}
