package model;

import model.people.Nurse;
import model.rooms.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Nurse class
 */

public class NurseTest {

    private Nurse testNurse;
    private Nurse testNurse2;
    private Nurse testNurse3;
    private ArrayList<Integer> testRoomsToAssign;
    private Room testRoom100;
    private Room testRoom101;
    private Room testRoom102;

    @BeforeEach
    public void runBefore() {
        testNurse = new Nurse("Nursy", "Nurse");
        testRoomsToAssign = new ArrayList<>();
        testRoomsToAssign.add(100);
        testRoomsToAssign.add(102);
        testNurse2 = new Nurse("Shifty", "Shift", "0830-1630");
        testNurse3 = new Nurse("Loady", "Load", "0400-1200", testRoomsToAssign);
        testRoom100 = new Room(100);
        testRoom101 = new Room(101);
        testRoom102 = new Room(102);
    }

    @Test
    public void testConstructor1() {
        assertEquals("Nursy Nurse", testNurse.getFullName());
        assertEquals(0, testNurse.findAssignedRooms().size());
        assertEquals("Nurse", testNurse.getPosition());
        assertEquals("NursyNurse", testNurse.getNameForScanner());
        assertNull(testNurse.getRoomNumbersToAssign());
    }

    @Test
    public void testConstructor2() {
        assertEquals("0830-1630", testNurse2.getShift());
        assertEquals("Shifty Shift", testNurse2.getFullName());
        assertEquals(0, testNurse2.findAssignedRooms().size());
        assertEquals("Nurse", testNurse2.getPosition());
        assertEquals("ShiftyShift", testNurse2.getNameForScanner());
        assertNull(testNurse2.getRoomNumbersToAssign());
    }

    @Test
    public void testConstructor3() {
        assertEquals("0400-1200", testNurse3.getShift());
        assertEquals(2, testNurse3.getRoomNumbersToAssign().size());
        assertEquals(100, testNurse3.getRoomNumbersToAssign().get(0));
        assertEquals(102, testNurse3.getRoomNumbersToAssign().get(1));

    }
    @Test
    public void testSetAssignedRoom() {
        testNurse.setAssignedRoom(testRoom100);
        testNurse.setAssignedRoom(testRoom102);
        int numberRoomsAssigned = testNurse.findAssignedRooms().size();
        assertEquals(2, numberRoomsAssigned);
        assertEquals(testRoom100, testNurse.findAssignedRooms().get(0));
        assertEquals(testRoom102, testNurse.findAssignedRooms().get(1));
        assertEquals(testNurse, testRoom100.getNurse());
        assertEquals(testNurse, testRoom102.getNurse());
        assertNull(testRoom101.getNurse());
    }
}
