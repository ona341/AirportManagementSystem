package Singleton;

import Entities.Employee;
import Entities.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

/**
 * A Employee Map using the singleton pattern.
 */
public class EmployeeMapAccess {

//    /**
//     * The singleton employee map
//     */
//    private static final TreeMap<String, Employee> employees = new TreeMap<>();
//
//    /**
//     * Private do nothing constructor
//     */
//    private EmployeeMapAccess() {}
//
//    /**
//     * Gets the instance of the map.
//     *
//     * @return the map instance
//     */
//    public static TreeMap<String, Employee> getInstance() {
//        return employees;
//    }


    private static ObservableList<Employee> employees;

    private EmployeeMapAccess() {}

    public static ObservableList<Employee> getInstance() {
        if (employees == null) initialize();
        return employees;
    }

    private static void initialize() {
        if (employees == null) {
            employees = FXCollections.observableArrayList();
            try {
                String sql = "SELECT * FROM employees";
                Connection conn = dbConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    employees.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
