package Singleton;

import Entities.Flight;
import Entities.Passenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;

import java.sql.*;

public class FlightsAccess {

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
                String sql = "SELECT flights.flightNum, flights.airline, flights.destination, flights.date, flights.time, flights.gate, flights.capacity, passengerFlightRelation.passengerID FROM flights LEFT JOIN passengerFlightRelation ON flights.flightNum=passengerFlightRelation.flightNumber";
                Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                Flight theFlight = null;
                while (rs.next()) {
                    if (theFlight == null || !theFlight.getFlightNumber().equals(rs.getString(1))) {
                        flights.add(theFlight = new Flight(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getTime(5), rs.getInt(6), rs.getInt(7)));
                        AirportAccess.getInstance().getGates().assignEntityToStall(theFlight, theFlight.getGate());
                    }
                    String passengerID = rs.getString(8);
                    if (passengerID != null) {
                        FilteredList<Passenger> passengers = PassengerAccess.getInstance().filtered(passenger -> passenger.getId().equals(passengerID));
                        if (!passengers.isEmpty()) {
                            passengers.get(0).addFlight(theFlight);
                            theFlight.getSeats().assignEntityToStall(passengers.get(0), theFlight.getSeats().firstAvailableStall());
                        }
                        else {
                            throw new IllegalStateException("No Passenger with that id was found");
                        }
                    }
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
