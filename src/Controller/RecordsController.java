package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RecordsController {

    @FXML
    private RadioButton TypeRadioButton;

    @FXML
    private Button HomeButton;

    @FXML
    private ComboBox<?> Records3Combo;

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
    private ComboBox<?> Records2Combo;

    @FXML
    private Label Records1Label;

    @FXML
    private Button GenerateButton;

    @FXML
    private RadioButton MonthRadioButton;

    @FXML
    void Generate1(ActionEvent event) {

    }

    @FXML
    void Generate2(ActionEvent event) {

    }

    @FXML
    void Generate3(ActionEvent event) {

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
