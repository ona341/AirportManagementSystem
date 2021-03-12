package loginapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Singleton.dbConnection;
import javafx.util.Pair;

public class LoginModel {
    Connection connection;

    public static LoginModel user;

    public LoginModel(){
        try{
            this.connection = dbConnection.getConnection();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        if(this.connection == null){
            System.exit(1);
        }
    }


    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public Pair<Boolean,String> Login(String id, String pass) throws Exception{
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "SELECT representation FROM login where id = ? and password = ?";

        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, id);
            pr.setString(2, pass);

            rs = pr.executeQuery();

            return new Pair<>(rs.next(),rs.getString(1));
        }
        catch (SQLException ex) {
            return new Pair<>(false,null);
        }
        finally {
            pr.close();
        }
    }
    public String getID(){
        return user.getID();
    }

}
