package ui.tools.applicationtools;

import model.ActiveStaff;
import model.CheckedInPatients;
import model.exceptions.PatientCheckedInException;
import model.exceptions.StaffClockedInException;
import model.people.Nurse;
import model.people.Patient;
import model.people.Staff;
import model.rooms.Room;
import persistence.Reader;
import ui.EmergencyDepartment;
import ui.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.ActiveStaff.activeNurses;
import static model.rooms.Room.allRooms;

/**
 * Represents a GUI tool to load patients, nurses, and other staff from file
 */

public class LoadTool extends Tool {

    public LoadTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: Return string for label
    protected String getLabel() {
        return "Load";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new LoadToolClickHandler());
    }

    private class LoadToolClickHandler extends JFrame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame loadToolFrame = new JFrame("Loading...");
            loadToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            loadToolFrame.setPreferredSize(new Dimension(500, 225));
            loadToolFrame.setLayout(new FlowLayout());
            JLabel label = new JLabel("<html><br><br>" + loadAll());
            loadToolFrame.add(label);
            loadToolFrame.pack();
            loadToolFrame.setLocationRelativeTo(null);
            loadToolFrame.setVisible(true);
            loadToolFrame.setResizable(false);
            Toolkit.getDefaultToolkit().beep();
        }

        // EFFECTS: Loads patients and staff info from PATIENTS_FILE, NURSES_FILE, and OTHER_STAFF_FILE if files exist;
        // otherwise does not load
        public String loadAll() {
            String result = "<html>";
            result = loadPatients(result);
            result = loadNurses(result);
            result = loadOtherStaff(result);
            return result;
        }

        // MODIFIES: allRooms, CheckedInPatients
        // EFFECTS: Loads patients from PATIENTS_FILE if the file exists //TODO: add "and checks them in" and same for other People below, but maybe do this in Decoder instead?
        private String loadPatients(String resultSoFar) {
            try {
                java.util.List<Patient> patients = Reader.readPatients(new File(PATIENTS_FILE));
                for (Patient p: patients) {
                    CheckedInPatients.getInstance().checkInPatient(p);
                    int roomNumberToAssign = p.getRoomNumberToAssign();
                    if (roomNumberToAssign != 0) {
                        for (Room r: allRooms) {
                            if (r.getRoomNumber() == roomNumberToAssign) {
                                p.assignRoom(r);
                            }
                        }
                    }
                }
                return resultSoFar + "All patients loaded.<br><br>";
            } catch (IOException e) {
                return resultSoFar + "Patient file not found - No patients loaded.<br><br>";
            } catch (PatientCheckedInException e) {
                return resultSoFar + "An error occurred while loading the patients file - No patients loaded.<br><br>";
            }
        }

        // MODIFIES: allRooms, ActiveStaff, activeNurses
        // EFFECTS: Loads nurses from NURSES_FILE if the file exists
        private String loadNurses(String resultSoFar) {
            try {
                java.util.List<Nurse> nurses = Reader.readNurses(new File(NURSES_FILE));
                for (Nurse n: nurses) {
                    String shift = n.getShift();
                    ActiveStaff.getInstance().clockIn(n, shift);
                    activeNurses.add(n);
                    ArrayList<Integer> roomNumsToAssign = n.getRoomNumbersToAssign();
                    for (int roomToAssign: roomNumsToAssign) {
                        for (Room r: allRooms) {
                            if (r.getRoomNumber() == roomToAssign) {
                                n.setAssignedRoom(r);
                            }
                        }
                    }
                }
                return resultSoFar + "All nurses loaded.<br><br>";
            } catch (IOException e) {
                return resultSoFar + "Nurse file not found - No nurses loaded.<br><br>";
            } catch (StaffClockedInException e) {
                return resultSoFar + "An error occurred while loading the nurses file - No nurses loaded.<br><br>";
            }
        }

        // MODIFIES: ActiveStaff
        // EFFECTS: Loads other staff from OTHER_STAFF_FILE if the file exists
        private String loadOtherStaff(String resultSoFar) {
            try {
                List<Staff> staff = Reader.readOtherStaff(new File(OTHER_STAFF_FILE));
                for (Staff s: staff) {
                    String shift = s.getShift();
                    ActiveStaff.getInstance().clockIn(s, shift);
                }
                return resultSoFar + "All other staff loaded.";
            } catch (IOException e) {
                return resultSoFar + "Other staff file not found - No other staff loaded";
            } catch (StaffClockedInException e) {
                return resultSoFar + "An error occurred while loading the other staff file - No other staff loaded.<br>"
                        + "<br>";
            }
        }
    }
}
