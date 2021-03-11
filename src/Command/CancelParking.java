package Command;

import Passenger.ParkingController;
import Singleton.AirportAccess;
import dbUtil.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

            AirportAccess.getInstance().getParkingStalls().freeStall(Integer.parseInt(parkingController.parkingFieldCancel.getText()));
            pstmt.executeUpdate();


            parkingController.clearCancelForm(null);

            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
