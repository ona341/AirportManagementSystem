package AirportManager;

import Command.AddFlight;
import Command.DeleteFlight;
import Command.UpdateFlight;
import Entities.Flight;
import FlightView.FlightView;
import Singleton.FlightsAccess;
import dbUtil.dbConnection;
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

    private ObservableList<Flight> data;

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



    /*public void toRegistration(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 491));
            registerStage.setTitle("Registration");
            registerStage.setResizable(false);
            registerStage.show();

        } catch (
                Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }*/

    @FXML
    public void logout(ActionEvent event) throws IOException
    {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("/loginapp/login.fxml"));
        Scene loginViewScene = new Scene(loginViewParent);
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginViewScene);
        window.show();
    }

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

    @FXML
    public void addFlight(ActionEvent event) {
        AddFlight addflight = new AddFlight(this);
        addflight.execute();
    }

    @FXML
    private void searchTable(KeyEvent event) {
        if(searchBox.getText().isBlank()) {
            tableview.setItems(FlightsAccess.getInstance());
        }
        else {
            tableview.setItems(FlightsAccess.search(searchBox.getText()));
        }
    }


    @FXML
    public void clearForm(ActionEvent event) {
        flightnum.clear();
        airline.clear();
        destination.clear();
        time.clear();
        date.setValue(null);
    }


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
            statement.close();


        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public void deleteRow(ActionEvent actionEvent) {
        ObservableList<Flight> selectedFlights;
        if (!(selectedFlights = tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
            DeleteFlight deleteflight = new DeleteFlight(selectedFlights);
            deleteflight.execute();
        }
    }


    public void updateRow(ActionEvent actionEvent) throws SQLException {
        ObservableList<Flight> selectedFlights;
        if (!(selectedFlights = tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
            UpdateFlight updateFlight = new UpdateFlight(selectedFlights, this);
            updateFlight.execute();
        }
    }

    public void loadFLightData() throws SQLException {

        try {

            Connection connection = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM flights");

            while(rs.next()) {
                this.data.add(new Flight(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getTime(5), rs.getInt(6)));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.flightCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        this.airlineCol.setCellValueFactory(new PropertyValueFactory<>("airline"));
        this.destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        this.tableview.setItems(null);
        this.tableview.setItems(this.data);
    }
    @FXML
    public void doubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<Flight> selectedFlights;
            if (!(selectedFlights = tableview.getSelectionModel().getSelectedItems()).isEmpty()) {
                openFlightView(selectedFlights.get(0));
            }
        }
    }


    public void openFlightView(Flight flight) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FlightView/FlightView.fxml"));
            Stage stage = new Stage();
            Parent root = loader.load();
            stage.setScene(new Scene(root));


            loader.<FlightView>getController().initialize(flight);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
