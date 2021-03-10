package Command;/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */

import Entities.Passenger;
import IO.IOAccess;
import Singleton.ParkingAccess;
import Singleton.PassengerMapAccess;

import javax.management.openmbean.InvalidKeyException;

/**
 * Allows the user to assign a stall to a passenger.
 */
public class AssignStall implements Command {
    @Override
    public void execute() {
        String passenger;
        int stall;

        IOAccess.getInstance().outputString("Assigning passenger to a stall...");
        passenger = IOAccess.getInstance().readString("Enter the id number of the passenger:");
        stall = IOAccess.getInstance().readInt("Enter the stall label where you want to place the passenger:");
        IOAccess.getInstance().outputString("Entered:\tPassenger number: " + passenger + "\tStall label: " + stall);

        try {
            Passenger p = PassengerMapAccess.getInstance().get(Integer.parseInt(passenger));
            if (p == null) {
                throw new InvalidKeyException("A passenger with that id number was not found.");
            }
            ParkingAccess.getInstance().assignPassengerToStall(p, stall);
            p.setStallLabel(stall);
            IOAccess.getInstance().outputString("Passenger assigned to stall successfully");

        } catch (IllegalStateException | IllegalArgumentException e) {
            IOAccess.getInstance().outputString("Error: " + e.getMessage());
        }
    }
}
