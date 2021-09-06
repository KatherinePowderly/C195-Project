package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppointmentsController {

    @FXML
    private TableColumn<?, ?> ContactColumn;

    @FXML
    private Button HomeButton;

    @FXML
    private TableColumn<?, ?> DescriptionColumn;

    @FXML
    private Button DeleteAppointmentButton;

    @FXML
    private TableColumn<?, ?> LocationColumn;

    @FXML
    private TableView<?> AppointmentsTable;

    @FXML
    private TableColumn<?, ?> TitleColumn;

    @FXML
    private RadioButton MonthRadioButton;

    @FXML
    private TableColumn<?, ?> AppointmentIDColumn;

    @FXML
    private Button CreateAppointmentButton;

    @FXML
    private RadioButton WeekRadioButton;

    @FXML
    private TableColumn<?, ?> StartDateTimeColumn;

    @FXML
    private TableColumn<?, ?> EndDateTimeColumn;

    @FXML
    private Label Header;

    @FXML
    private Button UpdateCustomerButton;

    @FXML
    private TableColumn<?, ?> TypeColumn;

    @FXML
    private TableColumn<?, ?> CustomerIDColumn;

    @FXML
    void CreateAppointment(ActionEvent event) {

    }

    @FXML
    void UpdateAppointment(ActionEvent event) {

    }

    @FXML
    void DeleteAppointment(ActionEvent event) {

    }

    @FXML
    void Home(ActionEvent event) {

    }

}
