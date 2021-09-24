package Controller;

import Database.AppointmentsQuery;
import Database.ContactsQuery;
import Database.CustomersQuery;
import Database.UsersQuery;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** Controller to Update Appointment
 */
public class UpdateAppointmentController implements Initializable {

    private ZonedDateTime StartDateTimeConversion;
    private ZonedDateTime EndDateTimeConversion;

    /** Appointment selected from Appointment Controller View
     */
    private static Appointment selectedAppointment;

    @FXML
    private DatePicker StartDateDatePicker;

    @FXML
    private DatePicker EndDateDatePicker;

    @FXML
    private TextField LocationTextField;

    @FXML
    private Button HomeButton;

    @FXML
    private TextField DescriptionTextField;

    @FXML
    private ComboBox<String> ContactCombo;

    @FXML
    private Button CancelButton;

    @FXML
    private Label StartTimeLabel;

    @FXML
    private Button SaveButton;

    @FXML
    private TextField AppointmentIDTextField;

    @FXML
    private TextField CustomerIDTextField;

    @FXML
    private Label EndTimeLabel;

    @FXML
    private Label DescriptionLabel;

    @FXML
    private Label AppointmentIDLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private ComboBox<String> StartTimeCombo;

    @FXML
    private TextField TitleTextField;

    @FXML
    private Label TypeLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private ComboBox<String> EndTimeCombo;

    @FXML
    private Label Header;

    @FXML
    private Label EndDateLabel;

    @FXML
    private Label ContactLabel;

    @FXML
    private Label StartDateLabel;

    @FXML
    private Label CustomerIDLabel;

    @FXML
    private Label UserIDLabel;

    @FXML
    private ComboBox<Integer> CustomerIDCombo;

    @FXML
    private ComboBox<Integer> UserIDCombo;

    @FXML
    private ComboBox<String> TypeCombo;

