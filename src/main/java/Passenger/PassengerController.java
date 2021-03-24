package Passenger;

import AirlineEmployee.FlightTable;
import Entities.Passenger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PassengerController {
    Passenger passenger;

    public void initialize(Passenger passenger) {
        this.passenger = passenger;
    }

    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginViewScene);
        window.show();
    }

    public void toParkingReg(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/parkingFXML.fxml"));
            Stage reservationStage = new Stage();
            reservationStage.initStyle(StageStyle.UNDECORATED);
            reservationStage.setScene(new Scene(root, 555, 588));
            reservationStage.setTitle("Parking Reservation");
            reservationStage.setResizable(false);
            reservationStage.show();

        } catch (
                Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void toCancelParking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cancelParkingFXML.fxml"));
            Stage cancelResStage = new Stage();
            cancelResStage.initStyle(StageStyle.UNDECORATED);
            cancelResStage.setScene(new Scene(root, 600, 400));
            cancelResStage.setTitle("Cancel Parking Reservation");
            cancelResStage.setResizable(false);
            cancelResStage.show();

        } catch (
                Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void toFlightInformation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FlightTable.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));


            loader.<FlightTable>getController().initialize(passenger);
            loader.<FlightTable>getController().setPassengerPermissions();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
