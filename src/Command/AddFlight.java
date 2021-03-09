package Command;

/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */

import java.sql.*;
import java.util.Arrays;

import AirlineManager.AirlineManagerController;
import IO.IOAccess;
import Singleton.AirportAccess;
import Entities.Flight;
import Singleton.flightsAccess;
import dbUtil.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Allows the user to add a flight to the system.
 */
public class AddFlight implements Command{
    //@Override
    /*public void execute() {
        try {
            Connection c =  dbConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        IOAccess.getInstance().outputString("Adding a new flight...");
        String airline = IOAccess.getInstance().readString("Enter the airline:");
        String number = IOAccess.getInstance().readString("Enter the flight number:");
        String destination = IOAccess.getInstance().readString("Enter the destination:");
        int capacity = IOAccess.getInstance().readInt("Enter the capacity:");

        if (AirportAccess.getInstance().hasFlight(number)) {
            IOAccess.getInstance().outputString("A flight with this number already exists in the system");
        }
        else {
            Flight flight = new Flight(airline, number, destination, null, null);
            AirportAccess.getInstance().assignFlightToGate(flight,AirportAccess.getInstance().availableGates().get(0));
            IOAccess.getInstance().outputString("Flight created.");
        }
    }*/

    private AirlineManagerController airlineManagerController;

    public AddFlight(AirlineManagerController airlineManagerController) {
        this.airlineManagerController = airlineManagerController;
    }


    @FXML
    public void execute() {
        String sql = "INSERT INTO flights(flightNum,airline,destination,date,time) VALUES(?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setString(1,airlineManagerController.flightnum.getText());
            pstmt.setString(2,airlineManagerController.airline.getText());
            pstmt.setString(3,airlineManagerController.destination.getText());
            pstmt.setDate(4, Date.valueOf(airlineManagerController.date.getValue()));
            pstmt.setTime(5, Time.valueOf(airlineManagerController.time.getText()));

            pstmt.executeUpdate();
            flightsAccess.getInstance().add(new Flight(airlineManagerController.flightnum.getText(),
                    airlineManagerController.airline.getText(),
                    airlineManagerController.destination.getText(),
                    Date.valueOf(airlineManagerController.date.getValue()),
                    Time.valueOf(airlineManagerController.time.getText())));

            airlineManagerController.clearForm(null);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
