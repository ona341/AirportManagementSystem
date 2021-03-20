package Entities;

import javafx.beans.property.*;

import java.sql.Date;
import java.sql.Time;
import java.util.function.Predicate;

public class Flight implements Searchable{

    private StringProperty airline;

    private StringProperty flightNumber;

    private StringProperty destination;

    private ObjectProperty<Date> date;

    private ObjectProperty<Time> time;

    private IntegerProperty gate;

    private IntegerProperty capacity;

    private final EntityContainer<Passenger> seats;




    public Flight(String number, String airline, String destination, Date date, Time time, int gate, int capacity) {
        this.airline = new SimpleStringProperty(airline);
        this.flightNumber = new SimpleStringProperty(number);
        this.destination = new SimpleStringProperty(destination);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.seats = new EntityContainer<>(number, 1, capacity);
        this.gate = new SimpleIntegerProperty(gate);
        this.capacity = new SimpleIntegerProperty(capacity);
    }

    public EntityContainer<Passenger> getSeats() {
        return this.seats;
    }


    public String toString() {
        return "Flight: " + this.getFlightNumber();
    }


    public String getAirline() {
        return airline.get();
    }

    public StringProperty airlineProperty() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline.set(airline);
    }

    public String getFlightNumber() {
        return flightNumber.get();
    }

    public StringProperty flightNumberProperty() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber.set(flightNumber);
    }

    public String getDestination() {
        return destination.get();
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public Time getTime() {
        return time.get();
    }

    public ObjectProperty<Time> timeProperty() {
        return time;
    }

    public void setTime(Time time) {
        this.time.set(time);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public int getGate() {
        return gate.get();
    }

    public IntegerProperty gateProperty() {
        return gate;
    }

    public void setGate(int gate) {
        this.gate.set(gate);
    }

    public static Predicate<Flight> search(String text) {
        return (flight -> flight.getFlightNumber().contains(text) || flight.getAirline().contains(text)
                || flight.getDestination().contains(text) || flight.getDate().toString().contains(text)
                || flight.getTime().toString().contains(text));
    }
}
