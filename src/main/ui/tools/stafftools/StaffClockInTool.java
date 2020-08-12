package ui.tools.stafftools;

import model.ActiveStaff;
import model.exceptions.StaffClockedInException;
import model.people.Doctor;
import model.people.Nurse;
import model.people.Receptionist;
import ui.EmergencyDepartment;
import ui.tools.Tool;
import ui.tools.clickhandlers.ToolClickHandler;
import ui.tools.clickhandlers.ToolFrameClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.ActiveStaff.activeNurses;

/**
 * Represents a GUI tool to clock in a staff member
 */

public class StaffClockInTool extends Tool {

    public StaffClockInTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Clock In a Staff Member";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new StaffClockInToolClickHandler());
    }

    private class StaffClockInToolClickHandler extends ToolClickHandler implements ActionListener {

        private JTextField positionField;
        private JTextField firstNameField;
        private JTextField lastNameField;
        private JTextField shiftField;

        private JLabel positionFieldLabel = new JLabel("Enter Staff Member's Position:");
        private JLabel positionOptionsFieldLabel = new JLabel("(Nurse/Doctor/Receptionist)");
        private JLabel firstNameFieldLabel = new JLabel("Enter First Name:");
        private JLabel lastNameFieldLabel = new JLabel("Enter Last Name:");
        private JLabel shiftFieldLabel = new JLabel("Enter shift: (24hr-24hr)");

        public StaffClockInToolClickHandler() {
            btn = new JButton("Clock In");
            btn.setActionCommand("clockInStaff");
            positionField = new JTextField(10);
            firstNameField = new JTextField(15);
            lastNameField = new JTextField(15);
            shiftField = new JTextField(10);
            label = new JLabel("");
            addToolFrameListener();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window for labels, text fields, and button
        public void actionPerformed(ActionEvent e) {
            JFrame staffClockInToolFrame = new JFrame("Clock in a staff member");
            staffClockInToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            staffClockInToolFrame.setPreferredSize(new Dimension(500, 375));
            staffClockInToolFrame.setLayout(new FlowLayout());
            addLabelsAndFields(staffClockInToolFrame);
            staffClockInToolFrame.pack();
            staffClockInToolFrame.setLocationRelativeTo(null);
            staffClockInToolFrame.setVisible(true);
            staffClockInToolFrame.setResizable(false);
        }

        // MODIFIES: window
        // EFFECTS: Adds all staff text fields and labels describing the text fields
        private void addLabelsAndFields(JFrame window) {
            window.add(new JLabel(blankLineLabelText));
            window.add(positionFieldLabel);
            window.add(positionOptionsFieldLabel);
            window.add(positionField);
            window.add(new JLabel(blankLineLabelText));
            window.add(firstNameFieldLabel);
            window.add(firstNameField);
            window.add(new JLabel(blankLineLabelText));
            window.add(lastNameFieldLabel);
            window.add(lastNameField);
            window.add(new JLabel(blankLineLabelText));
            window.add(shiftFieldLabel);
            window.add(shiftField);
            window.add(new JLabel(blankLineLabelText));
            window.add(btn);
            window.add(new JLabel(blankLineLabelText));
            window.add(label);
        }

        // MODIFIES: this
        // EFFECTS: Associates new button with new ClickHandler
        private void addToolFrameListener() {
            btn.addActionListener(new StaffClockInToolFrameClickHandler());
        }

        private class StaffClockInToolFrameClickHandler extends ToolFrameClickHandler implements ActionListener {

            @Override
            // MODIFIES: this
            // EFFECTS: Clocks in staff member according to text fields
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("clockInStaff")) {
                    String position = positionField.getText();
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String shift = shiftField.getText();
                    String result;
                    result = staffClockIn(position, firstName, lastName, shift);
                    label.setText(result);
                    Toolkit.getDefaultToolkit().beep();
                    clearTextFields();
                }
            }

            // MODIFIES: this, ActiveStaff, activeNurses
            // EFFECTS: Clocks in staff member
            public String staffClockIn(String position, String firstName, String lastName, String shift) {
                ActiveStaff activeStaff = ActiveStaff.getInstance();
                try {
                    if (position.equals("Doctor")) {
                        Doctor doctor = new Doctor(firstName, lastName, shift);
                        activeStaff.clockIn(doctor, shift);
                        return "Doctor " + doctor.getFullName() + " has been clocked in for " + shift + ".";
                    } else if (position.equals("Nurse")) {
                        Nurse nurse = new Nurse(firstName, lastName, shift);
                        activeStaff.clockIn(nurse, shift);
                        return "Nurse " + nurse.getFullName() + " has been clocked in for " + shift + ".";
                    } else if (position.equals("Receptionist")) {
                        Receptionist receptionist = new Receptionist(firstName, lastName, shift);
                        activeStaff.clockIn(receptionist, shift);
                        return "Receptionist " + receptionist.getFullName() + " has been clocked in for " + shift
                                + ".";
                    } else {
                        return "Error: Position was not one of 'Doctor', 'Nurse', or 'Receptionist'";
                    }
                } catch (StaffClockedInException e) {
                    return "Error: " + e.getMessage();
                }
            }

            // MODIFIES: this
            // EFFECTS: Clears input fields
            private void clearTextFields() {
                clearTextField(positionField);
                clearTextField(firstNameField);
                clearTextField(lastNameField);
                clearTextField(shiftField);
            }
        }
    }
}
