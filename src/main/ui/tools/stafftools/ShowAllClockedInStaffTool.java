package ui.tools.stafftools;

import model.ActiveStaff;
import ui.EmergencyDepartment;
import ui.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a GUI tool to show all currently clocked in staff in the Emergency Department
 */

public class ShowAllClockedInStaffTool extends Tool {

    public ShowAllClockedInStaffTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Show All Clocked In Staff";
    }

    @Override
    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    protected void addListener() {
        button.addActionListener(new ShowAllClockedInStaffToolClickHandler());
    }

    private class ShowAllClockedInStaffToolClickHandler extends JFrame implements ActionListener {

        @Override
        // MODIFIES: this
        // EFFECTS: Creates new window to show all clocked in staff with their position, name, and shift
        public void actionPerformed(ActionEvent e) {
            JFrame showAllClockedInStaffToolFrame = new JFrame("All Clocked In Staff:");
            showAllClockedInStaffToolFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            showAllClockedInStaffToolFrame.setPreferredSize(new Dimension(400, 1000));
            showAllClockedInStaffToolFrame.setLayout(new FlowLayout());
            JLabel label = new JLabel(showAllStaff());
            showAllClockedInStaffToolFrame.add(new JLabel(blankLineLabelText));
            showAllClockedInStaffToolFrame.add(label);
            showAllClockedInStaffToolFrame.pack();
            showAllClockedInStaffToolFrame.setLocationRelativeTo(null);
            showAllClockedInStaffToolFrame.setVisible(true);
            showAllClockedInStaffToolFrame.setResizable(false);
        }

        // EFFECTS: Shows all clocked in staff
        public String showAllStaff() {
            return ActiveStaff.getInstance().showAllStaffGUI();
        }
    }
}
