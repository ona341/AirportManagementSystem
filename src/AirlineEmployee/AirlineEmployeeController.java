package AirlineEmployee;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Airline Employee, Currently only logs out
 */
public class AirlineEmployeeController {
    /**
     *Logs the user out of the Airline Employee and returns user to the login page
     * @param event the logout button clicked
     * @throws IOException throws this is the I/O is interrupted or fails
     */
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/loginapp/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show(); //Displays window
    }

}
