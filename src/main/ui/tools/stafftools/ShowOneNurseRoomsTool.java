package ui.tools.stafftools;

import model.people.Nurse;
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
 * Represents a GUI tool to show one nurse's assigned rooms
 */

public class ShowOneNurseRoomsTool extends Tool {

    public ShowOneNurseRoomsTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Show a Nurse's Rooms";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new ShowOneNurseRoomsToolClickHandler());
    }

    private class ShowOneNurseRoomsToolClickHandler extends ToolClickHandler implements ActionListener {

        private JTextField nurseFirstNameField;
        private JTextField nurseLastNameField;

        public ShowOneNurseRoomsToolClickHandler() {
            nurseFirstNameField = new JTextField(15);
            nurseLastNameField = new JTextField(15);
            label = new JLabel("");
            btn = new JButton("Show Rooms");
            btn.setActionCommand("showNurseAssignedRooms");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text fields, and button
        public void actionPerformed(ActionEvent e) {
            JFrame showOneNurseRoomsToolFrame = new JFrame("Show a Nurse's Assigned Rooms");
            showOneNurseRoomsToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            showOneNurseRoomsToolFrame.setPreferredSize(new Dimension(350, 250));
            showOneNurseRoomsToolFrame.setLayout(new FlowLayout());
            JLabel nurseFirstNameFieldLabel = new JLabel("Enter First Name of Nurse:");
            JLabel nurseLastNameFieldLabel = new JLabel("Enter Last Name of Nurse:");
            showOneNurseRoomsToolFrame.add(new JLabel(blankLineLabelText));
            showOneNurseRoomsToolFrame.add(nurseFirstNameFieldLabel);
            showOneNurseRoomsToolFrame.add(nurseFirstNameField);
            showOneNurseRoomsToolFrame.add(nurseLastNameFieldLabel);
            showOneNurseRoomsToolFrame.add(nurseLastNameField);
            showOneNurseRoomsToolFrame.add(btn);
            showOneNurseRoomsToolFrame.add(new JLabel(blankLineLabelText));
            showOneNurseRoomsToolFrame.add(label);
            showOneNurseRoomsToolFrame.pack();
            showOneNurseRoomsToolFrame.setLocationRelativeTo(null);
            showOneNurseRoomsToolFrame.setVisible(true);
            showOneNurseRoomsToolFrame.setResizable(false);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new ShowOneNurseRoomsToolFrameClickHandler());
        }

        private class ShowOneNurseRoomsToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Shows one nurse's rooms according to text fields
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("showNurseAssignedRooms")) {
                    String nurseFirstName = nurseFirstNameField.getText();
                    String nurseLastName = nurseLastNameField.getText();
                    String result = givenNurseFindRooms(nurseFirstName, nurseLastName);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // EFFECTS: Returns assigned rooms of given nurse name
            public String givenNurseFindRooms(String firstName, String lastName) {
                for (Nurse nurse: activeNurses) {
                    if (nurse.getFullName().equals(firstName + " " + lastName)) {
                        String nurseFullName = nurse.getFullName();
                        List<Room> assignedRooms = nurse.findAssignedRooms();
                        if (assignedRooms.size() == 0) {
                            return "Nurse " + nurseFullName + " has no assigned rooms.";
                        }
                        String nurseRooms = "Nurse " + nurseFullName + " has rooms:";
                        for (Room room: nurse.findAssignedRooms()) {
                            nurseRooms = nurseRooms + " " + room.getRoomNumber();
                        }
                        return nurseRooms;
                    }
                }
                return "Error: Nurse " + firstName + " " + lastName + " was not found";
            }

            // MODIFIES: this
            // EFFECTS: Clears input fields
            private void clearTextFields() {
                clearTextField(nurseFirstNameField);
                clearTextField(nurseLastNameField);
            }
        }
    }
}
