package ui.tools.applicationtools;

import persistence.Encoder;
import model.ActiveStaff;
import model.CheckedInPatients;
import ui.EmergencyDepartment;
import ui.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a GUI tool to save patients, nurses, and other staff to file
 */

public class SaveTool extends Tool {

    public SaveTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Save";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new SaveToolClickHandler());
    }

    private class SaveToolClickHandler extends JFrame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame saveToolFrame = new JFrame("Saving...");
            saveToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            saveToolFrame.setPreferredSize(new Dimension(400, 225));
            saveToolFrame.setLayout(new FlowLayout());
            JLabel label = new JLabel("<html><br><br>" + saveAll());
            saveToolFrame.add(label);
            saveToolFrame.pack();
            saveToolFrame.setLocationRelativeTo(null);
            saveToolFrame.setVisible(true);
            saveToolFrame.setResizable(false);
            Toolkit.getDefaultToolkit().beep();
        }

        // EFFECTS: Saves all patients and staff to file
        public String saveAll() {
            String result = "<html>";
            result = savePatients(result);
            result = saveNurses(result);
            result = saveOtherStaff(result);
            return result;
        }

        // MODIFIES: PATIENTS_FILE
        // EFFECTS: Saves patients to PATIENTS_FILE
        private String savePatients(String resultSoFar) {
            try {
                Encoder.encodePatients(CheckedInPatients.getInstance().listOfCheckedInPatients(), PATIENTS_FILE_PATH);
                return resultSoFar + "Patients saved to file " + PATIENTS_FILE_PATH + ".<br><br>";
            } catch (IOException e) {
                return resultSoFar + "An error occurred when saving the patients to " + PATIENTS_FILE_PATH
                        + ". (IOException)<br><br>";
            }
        }

        // MODIFIES: NURSES_FILE
        // EFFECTS: Saves nurses to NURSES_FILE
        private String saveNurses(String resultSoFar) {
            try {
                Encoder.encodeNurses(ActiveStaff.getInstance().getListOfActiveNurses(), NURSES_FILE_PATH);
                return resultSoFar + "Nurses saved to file " + NURSES_FILE_PATH + ".<br><br>";
            } catch (IOException e) {
                return resultSoFar + "An error occurred when saving the nurses to " + NURSES_FILE_PATH
                        + ". (IOException)<br><br>";
            }
        }

        // MODIFIES: OTHER_STAFF_FILE
        // EFFECTS: Saves other staff to OTHER_STAFF_FILE
        private String saveOtherStaff(String resultSoFar) {
            try {
                Encoder.encodeOtherStaff(ActiveStaff.getInstance().getListOfActiveOtherStaff(), OTHER_STAFF_FILE_PATH);
                return resultSoFar + "Other staff saved to file " + OTHER_STAFF_FILE_PATH + ".";
            } catch (IOException e) {
                return resultSoFar + "An error occurred when saving the other staff to " + OTHER_STAFF_FILE_PATH
                        + ". (IOException)";
            }
        }
    }
}
