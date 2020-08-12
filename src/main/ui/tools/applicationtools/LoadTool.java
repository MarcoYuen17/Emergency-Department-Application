package ui.tools.applicationtools;

import json.persistence.Decoder;
import model.ActiveStaff;
import model.CheckedInPatients;
import model.exceptions.PatientCheckedInException;
import model.exceptions.StaffClockedInException;
import model.people.Nurse;
import model.people.Patient;
import model.people.Staff;
import model.rooms.Room;
import org.json.simple.parser.ParseException;
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
        // EFFECTS: Loads patients from PATIENTS_FILE if the file exists and checks them in
        private String loadPatients(String resultSoFar) {
            try {
                List<Patient> patients = Decoder.decodePatients(new File(PATIENTS_FILE_PATH));
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
                return resultSoFar + createErrorMessage("patients", "IOException");
            } catch (ParseException e) {
                return resultSoFar + createErrorMessage("patients", "ParseException");
            } catch (PatientCheckedInException e) {
                return resultSoFar + createErrorMessage("patients", "PatientCheckedInException");
            }
        }

        // MODIFIES: allRooms, ActiveStaff, activeNurses
        // EFFECTS: Loads nurses from NURSES_FILE if the file exists and clocks them in
        private String loadNurses(String resultSoFar) {
            try {
                List<Nurse> nurses = Decoder.decodeNurses(new File(NURSES_FILE_PATH));
                for (Nurse n: nurses) {
                    String shift = n.getShift();
                    ActiveStaff.getInstance().clockIn(n, shift);
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
                return resultSoFar + createErrorMessage("nurses", "IOException");
            } catch (ParseException e) {
                return resultSoFar + createErrorMessage("nurses", "ParseException");
            } catch (StaffClockedInException e) {
                return resultSoFar + createErrorMessage("nurses", "StaffClockedInException");
            }
        }

        // MODIFIES: ActiveStaff
        // EFFECTS: Loads other staff from OTHER_STAFF_FILE if the file exists and clocks them in
        private String loadOtherStaff(String resultSoFar) {
            try {
                List<Staff> staff = Decoder.decodeOtherStaff(new File(OTHER_STAFF_FILE_PATH));
                for (Staff s: staff) {
                    String shift = s.getShift();
                    ActiveStaff.getInstance().clockIn(s, shift);
                }
                return resultSoFar + "All other staff loaded.";
            } catch (IOException e) {
                return resultSoFar + createErrorMessage("other staff", "IOException");
            } catch (ParseException e) {
                return resultSoFar + createErrorMessage("other staff", "ParseException");
            } catch (StaffClockedInException e) {
                return resultSoFar + createErrorMessage("other staff", "StaffClockedInException");
            }
        }

        // EFFECTS: Creates exception notification message
        private String createErrorMessage(String personType, String exceptionType) {
            String message = "An error occurred while loading the " + personType + " file. "
                    + "(" + exceptionType + ")";
            if (!personType.equals("other staff")) {
                message = message + "<br><br>";
            }
            return message;
        }
    }
}
