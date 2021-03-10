package Passenger;


import Command.AddParking;
import Singleton.AirportAccess;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;




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



    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }


    @FXML
    public void addParking(ActionEvent event) {

        if(nameField.getText().isEmpty() || idField.getText().isEmpty() || emailField.getText().isEmpty()
        || CheckinDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill all fields!");
            alert.setHeaderText("Fill all fields!");
            alert.setTitle("Error!");
            alert.showAndWait();
        }

        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (!(nameField.getText().isEmpty()) && !(nameField.getText()).matches("[A-Za-z\\s]{2,}")){
            nameError.setText("Name must contain only letters");
            nameError.setTextFill(Color.RED);

        }
        else if (!(emailField.getText().isEmpty()) &&!(emailField.getText()).matches(EMAIL_REGEX)) {
            emailError.setText("Must be at this form : user@domain.com");
            emailError.setTextFill(Color.RED);
            nameError.setText("");
        }

        else {
            AddParking addparking = new AddParking(this);
            addparking.execute();
            nameError.setText("Successfully Booked Parking!");
            nameError.setTextFill(Color.GREEN);
            emailError.setText("");

        }
    }

    @FXML
    public void clearForm(ActionEvent event) {
        nameField.clear();
        emailField.clear();
        CheckinDatePicker.setValue(null);
        nameError.setText("");
        emailError.setText("");
        parkingLabel.setText("");
        idField.setText("");

    }

    @FXML
    public void search(ActionEvent event) {

        parkingLabel.setText(String.valueOf(AirportAccess.getInstance().firstAvailableStall()));

    }

}