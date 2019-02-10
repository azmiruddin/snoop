package de.tuberlin.dima.dbt.grading.logging;

import de.tuberlin.dima.dbt.exercises.logging.LogRecord;
import de.tuberlin.dima.dbt.exercises.logging.LogRecordType;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Convenience methods to construct log records and Java maps.
 */
public class LogManagerTestHelper {

    /////
    ///// Record factory methods
    /////

    public static LogRecord record(int lsn, LogRecordType type) {
        return new LogRecord(lsn, type);
    }

    public static LogRecord record(int lsn, LogRecordType type,
                                   Integer transactionId) {
        return new LogRecord(lsn, type, transactionId, null);
    }

    public static LogRecord record(int lsn, LogRecordType type,
                                   Integer transactionId, int psn) {
        return new LogRecord(lsn, type, transactionId, psn);
    }

    public static LogRecord record(int lsn, int transactionId, int psn,
                                   int pageId, int elementId, String oldValue,
                                   String newValue) {
        return new LogRecord(lsn, transactionId, psn, pageId, elementId,
                             oldValue, newValue);
    }

    /////
    ///// Map factory methods
    /////

    @SafeVarargs
    public static final <K, V> Map<K, V> ofEntries(
            Map.Entry<? extends K, ? extends V>... entries) {
        Map<K, V> map = new HashMap<>();
        for (Map.Entry<? extends K, ? extends V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static <K, V> Map.Entry<? extends K, ? extends V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }

}
