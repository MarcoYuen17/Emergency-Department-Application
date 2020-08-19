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

/**
 * Represents a GUI tool to show all of one patient's information
 */

public class ShowAllOnePatientInfoTool extends Tool {

    public ShowAllOnePatientInfoTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "<html>Show All Of One <br>Patient's Information</html>";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new ShowAllOnePatientInfoToolClickHandler());
    }

    private class ShowAllOnePatientInfoToolClickHandler extends ToolClickHandler implements ActionListener {

        private JTextField patientIDField;

        public ShowAllOnePatientInfoToolClickHandler() {
            btn = new JButton("Get All Information");
            btn.setActionCommand("showAllOnePatientInfo");
            patientIDField = new JTextField(10);
            label = new JLabel("");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text field, and button
        public void actionPerformed(ActionEvent e) {
            JFrame showAllOnePatientToolFrame = new JFrame("Show All of One Patient's Information");
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            showAllOnePatientToolFrame.setPreferredSize(new Dimension(200, 400));
            showAllOnePatientToolFrame.setLayout(new FlowLayout());
            JLabel patientIDFieldLabel = new JLabel("Enter ID of Patient to See Information:");
            showAllOnePatientToolFrame.add(new JLabel(blankLineLabelText));
            showAllOnePatientToolFrame.add(patientIDFieldLabel);
            showAllOnePatientToolFrame.add(patientIDField);
            showAllOnePatientToolFrame.add(btn);
            showAllOnePatientToolFrame.add(new JLabel(blankLineLabelText));
            showAllOnePatientToolFrame.add(label);
            showAllOnePatientToolFrame.pack();
            showAllOnePatientToolFrame.setLocationRelativeTo(null);
            showAllOnePatientToolFrame.setVisible(true);
            showAllOnePatientToolFrame.setResizable(false);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new ShowOnePatientInfoToolFrameClickHandler());
        }

        private class ShowOnePatientInfoToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Shows all information related to patient with given id
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("showAllOnePatientInfo")) {
                    int patientIDToShow = Integer.parseInt(patientIDField.getText());
                    String result = printPatientInfo(patientIDToShow);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // EFFECTS: Returns all info of given patient on separate lines
            public String printPatientInfo(int idForInfo) {
                for (Patient p: CheckedInPatients.getInstance().listOfCheckedInPatients()) {
                    if (p.getID() == idForInfo) {
                        return p.allInfoGUI();
                    }
                }
                return "Error: Patient " + idForInfo + " was not found";
            }

            // MODIFIES: this
            // EFFECTS: Clears input fields
            private void clearTextFields() {
                clearTextField(patientIDField);
            }
        }
    }
}
