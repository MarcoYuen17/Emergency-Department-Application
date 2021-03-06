package model.people;

/**
 * Represents a staff member in the Emergency Department
 */

public class Staff extends Person {

    protected String shift;
    protected String position;
    protected String nameForScanner;
    protected String fullName;

    // EFFECTS: Constructs a new staff member with given names and position
    public Staff(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        nameForScanner = firstName + lastName;
        this.fullName = getFullName();
    }

    // EFFECTS: Constructs a new staff member with given names and shift
    public Staff(String firstName, String lastName, String shift) {
        this.firstName = firstName;
        this.lastName = lastName;
        setShift(shift);
        nameForScanner = firstName + lastName;
        this.fullName = getFullName();
    }

    // MODIFIES: this
    // EFFECTS: Assigns shift
    public void setShift(String shift) {
        this.shift = shift;
    }

    // EFFECTS: Returns name for scanner
    public String getNameForScanner() {
        return nameForScanner;
    }

    // EFFECTS: Returns shift
    public String getShift() {
        return shift;
    }

    // EFFECTS: Sets position
    public void setPosition(String position) {
        this.position = position;
    }

    // EFFECTS: Returns position
    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Staff)) {
            return false;
        }

        Staff staff = (Staff) o;

        if (!position.equals(staff.position)) {
            return false;
        }
        return fullName.equals(staff.fullName);
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }
}
