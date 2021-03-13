package Command;/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */
/*
import Entities.Employee;
import Entities.Manager;
import IO.IOAccess;
import Singleton.EmployeeMapAccess;

/**
 * Allows the manager to add an employee to the system.
 */
/*
public class AddEmployee implements Command{
    @Override
    public void execute() {
        String name, number, email, manager;
        boolean isManager;

        IOAccess.getInstance().outputString("Adding a new employee...");

        name = IOAccess.getInstance().readString("Enter the name of the employee:");
        number = IOAccess.getInstance().readString("Enter the ID number of the employee:");
        email = IOAccess.getInstance().readString("Enter the email of the employee:");
        manager = IOAccess.getInstance().readString("Is this employee a manager? (y/n)");

        IOAccess.getInstance().outputString("Entered:\tName: " + name + "\t" + "Is a Manager: " + manager);

        isManager =  manager.equalsIgnoreCase("yes") || manager.equalsIgnoreCase("y") || manager.equalsIgnoreCase("true");

        if (EmployeeMapAccess.getInstance().get(name) == null) {
            Employee employee = isManager ? new Manager(name, number, email) : new Employee(name, number, email);
            EmployeeMapAccess.getInstance().put(name, employee);
            IOAccess.getInstance().outputString("Employee created.");
        }
        else {
            IOAccess.getInstance().outputString("A employee with this name already exists in the system.");
        }
    }
}
*/