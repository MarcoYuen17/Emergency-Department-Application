package ui.tools;

import ui.EmergencyDepartment;

import javax.swing.*;

public class BlankTool extends Tool {

    /**
     * Represents a GUI tool which fills empty space in the options menu
     */

    public BlankTool(EmergencyDepartment ed, JComponent parent) {
        super(ed, parent);
    }

    @Override
    // EFFECTS: Returns string for label
    protected String getLabel() {
        return "";
    }

    @Override
    protected void addListener() {
    }
}
