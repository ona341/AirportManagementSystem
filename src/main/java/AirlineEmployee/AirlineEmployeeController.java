package AirlineEmployee;

import Command.AddUser;
import Entities.Flight;
import Entities.Passenger;
import Singleton.DBConnection;
import Singleton.FlightsAccess;
import Singleton.PassengerAccess;
import PopoutControllers.FlightTable;
import PopoutControllers.PassengerTable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



/**
 * JavaFX Controller for the Airline Employee
 */
public class AirlineEmployeeController implements Initializable {
    // # # # Passenger Table Fields # # #
    @FXML
    public TableView<Passenger> passengerTable;
    @FXML
    public TableColumn<Passenger,String> IDCol;
    @FXML
    public TableColumn<Passenger,String> passNameCol;
    @FXML
    public TableColumn<Passenger,Integer> passFlightCol;
    @FXML
    public TableColumn<Passenger,String> passContactCol;
    // # # # Flight Table Fields # # #
    @FXML
    public TableView<Flight> flightTable;
    @FXML
    public TableColumn<Flight, String> flightCol;
    @FXML
    public TableColumn<Flight, String> airlineCol;
    @FXML
    public TableColumn<Flight,String> destinationCol;
    @FXML
    public TableColumn<Flight,Character> gateCol;
    @FXML
    public TableColumn<Flight, Date> dateCol;
    @FXML
    public TableColumn<Flight, Time> timeCol;
    @FXML
    public TableColumn<Flight,Integer> passengerCol;
    // # # # MISC Structure Fields
    @FXML
    public HBox topBox;
    @FXML
    public Pane passPane;
    // # # # Add Passenger Fields # # #
    @FXML
    public TextField NameField;
    @FXML
    public TextField IDField;
    @FXML
    public TextField EmailField;
    @FXML
    public Button ConfirmButton;
    // # # # Search boxes
    @FXML
    public TextField searchPassenger;
    @FXML
    public TextField searchFlight;

    /**
     * Logs the user out of the Airline Employee and returns user to the login page
     */
    public void logout(ActionEvent event) throws IOException {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

    /**
     * When a new AirlineEmployee Scene is loaded by JavaFx this method is automatically called
     * It is used to set up the configuration to display the information properly
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setup the columns for the table with flight information
        flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));
        passengerCol.setCellValueFactory(flight -> flight.getValue().getSeats().count().asObject());

        // Set the items to the table
        flightTable.setItems(FlightsAccess.getSearchInstance());
        // Initialize the search box
        searchFlight.textProperty().addListener((a,b,c) -> FlightsAccess.getSearchInstance().setPredicate(Flight.search(c)));

        // Setup the columns for the table with passenger information
        IDCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        passNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        passFlightCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        passContactCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Set the items to the table
        passengerTable.setItems(PassengerAccess.getSearchInstance());
        // Initialize the search box
        searchPassenger.textProperty().addListener((a,b,c) -> PassengerAccess.getSearchInstance().setPredicate(Passenger.search(c)));

        // By default the new passenger window is closed
        passPane.setVisible(!passPane.isVisible());
        passPane.setManaged(!passPane.isManaged());
    }

    /**
     * When the Assign Passenger to flight button is clicked this event is triggered
     * The selected passenger is added to the selected flight
     */
    public void makeAssociation(ActionEvent actionEvent) {
        // Find the selected items
        Passenger p = passengerTable.getSelectionModel().getSelectedItem();
        Flight f = flightTable.getSelectionModel().getSelectedItem();

        // Make sure there is a valid selection
        // The request is ignored if that passenger is already on the selected flight
        if (p != null && f != null && !f.getSeats().hasEntity(p)) {

            // Make the association
            p.addFlight(f);
            p.setSeatNumber(f,f.getSeats().firstAvailableStall());
            f.getSeats().assignEntityToStall(p,p.getSeatNumber(f));

            // Update the DB
            String sql = "INSERT INTO passengerFlightRelation(passengerID,flightNumber,seatNumber) VALUES(?,?,?)";
            try {
                PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql);

                pstmt.setString(1,p.getId());
                pstmt.setString(2,f.getFlightNumber());
                pstmt.setInt(3,p.getSeatNumber(f));

                pstmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * This method is called when the New Passenger button is clicked
     * It opens the window for adding new passengers
     */
    public void openNewPassenger(ActionEvent actionEvent) {
        passPane.setVisible(!passPane.isVisible());
        passPane.setManaged(!passPane.isManaged());
        topBox.getScene().getWindow().sizeToScene();
    }

    /**
     * Within the new passenger window, this method is called when the confirm button is clicked
     * Adds a new passenger to the system
     */
    public void addPassenger(ActionEvent actionEvent) {
        Passenger p = new Passenger(NameField.getText(),IDField.getText(),EmailField.getText());
        new AddUser(p,new char[] {1,2,3}).execute();
    }

    /**
     * Detect if a passenger in the table is double clicked on
     * Opens a view of all the flights the passenger is registered to
     */
    @FXML
    public void doubleClickPassenger(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<Passenger> selectedPassengers;
            if (!(selectedPassengers = passengerTable.getSelectionModel().getSelectedItems()).isEmpty()) {
                openFlightTable(selectedPassengers.get(0));
            }
        }
    }


    /**
     * Show the special flight table after a double click
     */
    private void openFlightTable(Passenger passenger) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FlightTable.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            // Pass the information for this passenger to the controller
            loader.<FlightTable>getController().initialize(passenger);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Detect if a flight in the table is double clicked on
     * Opens a view of all the passengers registered to this flight
     */
    @FXML
    public void doubleClickFlight(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<Flight> selectedFlights;
            if (!(selectedFlights = flightTable.getSelectionModel().getSelectedItems()).isEmpty()) {
                openPassengerTable(selectedFlights.get(0));
            }
        }
    }

    /**
     * Show the special passenger table after a double click
     */
    private void openPassengerTable(Flight flight) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PassengerTable.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            // Pass the information for this flight to the controller
            loader.<PassengerTable>getController().initialize(flight);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
