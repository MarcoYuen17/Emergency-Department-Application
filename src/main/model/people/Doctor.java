package model.people;

/**
 * Represents a doctor in the Emergency Department
 */

public class Doctor extends MedicalStaff {

    // EFFECTS: Constructs a new doctor with first name and last name with Doctor position
    public Doctor(String firstName, String lastName) {
        super(firstName, lastName);
        this.position = "Doctor";
    }

    // EFFECTS: Constructs a new doctor with first name, last name, shift, and Doctor position
    public Doctor(String firstName, String lastName, String shift) {
        super(firstName, lastName, shift);
        this.position = "Doctor";
    }
}
