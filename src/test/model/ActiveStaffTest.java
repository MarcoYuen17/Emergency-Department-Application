package model;

import model.exceptions.StaffClockedInException;
import model.people.Doctor;
import model.people.Nurse;
import model.people.Receptionist;
import model.people.Staff;
import model.rooms.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ActiveStaff class
 */

public class ActiveStaffTest {

    private Nurse testNurse;
    private Nurse testNurseSameName;
    private Doctor testDoctor;
    private Doctor testDoctorSameName;
    private Receptionist testReceptionist;
    private ActiveStaff testActiveStaff;
    private Room testRoom1;
    private Room testRoom2;
    private Room testRoom3;

    @BeforeEach
    public void runBefore() {
        testNurse = new Nurse("Nursy", "Nur");
        testNurseSameName = new Nurse("Nursy", "Nur");
        testDoctor = new Doctor("Docty", "Doc");
        testDoctorSameName = new Doctor("Docty", "Doc");
        testReceptionist = new Receptionist("Clerky", "Clerk");
        testRoom1 = new Room(1);
        testRoom2 = new Room(2);
        testRoom3 = new Room(3);

        ActiveStaff.resetInstance();
        testActiveStaff = ActiveStaff.getInstance();
    }

    @Test
    public void testConstructor() {
        assertEquals(testActiveStaff, ActiveStaff.getInstance());
        assertEquals(0, testActiveStaff.staffPower());
    }

    @Test
    public void testClockInAndClockOut() {
        Nurse anotherTestNurse = new Nurse("x", "y");
        Nurse finalNurse = new Nurse("1", "2");
        try {
            testActiveStaff.clockIn(testNurse, "0700-1500");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockIn(testDoctor,"0730-1530");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockIn(testReceptionist,"0800-1600");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockIn(testNurseSameName, "0900-1700");
            fail("StaffClockedInException should have been thrown");
        } catch (StaffClockedInException e) {
            // expected
        }
        try {
            testActiveStaff.clockIn(anotherTestNurse, "0000-0800");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        assertEquals(4, testActiveStaff.staffPower());
        assertEquals(2, testActiveStaff.getListOfActiveNurses().size());
        try {
            testActiveStaff.clockOut(testDoctor);
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockOut(testDoctorSameName);
            fail("StaffClockedInException should have been thrown");
        } catch (StaffClockedInException e) {
            // expected
        }
        anotherTestNurse.setAssignedRoom(testRoom1);
        anotherTestNurse.setAssignedRoom(testRoom2);
        try {
            testActiveStaff.clockOut(anotherTestNurse);
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        assertEquals(2, testActiveStaff.staffPower());
        assertEquals(1, testActiveStaff.getListOfActiveNurses().size());
        assertNull(testRoom1.getNurse());
        assertNull(testRoom2.getNurse());
        try {
            testActiveStaff.clockOut(testNurse);
        } catch(StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockOut(testReceptionist);
        } catch (StaffClockedInException e) {
            fail("StaffClockedOutException was thrown");
        }

        // Scenario when there is a nurse in activeStaff but activeNurses is empty:
        testActiveStaff.getListOfActiveStaff().add(finalNurse);
        assertEquals(1, testActiveStaff.staffPower());
        assertEquals(0, testActiveStaff.getListOfActiveNurses().size());
        try {
            testActiveStaff.clockOut(finalNurse);
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        assertEquals(0, testActiveStaff.staffPower());
        assertEquals(0, testActiveStaff.getListOfActiveNurses().size());
    }

    @Test
    public void testGetShift() {
        try {
            testActiveStaff.clockIn(testDoctor, "0200-1000");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockIn(testNurse, "0600-1400");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        assertEquals("0600-1400", testActiveStaff.getShift(testNurse));
    }

    @Test
    public void testGetActiveStaff() {
        try {
            testActiveStaff.clockIn(testNurse, "0100-0900");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockIn(testDoctor,"0530-1330");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockIn(testReceptionist,"0400-1200");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        assertEquals(testActiveStaff.getListOfActiveStaff(), testActiveStaff.getListOfActiveStaff());
    }

    @Test
    public void testGetListOfActiveNurses() {
        Doctor anotherDoctor = new Doctor("Very", "New");
        Nurse anotherNurse = new Nurse("Big", "Xp");
        try {
            testActiveStaff.clockIn(testReceptionist, "0100-0900");
            testActiveStaff.clockIn(testNurse, "0200-1000");
            testActiveStaff.clockIn(anotherDoctor, "0130-0930");
            testActiveStaff.clockIn(anotherNurse, "0400-1200");
            testActiveStaff.clockIn(testDoctor, "0600-1400");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown.");
        }
        List<Nurse> activeNurses = testActiveStaff.getListOfActiveNurses();
        assertEquals(2, activeNurses.size());
        assertEquals(testNurse, activeNurses.get(0));
        assertEquals(anotherNurse, activeNurses.get(1));
        try {
            testActiveStaff.clockOut(testNurse);
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown.");
        }
        assertEquals(1, activeNurses.size());
        assertEquals(anotherNurse, activeNurses.get(0));
    }

    @Test
    public void testGetListOfOtherActiveStaff() {
        Doctor anotherDoctor = new Doctor("Very", "New");
        Nurse anotherNurse = new Nurse("Big", "Xp");
        try {
            testActiveStaff.clockIn(testReceptionist, "0100-0900");
            testActiveStaff.clockIn(testNurse, "0200-1000");
            testActiveStaff.clockIn(anotherDoctor, "0130-0930");
            testActiveStaff.clockIn(anotherNurse, "0400-1200");
            testActiveStaff.clockIn(testDoctor, "0600-1400");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown.");
        }

        List<Staff> otherStaff = testActiveStaff.getListOfActiveOtherStaff();
        assertEquals(3, otherStaff.size());
        assertEquals(testReceptionist, otherStaff.get(0));
        assertEquals(anotherDoctor, otherStaff.get(1));
        assertEquals(testDoctor, otherStaff.get(2));
    }

    @Test
    public void testShowAllStaffGUI() {
        try {
            testActiveStaff.clockIn(testNurse, "0100-0900");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockIn(testDoctor,"0530-1330");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        try {
            testActiveStaff.clockIn(testReceptionist,"0400-1200");
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown");
        }
        assertEquals("<html>All Clocked In Staff: (Position: Name - Shift)<br/><br/>Nurse: Nursy Nur - "
                        + "0100-0900<br/>Doctor: Docty Doc - 0530-1330<br/>Receptionist: Clerky Clerk - 0400-1200<br/>"
                        + "</html>",
                testActiveStaff.showAllStaffGUI());
    }
}
