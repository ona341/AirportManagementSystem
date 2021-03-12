package IO;

/**
 * An IO interface using the singleton pattern.
 */
public class IOAccess {

    /**
    * The io interface
     */
    private static InputOutputInterface io = null;

    /**
     * Private do nothing constructor
     */
    private IOAccess(){}

    /**
     * Gets instance of the interface.
     *
     * @return the instance
     */
    public static InputOutputInterface getInstance() {
        if (io == null) {
            io = new DialogIO();
            String[] choices = {"DialogIO","ConsoleIO"};
            int choice = io.readChoice(choices);
            if (choice == 1) {
                io = new ConsoleIO();
            }
        }
        return io;
    }
}
