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
    public void testGetName() {
        assertEquals("Testy Tester", testStaff.getFullName());
    }
}
