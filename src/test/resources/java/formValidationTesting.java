package java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class formValidationTesting {



    public boolean testName(String name) {
        // checks for empty input
        // regex looks for anything that is not a letter in name and allows for spaces for first and last names
        return !(name.isEmpty()) && name.matches("[A-Za-z\\s]{2,}");
    }

    public boolean testEmail(String email) {
        // checks for empty input
        // regex looks for correct email format. Essentially, it must be in the format user@domain.com
        String EMAIL_REGEX = "^[0-9?A-z0-9?]+(\\.)?[0-9?A-z0-9?]+@[A-z]+\\.[A-z]{2}.?[A-z]{0,3}$";
        return (!(email.isEmpty()) && email.matches(EMAIL_REGEX));
    }

    public boolean testTime(String time){
        // checks for empty input
        // regex looks for valid time format. Must be in the form of hour::minutes:seconds
        return(!(time.isEmpty()) && time.matches("^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$"));
    }

    public boolean testPassword(String password){
        // checks for empty input
        // regex looks for password of atleast length 6 that contains, one uppercase,
        // one lowercase letter, and one number
        return(!(password.isEmpty()) && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$"));
    }


    public boolean testID(String id){
        // Also checks for empty input
        // may potentially be used for id numbers to ensure only numbers are inputted
        return(!(id.isEmpty()) && id.matches("[\\d\\s]+"));
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

    @Test
    void idTest(){
        assertTrue(testID("123456789"));
        assertTrue(testID("306 914 0210"));
        assertTrue(testID("233223441"));
        assertTrue(testID("00000000"));

        assertFalse(testID("123HELLO456"));
        assertFalse(testID("123##456"));
        assertFalse(testID(""));
        assertFalse(testID(".   "));
        assertFalse(testID("3B"));
    }

    @Test
    void timeTest(){
        assertTrue(testTime("00:00:00"));
        assertTrue(testTime("23:00:00"));
        assertTrue(testTime("23:59:00"));
        assertTrue(testTime("23:59:59"));
        assertTrue(testTime("01:00:00"));

        assertFalse(testTime("24:00:00"));
        assertFalse(testTime("1:00:00"));
        assertFalse(testTime("-1:1000:00"));
        assertFalse(testTime("12:-54:34"));
        assertFalse(testTime("12:-54:34"));
        assertFalse(testTime("13:54:63"));

    }

    @Test
    void passwordTest(){
        assertTrue(testPassword("mohamedB1"));
        assertTrue(testPassword("hElLo123"));
        assertTrue(testPassword("passworD70"));
        assertTrue(testPassword("PASs12"));
        assertTrue(testPassword("passwordpasswordpasswordS1"));

        assertFalse(testPassword("HELLO123"));
        assertFalse(testPassword("hello123"));
        assertFalse(testPassword("passwordPassword"));
        assertFalse(testPassword("123456"));
        assertFalse(testPassword(""));
        assertFalse(testPassword("m     B"));
        assertFalse(testPassword("HeLo123!!!"));

    }
}
