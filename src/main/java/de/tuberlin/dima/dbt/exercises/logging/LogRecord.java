package de.tuberlin.dima.dbt.exercises.logging;

import static de.tuberlin.dima.dbt.exercises.logging.LogRecordType.BEGIN_OF_TRANSACTION;
import static java.util.Arrays.asList;



/**
 * Represents an entry in an undo/redo log.
 *
 * Every log record consists at least of a log sequence number (LSN) and a
 * log record type {@see LogRecordType}. An entry can optionally contain a
 * transaction ID and a pointer to the previous log entry of the same
 * transaction. UPDATE entries also contain the page ID, the element ID, and
 * the old and new value.
 *
 * This class also provides convenience constructors for different log record
 * type.
 */
public class LogRecord {

    private int logSequenceNumber;
    private LogRecordType type;
    private Integer transactionId;
    private Integer previousSequenceNumber;
    private Integer pageId;
    private Integer elementId;
    private String oldValue;
    private String newValue;    
    

    public LogRecord(int logSequenceNumber, LogRecordType type) {
        this(logSequenceNumber, type, null, null);
    }

    public LogRecord(int logSequenceNumber, int transactionId) {
        this(logSequenceNumber, BEGIN_OF_TRANSACTION, transactionId, null);
    }

    public LogRecord(int logSequenceNumber, LogRecordType type,
                     Integer transactionId, Integer previousSequenceNumber) {
        this(logSequenceNumber, type, transactionId, previousSequenceNumber,
             null, null, null, null);
    }

    public LogRecord(int logSequenceNumber, int transactionId,
                     Integer previousSequenceNumber, int pageId,
                     Integer elementId, String oldValue, String newValue) {
        this(logSequenceNumber, LogRecordType.UPDATE, transactionId,
             previousSequenceNumber, pageId, elementId, oldValue, newValue);
    }

    private LogRecord(int logSequenceNumber, LogRecordType type,
                      Integer transactionId, Integer previousSequenceNumber,
                      Integer pageId, Integer elementId, String oldValue,
                      String newValue) {
        this.setLogSequenceNumber(logSequenceNumber);
        this.setTransactionId(transactionId);
        this.setPreviousSequenceNumber(previousSequenceNumber);
        this.setType(type);
        this.setPageId(pageId);
        this.setElementId(elementId);
        this.setOldValue(oldValue);
        this.setNewValue(newValue);
    }

    public String toString() {
        return String.join(", ", asList(
                "LSN = " + getLogSequenceNumber(),
                "TYPE = " + getType(),
                "TID = " + getTransactionId(),
                "PSN = " + getPreviousSequenceNumber(),
                "PID = " + getPageId(),
                "EID = " + getElementId(),
                "OLD = " + getOldValue(),
                "NEW = " + getNewValue()
        ));
    }

    ///// Getter / Setters

    public int getLogSequenceNumber() {
        return logSequenceNumber;
    }

    public void setLogSequenceNumber(int logSequenceNumber) {
        this.logSequenceNumber = logSequenceNumber;
    }

    public LogRecordType getType() {
        return type;
    }

    public void setType(LogRecordType type) {
        this.type = type;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getPreviousSequenceNumber() {
        return previousSequenceNumber;
    }

    public void setPreviousSequenceNumber(Integer previousSequenceNumber) {
        this.previousSequenceNumber = previousSequenceNumber;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

}
