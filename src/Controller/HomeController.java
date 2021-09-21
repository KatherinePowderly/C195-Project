package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Home Controller - Primary means of Navigation
 *  - Lambda Expression created to pass Login Activity filename to functions: createFile and logoutSuccess.
 *  This reduces code and add re-usability to application
 */
public class HomeController {

    /** Lambda Expression
     */
    LogActivity logActivity = () -> {
        return "login_activity.txt";
    };

    @FXML
    private Label Header;

    @FXML
    private Button AppointmentsButton;

    @FXML
    private Button CustomersButton;

    @FXML
    private Button ReportsButton;

    @FXML
    private Button LogoutButton;

    /** This method enables the user to logout
     *  Calls helper functions to create a new log activity text file and validates that the logout was successful
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent Logs out of application when logout button is clicked
     */
    @FXML
    void Logout(ActionEvent event) {
        createFile();
        logoutSuccess();

        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Helper function to write user logout activity to the login_activity.txt file
     *  Catches Exception, throws alert, and prints stacktrace.
     *  Retrieves value from Lambda Expression
     */
    private void logoutSuccess() {
        try {
            FileWriter fileWriter = new FileWriter(logActivity.getFileName(), true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("Successful Logout: " + simpleDateFormat.format(date) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Helper function to create login_activity.txt file if it doesn't already exist
     *  Catches Exception, throws alert, and prints stacktrace.
     *  Retrieves value from Lambda Expression
     */
    private void createFile(){
        try {
            File newfile = new File(logActivity.getFileName());
            if (newfile.createNewFile()) {
                System.out.println("File created:" + newfile.getName());
            } else {
                System.out.println("File already exists. Location: "+ newfile.getPath());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /** Navigates to Customer View
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Customers Screen when clicked
     */
    @FXML
    void Customers(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/Customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customers");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Navigates to Appointments View
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Appointments Screen when clicked
     */
    @FXML
    void Appointments(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointments");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** Navigates to Reports View
     *  Catches Exception, throws alert, and prints stacktrace.
     * @param event ActionEvent navigates to Reports Screen when clicked
     */
    @FXML
    void Reports(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Reports");
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
