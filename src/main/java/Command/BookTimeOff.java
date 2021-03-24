package Command;

import Singleton.dbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.sql.*;

/**
 * BookTimeOff Class implements the command pattern to book a employees day off
 */
public class BookTimeOff implements Command {
    /**
     * The id of the employee
     */
    String employeeId;

    /**
     * Initializes the employee Id attribute of the BookTimeOff class
     * @param employeeId the employee's ID
     */
    public void initialize(String employeeId) {this.employeeId = employeeId; }

    /**
     * Books Sunday off
     * @param event the button labelled sunday is clicked
     */
    public void sunDay(ActionEvent event) {

            try {
                Connection conn = dbConnection.getConnection();
                String sql = "UPDATE workSchedule SET sunday = ? WHERE employeeId ='" + employeeId + "'";
                PreparedStatement prpst = conn.prepareStatement(sql);
                prpst.setString(1, "OFF");
                prpst.executeUpdate();
                booked();
                prpst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    /**
     * Books Monday off
      * @param event the button labelled Monday is clicked
     */
    public void monDay(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "UPDATE workSchedule SET monday = ? WHERE employeeId ='" + employeeId + "'";
            PreparedStatement prpst = conn.prepareStatement(sql);
            prpst.setString(1, "OFF");
            prpst.executeUpdate();
            booked();
            prpst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }    }
    /**
     * Books Tuesday off
     * @param event the button labelled Tuesday is clicked
     */
    public void tuesDay(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "UPDATE workSchedule SET tuesday = ? WHERE employeeId ='" + employeeId + "'";
            PreparedStatement prpst = conn.prepareStatement(sql);
            prpst.setString(1, "OFF");
            prpst.executeUpdate();
            booked();
            prpst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }    }
    /**
     * Books Wednesday off
     * @param event the button labelled Wednesday is clicked
     */
    public void wednesDay(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "UPDATE workSchedule SET wednesday = ? WHERE employeeId ='" + employeeId + "'";
            PreparedStatement prpst = conn.prepareStatement(sql);
            prpst.setString(1, "OFF");
            prpst.executeUpdate();
            booked();
            prpst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }       }
    /**
     * Books Thursday off
     * @param event the button labelled Thursday is clicked
     */
    public void thursDay(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "UPDATE workSchedule SET thursday = ? WHERE employeeId ='" + employeeId + "'";
            PreparedStatement prpst = conn.prepareStatement(sql);
            prpst.setString(1, "OFF");
            prpst.executeUpdate();
            booked();
            prpst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }      }
    /**
     * Books Friday off
     * @param event the button labelled Friday is clicked
     */
    public void friDay(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "UPDATE workSchedule SET friday = ? WHERE employeeId ='" + employeeId + "'";
            PreparedStatement prpst = conn.prepareStatement(sql);
            prpst.setString(1, "OFF");
            prpst.executeUpdate();
            booked();
            prpst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }     }

    /**
     * Books Saturday off
     * @param event the button labelled Saturday is clicked
     */
    public void saturDay(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "UPDATE workSchedule SET saturday = ? WHERE employeeId ='" + employeeId + "'";
            PreparedStatement prpst = conn.prepareStatement(sql);
            prpst.setString(1, "OFF");
            prpst.executeUpdate();
            booked();
            prpst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }      }

    /**
     * Displays Success message if the day off was successful
     */
    private void booked(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Success! You now have this day off.\nTo reverse this booking you will have to contact your manager");
        alert.showAndWait();
    }

    /**
     * Takes the employee back to the employee features page
     * @param event the back button is clicked
     */
    public void back(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @Override
    /**
     * Execute method for the command pattern
     */
    public void execute() throws SQLException {
    }
}
