package Command;

/*
  Portions of this file are inspired by the SystemState class from CMPT 270.
  Permission to use that file as a starting point has been obtained from
  the course instructor of CMPT 270 and 370.

  CMPT 270 Course material
  Copyright (c) 2020
  All rights reserved.
 */

import java.util.Map;

import Entities.Employee;
import Entities.Passenger;
import Singleton.AirportAccess;
import Singleton.EmployeeMapAccess;
import Singleton.PassengerMapAccess;
import javafx.scene.control.Alert;

/**
 * Prints the current state of the system to console output.
 */
public class SystemState implements Command{
    @Override
    public void execute() {
        String outString = "\nThe Airport system has the following employees registered:\n";
        String temp = "";
        for (Map.Entry<String, Employee> entry : EmployeeMapAccess.getInstance().entrySet()) {
            temp += entry.getValue();
        }
        outString += (temp.equals("")) ? "None\n" : temp;


        outString += "\nThe airport system has the following passengers registered:\n";
        temp = "";
        for (Map.Entry<String, Passenger> entry : PassengerMapAccess.getInstance().entrySet()) {
            temp += entry.getValue();
        }
        outString += (temp.equals("")) ? "None\n" : temp;


        outString += AirportAccess.getInstance();

        new Alert(Alert.AlertType.INFORMATION,outString);

    }
}

