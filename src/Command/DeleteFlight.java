package Command;

import Entities.Flight;
import Singleton.AirportAccess;
import Singleton.FlightsAccess;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFlight implements Command{

    private final ObservableList<Flight> selectedFlights;

    public DeleteFlight(ObservableList<Flight> selectedFlights) {
        this.selectedFlights = selectedFlights;
    }




    @Override
    public void execute() {
        ObservableList<Flight> localCopy = FXCollections.observableArrayList(selectedFlights);
        FlightsAccess.getInstance().removeAll(localCopy);
        FlightsAccess.getSearchInstance().removeAll(localCopy);



        String sql = "DELETE FROM flights WHERE flightNum = ?";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Flight flight : localCopy) {
                AirportAccess.getInstance().removeFlightFromGate(flight.getGate());
                pstmt.setString(1, flight.getFlightNumber());
                pstmt.executeUpdate();
            }
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
