package Singleton;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DBConnection connects the system to the database
 * Uses the singleton pattern to ensure there cannot be multiple connections
 */
public class DBConnection {
    /**
     * The connection url
     */
    private static final String SQCONN = "jdbc:sqlite:airport.sqlite";
    /**
     * The actual Connection object
     */
    private static Connection connection;

    /**
     * Gets the connection to the database
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
