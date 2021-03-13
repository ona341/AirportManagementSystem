package Entities;

/*
  Portions of this file are inspired by the Ward class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class EntityContainer<I> {

    private final String name;


    private final int minStallLabel;


    private final I[] stalls;


    private int count = 0;


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





    public String getName() {
        return name;
    }


    public int getMinStallLabel() {
        return minStallLabel;
    }


    public int getMaxStallLabel() {
        return minStallLabel + stalls.length - 1;
    }


    private int externalToInternalIndex(int stallLabel) {
        if (!isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        return stallLabel - minStallLabel;
    }


    private int internalToExternalLabel(int arrayIndex) {
        if (arrayIndex < 0 || arrayIndex >= stalls.length)
            throw new IllegalArgumentException("The value " + arrayIndex +
                    " is not a valid index for an array of length " + stalls.length + ".");

        return arrayIndex + minStallLabel;
    }


    public boolean isOccupied(int stallLabel) {
        if (!isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        return stalls[externalToInternalIndex(stallLabel)] != null;
    }


    public boolean hasEntity(I entity) {
        for (I e : stalls) {
            if (e != null && e.equals(entity)) {
                return true;
            }
        }
        return false;
    }


    public int getEntityInternalIndex(I entity) {
        for (int i = 0; i < stalls.length; i++) {
            if (stalls[i] != null && stalls[i].equals(entity)) {
                return i;
            }
        }
        return -1;
    }


    public I getEntity(int stallLabel) {
        if (!isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        if (!isOccupied(stallLabel))
            throw new IllegalStateException("Stall " + stallLabel + " is not currently occupied"
                    + " so cannot get its entity");
        return stalls[externalToInternalIndex(stallLabel)];
    }


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


    public boolean isValidLabel(int stallLabel) {
        return stallLabel >= minStallLabel && stallLabel <= minStallLabel + stalls.length - 1;
    }

    public int count() {
        return count;
    }


    public ObservableList<I> getObservableList () {
        return FXCollections.observableArrayList(stalls);
    }


}
