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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

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
    private TextField TypeTextField;

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
    void PickStartDate(ActionEvent event) {

    }

    @FXML
    void PickEndDate(ActionEvent event) {

    }

    @FXML
    void Save(ActionEvent event) {
        boolean valid = validateNotEmpty(
                TitleTextField.getText(),
                DescriptionTextField.getText(),
                LocationTextField.getText(),
                TypeTextField.getText(),
                AppointmentIDTextField.getText()
        );

        if (valid) {
            try {

                boolean success = AppointmentsQuery.updateAppointment(
                        ContactCombo.getSelectionModel().getSelectedItem(),
                        TitleTextField.getText(),
                        DescriptionTextField.getText(),
                        LocationTextField.getText(),
                        TypeTextField.getText(),
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

    private boolean validateNotEmpty(String title, String description, String location, String type, String appointmentId){
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

        if (type.isEmpty()) {
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

        return true;
    }

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

    private void populateTimeComboBoxes() {
        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(22, 0);

        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }

        StartTimeCombo.setItems(time);
        EndTimeCombo.setItems(time);
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTimeComboBoxes();
        populateContactComboBox();
        populateCustomerIDComboBox();
        populateUserIDComboBox();

        try {
            Appointment appointment = AppointmentsQuery.getAppointmentByAppointmentID(selectedAppointment.getAppointmentId());

            ContactCombo.getSelectionModel().select(appointment.getContactName());
            TitleTextField.setText(appointment.getTitle());
            DescriptionTextField.setText(appointment.getDescription());
            LocationTextField.setText(appointment.getLocation());
            TypeTextField.setText(appointment.getType());
            UserIDCombo.getSelectionModel().select(Integer.valueOf(appointment.getUserId()));
            AppointmentIDTextField.setText(String.valueOf(appointment.getAppointmentId()));
            StartDateDatePicker.setValue(appointment.getStartDate());
            StartTimeCombo.getSelectionModel().select(String.valueOf(appointment.getStartTime().toLocalTime()));
            EndDateDatePicker.setValue(appointment.getEndDate());
            EndTimeCombo.getSelectionModel().select(String.valueOf(appointment.getEndTime().toLocalTime()));
            CustomerIDCombo.getSelectionModel().select(Integer.valueOf(appointment.getCustomerId()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}