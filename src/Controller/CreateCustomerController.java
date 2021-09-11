package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class CreateCustomerController {

    @FXML
    private Label NameLabel;

    @FXML
    private Label PhoneLabel;

    @FXML
    private Button HomeButton;

    @FXML
    private TextField PostalCodeTextField;

    @FXML
    private TextField AddressTextField;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField NameTextField;

    @FXML
    private Button SaveButton;

    @FXML
    private Label Header;

    @FXML
    private Label IDLabel;

    @FXML
    private Label DivisionLabel;

    @FXML
    private ComboBox<?> CountryCombo;

    @FXML
    private Label PostalCodeLabel;

    @FXML
    private Label AddressLabel;

    @FXML
    private Label CountryLabel;

    @FXML
    private ComboBox<?> DivisionCombo;

    @FXML
    private TextField PhoneTextField;

    @FXML
    private TextField IDTextField;

    @FXML
    void Save(ActionEvent event) {

    }

    @FXML
    void Cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Navigate back to Customers?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/View/Customers.fxml"));
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
