package Command;

import java.sql.*;

import Passenger.ParkingController;
import Entities.Passenger;
import Singleton.AirportAccess;
import Singleton.PassengerAccess;
import Singleton.dbConnection;
import javafx.fxml.FXML;

/**
 * Allows a passenger to request parking
 */
public class AddParking implements Command{
    /**
     * ParkingController instance object
     */
    private final ParkingController parkingController;

    /**
     * Constructor
     * @param parkingController ParkingController instance object
     */
    public AddParking(ParkingController parkingController) {
            this.parkingController = parkingController;
            }


    @FXML
    public void execute() {
        try {
            //gets the passenger with the idField from the Passenger access
            // and creates a new one if the passenger does not exist
            Passenger passenger = PassengerAccess.getInstance().stream().filter(p -> p.getId().equals(parkingController.idField.getText())).findAny().orElse(null);
            int parkingStall = AirportAccess.getInstance().getParkingStalls().firstAvailableStall();
            if (passenger == null) {
                passenger = new Passenger(parkingController.nameField.getText(),
                        parkingController.idField.getText(),
                        parkingController.emailField.getText(),
                        Date.valueOf(parkingController.CheckinDatePicker.getValue()),
                        parkingStall);
               new AddUser(passenger, new char[]{'1', '2', '3'}).execute();
            }
            else {
                if (passenger.getCheckInDate() == null) {
                    passenger.setCheckInDate(Date.valueOf(parkingController.CheckinDatePicker.getValue()));
                    passenger.setParkingStallLabel(parkingStall);

                    String sql = "UPDATE login SET checkIn = ?, parkingStall = ? WHERE id = ?";
                    Connection conn = dbConnection.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    pstmt.setDate(1,Date.valueOf(parkingController.CheckinDatePicker.getValue()));
                    pstmt.setInt(2,parkingStall);
                    pstmt.setString(3,parkingController.idField.getText());
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            //assigns the passenger and their parking stall to a stall in the airport
            AirportAccess.getInstance().getParkingStalls().assignEntityToStall(passenger, parkingStall);


            parkingController.clearReserveForm(null);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
