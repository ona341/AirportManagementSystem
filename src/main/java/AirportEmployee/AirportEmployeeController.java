package AirportEmployee;

import Command.BookTimeOff;
import Command.Command;
import Entities.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Airport Employee Controller currently only goes back to logout
 */
public class AirportEmployeeController  {
    @FXML
    Button Sunday;
    @FXML
    Button Monday;
    @FXML
    Button Tuesday;
    @FXML
    Button Wednesday;
    @FXML
    Button Thursday;
    @FXML
    Button Friday;
    @FXML
    Button Saturday;
    @FXML
    Button Back;
    Employee employee;
    String employeeId;
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
//        @Override
//    public void initialize(URL location, ResourceBundle resources) {
////        this.employee.
//    }

    /**
     * View employee's daily tasks
     * @param event an event performed by the user
     */
    public void toViewDailyTasks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewTasks.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewEmployeeSchedule.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toRequestTimeOff(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookDayOff.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            loader.<BookTimeOff>getController().initialize(this.employeeId);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public void initialize(String employeeId){
        this.employeeId = employeeId;
    }


}
