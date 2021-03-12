package Command;
import java.util.Map;
import Entities.Passenger;
import Singleton.AirportAccess;
import Singleton.PassengerMapAccess;
import IO.IOAccess;

/**
 * Prints the current state of the system to console output.
 */
public class SystemState implements Command{
    @Override
    public void execute() {
        String outString = "";
        String temp = "";

        /*
        outString += "\nThe Airport system has the following employees registered:\n";
        String temp = "";
        for (Map.Entry<String, Employee> entry : EmployeeMapAccess.getInstance().entrySet()) {
            temp += entry.getValue();
        }
        outString += (temp.equals("")) ? "None\n" : temp;
         */
/**
        outString += "\nThe airport system has the following passengers registered:\n";
        temp = "";
        for (Map.Entry<String, Passenger> entry : PassengerMapAccess.getInstance().entrySet()) {
            temp += entry.getValue();
        }

        outString += (temp.equals("")) ? "None\n" : temp;

        outString += AirportAccess.getInstance();

        IOAccess.getInstance().outputString(outString);
    }
 **/

}}

