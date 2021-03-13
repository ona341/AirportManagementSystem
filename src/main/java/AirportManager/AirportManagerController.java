package AirportManager;

import Command.AddFlight;
import Command.DeleteFlight;
import Command.UpdateFlight;
import Command.ViewEmployeeSchedule;
import Entities.Employee;
import Entities.Flight;
import FlightView.FlightView;
import Singleton.EmployeeMapAccess;
import Singleton.FlightsAccess;
import Singleton.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import loginapp.option;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * The Airport manager controller.
 */
public class AirportManagerController implements Initializable{

    @FXML
    public TextField flightnum;
    @FXML
    public DatePicker date;
    @FXML
    public TextField airline;
    @FXML
    public TextField destination;
    @FXML
    public TextField time;
    @FXML
    public Button logoutbutton;
    @FXML
    public TableView<Flight> tableview;
    @FXML
    public TableColumn<Flight,String> flightCol;
    @FXML
    public TableColumn<Flight,String> airlineCol;
    @FXML
    public TableColumn<Flight,String> destinationCol;
    @FXML
    public TableColumn<Flight,Date> dateCol;
    @FXML
    public TableColumn<Flight,Time> timeCol;
    @FXML
    public TableColumn<Flight,Character> gateCol;
    @FXML
    public TextField searchBox;
    @FXML
    public Spinner<Integer> capacity;

    private ObservableList<Flight> flightData;

    // Add User Tab

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
    @FXML
    private Label errorMessageLabel;


    @FXML
    public TextField employeeId;
    @FXML
    public TextField employeeName;
    @FXML
    public TextField employeeRole;
    @FXML
    public TableView<Employee> tableviewEmployees;
    @FXML
    public TableColumn<Employee,String> employeeIdCol;
    @FXML
    public TableColumn<Employee,String> employeeNameCol;
    @FXML
    public TableColumn<Employee,String> employeeRoleCol;


    /**
     * Logout as the Airport Manager.
     *
     * @param event the button event to log out
     * @throws IOException
     */
    @FXML
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

    /**
     * Checks the format of the inputted time for validity
     * @param event a Mouse event
     */
    @FXML
    private void checkTime(MouseEvent event) {
        if (time.getText().matches("^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$")) {
        }
        else if (time.getText().matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"))
            time.appendText(":00");
        else{
            time.clear();
        }
    }

    /**
     * Calls and executes the add flight command
     * @param event when the addFlight button is clicked
     */
    @FXML
    public void addFlight(ActionEvent event) {
        AddFlight addflight = new AddFlight(this);
        addflight.execute();
    }

    /**
     * Finds the flight with the given input
     * @param event keys entered
     */
    @FXML
    private void searchTable(KeyEvent event) {
        if(searchBox.getText().isBlank()) {
            tableview.setItems(FlightsAccess.getInstance());
        }
        else {
            tableview.setItems(FlightsAccess.search(searchBox.getText()));
        }
    }

    /**
     * Clears the form
     *
     * @param event an action performed by the user
     */
    @FXML
    public void clearForm(ActionEvent event) {
        flightnum.clear();
        airline.clear();
        destination.clear();
        time.clear();
        date.setValue(null);
    }

    /**
     * Initializes the Controller
     * @param url he location used to resolve the relative paths of the object or null if unknown
     * @param resourceBundle the resources used to localize the root of the object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.selectionComboBox.setItems(FXCollections.observableArrayList(option.values()));


        flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));

        tableview.setItems(FlightsAccess.getInstance());

        capacity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000));



        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableviewEmployees.setItems(EmployeeMapAccess.getInstance());
    }

    /**
     * Register button on action.
     *
     * @param event the event
     */
    public void registerButtonOnAction(ActionEvent event){
        if(this.idNumberTextField.getText().isEmpty() || this.setPasswordField.getText().isEmpty() ||
                this.confirmPasswordField.getText().isEmpty()){
            this.errorMessageLabel.setText("Please make sure all fields are correctly filled out!");
            this.messageLabel.setText("");
            this.passMessageLabel.setText("");
        }

        if(setPasswordField.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$") &&
                setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            passMessageLabel.setText("");
        }
        else {
            passMessageLabel.setText("Please make sure your passwords match and that it contains at least one uppercase, " +
                    "lowercase, and number");
            this.messageLabel.setText("");
        }
    }

    /**
     * Register user.
     */
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
            statement.close();


        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Deletes the selected flight from the  system
     *
     * @param actionEvent the user selecting delete flight
     */
    public void deleteRow(ActionEvent actionEvent) {
        ObservableList<Flight> selectedFlights;
        if (!(selectedFlights = tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
            DeleteFlight deleteflight = new DeleteFlight(selectedFlights);
            deleteflight.execute();
        }
    }

    /**
     * Updates the flight row selected
     *
     * @param actionEvent an action performed by the user
     */
    public void updateRow(ActionEvent actionEvent) {
        ObservableList<Flight> selectedFlights;
        if (!(selectedFlights = tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
            UpdateFlight updateFlight = new UpdateFlight(selectedFlights, this);
            updateFlight.execute();
        }
    }

    /**
     * Loads the flights in the system into the panel to be viewed
     */
    public void loadFLightData() {

        try {

            Connection conn = dbConnection.getConnection();
            this.flightData = FXCollections.observableArrayList();  //sets the flight data attribute to be the observableArrayList from FXCollections

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM flights");

            while(rs.next()) {
                this.flightData.add(new Flight(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getTime(5), rs.getInt(6), rs.getInt(7)));
            }

            conn.close(); //closes the database connection

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        this.airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        this.destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));

        this.tableview.setItems(null);
        this.tableview.setItems(this.flightData);
    }

    /**
     * Double click.
     *
     * @param event the event
     */
    @FXML
    public void doubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<Flight> selectedFlights;
            if (!(selectedFlights = tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
                openFlightView(selectedFlights.get(0));
            }
        }
    }


    /**
     * Open flight view.
     *
     * @param flight the flight
     */
    public void openFlightView(Flight flight) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FlightView.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));


            loader.<FlightView>getController().initialize(flight);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Double click.
     *
     * @param event the event
     */
    @FXML
    public void doubleClickEmployee(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<Employee> selectedEmployee;
            if (!(selectedEmployee = tableviewEmployees.getSelectionModel().getSelectedItems()).isEmpty()) {
                viewEmployeeSchedule(selectedEmployee.get(0));
            }
        }
    }


    /**
     * Open flight view.
     *
     * @param employee the flight
     */
    public void viewEmployeeSchedule(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewEmployeeSchedule.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));


            loader.<ViewEmployeeSchedule>getController().initialize(employee);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
