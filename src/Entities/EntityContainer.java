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
 * A Container with a specified number of stalls with consecutive labels.
 */
public class EntityContainer<I> {
    /**
     * The name of this Container.
     */
    private final String name;

    /**
     * The (external) label of the first stall of the Container.
     */
    private final int minStallLabel;

    /**
     * An array to represent the stalls of the Container.  Each stall is empty (null)
     * or else has a Entity in it.
     */
    private final I[] stalls;


    private int count = 0;

    /**
     * Initialize the Container with the name given, and with stalls those labels are
     * the consecutive integers from minStallLabel to maxStallLabel.
     *
     * @param wName        the name of the Container
     * @param wMinStallLabel the label of the first stall in the Container
     * @param wMaxStallLabel the label of the last stall in the Container
     * @precond wName != null && !wName.equals("") && wMinStallLabel >= 0 && wMaxStallLabel >= wMinStallLabel
     */
    public EntityContainer(String wName, int wMinStallLabel, int wMaxStallLabel) {
        if (wName == null || wName.equals(""))
            throw new IllegalArgumentException("The name of a Container cannot be null or empty.  "
                    + "It is " + wName);
        if (wMinStallLabel < 0 || wMaxStallLabel < wMinStallLabel)
            throw new IllegalArgumentException("The stall labels " + wMinStallLabel + " and " + wMaxStallLabel
                    + " are invalid as they cannot be negative, and must have at least one stall.");

        name = wName;
        minStallLabel = wMinStallLabel;
        stalls = (I[]) new Object[wMaxStallLabel - wMinStallLabel + 1];
    }




    /**
     * Return the name of this Container.
     *
     * @return the name of this Container
     */
    public String getName() {
        return name;
    }

    /**
     * Return the smallest label for a stall on the Container.
     *
     * @return the smallest Label for a stall on the Container
     */
    public int getMinStallLabel() {
        return minStallLabel;
    }

    /**
     * Return the largest label for a stall on the Container.
     *
     * @return the largest label for a stall on the Container
     */
    public int getMaxStallLabel() {
        return minStallLabel + stalls.length - 1;
    }

