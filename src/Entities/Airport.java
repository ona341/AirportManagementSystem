package Entities;

import java.util.ArrayList;

/**
 * An airport which has a gateContainer and flightContainer.
 */
public class Airport {
    /**
     * The name of this airport.
     */
    private final String name;



    /**
     * An entityContainer to represent the gates of the airport
     *
     */
    private final EntityContainer<Flight> gates;


    /**
     * An entityContainer to represent the parking spots of the airport
     */
    private final EntityContainer<Passenger> parkingStalls;

    /**
     * Initialize the airport with the name given, and with gates those labels are
     * the consecutive integers from minGateLabel to maxGateLabel.
     *
     * @param wName        the name of the airport
     * @param wMinGateLabel the label of the first gate in the airport
     * @param wMaxGateLabel the label of the last gate in the airport
     * @precond wName != null && !wName.equals("") && wMinGateLabel >= 0 && wMaxGateLabel >= wMinGateLabel
     */
    public Airport(String wName, int wMinGateLabel, int wMaxGateLabel, int minParkingStalls, int maxParkingStalls) {
        if (wName == null || wName.equals(""))
            throw new IllegalArgumentException("The name of a airport cannot be null or empty.  "
                    + "It is " + wName);
        if (wMinGateLabel < 0 || wMaxGateLabel < wMinGateLabel)
            throw new IllegalArgumentException("The gate labels " + wMinGateLabel + " and " + wMaxGateLabel
                    + " are invalid as they cannot be negative, and must have at least one gate.");

        if (minParkingStalls < 0 || maxParkingStalls < minParkingStalls)
            throw new IllegalArgumentException("The parking stall labels " + minParkingStalls + " and " + maxParkingStalls
                    + " are invalid as they cannot be negative, and must have at least one parking stall.");

        name = wName;

        gates = new EntityContainer<>("Gates",wMinGateLabel,wMaxGateLabel);
        parkingStalls = new EntityContainer<>("Parking",minParkingStalls,maxParkingStalls);
    }



    /**
     * Return the name of this airport.
     *
     * @return the name of this airport
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the gates in the Airport
     * @return a container of flights that is the gates n the airport
     */
    public EntityContainer<Flight> getGates() {
        return gates;
    }

    /**
     * Gets the parking stalls in the Airport
     * @return a container of passengers that is the parking stalls
     */
    public EntityContainer<Passenger> getParkingStalls() {
        return parkingStalls;
    }

    /**
     * Converts a gate integer onto a character
     * @param i an integer i
     * @return a chacheter
     */
    public char gateIntToChar(int i) {
        return (char) (i + 'A' - 1);
    }


    /**
     * A method to test the class.
     *
     * @param args not used
     */
    public static void main(String[] args) {
    }




}
