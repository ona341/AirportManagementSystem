package PopoutControllers;

import Command.BreakAssociation;
import Entities.Flight;
import Entities.Passenger;
import javafx.beans.property.IntegerProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.AbstractMap.SimpleEntry;

/**
 * Display the flights of a particular passenger in a table
 */
public class FlightTable {
    @FXML
    public TableView<SimpleEntry<Flight, IntegerProperty>> flightTable;
    @FXML
    public TableColumn<SimpleEntry<Flight,IntegerProperty>,SimpleEntry<Flight,IntegerProperty>> deleteCol;
    @FXML
    public TableColumn<SimpleEntry<Flight,IntegerProperty>,String> flightCol;
    @FXML
    public TableColumn<SimpleEntry<Flight,IntegerProperty>,String> airlineCol;
    @FXML
    public TableColumn<SimpleEntry<Flight,IntegerProperty>,String> destinationCol;
    @FXML
    public TableColumn<SimpleEntry<Flight,IntegerProperty>,Integer> gateCol;
    @FXML
    public TableColumn<SimpleEntry<Flight,IntegerProperty>, Date> dateCol;
    @FXML
    public TableColumn<SimpleEntry<Flight,IntegerProperty>, Time> timeCol;
    @FXML
    public TableColumn<SimpleEntry<Flight,IntegerProperty>,Integer> seatCol;

    /**
     * The passenger for this instance of the FlightTable
     */
    public Passenger passenger;

    /**
     * Initializes this instance of the flight table with the provided passengers information
     * Call this method from the invoking class controller
     */
    public void initialize(Passenger passenger) {
        this.passenger = passenger;

        // Setup the columns
        flightCol.setCellValueFactory(pair -> pair.getValue().getKey().flightNumberProperty());
        airlineCol.setCellValueFactory(pair -> pair.getValue().getKey().airlineProperty());
        destinationCol.setCellValueFactory(pair -> pair.getValue().getKey().destinationProperty());
        dateCol.setCellValueFactory(pair -> pair.getValue().getKey().dateProperty());
        timeCol.setCellValueFactory(pair -> pair.getValue().getKey().timeProperty());
        gateCol.setCellValueFactory(pair -> pair.getValue().getKey().gateProperty().asObject());
        seatCol.setCellValueFactory(pair -> pair.getValue().getValue().asObject());

        // Set the data to the flights stored by passenger
        ObservableList<SimpleEntry<Flight, IntegerProperty>> flights = passenger.getFlights();
        flightTable.setItems(flights);

        // Initialize the special column that lets the user delete flights from this passenger
        deleteCol.setCellValueFactory(flight -> new ReadOnlyObjectWrapper<>(flight.getValue()));
        deleteCol.setCellFactory(param -> {
            Button button = new Button("Delete");
            TableCell<SimpleEntry<Flight,IntegerProperty>,SimpleEntry<Flight,IntegerProperty>> cell = new TableCell<>() {

                @Override
                protected void updateItem(SimpleEntry<Flight,IntegerProperty> item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item.getKey() == null)
                        setGraphic(null);
                    else
                        setGraphic(button);
                }
            };
            button.setOnAction(event -> {new BreakAssociation(cell.getItem().getKey(),passenger).execute();
            });
            return cell;
        });
    }

    /**
     * A flight can be double clicked to view more information
     */
    @FXML
    public void doubleClickFlight(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<SimpleEntry<Flight, IntegerProperty>> selectedFlights;
            if (!(selectedFlights = flightTable.getSelectionModel().getSelectedItems()).isEmpty()) {
                openPassengerTable(selectedFlights.get(0).getKey());
            }
        }
    }

    /**
     * When a flight is double clicked open a PassengerTable to view the passengers on that flight
     */
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

    /**
     * If this table is opened by a passenger use this method to revoke the deleting permissions
     */
    public void setPassengerPermissions() {
        flightTable.getColumns().remove(deleteCol);
        flightTable.setOnMouseClicked(null);
    }
}
