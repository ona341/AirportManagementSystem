package Command;
/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */

import java.util.Arrays;
import IO.IOAccess;
import Singleton.AirportAccess;
import Entities.Flight;

/**
 * Allows the user to add a flight to the system.
 */
public class AddFlight implements Command{
    @Override
    public void execute() {
        String number, capacity;
        IOAccess.getInstance().outputString("Adding a new flight...");
        number = IOAccess.getInstance().readString("Enter the flight number:");
        capacity = IOAccess.getInstance().readString("Enter the capacity:");

        IOAccess.getInstance().outputString("Entered:\tNumber: " + number + "\t" + "Capacity: " + capacity);

        if (Arrays.stream(AirportAccess.getInstance().getFlight()).anyMatch(flight -> flight.getFlightNumber().equalsIgnoreCase(number))) {
            IOAccess.getInstance().outputString("A flight with this number already exists in the system");
        }
        else {
            Flight flight = new Flight(null, number, null, null, Integer.parseInt(capacity));
            AirportAccess.getInstance().assignFlightToGate(flight,AirportAccess.getInstance().availableGates().get(0));
            IOAccess.getInstance().outputString("Entities.Flight created.");
        }
    }
}
