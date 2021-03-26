package Startup;


import Entities.DailyTasks;
import Singleton.*;
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

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The main class for starting the Airport Management System
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

    /**
     * On startup this method is called automatically
     * It loads the appropriate splash screen
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        // If the database already exists go straight to login
        if (new File("airport.sqlite").isFile()) {
            root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            initialize();
        }
        // Loads the first time initialization
        else
            root = FXMLLoader.load(getClass().getResource("/AMS.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Airport Management System");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Initialize method if there is no database
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maxgates.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000));
        maxstalls.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000));

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "CREATE TABLE IF NOT EXISTS airport (name TEXT, gateCapacity INT, parkingCapacity INT)";
            conn.createStatement().execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS flights (flightNum TEXT PRIMARY KEY , airline TEXT, destination TEXT, date DATE, time TIME, gate INT, capacity INT)";
            conn.createStatement().execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS login (id TEXT PRIMARY KEY, name TEXT, password TEXT, email TEXT, role TEXT, checkIn DATE, parkingStall INT, seatNumber INT)";
            conn.createStatement().execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS workSchedule (employeeId TEXT PRIMARY KEY , sunday TEXT, monday TEXT, tuesday TEXT, wednesday TEXT, thursday, friday TEXT, saturday TEXT)";
            conn.createStatement().execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS dailyTasks (employeeId TEXT, fromTime TEXT, toTime TEXT, location TEXT, task TEXT)";
            conn.createStatement().execute(sql);
            sql = "CREATE TABLE IF NOT EXISTS passengerFlightRelation (passengerID TEXT, flightNumber TEXT, seatNumber INT)";
            conn.createStatement().execute(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * The initialize method if there is already a database
     * Or after the user has configured the system for the first time
     */
    private void initialize() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT name, gateCapacity, parkingCapacity FROM airport";
            PreparedStatement prpst = conn.prepareStatement(sql);
            ResultSet rs = prpst.executeQuery();

            // Initialize all the data structures
            AirportAccess.initialize(rs.getString(1),1,rs.getInt(2), 1,rs.getInt(3));
            prpst.close();

            FlightsAccess.getInstance();
            PassengerAccess.getInstance();
            EmployeeAccess.getInstance();
            DailyTasksAccess.getInstance();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * When the user configures the application for the first time
     */
    @FXML
    private void initializeAMS(ActionEvent event) {
        try {
            Connection conn = DBConnection.getConnection();

            // Saves the airport parameters
            String sql = "INSERT INTO airport(name,gateCapacity,parkingCapacity) VALUES(?,?,?)";
            PreparedStatement prpst = conn.prepareStatement(sql);
            prpst.setString(1, airportname.getText());
            prpst.setInt(2, maxgates.getValue());
            prpst.setInt(3, maxstalls.getValue());
            prpst.executeUpdate();
            prpst.close();

            // Sets the master login credentials
            sql = "INSERT INTO login(id,password,role) VALUES(?,?,?)";
            prpst = conn.prepareStatement(sql);
            prpst.setString(1,username.getText());
            prpst.setString(2,password.getText());
            prpst.setString(3,"Admin");
            prpst.executeUpdate();
            prpst.close();

            ((Button) event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            start(stage); // Go back to start and load the login page

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Begin the program via JavaFx convention
     */
    public static void main(String[] args) {
        launch(args);
    }

}
