package AirportManager;

import Command.*;
import Entities.Employee;
import Entities.Flight;
import Entities.Passenger;
import Entities.User;
import PopoutControllers.AddTasks;
import PopoutControllers.FlightInfo;
import PopoutControllers.ModifyEmployeeInformation;
import Singleton.EmployeeAccess;
import Singleton.FlightsAccess;
import Singleton.PassengerAccess;
import Singleton.UserAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import Login.Option;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * The Airport manager controller.
 */
public class AirportManagerController implements Initializable{
    // # # # FLIGHT TAB # # #
    // ADD FLIGHT
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
    public Spinner<Integer> capacity;

    // Flight Table
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

    // MISC
    @FXML
    public Button logoutbutton;
    @FXML
    public TextField searchBox;

    // # # # User Management Tab # # #
    // Add Users
    @FXML
    private TextField usersName;
    @FXML
    private TextField idNumberTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    public ComboBox<Option> selectionComboBox;
    @FXML
    public TextField employeeRoleTextField;
    @FXML
    private Label messageLabel;
    @FXML
    private Label passMessageLabel;
    @FXML
    private Label errorMessageLabel;

    // User Table
    @FXML
    public TableView<User> tableviewEmployees;
    @FXML
    public TableColumn<User,String> employeeIdCol;
    @FXML
    public TableColumn<User,String> employeeNameCol;
    @FXML
    public TableColumn<User,String> employeeRoleCol;
    @FXML
    public TextField searchUsers;

    // CheckBoxes
    @FXML
    private RadioButton passengerCheck;
    @FXML
    private RadioButton employeeCheck;
    @FXML
    private RadioButton allUsersCheck;


    /**
     * Logout as the Airport Manager.
     */
    @FXML
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

    /**
     * Checks the format of the inputted time for validity
     */
    @FXML
    private boolean checkFields(MouseEvent event) {
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
     * Shows an error message to the user
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
        if (FlightsAccess.getInstance().stream().anyMatch(flight -> flight.getFlightNumber().equals(flightnum.getText()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Flight with this number already exists!");
            alert.showAndWait();
        }
        else {
            AddFlight addflight = new AddFlight(this);
            if (checkFields(null)) {
                addflight.execute();
            }
        }
    }

    /**
     * Clears the form
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
        selectionComboBox.setItems(FXCollections.observableArrayList(Option.values()));

        employeeRoleTextField.setVisible(false);

        // checks is comboBox selection is 'Airport Employee' to make role text field visible
        selectionComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        employeeRoleTextField.setVisible(newValue.compareTo(Option.AIRPORTEMPLOYEE) == 0);
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

        tableviewEmployees.setItems(UserAccess.getSearchInstance());
        searchUsers.textProperty().addListener((observableValue,oldValue,newValue) -> UserAccess.getSearchInstance().setPredicate(User.search(newValue)));

        // Radio button to filter out everyone that isn't a passenger from the user management table
        passengerCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<User> actualList = tableviewEmployees.getItems();
            FilteredList<User> items = new FilteredList<>(actualList);

            // if checked
            if(newValue){
                Predicate<User> isPassenger = i -> i.getRole().equals("Passenger");
                items.setPredicate(isPassenger);
                tableviewEmployees.setItems(items);
            }
            else{
                tableviewEmployees.setItems(UserAccess.getSearchInstance());
            }
        });

        // Radio button to filter out everyone that isn't a passenger from the user management table
        employeeCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<User> actualList = tableviewEmployees.getItems();
            FilteredList<User> items = new FilteredList<>(actualList);

            // if checked
            if(newValue){
                Predicate<User> isNotPassenger = i -> !i.getRole().equals("Passenger");
                items.setPredicate(isNotPassenger);
                tableviewEmployees.setItems(items);
            }
            else{
                tableviewEmployees.setItems(UserAccess.getSearchInstance());
            }
        });

        // Radio button to view all users
        allUsersCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // if checked
            if(newValue){
                tableviewEmployees.setItems(UserAccess.getSearchInstance());
            }
        });
    }

    /**
     * Register button on action.
     *
     * @param event the event
     */
    public void registerButtonOnAction(ActionEvent event){
        ObservableList<User> userList = tableviewEmployees.getItems();
        boolean duplicateUser = false;

        if(this.idNumberTextField.getText().isEmpty() || this.setPasswordField.getText().isEmpty() || this.confirmPasswordField.getText().isEmpty()){
            this.errorMessageLabel.setText("Please make sure all fields are correctly filled out!");
            this.messageLabel.setText("");
            this.passMessageLabel.setText("");
            return;
        }
        if (this.selectionComboBox.getValue() == null) {
            this.errorMessageLabel.setText("Please select a user type!");
            this.messageLabel.setText("");
            this.passMessageLabel.setText("");
            return;
        }

        if(!this.usersName.getText().matches("[A-Za-z\\s]{2,}")){
            this.errorMessageLabel.setText("Please enter a valid name!");
            this.messageLabel.setText("");
            this.passMessageLabel.setText("");
            return;
        }

        if(setPasswordField.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$") && setPasswordField.getText().equals(confirmPasswordField.getText())){


            Passenger e;
            if (selectionComboBox.getValue().equals(Option.PASSENGER))
                e = new Passenger(usersName.getText(), idNumberTextField.getText(), "");
            else
                e = new Employee(idNumberTextField.getText(), usersName.getText(), "");

            if(selectionComboBox.getValue().toString().compareTo(Option.AIRPORTEMPLOYEE.toString()) == 0) {

                if(employeeRoleTextField.getText().isEmpty())
                    e.setRole(selectionComboBox.getValue().toString());
                else
                    e.setRole(employeeRoleTextField.getText());

            } else {
                e.setRole(selectionComboBox.getValue().toString());
            }


            for(User user : userList){
                if (user.getId().equals(e.getId())) {
                    duplicateUser = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("User with this ID already exists!");
                    alert.showAndWait();
                    messageLabel.setText("");
                    errorMessageLabel.setText("");
                    passMessageLabel.setText("");
                }
            }

            if(!duplicateUser){
                new AddUser(e, setPasswordField.getText().toCharArray()).execute();

                clearUserForm(null);

                messageLabel.setText("User has been registered successfully!");
                errorMessageLabel.setText("");
                passMessageLabel.setText("");
            }
        }
        else {
            passMessageLabel.setText("Please make sure your passwords match and that it contains at least one uppercase, " +
                    "lowercase, and number");
            this.messageLabel.setText("");
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
        if (!(tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
            if (checkFields(null)) {
                Flight theFlight = tableview.getSelectionModel().getSelectedItem();
                theFlight.setAirline(airline.getText());
                theFlight.setDestination(destination.getText());
                theFlight.setDate(Date.valueOf(date.getValue()));
                theFlight.setTime(Time.valueOf(time.getText()));

                new UpdateFlight(theFlight).execute();
                clearForm(null);
            }
        }
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

            loader.<FlightInfo>getController().initialize(flight);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Double click to modify employee.
     *
     * @param event the event
     */
    @FXML
    public void doubleClickEmployee(MouseEvent event) {
        if (event.getClickCount() == 2) {
            User user;
            if ((user = tableviewEmployees.getSelectionModel().getSelectedItem()) != null){
                if (user instanceof Employee) {
                    modifyEmployeeInformation((Employee) user);
                    addTask((Employee) user);
                }
            }
        }
    }

    /**
     * Open modify employee view.
     *
     * @param employee the flight
     */
    public void modifyEmployeeInformation(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyEmployeeInformation.fxml"));

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTasks.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            loader.<AddTasks>getController().initialize(employee);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
