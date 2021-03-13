package Entities;

/*
  Portions of this file are inspired by the Person class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Person {


    private StringProperty name;


    private final StringProperty number;


    public Person(String pName, String pNumber) {
        name = new SimpleStringProperty(pName);
        number = new SimpleStringProperty(pNumber);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    /**
     * Return a string representation of the person.
     *
     * @return a string representation of the person
     */
    public String toString() {
        return "\nName: " + name + "\nid  number: " + number + "\n";
    }

}
