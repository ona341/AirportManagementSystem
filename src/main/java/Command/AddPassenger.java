package Command;

/*
  CMPT 270 A5Q5
  @author Blake Stadnyk; 11195866 - BJS645
 */

//import IO.IOAccess;
import Singleton.PassengerMapAccess;
import Entities.Passenger;

/**
 * Allows the user to add a passenger to the system.
 */
/**
public class AddPassenger implements Command{
    @Override

    public void execute() {
        String name, number;
        IOAccess.getInstance().outputString("Adding a new passenger...");
        name = IOAccess.getInstance().readString("Enter the name of the passenger:");
        number = IOAccess.getInstance().readString("Enter the ID number of the passenger:");

        IOAccess.getInstance().outputString("Entered:\tName: " + name + "\t" + "IDNo: " + number);
        if (PassengerMapAccess.getInstance().get(number) == null) {
            Passenger passenger = new Passenger(name, number);
            PassengerMapAccess.getInstance().put(number, passenger);
            IOAccess.getInstance().outputString("Passenger created.");
        }
        else {
            IOAccess.getInstance().outputString("A passenger with this ID number already exists in the system");
        }
    }

}

     **/