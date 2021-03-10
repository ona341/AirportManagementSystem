package Singleton;
/*
  CMPT 270 A5Q4
  @author Blake Stadnyk; 11195866 - BJS645
 */

import Entities.Flight;
import Entities.Passenger;
import Entities.Person;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

/**
 * A Passenger Map using the singleton pattern
 */
public class PassengerMapAccess {

    private static final String sql = "SELECT name, id, email, checkin, parkingStall FROM parking";

    private static ObservableList<Passenger> passengers;



    private PassengerMapAccess() {
    }

    public static ObservableList<Passenger> getInstance() {
        if (passengers == null) initialize();
        return passengers;
    }

    private static void initialize() {
        if (passengers == null) {
            passengers = FXCollections.observableArrayList();
            try {
                Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    passengers.add(new Passenger(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}