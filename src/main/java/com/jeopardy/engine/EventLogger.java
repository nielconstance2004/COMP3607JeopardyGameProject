package com.jeopardy.engine;

import java.io.BufferedWriter; // for writing to files
import java.io.File; // for file representation
import java.io.FileWriter; // for writing character files
import java.time.ZonedDateTime; // for timestamping
import java.time.format.DateTimeFormatter; // for formatting timestamps

/**
 * Event logger for tracking game activities for process mining.
 * Implements Singleton pattern and logs events in CSV format.
 * 
 * @author Group 33
 * @version 1.0
 */
public class EventLogger { 
    private static EventLogger instance; // singleton instance
    private final File outFile; // output file for logs
    private boolean headerWritten = false; // flag to track if header is written

    /**
     * Private constructor for Singleton pattern.
     * 
     * @param outFile the file to write logs to
     * @throws Exception if file creation fails
     */
    private EventLogger(File outFile) throws Exception { // private constructor
        this.outFile = outFile; // assign output file
        if (!outFile.exists()) outFile.createNewFile(); // create file if it doesn't exist
    } // get singleton instance

    /**
     * Gets the singleton instance of EventLogger.
     * 
     * @param outFile the file to write logs to
     * @return the singleton EventLogger instance
     * @throws Exception if file creation fails
     */
    public static synchronized EventLogger getInstance(File outFile) throws Exception {
        if (instance == null) instance = new EventLogger(outFile); // create new instance if null
        return instance; // return singleton instance
    }

    /**
     * Logs an event with all relevant details.
     * 
     * @param caseId the unique case identifier for the game session
     * @param playerId the identifier of the player performing the action
     * @param activity the type of activity being logged
     * @param category the question category (if applicable)
     * @param questionValue the question point value (if applicable)
     * @param answerGiven the answer provided by the player (if applicable)
     * @param result the result of the activity (e.g., CORRECT, WRONG, OK)
     * @param scoreAfter the player's score after the activity
     */
    public synchronized void log(String caseId, String playerId, String activity, String category, Integer questionValue,
                                 String answerGiven, String result, int scoreAfter) { // log event
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile, true))) { // open file in append mode
            if (!headerWritten && outFile.length() == 0) { // write header if not written
                bw.write("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n"); // write CSV header
                headerWritten = true; // set header written flag
            } // get current timestamp
            String ts = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME); // format timestamp
            // prepare CSV line
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d\n", // CSV format
                    safe(caseId), safe(playerId), safe(activity), safe(ts), safe(category), // CSV fields
                    questionValue == null ? "" : questionValue.toString(), safe(answerGiven), safe(result), scoreAfter); // remaining fields
            bw.write(line); // write line to file
        } catch (Exception ex) { // handle exceptions
            System.err.println("Failed to write log: " + ex.getMessage()); // print error message
        } // end try-catch
    } // end log method

    /**
     * Sanitizes strings for CSV format by removing commas and newlines.
     * 
     * @param s the string to sanitize
     * @return the sanitized string safe for CSV
     */
    private String safe(String s) { // replace commas and newlines
        if (s == null) return ""; // handle nulls
        return s.replaceAll(",", " ").replaceAll("\n", " ").replaceAll("\r", " "); // sanitize string
    } // end safe method
}
