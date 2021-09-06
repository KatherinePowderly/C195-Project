package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomersController {

    @FXML
    private TableColumn<?, ?> AddressColumn;

    @FXML
    private Button HomeButton;

    @FXML
    private TableView<?> CustomersTable;

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

    }

    @FXML
    void UpdateCustomer(ActionEvent event) {

    }

    @FXML
    void DeleteCustomer(ActionEvent event) {

    }

    @FXML
    void Home(ActionEvent event) {

    }

}
