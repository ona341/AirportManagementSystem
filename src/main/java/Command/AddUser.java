package Command;

import Entities.Employee;
import Entities.Passenger;
import Singleton.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.*;

/**
 * Allows the manager to add any type of user to the system.
 */
public class AddUser implements Command{
    private final Passenger user;
    private final char[] password;

    public AddUser(Passenger user, char[] password) {
        this.user = user;
        this.password = password;
    }

    @FXML
    public void execute() {
        String sqlA = "INSERT INTO login(id,password,role,name,email,checkIn,parkingStall) VALUES(?,?,?,?,?,?,?)";
        String sqlB = "INSERT INTO workSchedule(employeeId,sunday,monday,tuesday,wednesday,thursday,friday,saturday) VALUES(?,?,?,?,?,?,?,?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmtA = conn.prepareStatement(sqlA);
            PreparedStatement pstmtB = conn.prepareStatement(sqlB);

            pstmtA.setString(1, user.getId());
            pstmtA.setString(4, user.getName());
            pstmtA.setString(5, user.getEmail());

            pstmtA.setString(2, String.valueOf(password));

            if (user instanceof Employee) {
                pstmtA.setString(3, ((Employee) user).getRole());
                EmployeeAccess.getInstance().add((Employee) user);


            }
            else {
                pstmtA.setString(3, "Passenger");
                pstmtA.setDate(6,user.getCheckInDate());
                pstmtA.setInt(7,user.getParkingStallLabel());
                PassengerAccess.getInstance().add(user);
            }

            pstmtB.setString(1, user.getId());
            pstmtB.setString(2, "new");
            pstmtB.setString(3, "new");
            pstmtB.setString(4, "new");
            pstmtB.setString(5, "new");
            pstmtB.setString(6, "new");
            pstmtB.setString(7, "new");
            pstmtB.setString(8, "new");

            pstmtA.executeUpdate();
            pstmtB.executeUpdate();

            pstmtA.close();
            pstmtB.close();

        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                new Alert(Alert.AlertType.ERROR, "A passenger with this id is already in the system").showAndWait();
            }
            else
                e.printStackTrace();
        }
    }
}
