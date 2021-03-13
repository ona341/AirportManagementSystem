package Command;

import Entities.Passenger;
import Passenger.ParkingController;
import Singleton.AirportAccess;
import Singleton.PassengerMapAccess;
import Singleton.dbConnection;

import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Allows a passenger to cancel their parking
 */
public class CancelParking implements Command{
    private final ParkingController parkingController;

    public CancelParking(ParkingController parkingController) {
        this.parkingController = parkingController;
    }


    @Override
    public void execute() {

        String sql = "DELETE FROM parking WHERE id = ? AND parkingStall = ?";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setString(1, parkingController.idFieldCancel.getText());
            pstmt.setString(2, parkingController.parkingFieldCancel.getText());


            pstmt.executeUpdate();

            Passenger p = PassengerMapAccess.getInstance().get(parkingController.idField.getText());

            if (p == null) {
                throw new InvalidKeyException("A passenger with that id number was not found.");
            }

            int stall = p.getParkingStallLabel();

            if (stall == -1) {
                throw new IllegalStateException("This passenger is not in a stall.");
            }

            p.setParkingStallLabel(-1);
            AirportAccess.getInstance().getParkingStalls().freeStall(stall);


            parkingController.clearCancelForm(null);

            pstmt.close();

        } catch (SQLException | InvalidKeyException throwables) {
            throwables.printStackTrace();
        }

    }
}
