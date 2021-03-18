package Singleton;

import Entities.dailyTasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A dailyTasks Map using the singleton pattern
 */
public class dailyTasksAccess {

    /**
     * The singleton passenger map
     */
    private static ObservableList<dailyTasks> dailyTasks;

    /**
     * Private do nothing constructor
     */
    private dailyTasksAccess() {
    }

    /**
     * Gets the instance of the map
     *
     * @return  the map instance
     */
    public static ObservableList<dailyTasks> getInstance() {
        if (dailyTasks == null) initialize();
        return dailyTasks;
    }

    /**
     * Initialize the daily tasks map with the data in the database
     */
    private static void initialize() {
        if (dailyTasks == null) {
            dailyTasks = FXCollections.observableArrayList();
            try {
                String sql = "SELECT employeeId, fromTime, toTime, task FROM dailyTasks";
                Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    dailyTasks.add(new dailyTasks(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}