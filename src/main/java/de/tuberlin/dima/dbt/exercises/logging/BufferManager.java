package de.tuberlin.dima.dbt.exercises.logging;

import java.util.Map;

/**
 * The Buffer Manager manages writes to disk.
 */
public interface BufferManager {

    /**
     * Write the value of an element to disk.
     *
     * @param pageId    The disk page that contains the element.
     * @param elementId The element identifier.
     * @param value     The new value.
     */
    void writeElement(int pageId, int elementId, String value);

    /**
     * Write the log sequence number of the BEGIN_OF_CHECKPOINT statement to disk.
     *
     * @param lsn The log sequence number of the BEGIN_OF_CHECKPOINT.
     */
    void writeBeginningOfCheckpoint(int lsn);

    /**
     * Write the transaction table at the beginning of a checkpoint to disk.
     * <p>
     * The transaction table maps transaction IDs to log sequence numbers.
     *
     * @param transactionTable The transaction table.
     */
    void writeTransactionTable(Map<Integer, Integer> transactionTable);

    /**
     * Write the dirty page table at the beginning of a checkpoint to disk.
     * <p>
     * The dirty page table maps page IDs to log sequence numbers.
     *
     * @param dirtyPageTable The dirty page table.
     */
    void writeDirtyPageTable(Map<Integer, Integer> dirtyPageTable);

    /**
     * Get the page log sequence number of a page.
     * <p>
     * The page log sequence number (page lsn) identifies the last log entry
     * containing information that has been flushed to disk.«
     *
     * @param pageId A page ID.
     * @return The page LSN of the page.
     */
    int getPageLsn(int pageId);

}
