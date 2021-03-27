package AirportEmployee;

import PopoutControllers.BookDayOff;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Airport Employee Controller class
 */
public class AirportEmployeeController  {
    /**
     * a string that is the employee's ID
     */
    String employeeId;
    /**
     * Takes the user back to the login page
     * @param event a button is clicked
     * @throws IOException if I/O operations fail or are interrupted
     */
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show(); //displays the window
    }

    /**
     * View employee's daily tasks
     * @param event an event performed by the user
     */
    public void toViewDailyTasks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewTasks.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * View employee's schedule
     * @param event the event that will trigger the method
     */
    public void toViewSchedule(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewEmployeeSchedule.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To Request time off for an employee
     * @param event an action performed by user
     */
    public void toRequestTimeOff(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookDayOff.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            loader.<BookDayOff>getController().initialize(this.employeeId);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Initializes the important values for an employee
     * @param employeeId the employees Id
     */
    public void initialize(String employeeId){
        this.employeeId = employeeId;
    }


}
