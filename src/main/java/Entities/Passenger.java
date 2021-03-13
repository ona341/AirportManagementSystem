package Entities;

/*
  Portions of this file are inspired by the Patient class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */

import javafx.beans.property.*;

import java.sql.Date;
import java.util.ArrayList;


public class Passenger extends Person {



  private final ArrayList<Flight> flights = new ArrayList<>();

  private StringProperty email;

  private ObjectProperty<Date> checkInDate;

  private IntegerProperty parkingStallLabel;

  private IntegerProperty seatNumber;


  public Passenger(String name, String number, String email, Date date, int stallLabel) {

    super(name, number);
    this.email = new SimpleStringProperty(email);
    this.checkInDate = new SimpleObjectProperty<>(date);
    this.parkingStallLabel = new SimpleIntegerProperty(stallLabel);
    this.seatNumber = new SimpleIntegerProperty(-1);
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

  public int getSeatNumber() {
    return seatNumber.get();
  }

  public IntegerProperty seatNumberProperty() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    if (this.getSeatNumber() != -1 && seatNumber != -1) {
      throw new IllegalStateException("Error: This passenger is already assigned to a seat");
    } else {
      this.seatNumber.set(seatNumber);
    }
  }



  public void addFlight(Flight f) {
    if (hasFlight(f.getFlightNumber())) {
      throw new IllegalStateException("Error: Attempting to create an association that already exists");
    } else {
      flights.add(f);
    }
  }


  public void removeFlight(String number) {
    if (!flights.removeIf(flight -> flight.getFlightNumber().equals(number))) {
      throw new IllegalStateException("Error: Attempting to remove an association that does not exist");
    }
  }


  public boolean hasFlight(String number) {
    return flights.stream().anyMatch(flight -> flight.getFlightNumber().equals(number));
  }


  public String toString() {
    String temp;
    temp = super.toString();
    temp += "Stall: " + getSeatNumber() + "\n";

    if (flights.size() == 0) {
      temp += "Entities.Flight(s): None\n";
    } else {
      temp += "Entities.Flight(s): ";
      for (Flight flight : flights) {
        temp += flight.getFlightNumber() + "\t";
      }
      temp += "\n";
    }

    return temp;
}


}