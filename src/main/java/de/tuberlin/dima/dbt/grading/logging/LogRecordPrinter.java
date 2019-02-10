package de.tuberlin.dima.dbt.grading.logging;

import de.tuberlin.dima.dbt.exercises.logging.LogRecord;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogRecordPrinter {

    private static final String[] headers = {"LSN", "TYPE", "TID", "PSN", "PID",
                                             "EID", "OLD", "NEW"};

    private final int[] widths = new int[headers.length];

    private PrintStream out;
    private LogRecord[] records;

    private LogRecordPrinter(PrintStream out, LogRecord... records) {
        this.out = out;
        this.records = records;
    }

    public static void printLogRecords(PrintStream out,
                                       List<LogRecord> records) {
        printLogRecords(out, records.toArray(new LogRecord[] { }));

    }

    public static void printLogRecords(PrintStream out, LogRecord... records) {
        new LogRecordPrinter(out, records).print();
    }

    public static String toString(List<LogRecord> records) {
       return toString(records.toArray(new LogRecord[] { }));
    }

    public static String toString(LogRecord... records) {
        OutputStream os = new ByteArrayOutputStream();
        printLogRecords(new PrintStream(os), records);
        return os.toString();
    }

    private void print() {
        computeMinimalWidths();
        computeWidths();
        printHeader();
        printValues();
    }

    private void computeMinimalWidths() {
        for (int i = 0; i < headers.length; ++i) {
            widths[i] = headers[i].length();
        }
    }

    private void computeWidths() {
        for (LogRecord record : records) {
            adjustWidth(0, record.getLogSequenceNumber());
            adjustWidth(1, record.getType());
            adjustWidth(2, record.getTransactionId());
            adjustWidth(3, record.getPreviousSequenceNumber());
            adjustWidth(4, record.getPageId());
            adjustWidth(5, record.getElementId());
            adjustWidth(6, record.getOldValue());
            adjustWidth(7, record.getNewValue());
        }
    }

    private void printHeader() {
        String headerLine = IntStream.range(0, headers.length).mapToObj(
                i -> cell(headers[i], widths[i])).collect(Collectors.joining("|"));
        String separator = IntStream.range(0, headers.length).mapToObj(
                i -> line(widths[i])).collect(Collectors.joining("+"));
        out.println(headerLine);
        out.println(separator);
    }

    private void printValues() {
        for (LogRecord record : records) {
            String line = String.join("|", new String[]{
                    cell(record.getLogSequenceNumber(), widths[0]),
                    cell(record.getType(), widths[1]),
                    cell(record.getTransactionId(), widths[2]),
                    cell(record.getPreviousSequenceNumber(), widths[3]),
                    cell(record.getPageId(), widths[4]),
                    cell(record.getElementId(), widths[5]),
                    cell(record.getOldValue(), widths[6]),
                    cell(record.getNewValue(), widths[7])});
            out.println(line);
        }
    }

    private void adjustWidth(int index, Object value) {
        int width = safeValueOf(value).length();
        if (widths[index] < width) {
            widths[index] = width;
        }
    }

    private static String safeValueOf(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private static String cell(Object value, int width) {
        String str = safeValueOf(value);
        StringBuffer sb = new StringBuffer();
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        sb.append(String.join("", Collections
                .nCopies(width - str.length(), " ")));
        return sb.toString();
    }

    private static String line(int width) {
        return String.join("", Collections.nCopies(width + 2, "-"));
    }

}
