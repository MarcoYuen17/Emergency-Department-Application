package ui.tools.patienttools;

import model.CheckedInPatients;
import model.exceptions.PatientCheckedInException;
import model.people.Patient;
import ui.EmergencyDepartment;
import ui.tools.Tool;
import ui.tools.clickhandlers.ToolClickHandler;
import ui.tools.clickhandlers.ToolFrameClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a GUI tool to discharge patients from the Emergency Department
 */

public class DischargePatientTool extends Tool {

    public DischargePatientTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Discharge a Patient";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new DischargePatientToolClickHandler());
    }

    private class DischargePatientToolClickHandler extends ToolClickHandler implements ActionListener {

        private JTextField patientIDField;

        public DischargePatientToolClickHandler() {
            btn = new JButton("Discharge");
            btn.setActionCommand("discharge");
            patientIDField = new JTextField(10);
            label = new JLabel("");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text field, and button
        public void actionPerformed(ActionEvent e) {
            JFrame dischargePatientToolFrame = new JFrame("Discharge a Patient");
            dischargePatientToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dischargePatientToolFrame.setPreferredSize(new Dimension(450, 200));
            dischargePatientToolFrame.setLayout(new FlowLayout());
            JLabel patientIDFieldLabel = new JLabel("Enter ID of Patient to Discharge:");
            dischargePatientToolFrame.add(new JLabel(blankLineLabelText));
            dischargePatientToolFrame.add(patientIDFieldLabel);
            dischargePatientToolFrame.add(patientIDField);
            dischargePatientToolFrame.add(btn);
            dischargePatientToolFrame.add(new JLabel(blankLineLabelText));
            dischargePatientToolFrame.add(label);
            dischargePatientToolFrame.pack();
            dischargePatientToolFrame.setLocationRelativeTo(null);
            dischargePatientToolFrame.setVisible(true);
            dischargePatientToolFrame.setResizable(false);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new DischargePatientToolFrameClickHandler());
        }

        private class DischargePatientToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Discharges patient according to text field
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("discharge")) {
                    int patientIDToDischarge = Integer.parseInt(patientIDField.getText());
                    String result = dischargePatient(patientIDToDischarge);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // MODIFIES: CheckedInPatients
            // EFFECTS: Discharges a patient from the ED
            public String dischargePatient(int idToDischarge) {
                CheckedInPatients checkedInPatients = CheckedInPatients.getInstance();
                for (Patient p: checkedInPatients.listOfCheckedInPatients()) {
                    if (p.getID() == idToDischarge) {
                        try {
                            checkedInPatients.dischargePatient(p);
                            return "Patient " + p.getFullName() + " - " + idToDischarge
                                    + " has been discharged from the ED.";
                        } catch (PatientCheckedInException e) {
                            return "Error: " + e.getMessage();
                        }
                    }
                }
                return "Error: Patient " + idToDischarge + " was not found";
            }

            // MODIFIES: this
            // EFFECTS: Clears input fields
            private void clearTextFields() {
                clearTextField(patientIDField);
            }
        }
    }

}
