package model.exceptions;

/**
 * Exceptions for patients with conflicting checked in state
 */

public class PatientCheckedInException extends Exception {

    public PatientCheckedInException(String msg) {
        super(msg);
    }
}
