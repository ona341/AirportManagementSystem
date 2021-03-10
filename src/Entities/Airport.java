package Entities;

/*
  CMPT 270 A5
  @author Blake Stadnyk; 11195866 - BJS645
 */

/*
  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.

  This document contains resources for homework assigned to students of
  CMPT 270 and shall not be distributed without permission.  Posting this
  file to a public or private website, or providing this file to a person
  not registered in CMPT 270, constitutes Academic Misconduct, according
  to the University of Saskatchewan Policy on Academic Misconduct.

  Synopsis:
     Starter File for Assignment 4
 */

import java.util.ArrayList;

/**
 * An airport with a specified number of gates with consecutive labels.
 */
public class Airport {
    /**
     * The name of this airport.
     */
    private final String name;

    /**
     * The (external) label of the first gate of the airport.
     */
    private final int minGateLabel;

    /**
     * The (external) label of the first parking stall of the airport.
     */
    private final int minParkingLabel;

    /**
     * An array to represent the gates of the airport.  Each gate is empty (null)
     * or else has a Flight in it.
     */
    private final Flight[] gates;


    /**
     * An array to represent the parking stalls of the airport.  Each stall is empty (null)
     * or else has a Person in it.
     */
    private final Passenger[] parkingStalls;
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
        minGateLabel = wMinGateLabel;
        minParkingLabel= minParkingStalls;
        gates = new Flight[wMaxGateLabel - wMinGateLabel + 1];
        parkingStalls = new Passenger[maxParkingStalls - minParkingStalls + 1];
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
     * Return the smallest label for a gate on the airport.
     *
     * @return the smallest Label for a gate on the airport
     */
    public int getMinGateLabel() {
        return minGateLabel;
    }

    /**
     * Return the largest label for a gate on the airport.
     *
     * @return the largest label for a gate on the airport
     */
    public int getMaxGateLabel() {
        return minGateLabel + gates.length - 1;
    }


    /**
     * Return the smallest label for a parking stall in the airport.
     *
     * @return the smallest Label for a parking stall in the airport
     */
    public int getMinParkingLabel() {
        return minParkingLabel;
    }

    /**
     * Return the largest label for a parking stall in the airport.
     *
     * @return the largest label for a parking stall in the airport
     */
    public int getMaxParkingLabel() {
        return minParkingLabel + parkingStalls.length - 1;
    }

    /**
     * Return the internal/array index of the gate corresponding to the external label.
     *
     * @param gateLabel the label of a gate from the external/user perspective
     * @return the internal/array index of the gate corresponding to the external label
     * @precond isValidLabel(gateLabel)
     */
    private int externalToInternalIndex(int gateLabel) {
        if (!isValidLabel(gateLabel))
            throw new IllegalArgumentException("The value " + gateLabel
                    + " is not a valid label for a gate in the airport.");

        return gateLabel - minGateLabel;
    }

    /**
     * Return the external/user label of the gate corresponding to the internal index.
     *
     * @param arrayIndex the index of a location in the gates array
     * @return the external/user label of the gate corresponding to the internal index
     * @precond 0 <= arrayIndex < gates.length
     */
    private int internalToExternalLabel(int arrayIndex) {
        if (arrayIndex < 0 || arrayIndex >= gates.length)
            throw new IllegalArgumentException("The value " + arrayIndex +
                    " is not a valid index for an array of length " + gates.length + ".");

        return arrayIndex + minGateLabel;
    }
    /**
     * Return the internal/array index of the parking stall corresponding to the external label.
     *
     * @param parkingLabel the label of a parking stall from the external/user perspective
     * @return the internal/array index of the parking stall corresponding to the external label
     * @precond isValidLabel(parkingLabel)
     */
    private int externalToInternalParkingIndex(int parkingLabel) {
        if (!isValidLabel(parkingLabel))
            throw new IllegalArgumentException("The value " + parkingLabel
                    + " is not a valid label for a parking stall in the airport.");

        return parkingLabel - minParkingLabel;
    }

    /**
     * Return the external/user label of the parking stall corresponding to the internal index.
     *
     * @param arrayIndex the index of a location in the parking stalls array
     * @return the external/user label of the parking stall corresponding to the internal index
     * @precond 0 <= arrayIndex < parkingStalls.length
     */
    private int internalToExternalParkingLabel(int arrayIndex) {
        if (arrayIndex < 0 || arrayIndex >= parkingStalls.length)
            throw new IllegalArgumentException("The value " + arrayIndex +
                    " is not a valid index for an array of length " + parkingStalls.length + ".");

        return arrayIndex + minParkingLabel;
    }

