package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class Main extends Application {
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


    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
