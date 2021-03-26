package PopoutControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConfirmationWindow {

    @FXML
    private Label nameConfirmation;
    @FXML
    private Label idConfirmation;
    @FXML
    private Label emailConfirmation;
    @FXML
    private Label parkingNumConfirmation;

    @FXML
    public void closeButtonOnAction(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    public void setNameConfirmation(String name) {
        nameConfirmation.setText(name);
    }

    public void setIdConfirmation(String id) {
        idConfirmation.setText(id);
    }

    public void setEmailConfirmation(String email) {
        emailConfirmation.setText(email);
    }

    public void setParkingNumConfirmation(String parkingNum) {
        parkingNumConfirmation.setText(parkingNum);
    }





}