    /** Converts time to EST.
     * @param time LocalDateTime time to convert
     * @return ZonedDateTime Returns time converted to EST
     */
    private ZonedDateTime convertToEST(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    private ZonedDateTime convertToTimeZone(LocalDateTime time, String zoneId) {
        return ZonedDateTime.of(time, ZoneId.of(zoneId));
    }

    @FXML
    void PickStartDate(ActionEvent event) {

    }

    @FXML
    void PickEndDate(ActionEvent event) {

    }

    /** Updates Appointment
     * Calls validation function
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent updates Appointment if valid when Save button is clicked
     */
    @FXML
    void Save(ActionEvent event) {
        boolean valid = validateAppointment(
                TitleTextField.getText(),
                DescriptionTextField.getText(),
                LocationTextField.getText(),
                AppointmentIDTextField.getText()
        );

        if (valid) {
            try {

                boolean success = AppointmentsQuery.updateAppointment(
                        ContactCombo.getSelectionModel().getSelectedItem(),
                        TitleTextField.getText(),
                        DescriptionTextField.getText(),
                        LocationTextField.getText(),
                        TypeCombo.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(StartDateDatePicker.getValue(), LocalTime.parse(StartTimeCombo.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(EndDateDatePicker.getValue(), LocalTime.parse(EndTimeCombo.getSelectionModel().getSelectedItem())),
                        CustomerIDCombo.getSelectionModel().getSelectedItem(),
                        UserIDCombo.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(AppointmentIDTextField.getText()));

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully updated appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
                        try {
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent scene = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setContentText("Load Screen Error.");
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update appointment");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** Helper function to validate Appointment Fields are selected and not empty
     * Throws alert if fields are not selected, if fields are empty, if there are overlapping appointments, and if appointment is outside of business hours
     * @param title String value of Appointment Title
     * @param description String value of Appointment Description
     * @param location String value of Appointment Location
     * @param appointmentId String value of Appointment ID
     * @return Boolean Returns true if valid and false if not valid
     */
    private boolean validateAppointment(String title, String description, String location, String appointmentId){
        if (ContactCombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Contact is required.");
            alert.showAndWait();
            return false;
        }

        if (title.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Title is required.");
            alert.showAndWait();
            return false;
        }

        if (description.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Description is required.");
            alert.showAndWait();
            return false;
        }

        if (location.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Location is required.");
            alert.showAndWait();
            return false;
        }

        if (TypeCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Type is required.");
            alert.showAndWait();
            return false;
        }

        if (appointmentId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointment ID is required.");
            alert.showAndWait();
            return false;
        }

        if (StartDateDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Start Date is required.");
            alert.showAndWait();
            return false;
        }

        if (StartTimeCombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Start Time is required.");
            alert.showAndWait();
            return false;
        }

        if (EndDateDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Date is required.");
            alert.showAndWait();
            return false;
        }

        if (EndDateDatePicker.getValue().isBefore(StartDateDatePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Date must be after Start Date.");
            alert.showAndWait();
            return false;
        }

        if (EndTimeCombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("End Time is required.");
            alert.showAndWait();
            return false;
        }

        if (CustomerIDCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Customer ID is required.");
            alert.showAndWait();
            return false;
        }

        if (UserIDCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("User ID is required.");
            alert.showAndWait();
            return false;
        }

        // additional date validation

        LocalTime startTime = LocalTime.parse(StartTimeCombo.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse(EndTimeCombo.getSelectionModel().getSelectedItem());

        if (endTime.isBefore(startTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointment start time must be before end time.");
            alert.showAndWait();
            return false;
        };

        LocalDate startDate = StartDateDatePicker.getValue();
        LocalDate endDate = EndDateDatePicker.getValue();

        if (!startDate.equals(endDate)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must start and end on the same date.");
            alert.showAndWait();
            return false;
        };

        // Check for overlapping appointments

        LocalDateTime selectedStart = startDate.atTime(startTime);
        LocalDateTime selectedEnd = endDate.atTime(endTime);

        LocalDateTime proposedAppointmentStart;
        LocalDateTime proposedAppointmentEnd;


        try {
            ObservableList<Appointment> appointments = AppointmentsQuery.getAppointmentsByCustomerID(CustomerIDCombo.getSelectionModel().getSelectedItem());
            for (Appointment appointment: appointments) {
                proposedAppointmentStart = appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
                proposedAppointmentEnd = appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());

                if (proposedAppointmentStart.isAfter(selectedStart) && proposedAppointmentStart.isBefore(selectedEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                } else if (proposedAppointmentEnd.isAfter(selectedStart) && proposedAppointmentEnd.isBefore(selectedEnd)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Appointments must not overlap with existing customer appointments.");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // check if between business hours
        StartDateTimeConversion = convertToEST(LocalDateTime.of(StartDateDatePicker.getValue(), LocalTime.parse(StartTimeCombo.getSelectionModel().getSelectedItem())));
        EndDateTimeConversion = convertToEST(LocalDateTime.of(EndDateDatePicker.getValue(), LocalTime.parse(EndTimeCombo.getSelectionModel().getSelectedItem())));

        if (StartDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (StartDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        if (EndDateTimeConversion.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Appointments must be within business hours 8AM - 10PM EST.");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    /** Method to receive selected appointment from Appointment View
     * @param appointment Selected Appointment
     */
    public static void receiveSelectedAppointment(Appointment appointment) {
        selectedAppointment = appointment;
    }

    @FXML
    void Cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Navigate back to Appointments?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();
            }
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

    /** Populates Start Time and End Time Combo Boxes in 15 minute increments
     */
    private void populateTimeComboBoxes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }

        StartTimeCombo.setItems(time);
        EndTimeCombo.setItems(time);
    }

    /** Populates Contact Combo Box with Contacts List
     */
    private void populateContactComboBox() {
        ObservableList<String> contactComboList = FXCollections.observableArrayList();

        try {
            ObservableList<Contact> contacts = ContactsQuery.getContacts();
            if (contacts != null){
                for (Contact contact: contacts) {
                    if (!contactComboList.contains(contact.getContactName())) {
                        contactComboList.add(contact.getContactName());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ContactCombo.setItems(contactComboList);
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

        CustomerIDCombo.setItems(customerIDComboList);
    }

    /** Populates User ID Combo Box with User ID List
     */
    private void populateUserIDComboBox() {
        ObservableList<Integer> userIDComboList = FXCollections.observableArrayList();

        try {
            ObservableList<User> users = UsersQuery.getUsers();
            if (users != null) {
                for (User user: users) {
                    userIDComboList.add(user.getUserId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserIDCombo.setItems(userIDComboList);
    }

    /** Populates Type Combo Box with hardcoded List
     */
    private void populateTypeComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        typeList.addAll("Planning Session", "De-Briefing", "Follow-up", "Pre-Briefing", "Open Session");


        TypeCombo.setItems(typeList);
    }

    /** This method sets the fields with the selected appointment fields and initializes the combo boxes in the Update Appointment view.
     *  Catches Exception, throws alert, and prints stacktrace.
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTimeComboBoxes();
        populateContactComboBox();
        populateCustomerIDComboBox();
        populateUserIDComboBox();
        populateTypeComboBox();

        try {
            Appointment appointment = AppointmentsQuery.getAppointmentByAppointmentID(selectedAppointment.getAppointmentId());

            ZonedDateTime zonedStartTime = convertToTimeZone(appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime()), String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            ZonedDateTime zonedEndTime = convertToTimeZone(appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime()), String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));

            if (appointment != null) {
                ContactCombo.getSelectionModel().select(appointment.getContactName());
                TitleTextField.setText(appointment.getTitle());
                DescriptionTextField.setText(appointment.getDescription());
                LocationTextField.setText(appointment.getLocation());
                TypeCombo.getSelectionModel().select(appointment.getType());
                UserIDCombo.getSelectionModel().select(Integer.valueOf(appointment.getUserId()));
                AppointmentIDTextField.setText(String.valueOf(appointment.getAppointmentId()));
                StartDateDatePicker.setValue(appointment.getStartDate());
                StartTimeCombo.getSelectionModel().select(String.valueOf(zonedStartTime.toLocalTime()));
                EndDateDatePicker.setValue(appointment.getEndDate());
                EndTimeCombo.getSelectionModel().select(String.valueOf(zonedEndTime.toLocalTime()));
                CustomerIDCombo.getSelectionModel().select(Integer.valueOf(appointment.getCustomerId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}