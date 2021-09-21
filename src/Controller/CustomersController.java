package Controller;

import Database.AppointmentsQuery;
import Database.CustomersQuery;
import Model.Customer;
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

/** Controller for Viewing and Manipulating Customers
 */
public class CustomersController implements Initializable {

    /** List of Customers
     */
    static ObservableList<Customer> customers;

    @FXML
    private TextField SearchTextField;

    @FXML
    private TableColumn<?, ?> AddressColumn;

    @FXML
    private Button HomeButton;

    @FXML
    private TableView<Customer> CustomersTable;

    @FXML
    private TableColumn<?, ?> NameColumn;

    @FXML
    private Button CreateCustomerButton;

    @FXML
    private TableColumn<?, ?> CountryColumn;

    @FXML
    private TableColumn<?, ?> PhoneColumn;

    @FXML
    private Button DeleteCustomerButton;

    @FXML
    private Label Header;

    @FXML
    private TableColumn<?, ?> PostalCodeColumn;

    @FXML
    private TableColumn<?, ?> DivisionColumn;

    @FXML
    private Button UpdateCustomerButton;

    @FXML
    private TableColumn<?, ?> CustomerIDColumn;

    /** Navigates to Create Customer View when clicked
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Create Customer View when Create Customer button is clicked
     */
    @FXML
    void CreateCustomer(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CreateCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create New Customer");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Navigates to Update Customer View when clicked
     * Customer must be selected prior to clicking Update Customer button or it will throw an error dialog
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Create Customer View when Create Customer button is clicked
     */
    @FXML
    void UpdateCustomer(ActionEvent event) {

        UpdateCustomerController.receiveSelectedCustomer(CustomersTable.getSelectionModel().getSelectedItem());

        if (CustomersTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/View/UpdateCustomer.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Existing Customer");
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
            alert.setContentText("You must select a customer to update.");
            alert.showAndWait();
        }
    }

    @FXML
    private Button SearchButton;

    /** Helper function to validate customer is allowed to be deleted
     * @param selectedCustomer Customer selected from Customer View for deletion
     * Catches Exception, throws alert, and prints stacktrace.
     * @return boolean Returns true if the customer is allowed to be deleted and false if not
     */
    private boolean checkAppointments(Customer selectedCustomer) {
        try {
            ObservableList appointments = AppointmentsQuery.getAppointmentsByCustomerID(selectedCustomer.getCustomerId());
            if (appointments != null && appointments.size() < 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /** Deletes customer when clicked.
     * Customer must be selected prior to clicking Delete Customer button or it will throw an error dialog.
     * Calls validation function to verify Customer is allowed to be deleted
     * Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent deletes customer when clicked
     */
    @FXML
    void DeleteCustomer(ActionEvent event) {

        Customer selectedCustomer = CustomersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select a customer to delete.");
            alert.showAndWait();
        } else if (CustomersTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected customer. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {

                    boolean valid = checkAppointments(selectedCustomer);
                    if (valid) {
                        boolean deleteSuccessful = CustomersQuery.deleteCustomer(CustomersTable.getSelectionModel().getSelectedItem().getCustomerId());

                        if (deleteSuccessful) {
                            customers = CustomersQuery.getCustomers();
                            CustomersTable.setItems(customers);
                            CustomersTable.refresh();
                        } else {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setContentText("Could not delete Customer.");
                            alert.showAndWait();
                        }
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("Cannot delete customer with existing appointments.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

    /** Updates Customer Table based on search text
     * @param event ActionEvent when search button is clicked
     */
    @FXML
    void SearchCustomers(ActionEvent event) {
        ObservableList<Customer> updateTable = lookupCustomer(SearchTextField.getText());
        CustomersTable.setItems(updateTable);
    }

    /** Helper function for Search Functionality
     * Gets Customer List based on Search input
     * @param input String value of search text
     * @return ObservableList List of Customers
     */
    private static ObservableList<Customer> lookupCustomer(String input) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        for (Customer customer: customers) {
            if (customer.getCustomerName().contains(input)) {
                customerList.add(customer);
            } else if (Integer.toString(customer.getCustomerId()).contains(input)) {
                customerList.add(customer);
            }
        }
        return customerList;
    }

    /** This method initializes the table in the Customers View.
     *  Catches Exception, throws alert, and prints stacktrace.
     *  @param location Location to resolve relative paths
     *  @param resources Resources to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            customers = CustomersQuery.getCustomers();
            CustomersTable.setItems(customers);
            CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            NameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            PostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            DivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
            CountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
