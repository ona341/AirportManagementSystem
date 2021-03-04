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
        IOAccess.getInstance().outputString("Adding a new flight...");
        String airline = IOAccess.getInstance().readString("Enter the airline:");
        String number = IOAccess.getInstance().readString("Enter the flight number:");
        String destination = IOAccess.getInstance().readString("Enter the destination:");
        int capacity = IOAccess.getInstance().readInt("Enter the capacity:");

        if (AirportAccess.getInstance().hasFlight(number)) {
            IOAccess.getInstance().outputString("A flight with this number already exists in the system");
        }
        else {
            Flight flight = new Flight(airline, number, destination, null, capacity);
            AirportAccess.getInstance().assignFlightToGate(flight,AirportAccess.getInstance().availableGates().get(0));
            IOAccess.getInstance().outputString("Flight created.");
        }
    }
}
