package Singleton;

import Entities.Flight;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;

import java.sql.*;

public class FlightsAccess {

    private static final String sql = "SELECT flightNum, airline, destination, date, time, gate FROM flights";

    private static ObservableList<Flight> flights;

    private static ObservableList<Flight> searchResult;

    private FlightsAccess() {}

    public static ObservableList<Flight> getInstance() {
        if (flights == null) initialize();
        return flights;
    }

    private static void initialize() {
        if (flights == null) {
            flights = FXCollections.observableArrayList();
            try {
                Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    flights.add(new Flight(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getTime(5),rs.getInt(6)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static ObservableList<Flight> search(String text) {
        FilteredList<Flight> filteredByNum = flights.filtered(flight -> flight.getFlightNumber().contains(text));
        FilteredList<Flight> filteredByAirline = flights.filtered(flight -> flight.getAirline().contains(text));
        FilteredList<Flight> filteredByDestination = flights.filtered(flight -> flight.getDestination().contains(text));
        FilteredList<Flight> filteredByDate = flights.filtered(flight -> flight.getDate().toString().contains(text));
        FilteredList<Flight> filteredByTime = flights.filtered(flight -> flight.getTime().toString().contains(text));

        ObservableSet<Flight> resultSet = FXCollections.observableSet();
        resultSet.addAll(filteredByNum); resultSet.addAll(filteredByAirline);
        resultSet.addAll(filteredByDestination); resultSet.addAll(filteredByDate);
        resultSet.addAll(filteredByTime);

        return searchResult = FXCollections.observableArrayList(resultSet);
    }

    public static ObservableList<Flight> getSearchInstance() {
        if (searchResult == null)
            searchResult =  FXCollections.observableArrayList();
        return searchResult;
    }
}
