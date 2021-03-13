package Command;

import java.sql.SQLException;

public interface Command {

    /**
     * Carry out the command!
     */
    void execute() throws SQLException;
}
