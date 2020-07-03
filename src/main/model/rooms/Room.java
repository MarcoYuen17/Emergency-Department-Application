package model.rooms;


import model.people.Nurse;
import model.people.Patient;
import model.people.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the Emergency Department
 */

public class Room {

    private int number;
    private String numberString;
    private Patient patient;
    private Nurse nurse;
    private String precautions;
    private List<Capabilities> specialCapabilities;

    private static final int MAX_ROOM_NUM = 155;
    private static final int MIN_ROOM_NUM = 101;

    public static final String ERROR_ROOM_DOES_NOT_EXIST = "Room does not exist";

    public static ArrayList<Room> allRooms = new ArrayList<>();

    // EFFECTS: Constructs new room with given number
    public Room(int number) {
        this.number = number;
        this.numberString = Integer.toString(number);
    }

    // MODIFIES: this
    // EFFECTS: Assigns patient and precautions to room
    public void assignPatient(Patient p) {
        this.patient = p;
        this.precautions = p.getPrecautions();
    }

    // MODIFIES: this
    // EFFECTS: Assigns nurse to room
    public void assignNurse(Nurse n) {
        if (nurse != null) {
            nurse.findAssignedRooms().remove(this);
        }
        this.nurse = n;
    }

    // MODIFIES: this
    // EFFECTS: Sets nurse to null
    public void clearNurse() {
        nurse = null;
    }

    // EFFECTS: Returns patient in room
    public Patient getPatient() {
        return patient;
    }

    // EFFECTS: Returns nurse in room
    public Nurse getNurse() {
        return nurse;
    }

    // EFFECTS: Returns room's recommended precautions (from patient)
    public String getPrecautions() {
        return precautions;
    }

    // EFFECTS: Returns number of room
    public int getRoomNumber() {
        return number;
    }

    // EFFECTS: Returns string number of ROom
    public String getRoomNumberString() {
        return numberString;
    }

    // MODIFIES: this
    // EFFECTS: Sets list of capabilities
    public void setCapabilities(List<Capabilities> capabilities) {
        this.specialCapabilities = capabilities;
    }

    // EFFECTS: Returns list of special capabilities
    public List<Capabilities> listCapabilities() {
        return specialCapabilities;
    }

    // EFFECTS: Returns true if room number does not exist in the ED
    public static Boolean isRoomNumIllegal(int roomNum) {
        return roomNum > MAX_ROOM_NUM || roomNum < MIN_ROOM_NUM;
    }

}
