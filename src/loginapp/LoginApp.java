package loginapp;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApp extends Application {

    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Airport Management System");
        stage.setResizable(false);
        stage.show();



    }
    public static void main(String[] args){
        launch(args);
    }
}
