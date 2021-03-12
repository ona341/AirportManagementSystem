package loginapp;

import AirlineEmployee.AirlineEmployeeController;
import AirlineManager.AirlineManagerController;
import AirportEmployee.AirportEmployeeController;
import AirportManager.AirportManagerController;
import Passenger.PassengerController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * LoginController
 */
public class LoginController implements Initializable {
    /**
     * LoginMod
     */
    final LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField idNumber;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> selection;
    @FXML
    private Button loginButton;
    @FXML
    private Button registrationButton;
    @FXML
    private Label loginStatus;

    public void initialize(URL url, ResourceBundle rb) {
        if (this.loginModel.isDatabaseConnected()) {
            this.dbstatus.setText("Connected to Database");
        } else {
            this.dbstatus.setText("Not Connected to Database");
        }

        this.selection.setItems(FXCollections.observableArrayList(option.values()));
    }

    @FXML
    public void Login(ActionEvent event){
        try {
            Pair<Boolean,String> login;
            if ((login = this.loginModel.Login(this.idNumber.getText(), this.password.getText())).getKey()) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                switch(login.getValue()){
                    case "Airport Manager":
                        airportManagerLogin();
                        break;
                    case "Airline Manager":
                        airlineManagerLogin();
                        break;
                    case "Passenger":
                        passengerLogin();
                        break;
                    case "Admin":
                        airlineManagerLogin();
                        airportManagerLogin();
                        passengerLogin();
                        break;

                }
            } else {
                this.loginStatus.setText("Invalid login. Please try again.");

            }

        } catch (Exception localException) {

        }
    }


    public void airportManagerLogin(){
        try{
            Stage userStage = new Stage();
            FXMLLoader airportManLoader = new FXMLLoader();
            Pane airportManRoot = airportManLoader.load(getClass().getResource("/AirportManager/airportManagerFXML.fxml").openStream());

            AirportManagerController airportManagerController = airportManLoader.getController();

            Scene scene = new Scene(airportManRoot);
            userStage.setScene(scene);
            userStage.setTitle("Airport Manager Dashboard");
            userStage.setResizable(false);
            userStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public void airlineManagerLogin(){

        try{
            Stage userStage = new Stage();
            FXMLLoader airlineManLoader = new FXMLLoader();
            Pane airlineManRoot = airlineManLoader.load(getClass().getResource("/AirlineManager/airlineManagerFXML.fxml").openStream());

            AirlineManagerController airlineManagerController = airlineManLoader.getController();

            Scene scene = new Scene(airlineManRoot);
            userStage.setScene(scene);
            userStage.setTitle("Airline Manager Dashboard");
            userStage.setResizable(false);
            userStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public void airportEmployeeLogin(){

        try{
            Stage userStage = new Stage();
            FXMLLoader airportEmpLoader = new FXMLLoader();
            Pane airportEmpRoot = airportEmpLoader.load(getClass().getResource("/AirportEmployee/airportEmployeeFXML.fxml").openStream());

            AirportEmployeeController airportEmployeeController = airportEmpLoader.getController();

            Scene scene = new Scene(airportEmpRoot);
            userStage.setScene(scene);
            userStage.setTitle("Airport Employee Dashboard");
            userStage.setResizable(false);
            userStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }


    }
    public void airlineEmployeeLogin(){

        try{
            Stage userStage = new Stage();
            FXMLLoader airlineEmpLoader = new FXMLLoader();
            Pane airlineEmpRoot = airlineEmpLoader.load(getClass().getResource("/AirlineEmployee/airlineEmployeeFXML.fxml").openStream());

            AirlineEmployeeController airlineEmployeeController = airlineEmpLoader.getController();

            Scene scene = new Scene(airlineEmpRoot);
            userStage.setScene(scene);
            userStage.setTitle("Airline Employee Dashboard");
            userStage.setResizable(false);
            userStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public void passengerLogin(){
        try{
            Stage userStage = new Stage();
            FXMLLoader passengerLoader = new FXMLLoader();
            Pane passengerRoot = (Pane)passengerLoader.load(getClass().getResource("/Passenger/passengerFXML.fxml").openStream());

            PassengerController passengerController = (PassengerController)passengerLoader.getController();

            Scene scene = new Scene(passengerRoot);
            userStage.setScene(scene);
            userStage.setTitle("Passenger Dashboard");
            userStage.setResizable(false);
            userStage.show();

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }
    public String getIdNumber(){
        return this.idNumber.getText();
    }

}
