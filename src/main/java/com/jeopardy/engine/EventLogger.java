package com.jeopardy.engine;

import java.io.BufferedWriter; // for writing to files
import java.io.File; // for file representation
import java.io.FileWriter; // for writing character files
import java.time.ZonedDateTime; // for timestamping
import java.time.format.DateTimeFormatter; // for formatting timestamps

public class EventLogger { 
    private static EventLogger instance; // singleton instance
    private final File outFile; // output file for logs
    private boolean headerWritten = false; // flag to track if header is written

    private EventLogger(File outFile) throws Exception { // private constructor
        this.outFile = outFile; // assign output file
        if (!outFile.exists()) outFile.createNewFile(); // create file if it doesn't exist
    } // get singleton instance

    // synchronized to ensure thread safety
    public static synchronized EventLogger getInstance(File outFile) throws Exception {
        if (instance == null) instance = new EventLogger(outFile); // create new instance if null
        return instance; // return singleton instance
    }

    // synchronized to ensure thread safety
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

    // sanitize strings for CSV
    private String safe(String s) { // replace commas and newlines
        if (s == null) return ""; // handle nulls
        return s.replaceAll(",", " ").replaceAll("\n", " ").replaceAll("\r", " "); // sanitize string
    } // end safe method
}
