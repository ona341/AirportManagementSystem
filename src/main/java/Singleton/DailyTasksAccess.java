package Singleton;

import Entities.DailyTasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A DailyTasks list using the singleton pattern
 */
public class DailyTasksAccess {

    /**
     * The list of tasks
     */
    private static ObservableList<DailyTasks> dailyTasks;

    /**
     * Private do nothing constructor
     */
    private DailyTasksAccess() {
    }

    /**
     * Gets the list
     */
    public static ObservableList<DailyTasks> getInstance() {
        if (dailyTasks == null) initialize();
        return dailyTasks;
    }

    /**
     * Initialize the daily tasks list from the database
     */
    private static void initialize() {
        if (dailyTasks == null) {
            dailyTasks = FXCollections.observableArrayList();
            try {
                String sql = "SELECT employeeId, fromTime, toTime, location, task FROM dailyTasks";
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    dailyTasks.add(new DailyTasks(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}