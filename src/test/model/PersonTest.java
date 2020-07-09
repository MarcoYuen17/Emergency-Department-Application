package model;

import model.people.Staff;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Person class
 */

public class PersonTest {

    private Staff testStaff = new Staff("Testy", "Tester");

    @Test
    public void testGetFullName() {
        assertEquals("Testy Tester", testStaff.getFullName());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Testy", testStaff.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Tester", testStaff.getLastName());
    }
}
