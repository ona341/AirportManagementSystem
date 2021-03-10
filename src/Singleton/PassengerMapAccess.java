package Singleton;
/*
  CMPT 270 A5Q4
  @author Blake Stadnyk; 11195866 - BJS645
 */

import Entities.Passenger;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
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

    private static ObservableMap<String,Passenger> passengers;


    private PassengerMapAccess() {
    }

    public static ObservableMap<String,Passenger> getInstance() {
        if (passengers == null) initialize();
        return passengers;
    }

    private static void initialize() {
        if (passengers == null) {
            passengers = FXCollections.observableMap(new TreeMap<>());
            try {
                Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    passengers.put(rs.getString(2),new Passenger(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}