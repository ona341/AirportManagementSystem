package Command;

import Passenger.ParkingController;
import Singleton.AirportAccess;
import dbUtil.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implements the interface Command, Cancels a reserved parking space
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

        String sql = "DELETE FROM parking WHERE id = ? AND parkingStall = ?";

        try {
            //opens the database connection
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //sets the string parameter indexes if the PreoaredStatement to their respective fields
            pstmt.setString(1, parkingController.idFieldCancel.getText());
            pstmt.setString(2, parkingController.parkingFieldCancel.getText());
            //frees the stall in the Airport
            AirportAccess.getInstance().getParkingStalls().freeStall(Integer.parseInt(parkingController.parkingFieldCancel.getText()));
            pstmt.executeUpdate();


            parkingController.clearCancelForm(null);
            //Closes the connection
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
