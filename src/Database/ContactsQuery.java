package Database;

import Model.Contact;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsQuery {

    public static ObservableList getContacts() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM contacts AS c INNER JOIN appointments AS a ON c.Contact_ID = a.Contact_ID;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();;

            // Forward scroll resultSet
            while (resultSet.next()) {
                Contact newContact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );

                contacts.add(newContact);
            }
            return contacts;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

//    public static ObservableList<Contact> getContacts(Integer contactId) throws SQLException {
//        ObservableList<Contact> contacts = FXCollections.observableArrayList();
//
//        String queryStatement = "SELECT * FROM contacts WHERE Contact_ID=?";
//
//        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
//        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
//
//        preparedStatement.setInt(1, contactId);
//
//
//        try {
//            preparedStatement.execute();
//            ResultSet resultSet = preparedStatement.getResultSet();;
//
//            // Forward scroll resultSet
//            while (resultSet.next()) {
//                Contact newContact = new Contact(
//                        resultSet.getInt("Contact_ID"),
//                        resultSet.getString("Contact_Name"),
//                        resultSet.getString("Email")
//                );
//
//                contacts.add(newContact);
//            }
//
//            return contacts;
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//            return null;
//        }
//    }
}