package Passenger;


import Command.AddParking;
import Command.CancelParking;
import Singleton.AirportAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ParkingController {

    @FXML
    public Button closeButton;

    @FXML
    public TextField nameField;

    @FXML
    public TextField idField;

    @FXML
    public Label nameError;

    @FXML
    public TextField emailField;

    @FXML
    public Label emailError;

    @FXML
    public DatePicker CheckinDatePicker;

    @FXML
    public Label parkingLabel;

    @FXML
    public TextField idFieldCancel;

    @FXML
    public TextField parkingFieldCancel;

    @FXML
    public Label cancelMessage;

    @FXML
    public Label nameConfirmation;

    @FXML
    public Label emailConfirmation;

    @FXML
    public Label idConfirmation;

    @FXML
    public Label parkingNumConfirmation;

    @FXML
    public Button okButton;




    @FXML
    public void closeButtonOnAction(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

    public void toConfirmation(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/confirmParkingInfo.fxml"));
            Stage confirmationStage = new Stage();
            confirmationStage.initStyle(StageStyle.UNDECORATED);
            confirmationStage.setScene(new Scene(root, 600, 496));
            confirmationStage.setTitle("Parking Confirmation");
            confirmationStage.setResizable(false);
            confirmationStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }


    @FXML
    public void addParking(ActionEvent event) {


        if (nameField.getText().isEmpty() || idField.getText().isEmpty() || emailField.getText().isEmpty()
                || CheckinDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill all fields!");
            alert.setHeaderText("Fill all fields!");
            alert.setTitle("Error!");
            alert.showAndWait();
        }

        String EMAIL_REGEX = "^[0-9?A-z0-9?]+(\\.)?[0-9?A-z0-9?]+@[A-z]+\\.[A-z]{2}.?[A-z]{0,3}$";

        // checks name for any number and allows for spaces
        if (!(nameField.getText().isEmpty()) && !(nameField.getText()).matches("[A-Za-z\\s]{2,}")) {
            nameError.setText("Name must contain only letters");
            nameError.setTextFill(Color.RED);

        }

        // checks for correct email format
        else if (!(emailField.getText().isEmpty()) && !(emailField.getText()).matches(EMAIL_REGEX)) {
            emailError.setText("Must be at this form : user@domain.com");
            emailError.setTextFill(Color.RED);
            nameError.setText("");
        }

        // success
        else {
            AddParking addparking = new AddParking(this);
            addparking.execute();

            toConfirmation(event);

        }
    }

    @FXML
    public void deleteParkingReservation(ActionEvent actionEvent) {
        int parkingStall;
        try {
            parkingStall = Integer.parseInt(parkingFieldCancel.getText());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.OK);
            alert.setHeaderText("Error, parking number is not valid!");
            alert.setContentText(e.getMessage());
            alert.setTitle("Error");
            alert.show();
            return;
        }
        if (idFieldCancel.getText().isEmpty() || parkingFieldCancel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill all fields!");
            alert.setHeaderText("Fill all fields!");
            alert.setTitle("Error!");
            alert.showAndWait();
        }
        else if (AirportAccess.getInstance().getParkingStalls().isOccupied(parkingStall) &&
                AirportAccess.getInstance().getParkingStalls().getEntity(parkingStall).getId().equalsIgnoreCase(idFieldCancel.getText())) {

            cancelMessage.setText("Successfully Cancelled Parking Reservation");
            cancelMessage.setTextFill(Color.GREEN);
            CancelParking cancelParking = new CancelParking(this);
            cancelParking.execute();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
            alert.setHeaderText("Parking number was already empty or not a valid parking number! Please try again.");
            alert.setTitle("Info");
            alert.show();

        }
    }


    @FXML
    public void clearReserveForm(ActionEvent event) {
        nameField.clear();
        emailField.clear();
        CheckinDatePicker.setValue(null);
        nameError.setText("");
        emailError.setText("");
        parkingLabel.setText("");
        idField.setText("");

    }

    @FXML
    public void clearCancelForm(ActionEvent event) {
        idFieldCancel.clear();
        parkingFieldCancel.clear();
    }


    @FXML
    public void search(ActionEvent event) {
        if (nameField.getText().isEmpty() || idField.getText().isEmpty() || emailField.getText().isEmpty()
                || CheckinDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill all fields!");
            alert.setHeaderText("Fill all fields!");
            alert.setTitle("Error!");
            alert.showAndWait();
        }
        else{
            parkingLabel.setText(String.valueOf(AirportAccess.getInstance().getParkingStalls().firstAvailableStall()));

        }
    }
}