package Controller;

import Database.AppointmentsQuery;
import Database.UsersQuery;
import Model.Appointment;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    ObservableList<User> users;

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
    private Label LocationLabel;

    @FXML
    private Label PasswordLabel;

    @FXML
    private Button LoginButton;

    @FXML
    void Login(ActionEvent event) {
        validateNotEmpty("Username", UsernameTextField.getText());
        validateNotEmpty("Password", PasswordTextField.getText());

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
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Load Screen Error.");
                    alert.showAndWait();
                }
            } else {

                loginFailure();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Username and password is incorrect.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createFile(){
        try {
            File newfile = new File("login_activity.txt");
            if (newfile.createNewFile()) {
                System.out.println("File created:" + newfile.getName());
            } else {
                System.out.println("File already exists. Location: "+ newfile.getPath());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void validateNotEmpty(String label, String textField){
        if (textField.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(label + " is required");
            alert.showAndWait();
        }
    }

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

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Appointment Alert");
                        alert.setContentText("Appointment starts in less than 15 minutes \nAppointment ID: "
                                + appointment.getAppointmentId() +
                                "\nDate: " +
                                appointment.getStartDate() +
                                "\nTime: " + appointment.getStartTime().toLocalTime());
                        alert.setResizable(true);
                        alert.showAndWait();
                    }
                }

                if (upcomingAppointments.size() < 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setContentText("No upcoming appointments within the next 15 minutes");
                    alert.setResizable(true);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Alert");
                alert.setContentText("No upcoming appointments within the next 15 minutes");
                alert.setResizable(true);
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loginSuccess() {

        alertAppointment();

        try {
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Successful Login: Username=" + UsernameTextField.getText() + " Password=" + PasswordTextField.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginFailure() {
        try {
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Failed Login: Username=" + UsernameTextField.getText() + " Password=" + PasswordTextField.getText() + " Timestamp: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language/language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            // CONFIRMATION is same in French and English
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("error"));
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language/language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){
            Header.setText(resourceBundle.getString("header"));
            Description.setText(resourceBundle.getString("description"));
            Title.setText(resourceBundle.getString("title"));
            UsernameLabel.setText(resourceBundle.getString("username"));
            PasswordLabel.setText(resourceBundle.getString("password"));
            LocationLabel.setText(resourceBundle.getString("location"));
            LocationField.setText(resourceBundle.getString("country"));
            LoginButton.setText(resourceBundle.getString("login"));
            CancelButton.setText(resourceBundle.getString("cancel"));
        }
    }
}
