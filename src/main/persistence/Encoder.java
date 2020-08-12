package persistence;

import model.people.Nurse;
import model.people.Patient;
import model.people.Staff;
import model.rooms.Room;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An encoder that can encode patients, nurses, and other staff to file
  */

public class Encoder {

    public static void encodePatients(Collection<Patient> patients, String filePath) throws IOException {
        JSONArray patientsJsonArrayObject = new JSONArray();

        for (Patient patient: patients) {
            JSONObject jsonPatient = new JSONObject();

            jsonPatient.put("firstName", patient.getFirstName());
            jsonPatient.put("lastName", patient.getLastName());
            jsonPatient.put("dob", patient.getDOB());
            jsonPatient.put("id", patient.getID());
            jsonPatient.put("timeCheckedIn", patient.getTimeCheckedIn());
            jsonPatient.put("urgency", patient.getUrgency());
            jsonPatient.put("reasonForVisit", patient.getReasonForVisit());
            jsonPatient.put("precautions", patient.getPrecautions());
            jsonPatient.put("allergies", patient.getAllergies());
            jsonPatient.put("medications", patient.getMedications());
            jsonPatient.put("roomNumberToAssign", patient.printRoomNumberToSave());

            patientsJsonArrayObject.add(jsonPatient);
        }

        FileWriter patientFileWriter = new FileWriter(filePath);
        patientFileWriter.write(patientsJsonArrayObject.toJSONString());
        patientFileWriter.close();
    }

    public static void encodeNurses(Collection<Nurse> nurses, String filePath) throws IOException {
        JSONArray nursesJsonArrayObject = new JSONArray();

        for (Nurse nurse: nurses) {
            JSONObject jsonNurse = new JSONObject();

            jsonNurse.put("firstName", nurse.getFirstName());
            jsonNurse.put("lastName", nurse.getLastName());
            jsonNurse.put("shift", nurse.getShift());

            List<Integer> assignedRoomNumbers = new ArrayList<>();
            for (Room room: nurse.findAssignedRooms()) {
                assignedRoomNumbers.add(room.getRoomNumber());
            }
            jsonNurse.put("roomNumbersToAssign", assignedRoomNumbers);

            nursesJsonArrayObject.add(jsonNurse);
        }

        FileWriter nurseFileWriter = new FileWriter(filePath);
        nurseFileWriter.write(nursesJsonArrayObject.toJSONString());
        nurseFileWriter.close();
    }

    public static void encodeOtherStaff(Collection<Staff> staff, String filePath) throws IOException {
        JSONArray otherStaffJsonArrayObject = new JSONArray();

        for (Staff otherStaff: staff) {
            JSONObject jsonOtherStaff = new JSONObject();

            jsonOtherStaff.put("position", otherStaff.getPosition());
            jsonOtherStaff.put("firstName", otherStaff.getFirstName());
            jsonOtherStaff.put("lastName", otherStaff.getLastName());
            jsonOtherStaff.put("shift", otherStaff.getShift());

            otherStaffJsonArrayObject.add(jsonOtherStaff);
        }

        FileWriter otherStaffFileWriter = new FileWriter(filePath);
        otherStaffFileWriter.write(otherStaffJsonArrayObject.toJSONString());
        otherStaffFileWriter.close();
    }
}