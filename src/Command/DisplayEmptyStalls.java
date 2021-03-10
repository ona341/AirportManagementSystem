package Command;/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */

import IO.IOAccess;
import Singleton.ParkingAccess;

/**
 * Display empty stalls.
 */
public class DisplayEmptyStalls implements Command {
    public void execute() {
        IOAccess.getInstance().outputString("The empty stalls are: ");
        IOAccess.getInstance().outputString(ParkingAccess.getInstance().availableStalls().toString());
    }
}
