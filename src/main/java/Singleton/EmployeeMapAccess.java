package Singleton;
/*
  CMPT 270 A5Q4
  @author Blake Stadnyk; 11195866 - BJS645
 */

import Entities.Employee;

import java.util.TreeMap;

/**
 * A Employee Map using the singleton pattern.
 */
public class EmployeeMapAccess {

    /**
     * The singleton employee map
     */
    private static final TreeMap<String, Employee> employees = new TreeMap<>();

    /**
     * Private do nothing constructor
     */
    private EmployeeMapAccess() {}

    /**
     * Gets the instance of the map.
     *
     * @return the map instance
     */
    public static TreeMap<String, Employee> getInstance() {
        return employees;
    }
}
