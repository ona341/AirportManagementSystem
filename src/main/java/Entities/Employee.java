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

public class Employee extends Person {
  private StringProperty role;


  public Employee(String id, String name, String role) {
      super(name, id);
      this.role = new SimpleStringProperty(role);
  }

  public String getRole() { return role.get(); }

  public StringProperty roleProperty() {
    return role;
  }

  public void setRole(String role) { this.role.set(role); }

  public String toString() {
    return super.toString();
  }



}