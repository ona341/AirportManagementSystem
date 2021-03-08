package loginapp;

import dbUtil.dbConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController {

    @FXML
    private Button closeButton;
    @FXML
    private Label messageLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label passMessageLabel;
    @FXML
    private TextField idNumberTextField;
    @FXML
    private ComboBox<option> selectionComboBox;
    @FXML Label errorMessageLabel;


    public void initialize() {
        this.selectionComboBox.setItems(FXCollections.observableArrayList(option.values()));
    }

    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        // Exits application
        Platform.exit();

    }

    public void registerButtonOnAction(ActionEvent event){
        if(this.idNumberTextField.getText().isEmpty() || this.setPasswordField.getText().isEmpty() ||
                this.confirmPasswordField.getText().isEmpty()){
            this.errorMessageLabel.setText("Please make sure all fields are correctly filled out!");
            this.messageLabel.setText("");
            this.passMessageLabel.setText("");
        }

        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            passMessageLabel.setText("");
        }
        else {
            passMessageLabel.setText("Passwords do not match");
            this.messageLabel.setText("");
        }
    }

    public void registerUser(){
        String sqlInsert = "INSERT INTO login(id,password,representation) VALUES (?,?,?)";

        try{
            Connection connectDB = dbConnection.getConnection();
            PreparedStatement statement = connectDB.prepareStatement(sqlInsert);

            statement.setString(1, this.idNumberTextField.getText());
            statement.setString(2, this.setPasswordField.getText());
            statement.setString(3, this.selectionComboBox.getValue().toString());




            statement.execute();
            messageLabel.setText("User has been registered successfully!");
            errorMessageLabel.setText("");
            passMessageLabel.setText("");
            connectDB.close();


        } catch (Exception e){
            e.printStackTrace();
        }






    }
}

