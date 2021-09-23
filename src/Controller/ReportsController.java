package Controller;

import Database.AppointmentsQuery;
import Database.ContactsQuery;
import Database.CustomersQuery;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

/** Generate Reports Controller
 */
public class ReportsController implements Initializable {

    @FXML
    private RadioButton TypeRadioButton;

    @FXML
    private Button HomeButton;

    @FXML
    private Label Header;

    @FXML
    private AnchorPane Records2Label;

    @FXML
    private Label Records3Label;

    @FXML
    private Button GenerateButton2;

    @FXML
    private Button GenerateButton1;

    @FXML
    private Label Records1Label;

    @FXML
    private Button GenerateButton;

    @FXML
    private RadioButton MonthRadioButton;

    @FXML
    private ComboBox<Integer> ContactCombo;

    @FXML
    private ComboBox<Integer> CustomerCombo;

    /** This method generates a report based on Type of Month
     *  The report generated totals the number of appointments per Type and per Month
     *  @param event Generates report based on Radio Button selected
     */
    @FXML
    void Generate1(ActionEvent event) {
        // Type
        ObservableList<String> planning = FXCollections.observableArrayList();
        ObservableList<String> debriefing = FXCollections.observableArrayList();
        ObservableList<String> followup = FXCollections.observableArrayList();
        ObservableList<String> prebriefing = FXCollections.observableArrayList();
        ObservableList<String> open = FXCollections.observableArrayList();

        // Month
        ObservableList<Integer> january = FXCollections.observableArrayList();
        ObservableList<Integer> february = FXCollections.observableArrayList();
        ObservableList<Integer> march = FXCollections.observableArrayList();
        ObservableList<Integer> april = FXCollections.observableArrayList();
        ObservableList<Integer> may = FXCollections.observableArrayList();
        ObservableList<Integer> june = FXCollections.observableArrayList();
        ObservableList<Integer> july = FXCollections.observableArrayList();
        ObservableList<Integer> august = FXCollections.observableArrayList();
        ObservableList<Integer> september = FXCollections.observableArrayList();
        ObservableList<Integer> october = FXCollections.observableArrayList();
        ObservableList<Integer> november = FXCollections.observableArrayList();
        ObservableList<Integer> december = FXCollections.observableArrayList();

        try {
            ObservableList<Appointment> appointments = AppointmentsQuery.getAppointments();

            if (appointments != null) {
                for (Appointment appointment : appointments) {
                    String type = appointment.getType();
                    LocalDate date = appointment.getStartDate();

                    if (type.equals("Planning Session")) {
                        planning.add(type);
                    }

                    if (type.equals("De-Briefing")) {
                        debriefing.add(type);
                    }

                    if (type.equals("Follow-up")) {
                        followup.add(type);
                    }

                    if (type.equals("Pre-Briefing")) {
                        prebriefing.add(type);
                    }

                    if (type.equals("Open Session")) {
                        open.add(type);
                    }

                    if (date.getMonth().equals(Month.of(1))) {
                        january.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(2))) {
                        february.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(3))) {
                        march.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(4))) {
                        april.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(5))) {
                        may.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(6))) {
                        june.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(7))) {
                        july.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(8))) {
                        august.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(9))) {
                        september.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(10))) {
                        october.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(11))) {
                        november.add(date.getMonthValue());
                    }

                    if (date.getMonth().equals(Month.of(12))) {
                        december.add(date.getMonthValue());
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (TypeRadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report: Customer Appointment Count by Type");
            alert.setContentText("Total number of Customer Appointments by Type: " +
                    "\nPlanning Session: " + planning.size() +
                    "\nDe-Briefing: " + debriefing.size() +
                    "\nFollow-up: " + followup.size() +
                    "\nPre-Briefing: " + prebriefing.size() +
                    "\nOpen Session: " + open.size());
            alert.setResizable(true);
            alert.showAndWait();
        }

        if (MonthRadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report: Customer Appointment Count by Month");
            alert.setContentText("Total number of Customer Appointments by Month: " +
                    "\nJanuary: " + january.size() +
                    "\nFebruary: " + february.size() +
                    "\nMarch: " + march.size() +
                    "\nApril: " + april.size() +
                    "\nMay: " + may.size() +
                    "\nJune: " + june.size() +
                    "\nJuly: " + july.size() +
                    "\nAugust: " + august.size() +
                    "\nSeptember: " + september.size() +
                    "\nOctober: " + october.size() +
                    "\nNovember: " + november.size() +
                    "\nDecember: " + december.size()
                    );
            alert.showAndWait();
        }
    }

    /** This method generates appointment reports by selected contact
     *  Schedules are displayed on a dialog box per appointment on file for selected Contact
     *  @param event Generates report based on Combo Box field selected
     */
    @FXML
    void Generate2(ActionEvent event) {
        Integer contactID = ContactCombo.getSelectionModel().getSelectedItem();
        try {
            ObservableList<Appointment> appointments = AppointmentsQuery.getAppointmentsByContactID(contactID);


            if (appointments != null) {
                for (Appointment appointment: appointments) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Report: Customer Appointment by Contact ID");

                    alert.setContentText("Appointments by Contact ID #" + contactID + ": " +
                            "\nAppointment ID: " + appointment.getAppointmentId() +
                            "\nTitle: " + appointment.getTitle() +
                            "\nType: " + appointment.getType() +
                            "\nDescription: " + appointment.getDescription() +
                            "\nStart Date: " + appointment.getStartDate() +
                            "\nStart Time: " + appointment.getStartTime() +
                            "\nEnd Date: " + appointment.getEndDate() +
                            "\nEnd Time: " + appointment.getEndTime() +
                            "\nCustomer ID: " + appointment.getCustomerId() +
                            "\nUser ID: " + appointment.getUserId()
                    );

                    alert.setResizable(true);
                    alert.showAndWait();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method generates the total of appointments per customer ID
     *  @param event Generates report based on Combo Box field selected
     */
    @FXML
    void Generate3(ActionEvent event) {
        Integer customerID = CustomerCombo.getSelectionModel().getSelectedItem();
        try {
            ObservableList<Appointment> appointments = AppointmentsQuery.getAppointmentsByCustomerID(customerID);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report: Customer Appointment Count by Customer ID");
            alert.setContentText("Total number of Customer Appointments by Customer ID #" + customerID + ": " + appointments.size());
            alert.setResizable(true);
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Navigates to Home View
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Home Screen when clicked
     */
    @FXML
    void Home(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Home");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Populates Contact ID Combo Box with Contact ID List
     */
    private void populateContactIDComboBox() {
        ObservableList<Integer> contactIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Contact> contacts = ContactsQuery.getContacts();
            if (contacts != null) {
                for (Contact contact: contacts) {
                    if (!contactIDComboList.contains(contact.getContactId())) {
                        contactIDComboList.add(contact.getContactId());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ContactCombo.setItems(contactIDComboList);
    }

    /** Populates Customer ID Combo Box with Customer ID List
     */
    private void populateCustomerIDComboBox() {
        ObservableList<Integer> customerIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Customer> customers = CustomersQuery.getCustomers();
            if (customers != null) {
                for (Customer customer: customers) {
                    customerIDComboList.add(customer.getCustomerId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CustomerCombo.setItems(customerIDComboList);
    }

    /** This method initializes the combo boxes in the Reports view.
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateCustomerIDComboBox();
        populateContactIDComboBox();
    }
}
