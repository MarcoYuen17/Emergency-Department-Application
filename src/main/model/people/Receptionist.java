package model.people;

/**
 * Represents a receptionist in the Emergency Department
 */

public class Receptionist extends Staff {

    // EFFECTS: Constructs a new receptionist with first name and last name with Receptionist position
    public Receptionist(String firstName, String lastName) {
        super(firstName, lastName);
        this.position = "Receptionist";
    }

    // EFFECTS: Constructs a new receptionist with first name, last name, shift, and Receptionist position
    public Receptionist(String firstName, String lastName, String shift) {
        super(firstName, lastName, shift);
        this.position = "Receptionist";
    }
}