package de.tuberlin.dima.dbt.exercises.logging;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.tuberlin.dima.dbt.exercises.logging.LogRecordType.*;
import static de.tuberlin.dima.dbt.grading.logging.LogManagerTestHelper.*;
import static de.tuberlin.dima.dbt.grading.logging.LogRecordMatcher.hasRecords;
import static de.tuberlin.dima.dbt.grading.logging.LogRecordPrinter.printLogRecords;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.mockito.Mockito.*;

public class LogManagerTest {

    private LogManager logManager;
    private BufferManager bufferManager;

    @Before
    public void setup() {
        bufferManager = mock(BufferManager.class);
        logManager = new LogManager(bufferManager);
    }

    @Test
    public void beginTransaction() {
        // given
        logManager.beginTransaction(1);
        // when
        logManager.beginTransaction(2);
        // then
        verifyLogRecords(record(1, BEGIN_OF_TRANSACTION, 2));
    }

    @Test(expected = LogManagerException.class)
    public void beginTransactionErrorIfAlreadyUsed() {
        // when
        logManager.beginTransaction(1);
        // when
        logManager.beginTransaction(1);
    }

    @Test
    public void update() {
        // given
        logManager.beginTransaction(1);
        // when
        logManager.update(1, 1, 1, "a", "A");
        logManager.update(1, 1, 2, "b", "B");
        // then
        verifyLogRecords(record(1, 1, 0, 1, 1, "a", "A"),
                         record(2, 1, 1, 1, 2, "b", "B"));
    }

    @Test(expected = LogManagerException.class)
    public void updateRequiresPreviousBeginTransaction() {
        // when
        logManager.update(1, 1, 1, "a", "A");
    }

    @Test
    public void commit() {
        // given
        logManager.beginTransaction(1);
        logManager.beginTransaction(2);
        // when
        logManager.commit(2);
        logManager.commit(1);
        // then
        verifyLogRecords(record(2, COMMIT, 2, 1),
                         record(3, COMMIT, 1, 0));
    }

    @Test(expected = LogManagerException.class)
    public void commitRequiresPreviousTransaction() {
        // when
        logManager.commit(1);
    }

    @Test
    public void endOfTransaction() {
        // given
        logManager.beginTransaction(1);
        logManager.beginTransaction(2);
        // when
        logManager.endTransaction(2);
        logManager.endTransaction(1);
        // then
        verifyLogRecords(record(2, END_OF_TRANSACTION, 2, 1),
                         record(3, END_OF_TRANSACTION, 1, 0));
    }

    @Test
    public void beginCheckpoint() {
        // given
        logManager.beginTransaction(1);
        logManager.beginCheckpoint();
        // then
        verifyLogRecords(record(1, BEGIN_OF_CHECKPOINT));
    }

    @Test
    public void endCheckpoint() {
        // given
        logManager.beginTransaction(1);
        logManager.endCheckpoint();
        // then
        verifyLogRecords(record(1, END_OF_CHECKPOINT));
    }

    @Test
    public void checkpointWriteLsn() {
        // given
        logManager.beginTransaction(1);
        // when
        logManager.beginCheckpoint();
        logManager.endCheckpoint();
        // then
        verify(bufferManager).writeBeginningOfCheckpoint(1);
    }

    @Test
    public void checkpointWriteTransactionTable() {
        // given
        logManager.beginTransaction(1);
        logManager.beginTransaction(2);
        // when
        logManager.beginCheckpoint();
        logManager.endCheckpoint();
        // then
        verify(bufferManager).writeTransactionTable(
                ofEntries(entry(1, 0), entry(2, 1)));
    }

    @Test
    public void checkpointWriteDirtyPageTable() {
        // given
        logManager.beginTransaction(1);
        logManager.update(1, 1, 1, "a", "A");
        logManager.update(1, 2, 2, "b", "B");
        logManager.update(1, 1, 3, "c", "C");
        logManager.update(1, 2, 4, "d", "D");
        // when
        logManager.beginCheckpoint();
        logManager.endCheckpoint();
        // then
        verify(bufferManager).writeDirtyPageTable(
                ofEntries(entry(1, 1), entry(2, 2)));
    }

    @Test
    public void analysisPass() {
        // given
        List<LogRecord> logRecords = asList(
                record(0, BEGIN_OF_TRANSACTION, 1),
                record(1, BEGIN_OF_CHECKPOINT),
                record(2, 1, 0, 1, 1, "a", "A"),
                record(3, END_OF_CHECKPOINT),
                record(3, 1, 2, 1, 2, "b", "B"));
        Map<Integer, Integer> transactions = ofEntries(entry(1, 0));
        // when
        logManager.analysisPass(logRecords, 1, transactions, new HashMap<>());
        // then
        assertThat(transactions, hasEntry(1, 4));
    }

    @Test
    public void redoPageInDirtyPageTable() {
        // given
        List<LogRecord> logRecords = asList(record(0, BEGIN_OF_CHECKPOINT),
                                            record(1, BEGIN_OF_TRANSACTION, 1),
                                            record(2, 1, 1, 1, 1, "a", "A"),
                                            record(3, END_OF_CHECKPOINT));
        Map<Integer, Integer> dirtyPages = ofEntries(entry(1, 2));
        // when
        logManager.redoPass(logRecords, dirtyPages);
        // then
        verify(bufferManager).writeElement(1, 1, "A");
    }

    @Test
    public void redoIgnorePageNotInDirtyPageTable() {
        // given
        List<LogRecord> logRecords = asList(record(0, BEGIN_OF_CHECKPOINT),
                                            record(1, BEGIN_OF_TRANSACTION, 1),
                                            record(2, 1, 1, 1, 1, "a", "A"),
                                            record(3, END_OF_CHECKPOINT));
        Map<Integer, Integer> dirtyPages = new HashMap<>();
        // when
        logManager.redoPass(logRecords, dirtyPages);
        // then
        verifyNoMoreInteractions(bufferManager);
    }

    @Test
    public void undoUpdatesInTransactions() {
        // given
        List<LogRecord> logRecords = asList(
                record(0, BEGIN_OF_TRANSACTION, 1),
                record(1, 1, 0, 1, 1, "a", "A"),
                record(2, END_OF_TRANSACTION, 1, 1));
        Map<Integer, Integer> transactions = ofEntries(entry(1, 2));
        // when
        logManager.undoPass(logRecords, transactions);
        // then
        verify(bufferManager).writeElement(1, 1, "a");
    }

    private void verifyLogRecords(LogRecord... expected) {
        try {
            assertThat(logManager.getLogRecords(), hasRecords(expected));
        } catch (AssertionError e) {
            System.out.println("I expected a log containing at least the following log entries:");
            printLogRecords(System.out, expected);
            System.out.println("I found the following log:");
            printLogRecords(System.out, logManager.getLogRecords());
            throw e;
        }
    }

}
