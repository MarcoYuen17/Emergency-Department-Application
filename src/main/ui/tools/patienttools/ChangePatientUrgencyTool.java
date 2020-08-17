package ui.tools.patienttools;

import model.CheckedInPatients;
import model.people.Patient;
import ui.EmergencyDepartment;
import ui.tools.Tool;
import ui.tools.clickhandlers.ToolClickHandler;
import ui.tools.clickhandlers.ToolFrameClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.people.Patient.MAX_URGENCY;
import static model.people.Patient.MIN_URGENCY;

/**
 * Represents a GUI tool to change a patient's urgency
 */

public class ChangePatientUrgencyTool extends Tool {

    public ChangePatientUrgencyTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Change a patient's urgency";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new ChangePatientUrgencyToolClickHandler());
    }

    private class ChangePatientUrgencyToolClickHandler extends ToolClickHandler implements ActionListener {

        private JTextField patientIDField;
        private JComboBox<Integer> urgencyDropDownBox;

        public ChangePatientUrgencyToolClickHandler() {
            btn = new JButton("Change Urgency");
            btn.setActionCommand("changeUrgency");
            patientIDField = new JTextField(10);
            urgencyDropDownBox = new JComboBox<>();
            urgencyDropDownBox.addItem(1);
            urgencyDropDownBox.addItem(2);
            urgencyDropDownBox.addItem(3);
            label = new JLabel("");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text fields, and button
        public void actionPerformed(ActionEvent e) {
            JFrame changePatientUrgencyToolFrame = new JFrame("Change a Patient's Urgency");
            changePatientUrgencyToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            changePatientUrgencyToolFrame.setPreferredSize(new Dimension(325, 250));
            changePatientUrgencyToolFrame.setLayout(new FlowLayout());
            JLabel patientIDFieldLabel = new JLabel("Enter ID of Patient:");
            JLabel newUrgencyInputLabel = new JLabel("Select New Urgency: (" + MIN_URGENCY + " - " + MAX_URGENCY
                    + ")");
            changePatientUrgencyToolFrame.add(new JLabel(blankLineLabelText));
            changePatientUrgencyToolFrame.add(patientIDFieldLabel);
            changePatientUrgencyToolFrame.add(patientIDField);
            changePatientUrgencyToolFrame.add(newUrgencyInputLabel);
            changePatientUrgencyToolFrame.add(urgencyDropDownBox);
            changePatientUrgencyToolFrame.add(btn);
            changePatientUrgencyToolFrame.add(new JLabel(blankLineLabelText));
            changePatientUrgencyToolFrame.add(label);
            changePatientUrgencyToolFrame.pack();
            changePatientUrgencyToolFrame.setLocationRelativeTo(null);
            changePatientUrgencyToolFrame.setVisible(true);
            changePatientUrgencyToolFrame.setResizable(false);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new ChangeUrgencyToolFrameClickHandler());
        }

        private class ChangeUrgencyToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Changes patient's urgency according to text fields
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("changeUrgency")) {
                    int patientIDToUpdate = Integer.parseInt(patientIDField.getText());
                    int newUrgency = (Integer) urgencyDropDownBox.getSelectedItem();
                    String result = changePatientUrgency(patientIDToUpdate, newUrgency);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // MODIFIES: Patient p
            // EFFECTS: Changes patient's urgency
            public String changePatientUrgency(int idToChange, int newUrgency) {
                if (newUrgency < MIN_URGENCY || newUrgency > MAX_URGENCY) {
                    return "Invalid urgency";
                }
                for (Patient p: CheckedInPatients.getInstance().listOfCheckedInPatients()) {
                    if (p.getID() == idToChange) {
                        p.changeUrgency(newUrgency);
                        return "\nPatient " + p.getFullName() + "'s urgency has been updated to " + newUrgency
                                + ".\n";
                    }
                }
                return "Error: Patient " + idToChange + " was not found";
            }

            // MODIFIES: this
            // EFFECTS: Clears input fields
            private void clearTextFields() {
                clearTextField(patientIDField);
            }
        }
    }
}
