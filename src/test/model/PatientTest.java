package model;

import model.people.Patient;
import model.rooms.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Patient class
 */

public class PatientTest {

    private Patient testPatient;
    private Room testRoom;
    private Patient testPatient2;

    @BeforeEach
    public void runBefore() {
        testRoom = new Room(5);
        testPatient = new Patient("Biggie", "Cheese", 20001014, 12345, 0,
                1, "Headache","None", "Peanuts", "Zofran");
        testPatient2 = new Patient("F", "L", 1, 1, 1, 1, "r",
                "p", "a", "m", 101);
    }

    @Test
    public void testConstructor1() {
        assertNull(testPatient.getLocation());
        assertEquals("Biggie Cheese", testPatient.getFullName());
        assertEquals(20001014, testPatient.getDOB());
        assertEquals(12345, testPatient.getID());
        assertEquals(0, testPatient.getTimeCheckedIn());
        assertEquals(1, testPatient.getUrgency());
        assertEquals("Headache", testPatient.getReasonForVisit());
        assertEquals("None", testPatient.getPrecautions());
        assertEquals("Peanuts", testPatient.getAllergies());
        assertEquals("Zofran", testPatient.getMedications());
        assertEquals(0, testPatient.getRoomNumberToAssign());
    }

    @Test
    public void testConstructor2() {
        assertEquals(101, testPatient2.getRoomNumberToAssign());
    }

    @Test
    public void testAssignRoom() {
        testPatient.assignRoom(testRoom);
        assertEquals(testRoom, testPatient.getLocation());
        assertEquals(testPatient, testRoom.getPatient());
    }

    @Test
    public void testChangeUrgency() {
        testPatient.changeUrgency(3);
        assertEquals(3, testPatient.getUrgency());
        testPatient.changeUrgency(2);
        assertEquals(2, testPatient.getUrgency());
    }

    @Test
    public void testAllInfo() {
        assertEquals("Name: Biggie Cheese\nDOB: 20001014\nID: 12345\nTime Checked In: 0\n" +
                "Urgency: 1\nReason for visit: Headache\nPrecautions: None\nAllergies: Peanuts\n" +
                        "Medications: Zofran\n",
                testPatient.allInfo());
    }

    @Test
    public void testAllInfoGUI() {
        assertEquals("<html>Name: Biggie Cheese<br/>DOB: 20001014<br/>ID: 12345<br/>Time Checked In: 0<br/>" +
                        "Urgency: 1<br/>Reason for visit: Headache<br/>Precautions: None<br/>Allergies: Peanuts<br/>" +
                        "Medications: Zofran<br/></html>",
                testPatient.allInfoGUI());
    }
}
