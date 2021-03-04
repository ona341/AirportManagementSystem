package loginapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();

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

    public void initialize(URL url, ResourceBundle rb){
        if(this.loginModel.isDatabaseConnected()){
            this.dbstatus.setText("Connected to Database");
        }
        else{
            this.dbstatus.setText("Not Connected to Database");
        }

        this.selection.setItems(FXCollections.observableArrayList(option.values()));


    }

}
