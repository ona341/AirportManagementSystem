package Entities;

/*
  Portions of this file are inspired by the Doctor class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * An employee at the airport
 * The employee is also a passenger
 */
public class Employee extends Passenger implements Searchable{

  private StringProperty role;

  private WorkSchedule schedule;


  public Employee(String id, String name, String role) {
      super(name, id);
      this.role = new SimpleStringProperty(role);
      this.schedule = new WorkSchedule("", "", "", "", "", "", "");
  }

  public Employee(String name, String number, String email, Date date, int stallLabel, String role) {
    super(name, number, email, date, stallLabel);
    this.role = new SimpleStringProperty(role);
    this.schedule = new WorkSchedule("", "", "", "", "", "", "");
  }

  public String getRole() { return role.get(); }

  public StringProperty roleProperty() {
    return role;
  }

  public void setRole(String role) { this.role.set(role); }


  public WorkSchedule getSchedule() { return this.schedule; }

  /**
   * Provides the ability to search the attributes of this employee
   */
  public static Predicate<Passenger> search(String text) {
    return Passenger.search(text).or(employee -> {
      boolean result = false;
      String getString;
      if (Optional.ofNullable(getString = ((Employee) employee).getRole()).isPresent())
        result = getString.contains(text);

      return result;
    });
  }

}