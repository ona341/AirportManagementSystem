package Command;

import Entities.Passenger;
import Passenger.ParkingController;
import Singleton.AirportAccess;
import Singleton.PassengerAccess;
import Singleton.DBConnection;

import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * Allows a passenger to cancel their parking
 */
public class CancelParking implements Command{
    /**
     * a ParkingController instance variable
     */
    private final ParkingController parkingController;

    /**
     * Constructor method
     * @param parkingController a ParkingController variable
     */
    public CancelParking(ParkingController parkingController) {
        this.parkingController = parkingController;
    }


    @Override
    public void execute() {

        String sql = "UPDATE login SET parkingStall = -1, checkIn = null WHERE id = ?";

        try {
            //opens the database connection
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //sets the string parameter indexes if the PreparedStatement to their respective fields
            pstmt.setString(1, parkingController.idFieldCancel.getText());


            pstmt.executeUpdate();

            Stream<Passenger> stream = PassengerAccess.getInstance().stream().filter(p -> p.getId().equals(parkingController.idFieldCancel.getText()));


            Passenger passenger = stream.findAny().orElse(null);

            if (passenger == null) {
                throw new InvalidKeyException("A passenger with that id number was not found.");
            }

            int stall = passenger.getParkingStallLabel();

            if (stall == -1) {
                throw new IllegalStateException("This passenger is not in a stall.");
            }

            passenger.setParkingStallLabel(-1);
            AirportAccess.getInstance().getParkingStalls().freeStall(stall);


            parkingController.clearCancelForm(null);
            //Closes the connection
            pstmt.close();

        } catch (SQLException | InvalidKeyException throwables) {
            throwables.printStackTrace();
        }

    }
}
