package model;

import model.people.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Doctor class
 */

public class DoctorTest {

    private Doctor testDoctor;
    private Doctor testDoctor2;

    @BeforeEach
    public void runBefore() {
        testDoctor = new Doctor("Donald", "Chen");
        testDoctor2 = new Doctor("Koerner", "Library", "0600-1400");
    }

    @Test
    public void testConstructor() {
        assertEquals("Donald Chen", testDoctor.getFullName());
        assertEquals("Doctor", testDoctor.getPosition());
        assertEquals("DonaldChen", testDoctor.getNameForScanner());
        assertEquals("Koerner Library", testDoctor2.getFullName());
        assertEquals("0600-1400", testDoctor2.getShift());
    }

}
