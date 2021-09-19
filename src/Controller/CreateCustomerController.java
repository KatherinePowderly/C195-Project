package Controller;

import Database.CountryQuery;
import Database.CustomersQuery;
import Database.DivisionQuery;
import Model.Country;
import Model.Division;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateCustomerController implements Initializable {

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
    private ComboBox<String> CountryCombo;

    @FXML
    private Label PostalCodeLabel;

    @FXML
    private Label AddressLabel;

    @FXML
    private Label CountryLabel;

    @FXML
    private ComboBox<String> DivisionCombo;

    @FXML
    private TextField PhoneTextField;

    @FXML
    private TextField IDTextField;

    @FXML
    void SelectCountry(ActionEvent event) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisions = DivisionQuery.getDivisionsByCountry(CountryCombo.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (Division division: divisions) {
                    divisionList.add(division.getDivision());
                }
            }
            DivisionCombo.setItems(divisionList);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void SelectDivision(ActionEvent event) {

    }

    @FXML
    void Save(ActionEvent event) throws SQLException {
        boolean valid = validateNotEmpty(
                NameTextField.getText(),
                AddressTextField.getText(),
                PostalCodeTextField.getText(),
                PhoneTextField.getText());

        if (valid) {
            try {
                boolean success = CustomersQuery.createCustomer(
                        NameTextField.getText(),
                        AddressTextField.getText(),
                        PostalCodeTextField.getText(),
                        PhoneTextField.getText(),
                        DivisionCombo.getValue());

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully created new customer");
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
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save new customer");
                    Optional<ButtonType> result = alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateNotEmpty(String name, String address, String postalCode, String phone){
        if (name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Name is required.");
            alert.showAndWait();
            return false;
        }

        if (address.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Address is required.");
            alert.showAndWait();
            return false;
        }

        if (postalCode.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Postal Code is required.");
            alert.showAndWait();
            return false;
        }

        if (phone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Phone Number is required.");
            alert.showAndWait();
            return false;
        }

        if (DivisionCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Division is required.");
            alert.showAndWait();
            return false;
        }

        if (CountryCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Country is required.");
            alert.showAndWait();
            return false;
        }

        return true;
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

    private void setDivisionCombo(){
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        try {
            ObservableList<Division> divisions = DivisionQuery.getDivisions();;
            if (divisions != null) {
                for (Division division: divisions) {
                    divisionList.add(division.getDivision());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DivisionCombo.setItems(divisionList);
    }

    private void setCountryCombo(){
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            ObservableList<Country> countries = CountryQuery.getCountries();;
            if (countries != null) {
                for (Country country: countries) {
                    countryList.add(country.getCountry());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CountryCombo.setItems(countryList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDivisionCombo();
        setCountryCombo();
    }
}
