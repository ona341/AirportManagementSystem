package Entities;

/*
  Portions of this file are inspired by the Doctor class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */


public class Employee extends Person {
  private String email;


  public Employee(String name, String number, String email) {
      super(name, number);
      this.email = email;
  }

  public String getEmail() {
    return email;
  }



  public String toString() {
    return super.toString();
  }



}