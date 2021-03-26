package Command;

import AirportManager.AirportManagerController;
import Entities.Flight;
import Singleton.DBConnection;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * UpdateFlight class implements the Command interface to modify the flights in the system
 */
public class UpdateFlight implements Command {
    /**
     * The flight to update
     */
    private final Flight flight;

    public UpdateFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public void execute() {
        String sql = "UPDATE flights SET airline = ?, destination = ?, date = ?, time = ? WHERE flightNum = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(5, flight.getFlightNumber());
            pstmt.setString(1, flight.getAirline());
            pstmt.setString(2, flight.getDestination());
            pstmt.setDate(3, flight.getDate());
            pstmt.setTime(4, flight.getTime());

            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
