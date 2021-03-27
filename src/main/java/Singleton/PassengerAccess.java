package Singleton;

import Entities.Passenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A List of passengers using the singleton pattern
 */
public class PassengerAccess {

    /**
     * The backend list for adding and removing passengers
     */
    private static ObservableList<Passenger> passengers;

    /**
     * The frontend list that is not editable
     */
    private static FilteredList<Passenger> searchedPassengers;

    /**
     * Private do nothing constructor
     */
    private PassengerAccess() {
    }

    /**
     * Gets the backend list
     * Should only be used for modifying the list
     */
    public static ObservableList<Passenger> getInstance() {
        if (passengers == null) initialize();
        return passengers;
    }

    /**
     * Get the frontend list
     * For displaying the data
     */
    public static FilteredList<Passenger> getSearchInstance() {
        if (searchedPassengers == null) searchedPassengers = new FilteredList<>(getInstance());
        return searchedPassengers;
    }

    /**
     * Initialize the list from the database
     */
    private static void initialize() {
        if (passengers == null) {
            passengers = FXCollections.observableArrayList();
            try {
                String sql = "SELECT name, id, email, checkin, parkingStall FROM login WHERE role ='Passenger'";
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) { // Continue for every item in the database
                    Passenger thePassenger;
                    passengers.add(thePassenger = new Passenger(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5)));
                    UserAccess.getInstance().add(thePassenger);
                    if (thePassenger.getParkingStallLabel() != 0 && thePassenger.getParkingStallLabel() != -1) {
                        // If there is a valid parking spot then also put the passenger into the parking lot
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