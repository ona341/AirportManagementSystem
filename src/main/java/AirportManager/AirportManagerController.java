package AirportManager;

import Command.*;
import Entities.DailyTasks;
import Entities.Employee;
import Entities.Flight;
import Entities.Passenger;
import FlightView.FlightView;
import Singleton.EmployeeAccess;
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
    @FXML
    public TextField fromTime;
    public TextArea taskToDo;
    public TextField toTime;
    public TextField searchUsers;

    private ObservableList<Flight> flightData;
    private ObservableList<Employee> employeeData;
    private ObservableList<DailyTasks> dailyTasksData;

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
    public TextField nameTextField;
    @FXML
    public ComboBox<option> selectionComboBox;
    @FXML
    public TextField employeeRoleTextField;
    @FXML
    private Label errorMessageLabel;

    @FXML
    public TextField employeeId;
    @FXML
    private TextField usersName;

    @FXML
    public TableView<Employee> tableviewEmployees;
    @FXML
    public TableColumn<Employee,String> employeeIdCol;
    @FXML
    public TableColumn<Employee,String> employeeNameCol;
    @FXML
    public TableColumn<Employee,String> employeeRoleCol;

    @FXML
    public TableView<DailyTasks> tableviewTasks;
    @FXML
    public TableColumn<DailyTasks,String> fromCol;
    @FXML
    public TableColumn<DailyTasks,String> toCol;
    @FXML
    public TableColumn<DailyTasks,String> taskCol;


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
     * @param event an action event
     */
    @FXML
    private boolean checkInvalidFields(MouseEvent event) {
        boolean isValid = true;
        if (flightnum.getText().isEmpty() || airline.getText().isEmpty() || destination.getText().isEmpty() ||
                date.getValue() == null || time.getText().isEmpty()) {
            isValid = false;
            notifyError("the empty field(s)");
        }
        if (!flightnum.getText().matches("[0-9]+")) {
            isValid = false;
            notifyError("a valid flight number");
            flightnum.clear();
        }
        if (time.getText().matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            time.appendText(":00");
        }
        if (!time.getText().matches("^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$")) {
            isValid = false;
            notifyError("a valid time");
            time.clear();
        }
        return isValid;
    }

    /**
     * Checks the format of the inputted time for validity
     */
    private void notifyError(String errorInfo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid " + errorInfo);
        alert.setContentText("Please fill " + errorInfo);
        alert.showAndWait();
    }

    /**
     * Calls and executes the add flight command
     * @param event when the addFlight button is clicked
     */
    @FXML
    public void addFlight(ActionEvent event) {
        AddFlight addflight = new AddFlight(this);
        if (checkInvalidFields(null)) {
            addflight.execute();
        }
    }

    /**
     * Finds the flight with the given input
     * @param event keys entered
     */
