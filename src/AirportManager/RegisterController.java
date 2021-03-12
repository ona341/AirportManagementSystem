package AirportManager;

import dbUtil.dbConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;
import javafx.stage.Stage;
import loginapp.option;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController {
    //Instances for GUI control
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

    /**
     * Initializes the Regiter controller
     */
    public void initialize() {
        this.selectionComboBox.setItems(FXCollections.observableArrayList(option.values())); //sets the items in selectionComboBox to the observableArrayList of option values
    }

    /**
     * Closes the add user window
     * @param event the close button is clicked
     */
    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Verifies all the user inputs and registers the user if so
     * @param event the register button is clicked
     */
    public void registerButtonOnAction(ActionEvent event){
        //displays error message if any input value format is wrong
        if(this.idNumberTextField.getText().isEmpty() || this.setPasswordField.getText().isEmpty() ||
                this.confirmPasswordField.getText().isEmpty()){
            this.errorMessageLabel.setText("Please make sure all fields are correctly filled out!");
            this.messageLabel.setText("");
            this.passMessageLabel.setText("");
        }
        //registers the user if the passwordField and confirm password fields match
        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            passMessageLabel.setText("");
        }
        //displays "Passwords do not match text"
        else {
            passMessageLabel.setText("Passwords do not match");
            this.messageLabel.setText("");
        }
    }

    /**
     * Adds a user to the system
     */
    public void registerUser(){
        String sqlInsert = "INSERT INTO login(id,password,representation) VALUES (?,?,?)";

        try{
            Connection connectDB = dbConnection.getConnection(); //opens the database connection
            PreparedStatement statement = connectDB.prepareStatement(sqlInsert);
            //sets the statements indexes to their respective fields
            statement.setString(1, this.idNumberTextField.getText());
            statement.setString(2, this.setPasswordField.getText());
            statement.setString(3, this.selectionComboBox.getValue().toString());


            statement.execute();//executes statement
            messageLabel.setText("User has been registered successfully!");
            errorMessageLabel.setText("");
            passMessageLabel.setText("");
            connectDB.close(); //closes the database connection


        } catch (Exception e){
            e.printStackTrace();
        }






    }
}

