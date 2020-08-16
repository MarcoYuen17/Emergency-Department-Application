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
 * Represents a GUI tool to check in new patients into the Emergency Department
 */

public class PatientCheckInTool extends Tool {

    public PatientCheckInTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "New Patient Check In";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new PatientCheckInToolClickHandler());
    }

    private class PatientCheckInToolClickHandler extends ToolClickHandler implements ActionListener {

        private JButton clearBtn;

        private JTextField patientFirstNameField;
        private JTextField patientLastNameField;
        private JTextField patientDobField;
        private JTextField patientIDField;
        private JTextField patientTimeInField;
        private JComboBox<Integer> patientUrgencyDropDownBox;
        private JTextField patientReasonField;
        private JTextField patientPrecautionsField;
        private JTextField patientAllergiesField;
        private JTextField patientMedicationsField;

        private JLabel patientFirstNameFieldLabel = new JLabel("Enter the Patient's First Name:");
        private JLabel patientLastNameFieldLabel = new JLabel("Enter the Patient's Last Name:");
        private JLabel patientDobFieldLabel = new JLabel("Enter the Patient's Date of Birth in YYYYMMDD:");
        private JLabel patientIDFieldLabel = new JLabel("Enter the Patient's Health ID:");
        private JLabel patientTimeInFieldLabel = new JLabel("Enter the Current Time:  (24hr)");
        private JLabel patientUrgencyInputLabel = new JLabel("Select the Patient's Urgency:  (1 = low, 2 = moderate"
                + ", 3 = high)");
        private JLabel patientReasonFieldLabel = new JLabel("Enter the Patient's Reason for Visit:");
        private JLabel patientPrecautionsFieldLabel = new JLabel("Enter the Patient's Recommended Precautions:");
        private JLabel patientAllergiesFieldLabel = new JLabel("Enter the Patient's Allergies:");
        private JLabel patientMedicationsFieldLabel = new JLabel("Enter the Patient's Medications:");

        public PatientCheckInToolClickHandler() {
            btn = new JButton("Check In");
            btn.setActionCommand("checkIn");
            clearBtn = new JButton("Clear Fields");
            clearBtn.setActionCommand("clear");
            patientFirstNameField = new JTextField(20);
            patientLastNameField = new JTextField(20);
            patientDobField = new JTextField(10);
            patientIDField = new JTextField(10);
            patientTimeInField = new JTextField(10);
            patientUrgencyDropDownBox = new JComboBox<>();
            patientUrgencyDropDownBox.addItem(1);
            patientUrgencyDropDownBox.addItem(2);
            patientUrgencyDropDownBox.addItem(3);
            patientReasonField = new JTextField(30);
            patientPrecautionsField = new JTextField(10);
            patientAllergiesField = new JTextField(20);
            patientMedicationsField = new JTextField(20);
            label = new JLabel("");
            addToolFrameListener();
            addClearListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text fields, and button
        public void actionPerformed(ActionEvent e) {
            JFrame patientCheckInToolFrame = new JFrame("Check In a New Patient");
            patientCheckInToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            patientCheckInToolFrame.setPreferredSize(new Dimension(450, 500));
            patientCheckInToolFrame.setLayout(new FlowLayout());
            patientCheckInToolFrame.add(new JLabel(blankLineLabelText));
            addLabelsAndFields(patientCheckInToolFrame);
            patientCheckInToolFrame.add(new JLabel(blankLineLabelText));
            patientCheckInToolFrame.add(btn);
            patientCheckInToolFrame.add(clearBtn);
            patientCheckInToolFrame.add(new JLabel(blankLineLabelText));
            patientCheckInToolFrame.add(label);
            patientCheckInToolFrame.pack();
            patientCheckInToolFrame.setLocationRelativeTo(null);
            patientCheckInToolFrame.setVisible(true);
            patientCheckInToolFrame.setResizable(false);
        }

        // MODIFIES: window
        // EFFECTS: Adds all patient text fields and labels describing the text fields
        private void addLabelsAndFields(JFrame window) {
            window.add(patientFirstNameFieldLabel);
            window.add(patientFirstNameField);
            window.add(patientLastNameFieldLabel);
            window.add(patientLastNameField);
            window.add(patientDobFieldLabel);
            window.add(patientDobField);
            window.add(patientIDFieldLabel);
            window.add(patientIDField);
            window.add(patientTimeInFieldLabel);
            window.add(patientTimeInField);
            window.add(patientUrgencyInputLabel);
            window.add(patientUrgencyDropDownBox);
            window.add(patientReasonFieldLabel);
            window.add(patientReasonField);
            window.add(patientPrecautionsFieldLabel);
            window.add(patientPrecautionsField);
            window.add(patientAllergiesFieldLabel);
            window.add(patientAllergiesField);
            window.add(patientMedicationsFieldLabel);
            window.add(patientMedicationsField);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new PatientCheckInToolFrameClickHandler());
        }

        // MODIFIES: this
        // EFFECTS: Associates clear button with new ClickHandler
        private void addClearListener() {
            clearBtn.addActionListener(new ClearClickHandler());
        }

        private class PatientCheckInToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this, CheckedInPatients
            // EFFECTS: Checks in new patient according to text fields
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("checkIn")) {
                    String firstName = patientFirstNameField.getText();
                    String lastName = patientLastNameField.getText();
                    int dob = Integer.parseInt(patientDobField.getText());
                    int id = Integer.parseInt(patientIDField.getText());
                    int timeCheckedIn = Integer.parseInt(patientTimeInField.getText());
                    int urgency = (Integer) patientUrgencyDropDownBox.getSelectedItem();
                    String reason = patientReasonField.getText();
                    String precautions = patientPrecautionsField.getText();
                    String allergies = patientAllergiesField.getText();
                    String medications = patientMedicationsField.getText();
                    String result = createPatient(firstName, lastName, dob, id, timeCheckedIn, urgency, reason,
                            precautions, allergies, medications);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }

        // MODIFIES: CheckedInPatients
        // EFFECTS: Constructs a new patient and checks them in
        public String createPatient(String firstName, String lastName, int dob, int id, int timeIn, int urgency,
                                    String reasonForVisit, String precautions, String allergies, String medications) {

            Patient patient = new Patient(firstName, lastName, dob, id, timeIn, urgency, reasonForVisit,
                    precautions, allergies, medications);

            try {
                CheckedInPatients.getInstance().checkInPatient(patient);
                return "Patient " + patient.getFullName() + " - " + patient.getID() + " has been checked in.";
            } catch (PatientCheckedInException e) {
                return "Error: " + e.getMessage();
            }
        }

        private class ClearClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Clears input fields and label
            public void actionPerformed(ActionEvent e) {
                clearTextField(patientFirstNameField);
                clearTextField(patientLastNameField);
                clearTextField(patientDobField);
                clearTextField(patientIDField);
                clearTextField(patientTimeInField);
                clearTextField(patientReasonField);
                clearTextField(patientPrecautionsField);
                clearTextField(patientAllergiesField);
                clearTextField(patientMedicationsField);
                label.setText("");
            }
        }
    }
}
