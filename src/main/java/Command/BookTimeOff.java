package Command;

import Entities.Employee;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
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
    public void initialize(URL location, ResourceBundle resources) { }
//    @Override
    public void execute(Employee employee,String day) {
        switch (day){
            case "Sunday":
                employee.schedule().setSunday("OFF"); break;
            case "Monday":
                employee.schedule().setMonday("OFF"); break;
            case "Tuesday":
                employee.schedule().setTuesday("OFF"); break;
            case "Wednesday":
                employee.schedule().setWednesday("OFF"); break;
            case "Thursday":
                employee.schedule().setThursday("OFF"); break;
            case "Friday":
                employee.schedule().setFriday("OFF"); break;
            case "Saturday":
                employee.schedule().setSaturday("OFF"); break;

        }

//        if (day.equals("Saturday")){employee.schedule().setSaturday("OFF"); }
//        else if (day.equals("Sunday")){employee.schedule().setSunday("OFF"); }
//        else if (day.equals("Monday")){employee.schedule().setMonday("OFF"); }
//        else if (day.equals("Tuesday")){employee.schedule().setTuesday("OFF"); }
//        else if (day.equals("Wednesday")){employee.schedule().setWednesday("OFF"); }
//        else if (day.equals("Thursday")){employee.schedule().setThursday("OFF"); }
//        else if (day.equals("Friday")){employee.schedule().setFriday("OFF"); }


    }

    @Override
    public void execute() throws SQLException {
    }
}
