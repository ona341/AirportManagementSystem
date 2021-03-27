package PopoutControllers;

import Singleton.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewEmployeeSchedule{

    @FXML
    public Button done;
    @FXML
    public Text sunday;
    @FXML
    public Text monday;
    @FXML
    public Text tuesday;
    @FXML
    public Text wednesday;
    @FXML
    public Text thursday;
    @FXML
    public Text friday;
    @FXML
    public Text saturday;

    public TextField employeeId;

    /**
     * constructor for viewEmployeeSchedule
     */
    public ViewEmployeeSchedule() {
    }

    /**
     * This method displays the employee schedule from their input employee id
     * @param event an event performed by the user
     */
    @FXML
    public void checkButton(ActionEvent event) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM workSchedule WHERE employeeId ='" + employeeId.getText() + "'";
            Statement pstmt = conn.createStatement();
            ResultSet rs = pstmt.executeQuery(sql);

            if (rs.next()) {
                sunday.setText(rs.getString(2));
                monday.setText(rs.getString(3));
                tuesday.setText(rs.getString(4));
                wednesday.setText(rs.getString(5));
                thursday.setText(rs.getString(6));
                friday.setText(rs.getString(7));
                saturday.setText(rs.getString(8));
            } else {
                notifyError();
            }

            pstmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        done.getScene().getWindow().sizeToScene();
        employeeId.clear();
    }

    /**
     * notify the user that their input employee ID is invalid
     */
    private void notifyError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid employee ID");
        alert.setContentText("Please fill in the correct employee ID");
        alert.showAndWait();
    }

    /**
     * Allows the user to close the window when they are done with viewing schedule
     * @param event an event performed by the user
     */
    @FXML
    public void done(ActionEvent event){
        ((Button) event.getSource()).getScene().getWindow().hide();
    }


}
