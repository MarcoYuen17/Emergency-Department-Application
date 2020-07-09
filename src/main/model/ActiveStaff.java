package model;

import model.exceptions.StaffClockedInException;
import model.people.Nurse;
import model.people.Staff;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the staff currently on shift in the Emergency Department
 */

public class ActiveStaff {

    private static ActiveStaff instance = null;
    private ArrayList<Staff> listOfActiveStaff;
    public static ArrayList<Nurse> activeNurses;

    // EFFECTS: Constructs new active staff lists
    private ActiveStaff() {
        listOfActiveStaff = new ArrayList<>();
        activeNurses = new ArrayList<>();
    }

    // EFFECTS: Returns single instance of ActiveStaff or new ActiveStaff if null
    public static ActiveStaff getInstance() {
        if (instance == null) {
            instance = new ActiveStaff();
        }
        return instance;
    }

    // MODIFIES: this
    // EFFECTS: Resets instance to null
    public static void resetInstance() {
        instance = null;
    }

    // MODIFIES: this
    // EFFECTS: Adds staff member and shift in "time-time" to list of active staff;
    //          if already clocked in, throws StaffClockedInException
    public void clockIn(Staff staff, String shift) throws StaffClockedInException {
        if (listOfActiveStaff.contains(staff)) {
            throw new StaffClockedInException(staff.getPosition() + " " + staff.getFullName()
                    + " is already clocked in.");
        }

        staff.setShift(shift);
        listOfActiveStaff.add(staff);

        if (staff.getPosition().equals("Nurse")) {
            activeNurses.add((Nurse) staff);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes staff member from list of active staff;
    //          throws StaffClockedInException if staff member is not clocked in
    public void clockOut(Staff staff) throws StaffClockedInException {
        if (!listOfActiveStaff.contains(staff)) {
            throw new StaffClockedInException(staff.getPosition() + " " + staff.getFullName() + " is not clocked in.");
        }
        listOfActiveStaff.remove(staff);
    }

    // EFFECTS: Returns shift of staff
    public String getShift(Staff staff) {
        return staff.getShift();
    }

   // EFFECTS: Returns number of staff on shift
    public int staffPower() {
        return listOfActiveStaff.size();
    }

    // EFFECTS: Returns list of active staff
    public ArrayList<Staff> getListOfActiveStaff() {
        return listOfActiveStaff;
    }

    // EFFECTS: Returns list of active nurses
    public ArrayList<Nurse> getListOfActiveNurses() {
        return activeNurses;
    }

    // EFFECTS: Returns list of active staff other than nurses()
    public List<Staff> getListOfActiveOtherStaff() {
        List<Staff> otherStaff = new ArrayList<>();
        for (Staff staff: listOfActiveStaff) {
            if (staff.getPosition() != "Nurse") {
                otherStaff.add(staff);
            }
        }
        return otherStaff;
    }

    // EFFECTS: Returns all staff and corresponding shift on separate lines (for JLabel)
    public String showAllStaffGUI() {
        String allStaff = "<html>All Clocked In Staff: (Position: Name - Shift)<br/><br/>";
        for (Staff s: listOfActiveStaff) {
            allStaff = allStaff + s.getPosition() + ": " + s.getFullName() + " - " + s.getShift() + "<br/>";
        }
        allStaff = allStaff + "</html>";
        return allStaff;
    }
}
