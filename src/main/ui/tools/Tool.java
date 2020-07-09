package ui.tools;

import ui.EmergencyDepartment;

import javax.swing.*;

/**
 * Abstract class to represent GUI tools
 */

public abstract class Tool {

    // From https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete

    protected JButton button;
    private EmergencyDepartment ed;
    public static String blankLineLabelText = "                                                                        "
            + "                                                                                                      ";

    public static final String PATIENTS_FILE = "./data/patients.txt"; //TODO: Move to save file?
    public static final String NURSES_FILE = "./data/nurses.txt";
    public static final String OTHER_STAFF_FILE = "./data/otherstaff.txt";

    public Tool(EmergencyDepartment ed, JComponent parent) { //TODO: add dropdown menus for checkins & clockins
        this.ed = ed;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: creates new button to activate tool and adds to parent
    protected void createButton(JComponent parent) {
        button = new JButton(getLabel());
        button = customizeButton(button);
    }

    // EFFECTS: returns string for label
    protected abstract String getLabel();

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }


}
