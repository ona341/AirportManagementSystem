package AirlineManager;

import Command.AddFlight;
import Command.DeleteFlight;
import Command.UpdateFlight;
import Entities.Flight;
import Singleton.FlightsAccess;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Controller for the Airline manager package
 */
public class AirlineManagerController implements Initializable {
    //Instances for the GUI
    @FXML
    public TextField flightnum;
    @FXML
    public DatePicker date;
    @FXML
    public TextField airline;
    @FXML
    public TextField destination;
    @FXML
    public TextField time;
    @FXML
    public Button logoutbutton;
    @FXML
    public TableView<Flight> tableview;
    @FXML
    public TableColumn<Flight,String> flightCol;
    @FXML
    public TableColumn<Flight,String> airlineCol;
    @FXML
    public TableColumn<Flight,String> destinationCol;
    @FXML
    public TableColumn<Flight,Date> dateCol;
    @FXML
    public TableColumn<Flight,Time> timeCol;
    @FXML
    public TableColumn<Flight,Character> gateCol;
    @FXML
    public TextField searchBox;
        //list of flights in database
    private ObservableList<Flight> flightData;

    /**
     * Logs out if the Airline Manager bag to the log in page
     * @param event the logout button is clicked
     * @throws IOException throws this if the I/O is interrupted or fails
     */
    @FXML
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/loginapp/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

    /**
     *Checks the format of the inputted time for validity
     * @param event a Mouse event
     */
    @FXML
    private void checkTime(MouseEvent event) {
        if (time.getText().matches("^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$")) {
        }
        else if (time.getText().matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"))
            time.appendText(":00");
        else{
            time.clear();
        }
    }

    /***
     * Calls and executes the add flight command
     * @param event when the addFlight bitton is clicked
     */
    @FXML
    public void addFlight(ActionEvent event) {
        AddFlight addflight = new AddFlight(this);
        addflight.execute();
    }

    /**
     * Finds the flight with the given input
     * @param event keys entered
     */
    @FXML
    private void searchTable(KeyEvent event) {
        if(searchBox.getText().isBlank()) {
            tableview.setItems(FlightsAccess.getInstance());
        }
        else {
            tableview.setItems(FlightsAccess.search(searchBox.getText()));
        }
    }

    /**
     * Clears the filled spaces in the form
     * @param event an action performed by the user
     */
    @FXML
    public void clearForm(ActionEvent event) {
        flightnum.clear();
        airline.clear();
        destination.clear();
        time.clear();
        date.setValue(null);
    }

    /**
     *Initializes the Controller
     * @param url he location used to resolve the relative paths of the object or null if unknown
     * @param resourceBundle the resources used to localize the root of the object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));

        tableview.setItems(FlightsAccess.getInstance()); //sets tableviews items to the instance of FlightsAccess
    }

    /**
     * Deletes the selected flight from the  system
     * @param actionEvent the user selecting delete flight
     */
    public void deleteRow(ActionEvent actionEvent) {
        ObservableList<Flight> selectedFlights;
        if (!(selectedFlights = tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
            DeleteFlight deleteflight = new DeleteFlight(selectedFlights);
            deleteflight.execute();
        }
    }

    /**
     * Updates the flight row selected
     * @param actionEvent an action performed by the user
     */
    public void updateRow(ActionEvent actionEvent) {
        ObservableList<Flight> selectedFlights;
        if (!(selectedFlights = tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
            UpdateFlight updateFlight = new UpdateFlight(selectedFlights, this);
            updateFlight.execute();
        }
    }

    /**
     * Loads the flights in the system into the panel to be viewed
     */
    public void loadFLightData() {

        try {

            Connection conn = dbConnection.getConnection();
            this.flightData = FXCollections.observableArrayList();  //sets the flight data attribute to be the observableArrayList from FXCollections

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM flights");

            while(rs.next()) { //creates and adds a flight object to the flight data while the query's result set has a next
                this.flightData.add(new Flight(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getTime(5), rs.getInt(6)));
            }

            conn.close(); //closes the database connection

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        this.airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        this.destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));

        this.tableview.setItems(null);
        this.tableview.setItems(this.flightData);
    }
}
