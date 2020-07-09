package json.persistence;

import model.people.Nurse;
import model.people.Patient;
import model.people.Staff;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Decoder class
 */

public class DecoderTest {

    @Test
    public void testDecodePatientsFile() {
        try {
            List<Patient> patients = Decoder.decodePatients(new File("./data/testPatientsFileJson"));

            assertEquals(2, patients.size());

            Patient patient1 = patients.get(0);
            assertEquals("Biggie Cheese", patient1.getFullName());
            assertEquals(20001014, patient1.getDOB());
            assertEquals(12345, patient1.getID());
            assertEquals(1130, patient1.getTimeCheckedIn());
            assertEquals(1, patient1.getUrgency());
            assertEquals("Headache", patient1.getReasonForVisit());
            assertEquals("None", patient1.getPrecautions());
            assertEquals("Peanuts", patient1.getAllergies());
            assertEquals("Zofran", patient1.getMedications());
            assertEquals(0, patient1.getRoomNumberToAssign()); //TODO: this could be removed when Patient TODO is complete
            assertNull(patient1.getLocation()); //TODO: this should be changed when patient TODO is complete

            Patient patient2 = patients.get(1);
            assertEquals("Another Patient", patient2.getFullName());
            assertEquals(20090903, patient2.getDOB());
            assertEquals(2666, patient2.getID());
            assertEquals(1900, patient2.getTimeCheckedIn());
            assertEquals(3, patient2.getUrgency());
            assertEquals("First seizure", patient2.getReasonForVisit());
            assertEquals("None", patient2.getPrecautions());
            assertEquals("None", patient2.getAllergies());
            assertEquals("Lotriderm", patient2.getMedications());
            assertEquals(101, patient2.getRoomNumberToAssign());
        } catch (IOException e) {
            fail("An IOException was thrown when decoding the patient file.");;
        } catch (ParseException e) {
            fail("A ParseException was thrown when decoding the patient file.");
        }
    }

    @Test
    public void testDecodeNursesFile() {
        try {
            List<Nurse> nurses = Decoder.decodeNurses(new File("./data/testNursesFileJson"));

            assertEquals(3, nurses.size());

            Nurse nurse1 = nurses.get(0);
            assertEquals("Nursy Nur", nurse1.getFullName());
            assertEquals("0100-0900", nurse1.getShift());
            assertEquals(0, nurse1.findAssignedRooms().size());
            assertEquals(1, nurse1.getRoomNumbersToAssign().size()); //TODO: this could be changed so that nurses are assigned rooms upon reinstantiation
            assertEquals(101, nurse1.getRoomNumbersToAssign().get(0));
            assertEquals("Nurse", nurse1.getPosition());

            Nurse nurse2 = nurses.get(1);
            assertEquals("Another Nurse", nurse2.getFullName());
            assertEquals("0500-1300", nurse2.getShift());
            assertEquals(0, nurse2.findAssignedRooms().size());
            assertEquals(2, nurse2.getRoomNumbersToAssign().size());
            assertEquals(102, nurse2.getRoomNumbersToAssign().get(0));
            assertEquals(103, nurse2.getRoomNumbersToAssign().get(1));
            assertEquals("Nurse", nurse2.getPosition());

            Nurse nurse3 = nurses.get(2);
            assertEquals("Last One", nurse3.getFullName());
            assertEquals("0700-1500", nurse3.getShift());
            assertEquals(0, nurse3.findAssignedRooms().size());
            assertEquals(0, nurse3.getRoomNumbersToAssign().size());
            assertEquals("Nurse", nurse3.getPosition());
        } catch (IOException e) {
            fail("An IOException was thrown when decoding the patient file.");
        } catch (ParseException e) {
            fail("A ParseException was thrown when decoding the patient file.");
        }
    }

    @Test
    public void testDecodeOtherStaffFile() {
        try {
            List<Staff> otherStaff = Decoder.decodeOtherStaff(new File("./data/testOtherStaffFileJson"));

            assertEquals(4, otherStaff.size());

            Staff doctor1 = otherStaff.get(0);
            assertEquals("Docty Doc", doctor1.getFullName());
            assertEquals("0130-0930", doctor1.getShift());
            assertEquals("Doctor", doctor1.getPosition());

            Staff doctor2 = otherStaff.get(1);
            assertEquals("ER Master", doctor2.getFullName());
            assertEquals("1500-2300", doctor2.getShift());
            assertEquals("Doctor", doctor1.getPosition());

            Staff receptionist1 = otherStaff.get(2);
            assertEquals("Clerky Clerk", receptionist1.getFullName());
            assertEquals("0000-0800", receptionist1.getShift());
            assertEquals("Receptionist", receptionist1.getPosition());

            Staff receptionist2 = otherStaff.get(3);
            assertEquals("Head Desk", receptionist2.getFullName());
            assertEquals("0800-1600", receptionist2.getShift());
            assertEquals("Receptionist", receptionist2.getPosition());
        } catch (IOException e) {
            fail("An IOException was thrown when decoding the patient file.");
        } catch (ParseException e) {
            fail("A ParseException was thrown when decoding the patient file.");
        }
    }
}
