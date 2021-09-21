/** @author Katherine Powderly
 * Simply: Scheduling Application Made Simple
 * C195 Advanced Java Concepts
 * JavaDoc Location ./C195-Project/javadoc
 * */

package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**This class creates an GUI-based scheduling application.*/
public class Main extends Application {

    /** This method starts the top level JavaFX Container.
     *  @param primaryStage Stage
     *  @throws IllegalStateException will throw exception if called more than once, caught and prints stacktrace
     *  @throws Exception Catches runtime exception, prints stacktrace, and provides Error Dialog.**/
    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            primaryStage.setTitle("Simply: Scheduling Application Made Simple");
            primaryStage.setScene(new Scene(root, 600, 600));
            primaryStage.show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** This is the main method. This is the first method that gets called when you run your Java Program
     *  @param args String[]**/
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
