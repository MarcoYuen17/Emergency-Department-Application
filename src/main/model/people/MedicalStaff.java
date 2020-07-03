package model.people;

/**
 * Abstract class to represent medical staff members in the Emergency Department
 */

public abstract class MedicalStaff extends Staff {

    // EFFECTS: Constructs a new medical staff with first name and last name
    public MedicalStaff(String firstName, String lastName) {
        super(firstName, lastName);
    }

    // EFFECTS: Constructs a new medical staff with first name, last name, and shift
    public MedicalStaff(String firstName, String lastName, String shift) {
        super(firstName, lastName, shift);
    }
}
