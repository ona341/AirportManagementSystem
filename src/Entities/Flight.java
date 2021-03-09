package Entities;

import java.sql.Date;
import java.sql.Time;

public class Flight {

    private String airline;

    private String flightNumber;

    private String destination;

    private Date date;

    private Time time;

    private int capacity;

    private PersonContainer seats;


    public Flight(String number, String airline, String destination, Date date, Time time) {
        this.airline = airline;
        this.flightNumber = number;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.seats = new PersonContainer(number, 0, capacity);
    }

    public PersonContainer getSeats() {
        return this.seats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String toString() {
        return "Flight: " + this.getFlightNumber();
    }


    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
