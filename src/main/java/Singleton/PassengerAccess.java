package Singleton;

import Entities.Passenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;


/**
 * A Passenger Map using the singleton pattern
 */
public class PassengerAccess {

    /**
     * The singleton passenger map
     */
    private static ObservableList<Passenger> passengers;
    private static FilteredList<Passenger> searchedPassengers;

    /**
     * Private do nothing constructor
     */
    private PassengerAccess() {
    }

    /**
     * Gets the instance of the map
     *
     * @return  the map instance
     */
    public static ObservableList<Passenger> getInstance() {
        if (passengers == null) initialize();
        return passengers;
    }

    public static FilteredList<Passenger> getSearchInstance() {
        if (searchedPassengers == null) searchedPassengers = new FilteredList<>(getInstance());
        return searchedPassengers;
    }

    /**
     * Initialize the passenger map with the data in the database
     */
    private static void initialize() {
        if (passengers == null) {
            passengers = FXCollections.observableArrayList();
            try {
                String sql = "SELECT name, id, email, checkin, parkingStall FROM login WHERE role ='Passenger'";
                Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Passenger thePassenger;
                    passengers.add(thePassenger = new Passenger(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5)));
                    if (thePassenger.getParkingStallLabel() != 0 && thePassenger.getParkingStallLabel() != -1) {
                        AirportAccess.getInstance().getParkingStalls().assignEntityToStall(thePassenger,thePassenger.getParkingStallLabel());
                    }
                }

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}