//    @FXML
//    private void searchTable(KeyEvent event) {
//        searchBox.textProperty().bind(FlightsAccess.getInstance().filtered(p -> true).predicateProperty().asString());
//        if(searchBox.getText().isBlank()) {
//            tableview.setItems(FlightsAccess.getInstance());
//        }
//        else {
//            tableview.setItems(FlightsAccess.search(searchBox.getText()));
//        }
//    }

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

    @FXML
    public void clearUserForm(ActionEvent event) {
        usersName.clear();
        idNumberTextField.clear();
        setPasswordField.clear();
        confirmPasswordField.clear();
        selectionComboBox.setValue(null);
        employeeRoleTextField.clear();
        employeeRoleTextField.setVisible(false);
    }

    /**
     * Initializes the Controller
     * @param url he location used to resolve the relative paths of the object or null if unknown
     * @param resourceBundle the resources used to localize the root of the object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectionComboBox.setItems(FXCollections.observableArrayList(option.values()));

        employeeRoleTextField.setVisible(false);

        // checks is comboBox selection is 'Airport Employee' to make role text field visible
        selectionComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        if(newValue.compareTo(option.AIRPORTEMPLOYEE) == 0)
                            employeeRoleTextField.setVisible(true);
                        else
                            employeeRoleTextField.setVisible(false);
                    } catch (Exception e) { }
                }
                );

        flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        gateCol.setCellValueFactory(new PropertyValueFactory<>("gate"));

        tableview.setItems(FlightsAccess.getSearchInstance());

        searchBox.textProperty().addListener((observableValue,oldValue,newValue) -> FlightsAccess.getSearchInstance().setPredicate(Flight.search(newValue)));

        capacity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000));

        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableviewEmployees.setItems(EmployeeAccess.getSearchInstance());
        searchUsers.textProperty().addListener((observableValue,oldValue,newValue) -> EmployeeAccess.getSearchInstance().setPredicate(Employee.search(newValue)));
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

            Employee e = new Employee(idNumberTextField.getText(), usersName.getText(), "");

            if(selectionComboBox.getValue().toString().compareTo(option.AIRPORTEMPLOYEE.toString()) == 0) {
                
                if(employeeRoleTextField.getText().isEmpty())
                    e.setRole(selectionComboBox.getValue().toString());
                else
                    e.setRole(employeeRoleTextField.getText());

            } else {
                e.setRole(selectionComboBox.getValue().toString());
            }

            new AddUser(e, setPasswordField.getText().toCharArray()).execute();

            clearUserForm(null);

            messageLabel.setText("User has been registered successfully!");
            errorMessageLabel.setText("");
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
    //public void registerUser(){
        //String sqlInsert = "INSERT INTO login(id,password,representation,name) VALUES (?,?,?,?)";

        //try{
            //Connection connectDB = dbConnection.getConnection();
            //PreparedStatement statement = connectDB.prepareStatement(sqlInsert);

            //statement.setString(1, this.idNumberTextField.getText());
            //statement.setString(2, this.setPasswordField.getText());
            //statement.setString(3, this.selectionComboBox.getValue().toString());
            //statement.setString(4, this.nameTextField.getText());

            //Employee e = new Employee(idNumberTextField.getText(), usersName.getText(), selectionComboBox.getValue().toString());
            //new AddUser(e, setPasswordField.getText().toCharArray()).execute();

            //statement.execute();
            //messageLabel.setText("User has been registered successfully!");
            //errorMessageLabel.setText("");
            //passMessageLabel.setText("");
            //statement.close();


        //} catch (Exception e){
           // e.printStackTrace();
       // }
    //}


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

            rs.close(); //closes the database connection

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
     * Loads the flights in the system into the panel to be viewed
     */
    public void loadDailyTasksData() {

        try {

            Connection conn = dbConnection.getConnection();
            this.dailyTasksData = FXCollections.observableArrayList();  //sets the flight data attribute to be the observableArrayList from FXCollections

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM dailyTasks");

            while(rs.next()) {
                this.dailyTasksData.add(new DailyTasks(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            rs.close(); //closes the database connection

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        this.toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        this.taskCol.setCellValueFactory(new PropertyValueFactory<>("tasks"));

        this.tableviewTasks.setItems(null);
        this.tableviewTasks.setItems(this.dailyTasksData);
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
            if (!(selectedEmployee = tableviewEmployees.getSelectionModel().getSelectedItems()).isEmpty()){
                for (Employee employee : selectedEmployee) {
                    if (!(employee.getRole().equals("Passenger"))) {
                        modifyEmployeeInformation(selectedEmployee.get(0));
                        addTask(selectedEmployee.get(0));

                    }
                }
            }
        }
    }



    /**
     * Open flight view.
     *
     * @param employee the flight
     */
    public void modifyEmployeeInformation(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifyEmployeeInformation.fxml"));

            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            loader.<ModifyEmployeeInformation>getController().initialize(employee);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open daily task window.
     *
     * @param employee the employee to look at the daily task
     */
    public void addTask(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addTasks.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            loader.<AddTasks>getController().initialize(employee);
            loader.<AddTasks>getController().execute();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
