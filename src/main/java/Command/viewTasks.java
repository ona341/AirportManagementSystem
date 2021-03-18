package Command;

import Entities.dailyTasks;
import Singleton.dailyTasksAccess;
import Singleton.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class viewTasks implements Initializable {
    public TextField employeeId;

    public TableColumn<dailyTasks,String> fromCol;
    public TableColumn<dailyTasks,String> toCol;
    public TableColumn<dailyTasks,String> taskCol;
    public TableView<dailyTasks> table;


    public void initialize() {

    }

    /**
     * Clears the form
     *
     * @param event an action performed by the user
     */
    @FXML
    public void clearForm(ActionEvent event) {
        employeeId.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("tasks"));
        table.setItems(dailyTasksAccess.getInstance());
    }

    /**
     * Checks the format of the inputted time for validity
     */
    private void notifyError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid employee ID");
        alert.setContentText("Please fill in a valid employee ID");
        alert.showAndWait();
    }

    public void viewDailyTasks(ActionEvent actionEvent) {
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT fromTime,toTime,task FROM dailyTasks WHERE employeeId ='" + employeeId.getText() + "'";
            Statement pstmt = conn.createStatement();
            ResultSet rs = pstmt.executeQuery(sql);

            while(rs.next()) {
                dailyTasks dt = new dailyTasks(employeeId.getText(), rs.getString(1), rs.getString(2), rs.getString(3));
                dailyTasksAccess.getInstance().add(dt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearForm(null);
    }
}
