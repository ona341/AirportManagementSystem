package Command;

import Entities.Employee;
import Entities.WorkSchedule;
import Singleton.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class addEmployeeSchedule implements Initializable {

    @FXML
    public Button done;
    @FXML
    public Text employeeNameLabel;
    @FXML
    public Text sun;
    @FXML
    public Text mon;
    @FXML
    public Text tues;
    @FXML
    public Text wed;
    @FXML
    public Text thur;
    @FXML
    public Text fri;
    @FXML
    public Text sat;

    private Employee employee;

    private ObservableList<WorkSchedule> schedule;

    Connection conn = dbConnection.getConnection();

    public addEmployeeSchedule() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initialize(Employee employee) {
        this.employee = employee;
        employeeNameLabel.setText(employee.getName());

        String sql = "SELECT * FROM workSchedule WHERE employeeId = ?";

        try {

            this.schedule = FXCollections.observableArrayList();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.employee.getId());
            ResultSet rs = pstmt.executeQuery();


            this.employee.schedule().setSunday(rs.getString(2));
            this.employee.schedule().setMonday(rs.getString(3));
            this.employee.schedule().setTuesday(rs.getString(4));
            this.employee.schedule().setWednesday(rs.getString(5));
            this.employee.schedule().setThursday(rs.getString(6));
            this.employee.schedule().setFriday(rs.getString(7));
            this.employee.schedule().setSaturday(rs.getString(8));

            pstmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        sun.setText(employee.schedule().getSunday());
        mon.setText(employee.schedule().getMonday());
        tues.setText(employee.schedule().getTuesday());
        wed.setText(employee.schedule().getWednesday());
        thur.setText(employee.schedule().getThursday());
        fri.setText(employee.schedule().getFriday());
        sat.setText(employee.schedule().getSaturday());

        done.getScene().getWindow().sizeToScene();
    }

    @FXML
    public void done(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    public String newDialog(String defaultValue, String fieldName) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle("");
        dialog.setHeaderText("Editing " + fieldName);
        dialog.setContentText("");
        dialog.setGraphic(null);

        Optional<String> answer = dialog.showAndWait();
        return answer.orElse(defaultValue);

    }

    public void dbUpdate(Employee employee) {

        String sql = "UPDATE workSchedule SET sunday = ?, monday = ?, tuesday = ?, wednesday = ?, thursday = ?, friday = ?, saturday = ? WHERE employeeId = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, employee.schedule().getSunday());
            pstmt.setString(2, employee.schedule().getMonday());
            pstmt.setString(3, employee.schedule().getTuesday());
            pstmt.setString(4, employee.schedule().getWednesday());
            pstmt.setString(5, employee.schedule().getThursday());
            pstmt.setString(6, employee.schedule().getFriday());
            pstmt.setString(7, employee.schedule().getSaturday());

            pstmt.setString(8, employee.getId());

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void editSunday(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addTasks.fxml"));
//            Stage stage = new Stage();
//            Parent root = loader.load();
//            stage.setScene(new Scene(root));
//
//            loader.<addTasks>getController().initialize(employee);
//
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        employee.schedule().setSunday(newDialog(sun.getText(), "Sunday"));
        dbUpdate(employee);
        sun.setText(employee.schedule().getSunday());
    }

    public void editMonday(ActionEvent event) {
        employee.schedule().setMonday(newDialog(mon.getText(), "Monday"));
        dbUpdate(employee);
        mon.setText(employee.schedule().getMonday());
    }

    public void editTuesday(ActionEvent event) {
        employee.schedule().setTuesday(newDialog(tues.getText(), "Tuesday"));
        dbUpdate(employee);
        tues.setText(employee.schedule().getTuesday());
    }

    public void editWednesday(ActionEvent event) {
        employee.schedule().setWednesday(newDialog(wed.getText(), "Wednesday"));
        dbUpdate(employee);
        wed.setText(employee.schedule().getWednesday());
    }

    public void editThursday(ActionEvent event) {
        employee.schedule().setThursday(newDialog(thur.getText(), "Thursday"));
        dbUpdate(employee);
        thur.setText(employee.schedule().getThursday());
    }

    public void editFriday(ActionEvent event) {
        employee.schedule().setFriday(newDialog(fri.getText(), "Friday"));
        dbUpdate(employee);
        fri.setText(employee.schedule().getFriday());
    }

    public void editSaturday(ActionEvent event) {
        employee.schedule().setSaturday(newDialog(sat.getText(), "Saturday"));
        dbUpdate(employee);
        sat.setText(employee.schedule().getSaturday());
    }
}
