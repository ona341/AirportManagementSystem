package AirlineEmployee;

import Command.BreakAssociation;
import Entities.Flight;
import Entities.Passenger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

public class FlightTable {


    public TableView<Flight> flightTable;
    public TableColumn<Flight,Flight> deleteCol;


    public TableColumn<Flight,String> flightCol;
    public TableColumn<Flight,String> airlineCol;
    public TableColumn<Flight,String> destinationCol;
    public TableColumn<Flight,Character> gateCol;
    public TableColumn<Flight, Date> dateCol;
    public TableColumn<Flight, Time> timeCol;
    public TableColumn<Flight,Integer> passengerCol;

    public Passenger passenger;

    public void initialize(Passenger passenger) {
        this.passenger = passenger;
        ObservableList<Flight> flights =  passenger.getFlights();

        flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));
        passengerCol.setCellValueFactory(flight -> flight.getValue().getSeats().count().asObject());

        flightTable.setItems(flights);

        deleteCol.setCellValueFactory(flight -> new ReadOnlyObjectWrapper<>(flight.getValue()));
        deleteCol.setCellFactory(param -> {
            Button button = new Button("Delete");
            TableCell<Flight,Flight> cell = new TableCell<>() {

                @Override
                protected void updateItem(Flight item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty)
                        setGraphic(null);
                    else
                        setGraphic(button);
                }
            };
            button.setOnAction(event -> {new BreakAssociation(cell.getItem(),passenger).execute();
            });
            return cell;
        });
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
