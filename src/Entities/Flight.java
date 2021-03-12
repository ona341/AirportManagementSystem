package Entities;

import java.sql.Date;
import java.sql.Time;

/**
 * Flight Class that is a representation of the flights in the airport
 */
public class Flight {
    /**
     * Instance variable that is the flight's airline
     */
    private String airline;
    /**
     * Instance variable that is the flight number
     */
    private String flightNumber;
    /**
     * Instance variable that is the flight's destination
     */
    private String destination;
    /**
     * Instance variable that is the flight's date
     */
    private Date date;
    /**
     * Instance variable that is the flight's time
     */
    private Time time;
    /**
     * Instance variable that is the flight's capacity
     */
    private int capacity;
    /**
     * Instance variable that is a container of passengers
     */
    private final EntityContainer<Passenger> seats;
    /**
     * Instance variable that is the flights gate
     */
    private int gate;

    /**
     * Constructor initializes all instance variables
     * @param number an string that is the flight number
     * @param airline a string that is the airline name
     * @param destination a string that if the flights destination
     * @param date a Date object that is the date of the flight
     * @param time a Time object that is the flights scheduled time
     * @param gate an integer that is the flights gate
     */
    public Flight(String number, String airline, String destination, Date date, Time time, int gate) {
        this.airline = airline;
        this.flightNumber = number;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.seats = new EntityContainer<>(number, 0, capacity);
        this.gate = gate;
    }

    /**
     * Gets the seats in the flights
     * @return a container of passengers that is the seats in the flight
     */
    public EntityContainer<Passenger> getSeats() {
        return this.seats;
    }

    /**
     * Gets the Flight's number
     * @return a string that is the flight's number
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Gives the string representation of a flight
     * @return a String
     */
    public String toString() {
        return "Flight: " + this.getFlightNumber();
    }

    /**
     * Gets the flight's airline
     * @return a string that is the flight's airline
     */
    public String getAirline() {
        return airline;
    }

    /**
     * Sets the flight's airline
     * @param airline a string
     */
    public void setAirline(String airline) {
        this.airline = airline;
    }

    /**
     * Sets the flight's number
     * @param flightNumber a string that is the flight's number
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Gets the flight's destination
     * @return a string that is  the flight's destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the flight's destination
     * @param destination a string that is the flight's destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the flights date
     * @return a Date object that is the flight's date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the Date of the flights
     * @param date a Date object that is the flights date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the Flight's Scheduled time
     * @return a Time object
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets the flight's time
     * @param time a Time object that is the flight's scheduled time
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * Gets the flights capacity
     * @return an integer that is the capacity of the flight
     */
    public int getCapacity() {
        return capacity;
    }
    /**
     * Sets the flight's Capacity
     * @param capacity an integer object that is the flight's Capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    /**
     * Gets the Flight's Gate
     * @return an integer that is the flights gate
     */
    public int getGate() {
        return gate;
    }

    /**
     * Sets the flight's Gate
     * @param gate an integer
     */
    public void setGate(int gate) {
        this.gate = gate;
    }
}
