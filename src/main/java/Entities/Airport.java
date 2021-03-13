package Entities;

/*
  Portions of this file are inspired by the Ward class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */


public class Airport {


    private final String name;



    private final EntityContainer<Flight> gates;



    private final EntityContainer<Passenger> parkingStalls;


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


}
