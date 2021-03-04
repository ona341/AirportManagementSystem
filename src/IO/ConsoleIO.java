package IO;/*
  CMPT 270 A5Q2
  @author Blake Stadnyk; 11195866
 */

import java.security.InvalidKeyException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class that implements the InputOutputInterface using console input output
 */
public class ConsoleIO implements InputOutputInterface {

    /**
     * The scanner object for system input
     */
    private final Scanner s = new Scanner(System.in);

    /**
     * Display a prompt and read the string entered.
     *
     * @param prompt the string to be displayed as a prompt
     * @return the String read
     */
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return s.nextLine();
    }

    /**
     * Display a prompt and read the int entered.
     *
     * @param prompt the string to be displayed as a prompt
     * @return the int read
     */
    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        while(true) {
            try {
                int i = s.nextInt();
                s.nextLine();
                return i;
            }
            catch (InputMismatchException e) {
                System.out.println("Could not interpret input as int. Try again ");
                s.nextLine();
            }
        }
    }

    /**
     * Display the list of options and read an int that is the index of one of the options. The
     * first option is the default.
     *
     * @param options an array with the options that are presented to the user
     * @return the int specifying the array index for the option selected by the user
     */
    @Override
    public int readChoice(String[] options) {
        for (String option: options) {
            System.out.println(option);
        }
        while(true) {
            try {
                int i = readInt("Make a selection from the options: ");
                if (i < 0 || i > options.length-1) {
                    throw new InvalidKeyException("The choice is not a valid option.");
                }
                return i;
            } catch (InvalidKeyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Output the String parameter.
     *
     * @param outString the string whose value is to be displayed
     */
    @Override
    public void outputString(String outString) {
        System.out.println(outString);
    }

    /**
     * Main method for simple testing
     *
     */
    public static void main(String[] args) {
        // Create a ConsoleIO object
        ConsoleIO c = new ConsoleIO();

        // Testing readString()
        String inputString = c.readString("Enter a string for testing:");
        System.out.println("The string stored by readString was:");
        c.outputString(inputString);

        // Testing readInt
        int inputInt = c.readInt("Enter a int for testing:");
        System.out.println("The int stored by readInt was:");
        c.outputString(Integer.toString(inputInt));

        // Testing readChoice
        String[] options = {"1: Option 1", "2: Option 2"};
        inputInt = c.readChoice(options);
        System.out.println("The int stored by readChoice was:");
        c.outputString(Integer.toString(inputInt));
    }
}
