package Command;

import Entities.Passenger;
import IO.IOAccess;
import Singleton.AirportAccess;
import Singleton.PassengerMapAccess;

import javax.management.openmbean.InvalidKeyException;

/**
 * Releases passenger from their stall.
 */
public class FreeStall implements Command {
    @Override
    public void execute() {
        String passenger;
        int stall;
        //Displays the  the required strings for collecting required input
        IOAccess.getInstance().outputString("Releasing passenger from their stall...");
        passenger = IOAccess.getInstance().readString("Enter the id number of the passenger:");
        IOAccess.getInstance().outputString("Entered:\tPassenger number: " + passenger);

        try {
            //Gets the passenger with the idnumber given and gets their stall number if not null
            Passenger p = PassengerMapAccess.getInstance().get(Integer.parseInt(passenger));
            if (p == null) {
                throw new InvalidKeyException("A passenger with that id number was not found.");
            }
            stall = p.getStallLabel();
            //If there is no passenger in the stall throw exception else set the Stall label to empty
            if (stall == -1) {
                throw new IllegalStateException("This passenger is not in a stall.");
            }
            p.setStallLabel(-1);
            //Free the parking stall in the airport with "stall"
            AirportAccess.getInstance().getParkingStalls().freeStall(stall);
            IOAccess.getInstance().outputString("Passenger removed from stall successfully");

        } catch (IllegalArgumentException | IllegalStateException e) {
            IOAccess.getInstance().outputString("Error: " + e.getMessage());
        }
    }
}
