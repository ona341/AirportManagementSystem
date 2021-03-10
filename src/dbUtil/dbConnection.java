package dbUtil;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class dbConnection {
    private static final String SQCONN = "jdbc:sqlite:airport.sqlite";

    private static Connection connection;

    public static Connection getConnection() throws SQLException{
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(SQCONN);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }

}
