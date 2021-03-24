package Command;

import AirportManager.AirportManagerController;
import Entities.Employee;
import Entities.Passenger;
import Singleton.*;
import javafx.fxml.FXML;
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
        String sql = "INSERT INTO login(id,password,role,name,email,checkIn,parkingStall) VALUES(?,?,?,?,?,?,?)";
        String sql1 = "INSERT INTO workSchedule(employeeId,sunday,monday,tuesday,wednesday,thursday,friday,saturday) VALUES(?,?,?,?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);

            pstmt.setString(1, user.getId());
            pstmt.setString(4, user.getName());
            pstmt.setString(5, user.getEmail());

            pstmt.setString(2, String.valueOf(password));

            if (user instanceof Employee) {
                pstmt.setString(3, ((Employee) user).getRole());
                EmployeeAccess.getInstance().add((Employee) user);
            }
            else {
                pstmt.setString(3, "Passenger");
                pstmt.setDate(6,user.getCheckInDate());
                pstmt.setInt(7,user.getParkingStallLabel());
                PassengerAccess.getInstance().add(user);
            }

            pstmt1.setString(1, user.getId());
            pstmt1.setString(2, "new");
            pstmt1.setString(3, "new");
            pstmt1.setString(4, "new");
            pstmt1.setString(5, "new");
            pstmt1.setString(6, "new");
            pstmt1.setString(7, "new");
            pstmt1.setString(8, "new");

            pstmt.executeUpdate();
            pstmt1.executeUpdate();

            pstmt.close();
            pstmt1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
