package Command;

import Entities.Flight;
import Singleton.flightsAccess;
import dbUtil.dbConnection;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFlight implements Command{

    private ObservableList<Flight> selectedFlights;

    public DeleteFlight(ObservableList<Flight> selectedFlights) {
        this.selectedFlights = selectedFlights;
    }




    @Override
    public void execute() {
        flightsAccess.getInstance().removeAll(selectedFlights);
        flightsAccess.getSearchInstance().removeAll(selectedFlights);

        String sql = "DELETE FROM flights WHERE flightNum = ?";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Flight flight : selectedFlights) {
                pstmt.setString(1, flight.getFlightNumber());
                pstmt.executeUpdate();
            }
            conn.close();
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
