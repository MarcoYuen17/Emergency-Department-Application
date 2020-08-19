package ui.tools.stafftools;

import model.people.Nurse;
import model.people.Patient;
import model.rooms.Capabilities;
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
 * Represents a GUI tool to show all relevant information of a room
 */

public class ShowRoomStatusTool extends Tool {

    public ShowRoomStatusTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Show a Room's Status";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new ShowOneRoomAllInfoToolClickHandler());
    }

    private class ShowOneRoomAllInfoToolClickHandler extends ToolClickHandler implements ActionListener {

        private JTextField roomNumField;

        public ShowOneRoomAllInfoToolClickHandler() {
            btn = new JButton("Get Information");
            btn.setActionCommand("showRoomAllInfo");
            roomNumField = new JTextField(5);
            label = new JLabel("");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text field, and button
        public void actionPerformed(ActionEvent e) {
            JFrame showOneRoomAllInfoToolFrame = new JFrame("Show All of One Room's Current Information");
            showOneRoomAllInfoToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            showOneRoomAllInfoToolFrame.setPreferredSize(new Dimension(300, 300));
            showOneRoomAllInfoToolFrame.setLayout(new FlowLayout());
            JLabel roomNumFieldLabel = new JLabel("Enter Room Number to Retrieve Information:");
            showOneRoomAllInfoToolFrame.add(new JLabel(blankLineLabelText));
            showOneRoomAllInfoToolFrame.add(roomNumFieldLabel);
            showOneRoomAllInfoToolFrame.add(roomNumField);
            showOneRoomAllInfoToolFrame.add(btn);
            showOneRoomAllInfoToolFrame.add(new JLabel(blankLineLabelText));
            showOneRoomAllInfoToolFrame.add(label);
            showOneRoomAllInfoToolFrame.pack();
            showOneRoomAllInfoToolFrame.setLocationRelativeTo(null);
            showOneRoomAllInfoToolFrame.setVisible(true);
            showOneRoomAllInfoToolFrame.setResizable(false);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new ShowOneRoomAllInfoToolFrameClickHandler());
        }

        private class ShowOneRoomAllInfoToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Shows all of one room's info according to text field
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("showRoomAllInfo")) {
                    int roomNumToFind = Integer.parseInt(roomNumField.getText());
                    String result = givenRoomGetInfo(roomNumToFind);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // EFFECTS: Returns all information of given room number
            public String givenRoomGetInfo(int roomNum) {
                if (isRoomNumIllegal(roomNum)) {
                    return ERROR_ROOM_DOES_NOT_EXIST;
                }
                for (Room room: allRooms) {
                    if (room.getRoomNumber() == roomNum) {
                        String patientInfo = givenRoomGetPatientAndID(room);
                        String nurseName = givenRoomGetNurse(room);
                        String precautions = room.getPrecautions();
                        String capabilities = givenRoomGetCapabilities(room);
                        return "<html>Room " + roomNum + " Information:<br><br>" + "Patient: " + patientInfo
                                + "<br>Nurse: " + nurseName + "<br>Precautions: "
                                + precautions + "<br>Special Capabilities:" + capabilities;
                    }
                }
                return "Room " + roomNum + " information could not be found";
            }

            // EFFECTS: Returns patient with id in room or "No Assigned Patient" if null
            private String givenRoomGetPatientAndID(Room room) {
                try {
                    Patient patient = room.getPatient();
                    String patientName = patient.getFullName();
                    String patientID = Integer.toString(patient.getID());
                    return patientName + " - " + patientID;
                } catch (NullPointerException e) {
                    return "No Assigned Patient";
                }
            }

            // EFFECTS: Returns full name of nurse in room or "No Assigned Nurse" if null
            private String givenRoomGetNurse(Room room) {
                try {
                    Nurse nurse = room.getNurse();
                    return nurse.getFullName();
                } catch (NullPointerException e) {
                    return "No Assigned Nurse";
                }
            }

            // EFFECTS: Returns all special capabilities of given room or "None" if null
            private String givenRoomGetCapabilities(Room room) {
                String capabilities = "";
                try {
                    for (Capabilities c: room.listCapabilities()) {
                        capabilities = capabilities + " " + c + ",";
                    }
                    return capabilities;
                } catch (NullPointerException e) {
                    return " None";
                }
            }

            // MODIFIES: this
            // EFFECTS: Clears input field
            private void clearTextFields() {
                clearTextField(roomNumField);
            }
        }
    }
}
