package Controller;

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

public class CustomersController implements Initializable {

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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

    @FXML
    void SearchCustomers(ActionEvent event) {
        ObservableList<Customer> updateTable = lookupCustomer(SearchTextField.getText());
        CustomersTable.setItems(updateTable);
    }

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
            DivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
            CountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
