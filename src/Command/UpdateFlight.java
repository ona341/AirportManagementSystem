package Command;

import AirlineManager.AirlineManagerController;
import Entities.Flight;
import dbUtil.dbConnection;
import javafx.collections.ObservableList;

import java.sql.*;

public class UpdateFlight implements Command {

    private static ObservableList<Flight> selectedFlights;

    private AirlineManagerController airlineManagerController;

    public UpdateFlight(ObservableList<Flight> selectedFlights, AirlineManagerController airlineManagerController) {

        this.selectedFlights = selectedFlights;
        this.airlineManagerController = airlineManagerController;
    }

    @Override
    public void execute() throws SQLException {

        String sql = "UPDATE flights SET airline = ?, destination = ?, date = ?, time = ? WHERE flightNum = ?";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Flight flight1 : selectedFlights) {

                pstmt.setString(5, flight1.getFlightNumber());

                pstmt.setString(1, airlineManagerController.airline.getText());
                pstmt.setString(2, airlineManagerController.destination.getText());
                pstmt.setDate(3, Date.valueOf(airlineManagerController.date.getValue()));
                pstmt.setTime(4, Time.valueOf(airlineManagerController.time.getText()));

                pstmt.executeUpdate();

            }

            conn.close();
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        this.airlineManagerController.clearForm(null);
        this.airlineManagerController.loadFLightData();
    }
}