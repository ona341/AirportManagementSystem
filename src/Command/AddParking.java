package Command;

import java.sql.*;

import Passenger.ParkingController;
import Entities.Flight;
import Entities.Passenger;
import Singleton.AirportAccess;
import Singleton.PassengerMapAccess;
import Singleton.flightsAccess;
import dbUtil.dbConnection;
import javafx.fxml.FXML;


public class AddParking implements Command{
    private final ParkingController parkingController;

    public AddParking(ParkingController parkingController) {
            this.parkingController = parkingController;
            }


    @FXML
    public void execute() {
        String sql = "INSERT INTO parking(name,id,email,checkin,parkingStall) VALUES(?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setString(1, parkingController.nameField.getText());
            pstmt.setString(2, parkingController.idField.getText());
            pstmt.setString(3, parkingController.emailField.getText());
            pstmt.setDate(4, Date.valueOf(parkingController.CheckinDatePicker.getValue()));

            int parkingStall = AirportAccess.getInstance().firstAvailableStall();
            pstmt.setInt(5, parkingStall);

            pstmt.executeUpdate();

            Passenger passenger = new Passenger(parkingController.nameField.getText(),
                    parkingController.idField.getText(),
                    parkingController.emailField.getText(),
                    Date.valueOf(parkingController.CheckinDatePicker.getValue()),
                    parkingStall);
            PassengerMapAccess.getInstance().add(passenger);
            AirportAccess.getInstance().assignPassengerToStall(passenger, parkingStall);


            parkingController.clearReserveForm(null);


            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
