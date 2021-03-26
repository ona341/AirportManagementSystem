package Command;

import Entities.DailyTasks;
import Entities.Employee;
import Singleton.DailyTasksAccess;
import Singleton.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTasks implements Command {

    public TextField taskLocation;
    @FXML
    public TextField fromTime;
    @FXML
    public TextField toTime;
    @FXML
    public TextArea taskToDo;
    public TableColumn<DailyTasks,String> fromCol;
    public TableColumn<DailyTasks,String> toCol;
    public TableColumn<DailyTasks,String> locationCol;
    public TableColumn<DailyTasks,String> taskCol;
    public TableView<DailyTasks> table;


    private Employee employee;
    public ObservableList<DailyTasks> dta2;

    /**
     * Constructor for AddTasks
     */
    public AddTasks () {
    }

    /**
     * initialize the given employee
     * @param employee the employee to add the tasks to
     */
    public void initialize(Employee employee) {
        this.employee = employee;
    }

    /**
     * Execute (displays the existing tasks of the employee)
     */
    @Override
    public void execute() {
        dta2 = FXCollections.observableArrayList();
        for (DailyTasks dts : DailyTasksAccess.getInstance()) {
            if (dts.getEmployeeId().compareTo(this.employee.getId()) == 0) {
                dta2.add(dts);
            }
        }
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("tasks"));

        table.setItems(dta2);
    }

    /**
     * Clears the form
     * @param event an action performed by the user
     */
    @FXML
    public void clearForm(ActionEvent event) {
        fromTime.clear();
        toTime.clear();
        taskLocation.clear();
        taskToDo.clear();
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
     * Notify the user that their input was invalid
     */
    private void notifyError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid time");
        alert.setContentText("Please fill in a valid time");
        alert.showAndWait();
    }

    /**
     * Add the input task and its time period to the employee list of tasks
     * @param actionEvent an action performed by the user
     */
    public void buttonEvent(ActionEvent actionEvent) {
        if (checkInvalidFields(null)) {
            String sql = "INSERT INTO dailyTasks(employeeId,fromTime,toTime,location,task) VALUES(?,?,?,?,?)";

            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement rs = conn.prepareStatement(sql);
                rs.setString(1, employee.getId());
                rs.setString(2, fromTime.getText());
                rs.setString(3, toTime.getText());
                rs.setString(4, taskLocation.getText());
                rs.setString(5, taskToDo.getText());
                rs.execute();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            DailyTasks dt = new DailyTasks(employee.getId(), fromTime.getText(), toTime.getText(), taskLocation.getText(), taskToDo.getText());
            clearForm(null);
            DailyTasksAccess.getInstance().add(dt);
            dta2.add(dt);
        }
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("tasks"));

        table.setItems(dta2);
    }
}
