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

import static model.ActiveStaff.activeNurses;
import static model.rooms.Room.*;

/**
 * Represents a GUI tool to assign a nurse to a room
 */

public class AssignNurseToRoomTool extends Tool {

    public AssignNurseToRoomTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Assign a Nurse to a Room";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new AssignNurseToRoomToolClickHandler());
    }

    private class AssignNurseToRoomToolClickHandler extends ToolClickHandler implements ActionListener {

        private JTextField nurseFirstNameField;
        private JTextField nurseLastNameField;
        private JTextField roomNumField;

        public AssignNurseToRoomToolClickHandler() {
            btn = new JButton("Assign");
            btn.setActionCommand("assignNurseToRoom");
            nurseFirstNameField = new JTextField(15);
            nurseLastNameField = new JTextField(15);
            roomNumField = new JTextField(5);
            label = new JLabel("");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text fields, and button
        public void actionPerformed(ActionEvent e) {
            JFrame assignNurseToRoomToolFrame = new JFrame("Assign a Nurse to a Room");
            assignNurseToRoomToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            assignNurseToRoomToolFrame.setPreferredSize(new Dimension(350, 300));
            assignNurseToRoomToolFrame.setLayout(new FlowLayout());
            JLabel nurseFirstNameFieldLabel = new JLabel("Enter First Name of Nurse to Assign:");
            JLabel nurseLastNameFieldLabel = new JLabel("Enter Last Name of Nurse to Assign:");
            JLabel roomNumFieldLabel = new JLabel("Enter Room Number to be Assigned: (101-155)");
            assignNurseToRoomToolFrame.add(new JLabel(blankLineLabelText));
            assignNurseToRoomToolFrame.add(nurseFirstNameFieldLabel);
            assignNurseToRoomToolFrame.add(nurseFirstNameField);
            assignNurseToRoomToolFrame.add(nurseLastNameFieldLabel);
            assignNurseToRoomToolFrame.add(nurseLastNameField);
            assignNurseToRoomToolFrame.add(roomNumFieldLabel);
            assignNurseToRoomToolFrame.add(roomNumField);
            assignNurseToRoomToolFrame.add(btn);
            assignNurseToRoomToolFrame.add(new JLabel(blankLineLabelText));
            assignNurseToRoomToolFrame.add(label);
            assignNurseToRoomToolFrame.pack();
            assignNurseToRoomToolFrame.setLocationRelativeTo(null);
            assignNurseToRoomToolFrame.setVisible(true);
            assignNurseToRoomToolFrame.setResizable(false);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new AssignNurseToRoomToolFrameClickHandler());
        }

        private class AssignNurseToRoomToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Assigns nurse to room according to text fields
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("assignNurseToRoom")) {
                    String nurseFirstNameToAssign = nurseFirstNameField.getText();
                    String nurseLastNameToAssign = nurseLastNameField.getText();
                    int roomNumToAssign = Integer.parseInt(roomNumField.getText());
                    String result = assignNurseToRoom(nurseFirstNameToAssign, nurseLastNameToAssign,
                            roomNumToAssign);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // MODIFIES: Nurse nurse, Room room
            // EFFECTS: Assigns nurse with given name to room with given number
            public String assignNurseToRoom(String firstName, String lastName, int roomNum) {
                if (isRoomNumIllegal(roomNum)) {
                    return ERROR_ROOM_DOES_NOT_EXIST;
                }
                for (Nurse nurse: activeNurses) {
                    if (nurse.getFullName().equals(firstName + " " + lastName)) {
                        for (Room room: allRooms) {
                            if (room.getRoomNumber() == roomNum) {
                                nurse.setAssignedRoom(room);
                                return "Nurse " + nurse.getFullName() + " has been assigned to room "
                                        + room.getRoomNumber() + ".";
                            }
                        }
                    }
                }
                return "Error: Nurse " + firstName + " " + lastName + " was not found";
            }

            // MODIFIES: this
            // EFFECTS: Clears input fields
            private void clearTextFields() {
                clearTextField(nurseFirstNameField);
                clearTextField(nurseLastNameField);
                clearTextField(roomNumField);
            }
        }
    }
}
