package Controller;

import Database.CustomersQuery;
import Model.Customer;
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
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

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
    }

    @FXML
    private Button SearchButton;

    @FXML
    void DeleteCustomer(ActionEvent event) {

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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<Customer> customers = CustomersQuery.getCustomers();
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
