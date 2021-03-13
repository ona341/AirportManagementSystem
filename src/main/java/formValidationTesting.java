import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class formValidationTesting {
    /**
     * Comes from textField input Used in: - ClientSignUpController x 1 - InstructorSignUpController x
     * 1 - ManagerSignUpController x 1 - OrganizationSignUpConrtoller x2
     *
     * @param name string
     * @return false if input is wrong
     */
    public boolean testName(String name) {
        // regex looks for a string space string, meaning users must enter a first and last name
        return (!(name.length() > 0) || name.matches("^\\s+$"));
    }

}
