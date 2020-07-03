package persistence;

import model.people.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A reader that can read patients, nurses, and other staff from file
 */

public class Reader {

    public Reader() {}

    // From https://github.students.cs.ubc.ca/CPSC210/TellerApp

    public static final String DELIMITER = ",";
    public static final String ROOM_DELIMITER = ";";

    // EFFECTS: Returns a list of patients parsed from file;
    // throws IOException if an exception is raised when opening/reading file
    public static List<Patient> readPatients(File file) throws IOException {
        List<String> patientFileContent = readFile(file);
        return parsePatientFile(patientFileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: Returns a list of patients parsed from list of strings
    // where each string contains data for one patient
    private static List<Patient> parsePatientFile(List<String> patientFileContent) {
        List<Patient> patients = new ArrayList<>();

        for (String line: patientFileContent) {
            ArrayList<String> lineComponents = splitString(line);
            patients.add(parsePatient(lineComponents));
        }

        return patients;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: Components has size 11, element 0 is first name, element 1 is last name, element 2 is DOB,
    // element 3 is health ID, element 4 is time checked in, element 5 is urgency, element 6 is reason for visit,
    // element 7 is recommended precautions, element 8 is allergies, element 9 is medications, element 10 is assigned
    // room where 0 means no room
    // EFFECTS: returns a patient constructed from components
    private static Patient parsePatient(List<String> components) {
        String firstName = components.get(0);
        String lastName = components.get(1);
        int dob = Integer.parseInt(components.get(2));
        int id = Integer.parseInt(components.get(3));
        int timeCheckedIn = Integer.parseInt(components.get(4));
        int urgency = Integer.parseInt(components.get(5));
        String reasonForVisit = components.get(6);
        String precautions = components.get(7);
        String allergies = components.get(8);
        String medications = components.get(9);
        int roomNumber = Integer.parseInt(components.get(10));

        return new Patient(firstName, lastName, dob, id, timeCheckedIn, urgency, reasonForVisit, precautions,
                allergies, medications, roomNumber);
    }

    // EFFECTS: Returns a list of nurses parsed from file;
    // throws IOException if an exception is raised when opening/reading file
    public static List<Nurse> readNurses(File file) throws IOException {
        List<String> nurseFileContent = readFile(file);
        return parseNurseFile(nurseFileContent);
    }

    // EFFECTS: Returns a list of nurses parsed from list of strings
    // where each string contains data for one nurse
    public static List<Nurse> parseNurseFile(List<String> nurseFileContent) {
        List<Nurse> nurses = new ArrayList<>();

        for (String line: nurseFileContent) {
            ArrayList<String> lineComponents = splitString(line);
            nurses.add(parseNurse(lineComponents));
        }

        return nurses;
    }

    // REQUIRES: Components has size 4, element 0 is first name, element 1 is last name, element 2 is shift,
    // element 3 is assigned rooms where 0 means no assigned rooms
    // EFFECTS: returns a nurse constructed from components
    private static Nurse parseNurse(List<String> components) {
        String firstName = components.get(0);
        String lastName = components.get(1);
        String shift = components.get(2);
        ArrayList<Integer> roomNumbers = splitRooms(components.get(3));

        return new Nurse(firstName, lastName, shift, roomNumbers);
    }

    // EFFECTS: returns a list of integers obtained by splitting line on ROOMS_DELIMITER
    private static ArrayList<Integer> splitRooms(String roomLine) {
        String[] roomSplits = roomLine.split(ROOM_DELIMITER);
        ArrayList<String> roomStringNums = new ArrayList<>(Arrays.asList(roomSplits));
        ArrayList<Integer> roomNums = new ArrayList<>();
        for (String rsn: roomStringNums) {
            if (rsn.equals("0")) {
                break;
            }
            int roomNum = Integer.parseInt(rsn);
            roomNums.add(roomNum);
        }
        return roomNums;
    }

    // EFFECTS: Returns a list of doctors/receptionists parsed from file;
    // throws IOException if an exception is raised when opening/reading the file
    public static List<Staff> readOtherStaff(File file) throws IOException {
        List<String> otherStaffFileContent = readFile(file);
        return parseOtherStaffFile(otherStaffFileContent);
    }

    // EFFECTS: Returns a list of doctors/receptionists parsed from list of strings
    // where each string contains data for one staff member
    private static List<Staff> parseOtherStaffFile(List<String> staffFileContent) {
        List<Staff> staff = new ArrayList<>();

        for (String line: staffFileContent) {
            ArrayList<String> lineComponents = splitString(line);
            staff.add(parseOtherStaff(lineComponents));
        }

        return staff;
    }

    // REQUIRES: components has size 4, element 0 is position, element 1 is first name, element 2 is last name,
    // element 3 is shift
    // EFFECTS: Returns a doctor/receptionist constructed from components
    private static Staff parseOtherStaff(ArrayList<String> components) {
        String position = components.get(0);
        String firstName = components.get(1);
        String lastName = components.get(2);
        String shift = components.get(3);

        Staff staff;
        if (position.equals("Doctor")) {
            staff = new Doctor(firstName, lastName, shift);
        } else {
            staff = new Receptionist(firstName, lastName, shift);
        }
        return staff;
    }

}
