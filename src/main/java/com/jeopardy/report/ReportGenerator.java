package com.jeopardy.report; // package declaration

import com.jeopardy.engine.EventLogger; // import EventLogger for logging events
import com.jeopardy.model.TurnRecord; // import TurnRecord model
import com.jeopardy.model.Player; // import Player model

import java.io.BufferedWriter; // import BufferedWriter for file writing
import java.io.File; // import File class
import java.io.FileWriter; // import FileWriter for file writing
import java.util.List; // import List interface

// ReportGenerator class to generate text reports
public class ReportGenerator {
    public void generateTxtReport(File outFile, List<Player> players, List<TurnRecord> records) throws Exception { // method to generate text report
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) { // try-with-resources to ensure BufferedWriter is closed
            bw.write("Multi-Player Jeopardy - Summary Report\n"); // write report title
            bw.write("==============================\n\n"); // write separator
            bw.write("Final Scores:\n"); // write final scores header
            for (Player p : players) bw.write(String.format("%s: %d\n", p.getName(), p.getScore())); // write each player's final score
            bw.write("\nTurn-by-turn:\n"); // write turn-by-turn header
            for (TurnRecord t : records) { // iterate through each turn record
                bw.write(String.format("Player: %s (%s) | Category: %s | Value: %d\n", t.getPlayerName(), t.getPlayerId(), t.getCategory(), t.getValue())); // write turn details
                bw.write(String.format("Question: %s\n", t.getQuestionText())); // write question text
                // write multiple choice options
                bw.write("Options: "); // write options header
                StringBuilder options = new StringBuilder(); // build options string
                if (t.getChoiceA() != null && !t.getChoiceA().isEmpty()) { // check choice A
                    options.append("(A) ").append(t.getChoiceA()); // add choice A
                    if (t.getChoiceB() != null || t.getChoiceC() != null || t.getChoiceD() != null) options.append(" | "); // add separator if more options
                } // end choice A
                if (t.getChoiceB() != null && !t.getChoiceB().isEmpty()) { // check choice B
                    options.append("(B) ").append(t.getChoiceB()); // add choice B
                    if (t.getChoiceC() != null || t.getChoiceD() != null) options.append(" | "); // add separator if more options
                } // end choice B
                if (t.getChoiceC() != null && !t.getChoiceC().isEmpty()) { // check choice C
                    options.append("(C) ").append(t.getChoiceC()); // add choice C
                    if (t.getChoiceD() != null) options.append(" | "); // add separator if more options
                } // end choice C
                if (t.getChoiceD() != null && !t.getChoiceD().isEmpty()) { // check choice D
                    options.append("(D) ").append(t.getChoiceD()); // add choice D
                } // end choice D
                bw.write(options.toString() + "\n"); // write options
                bw.write(String.format("Answer Given: %s | Correct: %s | Points Earned: %d | Running Total: %d\n\n", // write answer details
                        t.getAnswerGiven(), t.isCorrect() ? "YES" : "NO", t.getPointsEarned(), t.getRunningTotal())); // write answer details
            } // end for
        } // end try-with-resources
    } // end generateTxtReport method

    /**
     * Overloaded report generator that logs report generation events via EventLogger when provided.
     */
    public void generateTxtReport(File outFile, List<Player> players, List<TurnRecord> records, EventLogger logger) throws Exception { // overloaded method to generate text report with logging
        if (logger != null) logger.log("", "SYSTEM", "GENERATE_REPORT", "", null, outFile.getName(), "START", 0); // log report generation start
        generateTxtReport(outFile, players, records); // call original report generation method
        if (logger != null) logger.log("", "SYSTEM", "GENERATE_REPORT", "", null, outFile.getName(), "OK", 0); // log report generation success
    }
}
