package Entities;/*
  CMPT 270 A5
  @author Blake Stadnyk; 11195866 - BJS645
 */

import java.util.ArrayList;

/**
 * A Employee who extends from BasicEmployee and has a list of passenger.
 */
public class Employee extends Person {

  /**
   * The list of Passengers.
   */
  public final ArrayList<Passenger> passengers = new ArrayList<>();

  /**
   * Instantiates a new Employee with the given name.
   *
   * @param name the name of the employee
   */
  public Employee(String name, String number) {
    super(name, number);
  }

  /**
   * Add passenger.
   *
   * @param p the passenger
   * @precond (p != null) && (!hasPassenger(p.getidNumber()))
   */
  public void addPassenger(Passenger p) throws NullPointerException{
    if (hasPassenger(p.getIDNumber())) {
      throw new IllegalStateException("Error: Attempting to create an association that already exists");
    } else {
      passengers.add(p);
    }
  }

  /**
   * Remove passenger.
   *
   * @param idNum the id num
   * @precond Passenger association must exist
   */
  public void removePassenger(String idNum) {
    if (!passengers.removeIf(passenger -> passenger.getIDNumber().equals(idNum))) {
      throw new IllegalStateException("Error: Attempting to remove an association that does not exist");
    }
  }

  /**
   * Has passenger boolean.
   *
   * @param idNum the id num of a passenger
   * @return True if the employee has a passenger with this number
   */
  public boolean hasPassenger(String idNum) {
    return passengers.stream().anyMatch(passenger -> passenger.getIDNumber().equals(idNum));
  }

  /**
   * Get a string representation of the Employee
   * Useful for printing
   *
   * @return a string representation of the Employee
   */
  public String toString() {
    String temp;
    temp = super.toString();

    if (passengers.size() == 0) {
      temp += "Passenger(s): None\n";
    } else {
      temp += "Passenger(s): ";
      for (Passenger passenger : passengers) {
        temp += passenger.getName() + "\t";
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
    // Create a new employee
    Employee d = new Employee("Bob", "123");

    // Testing constructor and getName() //
    if (! d.getName().equals("Bob")) {
      System.out.println("Problem with constructor or getName");
    }

    // Testing setName //
    d.setName("Joe");
    if (! d.getName().equals("Joe")) {
      System.out.println("Problem with setName");
    }

    // Testing addPassenger and hasPassenger
    Passenger p = new Passenger("Mike", "123",null,null,0);

    if (d.hasPassenger("123")) {
      System.out.println("Problem with hasPassenger");
    }

    d.addPassenger(p);
    if (! d.hasPassenger("123")) {
      System.out.println("Problem with addPassenger or hasPassenger");
    }

    // Testing toString
    String toStringExpected = "\nName: Joe\n" +
            "Passenger(s): Mike\t\n";

    if (! d.toString().equals(toStringExpected)) {
      System.out.println("Problem with toString");
    }

    // Testing removePassenger

    try {
      d.removePassenger("Todd");
      System.out.println("Problem with removePassenger; expected an exception");
    }
    catch (IllegalStateException ignored) {}

    d.removePassenger("123");

    if (d.hasPassenger("123")) {
      System.out.println("Problem with removePassenger");
    }

    System.out.println("Testing Complete");

  }

}