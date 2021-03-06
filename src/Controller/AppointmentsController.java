package Controller;

import Database.AppointmentsQuery;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for Viewing and Manipulating Appointments
 */
public class AppointmentsController implements Initializable {

    /** List of Appointments
     */
    static ObservableList<Appointment> appointments;

    @FXML
    private TextField SearchTextField;

    @FXML
    private TableColumn<?, ?> ContactColumn;

    @FXML
    private Button HomeButton;

    @FXML
    private TableColumn<?, ?> DescriptionColumn;

    @FXML
    private Button DeleteAppointmentButton;

    @FXML
    private TableColumn<?, ?> LocationColumn;

    @FXML
    private TableView<Appointment> AppointmentsTable;

    @FXML
    private TableColumn<?, ?> TitleColumn;

    @FXML
    private RadioButton MonthRadioButton;

    @FXML
    private TableColumn<?, ?> AppointmentIDColumn;

    @FXML
    private Button CreateAppointmentButton;

    @FXML
    private RadioButton AllRadioButton;

    @FXML
    private RadioButton WeekRadioButton;

    @FXML
    private TableColumn<?, ?> StartDateTimeColumn;

    @FXML
    private TableColumn<?, ?> EndDateTimeColumn;

    @FXML
    private Label Header;

    @FXML
    private Button UpdateCustomerButton;

    @FXML
    private Button SearchButton;

    @FXML
    private TableColumn<?, ?> TypeColumn;

    @FXML
    private TableColumn<?, ?> CustomerIDColumn;

    @FXML
    private TableColumn<?, ?> UserIDColumn;

    @FXML
    private ToggleGroup ToggleView;

    /** Toggle View - All, Week, or Month.
     * Sets Appointment Table based on Radio Button selected by User.
     * Catches SQL exceptions and prints stacktrace.
     * @param event ActionEvent updates Appointment Table when Radio Button is selected
     */
    @FXML
    void ViewToggle(ActionEvent event) {

        if (AllRadioButton.isSelected()) {
            try {
                appointments = AppointmentsQuery.getAppointments();
                AppointmentsTable.setItems(appointments);
                AppointmentsTable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ToggleView.getSelectedToggle().equals(MonthRadioButton)) {
            try {
                appointments = AppointmentsQuery.getAppointmentsMonth();
                AppointmentsTable.setItems(appointments);
                AppointmentsTable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ToggleView.getSelectedToggle().equals(WeekRadioButton)) {
            try {
                appointments = AppointmentsQuery.getAppointmentsWeek();
                AppointmentsTable.setItems(appointments);
                AppointmentsTable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /** Navigates to Create Appointment View when clicked
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent creates Appointment when Create Appointment button is clicked
     */
    @FXML
    void CreateAppointment(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CreateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create New Appointment");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Navigates to Update Appointment View when clicked.
     * Appointment must be selected prior to clicking Update Appointment button or it will throw an error dialog.
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent updates Appointment when Update Appointment button is clicked
     */
    @FXML
    void UpdateAppointment(ActionEvent event) {
        UpdateAppointmentController.receiveSelectedAppointment(AppointmentsTable.getSelectionModel().getSelectedItem());

        if (AppointmentsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/View/UpdateAppointment.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Existing Appointment");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Load Screen Error.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select an appointment to update.");
            alert.showAndWait();
        }
    }

    /** Deletes appointment when clicked.
     * Appointment must be selected prior to clicking Delete Appointment button or it will throw an error dialog.
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent deletes appointment when clicked
     */
    @FXML
    void DeleteAppointment(ActionEvent event) {
        Appointment selectedAppointment = AppointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select an appointment to delete.");
            alert.showAndWait();
        } else if (AppointmentsTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected appointment. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean deleteSuccessful = AppointmentsQuery.deleteAppointment(AppointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId());

                    if (deleteSuccessful) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Delete");
                        alert.setContentText("Successfully deleted Appointment ID: " + selectedAppointment.getAppointmentId() + " Type: " + selectedAppointment.getType());
                        alert.showAndWait();

                        appointments = AppointmentsQuery.getAppointments();
                        AppointmentsTable.setItems(appointments);
                        AppointmentsTable.refresh();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("Could not delete appointment.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /** Navigates to Home Screen
     * Catches Exception, throws alert, and prints stacktrace.
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


    /** Updates Appointment Table based on search text
     * @param event ActionEvent when search button is clicked
     */
    @FXML
    void SearchAppointments(ActionEvent event) {
        ObservableList<Appointment> updateTable = lookupAppointment(SearchTextField.getText());
        AppointmentsTable.setItems(updateTable);
    }

    /** Helper function for Search Functionality
     * Gets Appointment List based on Search input
     * @param input String value of search text
     * @return ObservableList List of Appointments
     */
    private static ObservableList<Appointment> lookupAppointment(String input) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        for (Appointment appointment: appointments) {
            if (appointment.getTitle().contains(input)) {
                appointmentList.add(appointment);
            } else if (Integer.toString(appointment.getAppointmentId()).contains(input)) {
                appointmentList.add(appointment);
            }
        }
        return appointmentList;
    }

    /** This method initializes the table in the Appointments View.
     *  Catches Exception, throws alert, and prints stacktrace.
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AllRadioButton.setToggleGroup(ToggleView);
        WeekRadioButton.setToggleGroup(ToggleView);
        MonthRadioButton.setToggleGroup(ToggleView);
        try {
            appointments = AppointmentsQuery.getAppointments();

            AppointmentsTable.setItems(appointments);
            AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            LocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            ContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            StartDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            EndDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            UserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
