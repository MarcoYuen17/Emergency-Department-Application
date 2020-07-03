package ui.tools.patienttools;

import model.CheckedInPatients;
import ui.EmergencyDepartment;
import ui.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a GUI tool to show all currently checked in patients in the Emergency Department
 */

public class ShowAllCheckedInPatientsTool extends Tool {

    public ShowAllCheckedInPatientsTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Show All Checked In Patients";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new ShowAllCheckedInPatientsToolClickHandler());
    }

    private class ShowAllCheckedInPatientsToolClickHandler extends JFrame implements ActionListener {

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window to show all patients currently clocked in
        //          with their room, name, id, time in, and urgency
        public void actionPerformed(ActionEvent e) {
            JFrame showAllPatientsToolFrame = new JFrame("All Checked In Patients:");
            showAllPatientsToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            showAllPatientsToolFrame.setPreferredSize(new Dimension(500, 1000));
            showAllPatientsToolFrame.setLayout(new FlowLayout());
            JLabel label = new JLabel(showAllPatients());
            showAllPatientsToolFrame.add(new JLabel(blankLineLabelText));
            showAllPatientsToolFrame.add(label);
            showAllPatientsToolFrame.pack();
            showAllPatientsToolFrame.setLocationRelativeTo(null);
            showAllPatientsToolFrame.setVisible(true);
            showAllPatientsToolFrame.setResizable(false);
        }

        // EFFECTS: Shows all checked in patients
        public String showAllPatients() {
            return CheckedInPatients.getInstance().showAllPatientsGUI();
        }
    }
}
