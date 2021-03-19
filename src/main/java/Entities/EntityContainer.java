package Entities;

/*
  Portions of this file are inspired by the Ward class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class EntityContainer<I> {

    private final String name;


    private final int minStallLabel;


    private final ObservableList<I> stalls;


    private IntegerProperty count = new SimpleIntegerProperty(0);


    public EntityContainer(String wName, int wMinStallLabel, int wMaxStallLabel) {
        if (wName == null || wName.equals(""))
            throw new IllegalArgumentException("The name of a Container cannot be null or empty.  "
                    + "It is " + wName);
        if (wMinStallLabel < 0 || wMaxStallLabel < wMinStallLabel)
            throw new IllegalArgumentException("The stall labels " + wMinStallLabel + " and " + wMaxStallLabel
                    + " are invalid as they cannot be negative, and must have at least one stall.");

        name = wName;
        minStallLabel = wMinStallLabel;
       // stalls = (I[]) new Object[wMaxStallLabel - wMinStallLabel + 1];
        stalls = FXCollections.observableArrayList();
        for (int i = 0; i < wMaxStallLabel - wMinStallLabel + 1; i++) {
            stalls.add(null);
        }
    }





    public String getName() {
        return name;
    }


    public int getMinStallLabel() {
        return minStallLabel;
    }


    public int getMaxStallLabel() {
        return minStallLabel + stalls.size() - 1;
    }


    private int externalToInternalIndex(int stallLabel) {
        if (isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        return stallLabel - minStallLabel;
    }


    private int internalToExternalLabel(int arrayIndex) {
        if (arrayIndex < 0 || arrayIndex >= stalls.size())
            throw new IllegalArgumentException("The value " + arrayIndex +
                    " is not a valid index for an array of length " + stalls.size() + ".");

        return arrayIndex + minStallLabel;
    }


    public boolean isOccupied(int stallLabel) {
        if (isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        return stalls.get(externalToInternalIndex(stallLabel)) != null;
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
        for (int i = 0; i < stalls.size(); i++) {
            if (stalls.get(i) != null && stalls.get(i).equals(entity)) {
                return i;
            }
        }
        return -1;
    }


    public I getEntity(int stallLabel) {
        if (isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        if (!isOccupied(stallLabel))
            throw new IllegalStateException("Stall " + stallLabel + " is not currently occupied"
                    + " so cannot get its entity");
        return stalls.get(externalToInternalIndex(stallLabel));
    }


    public void assignEntityToStall(I e, int stallLabel) {
        if (isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        if (isOccupied(stallLabel))
            throw new IllegalStateException("Stall " + stallLabel + " is currently occupied by "
                    + stalls.get(externalToInternalIndex(stallLabel))
                    + " so cannot be assigned to another Entity");
        count.set(count.get()+1);
        stalls.set(externalToInternalIndex(stallLabel), e);
    }


    public ArrayList<Integer> availableStalls() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < stalls.size(); i++) {
            if (stalls.get(i) == null) {
                temp.add(internalToExternalLabel(i));
            }
        }
        return temp;
    }

    public int firstAvailableStall() {
        for (int i = 0; i < stalls.size(); i++) {
            if (stalls.get(i) == null) {
                return internalToExternalLabel(i);
            }
        }
        return -1;
    }




    public void freeStall(int stallLabel) {
        if (isValidLabel(stallLabel))
            throw new IllegalArgumentException("The value " + stallLabel
                    + " is not a valid label for a stall in the Container.");

        if (!isOccupied(stallLabel))
            throw new IllegalStateException("Stall " + stallLabel + " is not currently occupied"
                    + " so the stall cannot be freed");
        stalls.set(externalToInternalIndex(stallLabel), null);
        count.set(count().get()-1);
    }


    public String toString() {
        String result = "\nEntities.EntityContainer " + name + " with capacity " + stalls.size()
                + " has the following Entities: ";
        for (int i = 0; i < stalls.size(); i++) {
            result = result + "\nstall " + internalToExternalLabel(i) + ": ";
            if (stalls.get(i) != null)
                result = result + stalls.get(i).toString();
        }
        return result + "\n";
    }


    public boolean isValidLabel(int stallLabel) {
        return stallLabel < minStallLabel || stallLabel > minStallLabel + stalls.size() - 1;
    }

    public IntegerProperty count() {
        return count;
    }


    public ObservableList<I> getObservableList () {
        return stalls;
    }

    public static void main(String[] args) {
        EntityContainer<Integer> C = new EntityContainer<>("name", 1,5);
        System.out.println(C.firstAvailableStall());
        System.out.println(C.availableStalls());

    }

}
