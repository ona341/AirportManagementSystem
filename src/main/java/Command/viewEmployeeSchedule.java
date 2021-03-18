package Command;

import Singleton.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class viewEmployeeSchedule implements Initializable {

    @FXML
    public Button done1;
    @FXML
    public Text sun1;
    @FXML
    public Text mon1;
    @FXML
    public Text tues1;
    @FXML
    public Text wed1;
    @FXML
    public Text thur1;
    @FXML
    public Text fri1;
    @FXML
    public Text sat1;
    public TextField employeeId;
    Connection conn = dbConnection.getConnection();

    public viewEmployeeSchedule() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initialize() {

    }

    @FXML
    public void check(ActionEvent event) {
        try {
            String sql = "SELECT * FROM workSchedule WHERE employeeId ='" + employeeId.getText() + "'";
            Statement pstmt = conn.createStatement();
            ResultSet rs = pstmt.executeQuery(sql);

            if (rs.next()) {
                sun1.setText(rs.getString(2));
                mon1.setText(rs.getString(3));
                tues1.setText(rs.getString(4));
                wed1.setText(rs.getString(5));
                thur1.setText(rs.getString(6));
                fri1.setText(rs.getString(7));
                sat1.setText(rs.getString(8));
            } else {
                notifyError();
            }

            pstmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        done1.getScene().getWindow().sizeToScene();
        employeeId.clear();
    }

    /**
     * Checks the format of the inputted time for validity
     */
    private void notifyError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid employee ID");
        alert.setContentText("Please fill in the correct employee ID");
        alert.showAndWait();
    }

    @FXML
    public void done(ActionEvent event) throws SQLException {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
