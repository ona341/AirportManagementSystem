package Entities;/*
   CMPT 270 A5
   @author Blake Stadnyk; 11195866 - BJS645
 */

import java.util.ArrayList;

/**
 * A Entities.Passenger who extends from person and has a list of Flights.
 */
public class Passenger extends Person {

  /**
   * The Stall label.
   */
  private int stallLabel = -1;

  /**
   * The list of Flights.
   */
  private final ArrayList<Flight> flights = new ArrayList<>();

  /**
   * Instantiates a new Entities.Passenger with the given name and id number.
   *
   * @param name   the name of the passenger
   * @param number the number of the passenger
   */
  public Passenger(String name, String number) {
    super(name, number);
  }

  /**
   * Gets stall label.
   *
   * @return the stall label
   */
  public int getStallLabel() {
    return stallLabel;
  }

  /**
   * Sets stall label.
   *
   * @param stallLabel the stall label
   * @precond (stallLabel == -1)
   */
  public void setStallLabel(int stallLabel) {
    if (this.stallLabel != -1 && stallLabel != -1) {
      throw new IllegalStateException("Error: This passenger is already assigned to a stall");
    } else {
      this.stallLabel = stallLabel;
    }
  }

  /**
   * Add flight.
   *
   * @param f the Entities.Flight
   * @precond (f != null) && (!hasFlight(f.getName()))
   */
  public void addFlight(Flight f) {
    if (hasFlight(f.getFlightNumber())) {
      throw new IllegalStateException("Error: Attempting to create an association that already exists");
    } else {
      flights.add(f);
    }
  }

  /**
   * Remove flight.
   *
   * @param number the number of the Entities.Flight
   * @precond Entities.Flight association must exist
   */
  public void removeFlight(String number) {
    if (!flights.removeIf(flight -> flight.getFlightNumber().equals(number))) {
      throw new IllegalStateException("Error: Attempting to remove an association that does not exist");
    }
  }

  /**
   * Has flight boolean.
   *
   * @param number the number of the Entities.Flight
   * @return True if the passenger has a Entities.Flight with this name
   */
  public boolean hasFlight(String number) {
    return flights.stream().anyMatch(flight -> flight.getFlightNumber().equals(number));
  }

  /**
   * Get a string representation of the Entities.Passenger
   * Useful for printing
   *
   * @return a string representation of the Entities.Passenger
   */
  public String toString() {
    String temp;
    temp = super.toString();
    temp += "Stall: " + stallLabel + "\n";

    if (flights.size() == 0) {
      temp += "Entities.Flight(s): None\n";
    } else {
      temp += "Entities.Flight(s): ";
      for (Flight flight : flights) {
        temp += flight.getFlightNumber() + "\t";
      }
      temp += "\n";
    }

    return temp;
}

  /**
   * A method to test the class.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    // Create a new passenger
    Passenger p = new Passenger("Bob", "123");

    // Testing constructor and getName() //
    if (! p.getName().equals("Bob")) {
      System.out.println("Problem with constructor or getName");
    }

    // Testing constructor and getidNumber() //
    if (! p.getIDNumber().equals("123")) {
      System.out.println("Problem with constructor or getidNumber");
    }

    // Testing constructor and getStallLabel //
    if (p.getStallLabel() != -1) {
      System.out.println("Problem with constructor or getStallLabel");
    }

    // Testing setName //
    p.setName("Joe");
    if (! p.getName().equals("Joe")) {
      System.out.println("Problem with setName");
    }

    // Testing setStallLabel

    p.setStallLabel(1);
    if (p.getStallLabel() != 1) {
      System.out.println("Problem with setStallLabel");
    }

    try {
      p.setStallLabel(2);
      System.out.println("Problem with setStallLabel; Expected an exception");
    }
    catch (IllegalStateException ignored) {}

    // Testing addFlight and hasFlight
    Flight d = new Flight(null, "123", null, null, 0);

    if (p.hasFlight("Mike")) {
      System.out.println("Problem with hasFlight");
    }

    p.addFlight(d);

    if (! p.hasFlight("Mike")) {
      System.out.println("Problem with addFlight or hasFlight");
    }

    // Testing toString
    String toStringExpected = "\nName: Joe\nid  number: 123\n" +
            "Stall: 1\nEntities.Flight(s): Mike\t\n";

    if (! p.toString().equals(toStringExpected)) {
      System.out.println("Problem with toString");
    }

    // Testing removeFlight

    try {
      p.removeFlight("Todd");
      System.out.println("Problem with removeFlight; expected an exception");
    }
    catch (IllegalStateException ignored) {}

    p.removeFlight("Mike");

    if (p.hasFlight("Mike")) {
      System.out.println("Problem with removeFlight");
    }

    System.out.println("Testing Complete");

  }
}