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


public abstract class Person {


    private StringProperty name;

    private final StringProperty id;


    public Person(String pName, String pNumber) {
        name = new SimpleStringProperty(pName);
        id = new SimpleStringProperty(pNumber);
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

    public String getId() {
        return id.get();
    }

    public StringProperty numberProperty() { return id; }

    public void setId(String number) {
        this.id.set(number);
    }

    /**
     * Return a string representation of the person.
     *
     * @return a string representation of the person
     */
    public String toString() {
        return "\nName: " + name + "\nid  number: " + id + "\n";
    }

}
