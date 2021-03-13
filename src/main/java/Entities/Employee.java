package Entities;

/*
  Portions of this file are inspired by the Doctor class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */

/**
 * A Employee who extends from Person.
 */
public class Employee extends Person {
  private String email;

  /**
   * Instantiates a new Employee with the given name.
   *
   * @param name the name of the employee
   */
  public Employee(String name, String number, String email) {
      super(name, number);
      this.email = email;
  }

  public String getEmail() {
    return email;
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
    Employee d = new Employee("Bob", "123",null);


    // Testing toString
    String toStringExpected = "\nName: Joe\n" +
            "Passenger(s): Mike\t\n";

    if (! d.toString().equals(toStringExpected)) {
      System.out.println("Problem with toString");
    }



    System.out.println("Testing Complete");

  }

}