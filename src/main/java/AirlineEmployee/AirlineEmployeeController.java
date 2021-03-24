package AirlineEmployee;

import Command.AddUser;
import Entities.Flight;
import Entities.Passenger;
import Singleton.FlightsAccess;
import Singleton.PassengerAccess;
import Singleton.dbConnection;
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
 * Controller for the Airline Employee, Currently only logs out
 */
public class AirlineEmployeeController implements Initializable {

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
    @FXML
    public HBox topBox;
    @FXML
    public Pane passPane;
    @FXML
    public TextField NameField;
    @FXML
    public TextField IDField;
    @FXML
    public TextField EmailField;
    @FXML
    public Button ConfirmButton;
    public TextField searchPassenger;
    public TextField searchFlight;

    /**
     * Logs the user out of the Airline Employee and returns user to the login page
     * @param event the logout button clicked
     * @throws IOException throws this is the I/O is interrupted or fails
     */
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show(); //Displays window
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));
        passengerCol.setCellValueFactory(flight -> flight.getValue().getSeats().count().asObject());

        flightTable.setItems(FlightsAccess.getSearchInstance());
        searchFlight.textProperty().addListener((a,b,c) -> FlightsAccess.getSearchInstance().setPredicate(Flight.search(c)));

        IDCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        passNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));


        passFlightCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        passContactCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        passengerTable.setItems(PassengerAccess.getSearchInstance());
        searchPassenger.textProperty().addListener((a,b,c) -> PassengerAccess.getSearchInstance().setPredicate(Passenger.search(c)));

        passPane.setVisible(!passPane.isVisible());
        passPane.setManaged(!passPane.isManaged());


    }

    /**
     * Associates the Passengers with their flight details
     * @param actionEvent
     */
    public void makeAssociation(ActionEvent actionEvent) {
        Passenger p = passengerTable.getSelectionModel().getSelectedItem();
        Flight f = flightTable.getSelectionModel().getSelectedItem();
        if (p != null && f != null && !f.getSeats().hasEntity(p)) {

            p.addFlight(f);
            p.setSeatNumber(f,f.getSeats().firstAvailableStall());
            f.getSeats().assignEntityToStall(p,p.getSeatNumber(f));

            String sql = "INSERT INTO passengerFlightRelation(passengerID,flightNumber,seatNumber) VALUES(?,?,?)";
            try {
                PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql);

                pstmt.setString(1,p.getId());
                pstmt.setString(2,f.getFlightNumber());
                pstmt.setInt(3,p.getSeatNumber(f));

                pstmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public void openNewPassenger(ActionEvent actionEvent) {
        passPane.setVisible(!passPane.isVisible());
        passPane.setManaged(!passPane.isManaged());
        topBox.getScene().getWindow().sizeToScene();
    }

    /**
     * Adds a passenger to the system
     * @param actionEvent
     */
    public void addPassenger(ActionEvent actionEvent) {
        Passenger p = new Passenger(NameField.getText(),IDField.getText(),EmailField.getText());
        new AddUser(p,new char[] {1,2,3}).execute();
    }

    @FXML
    public void doubleClickPassenger(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<Passenger> selectedPassengers;
            if (!(selectedPassengers = passengerTable.getSelectionModel().getSelectedItems()).isEmpty()) {
                openFlightTable(selectedPassengers.get(0));
            }
        }
    }



    public void openFlightTable(Passenger passenger) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FlightTable.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));


            loader.<FlightTable>getController().initialize(passenger);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void doubleClickFlight(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<Flight> selectedFlights;
            if (!(selectedFlights = flightTable.getSelectionModel().getSelectedItems()).isEmpty()) {
                openPassengerTable(selectedFlights.get(0));
            }
        }
    }


    public void openPassengerTable(Flight flight) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PassengerTable.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));


            loader.<PassengerTable>getController().initialize(flight);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
