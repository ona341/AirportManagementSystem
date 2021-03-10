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
 * Allows the user to drop association between employee-passenger.
 */
public class DropAssociation implements Command {
    @Override
    public void execute() {
        String passenger, employee;

        IOAccess.getInstance().outputString("Removing employee passenger association...");
        employee = IOAccess.getInstance().readString("Enter the name of the employee:");
        passenger = IOAccess.getInstance().readString("Enter the id number of the passenger:");
        IOAccess.getInstance().outputString("Entered:\tEmployee name: " + employee + "\tPassenger name: " + passenger);

        Passenger p = PassengerMapAccess.getInstance().get(Integer.parseInt(passenger));
        Employee d = EmployeeMapAccess.getInstance().get(employee);

        try {
            //p.removeEmployee(employee);
            d.removePassenger(passenger);
            IOAccess.getInstance().outputString("Association removed successfully");
        } catch (NullPointerException e) {
            IOAccess.getInstance().outputString("Error removing Employee-passenger association: " + ((p == null) ? "No Passenger with that number found" : "No Employee with that name found"));
        } catch (IllegalStateException e) {
            IOAccess.getInstance().outputString("Error: " + e.getMessage());
        }
    }
}
