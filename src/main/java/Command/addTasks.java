package Command;

import Entities.Employee;
import Entities.dailyTasks;
import Singleton.dailyTasksAccess;
import Singleton.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void initialize(Employee employee) {
        this.employee = employee;
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("tasks"));

        table.setItems(dailyTasksAccess.getInstance());
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

    public void buttonEvent(ActionEvent actionEvent) {
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
    }
}
