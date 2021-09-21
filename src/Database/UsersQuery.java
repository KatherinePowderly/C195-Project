package Database;

import Model.Contact;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class contains SQL operations made on the Users Collection.*/
public class UsersQuery {

    /** This method checks if the username and password are valid
     * @param username Username
     * @param password Password
     * @return Boolean Returns true if valid and false if not valid
     * @throws SQLException Catches SQLException and prints stacktrace.
     */
    public static boolean checkUsernamePassword(String username, String password) throws SQLException {
        String searchStatement = "SELECT * FROM users WHERE User_Name=? AND Password=?";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            return (resultSet.next());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /** This method gets all Users from the User Collection
     * @return ObservableList Returns list of Users
     * @throws SQLException Catches SQLException and prints stacktrace.
     */
    public static ObservableList<User> getUsers() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM users;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            ;

            while (resultSet.next()) {
                User newUser = new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password")
                );

                users.add(newUser);
            }
            return users;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}