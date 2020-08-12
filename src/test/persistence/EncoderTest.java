package persistence;

import model.ActiveStaff;
import model.CheckedInPatients;
import model.exceptions.PatientCheckedInException;
import model.exceptions.StaffClockedInException;
import model.people.*;
import model.rooms.Room;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Decoder;
import persistence.Encoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for Encoder class
 */

public class EncoderTest {

    private static final String TEST_PATIENTS_FILE_PATH = "./data/testEncodePatientsFile.json";
    private static final String TEST_NURSES_FILE_PATH = "./data/testEncodeNursesFile.json";
    private static final String TEST_OTHER_STAFF_FILE_PATH = "./data/testEncodeOtherStaffFile.json";

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

    private CheckedInPatients testCheckedInPatients;
    private ActiveStaff testActiveStaff;

    @BeforeEach
    public void runBefore() {
        CheckedInPatients.resetInstance();
        testCheckedInPatients = CheckedInPatients.getInstance();
        ActiveStaff.resetInstance();
        testActiveStaff = ActiveStaff.getInstance();

        testPatient1 = new Patient("Bob", "Lee", 19700107, 12, 1430, 2,
                "Pain everywhere", "Contact", "Nuts", "None");
        testPatient2 = new Patient("Jason", "Bourne", 19710415, 162, 1700,
                3, "Amnesia", "None", "None", "Normality",
                125);
        try {
            testCheckedInPatients.checkInPatient(testPatient1);
            testCheckedInPatients.checkInPatient(testPatient2);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown.");
        }

        ArrayList<Integer> roomsForTestNurse2 = new ArrayList<>();
        roomsForTestNurse2.add(115);
        roomsForTestNurse2.add(117);
        testNurse1 = new Nurse("Robert", "Stone", "0200-1000");
        testNurse2 = new Nurse("Selena", "Johnson", "1900-0300", roomsForTestNurse2);

        testDoctor1 = new Doctor("Morgan", "Daniels", "0730-1530");
        testReceptionist = new Receptionist("Charles", "Gregor", "0800-1600");
        testDoctor2 = new Doctor("Alena", "Klein", "1500-2300");

        try {
            testActiveStaff.clockIn(testNurse1, testNurse1.getShift());
            testActiveStaff.clockIn(testDoctor1, testDoctor1.getShift());
            testActiveStaff.clockIn(testReceptionist, testReceptionist.getShift());
            testActiveStaff.clockIn(testDoctor2, testDoctor2.getShift());
            testActiveStaff.clockIn(testNurse2, testNurse2.getShift());
        } catch (StaffClockedInException e) {
            fail("StaffClockedInException was thrown.");
        }

        testRoom = new Room(125);
        testPatient2.assignRoom(testRoom);

        testRoom1 = new Room(115);
        testRoom2 = new Room(117);
        testNurse2.setAssignedRoom(testRoom1);
        testNurse2.setAssignedRoom(testRoom2);
    }

    @Test
    public void testEncodePatients() {
        try {
            Encoder.encodePatients(testCheckedInPatients.listOfCheckedInPatients(), TEST_PATIENTS_FILE_PATH);
        } catch (IOException e) {
            fail("An IOException was thrown when encoding the patient file.");
        }

        try {
            List<Patient> patients = Decoder.decodePatients(new File(TEST_PATIENTS_FILE_PATH));

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
            fail("An IOException was thrown when decoding the patient file.");
        } catch (ParseException e) {
            fail("A ParseException was thrown when decoding the patient file.");
        }
    }

    @Test
    public void testEncodeNurses() {
        try {
            Encoder.encodeNurses(testActiveStaff.getListOfActiveNurses(), TEST_NURSES_FILE_PATH);
        } catch (IOException e) {
            fail("An IOException was thrown when encoding the nurse file.");
        }

        try {
            List<Nurse> nurses = Decoder.decodeNurses(new File(TEST_NURSES_FILE_PATH));

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
            fail("An IOException was thrown when decoding the nurse file.");
        } catch (ParseException e) {
            fail("A ParseException was thrown when decoding the nurse file.");
        }
    }

    @Test
    public void testEncodeOtherStaff() {
        try {
            Encoder.encodeOtherStaff(testActiveStaff.getListOfActiveOtherStaff(), TEST_OTHER_STAFF_FILE_PATH);
        } catch (IOException e) {
            fail("An IOException was thrown when encoding the other staff file.");
        }

        try {
            List<Staff> otherStaff = Decoder.decodeOtherStaff(new File(TEST_OTHER_STAFF_FILE_PATH));

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
            fail("An IOException was thrown when decoding the other staff file.");
        } catch (ParseException e) {
            fail("A ParseException was thrown when decoding the other staff file.");
        }
    }
}
