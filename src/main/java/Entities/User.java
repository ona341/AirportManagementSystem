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

import java.util.Optional;
import java.util.function.Predicate;


public abstract class User {


    private StringProperty name;

    private final StringProperty id;

    private StringProperty role;


    public User(String pName, String pNumber) {
        name = new SimpleStringProperty(pName);
        id = new SimpleStringProperty(pNumber);
        role = new SimpleStringProperty("Passenger");
    }

    public String getRole() { return role.get(); }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) { this.role.set(role); }

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


    public static Predicate<User> search(String text) {
        return (user -> {
            Boolean result = false;
            String getString;

            if (Optional.ofNullable(getString = user.getId()).isPresent())
                result = result || getString.contains(text);

            if (Optional.ofNullable(getString = user.getName()).isPresent())
                result = result || getString.contains(text);

            return result;
        });
    }
}
