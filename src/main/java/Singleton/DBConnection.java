package Singleton;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DBConnection connects the system to the database
 */
public class DBConnection {
    /**
     * a string instance variable
     */
    private static final String SQCONN = "jdbc:sqlite:airport.sqlite";
    /**
     * a Connection instance variable
     */
    private static Connection connection;

    /**
     * Gets the connection to the database
     * @return a connection class object that is a connection to the database
     * @throws SQLException thrown there is an error accessing the database
     */
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
