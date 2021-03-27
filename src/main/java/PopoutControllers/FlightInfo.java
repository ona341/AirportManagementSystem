package PopoutControllers;

import Entities.Flight;
import Entities.Passenger;
import Singleton.AirportAccess;
import Singleton.DBConnection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.net.URL;

import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Display the details of a flight and provides the ability to modify those details
 */
public class FlightInfo {
    @FXML
    public Button done;
    @FXML
    public Text flightTitle;
    @FXML
    public Text flightNumber;
    @FXML
    public Text airline;
    @FXML
    public Text destination;
    @FXML
    public Text date;
    @FXML
    public Text time;
    @FXML
    public Text gate;
    @FXML
    public Text passengers;
    @FXML
    public TableView<Passenger> passengerTable;
    @FXML
    public AnchorPane tablePane;
    @FXML
    public HBox hBox;
    @FXML
    public Button showButton;
    @FXML
    public TableColumn<Passenger, String> nameCol;
    @FXML
    public TableColumn<Passenger, String> iDCol;
    @FXML
    public TableColumn<Passenger, String> emailCol;
    @FXML
    public TableColumn<Passenger, Integer> seatNumberCol;

    /**
     * The flight being looked at
     */
    private Flight flight;

    /**
     * Use this method from the calling class to load the flight to view
     */
    public void initialize(Flight flight) {
        this.flight = flight;

        flightTitle.setText("Flight: " + flight.getFlightNumber());
        flightNumber.setText(flight.getFlightNumber());
        airline.setText(flight.getAirline());
        destination.setText(flight.getDestination());
        date.setText(flight.getDate().toString());
        time.setText(flight.getTime().toString());
        gate.setText(String.valueOf(flight.getGate()));
        passengers.setText(String.valueOf(flight.getSeats().count()));

        // Hides the passenger list by default
        tablePane.setVisible(!tablePane.isVisible());
        tablePane.setManaged(!tablePane.isManaged());
        done.getScene().getWindow().sizeToScene();

        // Setup the columns in the passenger window
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        iDCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        seatNumberCol.setCellValueFactory(data -> data.getValue() != null ? new SimpleIntegerProperty(flight.getSeats().getEntityInternalIndex(data.getValue())).asObject() : null);

        // Set the data for the table
        passengerTable.setItems(flight.getSeats().getObservableList());

        // Ensure the text is updated if a passenger is removed from the flight
        passengers.textProperty().bind(flight.getSeats().count().asString());

    }

    /**
     * Closes the window if the user click the done button
     */
    @FXML
    public void done(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * A method for creating input dialog windows
     * Can customize the name of the field and set a default value
     */
    public static String newDialog(String defaultValue, String fieldName) {

        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle("");
        dialog.setHeaderText("Editing " + fieldName);
        dialog.setContentText("");
        dialog.setGraphic(null);

        // The user must take action before anything proceeds
        Optional<String> answer = dialog.showAndWait();
        return answer.orElse(defaultValue);
    }

    /**
     * If a change is made update the database accordingly
     */
    public void dbUpdate(Flight flight) {

        String sql = "UPDATE flights SET flightNum = ?, airline = ?, destination = ?, date = ?, time = ?, gate = ? WHERE flightNum = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, flight.getFlightNumber());
            pstmt.setString(2, flight.getAirline());
            pstmt.setString(3, flight.getDestination());
            pstmt.setDate(4, flight.getDate());
            pstmt.setTime(5, flight.getTime());
            pstmt.setInt(6,flight.getGate());
            pstmt.setString(7, flight.getFlightNumber());

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void editNumber(ActionEvent event) {
        flight.setFlightNumber(newDialog(flightNumber.getText(), "Flight Number"));
        dbUpdate(flight);
        flightNumber.setText(flight.getFlightNumber());
    }

    public void editAirline(ActionEvent event) {
        flight.setAirline(newDialog(airline.getText(), "Airline"));
        dbUpdate(flight);
        airline.setText(flight.getAirline());
    }

    public void editDestination(ActionEvent event) {
        flight.setDestination(newDialog(destination.getText(), "Destination"));
        dbUpdate(flight);
        destination.setText(flight.getDestination());
    }

    public int editGate(ActionEvent event) {
        int gate;
        try {
            gate = Integer.parseInt(newDialog(this.gate.getText(), "Gate"));
            if (gate == flight.getGate()) {
                return gate;
            }
            else if (gate == -1) {
                AirportAccess.getInstance().getGates().freeStall(flight.getGate());
                flight.setGate(gate);
            }
            else if (flight.getGate() != gate) {
                AirportAccess.getInstance().getGates().assignEntityToStall(flight,gate);
                if (!AirportAccess.getInstance().getGates().isInvalidLabel(flight.getGate()))
                    AirportAccess.getInstance().getGates().freeStall(flight.getGate());
                flight.setGate(gate);
            }

        }  catch (IllegalStateException | IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR,"The entered gate is either occupied, exceeds the maximum gate number, or is not an integer").showAndWait();
            return editGate(event);
        }
        dbUpdate(flight);
        this.gate.setText(String.valueOf(flight.getGate()));
        return gate;
    }


    public void editDate(ActionEvent event) {
        Stage stage = new Stage();
        DatePicker datePicker = new DatePicker();

        Scene scene = new Scene(datePicker,200,100);
        stage.setScene(scene);
        datePicker.show();
        stage.showAndWait();
        flight.setDate(Date.valueOf(datePicker.getValue()));
        dbUpdate(flight);
        this.date.setText(flight.getDate().toString());
    }

    public Time editTime(ActionEvent event) {
        Time time;
        try {
             time = Time.valueOf(newDialog(this.time.getText(), "Time")) ;
        } catch (IllegalArgumentException e) {
             new Alert(Alert.AlertType.ERROR,"The entered time could not be interpreted correctly").showAndWait();
             return editTime(event);
        }
        flight.setTime(time);
        dbUpdate(flight);
        this.time.setText(flight.getTime().toString());

        return time;
    }

    /**
     * Shows/Hides the list of passenger if the button is clicked
     */
    public void viewPassengers(ActionEvent actionEvent) {
        tablePane.setVisible(!tablePane.isVisible());
        showButton.setText(tablePane.isVisible() ? "Hide" : "Show");

        tablePane.setManaged(!tablePane.isManaged());
        ((Button) actionEvent.getSource()).getScene().getWindow().sizeToScene();
    }
}
