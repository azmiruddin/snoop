package de.tuberlin.dima.dbt.exercises.logging;

/**
 * Enumeration of different log entry types.
 */
public enum LogRecordType {

    UPDATE, BEGIN_OF_TRANSACTION, END_OF_TRANSACTION, COMMIT,
    BEGIN_OF_CHECKPOINT, END_OF_CHECKPOINT

}
