package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class CreateAppointmentController {

    @FXML
    private TextField LocationTextField;

    @FXML
    private Button HomeButton;

    @FXML
    private TextField DescriptionTextField;

    @FXML
    private ComboBox<?> ContactCombo;

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
    private ComboBox<?> StartTimeCombo;

    @FXML
    private TextField TitleTextField;

    @FXML
    private TextField TypeTextField;

    @FXML
    private TextField EndDateTextField;

    @FXML
    private Label TypeLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private ComboBox<?> EndTimeCombo;

    @FXML
    private Label Header;

    @FXML
    private Label EndDateLabel;

    @FXML
    private Label ContactLabel;

    @FXML
    private TextField StartDateTextField;

    @FXML
    private Label StartDateLabel;

    @FXML
    private Label CustomerIDLabel;

    @FXML
    void Save(ActionEvent event) {

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
}

