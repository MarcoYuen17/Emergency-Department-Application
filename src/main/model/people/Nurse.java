package model.people;

import model.rooms.Room;

import java.util.ArrayList;

/**
 * Represents a nurse in the Emergency Department
 */

public class Nurse extends MedicalStaff {

    private ArrayList<Room> assignedRooms;
    private ArrayList<Integer> roomNumbersToAssign;

    // EFFECTS: Constructs a new nurse with first name, last name, with Nurse position
    public Nurse(String firstName, String lastName) {
        super(firstName, lastName);
        this.position = "Nurse";
        assignedRooms = new ArrayList<>();
        nameForScanner = firstName + lastName;
    }

    // EFFECTS: Constructs a new nurse with first name, last name, shift, and Nurse position
    public Nurse(String firstName, String lastName, String shift) {
        super(firstName, lastName, shift);
        this.position = "Nurse";
        assignedRooms = new ArrayList<>();
        nameForScanner = firstName + lastName;
    }

    // EFFECTS: Constructs a new nurse with first name, last name, Nurse position, and room numbers to assign (for load)
    public Nurse(String firstName, String lastName, String shift, ArrayList<Integer> roomNumbers) {
        super(firstName, lastName, shift);
        this.position = "Nurse";
        assignedRooms = new ArrayList<>();
        nameForScanner = firstName + lastName;
        roomNumbersToAssign = roomNumbers;
    }

    // MODIFIES: this
    // EFFECTS: Assigns nurse to given room
    public void setAssignedRoom(Room room) {
        assignedRooms.add(room);
        room.assignNurse(this);
    }

    // EFFECTS: Returns room numbers to be assigned (for loading from saved file)
    public ArrayList<Integer> getRoomNumbersToAssign() {
        return roomNumbersToAssign;
    }

    // EFFECTS: Returns list of assigned rooms
    public ArrayList<Room> findAssignedRooms() {
        return assignedRooms;
    }
}
