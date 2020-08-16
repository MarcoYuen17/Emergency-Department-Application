package ui.tools.stafftools;

import model.ActiveStaff;
import model.exceptions.StaffClockedInException;
import model.people.Nurse;
import model.people.Staff;
import model.rooms.Room;
import ui.EmergencyDepartment;
import ui.tools.Tool;
import ui.tools.clickhandlers.ToolClickHandler;
import ui.tools.clickhandlers.ToolFrameClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static model.ActiveStaff.activeNurses;

/**
 * Represents a GUI tool to clock out a staff member
 */

public class StaffClockOutTool extends Tool {

    public StaffClockOutTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Clock Out a Staff Member";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new StaffClockOutToolClickHandler());
    }

    private class StaffClockOutToolClickHandler extends ToolClickHandler implements ActionListener {

        private JComboBox<String> positionDropDownBox;
        private JTextField firstNameField;
        private JTextField lastNameField;

        private JLabel positionInputLabel = new JLabel("Select Staff Member's Position:");
        private JLabel firstNameFieldLabel = new JLabel("Enter First Name:");
        private JLabel lastNameFieldLabel = new JLabel("Enter Last Name:");

        public StaffClockOutToolClickHandler() {
            btn = new JButton("Clock Out");
            btn.setActionCommand("clockOutStaff");
            positionDropDownBox = new JComboBox<>();
            positionDropDownBox.addItem("Nurse");
            positionDropDownBox.addItem("Doctor");
            positionDropDownBox.addItem("Receptionist");
            firstNameField = new JTextField(15);
            lastNameField = new JTextField(15);
            label = new JLabel("");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text fields, and button
        public void actionPerformed(ActionEvent e) {
            JFrame staffClockOutToolFrame = new JFrame("Clock out a staff member");
            staffClockOutToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            staffClockOutToolFrame.setPreferredSize(new Dimension(500, 325));
            staffClockOutToolFrame.setLayout(new FlowLayout());
            staffClockOutToolFrame.add(new JLabel(blankLineLabelText));
            staffClockOutToolFrame.add(positionInputLabel);
            staffClockOutToolFrame.add(positionDropDownBox);
            staffClockOutToolFrame.add(new JLabel(blankLineLabelText));
            staffClockOutToolFrame.add(firstNameFieldLabel);
            staffClockOutToolFrame.add(firstNameField);
            staffClockOutToolFrame.add(new JLabel(blankLineLabelText));
            staffClockOutToolFrame.add(lastNameFieldLabel);
            staffClockOutToolFrame.add(lastNameField);
            staffClockOutToolFrame.add(new JLabel(blankLineLabelText));
            staffClockOutToolFrame.add(btn);
            staffClockOutToolFrame.add(new JLabel(blankLineLabelText));
            staffClockOutToolFrame.add(label);
            staffClockOutToolFrame.pack();
            staffClockOutToolFrame.setLocationRelativeTo(null);
            staffClockOutToolFrame.setVisible(true);
            staffClockOutToolFrame.setResizable(false);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new StaffClockOutToolFrameClickHandler());
        }

        private class StaffClockOutToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Clocks out staff member according to text fields
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clockOutStaff")) {
                    String position = (String) positionDropDownBox.getSelectedItem();
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String result;
                    result = staffClockOut(position, firstName, lastName);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // MODIFIES: this, ActiveStaff, activeNurses
            // EFFECTS: Clocks out staff member
            public String staffClockOut(String position, String firstName, String lastName) {
                Staff staffToClockOut = new Staff(firstName, lastName);
                staffToClockOut.setPosition(position);
                try {
                    ActiveStaff.getInstance().clockOut(staffToClockOut);
                    if (position.equals("Nurse")) { //TODO: Move to clockOut()
                        for (Nurse nurse : activeNurses) {
                            if (nurse.getFullName().equals(staffToClockOut.getFullName())) {
                                List<Room> roomsToRemoveNurse = nurse.findAssignedRooms();
                                for (Room room : roomsToRemoveNurse) {
                                    room.clearNurse();
                                }
                                activeNurses.remove(nurse);
                                break;
                            }
                        }
                    }
                    return position + " " + firstName + " " + lastName + " has been clocked out.";
                } catch (StaffClockedInException e) {
                    return "Error: " + e.getMessage();
                }
            }

            // MODIFIES: this
            // EFFECTS: Clears input fields
            private void clearTextFields() {
                clearTextField(firstNameField);
                clearTextField(lastNameField);
            }
        }
    }
}