    /**
     * Is the specified gate occupied?
     *
     * @param gateLabel the label of the gate to be tested for being occupied
     * @return is the specified gate occupied?
     * @precond isValidLabel(gateLabel)
     */
    public boolean isOccupied(int gateLabel) {
        if (!isValidLabel(gateLabel))
            throw new IllegalArgumentException("The value " + gateLabel
                    + " is not a valid label for a gate in the airport.");

        return gates[externalToInternalIndex(gateLabel)] != null;
    }

    /**
     * Is the specified parking stall occupied?
     *
     * @param parkingLabel the label of the parking stall to be tested for being occupied
     * @return is the specified parking stall occupied?
     * @precond isValidLabel(parkingLabel)
     */
    public boolean isParkingStallOccupied(int parkingLabel) {
        if (!isValidLabel(parkingLabel))
            throw new IllegalArgumentException("The value " + parkingLabel
                    + " is not a valid label for a parking stall in the airport.");

        return parkingStalls[externalToInternalIndex(parkingLabel)] != null;
    }

    /**
     * Determine if a flight is in gates
     *
     * @param number        the number of the flight to search for
     * return true if the flight is found; false otherwise
     */
    public boolean hasFlight(String number) {
        for (Flight gate : gates) {
            if (gate != null && gate.getFlightNumber().equalsIgnoreCase(number)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if a passenger is in parking stalls
     *
     * @param number        the number of the flight to search for
     * return true if the flight is found; false otherwise
     */
    public boolean hasPassenger(String number) {
        for (Passenger stall : parkingStalls) {
            if (stall != null && stall.getIDNumber().equalsIgnoreCase(number)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the index of a flight in gates if it exists
     *
     * @param number        the number of the flight to search for
     * return an integer represents the index of the flight found; -1 if not found.
     */
    public int getFlightInternalIndex(String number) {
        for (int i = 0; i < gates.length; i++) {
            if (gates[i] != null && gates[i].getFlightNumber().equalsIgnoreCase(number)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get the index of a passenger in stalls if it exists
     *
     * @param number        the id number of passenger to search for
     * return an integer represents the index of the passenger found; -1 if not found.
     */
    public int getPassengerInternalIndex(String number) {
        for (int i = 0; i < parkingStalls.length; i++) {
            if (parkingStalls[i] != null && parkingStalls[i].getIDNumber().equalsIgnoreCase(number)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Return the flight in the specified gate.
     *
     * @param gateLabel the label of the gate that has the flight to be retrieved
     * @return the flight in the specified gate
     * @precond isValidLabel(gateLabel) && isOccupied(gateLabel)
     */
    public Flight getFlight(int gateLabel) {
        if (!isValidLabel(gateLabel))
            throw new IllegalArgumentException("The value " + gateLabel
                    + " is not a valid label for a gate in the airport.");

        if (!isOccupied(gateLabel))
            throw new IllegalStateException("Gate " + gateLabel + " is not currently occupied"
                    + " so cannot get its flight");
        return gates[externalToInternalIndex(gateLabel)];
    }

    /**
     * Return the flight in the specified gate.
     *
     * @param parkingLabel the label of the gate that has the flight to be retrieved
     * @return the flight in the specified gate
     * @precond isValidLabel(gateLabel) && isOccupied(gateLabel)
     */
    public Passenger getPassenger(int parkingLabel) {
        if (!isValidLabel(parkingLabel))
            throw new IllegalArgumentException("The value " + parkingLabel
                    + " is not a valid label for a parking stall in the airport.");

        if (!isOccupied(parkingLabel))
            throw new IllegalStateException("Stall " + parkingLabel + " is not currently occupied"
                    + " so cannot get its passenger");
        return parkingStalls[externalToInternalIndex(parkingLabel)];
    }

    public Passenger[] getPassenger() {
        return parkingStalls;
    }


    /**
     * Assign the specified flight to the specified gate.
     *
     * @param p        the flight to be assigned a gate
     * @param gateLabel the label of the gate that the flight is to be assigned
     * @precond isValidLabel(gateLabel) && !isOccupied(gateLabel)
     */
    public void assignFlightToGate(Flight p, int gateLabel) {
        if (!isValidLabel(gateLabel))
            throw new IllegalArgumentException("The value " + gateLabel
                    + " is not a valid label for a gate in the airport.");

        if (isOccupied(gateLabel))
            throw new IllegalStateException("Gate " + gateLabel + " is currently occupied by "
                    + gates[externalToInternalIndex(gateLabel)]
                    + " so cannot be assigned to another flight");

        gates[externalToInternalIndex(gateLabel)] = p;
    }

    /**
     * Assign the specified passenger to the specified Parking stall.
     *
     * @param p        the passenger to be assigned a parking stall
     * @param parkingLabel the label of the stall that the passenger is to be assigned
     * @precond isValidLabel(parkingLabel) && !isOccupied(parkingLabel)
     */
    public void assignPassengerToStall(Passenger p, int parkingLabel) {
        if (!isValidLabel(parkingLabel))
            throw new IllegalArgumentException("The value " + parkingLabel
                    + " is not a valid label for a parking stall in the airport.");

        if (isOccupied(parkingLabel))
            throw new IllegalStateException("Gate " + parkingLabel + " is currently occupied by "
                    + parkingStalls[externalToInternalIndex(parkingLabel)]
                    + " so cannot be assigned to another passenger");

        parkingStalls[externalToInternalIndex(parkingLabel)] = p;
    }

    /**
     * Remove a flight at the given internal index from gate.
     *
     * @param gateInternalIndex  the internal index of the gate containing the flight to be removed
     * @precond isValidLabel(internalToExternalLabel(gateInternalIndex))
     */
    public void removeFlightFromGate(int gateInternalIndex) {
        if (!isValidLabel(internalToExternalLabel(gateInternalIndex))) {
            throw new IllegalArgumentException("The value " + gateInternalIndex
                    + " is not a valid internal index for a gate in the airport.");
        }
        gates[gateInternalIndex] = null;
    }

    /**
     * Remove a passenger at the given internal index from parking stall.
     *
     * @param stallInternalIndex  the internal index of the parking stall containing the passenger to be removed
     * @precond isValidLabel(internalToExternalLabel(gateInternalIndex))
     */
    public void removePassengerFromStall(int stallInternalIndex) {
        if (!isValidLabel(internalToExternalLabel(stallInternalIndex))) {
            throw new IllegalArgumentException("The value " + stallInternalIndex
                    + " is not a valid internal index for a parking stall in the airport.");
        }
        parkingStalls[stallInternalIndex] = null;
    }

    /**
     * Finds available gates.
     *
     * @return a list of available gates
     */
    public ArrayList<Integer> availableGates() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < gates.length ; i++) {
            if (gates[i] == null) {
                temp.add(internalToExternalLabel(i));
            }
        }
        return temp;
    }

    /**
     * Finds available Parking stalls.
     *
     * @return a list of available stalls
     */
    public ArrayList<Integer> availableStalls() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < parkingStalls.length ; i++) {
            if (parkingStalls[i] == null) {
                temp.add(internalToExternalLabel(i));
            }
        }
        return temp;
    }


    public int firstAvailableGate() {
        for (int i = 0; i < gates.length ; i++) {
            if (gates[i] == null) {
                return internalToExternalLabel(i);
            }
        }
        return -1;
    }

    public int firstAvailableStall() {
        for (int i = 0; i < parkingStalls.length ; i++) {
            if (parkingStalls[i] == null) {
                return internalToExternalLabel(i);
            }
        }
        return -1;
    }

    public char gateIntToChar(int i) {
        return (char) (i + 'A' - 1);
    }



    /**
     * Frees a gate.
     *
     * @param gateLabel the gate label
     * @precond isValidLabel(gateLabel) && isOccupied(gateLabel)
     */
    public void freeGate(int gateLabel) {
        if (!isValidLabel(gateLabel))
            throw new IllegalArgumentException("The value " + gateLabel
                    + " is not a valid label for a gate in the airport.");

        if (!isOccupied(gateLabel))
            throw new IllegalStateException("Gate " + gateLabel + " is not currently occupied"
                    + " so the gate cannot be freed");
        gates[externalToInternalIndex(gateLabel)] = null;
    }

    /**
     * Frees a stall.
     *
     * @param parkingLabel the gate label
     * @precond isValidLabel(parkingLabel) && isOccupied(parkingLabel)
     */
    public void freeStall(int parkingLabel) {
        if (!isValidLabel(parkingLabel))
            throw new IllegalArgumentException("The value " + parkingLabel
                    + " is not a valid label for a parking stall in the airport.");

        if (!isOccupied(parkingLabel))
            throw new IllegalStateException("Stall " + parkingLabel + " is not currently occupied"
                    + " so the stall cannot be freed");
        gates[externalToInternalIndex(parkingLabel)] = null;
    }

    /**
     * Return a String representation of the properties of the airport.
     *
     * @return a String representation of the properties of the airport
     */
    public String toString() {
        String result = "\nAirport " + name + " with capacity " + gates.length
                + " has the following flights: ";
        for (int i = 0; i < gates.length; i++) {
            result = result + "\ngate " + internalToExternalLabel(i) + ": ";
            if (gates[i] != null)
                result = result + gates[i].toString();
        }
        return result + "\n";
    }

    /**
     * Is gateLabel a valid external label for a gate?
     *
     * @param gateLabel an int to be tested to determined whether it is a valid label
     *                 for a gate (from the external/user perspective)
     * @return is gateLabel a valid external label for a gate?
     * <p>
     * This is a helper method for testing pre-conditions students were not required to implement it
     */
    public boolean isValidLabel(int gateLabel) {
        return gateLabel >= minGateLabel && gateLabel <= minGateLabel + gates.length - 1;
    }


    /**
     * A method to test the class.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        int numErrors = 0;

        // testing all the methods with one instance of a Airport
        Airport w = new Airport("surgery", 200, 210, 200, 210);

        if (!w.getName().equals("surgery")) {
            System.out.println("The constructor or getName failed.");
            numErrors++;
        }

        if (w.getMinGateLabel() != 200) {
            System.out.println("The constructor or getMinGateLabel failed.");
            numErrors++;
        }

        if (w.getMaxGateLabel() != 210) {
            System.out.println("The constructor or getMaxGateLabel failed.");
            numErrors++;
        }


        if (!w.isValidLabel(200) || !w.isValidLabel(201)
                || !w.isValidLabel(210) || !w.isValidLabel(209)
                || w.isValidLabel(199)
                || w.isValidLabel(211)) {
            System.out.println("isValidLabel failed.");
            numErrors++;
        }

        if (w.internalToExternalLabel(w.externalToInternalIndex(200)) != 200
                || w.internalToExternalLabel(w.externalToInternalIndex(210)) != 210
                || w.internalToExternalLabel(w.externalToInternalIndex(205)) != 205) {
            System.out.println("internalToExternalLabel failed.");
            numErrors++;
        }

        if (w.externalToInternalIndex(w.internalToExternalLabel(0)) != 0
                || w.externalToInternalIndex(w.internalToExternalLabel(w.gates.length - 1)) != w.gates.length - 1
                || w.externalToInternalIndex(w.internalToExternalLabel(w.gates.length / 2)) != w.gates.length / 2) {
            System.out.println("externalToInternalIndex failed.");
            numErrors++;
        }

        if (w.externalToInternalIndex(200) != 0) {
            System.out.println("Minimum external label was not converted to 0.");
            numErrors++;
        }
        if (w.externalToInternalIndex(210) != w.gates.length - 1) {
            System.out.println("Maximum external label was not converted "
                    + "to last location of the array.");
            numErrors++;
        }
        if (w.internalToExternalLabel(0) != 200) {
            System.out.println("Minimum internal index was not converted to first gate label.");
            numErrors++;
        }
        if (w.internalToExternalLabel(w.gates.length - 1) != 210) {
            System.out.println("Maximum internal index was not converted "
                    + "to last gate label.");
            numErrors++;
        }

        if (w.isOccupied(205)) {
            System.out.println("Function isOccupied incorrectly returns that gate 205 is occupied.");
            numErrors++;
        }

        Flight p = new Flight("testAir", "123","Saskatoon",null, null,0);
        w.assignFlightToGate(p, 205);
        if (!w.isOccupied(205)) {
            System.out.println("assignFlightToGate() or isOccupied() failed: isOccupied incorrectly returns that gate 205 is not occupied.");
            numErrors++;
        }
        if (w.getFlight(205) != p) {
            System.out.println("getFlight() or isOccupied() failed: Flight Pete should be in gate 205, but " + w.getFlight(205) + " is.");
            numErrors++;
        }
        if (!w.getFlight(205).toString().equals("Pete")) {
            System.out.println("getFlight() or isOccupied() failed: Pete should be in gate 205, but " + w.getFlight(205) + " is.");
            numErrors++;
        }


        String expected = "\nAirport surgery with capacity 11 has the following flights: \n" +
                "gate 200: \n" +
                "gate 201: \n" +
                "gate 202: \n" +
                "gate 203: \n" +
                "gate 204: \n" +
                "gate 205: Pete\n" +
                "gate 206: \n" +
                "gate 207: \n" +
                "gate 208: \n" +
                "gate 209: \n" +
                "gate 210: \n";
        if (!w.toString().equals(expected)) {
            System.out.println("Error in toString() method. Expected: " + expected + "But returned: " + w);
            numErrors++;
        }


        // retest all the methods on a second instance of the class
        w = new Airport("ER", 1, 3, 1, 3);

        if (!w.getName().equals("ER")) {
            System.out.println("The constructor or getName failed.");
            numErrors++;
        }

        if (w.getMinGateLabel() != 1) {
            System.out.println("The constructor or getMinGateLabel failed.");
            numErrors++;
        }

        if (w.getMaxGateLabel() != 3) {
            System.out.println("The constructor or getMaxGateLabel failed.");
            numErrors++;
        }


        if (!w.isValidLabel(1) || !w.isValidLabel(2)
                || !w.isValidLabel(3)
                || w.isValidLabel(0)
                || w.isValidLabel(4)) {
            System.out.println("isValidLabel failed.");
            numErrors++;
        }

        if (w.internalToExternalLabel(w.externalToInternalIndex(1)) != 1
                || w.internalToExternalLabel(w.externalToInternalIndex(2)) != 2
                || w.internalToExternalLabel(w.externalToInternalIndex(3)) != 3) {
            System.out.println("internalToExternalLabel failed.");
            numErrors++;
        }

        if (w.externalToInternalIndex(w.internalToExternalLabel(0)) != 0
                || w.externalToInternalIndex(w.internalToExternalLabel(w.gates.length - 1)) != w.gates.length - 1
                || w.externalToInternalIndex(w.internalToExternalLabel(w.gates.length / 2)) != w.gates.length / 2) {
            System.out.println("externalToInternalIndex failed.");
            numErrors++;
        }

        if (w.externalToInternalIndex(1) != 0) {
            System.out.println("Minimum external label was not converted to 0.");
            numErrors++;
        }
        if (w.externalToInternalIndex(3) != w.gates.length - 1) {
            System.out.println("Maximum external label was not converted "
                    + "to last location of the array.");
            numErrors++;
        }
        if (w.internalToExternalLabel(0) != 1) {
            System.out.println("Minimum internal index was not converted to first gate label.");
            numErrors++;
        }
        if (w.internalToExternalLabel(w.gates.length - 1) != 3) {
            System.out.println("Maximum internal index was not converted "
                    + "to last gate label.");
            numErrors++;
        }

        if (w.isOccupied(2)) {
            System.out.println("Function isOccupied incorrectly returns that gate 2 is occupied.");
            numErrors++;
        }

       // p = new Flight("Dan", "789456");
        w.assignFlightToGate(p, 1);
        if (!w.isOccupied(1)) {
            System.out.println("assignFlightToGate() or isOccupied() failed: isOccupied incorrectly returns that gate 1 is not occupied.");
            numErrors++;
        }
        if (w.getFlight(1) != p) {
            System.out.println("getFlight() or isOccupied() failed: Flight Dan should be in gate 1, but " + w.getFlight(1) + " is.");
            numErrors++;
        }

        // Testing availableGates //
        ArrayList<Integer> aList = new ArrayList<>();
        aList.add(2); aList.add(3);

        if (!aList.equals(w.availableGates())) {
            numErrors++;
            System.out.println("Problem with availableGates");
        }

        // Testing freeGate //

        try {
            w.freeGate(2);
            numErrors++;
            System.out.println("Problem with freeGate; expecting an exception");
        }
        catch (IllegalStateException ignored) {}

        try {
            w.freeGate(6);
            numErrors++;
            System.out.println("Problem with freeGate; expecting an exception");
        }
        catch (IllegalArgumentException ignored) {}

        w.freeGate(1);
        if(w.isOccupied(1)) {
            System.out.println("Problem with freeGate");
            numErrors++;
        }

        // Testing availableGates and freeGate
        aList.clear();
        aList.add(1); aList.add(2); aList.add(3);

        if (!aList.equals(w.availableGates())) {
            numErrors++;
            System.out.println("Problem with availableGates or freeGate");
        }

        System.out.println("The number of errors found is " + numErrors);
    }




}
