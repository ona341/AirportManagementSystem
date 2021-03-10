package Entities;/*
  CMPT 270 A5
  @author Blake Stadnyk; 11195866 - BJS645
 */

import java.sql.Date;
import java.util.ArrayList;

/**
 * A Employee who extends from Person.
 */
public class Employee extends Passenger {


  /**
   * Instantiates a new Employee with the given name.
   *
   * @param name the name of the employee
   */
  public Employee(String name, String number, String email, Date date, int stallNumber) {
    super(name, number, email, date, stallNumber);
  }



  /**
   * Get a string representation of the Employee
   * Useful for printing
   *
   * @return a string representation of the Employee
   */
  public String toString() {
    return super.toString();
  }

  /**
   * A method to test the class.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    // Create a new employee
    Employee d = new Employee("Bob", "123",null,null,0);


    // Testing toString
    String toStringExpected = "\nName: Joe\n" +
            "Passenger(s): Mike\t\n";

    if (! d.toString().equals(toStringExpected)) {
      System.out.println("Problem with toString");
    }



    System.out.println("Testing Complete");

  }

}