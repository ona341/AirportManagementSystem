package Singleton;

import Entities.Airport;

/**
 * A airport using the singleton pattern.
 */
public class AirportAccess {

    /**
     * The Airport.
     */
    static Airport airport = null;

    /**
     * Private do nothing constructor
     */
    private AirportAccess() {}

    /**
     * Initialize the airport.
     *
     * @param name  the name of the airport
     * @param first the first stall number
     * @param last  the last stall number
     */
    public static void initialize(String name, int first, int last, int firstParkingStall, int lastParkingStall)
            throws IllegalStateException{
        if (airport != null) {
            throw new IllegalStateException("A airport has already been created");
        }
        else {
            airport = new Airport(name, first, last, firstParkingStall, lastParkingStall);
        }
    }

    /**
     * Gets the airport instance.
     *
     * @return the airport instance
     * @throws IllegalStateException the illegal state exception
     */
    public static Airport getInstance() throws IllegalStateException{
        if (airport == null) {
            throw new IllegalStateException("A airport has not been created");
        }
        else {
            return airport;
        }
    }
}
