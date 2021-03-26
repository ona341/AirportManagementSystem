package Singleton;

import Entities.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A list of employees using the singleton pattern
 */
public class EmployeeAccess {

    /**
     * The main list of employees
     */
    private static ObservableList<Employee> employees;
    /**
     * A searchable copy of the list
     */
    private static FilteredList<Employee> searchedEmployees;

    private EmployeeAccess() {}

    /**
     * Get the main list
     */
    public static ObservableList<Employee> getInstance() {
        if (employees == null) initialize();
        return employees;
    }

    /**
     * Get the searchable list
     * THIS LIST IS IMMUTABLE
     */
    public static FilteredList<Employee> getSearchInstance() {
        if (searchedEmployees == null) searchedEmployees = new FilteredList<>(getInstance());
        return searchedEmployees;
    }

    /**
     * Upon initialization, load the employees from the database
     */
    private static void initialize() {
        if (employees == null) {
            employees = FXCollections.observableArrayList();
            try {
                String sql = "SELECT name, id, email, checkin, parkingStall, role FROM login";
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    employees.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getString(6)));
                }

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
