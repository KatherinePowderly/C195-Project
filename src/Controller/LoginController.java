package Controller;

import Database.AppointmentsQuery;
import Database.UsersQuery;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

interface LogActivity{
    public String getFileName();
}

/** Login Controller
 *  - Lambda Expression created to pass Login Activity filename to functions: createFile, loginSuccess, and loginFailure.
 *  This reduces code and add re-usability to applicationn
 */
public class LoginController implements Initializable {

    /** Lambda Expression
     */
    LogActivity logActivity = () -> {
        return "login_activity.txt";
    };

    private ResourceBundle resourceBundle;

    @FXML
    private Label Description;

    @FXML
    private Label Header;

    @FXML
    private Label Title;

    @FXML
    private Button CancelButton;

    @FXML
    private Label LocationField;

    @FXML
    private TextField UsernameTextField;

    @FXML
    private TextField PasswordTextField;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label TimeZoneField;

    @FXML
    private Label TimeZoneLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private Label PasswordLabel;

    @FXML
    private Button LoginButton;

    /** This method enables the user to login
     *  Calls helper functions to create a new log activity text file and validates that the login was successful
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent Logs into application when logout button is clicked
     */
    @FXML
    void Login(ActionEvent event) {
        validateUsernameNotEmpty(UsernameTextField.getText());
        validatePasswordNotEmpty(PasswordTextField.getText());

        createFile();

        try {
            boolean valid = UsersQuery.checkUsernamePassword(UsernameTextField.getText(), PasswordTextField.getText());
            if (valid) {

                loginSuccess();

                try {
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setTitle("Home");
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();



                    if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(resourceBundle.getString("errorDialog"));
                        alert.setContentText(resourceBundle.getString("loadScreenError"));
                        alert.showAndWait();
                    }
                }
            } else {

                loginFailure();

                if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("errorDialog"));
                    alert.setContentText(resourceBundle.getString("incorrectUsernamePassword"));
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Helper function to create login_activity.txt file if it doesn't already exist
     *  Catches Exception and prints stacktrace.
     *  Retrieves file name value from Lambda Expression
     */
    private void createFile(){
        try {
            File newfile = new File(logActivity.getFileName());
            if (newfile.createNewFile()) {
                System.out.println("File created:" + newfile.getName());
            } else {
                System.out.println("File already exists. Location: "+ newfile.getPath());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /** Validates Username Text Field id not empty
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param username Text Field Value for Username
     */
    private void validateUsernameNotEmpty(String username){
        if (username.isEmpty()) {
            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("errorDialog"));
                alert.setContentText(resourceBundle.getString("usernameRequired"));
                alert.showAndWait();
            }
        }
    }

    /** Validates Password Text Field id not empty
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param password Text Field Value for Username
     */
    private void validatePasswordNotEmpty(String password){
        if (password.isEmpty()) {
            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("errorDialog"));
                alert.setContentText(resourceBundle.getString("passwordRequired"));
                alert.showAndWait();
            }
        }
    }

    /** This method alerts the user if there is an upcoming appointment within 15 minutes or if there are no upcoming appointments
     *  Catches Exception, throws alert, and prints stacktrace.
     */
    private void alertAppointment(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTimePlus15 = localDateTime.plusMinutes(15);

        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();


        try {
            ObservableList<Appointment> appointments = AppointmentsQuery.getAppointments();

            if (appointments != null) {
                for (Appointment appointment: appointments) {
                    if (appointment.getStartTime().isAfter(localDateTime) && appointment.getStartTime().isBefore(localDateTimePlus15)) {
                        upcomingAppointments.add(appointment);

                        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(resourceBundle.getString("appointmentAlert"));
                            alert.setContentText(
                                    resourceBundle.getString("lessThanFifteenMin") +  // TODO: need to figure this out
                                    "\n" +
                                    resourceBundle.getString("appointmentId") +
                                    " " +
                                    + appointment.getAppointmentId() +
                                    "\n" +
                                    resourceBundle.getString("date") +
                                    " " +
                                    appointment.getStartDate() +
                                    "\n" +
                                    resourceBundle.getString("time") +
                                    " " +
                                    appointment.getStartTime().toLocalTime());
                            alert.setResizable(true);
                            alert.showAndWait();
                        }
                    }
                }

                if (upcomingAppointments.size() < 1) {
                    if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(resourceBundle.getString("appointmentAlert"));
                        alert.setContentText(resourceBundle.getString("noUpcomingAppointments"));
                        alert.setResizable(true);
                        alert.showAndWait();
                    }
                }
            } else {
                if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(resourceBundle.getString("appointmentAlert"));
                    alert.setContentText(resourceBundle.getString("noUpcomingAppointments"));
                    alert.setResizable(true);
                    alert.showAndWait();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Helper function to write user login success activity to the login_activity.txt file
     *  Catches IOException, throws alert, and prints stacktrace.
     *  Retrieves file name value from Lambda Expression
     */
    private void loginSuccess() {

        alertAppointment();

        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Successful Login: Username=" + UsernameTextField.getText() + " Password=" + PasswordTextField.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Helper function to write user login failure activity to the login_activity.txt file
     *  Catches IOException, throws alert, and prints stacktrace.
     *  Retrieves file name value from Lambda Expression
     */
    private void loginFailure() {
        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Failed Login: Username=" + UsernameTextField.getText() + " Password=" + PasswordTextField.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Prompts the user to close the application when clicked
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent prompts the user to close the application by dialog box
     */
    @FXML
    void Cancel(ActionEvent event) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language/language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            // CONFIRMATION is same in French and English
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("cancelError"));
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }

    /** This method initializes the Login View
     *  Gets region and converts to French if location in France
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Language/language", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){
            Header.setText(resourceBundle.getString("header"));
            Description.setText(resourceBundle.getString("description"));
            Title.setText(resourceBundle.getString("title"));
            UsernameLabel.setText(resourceBundle.getString("username"));
            PasswordLabel.setText(resourceBundle.getString("password"));
            LocationLabel.setText(resourceBundle.getString("location"));
            LocationField.setText(resourceBundle.getString("country"));
            TimeZoneLabel.setText(resourceBundle.getString("timezone"));
            TimeZoneField.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            LoginButton.setText(resourceBundle.getString("login"));
            CancelButton.setText(resourceBundle.getString("cancel"));
        }
    }
}
