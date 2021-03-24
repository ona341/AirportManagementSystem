package Command;

import Entities.Employee;
import Singleton.dbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

//package Command;
//
//import Entities.Employee;
//import Entities.WorkSchedule;
//import Singleton.dbConnection;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.text.Text;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//public class BookTimeOff implements Initializable {
//    private Employee employee;
//    @FXML
//    public Text employeeNameLabel;
//    private ObservableList<WorkSchedule> schedule;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//    }
//
//    public void initialize(Employee employee) {
//        this.employee = employee;
//        employeeNameLabel.setText(employee.getName());
////    @Override
////    public void execute() throws SQLException {
//        String sql = "SELECT * FROM workSchedule WHERE employeeId = ?";
//
//        try {
//
//            Connection conn = dbConnection.getConnection();
//            this.schedule = FXCollections.observableArrayList();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, this.employee.getId());
//            ResultSet rs = pstmt.executeQuery();
//
//
////            rs.updateString(2,"Off"));
////            this.employee.schedule().setMonday(rs.getString(3));
////            this.employee.schedule().setTuesday(rs.getString(4));
////            this.employee.schedule().setWednesday(rs.getString(5));
////            this.employee.schedule().setThursday(rs.getString(6));
////            this.employee.schedule().setFriday(rs.getString(7));
////            this.employee.schedule().setSaturday(rs.getString(8));
//
//            pstmt.close();
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
////        sunday.setText(employee.schedule().getSunday());
////        monday.setText(employee.schedule().getMonday());
////        tuesday.setText(employee.schedule().getTuesday());
////        wednesday.setText(employee.schedule().getWednesday());
////        thursday.setText(employee.schedule().getThursday());
////        friday.setText(employee.schedule().getFriday());
////        saturday.setText(employee.schedule().getSaturday());
////
////        close.getScene().getWindow().sizeToScene();
//    }}
public class BookTimeOff implements Command {

//    @Override
    String employeeId;
    Employee employee;
//    public void initialize(URL location, ResourceBundle resources) { }
    public void initialize(String employeeId) {this.employeeId = employeeId; }

    //    @Override
//    public void execute(Employee employee,String day) {
////        String sql = "SELECT sunday,monday,tuesday,wednesday,thursday,friday,saturday FROM workSchedule WHERE employeeId ='" + this.employeeId + "'";
//        try {
//            Connection conn = dbConnection.getConnection();
//            String sql = "SELECT * FROM workSchedule WHERE employeeId ='" + this.employeeId + "'";
//            Statement pstmt = conn.createStatement();
//            ResultSet rs = pstmt.executeQuery(sql);
//
////            if (rs.next()) {
//                rs.updateString(2,"OFF");
////                mon1.setText(rs.getString(3));
////                tues1.setText(rs.getString(4));
////                wed1.setText(rs.getString(5));
////                thur1.setText(rs.getString(6));
////                fri1.setText(rs.getString(7));
////                sat1.setText(rs.getString(8));
////            } else {
////                notifyError();
////            }
//
//            pstmt.close();
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        switch (day){
//            case "Sunday":
//                employee.schedule().setSunday("OFF"); break;
//            case "Monday":
//                employee.schedule().setMonday("OFF"); break;
//            case "Tuesday":
//                employee.schedule().setTuesday("OFF"); break;
//            case "Wednesday":
//                employee.schedule().setWednesday("OFF"); break;
//            case "Thursday":
//                employee.schedule().setThursday("OFF"); break;
//            case "Friday":
//                employee.schedule().setFriday("OFF"); break;
//            case "Saturday":
//                employee.schedule().setSaturday("OFF"); break;
//
//        }
//        }
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
        alert.setContentText("Success! You now have this day off");
        alert.showAndWait();
    }


    public void back(ActionEvent event) throws IOException {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }



    @Override
    public void execute() throws SQLException {
    }
}
