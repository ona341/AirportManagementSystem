package Command;
import IO.IOAccess;
import Singleton.AirportAccess;
/**
 * Allows the user to add a passenger to the system.
 */
public class viewFlights implements Command{
    @Override
    public void execute() {
        String outString = "";

        outString += AirportAccess.getInstance().toString();
        IOAccess.getInstance().outputString(outString);

    }
}
