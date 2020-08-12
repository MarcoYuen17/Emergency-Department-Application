package deprecated.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * A writer that can write patient, nurse, and other staff data to file
 */

public class Writer {

    // From https://github.students.cs.ubc.ca/CPSC210/TellerApp

    private PrintWriter printWriter;

    // EFFECTS: Constructs a writer that writes data to file
    public Writer(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: Writes saveable to file
    public void write(Saveable saveable) {
        saveable.save(printWriter);
        printWriter.println();
    }

    // MODIFIES: this
    // EFFECTS: Close print writer
    public void close() {
        printWriter.close();
    }
}