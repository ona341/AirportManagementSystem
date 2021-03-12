package Command;

import java.sql.*;

import Passenger.ParkingController;
import Entities.Passenger;
import Singleton.AirportAccess;
import Singleton.PassengerMapAccess;
import dbUtil.dbConnection;
import javafx.fxml.FXML;

import javax.management.openmbean.InvalidKeyException;

/**
 * Reserves a parking spot for a passenger
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
        String sql = "INSERT INTO parking(name,id,email,checkin,parkingStall) VALUES(?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection(); //opens the database connection
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //Sets the parameter indices of the PreparedStatement to their respective fields
            pstmt.setString(1, parkingController.nameField.getText());
            pstmt.setString(2, parkingController.idField.getText());
            pstmt.setString(3, parkingController.emailField.getText());
            pstmt.setDate(4, Date.valueOf(parkingController.CheckinDatePicker.getValue()));

            int parkingStall = AirportAccess.getInstance().getParkingStalls().firstAvailableStall();
            pstmt.setInt(5, parkingStall);
            //executes the sql statement
            pstmt.executeUpdate();
            //gets the passenger with the idField from the Passenger map access
            // and creates a new one if the passenger does not exist
            Passenger passenger = PassengerMapAccess.getInstance().get(parkingController.idField.getText());
            if (passenger == null) {
                passenger = new Passenger(parkingController.nameField.getText(),
                        parkingController.idField.getText(),
                        parkingController.emailField.getText(),
                        Date.valueOf(parkingController.CheckinDatePicker.getValue()),
                        parkingStall);
                PassengerMapAccess.getInstance().put(passenger.getIDNumber(),passenger);
            }
            //assigns the passenger and their parking stall to a stall in the airport
            AirportAccess.getInstance().getParkingStalls().assignEntityToStall(passenger, parkingStall);
            passenger.setStallLabel(parkingStall);
            //Clears the form
            parkingController.clearReserveForm(null);
            //Closes the connection
            pstmt.close();




        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
