package Controller;

import Database.CountryQuery;
import Database.CustomersQuery;
import Database.DivisionQuery;
import Model.Country;
import Model.Customer;
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

/** Controller to Update Customer
 */
public class UpdateCustomerController implements Initializable {

    /** Customer selected from Customer Controller View
     */
    private static Customer selectedCustomer;

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

    /** Populates Division Combo Box with List of Divisions based on selected Country
     * Catches SQLException, throws alert, and prints stacktrace.
     * @param event ActionEvent selects country
     */
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

    /** Updates Customer
     * Calls validation function
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent updates Customer if valid when Save button is clicked
     */
    @FXML
    void Save(ActionEvent event) {
        boolean valid = validateNotEmpty(
                NameTextField.getText(),
                AddressTextField.getText(),
                PostalCodeTextField.getText(),
                PhoneTextField.getText());

        if (valid) {
            try {
                boolean success = CustomersQuery.updateCustomer(
                        Integer.parseInt(IDTextField.getText()),
                        NameTextField.getText(),
                        AddressTextField.getText(),
                        PostalCodeTextField.getText(),
                        PhoneTextField.getText(),
                        DivisionCombo.getValue());

                if (success) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully updated customer");
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

    /** Helper function to validate Customer Fields are selected and not empty
     * Throws alert if fields are not selected or are empty
     * @param name String value of Customer Name
     * @param address String value of Customer Address
     * @param postalCode String value of Customer Postal Code
     * @param phone String value of Customer Phone Number
     * @return Boolean Returns true if valid and false if not valid
     */
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

    /** Method to receive selected customer from Customer View
     * @param customer Selected Customer
     */
    public static void receiveSelectedCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    /** Cancels Customer created and navigates back to Customer View
     * Throws alert if Load Screen Error
     * @param event ActionEvent Navigates to Customer View when cancel button is clicked
     */
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

    /** Populates Division Combo Box
     */
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


    /** Populates Country Combo Box
     */
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

    /** This method sets the fields with the selected customer fields and initializes the combo boxes in the Update Customer view.
     *  Catches Exception, throws alert, and prints stacktrace.
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate dropdown for division and country
        setDivisionCombo();
        setCountryCombo();

        // Populate text fields with existing data
        IDTextField.setText(Integer.toString(selectedCustomer.getCustomerId()));
        NameTextField.setText(selectedCustomer.getCustomerName());
        PostalCodeTextField.setText(selectedCustomer.getPostalCode());
        AddressTextField.setText(selectedCustomer.getAddress());
        PhoneTextField.setText(selectedCustomer.getPhoneNumber());
        CountryCombo.getSelectionModel().select(selectedCustomer.getCountry());
        DivisionCombo.getSelectionModel().select(selectedCustomer.getDivision());
    }
}
