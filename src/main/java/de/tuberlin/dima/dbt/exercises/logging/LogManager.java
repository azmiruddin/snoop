package de.tuberlin.dima.dbt.exercises.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class LogManager {

    /**
     * Buffer Manager dependency.
     * <p>
     * The Log Manager needs to interact with the Buffer Manager during
     * checkpointing and recovery.
     */
    protected BufferManager bufferManager;
    private static StringBuffer output = new StringBuffer();
    private static final int LOG_BUFFER_SIZE = 4;
    private static List<List<LogRecord>> logFile;
    private static List<LogRecord> transactionTable;
    

    /**
     * The current log sequence number.
     */
    protected int currentLsn = 0;

    /**
     * The list of log records.
     */
    protected List<LogRecord> logRecords;

    /**
     * The active transaction table.
     * <p>
     * The transaction table maps transaction IDs to log sequence numbers.
     */
    protected Map<Integer, Integer> transactions;

    /**
     * The dirty page table.
     * <p>
     * The dirty page table maps page IDs to log sequence numbers.
     */
    protected Map<Integer, Integer> dirtyPages;

    /////
    ///// Functions that append log records
    /////

    /**
     * Add a BEGIN_OF_TRANSACTION entry to the log.
     *
     * @param transactionId The transaction ID.
     * @throws A LogManagerException if a transaction with the ID already exists.
     */
    public void beginTransaction(int transactionId) {
    	
        String operation = null;
		// TODO Append BEGIN_OF_TRANSACTION entry to log
    	writeToOutput(operation);

		// get transaction id from the operation
    	transactionId = Character.getNumericValue(getParameter(
				"transaction id", operation));

		// write to log buffer
		LogRecord log = new LogRecord(transactionId, transactionId);
		log.setTransactionId(transactionId);		
		log.setType(LogRecordType.BEGIN_OF_TRANSACTION);
		
		logRecords.add(log);

		// if log buffer is full
		if (logRecords.size() == LOG_BUFFER_SIZE) {
			// create a clone of log buffer
			List<LogRecord> tempLogBuffer = new ArrayList<LogRecord>();
			for (LogRecord tempLog : logRecords) {
				tempLogBuffer.add(tempLog);
			}
			// write buffer to disk
			logFile.add(tempLogBuffer);
			// empty buffer
			logRecords.clear();
		}

		// add to transaction table
		LogRecord transaction = new LogRecord(transactionId, transactionId);
		transaction.setTransactionId(transactionId);		
		writeToOutput("Transaction table:" + transactionTable.toString());
    	
    }

    /**
     * Add an UPDATE entry to the log.
     *
     * @param transactionId The transaction ID.
     * @param pageId        The page ID.
     * @param elementId     The element ID.
     * @param oldValue      The old value.
     * @param newValue      The new value.
     * @throws A LogManagerException if the transaction with the ID does not exist.
     */
    public void update(int transactionId, int pageId, int elementId,
                       String oldValue, String newValue) {
        // TODO Append UPDATE entry to log
    }

    /**
     * Add a COMMIT entry to the log.
     *
     * @param transactionId The transaction ID.
     * @throws A LogManagerException if the transaction with the ID does not exist.
     */
    public void commit(int transactionId) {
        // TODO Append COMMIT entry to log
    }

    /**
     * Add a END_OF_TRANSACTION entry to the log.
     *
     * @param transactionId The transaction ID.
     * @throws A LogManagerException if the transaction with the ID does not exist.
     */
    public void endTransaction(int transactionId) {
        // TODO Append END_OF_TRANSACTION entry to log
    }

    /////
    ///// Functions that implement checkpointing
    /////

    /**
     * Add a BEGIN_OF_CHECKPOINT entry to the log.
     */
    public void beginCheckpoint() {
        // TODO Append BEGIN_OF_CHECKPOINT entry to log and start checkpointing
    }

    /**
     * Add a END_OF_CHECKPOINT entry to the log and write checkpointing data to disk.
     *
     * Should write:
     * <ul>
     *     <li>The LSN of the BEGIN_OF_CHECKPOINT entry.</li>
     *     <li>The transaction table.</li>
     *     <li>The dirty page table.</li>
     * </ul>
     */
    public void endCheckpoint() {
        // TODO Append END_OF_CHECKPOINT entry to log and finish checkpointing
    }

    /////
    ///// Functions that implement recovery
    /////

    /**
     * Analyse an undo/redo log starting from a specific LSN and update the
     * transaction and dirty page tables.
     *
     * The transaction and dirty page tables, which are passed to this function,
     * should be updated in-place.
     *
     * @param logRecords        The complete undo/redo log.
     * @param checkPointedLsn   The LSN at which the analysis path should start.
     * @param transactions      The transaction table.
     * @param dirtyPages        The dirty page table.
     */
    public void analysisPass(List<LogRecord> logRecords, int checkPointedLsn,
                             Map<Integer, Integer> transactions,
                             Map<Integer, Integer> dirtyPages) {
        // TODO Implement analysis pass
    }

    /**
     * Perform the redo pass for the given log records.
     *
     * An UPDATE statement that has to be redone should call
     * bufferManager.writeElement with the required page ID, element ID, and
     * the value that should be writen.
     *
     * @param logRecords    The complete undo/redo log.
     * @param dirtyPages    The dirty page table constructed during the analysis
     *                      path.
     */
    public void redoPass(List<LogRecord> logRecords,
                         Map<Integer, Integer> dirtyPages) {
        // TODO Implement undo pass
    }

    /**
     * Perform the undo pass for the given log entries.
     *
     * An UPDATE statement that has to be undone should call
     * bufferManager.writeElement with the required page ID, element ID, and
     * the value that should be writen.
     *
     * @param logRecords    The complete undo/redo log.
     * @param transactions  The transaction table constructed during the
     *                      analysis path.
     */
    public void undoPass(List<LogRecord> logRecords,
                         Map<Integer, Integer> transactions) {
        // TODO Implement redo pass
    }

    /////
    ///// Constructor / Getter / Setter (DO NOT CHANGE THIS CODE)
    /////

    /**
     * Constructs an empty undo/redo log.
     *
     * DO NOT CHANGE THIS CODE.
     *
     * @param bufferManager Dependency on a Buffer Manager.
     */
    public LogManager(BufferManager bufferManager) {
        this(bufferManager, 0, new ArrayList<>(), new HashMap<>(),
             new HashMap<>());
    }

    /**
     * Constructs an undo/redo log based on existing log records and transaction
     * and dirty page table.
     *
     * DO NOT CHANGE THIS CODE.
     *
     * @param bufferManager Dependency on a Buffer Manager.
     * @param currentLsn    The current LSN. New entries will be appended to the
     *                      log starting with this LSN.
     * @param logRecords    A list of existing log records.
     * @param transactions  A transaction table.
     * @param dirtyPages    A dirty page table.
     */
    public LogManager(BufferManager bufferManager, int currentLsn,
                      List<LogRecord> logRecords,
                      Map<Integer, Integer> transactions,
                      Map<Integer, Integer> dirtyPages) {
        this.bufferManager = bufferManager;
        this.currentLsn = currentLsn;
        this.logRecords = logRecords;
        this.transactions = transactions;
        this.dirtyPages = dirtyPages;
    }

    public List<LogRecord> getLogRecords() {
        return logRecords;
    }
    
    //extend helper
    private static void writeToOutput(String string) {
		System.out.println(string);
		output.append(string + "\n");
	}
    
    private static Character getParameter(String param, String operation) {
		Character parameter = null;
		if (param.equals("transaction id")) {
			parameter = operation.charAt(1);
		} else if (param.equals("item")) {
			parameter = operation.charAt(3);
		} else if (param.equals("bfim")) {
			parameter = operation.charAt(5);
		} else if (param.equals("afim")) {
			parameter = operation.charAt(7);
		}
		return parameter;
	}
    

}
