package Command;

import java.sql.SQLException;

/**
 * Command interface with one method, execute
 */
public interface Command {

    /**
     * Carry out the command!
     */
    void execute() throws SQLException;
}
