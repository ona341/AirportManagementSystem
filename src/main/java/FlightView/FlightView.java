package FlightView;

import Entities.Flight;
import Entities.Passenger;
import Singleton.AirportAccess;
import Singleton.dbConnection;
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

public class FlightView implements Initializable {
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

    private Flight flight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

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

        tablePane.setVisible(!tablePane.isVisible());
        tablePane.setManaged(!tablePane.isManaged());
        done.getScene().getWindow().sizeToScene();

        loadPassengers();

    }
    @FXML
    public void done(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    public String newDialog(String defaultValue, String fieldName) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle("");
        dialog.setHeaderText("Editing " + fieldName);
        dialog.setContentText("");
        dialog.setGraphic(null);

        Optional<String> answer = dialog.showAndWait();
        return answer.orElse(defaultValue);

    }

    public void dbUpdate(Flight flight) {

        String sql = "UPDATE flights SET flightNum = ?, airline = ?, destination = ?, date = ?, time = ?, gate = ? WHERE flightNum = ?";

        try {
            Connection conn = dbConnection.getConnection();
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
            AirportAccess.getInstance().getGates().assignEntityToStall(flight,gate);
        }  catch (IllegalStateException | IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR,"The entered gate is either occupied, exceeds the maximum gate number, or is not an integer").showAndWait();
            return editGate(event);
        }

        flight.setGate(gate);
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


    public void viewPassengers(ActionEvent actionEvent) {
        tablePane.setVisible(!tablePane.isVisible());
        showButton.setText(tablePane.isVisible() ? "Hide" : "Show");


        tablePane.setManaged(!tablePane.isManaged());
        ((Button) actionEvent.getSource()).getScene().getWindow().sizeToScene();
    }

    public void loadPassengers() {

        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        iDCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        seatNumberCol.setCellValueFactory(data -> data.getValue() != null ? new SimpleIntegerProperty(flight.getSeats().getEntityInternalIndex(data.getValue())).asObject() : null);

        ObservableList<Passenger> thelist;
        passengerTable.setItems(thelist = flight.getSeats().getObservableList());
        Passenger testPass = new Passenger("Bob","123","bob@gmail.com",null, 2);
        flight.getSeats().assignEntityToStall(testPass,2);
        thelist.add(testPass);

    }
}
