package Singleton;/*
  CMPT 270 A5Q4
  @author Blake Stadnyk; 11195866 - BJS645
 */

import Entities.PersonContainer;

/**
 * A personContainer using the singleton pattern.
 */
public class ParkingAccess {

    /**
     * The PersonContainer.
     */
    static PersonContainer personContainer = null;

    /**
     * Private do nothing constructor
     */
    private ParkingAccess() {}

    /**
     * Initialize the personContainer.
     *
     * @param name  the name of the personContainer
     * @param first the first stall number
     * @param last  the last stall number
     * @throws IllegalStateException the illegal state exception
     */
    public static void initialize(String name, int first, int last) throws IllegalStateException{
        if (personContainer != null) {
            throw new IllegalStateException("A personContainer has already been created");
        }
        else {
            personContainer = new PersonContainer(name, first, last);
        }
    }

    /**
     * Gets the personContainer instance.
     *
     * @return the personContainer instance
     * @throws IllegalStateException the illegal state exception
     */
    public static PersonContainer getInstance() throws IllegalStateException{
        if (personContainer == null) {
            throw new IllegalStateException("A personContainer has not been created");
        }
        else {
            return personContainer;
        }
    }
}
