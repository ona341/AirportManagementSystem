package Command;

import AirportManager.AirportManagerController;
import Entities.Flight;
import Singleton.dbConnection;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * UpdateFlight class implements the Command interface to modify the flights in the system
 */
public class UpdateFlight implements Command {
    /**
     * A list of the flights in the system
     */
    private static ObservableList<Flight> selectedFlights;

    private AirportManagerController airportManagerController;

    public UpdateFlight(ObservableList<Flight> selectedFlights, AirportManagerController airportManagerController) {

        this.selectedFlights = selectedFlights;
        this.airportManagerController = airportManagerController;
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

                pstmt.setString(1, airportManagerController.airline.getText());
                pstmt.setString(2, airportManagerController.destination.getText());
                pstmt.setDate(3, Date.valueOf(airportManagerController.date.getValue()));
                pstmt.setTime(4, Time.valueOf(airportManagerController.time.getText()));

                pstmt.executeUpdate();

            }
            //Close the prepared statement
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.airportManagerController.clearForm(null);
        this.airportManagerController.loadFLightData();
    }
}
