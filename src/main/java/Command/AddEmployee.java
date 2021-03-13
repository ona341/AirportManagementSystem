package Command;

import Entities.Employee;
import Entities.Manager;

import Singleton.EmployeeMapAccess;

/**
 * Allows the manager to add an employee to the system.
 */
public class AddEmployee implements Command{

    private final Employee employee;

    public AddEmployee(Employee employee) {
        this.employee = employee;
    }


    @Override
    public void execute() {
        String name, number, email, manager;
        boolean isManager;

        name = employee.getName();
        number = employee.getNumber();
        email = employee.getEmail();
        manager = "yes";


        isManager =  manager.equalsIgnoreCase("yes") || manager.equalsIgnoreCase("y") || manager.equalsIgnoreCase("true");

        if (EmployeeMapAccess.getInstance().get(name) == null) {
            Employee employee = isManager ? new Manager(name, number, email) : new Employee(name, number, email);
            EmployeeMapAccess.getInstance().put(name, employee);
        }
    }
}
