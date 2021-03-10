package Command;/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */

import Entities.Employee;
import Entities.Passenger;
import IO.IOAccess;
import Singleton.EmployeeMapAccess;
import Singleton.PassengerMapAccess;

/**
 * Allows the user to assign a Employee to a Passenger.
 * Passenger is also assigned to the Employee
 */
public class AssignEmployeeToPassenger implements Command {
    @Override
    public void execute() {
        String passenger, employee;

        IOAccess.getInstance().outputString("Assigning employee to passenger...");

        employee = IOAccess.getInstance().readString("Enter the name of the employee:");
        passenger = IOAccess.getInstance().readString("Enter the id number of the passenger:");

        IOAccess.getInstance().outputString("Entered:\tEmployee name: " + employee + "\tPassenger name: " + passenger);

        Passenger p = PassengerMapAccess.getInstance().get(Integer.parseInt(passenger));
        Employee d = EmployeeMapAccess.getInstance().get(employee);

        try {
            //p.addEmployee(d);
            d.addPassenger(p);
            IOAccess.getInstance().outputString("Assignment successful");
        } catch (NullPointerException e) {
            IOAccess.getInstance().outputString("Error assigning Employee to passenger: " + ((p == null) ? "No Passenger with that number found" : "No Employee with that name found"));
        } catch (IllegalStateException e) {
            IOAccess.getInstance().outputString(e.getMessage());
        }
    }
}
