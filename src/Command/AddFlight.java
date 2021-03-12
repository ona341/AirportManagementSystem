package Command;

import java.sql.*;

import AirlineManager.AirlineManagerController;
import Singleton.AirportAccess;
import Entities.Flight;
import Singleton.FlightsAccess;
import dbUtil.dbConnection;
import javafx.fxml.FXML;

/**
 * Allows the user to add a flight to the system.
 */
public class AddFlight implements Command{
    /**
     * Airline manager Controller instance
     */
    private final AirlineManagerController airlineManagerController;

    /**
     * Constructor
     * @param airlineManagerController an AirlineManagerController instance
     */
    public AddFlight(AirlineManagerController airlineManagerController) {
        this.airlineManagerController = airlineManagerController;
    }


    @FXML
    /**
     * Executes the command
     */
    public void execute() {
        String sql = "INSERT INTO flights(flightNum,airline,destination,date,time,gate) VALUES(?,?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection(); //open a connection to the database
            PreparedStatement pstmt = conn.prepareStatement(sql);

            //sets the string parameter indexes of the PreparedStatement to their respective fields
            pstmt.setString(1,airlineManagerController.flightnum.getText());
            pstmt.setString(2,airlineManagerController.airline.getText());
            pstmt.setString(3,airlineManagerController.destination.getText());
            pstmt.setDate(4, Date.valueOf(airlineManagerController.date.getValue()));
            pstmt.setTime(5, Time.valueOf(airlineManagerController.time.getText()));

            int gate = AirportAccess.getInstance().getGates().firstAvailableStall();
            pstmt.setInt(6,gate);
            //executes the statement in the PreparedStatement
            pstmt.executeUpdate();
            //Creates a new flight from the valid fields in the airlineManagerController instance
            Flight flight = new Flight(airlineManagerController.flightnum.getText(),
                            airlineManagerController.airline.getText(),
                            airlineManagerController.destination.getText(),
                            Date.valueOf(airlineManagerController.date.getValue()),
                            Time.valueOf(airlineManagerController.time.getText()),
                            gate);
            //Adds the created flight to the flights in the system and assigns it to a stall in the airport
            FlightsAccess.getInstance().add(flight);
            AirportAccess.getInstance().getGates().assignEntityToStall(flight, gate);
            //Clears form filled after the flight was added
            airlineManagerController.clearForm(null);

            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
