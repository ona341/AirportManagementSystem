package IO;/*
  CMPT 270 A5Q3
  @author Blake Stadnyk; 11195866 - BJS645
 */

import javax.swing.*;

/**
 * Finishes implementing the InputOutputInterface using JOptionPane for DialogIO
 */
public class DialogIO extends AbstractDialogIO {
    /**
     * Display a prompt and read the string entered.
     *
     * @param prompt the string to be displayed as a prompt
     * @return the String read
     */
    @Override
    public String readString(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }

    /**
     * Display a prompt and read the int entered.
     *
     * @param prompt the string to be displayed as a prompt
     * @return the int read
     */
    @Override
    public int readInt(String prompt) {
        String input = JOptionPane.showInputDialog(prompt);
        if (input == null) {return 0;}
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return readInt(prompt);
        }
    }

    /**
     * Output the String parameter.
     *
     * @param outString the string whose value is to be displayed
     */
    @Override
    public void outputString(String outString) {
        JOptionPane.showMessageDialog(null,outString);
    }

    /**
     * Main method for simple tests
     *
     */
    public static void main(String[] args) {
        //Create new DialogIO object
        InputOutputInterface io = new DialogIO();

        //Testing readString()
        io.outputString(io.readString("Enter a string"));

        //Testing readInt()
        io.outputString(Integer.toString(io.readInt("Enter an Int")));

        //Testing readChoice()
        String[] choices = {"1: Choice 1", "2: Choice 2"};
        io.outputString(Integer.toString(io.readChoice(choices)));
    }
}
