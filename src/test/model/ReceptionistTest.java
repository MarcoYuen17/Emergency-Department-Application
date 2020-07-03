package model;

import model.people.Receptionist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Receptionist class
 */

public class ReceptionistTest {

    private Receptionist testReceptionist;
    private Receptionist testReceptionist2;

    @BeforeEach
    public void runBefore() {
        testReceptionist = new Receptionist("Wanda", "Oliynyk");
        testReceptionist2 = new Receptionist("Orchard", "Commons", "0500-1300");
    }

    @Test
    public void testConstructor() {
        assertEquals("Wanda Oliynyk", testReceptionist.getFullName());
        assertEquals("Receptionist", testReceptionist.getPosition());
        assertEquals("WandaOliynyk", testReceptionist.getNameForScanner());
        assertEquals("Orchard Commons", testReceptionist2.getFullName());
        assertEquals("0500-1300", testReceptionist2.getShift());
    }
}
