package Command;/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */

import Entities.Employee;
import Entities.Manager;
import IO.IOAccess;
import Singleton.EmployeeMapAccess;

/**
 * Allows the the manager to add a employee to the system.
 */
public class AddEmployee implements Command{
    @Override
    public void execute() {
        String Manager, name, number;

        IOAccess.getInstance().outputString("Adding a new employee...");

        name = IOAccess.getInstance().readString("Enter the name of the employee:");
        number = IOAccess.getInstance().readString("Enter the ID number of the employee:");
        Manager = IOAccess.getInstance().readString("Is this employee a manager (yes/no):");

        IOAccess.getInstance().outputString("Entered:\tName: " + name + "\t" + "Is a Manager: " + Manager);

        isSurgeon =  Manager.equalsIgnoreCase("yes") || Manager.equalsIgnoreCase("y") || Manager.equalsIgnoreCase("true");

        if (EmployeeMapAccess.getInstance().get(name) == null) {
            Employee employee = isSurgeon ? new Manager(name, number,null,null,0) : new Employee(name, number,null,null,0);
            EmployeeMapAccess.getInstance().put(name, employee);
            IOAccess.getInstance().outputString("Employee created.");
        }
        else {
            IOAccess.getInstance().outputString("A employee with this name already exists in the system.");
        }
    }
}
