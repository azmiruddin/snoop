package de.tuberlin.dima.dbt.grading.logging;

import de.tuberlin.dima.dbt.exercises.logging.LogRecord;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

import static java.util.Arrays.asList;

public class LogRecordMatcher extends TypeSafeMatcher<List<LogRecord>> {

    private List<LogRecord> expected;

    public LogRecordMatcher(List<LogRecord> expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(List<LogRecord> logRecords) {
        if (expected == null && logRecords == null) {
            return true;
        }
        if (expected == null || logRecords == null) {
            return false;
        }
        loop:
        for (LogRecord record1 : expected) {
            for (LogRecord record2 : logRecords) {
                if (equals(record1, record2)) {
                    continue loop;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValueList("", ",", "", expected);
    }

    private boolean safeEquals(Object thisValue, Object otherValue) {
        return thisValue == null && otherValue == null ||
                thisValue != null && thisValue.equals(otherValue);
    }

    private boolean equals(LogRecord record1, LogRecord record2) {
        return record1.getLogSequenceNumber() ==
                record2.getLogSequenceNumber() &&
                record1.getType() == record2.getType() &&
                safeEquals(record1.getTransactionId(),
                           record2.getTransactionId()) &&
                safeEquals(record1.getPreviousSequenceNumber(),
                           record2.getPreviousSequenceNumber()) &&
                safeEquals(record1.getPageId(), record2.getPageId()) &&
                safeEquals(record1.getElementId(), record2.getElementId()) &&
                safeEquals(record1.getOldValue(), record2.getOldValue()) &&
                safeEquals(record1.getNewValue(), record2.getNewValue());
    }

    public static LogRecordMatcher hasRecords(List<LogRecord> expected) {
        return new LogRecordMatcher(expected);
    }

    public static LogRecordMatcher hasRecords(LogRecord... expected) {
        return new LogRecordMatcher(asList(expected));
    }
}
