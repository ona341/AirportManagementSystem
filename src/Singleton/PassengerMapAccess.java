package Singleton;
/*
  CMPT 270 A5Q4
  @author Blake Stadnyk; 11195866 - BJS645
 */

import Entities.Passenger;

import java.util.TreeMap;

/**
 * A Passenger Map using the singleton pattern
 */
public class PassengerMapAccess {

    /**
     * The singleton passenger map
     */
    private static final TreeMap<String, Passenger> passengers = new TreeMap<>();

    /**
     * Private do nothing constructor
     */
    private PassengerMapAccess() {}

    /**
     * Gets the instance of the map.
     *
     * @return the map instance
     */
    public static TreeMap<String, Passenger> getInstance() {
        return passengers;
    }
}
