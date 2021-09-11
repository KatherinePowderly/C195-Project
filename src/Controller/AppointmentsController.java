package Controller;

import Database.AppointmentsQuery;
import Model.Appointment;
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

public class AppointmentsController implements Initializable {

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
    private TableView<Appointment> AppointmentsTable;

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
    private Button SearchButton;

    @FXML
    private TableColumn<?, ?> TypeColumn;

    @FXML
    private TableColumn<?, ?> CustomerIDColumn;

    @FXML
    void CreateAppointment(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CreateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create New Appointment");
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
    void UpdateAppointment(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/UpdateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Update Existing Appointment");
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
    void DeleteAppointment(ActionEvent event) {

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
    void SearchAppointments(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<Appointment> appointments = AppointmentsQuery.getAppointments();
            AppointmentsTable.setItems(appointments);
            AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            LocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
//            StartDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startDate" + "startTime"));
//            EndDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endDate" + "endTime"));
            CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
