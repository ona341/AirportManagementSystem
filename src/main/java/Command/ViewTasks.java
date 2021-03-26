package Command;

import Entities.DailyTasks;
import Singleton.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewTasks implements Command {
    public TextField employeeId;

    public TableColumn<DailyTasks,String> fromCol;
    public TableColumn<DailyTasks,String> toCol;
    public TableColumn<DailyTasks,String> locationCol;
    public TableColumn<DailyTasks,String> taskCol;
    public TableView<DailyTasks> table;

    /**
     * Clears the form
     *
     * @param event an action performed by the user
     */
    @FXML
    public void clearForm(ActionEvent event) {
        employeeId.clear();
    }

    /**
     * Notify the user if the employee Id input is invalid
     */
    private void notifyError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid employee ID");
        alert.setContentText("Please fill in a valid employee ID");
        alert.showAndWait();
    }

    /**
     * This method allows the employee to view their daily tasks
     * @param actionEvent an event performed by the user
     */
    public void viewDailyTasks(ActionEvent actionEvent) {
        ObservableList<DailyTasks> dta2 = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT fromTime,toTime,location,task FROM dailyTasks WHERE employeeId ='" + employeeId.getText() + "'";
            Statement pstmt = conn.createStatement();
            ResultSet rs = pstmt.executeQuery(sql);

            boolean errorFound = true;
            while(rs.next()) {
                DailyTasks dt = new DailyTasks(employeeId.getText(), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                dta2.add(dt);
                errorFound = false;
            }
            if (errorFound) {
                notifyError();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        taskCol.setCellValueFactory(new PropertyValueFactory<>("tasks"));
        table.setItems(dta2);
        clearForm(null);
    }

    /**
     * Execute the command (nothing for now)
     */
    @Override
    public void execute(){
    }
}
