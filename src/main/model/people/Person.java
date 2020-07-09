package model.people;

/**
 * Represents a person in the Emergency Department
 */

public class Person {

    protected String firstName;
    protected String lastName;
    protected int dob; //in yyyymmdd

    // EFFECTS: Returns person's first and last name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // EFFECTS: Returns person's first name
    public String getFirstName() {
        return firstName;
    }

    // EFFECTS: Returns person's last name
    public String getLastName() {
        return lastName;
    }
}
