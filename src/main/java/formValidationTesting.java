import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class formValidationTesting {

    public boolean testName(String name) {
        // Also checks for empty input
        // regex looks for anything that is not a letter in name and allows for spaces for first and last names
        return !(name.isEmpty()) && name.matches("[A-Za-z\\s]{2,}");
    }

    public boolean testEmail(String email) {

        String EMAIL_REGEX = "^[0-9?A-z0-9?]+(\\.)?[0-9?A-z0-9?]+@[A-z]+\\.[A-z]{2}.?[A-z]{0,3}$";
        return (!(email.isEmpty()) && email.matches(EMAIL_REGEX));
    }


    /**
     * The tests will use a mixture of assertTrues and assertFalses when the test is true and passes
     * the assertion, the tests will continue. Only when a test doesn't align with its assertion, will it
     * throw an error.
     */
    @Test
    void nameTest(){
        assertTrue(testName("Mohamed Bensaleh"));
        assertTrue(testName("M Bensaleh"));
        assertTrue(testName("M b"));
        assertTrue(testName("John F Kennedy"));
        assertTrue(testName("Blake Mohamed Kelly Onyinye Ben"));
        assertTrue(testName("MBensaleh"));
        assertTrue(testName("Kelly"));
        assertFalse(testName(""));
        assertFalse(testName("&^*(#"));
        assertFalse(testName("Mo. Bensaleh"));
    }

    @Test
    void emailTest(){
        assertTrue(testEmail("mohamed@usask.ca"));
        assertTrue(testEmail("mo123.bensaleh@hello.com"));
        assertTrue(testEmail("mo123.bensaleh@hello.com"));
        assertTrue(testEmail("hello123hello@hello.org"));
        assertFalse(testEmail(""));
        assertFalse(testEmail("   "));
        assertFalse(testEmail("mohamed.com"));
        assertFalse(testEmail("mohamed@.com"));
        assertFalse(testEmail("mohamed@usask"));
        assertFalse(testEmail("mo   hamed@usask"));


    }
}
