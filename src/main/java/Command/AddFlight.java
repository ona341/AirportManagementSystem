package Command;

import java.sql.*;

import AirportManager.AirportManagerController;
import Singleton.AirportAccess;
import Entities.Flight;
import Singleton.FlightsAccess;
import Singleton.dbConnection;
import javafx.fxml.FXML;

/**
 * Allows the user to add a flight to the system.
 */
public class AddFlight implements Command{
    private final AirportManagerController airportManagerController;

    public AddFlight(AirportManagerController airportManagerController) {
        this.airportManagerController = airportManagerController;
    }


    @FXML
    public void execute() {
        String sql = "INSERT INTO flights(flightNum,airline,destination,date,time,gate,capacity) VALUES(?,?,?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setString(1, airportManagerController.flightnum.getText());
            pstmt.setString(2, airportManagerController.airline.getText());
            pstmt.setString(3, airportManagerController.destination.getText());
            pstmt.setDate(4, Date.valueOf(airportManagerController.date.getValue()));
            pstmt.setTime(5, Time.valueOf(airportManagerController.time.getText()));

            int gate = AirportAccess.getInstance().getGates().firstAvailableStall();
            pstmt.setInt(6,gate);
            pstmt.setInt(7,airportManagerController.capacity.getValue());

            pstmt.executeUpdate();
            Flight flight = new Flight(airportManagerController.flightnum.getText(),
                            airportManagerController.airline.getText(),
                            airportManagerController.destination.getText(),
                            Date.valueOf(airportManagerController.date.getValue()),
                            Time.valueOf(airportManagerController.time.getText()),
                            gate,
                            airportManagerController.capacity.getValue());

            if (AirportAccess.getInstance().getGates().hasEntity(flight)) {
                //TODO handle error
            }


            FlightsAccess.getInstance().add(flight);
            AirportAccess.getInstance().getGates().assignEntityToStall(flight, gate);

            airportManagerController.clearForm(null);

            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
