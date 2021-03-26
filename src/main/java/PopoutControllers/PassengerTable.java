package PopoutControllers;

import Command.BreakAssociation;
import Entities.Flight;
import Entities.Passenger;

import Singleton.DBConnection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PassengerTable {


    public TableView<Passenger> passengerTable;
    public TableColumn<Passenger,Passenger> deleteCol;


    public TableColumn<Passenger,String> iDCol;
    public TableColumn<Passenger,String> nameCol;
    public TableColumn<Passenger,String> contactCol;
    public TableColumn<Passenger,Integer> seatCol;


    public Flight flight;

    public void initialize(Flight flight) {
        this.flight = flight;
        ObservableList<Passenger> passengers =  flight.getSeats().getObservableList();

        iDCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        seatCol.setCellValueFactory(passenger -> {
            if (passenger.getValue() != null)
                return passenger.getValue().seatProperty(flight).asObject();
            else
                return null;
        });

        passengerTable.setItems(passengers);

        deleteCol.setCellValueFactory(passenger -> new ReadOnlyObjectWrapper<>(passenger.getValue()));
        deleteCol.setCellFactory(param -> {
            Button button = new Button("Delete");
            TableCell<Passenger,Passenger> cell = new TableCell<>() {

                @Override
                protected void updateItem(Passenger item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null)
                        setGraphic(null);
                    else
                        setGraphic(button);
                }
            };
            button.setOnAction(event -> {
                if (cell.getItem()!=null)
                    new BreakAssociation(flight,cell.getItem()).execute();
            });
            return cell;
        });

        seatCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR,"The entered information was not an integer").showAndWait();
                    return -1;
                }
            }
        }));
        passengerTable.setEditable(true);
        seatCol.setOnEditCommit(this::changeSeat);
    }

    private void changeSeat(TableColumn.CellEditEvent<Passenger,Integer> event) {
        if (event.getNewValue() == -1) return;
            try {
                Passenger passenger = event.getRowValue();
                flight.getSeats().assignEntityToStall(passenger, event.getNewValue());
                flight.getSeats().freeStall(passenger.getSeatNumber(flight));
                passenger.setSeatNumber(flight,event.getNewValue());

                String sql = "UPDATE passengerFlightRelation SET seatNumber = ? WHERE flightNumber = ? AND passengerID = ?";
                PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(sql);

                pstmt.setInt(1,event.getNewValue());
                pstmt.setString(2, flight.getFlightNumber());
                pstmt.setString(3, passenger.getId());

                pstmt.executeUpdate();
                pstmt.close();

            } catch (IllegalStateException | IllegalArgumentException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "The entered seat is either occupied or exceeds the maximum seat number.").showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }

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
