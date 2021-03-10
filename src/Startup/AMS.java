package Startup;

/*
  CMPT 370 Airport Management System
  @author Blake Stadnyk; 11195866
 */

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Singleton.AirportAccess;
import dbUtil.dbConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Models an Airport management program.
 * Methods to control the operation of the system
 */
public class AMS extends Application implements Initializable{
    @FXML
    TextField airportname;
    @FXML
    Spinner<Integer> maxgates;
    @FXML
    Spinner<Integer> maxstalls;
    @FXML
    TextField username;
    @FXML
    TextField password;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        if (new File("airport.sqlite").isFile()) {
            root = FXMLLoader.load(getClass().getResource("/loginapp/login.fxml"));
            initialize();
        }
        else
            root = FXMLLoader.load(getClass().getResource("AMS.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Airport Management System");
        stage.setResizable(false);
        stage.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        maxgates.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000));
        maxstalls.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000));

        try {
            Connection conn = dbConnection.getConnection();
            String sql = "CREATE TABLE IF NOT EXISTS airport (name TEXT, gateCapacity INT, parkingCapacity INT)";
            conn.createStatement().execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS flights (flightNum TEXT, airline TEXT, destination TEXT, date DATE, time TIME, gate INT)";
            conn.createStatement().execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS login (id TEXT, password TEXT, representation TEXT)";
            conn.createStatement().execute(sql);

            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    private void initialize() {
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT name, gateCapacity, parkingCapacity FROM airport";
            PreparedStatement prpst = conn.prepareStatement(sql);
            ResultSet rs = prpst.executeQuery();

            AirportAccess.initialize(rs.getString(1),0,rs.getInt(2));

            conn.close();
            prpst.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void initializeAMS(ActionEvent event) {
        try {
            Connection conn = dbConnection.getConnection();

            String sql = "INSERT INTO airport(name,gateCapacity,parkingCapacity) VALUES(?,?,?)";
            PreparedStatement prpst = conn.prepareStatement(sql);
            prpst.setString(1, airportname.getText());
            prpst.setInt(2, maxgates.getValue());
            prpst.setInt(3, maxstalls.getValue());
            prpst.executeUpdate();
            prpst.close();

            sql = "INSERT INTO login(id,password,representation) VALUES(?,?,?)";
            prpst = conn.prepareStatement(sql);
            prpst.setString(1,username.getText());
            prpst.setString(2,password.getText());
            prpst.setString(3,"Admin");
            prpst.executeUpdate();

            conn.close();

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
            Stage stage = new Stage();
            start(stage);

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);

        //new AMS();
        //while(true) {
         //   IOAccess.getInstance().outputString("MAIN MENU: Make a selection from the following options:");
        //    String[] choices = {"\t0: quit",
         //                       "\t1: add a new passenger to the system",
         //                       "\t2: add a new flight to the system",
        //                        "\t3: view the flights in the system",
          //                      "\t4: display the empty stalls in the Entities.Flight",
         //                       "\t5: assign a passenger a stall",
          //                      "\t6: release a passenger",
          //                      "\t7: drop employee-passenger association",
         //                       "\t8: display the current state of the system"};

         //   int choice = IOAccess.getInstance().readChoice(choices);

         //   Command[] commands = new Command[9];
            //commands[0] = new SystemState();
           // commands[1] = new AddPassenger();
           // commands[2] = new AddFlight();
           // commands[3] = new viewFlights();

            //commands[3] = new AssignEmployeeToPassenger();
            //commands[4] = new DisplayEmptyStalls();
            //commands[5] = new AssignStall();
            //commands[6] = new ReleasePassenger();
            //commands[7] = new DropAssociation();
            //commands[8] = new SystemState();

         //   commands[choice].execute();
         //   if (choice == 0) {
         //       IOAccess.getInstance().outputString("Goodbye");
          //      return;
          //  }
       // }
    }

}
