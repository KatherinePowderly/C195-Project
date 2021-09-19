package Database;

import Model.Appointment;
import Model.Contact;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentsQuery {

    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static ObservableList<Appointment> getAppointmentsMonth() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime lastMonth = todaysDate.minusDays(30);

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start > ?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setDate(1, java.sql.Date.valueOf(lastMonth.toLocalDate()));

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static ObservableList<Appointment> getAppointmentsWeek() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime lastWeek = todaysDate.minusDays(7);

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start > ?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setDate(1, java.sql.Date.valueOf(lastWeek.toLocalDate()));

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static boolean createAppointment(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID) throws SQLException {

        Contact contact = ContactsQuery.getContactId(contactName);

        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, contact.getContactId());
        preparedStatement.setInt(9, userID);

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

    public static boolean deleteAppointment(int appointmentId) throws SQLException {
        String insertStatement = "DELETE from appointments WHERE Appointment_Id=?";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setInt(1, appointmentId);

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

    public static boolean updateAppointment(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID, Integer appointmentID) throws SQLException {
        Contact contact = ContactsQuery.getContactId(contactName);

        String updateStatement = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, Contact_ID=?, User_ID=? WHERE Appointment_ID = ?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), updateStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, contact.getContactId());
        preparedStatement.setInt(9, userID);
        preparedStatement.setInt(10, appointmentID);

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

    public static ObservableList<Appointment> getAppointmentsByCustomerID(int CustomerID) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID=?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setInt(1, CustomerID);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

//    public static ObservableList<Appointment> checkAppointments(LocalDateTime localDateTime) throws SQLException {
//        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
//
//        LocalDateTime localDateTimePlus15 = localDateTime.plusMinutes(15);
//
//        String queryStatement = "SELECT * FROM appointments WHERE Start > ? AND Start < ?;";
//
//        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
//        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
//
//        preparedStatement.setDate(1, java.sql.Date.valueOf(localDateTime.toString()));
//        preparedStatement.setDate(2, java.sql.Date.valueOf(localDateTimePlus15.toString()));
//
//
//        try {
//            preparedStatement.execute();
//            ResultSet resultSet = preparedStatement.getResultSet();
//
//            // Forward scroll resultSet
//            while (resultSet.next()) {
//                Appointment newAppointment = new Appointment(
//                        resultSet.getInt("Appointment_ID"),
//                        resultSet.getString("Title"),
//                        resultSet.getString("Description"),
//                        resultSet.getString("Location"),
//                        resultSet.getString("Type"),
//                        resultSet.getDate("Start").toLocalDate(),
//                        resultSet.getTimestamp("Start").toLocalDateTime(),
//                        resultSet.getDate("End").toLocalDate(),
//                        resultSet.getTimestamp("End").toLocalDateTime(),
//                        resultSet.getInt("Customer_ID"),
//                        resultSet.getInt("User_ID"),
//                        resultSet.getInt("Contact_ID"),
//                        null
//                );
//
//                appointments.add(newAppointment);
//            }
//            return appointments;
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//            return null;
//        }
//    }

    public static Appointment getAppointmentByAppointmentID(int AppointmentID) throws SQLException {

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Appointment_ID=?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setInt(1, AppointmentID);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name")
                );

                return newAppointment;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}