package Entities;/*
  CMPT 270 A5
  @author Blake Stadnyk; 11195866 - BJS645
 */

/*
  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.

  This document contains resources for homework assigned to students of
  CMPT 270 and shall not be distributed without permission.  Posting this
  file to a public or private website, or providing this file to a person
  not registered in CMPT 270, constitutes Academic Misconduct, according
  to the University of Saskatchewan Policy on Academic Misconduct.

  Synopsis:
     Starter File for Assignment 4
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The model of a person who has a name and an ID number
 * that cannot be changed.
 */
public class Person {

    /**
     * The name of the person.
     */
    private StringProperty name;

    /**
     * The person's id  number.
     */
    private final StringProperty number;

    /**
     * Initialize an instance of a Person with the given name and id number.
     *
     * @param pName the person's name
     * @param pNumber the person's id number
     */
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

    /**
     * A method to test the Entities.Person class.
     */
    public static void main(String[] args) {
        int numErrors = 0;

        // testing all the methods with one instance of a Entities.Person
        Person p = new Person("Pete", "123456");

        if(! p.getName().equals("Pete")) {
            System.out.println("The constructor or getName failed");
            numErrors++;
        }
        if(!p.getNumber().equals("123456")) {
            System.out.println("The constructor or getidNumber failed");
            numErrors++;
        }
        p.setName("Jim");
        if(! p.getName().equals("Jim")) {
            System.out.println("The constructor or setName failed");
            numErrors++;
        }
        String expected = "\nName: Jim\nid  number: 123456\n";
        if(!p.toString().equals(expected)) {
            System.out.println("The constructor or toString failed");
            numErrors++;
        }

        // testing all the methods with a second instance of a Entities.Person
        p = new Person("Mary", "987654");

        if(! p.getName().equals("Mary"))
        {
            System.out.println("The constructor or getName failed");
            numErrors++;
        }
        if(!p.getNumber().equals("987654"))
        {
            System.out.println("The constructor or getidNumber failed");
            numErrors++;
        }
        p.setName("Sue");
        if(! p.getName().equals("Sue"))
        {
            System.out.println("setName failed");
            numErrors++;
        }
        expected = "\nName: Sue\nid  number: 987654\n";
        if (!p.toString().equals(expected)) {
            System.out.println("The constructor or toString failed");
            numErrors++;
        }

        System.out.println("The number of errors found is " + numErrors);
    }
}
