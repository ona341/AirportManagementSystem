package Command;

import Entities.Employee;
import Entities.dailyTasks;
import Singleton.dailyTasksAccess;
import Singleton.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addTasks implements Initializable {

    public TextField fromTime;
    @FXML
    public TextField toTime;
    @FXML
    public TextArea taskToDo;
    public TableColumn<dailyTasks,String> fromCol;
    public TableColumn<dailyTasks,String> toCol;
    public TableColumn<dailyTasks,String> taskCol;
    public TableView<dailyTasks> table;


    private Employee employee;
    public ObservableList<dailyTasks> dta2;

    public void initialize(Employee employee) {
        this.employee = employee;
        dta2 = FXCollections.observableArrayList();
        for (dailyTasks dts : dailyTasksAccess.getInstance()) {
            if (dts.getEmployeeId().compareTo(this.employee.getId()) == 0) {
                dta2.add(dts);
            }
        }
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("tasks"));

        table.setItems(dta2);
    }

    /**
     * Clears the form
     *
     * @param event an action performed by the user
     */
    @FXML
    public void clearForm(ActionEvent event) {
        fromTime.clear();
        toTime.clear();
        taskToDo.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Checks the format of the inputted time for validity
     * @param event an action event
     */
    @FXML
    private boolean checkInvalidFields(MouseEvent event) {
        boolean isValid = true;
        if (fromTime.getText().matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            fromTime.appendText(":00");
        }
        if (toTime.getText().matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            toTime.appendText(":00");
        }
        if (!fromTime.getText().matches("^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$")) {
            isValid = false;
            notifyError();
            fromTime.clear();
        }
        if (!toTime.getText().matches("^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$")) {
            isValid = false;
            notifyError();
            toTime.clear();
        }
        return isValid;
    }

    /**
     * Checks the format of the inputted time for validity
     */
    private void notifyError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid time");
        alert.setContentText("Please fill in a valid time");
        alert.showAndWait();
    }

    public void buttonEvent(ActionEvent actionEvent) {
        if (checkInvalidFields(null)) {
            String sql = "INSERT INTO dailyTasks(employeeId,fromTime,toTime,task) VALUES(?,?,?,?)";

            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement rs = conn.prepareStatement(sql);
                rs.setString(1, employee.getId());
                rs.setString(2, fromTime.getText());
                rs.setString(3, toTime.getText());
                rs.setString(4, taskToDo.getText());
                rs.execute();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            dailyTasks dt = new dailyTasks(employee.getId(), fromTime.getText(), toTime.getText(), taskToDo.getText());
            clearForm(null);
            dailyTasksAccess.getInstance().add(dt);
            dta2.add(dt);
        }
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("tasks"));

        table.setItems(dta2);
    }
}
