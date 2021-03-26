package Command;

import Entities.Flight;
import Entities.Passenger;
import Singleton.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BreakAssociation implements Command{
    Flight flight;
    Passenger passenger;

    public BreakAssociation(Flight flight, Passenger passenger) {
        this.flight = flight;
        this.passenger = passenger;
    }

    @Override
    public void execute(){
        String sql = "DELETE FROM passengerFlightRelation WHERE passengerID = ? AND flightNumber = ?";

        try {
            PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql);

            pstmt.setString(1, passenger.getId());
            pstmt.setString(2, flight.getFlightNumber());

            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        flight.getSeats().freeStall(passenger.getSeatNumber(flight));
        passenger.setSeatNumber(flight,-1);
        passenger.removeFlight(flight.getFlightNumber());

    }
}
