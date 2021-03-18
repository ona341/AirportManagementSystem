package loginapp;

import AirlineEmployee.AirlineEmployeeController;
import AirportEmployee.AirportEmployeeController;
import AirportManager.AirportManagerController;
import Passenger.PassengerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    final LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField idNumber;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;

    public void initialize(URL url, ResourceBundle rb) {
        if (this.loginModel.isDatabaseConnected()) {
            this.dbstatus.setText("Connected to Database");
        } else {
            this.dbstatus.setText("Not Connected to Database");
        }
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
                    case "Passenger":
                        passengerLogin();
                        break;
                    case "Airport Employee":
                        airportEmployeeLogin();
                        break;
                    case "Admin":
                        airportManagerLogin();
                        passengerLogin();
                        airportEmployeeLogin();
                        airlineEmployeeLogin();
                        break;

                }
            } else {
                this.loginStatus.setText("Invalid login. Please try again.");

            }

        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }


    public void airportManagerLogin(){
        try{
            Stage userStage = new Stage();
            FXMLLoader airportManLoader = new FXMLLoader();
            Pane airportManRoot = airportManLoader.load(getClass().getResource("/airportManagerFXML.fxml").openStream());

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


    public void airportEmployeeLogin(){

        try{
            Stage userStage = new Stage();
            FXMLLoader airportEmpLoader = new FXMLLoader();
            Pane airportEmpRoot = airportEmpLoader.load(getClass().getResource("/airportEmployeeFXML.fxml").openStream());

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
            Parent airlineEmpRoot = airlineEmpLoader.load(getClass().getResource("/airlineEmployeeFXML.fxml").openStream());

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
            Pane passengerRoot = passengerLoader.load(getClass().getResource("/passengerFXML.fxml").openStream());

            PassengerController passengerController = passengerLoader.getController();

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
