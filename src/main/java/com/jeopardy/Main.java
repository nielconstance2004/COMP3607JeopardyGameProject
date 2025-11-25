package com.jeopardy; // package declaration

import com.jeopardy.engine.EventLogger; // import EventLogger class
import com.jeopardy.engine.GameEngine; // import GameEngine class
import com.jeopardy.model.Player; // import Player model
import com.jeopardy.report.ReportGenerator; // import ReportGenerator class

import java.io.File; // import File class
import java.util.Scanner;

// Main class to run the Jeopardy game
public class Main {
    // main method - entry point of the application
    public static void main(String[] args) throws Exception { // start of main method
        System.out.println("Jeopardy Game - Console version"); // print welcome message
        String base = System.getProperty("user.dir"); // get current working directory
        File logFile = new File(base, "game_event_log.csv"); // create log file in working directory
        EventLogger logger = EventLogger.getInstance(logFile); // get singleton EventLogger instance

    // get GameEngine singleton instance and set logger
    GameEngine engine = GameEngine.getInstance();
    engine.setLogger(logger);

    // Validate and log start
    if (logger != null) logger.log(engine.getCaseId(), "SYSTEM", "LAUNCH", "", null, "Main started", "OK", 0);

        // Get question data file from user
        try (java.util.Scanner sc = new java.util.Scanner(System.in)) { // try-with-resources to ensure Scanner is closed
            System.out.print("Enter question data file path (CSV/JSON/XML): "); // prompt user for file path
            String path = sc.nextLine().trim(); // read and trim user input
        File data = new File(path); // create File object from user input
        if (!data.exists()) { System.out.println("File not found: " + path); return; } // validate file existence
        engine.loadQuestions(data); // load questions into game engine

        // Get player information
        System.out.print("Number of players (1-4): "); // prompt user for number of players
            int n = Integer.parseInt(sc.nextLine().trim()); // read and parse number of players
            if (n < 1 || n > 4) { System.out.println("Invalid player count. Must be 1-4."); return; } // validate player count
            if (logger != null) logger.log(engine.getCaseId(), "SYSTEM", "SELECT_PLAYER_COUNT", "", null, String.valueOf(n), "OK", 0); // log player count selection
            for (int i=1;i<=n;i++) { // loop to get each player's name
                System.out.print("Player " + i + " name: "); // prompt for player name
                String name = sc.nextLine().trim(); // read and trim player name
                // enforce project requirement of max 4 players
                engine.addPlayerWithLimit(new Player("P"+i, name)); // add player to game engine
                if (logger != null) logger.log(engine.getCaseId(), "SYSTEM", "ENTER_PLAYER_NAME", "", null, name, "OK", 0); // log player name entry
            } // end for loop

            engine.runConsoleGame(); // run the console game
        } // end try-with-resources

        // Generate report
        ReportGenerator rg = new ReportGenerator(); // create ReportGenerator instance
        File report = new File(base, "sample_game_report.txt"); // define report file path
        // Use overloaded report generator that also logs events (Main also logs for clarity)
        if (logger != null) logger.log(engine.getCaseId(), "SYSTEM", "GENERATE_REPORT", "", null, report.getName(), "START", 0); // log report generation start
        rg.generateTxtReport(report, engine.getPlayers(), engine.getRecords(), logger); // generate report with logging
        if (logger != null) logger.log(engine.getCaseId(), "SYSTEM", "GENERATE_REPORT", "", null, report.getName(), "OK", 0); // log report generation success
        if (logger != null) logger.log(engine.getCaseId(), "SYSTEM", "GENERATE_EVENT_LOG", "", null, logFile.getName(), "OK", 0); // log event log generation success

        // Notify user of report and log locations
        System.out.println("Report written to: " + report.getAbsolutePath());
        System.out.println("Log written to: " + logFile.getAbsolutePath());

        // Log exit
        if (logger != null) logger.log(engine.getCaseId(), "SYSTEM", "EXIT_GAME", "", null, "User exit", "OK", engine.getPlayers().stream().mapToInt(p->p.getScore()).sum());
    }
}