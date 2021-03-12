package Command;

import AirlineManager.AirlineManagerController;
import Entities.Flight;
import dbUtil.dbConnection;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * UpdateFlight class inplements the Command interface to modify the flights in the system
 */
public class UpdateFlight implements Command {
    /**
     * A list of the flights in the system
     */
    private static ObservableList<Flight> selectedFlights;
    /**
     * An AirlineManagerController instance variable
     */
    private AirlineManagerController airlineManagerController;

    /**
     * Constructor
     * @param selectedFlights a list of flights
     * @param airlineManagerController an AirlineManagerController variable
     */
    public UpdateFlight(ObservableList<Flight> selectedFlights, AirlineManagerController airlineManagerController) {

        this.selectedFlights = selectedFlights;
        this.airlineManagerController = airlineManagerController;
    }

    @Override
    public void execute() {

        String sql = "UPDATE flights SET airline = ?, destination = ?, date = ?, time = ? WHERE flightNum = ?";

        try {
            //opens database connection
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //for each flight in selectedFlights set the fifth parameter index of the preparedStatement
            // to the flight number of the flight set the other parameter indices to their respective field values
            //update the prepared statement
            for (Flight flight : selectedFlights) {

                pstmt.setString(5, flight.getFlightNumber());

                pstmt.setString(1, airlineManagerController.airline.getText());
                pstmt.setString(2, airlineManagerController.destination.getText());
                pstmt.setDate(3, Date.valueOf(airlineManagerController.date.getValue()));
                pstmt.setTime(4, Time.valueOf(airlineManagerController.time.getText()));

                pstmt.executeUpdate();

            }
            //Close the prepared statement
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Clear the filled form and load the flight's data
        this.airlineManagerController.clearForm(null);
        this.airlineManagerController.loadFLightData();

    }
}
