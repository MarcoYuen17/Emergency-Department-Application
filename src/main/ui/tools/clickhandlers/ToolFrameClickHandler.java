package ui.tools.clickhandlers;

import javax.swing.*;

/**
 * Abstract class to represent frame click handler in GUI tools
 */

public abstract class ToolFrameClickHandler {

    // MODIFIES: field
    // EFFECTS: Clears text in field
    protected void clearTextField(JTextField field) {
        field.setText("");
    }
}
