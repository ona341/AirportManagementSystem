package Command;

import Entities.Flight;
import Singleton.AirportAccess;
import Singleton.FlightsAccess;
import Singleton.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Removes a flight from the system
 */
public class DeleteFlight implements Command{
    /**
     * A list of flights
     */
    private final ObservableList<Flight> selectedFlights;

    /**
     * Constructor
     * @param selectedFlights a list of selected flights
     */
    public DeleteFlight(ObservableList<Flight> selectedFlights) {
        this.selectedFlights = selectedFlights;
    }




    @Override
    public void execute() {
        //initializes a variable that is a list of flights with the list of flights from selectedFlights
        // and removes them from FlightsAccess
        ObservableList<Flight> localCopy = FXCollections.observableArrayList(selectedFlights);
        FlightsAccess.getInstance().removeAll(localCopy);
        FlightsAccess.getSearchInstance().removeAll(localCopy);



        String sql = "DELETE FROM flights WHERE flightNum = ?";

        try {
            //opens the database connection
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //for each flight in the localCopy list free the stall with the flights gate integer
            //and set the PreparedStatement parameter index to the flight number of the flight
            for (Flight flight : localCopy) {
                AirportAccess.getInstance().getGates().freeStall(flight.getGate());
                pstmt.setString(1, flight.getFlightNumber());
                pstmt.executeUpdate();
            }
            //Closes the prepared statement
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