    /**
     * Return the internal/array index of the stall corresponding to the external label.
     *
     * @param stallLabel the label of a stall from the external/user perspective
     * @return the internal/array index of the stall corresponding to the external label
     * @precond isValidLabel(stallLabel)
     */
    private int externalToInternalIndex(int stallLabel) {
        if (!isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        return stallLabel - minStallLabel;
    }

    /**
     * Return the external/user label of the stall corresponding to the internal index.
     *
     * @param arrayIndex the index of a location in the stalls array
     * @return the external/user label of the stall corresponding to the internal index
     * @precond 0 <= arrayIndex < stalls.length
     */
    private int internalToExternalLabel(int arrayIndex) {
        if (arrayIndex < 0 || arrayIndex >= stalls.length)
            throw new IllegalArgumentException("The value " + arrayIndex +
                    " is not a valid index for an array of length " + stalls.length + ".");

        return arrayIndex + minStallLabel;
    }

    /**
     * Is the specified stall occupied?
     *
     * @param stallLabel the label of the stall to be tested for being occupied
     * @return is the specified stall occupied?
     * @precond isValidLabel(stallLabel)
     */
    public boolean isOccupied(int stallLabel) {
        if (!isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        return stalls[externalToInternalIndex(stallLabel)] != null;
    }

    /**
     * Determine if a Entity is in stalls
     *
     * @param entity        the entity to search for
     * return true if the entity is found; false otherwise
     */
    public boolean hasEntity(I entity) {
        for (I e : stalls) {
            if (e != null && e.equals(entity)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the index of a entity if it exists
     *
     * @param entity the entity to search for
     * return an integer represents the index of the entity found; -1 if not found.
     */
    public int getEntityInternalIndex(I entity) {
        for (int i = 0; i < stalls.length; i++) {
            if (stalls[i] != null && stalls[i].equals(entity)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Return the passenger in the specified stall.
     *
     * @param stallLabel the label of the stall that has the passenger to be retrieved
     * @return the passenger in the specified stall
     * @precond isValidLabel(stallLabel) && isOccupied(stallLabel)
     */
    public I getEntity(int stallLabel) {
        if (!isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        if (!isOccupied(stallLabel))
            throw new IllegalStateException("Stall " + stallLabel + " is not currently occupied"
                    + " so cannot get its entity");
        return stalls[externalToInternalIndex(stallLabel)];
    }

    /**
     * Assign the specified Entity to the specified stall.
     *
     * @param e        the entity to be assigned a stall
     * @param stallLabel the label of the stall that the entity is to be assigned
     * @precond isValidLabel(stallLabel) && !isOccupied(stallLabel)
     */
    public void assignEntityToStall(I e, int stallLabel) {
        if (!isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        if (isOccupied(stallLabel))
            throw new IllegalStateException("Stall " + stallLabel + " is currently occupied by "
                    + stalls[externalToInternalIndex(stallLabel)]
                    + " so cannot be assigned to another Entity");
        count++;
        stalls[externalToInternalIndex(stallLabel)] = e;
    }

    /**
     * Finds available stalls.
     *
     * @return a list of available stalls
     */
    public ArrayList<Integer> availableStalls() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < stalls.length ; i++) {
            if (stalls[i] == null) {
                temp.add(internalToExternalLabel(i));
            }
        }
        return temp;
    }

    public int firstAvailableStall() {
        for (int i = 0; i < stalls.length ; i++) {
            if (stalls[i] == null) {
                return internalToExternalLabel(i);
            }
        }
        return -1;
    }



    /**
     * Frees a stall.
     *
     * @param stallLabel the stall label
     * @precond isValidLabel(stallLabel) && isOccupied(stallLabel)
     */
    public void freeStall(int stallLabel) {
        if (!isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        if (!isOccupied(stallLabel))
            throw new IllegalStateException("Stall " + stallLabel + " is not currently occupied"
                    + " so the stall cannot be freed");
        stalls[externalToInternalIndex(stallLabel)] = null;
        count--;
    }

    /**
     * Return a String representation of the properties of the Container.
     *
     * @return a String representation of the properties of the Container
     */
    public String toString() {
        String result = "\nEntities.EntityContainer " + name + " with capacity " + stalls.length
                + " has the following Entities: ";
        for (int i = 0; i < stalls.length; i++) {
            result = result + "\nstall " + internalToExternalLabel(i) + ": ";
            if (stalls[i] != null)
                result = result + stalls[i].toString();
        }
        return result + "\n";
    }

    /**
     * Is stallLabel a valid external label for a stall?
     *
     * @param stallLabel an int to be tested to determined whether it is a valid label
     *                 for a stall (from the external/user perspective)
     * @return is stallLabel a valid external label for a stall?
     * <p>
     * This is a helper method for testing pre-conditions students were not required to implement it
     */
    public boolean isValidLabel(int stallLabel) {
        return stallLabel >= minStallLabel && stallLabel <= minStallLabel + stalls.length - 1;
    }

    public int count() {
        return count;
    }


    /**
     * A method to test the class.
     *
     * @param args not used

    public static void main(String[] args) {
    int numErrors = 0;

    // testing all the methods with one instance of a Entities.EntityContainer
    EntityContainer w = new EntityContainer("surgery", 200, 210);

    if (!w.getName().equals("surgery")) {
    System.out.println("The constructor or getName failed.");
    numErrors++;
    }

    if (w.getMinStallLabel() != 200) {
    System.out.println("The constructor or getMinStallLabel failed.");
    numErrors++;
    }

    if (w.getMaxStallLabel() != 210) {
    System.out.println("The constructor or getMaxStallLabel failed.");
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
    || w.externalToInternalIndex(w.internalToExternalLabel(w.stalls.length - 1)) != w.stalls.length - 1
    || w.externalToInternalIndex(w.internalToExternalLabel(w.stalls.length / 2)) != w.stalls.length / 2) {
    System.out.println("externalToInternalIndex failed.");
    numErrors++;
    }

    if (w.externalToInternalIndex(200) != 0) {
    System.out.println("Minimum external label was not converted to 0.");
    numErrors++;
    }
    if (w.externalToInternalIndex(210) != w.stalls.length - 1) {
    System.out.println("Maximum external label was not converted "
    + "to last location of the array.");
    numErrors++;
    }
    if (w.internalToExternalLabel(0) != 200) {
    System.out.println("Minimum internal index was not converted to first stall label.");
    numErrors++;
    }
    if (w.internalToExternalLabel(w.stalls.length - 1) != 210) {
    System.out.println("Maximum internal index was not converted "
    + "to last stall label.");
    numErrors++;
    }

    if (w.isOccupied(205)) {
    System.out.println("Function isOccupied incorrectly returns that stall 205 is occupied.");
    numErrors++;
    }

    Passenger p = new Passenger("Pete", "123456");
    w.assignPassengerToStall(p, 205);
    if (!w.isOccupied(205)) {
    System.out.println("assignPassengerToStall() or isOccupied() failed: isOccupied incorrectly returns that stall 205 is not occupied.");
    numErrors++;
    }
    if (w.getPassenger(205) != p) {
    System.out.println("getPassenger() or isOccupied() failed: Entities.Passenger Pete should be in stall 205, but " + w.getPassenger(205) + " is.");
    numErrors++;
    }
    if (!w.getPassenger(205).getName().equals("Pete")) {
    System.out.println("getPassenger() or isOccupied() failed: Pete should be in stall 205, but " + w.getPassenger(205) + " is.");
    numErrors++;
    }


    String expected = "\nEntities.EntityContainer surgery with capacity 11 has the following passengers: \n" +
    "stall 200: \n" +
    "stall 201: \n" +
    "stall 202: \n" +
    "stall 203: \n" +
    "stall 204: \n" +
    "stall 205: Pete\n" +
    "stall 206: \n" +
    "stall 207: \n" +
    "stall 208: \n" +
    "stall 209: \n" +
    "stall 210: \n";
    if (!w.toString().equals(expected)) {
    System.out.println("Error in toString() method. Expected: " + expected + "But returned: " + w);
    numErrors++;
    }


    // retest all the methods on a second instance of the class
    w = new EntityContainer("ER", 1, 3);

    if (!w.getName().equals("ER")) {
    System.out.println("The constructor or getName failed.");
    numErrors++;
    }

    if (w.getMinStallLabel() != 1) {
    System.out.println("The constructor or getMinStallLabel failed.");
    numErrors++;
    }

    if (w.getMaxStallLabel() != 3) {
    System.out.println("The constructor or getMaxStallLabel failed.");
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
    || w.externalToInternalIndex(w.internalToExternalLabel(w.stalls.length - 1)) != w.stalls.length - 1
    || w.externalToInternalIndex(w.internalToExternalLabel(w.stalls.length / 2)) != w.stalls.length / 2) {
    System.out.println("externalToInternalIndex failed.");
    numErrors++;
    }

    if (w.externalToInternalIndex(1) != 0) {
    System.out.println("Minimum external label was not converted to 0.");
    numErrors++;
    }
    if (w.externalToInternalIndex(3) != w.stalls.length - 1) {
    System.out.println("Maximum external label was not converted "
    + "to last location of the array.");
    numErrors++;
    }
    if (w.internalToExternalLabel(0) != 1) {
    System.out.println("Minimum internal index was not converted to first stall label.");
    numErrors++;
    }
    if (w.internalToExternalLabel(w.stalls.length - 1) != 3) {
    System.out.println("Maximum internal index was not converted "
    + "to last stall label.");
    numErrors++;
    }

    if (w.isOccupied(2)) {
    System.out.println("Function isOccupied incorrectly returns that stall 2 is occupied.");
    numErrors++;
    }

    p = new Passenger("Dan", "789456");
    w.assignPassengerToStall(p, 1);
    if (!w.isOccupied(1)) {
    System.out.println("assignPassengerToStall() or isOccupied() failed: isOccupied incorrectly returns that stall 1 is not occupied.");
    numErrors++;
    }
    if (w.getPassenger(1) != p) {
    System.out.println("getPassenger() or isOccupied() failed: Entities.Passenger Dan should be in stall 1, but " + w.getPassenger(1) + " is.");
    numErrors++;
    }

    // Testing availableStalls //
    ArrayList<Integer> aList = new ArrayList<>();
    aList.add(2); aList.add(3);

    if (!aList.equals(w.availableStalls())) {
    numErrors++;
    System.out.println("Problem with availableStalls");
    }

    // Testing freeStall //

    try {
    w.freeStall(2);
    numErrors++;
    System.out.println("Problem with freeStall; expecting an exception");
    }
    catch (IllegalStateException ignored) {}

    try {
    w.freeStall(6);
    numErrors++;
    System.out.println("Problem with freeStall; expecting an exception");
    }
    catch (IllegalArgumentException ignored) {}

    w.freeStall(1);
    if(w.isOccupied(1)) {
    System.out.println("Problem with freeStall");
    numErrors++;
    }

    // Testing availableStalls and freeStall
    aList.clear();
    aList.add(1); aList.add(2); aList.add(3);

    if (!aList.equals(w.availableStalls())) {
    numErrors++;
    System.out.println("Problem with availableStalls or freeStall");
    }

    System.out.println("The number of errors found is " + numErrors);
    }

     **/
}
