package Entities;

import java.util.Calendar;

public class Flight {

    private Airline airline;

    private String flightNumber;

    private String destination;

    private Calendar time;

    private int capacity;

    private PersonContainer seats;


    public Flight(Airline airline, String number, String destination, Calendar time, int capacity) {
        this.airline = airline;
        this.flightNumber = number;
        this.destination = destination;
        this.time = time;
        this.capacity = capacity;
        this.seats = new PersonContainer(number, 0, capacity);
    }

    public PersonContainer getSeats() {
        return this.seats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }



}
