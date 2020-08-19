package model.people;

import model.rooms.Room;

/**
 * Represents a patient in the Emergency Department
 */

public class Patient extends Person {

    private String fullName;
    private Room room;
    private int id;
    private int timeCheckedIn;
    private int urgency;  // 1 means low, 2, means moderate, 3 means high
    private String reasonForVisit;
    private String precautions;
    private String allergies;
    private String medications;
    private int roomNumberToAssign;

    public static final int MIN_URGENCY = 1;
    public static final int MAX_URGENCY = 3;

    // EFFECTS: Constructs new patient with parameters in order
    public Patient(String firstName, String lastName, int dob, int id, int timeIn,
                   int urgency, String reasonForVisit, String precautions, String allergies, String medications) {

        room = null;
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = firstName + " " + lastName;
        this.dob = dob;
        this.id = id;
        this.timeCheckedIn = timeIn;
        this.urgency = urgency;
        this.reasonForVisit = reasonForVisit;
        this.precautions = precautions;
        this.allergies = allergies;
        this.medications = medications;

    }

    // EFFECTS: Constructs new patient with parameters in order (and room number for loader)
    public Patient(String firstName, String lastName, int dob, int id, int timeIn, int urgency, String reasonForVisit,
                   String precautions, String allergies, String medications, int roomNumberToAssign) {

        room = null;
        this.firstName = firstName;
        this.lastName = lastName;
        fullName = firstName + " " + lastName;
        this.dob = dob;
        this.id = id;
        this.timeCheckedIn = timeIn;
        this.urgency = urgency;
        this.reasonForVisit = reasonForVisit;
        this.precautions = precautions;
        this.allergies = allergies;
        this.medications = medications;
        this.roomNumberToAssign = roomNumberToAssign;
    }

    // EFFECTS: Returns patient's room
    public Room getLocation() {
        return room;
    }

    @Override
    // EFFECTS: Returns patient's full name
    public String getFullName() {
        return fullName;
    }

    // EFFECTS: Returns patient's date of birth
    public int getDOB() {
        return dob;
    }

    // EFFECTS: Returns patient's id
    public int getID() {
        return id;
    }

    // EFFECTS: Returns time patient was checked in
    public int getTimeCheckedIn() {
        return timeCheckedIn;
    }

    // EFFECTS: Returns patient's urgency
    public int getUrgency() {
        return urgency;
    }

    // EFFECTS: Returns patient's reason for visit
    public String getReasonForVisit() {
        return reasonForVisit;
    }

    // EFFECTS: Returns patient's recommended precautions
    public String getPrecautions() {
        return precautions;
    }

    // EFFECTS: Returns patient's allergies
    public String getAllergies() {
        return allergies;
    }

    // EFFECTS: Returns patient's medications
    public String getMedications() {
        return medications;
    }

    // EFFECTS: Returns room number to be assigned (for loading from saved file)
    public int getRoomNumberToAssign() {
        return roomNumberToAssign;
    }

    // REQUIRES: r has not already been assigned to a patient
    // MODIFIES: this, r
    // EFFECTS: Moves patient to given room
    public void assignRoom(Room r) {
        room = r;
        r.assignPatient(this);
    }

    // MODIFIES: this
    // EFFECTS: Updates patient's urgency
    public void changeUrgency(int urgency) {
        this.urgency = urgency;
    }

    // From https://github.students.cs.ubc.ca/cpsc210-2019w-t2/assign2_o7w2b
    // EFFECTS: Returns all of patient's info on separate lines
    public String allInfo() {
        return "Name: " + fullName + "\n" + "DOB: " + dob + "\n" + "ID: "
                + id + "\n" + "Time Checked In: " + timeCheckedIn + "\n" + "Urgency: " + urgency + "\n"
                + "Reason for visit: " + reasonForVisit + "\n" + "Precautions: " + precautions + "\n" + "Allergies: "
                + allergies + "\n" + "Medications: " + medications + "\n";
    }

    public String allInfoGUI() {
        return "<html>Name: " + fullName + "<br/>" + "DOB: " + dob + "<br/>" + "ID: " + id + "<br/>"
                + "Time Checked In: " + timeCheckedIn + "<br/>" + "Urgency: " + urgency + "<br/>"
                + "Reason for visit: " + reasonForVisit + "<br/>" + "Precautions: " + precautions + "<br/>"
                + "Allergies: " + allergies + "<br/>" + "Medications: " + medications + "<br/></html>";
    }

    // EFFECTS: Returns room number for saving
    public int printRoomNumberToSave() {
        if (room == null) {
            return 0;
        } else {
            return room.getRoomNumber();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Patient patient = (Patient) o;

        return id == patient.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
