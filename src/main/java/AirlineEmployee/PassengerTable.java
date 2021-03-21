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

public class PassengerTable {


    public TableView<Passenger> passengerTable;
    public TableColumn<Passenger,Passenger> deleteCol;


    public TableColumn<Passenger,String> iDCol;
    public TableColumn<Passenger,String> nameCol;
    public TableColumn<Passenger,String> contactCol;
    public TableColumn<Passenger,Integer> flightCol;


    public Flight flight;

    public void initialize(Flight flight) {
        this.flight = flight;
        ObservableList<Passenger> passengers =  flight.getSeats().getObservableList();

        iDCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        flightCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("email"));


        passengerTable.setItems(passengers);

        deleteCol.setCellValueFactory(passenger -> new ReadOnlyObjectWrapper<>(passenger.getValue()));
        deleteCol.setCellFactory(param -> {
            Button button = new Button("Delete");
            TableCell<Passenger,Passenger> cell = new TableCell<>() {

                @Override
                protected void updateItem(Passenger item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty)
                        setGraphic(null);
                    else
                        setGraphic(button);
                }
            };
            button.setOnAction(event -> {new BreakAssociation(flight,cell.getItem()).execute();
            });
            return cell;
        });
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

}
