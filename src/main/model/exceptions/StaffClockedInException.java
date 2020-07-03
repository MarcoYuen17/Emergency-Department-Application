package model.exceptions;

/**
 * Exceptions for staff with conflicting clocked in state
 */

public class StaffClockedInException extends Exception {

    public StaffClockedInException(String msg) {
        super(msg);
    }
}
