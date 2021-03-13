package Entities;


public class Manager extends Employee {


  public Manager(String name, String number, String email) {
    super(name, number, email);
  }


  public String toString() {
    return "\nManager:\n" + super.toString();
  }


}