package ui.tools.patienttools;

import model.CheckedInPatients;
import model.people.Patient;
import model.rooms.Room;
import ui.EmergencyDepartment;
import ui.tools.Tool;
import ui.tools.clickhandlers.ToolClickHandler;
import ui.tools.clickhandlers.ToolFrameClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.rooms.Room.*;

/**
 * Represents a GUI tool to assign a patient to a room
 */

public class AssignPatientToRoomTool extends Tool {

    public AssignPatientToRoomTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Assign a Patient to a Room";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new AssignPatientToRoomToolClickHandler());
    }

    private class AssignPatientToRoomToolClickHandler extends ToolClickHandler implements ActionListener {

        private JTextField patientIDField;
        private JTextField roomNumField;

        public AssignPatientToRoomToolClickHandler() {
            btn = new JButton("Assign");
            btn.setActionCommand("assignPatientToRoom");
            patientIDField = new JTextField(10);
            roomNumField = new JTextField(5);
            label = new JLabel("");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text fields, and button
        public void actionPerformed(ActionEvent e) {
            JFrame assignPatientToRoomToolFrame = new JFrame("Assign a Patient to a Room");
            assignPatientToRoomToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            assignPatientToRoomToolFrame.setPreferredSize(new Dimension(350, 250));
            assignPatientToRoomToolFrame.setLayout(new FlowLayout());
            JLabel patientIDFieldLabel = new JLabel("Enter ID of Patient to Assign:");
            JLabel roomNumFieldLabel = new JLabel("Enter Room Number to be Assigned: (101-155)");
            assignPatientToRoomToolFrame.add(new JLabel(blankLineLabelText));
            assignPatientToRoomToolFrame.add(patientIDFieldLabel);
            assignPatientToRoomToolFrame.add(patientIDField);
            assignPatientToRoomToolFrame.add(roomNumFieldLabel);
            assignPatientToRoomToolFrame.add(roomNumField);
            assignPatientToRoomToolFrame.add(btn);
            assignPatientToRoomToolFrame.add(new JLabel(blankLineLabelText));
            assignPatientToRoomToolFrame.add(label);
            assignPatientToRoomToolFrame.pack();
            assignPatientToRoomToolFrame.setLocationRelativeTo(null);
            assignPatientToRoomToolFrame.setVisible(true);
            assignPatientToRoomToolFrame.setResizable(false);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new AssignPatientToRoomToolFrameClickHandler());
        }


        private class AssignPatientToRoomToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Assigns patient to room according to text fields
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("assignPatientToRoom")) {
                    int patientIDToAssign = Integer.parseInt(patientIDField.getText());
                    int roomNumToAssign = Integer.parseInt(roomNumField.getText());
                    String result = assignPatientToRoom(patientIDToAssign, roomNumToAssign);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // MODIFIES: Patient p, Room r
            // EFFECTS: Assigns patient with given id to given room
            public String assignPatientToRoom(int idToAssign, int roomNumToAssign) {
                if (isRoomNumIllegal(roomNumToAssign)) {
                    return ERROR_ROOM_DOES_NOT_EXIST;
                }
                for (Patient p: CheckedInPatients.getInstance().listOfCheckedInPatients()) {
                    if (p.getID() == idToAssign) {
                        for (Room r: allRooms) {
                            if (r.getRoomNumber() == roomNumToAssign) {
                                p.assignRoom(r);
                                return "Patient " + p.getFullName() + " - " + p.getID() + " has been assigned to "
                                        + r.getRoomNumberString() + ".";
                            }
                        }
                    }
                }
                return "Error: Patient " + idToAssign + " was not found";
            }

            // MODIFIES: this
            // EFFECTS: Clears input fields
            private void clearTextFields() {
                clearTextField(patientIDField);
                clearTextField(roomNumField);
            }
        }
    }
}
