package model;

import model.exceptions.PatientCheckedInException;
import model.people.Patient;

import java.util.ArrayList;

/**
 * Represents the patients currently checked in in the Emergency Department
 */

public class CheckedInPatients {

    private static CheckedInPatients instance = null;
    private ArrayList<Patient> checkedInPatients;

    // EFFECTS: Constructs new list of checked in patients
    private CheckedInPatients() {
        checkedInPatients = new ArrayList<>();
    }

    // EFFECTS: Returns single instance of CheckedInPatients or new CheckedInPatients if null
    public static CheckedInPatients getInstance() {
        if (instance == null) {
            instance = new CheckedInPatients();
        }
        return instance;
    }

    // EFFECTS: Resets instance to null
    public static void resetInstance() {
        instance = null;
    }

    // MODIFIES: this
    // EFFECTS: Adds patient to list of checked in patients;
    //          throws PatientCheckedInException if patient ID already exists
    public void checkInPatient(Patient p) throws PatientCheckedInException {
        if (checkedInPatients.contains(p)) {
            throw new PatientCheckedInException("A patient with ID: " + p.getID() + " is already checked in.");
        }
        checkedInPatients.add(p);
    }

    // MODIFIES: this
    // EFFECTS: Removes patient from list of checked in patients;
    //          throws PatientCheckedInException if patient ID does not exist in ED
    public void admitPatient(Patient p) throws PatientCheckedInException {
        if (!checkedInPatients.contains(p)) {
            throw new PatientCheckedInException("A patient with ID: " + p.getID() + " is not checked in.");
        }
        checkedInPatients.remove(p);
    }

    // MODIFIES: this
    // EFFECTS: Removes patient from list of checked in patients;
    //          throws PatientCheckedInException if patient ID does not exist in ED
    public void dischargePatient(Patient p) throws PatientCheckedInException {
        if (!checkedInPatients.contains(p)) {
            throw new PatientCheckedInException("A patient with ID: " + p.getID() + " is not checked in.");
        }
        checkedInPatients.remove(p);
    }

    // From https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS: Returns patient with highest urgency who has waited longest time without a room
    public Patient nextPatientWithoutRoom() {
        Patient next = new Patient("No", "Patients", 10000000, 0, 9999,
                0, "", "", "", "");
        for (Patient p : checkedInPatients) {
            if ((p.getUrgency() > next.getUrgency() && p.getLocation() == null
                    || p.getUrgency() >= next.getUrgency() && p.getLocation() == null
                    && p.getTimeCheckedIn() < next.getTimeCheckedIn())) {
                next = p;
            }
        }
        if (checkedInPatients.size() == 0 || next.getFullName().equals("No Patients")) {
            return null;
        }
        return next;
    }

    // EFFECTS: Returns list of checked in patients
    public ArrayList<Patient> listOfCheckedInPatients() {
        return checkedInPatients;
    }

    // EFFECTS: Returns list of checked in patients with each patient having own line (for JLabel)
    public String showAllPatientsGUI() {
        String allPatients = "<html>All Checked In Patients: (Room - Name - ID - Time Checked In - Urgency)<br/><br/>";
        for (Patient p: checkedInPatients) {
            if (p.getLocation() == null) {
                String location = "Waiting";
                allPatients = allPatients + location + " - " + p.getFullName() + " - " + p.getID() + " - "
                        + p.getTimeCheckedIn() + " - " + p.getUrgency() + "<br/>";
            } else {
                String location = "Room " + p.getLocation().getRoomNumber();
                allPatients = allPatients + location + " - " + p.getFullName() + " - " + p.getID() + " - "
                    + p.getTimeCheckedIn() + " - " + p.getUrgency() + "<br/>";
            }
        }
        allPatients = allPatients + "</html>";
        return allPatients;
    }

    // EFFECTS: Returns number of patients checked in
    public int numberCheckedIn() {
        return checkedInPatients.size();
    }
}
