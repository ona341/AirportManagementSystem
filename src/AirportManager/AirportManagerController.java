package AirportManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * AirportManager Controller
 */
public class AirportManagerController {
    /**
     * logs the user out of Airport Manager page and takes them back to the login page
     * @param event the logout button is clicked
     * @throws IOException thrown when the I/O is thrown or fails
     */
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/loginapp/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show();//displays window
    }

    /**
     * Opens the registration page to add a user
     * @param event an action event
     */
    public void toRegistration(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();      //creates a new stage for the registration
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 491));
            registerStage.setTitle("Registration");
            registerStage.setResizable(false);
            registerStage.show();//displays created stage

        } catch (
                Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
