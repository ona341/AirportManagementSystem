package Entities;

/*
  Portions of this file are inspired by the Patient class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.util.AbstractMap.SimpleEntry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;


public class Passenger extends User implements Searchable{

  // A passenger has a list of flights that they are on
  private final ObservableList<SimpleEntry<Flight, IntegerProperty>> flights = FXCollections.observableArrayList();

  private StringProperty email;

  // The check-in date for the passengers parking
  private ObjectProperty<Date> checkInDate;

  private IntegerProperty parkingStallLabel;


  public Passenger(String name, String number) {
    super(name,number);
    this.email = new SimpleStringProperty(null);
    this.checkInDate = new SimpleObjectProperty<>(null);
    this.parkingStallLabel = new SimpleIntegerProperty(-1);
  }

  public Passenger(String name, String number, String email) {
    super(name,number);
    this.email = new SimpleStringProperty(email);
    this.checkInDate = new SimpleObjectProperty<>(null);
    this.parkingStallLabel = new SimpleIntegerProperty(-1);
  }

  public Passenger(String name, String number, String email, Date date, int stallLabel) {
    super(name, number);
    this.email = new SimpleStringProperty(email);
    this.checkInDate = new SimpleObjectProperty<>(date);
    this.parkingStallLabel = new SimpleIntegerProperty(stallLabel);
  }

  public Date getCheckInDate() {
    return checkInDate.get();
  }

  public ObjectProperty<Date> checkInDateProperty() {
    return checkInDate;
  }

  public void setCheckInDate(Date checkInDate) {
    this.checkInDate.set(checkInDate);
  }

  public int getParkingStallLabel() {
    return parkingStallLabel.get();
  }

  public IntegerProperty parkingStallLabelProperty() {
    return parkingStallLabel;
  }

  public void setParkingStallLabel(int parkingStallLabel) {
    if (this.getParkingStallLabel() != -1 && parkingStallLabel != -1) {
      throw new IllegalStateException("Error: This passenger is already assigned to a parking stall");
    } else {
      this.parkingStallLabel.set(parkingStallLabel);
    }
  }

  public String getEmail() {
    return email.get();
  }

  public StringProperty emailProperty() {
    return email;
  }

  public void setEmail(String email) {
    this.email.set(email);
  }

  public int getSeatNumber(Flight flight) {
    SimpleEntry<Flight, IntegerProperty> pair;
    pair = this.getFlightPair(flight);

    return pair.getValue().get();
  }

  public IntegerProperty seatProperty(Flight flight) {
    try {
      SimpleEntry<Flight, IntegerProperty> pair = this.getFlightPair(flight);
      return pair.getValue();
    } catch (NoSuchElementException e) {
      return new ReadOnlyIntegerWrapper(-1);
    }
  }

  public void setSeatNumber(Flight flight, int seatNumber) {
    SimpleEntry<Flight, IntegerProperty> pair = this.getFlightPair(flight);
    pair.setValue(new SimpleIntegerProperty(seatNumber));

  }

  public IntegerProperty sizeProperty() {
    IntegerProperty p = new SimpleIntegerProperty();
    p.bind(Bindings.size(flights));
    return p;
  }

  public int numFlights() {
    return flights.size();
  }

  public void addFlight(Flight f) {
    if (hasFlight(f.getFlightNumber())) {
      throw new IllegalStateException("Error: Attempting to create an association that already exists");
    } else {
      flights.add(new SimpleEntry<>(f,new SimpleIntegerProperty(-1)));
    }
  }

  public void addFlight(Flight f, int seatNum){
    if (hasFlight(f.getFlightNumber())) {
      throw new IllegalStateException("Error: Attempting to create an association that already exists");
    } else {
      flights.add(new SimpleEntry<>(f,new SimpleIntegerProperty(seatNum)));
    }
  }


  public void removeFlight(String number) {
    if (!flights.removeIf(pair -> pair.getKey().getFlightNumber().equals(number))) {
      throw new IllegalStateException("Error: Attempting to remove an association that does not exist");
    }
  }


  public boolean hasFlight(String number) {
    return flights.stream().anyMatch(pair -> pair.getKey().getFlightNumber().equals(number));
  }



  public ObservableList<SimpleEntry<Flight, IntegerProperty>> getFlights() {
    return flights;
  }

  private SimpleEntry<Flight, IntegerProperty> getFlightPair(Flight flight) {
    return this.flights.stream().filter(pair -> pair.getKey().equals(flight)).findFirst().orElseThrow();
  }

  /**
   * Provides the ability to search the attributes of this passenger
   */
  public static Predicate<User> search(String text) {
    return User.search(text).or(passenger -> {
      Boolean result = false;
      String getString;

      if (Optional.ofNullable(getString = ((Passenger) passenger).getEmail()).isPresent())
        result = result || getString.contains(text);

      if (Optional.ofNullable(((Passenger) passenger).getCheckInDate()).isPresent())
        result = result || ((Passenger) passenger).getCheckInDate().toString().contains(text);

      result = result || Integer.toString(((Passenger) passenger).getParkingStallLabel()).contains(text);

      result = result || ((Passenger) passenger).getFlights().stream().anyMatch(seat -> seat.getKey().getFlightNumber().contains(text));

      return result;
    });
  }

}