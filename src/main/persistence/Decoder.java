package persistence;

import model.people.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A decoder that can decode patients, nurses, and other staff from file
 */

public class Decoder {

    private static JSONParser jsonParser = new JSONParser();

    public static List<Patient> decodePatients(File file) throws IOException, ParseException {
        FileReader reader = new FileReader(file);
        Object parsedPatientArrayObject = jsonParser.parse(reader);
        JSONArray jsonPatientArray = (JSONArray) parsedPatientArrayObject;
        List<Patient> patients = new ArrayList<>();

        for (Object patientObject: jsonPatientArray) {
            JSONObject jsonPatientObject = (JSONObject) patientObject;

            String firstName = (String) jsonPatientObject.get("firstName");
            String lastName = (String) jsonPatientObject.get("lastName");
            Long dobLong = (Long) jsonPatientObject.get("dob");
            Long idLong = (Long) jsonPatientObject.get("id");
            Long timeCheckedInLong = (Long) jsonPatientObject.get("timeCheckedIn");
            Long urgencyLong = (Long) jsonPatientObject.get("urgency");
            String reasonForVisit = (String) jsonPatientObject.get("reasonForVisit");
            String precautions = (String) jsonPatientObject.get("precautions");
            String allergies = (String) jsonPatientObject.get("allergies");
            String medications = (String) jsonPatientObject.get("medications");
            Long roomNumberToAssignLong = (Long) jsonPatientObject.get("roomNumberToAssign");

            patients.add(new Patient(firstName, lastName, dobLong.intValue(), idLong.intValue(),
                    timeCheckedInLong.intValue(), urgencyLong.intValue(), reasonForVisit, precautions, allergies,
                    medications, roomNumberToAssignLong.intValue()));
        }

        return patients;
    }

    public static List<Nurse> decodeNurses(File file) throws IOException, ParseException {
        FileReader reader = new FileReader(file);
        Object parsedNurseArrayObject = jsonParser.parse(reader);
        JSONArray jsonNurseArray = (JSONArray) parsedNurseArrayObject;
        List<Nurse> nurses = new ArrayList<>();

        for (Object nurseObject: jsonNurseArray) {
            JSONObject jsonNurseObject = (JSONObject) nurseObject;
            String firstName = (String) jsonNurseObject.get("firstName");
            String lastName = (String) jsonNurseObject.get("lastName");
            String shift = (String) jsonNurseObject.get("shift");
            ArrayList<Long> roomNumbersToAssignLongs = (ArrayList<Long>) jsonNurseObject.get("roomNumbersToAssign");
            ArrayList<Integer> roomNumbersToAssign = new ArrayList<>();
            for (Long roomNumberLong: roomNumbersToAssignLongs) {
                if (roomNumberLong != 0) {
                    roomNumbersToAssign.add(roomNumberLong.intValue());
                }
            }

            nurses.add(new Nurse(firstName, lastName, shift, roomNumbersToAssign));
        }

        return nurses;
    }

    public static List<Staff> decodeOtherStaff(File file) throws IOException, ParseException {
        FileReader reader = new FileReader(file);
        Object parsedOtherStaffArrayObject = jsonParser.parse(reader);
        JSONArray jsonOtherStaffArray = (JSONArray) parsedOtherStaffArrayObject;
        List<Staff> otherStaff = new ArrayList<>();

        for (Object staffObject: jsonOtherStaffArray) {
            JSONObject jsonStaffObject = (JSONObject) staffObject;
            String position = (String) jsonStaffObject.get("position");
            String firstName = (String) jsonStaffObject.get("firstName");
            String lastName = (String) jsonStaffObject.get("lastName");
            String shift = (String) jsonStaffObject.get("shift");

            if (position.equals("Doctor")) {
                otherStaff.add(new Doctor(firstName, lastName, shift));
            } else if (position.equals("Receptionist")) {
                otherStaff.add(new Receptionist(firstName, lastName, shift));
            }
        }

        return otherStaff;
    }
}
