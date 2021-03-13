package Entities;/*
  CMPT 270 A5
  @author Blake Stadnyk; 11195866 - BJS645
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