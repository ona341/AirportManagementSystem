package PopoutControllers;

import Entities.Employee;
import Entities.WorkSchedule;
import Singleton.DBConnection;
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

public class ModifyEmployeeInformation implements Initializable {

    /** Employee ID text label. */
    @FXML
    public Text employeeIdLabel;

    /** Employee name text label. */
    @FXML
    public Text employeeNameLabel;

    /** Employee password text label. */
    @FXML
    public Text employeePasswordLabel;

    /** Employee email text label. */
    @FXML
    public Text employeeEmailLabel;

    /** Employee role text label. */
    @FXML
    public Text employeeRoleLabel;

    /** Sunday work schedule text label. */
    @FXML
    public Text sun;

    /** Monday work schedule text label. */
    @FXML
    public Text mon;

    /** Tuesday work schedule text label. */
    @FXML
    public Text tues;

    /** Wednesday work schedule text label. */
    @FXML
    public Text wed;

    /** Thursday work schedule text label. */
    @FXML
    public Text thur;

    /** Friday work schedule text label. */
    @FXML
    public Text fri;

    /** Saturday work schedule text label. */
    @FXML
    public Text sat;

    /** Done button. */
    @FXML
    public Button done;

    /** Employee object to view and modify information. */
    private Employee employee;

    /** Employee password to view and modify. */
    private String password;

    /** Emploee work schedule as obeservable list for viewing. */
    private ObservableList<WorkSchedule> schedule;

    /** Database connection initialization. */
    private final Connection conn = DBConnection.getConnection();

