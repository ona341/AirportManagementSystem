package Command;

import IO.IOAccess;
import Singleton.AirportAccess;

/**
 * Allows the user to add a flight to the system.
 */
public class RemoveFlight implements Command{
    @Override
    public void execute() {
        String number = IOAccess.getInstance().readString("Enter the flight number to remove:");
        int flightIndex = AirportAccess.getInstance().getFlightInternalIndex(number);

        if (flightIndex != -1) {
            AirportAccess.getInstance().removeFlightFromGate(flightIndex);
            IOAccess.getInstance().outputString("Flight with this number has been deleted from the system.");
        }
        else {
            IOAccess.getInstance().outputString("Flight with this number does not exist in the system.");
        }
    }
}
