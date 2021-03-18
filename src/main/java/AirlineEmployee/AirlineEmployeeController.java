package AirlineEmployee;

import Entities.Flight;
import Entities.Passenger;
import Singleton.FlightsAccess;
import Singleton.PassengerAccess;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
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

        flightTable.setItems(FlightsAccess.getInstance());

        IDCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        passNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        passFlightCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().numFlights()).asObject());
        passContactCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        passengerTable.setItems(PassengerAccess.getInstance());

    }
}
