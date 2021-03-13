package Command;

import AirportManager.AirportManagerController;
import Entities.Employee;
import Entities.Flight;
import Entities.Manager;

import Entities.Passenger;
import Singleton.AirportAccess;
import Singleton.EmployeeMapAccess;
import Singleton.FlightsAccess;
import Singleton.dbConnection;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

import java.sql.*;

/**
 * Allows the manager to add an employee to the system.
 */
public class AddEmployee implements Command{

    private final AirportManagerController airportManagerController;

    public AddEmployee(AirportManagerController airportManagerController) {
        this.airportManagerController = airportManagerController;
    }


//    @Override
//    public void execute() {
//        StringProperty name, number, email, manager;
//        boolean isManager;
//
//        name = employee.getName();
//        number = employee.getNumber();
//        email = employee.getEmail();
//        manager = "yes";
//
//
//        isManager =  manager.equalsIgnoreCase("yes") || manager.equalsIgnoreCase("y") || manager.equalsIgnoreCase("true");
//
//        if (EmployeeMapAccess.getInstance().get(name) == null) {
//            Employee employee = isManager ? new Manager(name, number, email) : new Employee(name, number, email);
//            EmployeeMapAccess.getInstance().put(name, employee);
//        }
//    }

    @FXML
    public void execute() {
        String sql = "INSERT INTO employees(id,name,role) VALUES(?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.setString(1, airportManagerController.employeeId.getText());
            pstmt.setString(2, airportManagerController.employeeName.getText());
            pstmt.setString(3, airportManagerController.employeeRole.getText());

            pstmt.executeUpdate();

            Employee employee = new Employee(airportManagerController.employeeName.getText(),
                    airportManagerController.employeeId.getText(),
                    airportManagerController.employeeRole.getText());

            EmployeeMapAccess.getInstance().add(employee);

            airportManagerController.clearForm(null);

            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
