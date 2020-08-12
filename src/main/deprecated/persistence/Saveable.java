package deprecated.persistence;

import java.io.PrintWriter;

/**
 * Represents a saveable object
 */

public interface Saveable {

    // From https://github.students.cs.ubc.ca/CPSC210/TellerApp

    // MODIFIES: printWriter
    // EFFECTS: Writes saveable to printWriter
    void save(PrintWriter printWriter);
}
