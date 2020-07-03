package model;

import model.people.Doctor;
import model.people.Nurse;
import model.people.Receptionist;
import model.people.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Staff class
 */

public class StaffTest {

    private Nurse testNurse;
    private Doctor testDoctor;
    private Receptionist testReceptionist;
    private Doctor testDoctor2;
    private Staff testStaff;

    @BeforeEach
    public void runBefore() {
        testNurse = new Nurse("Nursy", "Nurse");
        testDoctor = new Doctor("Doctory", "Doctor");
        testReceptionist = new Receptionist("Clerky", "Clerk");
        testDoctor2 = new Doctor("Drink", "Water", "0100-0900");
        testStaff = new Staff("Plain", "Staff");
    }

    @Test
    public void testConstructor() {
        assertNull(testNurse.getShift());
        assertNull(testDoctor.getShift());
        assertNull(testReceptionist.getShift());
        assertNull(testStaff.getShift());
        assertNull(testStaff.getPosition());
        assertEquals("NursyNurse", testNurse.getNameForScanner());
        assertEquals("DoctoryDoctor", testDoctor.getNameForScanner());
        assertEquals("ClerkyClerk", testReceptionist.getNameForScanner());
        assertEquals("0100-0900", testDoctor2.getShift());
        assertEquals("Doctor", testDoctor2.getPosition());
        assertEquals("Drink Water", testDoctor2.getFullName());
        assertEquals("Plain Staff", testStaff.getFullName());
        assertEquals("Nursy Nurse", testNurse.getFullName());
    }

    @Test
    public void testSetGetShift() {
        testNurse.setShift("0000-0800");
        testDoctor.setShift("0700-1500");
        testReceptionist.setShift("0800-1600");
        assertEquals("0000-0800", testNurse.getShift());
        assertEquals("0700-1500", testDoctor.getShift());
        assertEquals("0800-1600", testReceptionist.getShift());
    }

    @Test
    public void testGetSetPosition() {
        assertEquals("Nurse", testNurse.getPosition());
        assertEquals("Doctor", testDoctor.getPosition());
        assertEquals("Receptionist", testReceptionist.getPosition());
        testStaff.setPosition("Nurse");
        assertEquals("Nurse", testStaff.getPosition());
    }
}
