package model;

import model.exceptions.PatientCheckedInException;
import model.people.Patient;
import model.rooms.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CheckedInPatients class
 */

public class CheckedInPatientsTest {

    private Patient testPatient1;
    private Patient testPatient2;
    private Patient testPatient3;
    private Patient testPatient4;
    private Patient testPatient5;
    private Patient testPatient6;
    private Patient testPatient7;
    private Patient testPatient4SameID;
    private CheckedInPatients testCheckedInPatients;
    private Room testRoom1;
    private Room testRoom2;
    private Room testRoom3;
    private Room testRoom4;
    private Room testRoom5;
    private Room testRoom6;
    private Room testRoom7;

    @BeforeEach
    public void runBefore() {
        testPatient1 = new Patient("Bae", "Bunny", 20090201, 14,
                1035, 2, "Repeating headaches", "None", "Grass",
                "Benadryl");

        testPatient2 = new Patient("Brody", "Zhang", 20121024, 345,
                1537, 3, "Slipped on banana peel, extreme back pain",
                "None", "Pollen", "None");

        testPatient3 = new Patient("Me", "Myself", 20050616, 4432,
                1805, 3, "Dizzy, seeing double, nausea", "Contact",
                "None", "Ondansetron");

        testPatient4 = new Patient("Another", "Patient", 20090903, 2666,
                1330, 3, "First seizure", "None", "None",
                "Lotriderm");

        testPatient5 = new Patient("One", "More", 20001014, 98765,
                1000, 3, "Died", "Contact & Droplet", "None",
                "None");

        testPatient6 = new Patient("Hopefully", "Last", 19950805, 890,
                1537, 3, "Difficulty breathing", "Droplet", "Cats",
                "Ventolin");

        testPatient7 = new Patient("Please", "Last", 19910318, 1802,
                900, 1, "Always tired", "None", "None",
                "None");

        testPatient4SameID = new Patient("Shouldnt", "Work", 12345678, 2666, 1500,
                2, "r", "p", "a", "m");


        CheckedInPatients.resetInstance();
        testCheckedInPatients = CheckedInPatients.getInstance();

        testRoom1 = new Room(117);
        testRoom2 = new Room(118);
        testRoom3 = new Room(119);
        testRoom4 = new Room(120);
        testRoom5 = new Room(121);
        testRoom6 = new Room(122);
        testRoom7 = new Room(123);
    }

    @Test
    public void testConstructor() {
        assertEquals(testCheckedInPatients, CheckedInPatients.getInstance());
        assertEquals(0, testCheckedInPatients.numberCheckedIn());
    }

    @Test
    public void testCheckIn() {
        try {
            testCheckedInPatients.checkInPatient(testPatient2);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown");
        }
        try {
            testCheckedInPatients.checkInPatient(testPatient1);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown");
        }
        try {
            testCheckedInPatients.checkInPatient(testPatient4);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown");
        }
        try {
            testCheckedInPatients.checkInPatient(testPatient3);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown");
        }
        try {
            testCheckedInPatients.checkInPatient(testPatient4SameID);
            fail("PatientCheckedInException should have been thrown");
        } catch (PatientCheckedInException e) {
            // expected
        }
        assertEquals(4, testCheckedInPatients.numberCheckedIn());
        assertEquals(testPatient2, testCheckedInPatients.listOfCheckedInPatients().get(0));
        assertEquals(testPatient1, testCheckedInPatients.listOfCheckedInPatients().get(1));
        assertEquals(testPatient4, testCheckedInPatients.listOfCheckedInPatients().get(2));
        assertEquals(testPatient3, testCheckedInPatients.listOfCheckedInPatients().get(3));
    }

    @Test
    public void testNextPatientWithoutRoom() {
        assertNull(testCheckedInPatients.nextPatientWithoutRoom());
        try {
            testCheckedInPatients.checkInPatient(testPatient1);
            testCheckedInPatients.checkInPatient(testPatient2);
            testCheckedInPatients.checkInPatient(testPatient4);
            testCheckedInPatients.checkInPatient(testPatient3);
            testCheckedInPatients.checkInPatient(testPatient5);
            testCheckedInPatients.checkInPatient(testPatient6);
            testCheckedInPatients.checkInPatient(testPatient7);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown");
        }
        testPatient7.assignRoom(testRoom6);
        assertEquals(testPatient5, testCheckedInPatients.nextPatientWithoutRoom());
        testPatient5.assignRoom(testRoom4);
        testPatient2.assignRoom(testRoom1);
        assertEquals(testPatient4, testCheckedInPatients.nextPatientWithoutRoom());
        testPatient4.assignRoom(testRoom2);
        assertEquals(testPatient6, testCheckedInPatients.nextPatientWithoutRoom());
        testPatient6.assignRoom(testRoom5);
        assertEquals(testPatient3, testCheckedInPatients.nextPatientWithoutRoom());
        testPatient3.assignRoom(testRoom3);
        assertEquals(testPatient1, testCheckedInPatients.nextPatientWithoutRoom());
        testPatient1.assignRoom(testRoom7);
        assertNull(testCheckedInPatients.nextPatientWithoutRoom());
    }

    @Test
    public void testAdmitPatient() {
        try {
            testCheckedInPatients.checkInPatient(testPatient1);
            testCheckedInPatients.checkInPatient(testPatient2);
            testCheckedInPatients.admitPatient(testPatient2);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown");
        }
        try {
            testCheckedInPatients.admitPatient(testPatient5);
            fail("PatientCheckedInException should have been thrown");
        } catch (PatientCheckedInException e) {
            // expected
        }
        try {
            testCheckedInPatients.admitPatient(testPatient2);
            fail("PatientCheckedInException should have been thrown");
        } catch (PatientCheckedInException e) {
            // expected
        }
        assertEquals(1, testCheckedInPatients.numberCheckedIn());
        assertEquals(testPatient1, testCheckedInPatients.nextPatientWithoutRoom());
    }

    @Test
    public void testDischargePatient() {
        try {
            testCheckedInPatients.checkInPatient(testPatient3);
            testCheckedInPatients.checkInPatient(testPatient4);
            testCheckedInPatients.dischargePatient(testPatient4);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown");
        }
        try {
            testCheckedInPatients.dischargePatient(testPatient4);
            fail("PatientCheckedInException should have been thrown");
        } catch (PatientCheckedInException e) {
            // expected
        }
        try {
            testCheckedInPatients.dischargePatient(testPatient1);
            fail("PatientCheckedInException should have been thrown");
        } catch (PatientCheckedInException e) {
            // expected
        }
        assertEquals(1, testCheckedInPatients.numberCheckedIn());
        assertEquals(testPatient3, testCheckedInPatients.nextPatientWithoutRoom());
    }

    @Test
    public void testShowAllPatientsGUI() {
        try {
            testCheckedInPatients.checkInPatient(testPatient1);
            testCheckedInPatients.checkInPatient(testPatient2);
            testCheckedInPatients.checkInPatient(testPatient3);
        } catch (PatientCheckedInException e) {
            fail("PatientCheckedInException was thrown");
        }
        testPatient2.assignRoom(testRoom1);
        assertEquals("<html>All Checked In Patients: (Room - Name - ID - Time Checked In - Urgency)" +
                "<br/><br/>Waiting - Bae Bunny - 14 - 1035 - 2<br/>Room 117 - Brody Zhang - 345 - 1537 - 3<br/>Waiting" +
                " - Me Myself - 4432 - 1805 - 3<br/></html>", testCheckedInPatients.showAllPatientsGUI());

    }
}
