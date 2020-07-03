package ui.tools.applicationtools;

import model.ActiveStaff;
import model.CheckedInPatients;
import model.people.Nurse;
import model.people.Patient;
import model.people.Staff;
import persistence.Writer;
import ui.EmergencyDepartment;
import ui.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static model.ActiveStaff.activeNurses;

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

        // From https://github.students.cs.ubc.ca/CPSC210/TellerApp

        // MODIFIES: PATIENTS_FILE
        // EFFECTS: Saves patients to PATIENTS_FILE
        private String savePatients(String resultSoFar) {
            try {
                Writer writer = new Writer(new File(PATIENTS_FILE));
                for (Patient p: CheckedInPatients.getInstance().listOfCheckedInPatients()) {
                    writer.write(p);
                }
                writer.close();
                return resultSoFar + "Patients saved to file " + PATIENTS_FILE + ".<br><br>";
            } catch (FileNotFoundException e) {
                return resultSoFar + "Unable to save patients to " + PATIENTS_FILE + ". (File not found)<br><br>";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return resultSoFar + "An error occurred when saving the patients to " + PATIENTS_FILE + ".<br><br>";
        }

        // MODIFIES: NURSES_FILE
        // EFFECTS: Saves nurses to NURSES_FILE
        private String saveNurses(String resultSoFar) {
            try {
                Writer writer = new Writer(new File(NURSES_FILE));
                for (Nurse n: activeNurses) {
                    writer.write(n);
                }
                writer.close();
                return resultSoFar + "Nurses saved to file " + NURSES_FILE + ".<br><br>";
            } catch (FileNotFoundException e) {
                return resultSoFar + "Unable to save nurses to " + NURSES_FILE + ". (File not found)<br><br>";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return resultSoFar + "An error occurred when saving the nurses to " + NURSES_FILE + ".<br><br>";
        }

        // MODIFIES: OTHER_STAFF_FILE
        // EFFECTS: Saves other staff to OTHER_STAFF_FILE
        private String saveOtherStaff(String resultSoFar) {
            try {
                Writer writer = new Writer(new File(OTHER_STAFF_FILE));
                for (Staff s: ActiveStaff.getInstance().getListOfActiveStaff()) {
                    if (!s.getPosition().equals("Nurse")) {
                        writer.write(s);
                    }
                }
                writer.close();
                return resultSoFar + "Other staff saved to file " + OTHER_STAFF_FILE + ".";
            } catch (FileNotFoundException e) {
                return resultSoFar + "Unable to save other staff to " + OTHER_STAFF_FILE + ". (File not found)";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return resultSoFar + "An error occurred when saving the other staff to file " + OTHER_STAFF_FILE + ".";
        }
    }
}
