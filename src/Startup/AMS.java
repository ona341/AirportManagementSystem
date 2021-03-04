package Startup;

/*
  CMPT 370 Airport Management System
  @author Blake Stadnyk; 11195866
 */

import java.util.Map;

import Entities.Passenger;
import IO.IOAccess;
import Singleton.AirportAccess;
import Singleton.PassengerMapAccess;
import Command.*;

/**
 * Models an Airport management program.
 * Methods to control the operation of the system
 */
public class AMS {
    /**
     * Instantiates a new Airport System.
     */
    AMS() {
        String AirportName;
        int firstLabel, lastLabel;

        IOAccess.getInstance().outputString("Welcome to the Airport System");
        IOAccess.getInstance().outputString("Please complete the required info to begin.");
        do  {
            AirportName = IOAccess.getInstance().readString("Enter the name to use for the parking lot:");
            firstLabel = IOAccess.getInstance().readInt("Enter the label for the first stall in the Entities.Flight");
            lastLabel = IOAccess.getInstance().readInt("Enter the label for the first stall in the Entities.Flight");

            IOAccess.getInstance().outputString("Entered:\tEntities.PersonContainer name: " + AirportName + "\tFirst stall label: " + firstLabel + "\tLast stall label: " + lastLabel);

            try {
                AirportAccess.initialize(AirportName, firstLabel, lastLabel);
                return;
            }
            catch (IllegalArgumentException | IllegalStateException e) {
                IOAccess.getInstance().outputString(e.getMessage());
            }
        } while (true);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new AMS();
        while(true) {
            IOAccess.getInstance().outputString("MAIN MENU: Make a selection from the following options:");
            String[] choices = {"\t0: quit",
                                "\t1: add a new passenger to the system",
                                "\t2: add a new flight to the system",
                                "\t3: view the flights in the system",
                                "\t4: display the empty stalls in the Entities.Flight",
                                "\t5: assign a passenger a stall",
                                "\t6: release a passenger",
                                "\t7: drop employee-passenger association",
                                "\t8: display the current state of the system"};

            int choice = IOAccess.getInstance().readChoice(choices);

            Command[] commands = new Command[9];
            //commands[0] = new SystemState();
            commands[1] = new AddPassenger();
            commands[2] = new AddFlight();
            commands[3] = new viewFlights();

            //commands[3] = new AssignEmployeeToPassenger();
            //commands[4] = new DisplayEmptyStalls();
            //commands[5] = new AssignStall();
            //commands[6] = new ReleasePassenger();
            //commands[7] = new DropAssociation();
            //commands[8] = new SystemState();

            commands[choice].execute();
            if (choice == 0) {
                IOAccess.getInstance().outputString("Goodbye");
                return;
            }
        }
    }

    /**
     * Return a String representation of the properties of the airport system.
     *
     * @return a String representation of the properties of the airport system
     */
    public String toString() {
      String outString = "";
      String temp = "";

      /*
      outString += "\nThe Airport system has the following employees registered:\n";
        temp = "";
      for (Map.Entry<String, Employee> entry : EmployeeMapAccess.getInstance().entrySet()) {
          temp += entry.getValue();
      }
      outString += (temp.equals("")) ? "None\n" : temp;
       */

      outString += "\nThe airport system has the following passengers registered:\n";
      temp = "";
      for (Map.Entry<String, Passenger> entry : PassengerMapAccess.getInstance().entrySet()) {
          temp += entry.getValue();
      }

      outString += (temp.equals("")) ? "None\n" : temp;

      //outString += ParkingAccess.getInstance();

      return outString;
    }
}
