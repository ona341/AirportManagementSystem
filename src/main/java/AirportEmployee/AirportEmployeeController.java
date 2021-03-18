package AirportEmployee;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Airport Employee Controller currently only goes back to logout
 */
public class AirportEmployeeController {
    /**
     * Takes the user back to the login page
     * @param event a button is clicked
     * @throws IOException if I/O operations fail or are interrupted
     */
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show(); //displays the window
    }

    public void toViewDailyTasks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewEmployeeSchedule.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toViewSchedule(ActionEvent event) {

    }

    public void toRequestTimeOff(ActionEvent event) {

    }

}
