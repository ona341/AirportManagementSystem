package Command;/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */

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

        IOAccess.getInstance().outputString("Releasing passenger from their stall...");
        passenger = IOAccess.getInstance().readString("Enter the id number of the passenger:");
        IOAccess.getInstance().outputString("Entered:\tPassenger number: " + passenger);

        try {
            Passenger p = PassengerMapAccess.getInstance().get(Integer.parseInt(passenger));
            if (p == null) {
                throw new InvalidKeyException("A passenger with that id number was not found.");
            }
            stall = p.getParkingStallLabel();
            if (stall == -1) {
                throw new IllegalStateException("This passenger is not in a stall.");
            }
            p.setParkingStallLabel(-1);
            AirportAccess.getInstance().getParkingStalls().freeStall(stall);
            IOAccess.getInstance().outputString("Passenger removed from stall successfully");

        } catch (IllegalArgumentException | IllegalStateException e) {
            IOAccess.getInstance().outputString("Error: " + e.getMessage());
        }
    }
}
