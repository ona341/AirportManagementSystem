package Command;

import Singleton.dbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.*;


public class BookTimeOff implements Command {
    String employeeId;
    public void initialize(String employeeId) {this.employeeId = employeeId; }

        public void sunDay(ActionEvent event) throws SQLException {

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
    private void notifyError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid employee ID");
        alert.setContentText("Please fill in the correct employee ID");
        alert.showAndWait();
    }
    private void booked(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Success! You now have this day off.\nTo reverse this booking you will have to contact your manager");
        alert.showAndWait();
    }

    public void back(ActionEvent event) throws IOException {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void execute() throws SQLException {
    }
}