    /**
     * Modify employee information.
     * @throws SQLException
     */
    public ModifyEmployeeInformation() throws SQLException { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * Initialize modify view window.
     * @param employee Employee to modify or view
     */
    public void initialize(Employee employee) {

        this.employee = employee;

        employeeIdLabel.setText(employee.getId());
        employeeNameLabel.setText(employee.getName());
        employeeEmailLabel.setText(employee.getEmail());
        employeeRoleLabel.setText(employee.getRole());

        String sql1 = "SELECT password FROM login WHERE id = ?";
        String sql2 = "SELECT * FROM workSchedule WHERE employeeId = ?";

        try {
            this.schedule = FXCollections.observableArrayList();

            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);

            pstmt1.setString(1, this.employee.getId());
            pstmt2.setString(1, this.employee.getId());

            ResultSet rs1 = pstmt1.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();

            employeePasswordLabel.setText("****" + rs1.getString(1).substring(4));
            this.password = rs1.getString(1);

            employee.getSchedule().setSunday(rs2.getString(2));
            employee.getSchedule().setMonday(rs2.getString(3));
            employee.getSchedule().setTuesday(rs2.getString(4));
            employee.getSchedule().setWednesday(rs2.getString(5));
            employee.getSchedule().setThursday(rs2.getString(6));
            employee.getSchedule().setFriday(rs2.getString(7));
            employee.getSchedule().setSaturday(rs2.getString(8));

            pstmt1.close();
            pstmt2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        sun.setText(employee.getSchedule().getSunday());
        mon.setText(employee.getSchedule().getMonday());
        tues.setText(employee.getSchedule().getTuesday());
        wed.setText(employee.getSchedule().getWednesday());
        thur.setText(employee.getSchedule().getThursday());
        fri.setText(employee.getSchedule().getFriday());
        sat.setText(employee.getSchedule().getSaturday());

        done.getScene().getWindow().sizeToScene();
    }

    /**
     * Exit modify view.
     */
    @FXML
    public void done(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * Opens a new window for editing text
     */
    public static String newDialog(String defaultValue, String fieldName) {

        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle("");
        dialog.setHeaderText("Editing " + fieldName);
        dialog.setContentText("");
        dialog.setGraphic(null);

        Optional<String> answer = dialog.showAndWait();
        return answer.orElse(defaultValue);
    }

    /**
     * Update database data of an employee.
     * @param employee Employee data to update
     */
    public void dbUpdate(Employee employee) {

        String sql1 = "UPDATE workSchedule SET sunday = ?, monday = ?, tuesday = ?, wednesday = ?, thursday = ?, friday = ?, saturday = ? WHERE employeeId = ?";
        String sql2 = "UPDATE login SET name = ?, email = ?, role = ? WHERE id = ?";

        try {
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);

            pstmt1.setString(1, employee.getSchedule().getSunday());
            pstmt1.setString(2, employee.getSchedule().getMonday());
            pstmt1.setString(3, employee.getSchedule().getTuesday());
            pstmt1.setString(4, employee.getSchedule().getWednesday());
            pstmt1.setString(5, employee.getSchedule().getThursday());
            pstmt1.setString(6, employee.getSchedule().getFriday());
            pstmt1.setString(7, employee.getSchedule().getSaturday());
            pstmt1.setString(8, employee.getId());

            pstmt2.setString(1, employee.getName());
            pstmt2.setString(2, employee.getEmail());
            pstmt2.setString(3, employee.getRole());
            pstmt2.setString(4, employee.getId());

            pstmt1.executeUpdate();
            pstmt2.executeUpdate();

            pstmt1.close();
            pstmt2.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Edit employee name.
     * @param event button click event
     */
    public void editName(ActionEvent event) {
        employee.setName(newDialog(employeeNameLabel.getText(), "Name"));
        dbUpdate(employee);
        employeeNameLabel.setText(employee.getName());
    }

    /**
     * Edit employee email.
     * @param event button click event
     */
    public void editPassword(ActionEvent event) {
        employee.setEmail(newDialog(employeeEmailLabel.getText(), "Email"));
        dbUpdate(employee);
        employeeEmailLabel.setText(employee.getEmail());
    }

    /**
     * Edit employee email.
     * @param event button click event
     */
    public void editEmail(ActionEvent event) {
        employee.setEmail(newDialog(employeeEmailLabel.getText(), "Email"));
        dbUpdate(employee);
        employeeEmailLabel.setText(employee.getEmail());
    }

    /**
     * Edit employee role.
     * @param event button click event
     */
    public void editRole(ActionEvent event) {
        employee.setRole(newDialog(employeeRoleLabel.getText(), "Role"));
        dbUpdate(employee);
        employeeRoleLabel.setText(employee.getRole());
    }

    /**
     * Edit employee Sunday work schedule.
     * @param event button click event
     */
    public void editSunday(ActionEvent event) {
        employee.getSchedule().setSunday(newDialog(sun.getText(), "Sunday"));
        dbUpdate(employee);
        sun.setText(employee.getSchedule().getSunday());
    }

    /**
     * Edit employee Monday work schedule.
     * @param event button click event
     */
    public void editMonday(ActionEvent event) {
        employee.getSchedule().setMonday(newDialog(mon.getText(), "Monday"));
        dbUpdate(employee);
        mon.setText(employee.getSchedule().getMonday());
    }

    /**
     * Edit employee Tuesday work schedule.
     * @param event button click event
     */
    public void editTuesday(ActionEvent event) {
        employee.getSchedule().setTuesday(newDialog(tues.getText(), "Tuesday"));
        dbUpdate(employee);
        tues.setText(employee.getSchedule().getTuesday());
    }

    /**
     * Edit employee Wednesday work schedule.
     * @param event button click event
     */
    public void editWednesday(ActionEvent event) {
        employee.getSchedule().setWednesday(newDialog(wed.getText(), "Wednesday"));
        dbUpdate(employee);
        wed.setText(employee.getSchedule().getWednesday());
    }

    /**
     * Edit employee Thursday work schedule.
     * @param event button click event
     */
    public void editThursday(ActionEvent event) {
        employee.getSchedule().setThursday(newDialog(thur.getText(), "Thursday"));
        dbUpdate(employee);
        thur.setText(employee.getSchedule().getThursday());
    }

    /**
     * Edit employee Friday work schedule.
     * @param event button click event
     */
    public void editFriday(ActionEvent event) {
        employee.getSchedule().setFriday(newDialog(fri.getText(), "Friday"));
        dbUpdate(employee);
        fri.setText(employee.getSchedule().getFriday());
    }

    /**
     * Edit employee Saturday work schedule.
     * @param event button click event
     */
    public void editSaturday(ActionEvent event) {
        employee.getSchedule().setSaturday(newDialog(sat.getText(), "Saturday"));
        dbUpdate(employee);
        sat.setText(employee.getSchedule().getSaturday());
    }
}
