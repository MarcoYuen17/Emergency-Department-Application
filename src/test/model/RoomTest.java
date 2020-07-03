package model;

import model.people.Nurse;
import model.people.Patient;
import model.rooms.Capabilities;
import model.rooms.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.rooms.Capabilities.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Room class
 */

public class RoomTest {

    private Patient testPatient;
    private Room testRoom;
    private Room anotherTestRoom;
    private Nurse testNurse;
    private Nurse testNurse2;

    @BeforeEach
    public void runBefore() {
        testRoom = new Room(37);
        anotherTestRoom = new Room(106);
        testPatient = new Patient("Little", "Woman", 20050102, 43562,
                1300, 2, "Difficulty breathing", "Droplet",
                "Grass", "None");
        testNurse = new Nurse("First", "Last");
        testNurse2 = new Nurse("Hello", "World");
    }

    @Test
    public void testConstructor() {
        assertNull(testRoom.getPatient());
        assertNull(testRoom.getNurse());
        assertNull(testRoom.listCapabilities());
        assertEquals(37, testRoom.getRoomNumber());
        assertEquals("37", testRoom.getRoomNumberString());
    }

    @Test
    public void testAssignPatient() {
        testRoom.assignPatient(testPatient);
        assertEquals(testPatient, testRoom.getPatient());
        assertEquals("Droplet", testRoom.getPrecautions());
    }

    @Test
    public void testAssignThenClearNurse() {
        testRoom.assignNurse(testNurse);
        assertEquals(testNurse, testRoom.getNurse());
        testNurse2.setAssignedRoom(testRoom);
        assertEquals(testNurse2, testRoom.getNurse());
        assertFalse(testNurse.findAssignedRooms().contains(testRoom));
        assertTrue(testNurse2.findAssignedRooms().contains(testRoom));
        testRoom.clearNurse();
        assertNull(testRoom.getNurse());
    }

    @Test
    public void testSetCapabilities() {
        ArrayList<Capabilities> testCapabilities = new ArrayList<>();
        ArrayList<Capabilities> anotherTestCapabilities = new ArrayList<>();
        testCapabilities.add(Isolation);
        testCapabilities.add(Mental_Wellness);
        testRoom.setCapabilities(testCapabilities);
        assertEquals(testCapabilities, testRoom.listCapabilities());
        anotherTestCapabilities.add(Procedure);
        anotherTestCapabilities.add(Nitrous_Oxide);
        anotherTestCapabilities.add(Trauma);
        anotherTestRoom.setCapabilities(anotherTestCapabilities);
        assertEquals(anotherTestCapabilities, anotherTestRoom.listCapabilities());
    }

    @Test
    public void testIsRoomNumIllegal() {
        assertTrue(Room.isRoomNumIllegal(100));
        assertFalse(Room.isRoomNumIllegal(101));
        assertFalse(Room.isRoomNumIllegal(125));
        assertFalse(Room.isRoomNumIllegal(155));
        assertTrue(Room.isRoomNumIllegal(156));
    }
}
