package deprecated.persistence;

import model.people.*;
import model.rooms.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Writer class
 */

public class WriterTest {

    // From https://github.students.cs.ubc.ca/CPSC210/TellerApp

    private static final String TEST_PATIENT_FILE = "./data/testWritePatientsFile";
    private static final String TEST_NURSES_FILE = "./data/testWriteNursesFile";
    private static final String TEST_OTHER_STAFF_FILE = "./data/testWriteOtherStaffFile";

    private Writer testWriterPatients;
    private Writer testWriterNurses;
    private Writer testWriterOtherStaff;
    private Patient testPatient1;
    private Patient testPatient2;
    private Nurse testNurse1;
    private Nurse testNurse2;
    private Doctor testDoctor1;
    private Doctor testDoctor2;
    private Receptionist testReceptionist;
    private Room testRoom;
    private Room testRoom1;
    private Room testRoom2;

    @BeforeEach
    public void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriterPatients = new Writer(new File(TEST_PATIENT_FILE));
        testWriterNurses = new Writer(new File(TEST_NURSES_FILE));
        testWriterOtherStaff = new Writer(new File(TEST_OTHER_STAFF_FILE));

        testPatient1 = new Patient("Bob", "Lee", 19700107, 12, 1430, 2,
                "Pain everywhere", "Contact", "Nuts", "None");
        testPatient2 = new Patient("Jason", "Bourne", 19710415, 162, 1700,
                3, "Amnesia", "None", "None", "Normality",
                125);

        ArrayList<Integer> roomsForTestNurse2 = new ArrayList<>();
        roomsForTestNurse2.add(115);
        roomsForTestNurse2.add(117);
        testNurse1 = new Nurse("Robert", "Stone", "0200-1000");
        testNurse2 = new Nurse("Selena", "Johnson", "1900-0300", roomsForTestNurse2);

        testDoctor1 = new Doctor("Morgan", "Daniels", "0730-1530");
        testReceptionist = new Receptionist("Charles", "Gregor", "0800-1600");
        testDoctor2 = new Doctor("Alena", "Klein", "1500-2300");

        testRoom = new Room(125);
        testPatient2.assignRoom(testRoom);

        testRoom1 = new Room(115);
        testRoom2 = new Room(117);
        testNurse2.setAssignedRoom(testRoom1);
        testNurse2.setAssignedRoom(testRoom2);
    }

    @Test
    public void testWritePatients() {
        testWriterPatients.write(testPatient1);
        testWriterPatients.write(testPatient2);
        testWriterPatients.close();

        try {
            List<Patient> patients = Reader.readPatients(new File(TEST_PATIENT_FILE));

            Patient patient1 = patients.get(0);
            assertEquals("Bob Lee", patient1.getFullName());
            assertEquals(19700107, patient1.getDOB());
            assertEquals(12, patient1.getID());
            assertEquals(1430, patient1.getTimeCheckedIn());
            assertEquals(2, patient1.getUrgency());
            assertEquals("Pain everywhere", patient1.getReasonForVisit());
            assertEquals("Contact", patient1.getPrecautions());
            assertEquals("Nuts", patient1.getAllergies());
            assertEquals("None", patient1.getMedications());
            assertEquals(0, patient1.getRoomNumberToAssign());

            Patient patient2 = patients.get(1);
            assertEquals("Jason Bourne", patient2.getFullName());
            assertEquals(19710415, patient2.getDOB());
            assertEquals(162, patient2.getID());
            assertEquals(1700, patient2.getTimeCheckedIn());
            assertEquals(3, patient2.getUrgency());
            assertEquals("Amnesia", patient2.getReasonForVisit());
            assertEquals("None", patient2.getPrecautions());
            assertEquals("None", patient2.getAllergies());
            assertEquals("Normality", patient2.getMedications());
            assertEquals(125, patient2.getRoomNumberToAssign());

        } catch (IOException e) {
            fail("An unexpected IOException was thrown");
        }

    }

    @Test
    public void testWriteNurses() {
        testWriterNurses.write(testNurse1);
        testWriterNurses.write(testNurse2);
        testWriterNurses.close();

        try {
            List<Nurse> nurses = Reader.readNurses(new File(TEST_NURSES_FILE));

            Nurse nurse1 = nurses.get(0);
            assertEquals("Robert Stone", nurse1.getFullName());
            assertEquals("RobertStone", nurse1.getNameForScanner());
            assertEquals("0200-1000", nurse1.getShift());
            assertEquals(0, nurse1.getRoomNumbersToAssign().size());

            Nurse nurse2 = nurses.get(1);
            assertEquals("Selena Johnson", nurse2.getFullName());
            assertEquals("SelenaJohnson", nurse2.getNameForScanner());
            assertEquals("1900-0300", nurse2.getShift());
            assertEquals(2, nurse2.getRoomNumbersToAssign().size());
            assertEquals(115, nurse2.getRoomNumbersToAssign().get(0));
            assertEquals(117, nurse2.getRoomNumbersToAssign().get(1));

        } catch (IOException e) {
            fail("An unexpected IOException was thrown");
        }
    }

    @Test
    public void testWriteOtherStaff() {
        testWriterOtherStaff.write(testDoctor1);
        testWriterOtherStaff.write(testReceptionist);
        testWriterOtherStaff.write(testDoctor2);
        testWriterOtherStaff.close();

        try {
            List<Staff> otherStaff = Reader.readOtherStaff(new File(TEST_OTHER_STAFF_FILE));

            Staff staff1 = otherStaff.get(0);
            assertEquals("Doctor", staff1.getPosition());
            assertEquals("Morgan Daniels", staff1.getFullName());
            assertEquals("0730-1530", staff1.getShift());

            Staff staff2 = otherStaff.get(1);
            assertEquals("Receptionist", staff2.getPosition());
            assertEquals("Charles Gregor", staff2.getFullName());
            assertEquals("0800-1600", staff2.getShift());

            Staff staff3 = otherStaff.get(2);
            assertEquals("Doctor", staff3.getPosition());
            assertEquals("Alena Klein", staff3.getFullName());
            assertEquals("1500-2300", staff3.getShift());

        } catch (IOException e) {
            fail("An unexpected IOException was thrown");
        }
    }

}
