package ui.tools.applicationtools;

import ui.EmergencyDepartment;
import ui.tools.Tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a GUI tool to quit the application
 */

public class QuitTool extends Tool {

    public QuitTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: returns string for label
    protected String getLabel() {
        return "Quit";
    }

    // MODIFIES: this
    // EFFECTS: associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new QuitToolClickHandler());
    }

    private class QuitToolClickHandler implements ActionListener {

        // EFFECTS: Closes application
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
