package de.tuberlin.dima.dbt.exercises.logging;

/**
 * Exception that signals that an entry could not be added to the log.
 */
public class LogManagerException extends RuntimeException {

    public LogManagerException(String message) {
        super(message);
    }

}
