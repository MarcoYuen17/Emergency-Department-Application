package ui.tools.patienttools;

import model.CheckedInPatients;
import model.people.Patient;
import ui.EmergencyDepartment;
import ui.tools.Tool;
import ui.tools.clickhandlers.ToolClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a GUI tool to show the next patient who needs a room in the Emergency Department
 * based on urgency and time checked in
 */

public class ShowNextPatientNeedsRoomTool extends Tool {

    public ShowNextPatientNeedsRoomTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "<html>Show Next Patient <br>Who Needs a Room</html>";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new ShowNextPatientNeedsRoomToolClickHandler());
    }

    private class ShowNextPatientNeedsRoomToolClickHandler extends ToolClickHandler implements ActionListener {

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window to show next patient to assign room
        public void actionPerformed(ActionEvent e) {
            JFrame showNextPatientNeedsRoomToolFrame = new JFrame("Next Patient to Assign Room:");
            showNextPatientNeedsRoomToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            showNextPatientNeedsRoomToolFrame.setPreferredSize(new Dimension(350, 150));
            showNextPatientNeedsRoomToolFrame.setLayout(new FlowLayout());
            showNextPatientNeedsRoomToolFrame.add(new JLabel(blankLineLabelText));
            label = new JLabel(showNextPatient());
            showNextPatientNeedsRoomToolFrame.add(label);
            showNextPatientNeedsRoomToolFrame.pack();
            showNextPatientNeedsRoomToolFrame.setLocationRelativeTo(null);
            showNextPatientNeedsRoomToolFrame.setVisible(true);
            showNextPatientNeedsRoomToolFrame.setResizable(false);
            Toolkit.getDefaultToolkit().beep();
        }

        // EFFECTS: Shows full name and id of next patient without room
        //          with highest urgency who has waited for longest time
        public String showNextPatient() {
            Patient next = CheckedInPatients.getInstance().nextPatientWithoutRoom();
            if (next == null) {
                return "No Waiting Patients";
            }
            String nextName = next.getFullName();
            int nextID = next.getID();
            return "Next Patient: " + nextName + " - " + nextID;
        }
    }
}
