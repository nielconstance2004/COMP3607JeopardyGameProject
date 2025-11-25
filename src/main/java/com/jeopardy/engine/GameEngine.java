package com.jeopardy.engine;

import com.jeopardy.loaders.QuestionLoader; // interface for loading questions
import com.jeopardy.loaders.QuestionLoaderFactory; // factory for getting appropriate question loader
import com.jeopardy.model.Player; // player model
import com.jeopardy.model.Question; // question model
import com.jeopardy.model.TurnRecord; // record of a turn
import com.jeopardy.scoring.ScoringStrategy; // interface for scoring strategy
import com.jeopardy.scoring.SimpleScoringStrategy; // simple scoring strategy implementation

import java.io.File; // for file representation
import java.time.ZonedDateTime; // for timestamping
import java.util.*; // for collections and utilities
import java.util.stream.Collectors; // for stream operations

/**
 * GameEngine manages gameplay. Singleton for simplicity (demonstrates Singleton pattern).
 */
public class GameEngine {
    private static GameEngine instance; // singleton instance

    private final List<Player> players = new ArrayList<>(); // list of players
    private final Map<String, Map<Integer, Question>> board = new LinkedHashMap<>(); // game board: category -> (value -> question)
    private final List<TurnRecord> records = new ArrayList<>(); // list of turn records
    private ScoringStrategy scoringStrategy = new SimpleScoringStrategy(true); // scoring strategy
    private EventLogger logger; // event logger
    private String caseId = UUID.randomUUID().toString(); // unique case ID
    
    private GameEngine() {} // private constructor for singleton

    // get singleton instance
    public static synchronized GameEngine getInstance() { // synchronized to ensure thread safety
        if (instance == null) instance = new GameEngine(); // create new instance if null
        return instance; // return singleton instance
    } // end getInstance method

    // set event logger
    public void setLogger(EventLogger logger) { this.logger = logger; } // end setLogger method
    public void setScoringStrategy(ScoringStrategy strategy) { this.scoringStrategy = strategy; } // end setScoringStrategy method

    // load questions from file
    public void loadQuestions(File file) throws Exception { // load questions from specified file
        QuestionLoader loader = QuestionLoaderFactory.getLoader(file); // get appropriate loader for file type
        if (loader == null) throw new IllegalArgumentException("Unsupported file type: " + file.getName()); // throw exception if no loader found
        List<Question> list = loader.load(file); // load questions using the loader
        // populate board by category and value
        board.clear(); // clear existing board
        for (Question q : list) { // iterate over loaded questions
            board.computeIfAbsent(q.getCategory(), k -> new TreeMap<>()).put(q.getValue(), q); // add question to board
        } // end for
        if (logger != null) logger.log(caseId, "SYSTEM", "LOAD_QUESTIONS", "", null, file.getName(), "OK", 0); // log question loading
    } // end loadQuestions method

    // add player without limit
    public void addPlayer(Player p) { players.add(p); if (logger != null) logger.log(caseId, p.getId(), "PLAYER_JOIN", "", null, p.getName(), "OK", p.getScore()); } // end addPlayer method

    /**
     * Adds a player but enforces the project requirement of maximum 4 players.
     * Throws IllegalStateException if trying to add more than 4 players.
     */
    public void addPlayerWithLimit(Player p) { // add player with max limit
        if (p == null) throw new IllegalArgumentException("Player cannot be null"); // validate player
        if (players.size() >= 4) { // check max players
            if (logger != null) logger.log(caseId, p.getId(), "PLAYER_JOIN_ATTEMPT", "", null, p.getName(), "FAILED_MAX_PLAYERS", players.stream().mapToInt(Player::getScore).sum()); // log failed attempt due to max players
            throw new IllegalStateException("Cannot add more than 4 players"); // throw exception if limit exceeded
        } // end if
        players.add(p); // add player to list
        if (logger != null) logger.log(caseId, p.getId(), "PLAYER_JOIN", "", null, p.getName(), "OK", p.getScore()); // log successful player addition
    } // end addPlayerWithLimit method

    // get list of categories
    public List<String> categories() { return new ArrayList<>(board.keySet()); } // end categories method

    // get available values for a category
    public List<Integer> valuesForCategory(String cat) { // get available values for a category
        if (!board.containsKey(cat)) return Collections.emptyList(); // return empty list if category not found
        return new ArrayList<>(board.get(cat).keySet()); // return list of values for the category
    } // end valuesForCategory method

    // check if all questions have been answered
    public boolean allAnswered() { // check if all questions have been answered
        for (Map<Integer, Question> m : board.values()) for (Question q : m.values()) if (!q.isAsked()) return false; // if any question not asked,
        return true; // all questions have been asked
    } // end allAnswered method

    // run the game in console mode
    public void runConsoleGame() { // run the game in console mode
        try (Scanner sc = new Scanner(System.in)) { // scanner for user input
            if (players.isEmpty()) { // check if players are added
                System.out.println("No players added. Add players first."); // notify no players
                return; // exit if no players
            } // end if
            // Log start of game
            if (logger != null) logger.log(caseId, "SYSTEM", "START_GAME", "", null, "", "OK", players.stream().mapToInt(Player::getScore).sum()); // log game start
            int current = 0; // index of current player
            while (!allAnswered()) { // continue until all questions answered
                Player p = players.get(current); // get current player
                System.out.println("\nPlayer: " + p.getName() + " (Score: " + p.getScore() + ")"); // display current player and score
                System.out.println("Categories:"); // display categories
                int i=1; // index for category numbering
                List<String> cats = categories(); // get list of categories
                for (String c : cats) System.out.println((i++)+". " + c); // print categories with numbers
                System.out.print("Choose category number or 'q' to quit: "); // prompt for category choice
                String catInput = sc.nextLine().trim(); // read category input
                if (catInput.equalsIgnoreCase("q")) break; // exit if user chooses to quit
                int cidx = -1; // category index
                try { cidx = Integer.parseInt(catInput)-1; } catch (Exception ex) { System.out.println("Invalid"); continue; } // parse category index
                if (cidx < 0 || cidx >= cats.size()) { System.out.println("Invalid"); continue; } // validate category index
                String cat = cats.get(cidx); // get selected category
                // Log category selection
                if (logger != null) logger.log(caseId, p.getId(), "SELECT_CATEGORY", cat, null, "", "OK", p.getScore()); // log category selection
                List<Integer> values = valuesForCategory(cat).stream().filter(v -> !board.get(cat).get(v).isAsked()).collect(Collectors.toList()); // get available values
                if (values.isEmpty()) { System.out.println("No remaining questions in this category."); continue; } // notify if no questions left
                System.out.println("Available values: " + values); // display available values
                System.out.print("Choose value: "); // prompt for value choice
                String vStr = sc.nextLine().trim(); // read value input
                int value = 0; try { value = Integer.parseInt(vStr); } catch (Exception ex) { System.out.println("Invalid"); continue; } // parse value
                if (!board.get(cat).containsKey(value)) { System.out.println("Invalid"); continue; } // validate value
                Question q = board.get(cat).get(value); // get selected question
                if (q.isAsked()) { System.out.println("Already asked"); continue; } // check if question already asked

                // Log question selection
                if (logger != null) logger.log(caseId, p.getId(), "SELECT_QUESTION", cat, value, "", "OK", p.getScore()); // log question selection

                System.out.println("Question: " + q.getText()); // display question text
                // print multiple-choice options if available
                if (q.getChoiceA() != null) System.out.println("(A) " + q.getChoiceA()); // print choice A
                if (q.getChoiceB() != null) System.out.println("(B) " + q.getChoiceB()); // print choice B
                if (q.getChoiceC() != null) System.out.println("(C) " + q.getChoiceC()); // print choice C
                if (q.getChoiceD() != null) System.out.println("(D) " + q.getChoiceD()); // print choice D
                System.out.print("Your answer: "); // prompt for answer
                String ans = sc.nextLine().trim(); // read answer

                // Use the user's raw answer for scoring (comparisons use Question.getAnswer())
                // call scoring (scoring strategy will resolve single-letter answers against choices)
                int delta = scoringStrategy.scoreForAnswer(p, q, ans); // calculate score delta
                boolean correct = delta > 0; // determine if answer is correct
                p.addScore(delta); // update player score
                q.setAsked(true); // mark question as asked

                // Log score update
                if (logger != null) logger.log(caseId, p.getId(), "SCORE_UPDATED", cat, value, ans, correct ? "CORRECT" : "WRONG", p.getScore()); // log score update

                TurnRecord tr = new TurnRecord(p.getId(), p.getName(), cat, value, q.getText(), q.getChoiceA(), q.getChoiceB(), q.getChoiceC(), q.getChoiceD(), ans, correct, delta, p.getScore(), ZonedDateTime.now()); // create turn record
                records.add(tr); // add turn record to list
                if (logger != null) logger.log(caseId, p.getId(), "ANSWER", cat, value, ans, correct ? "CORRECT" : "WRONG", p.getScore()); // log answer

                System.out.println((correct?"Correct!":"Wrong.") + " Points: " + delta + " New total: " + p.getScore()); // display result
                // move to next player

                current = (current + 1) % players.size(); // update current player index
            } // end while
            // Log end of game
            if (logger != null) logger.log(caseId, "SYSTEM", "END_GAME", "", null, "", "OK", players.stream().mapToInt(Player::getScore).sum()); // log game end
            System.out.println("Game over. Generating report..."); // notify game over
            // Print final scores
            System.out.println("Final Scores:"); // display final scores header
            for (Player p : players) { // iterate over players
                System.out.println(p.getName() + ": " + p.getScore()); // print player name and score
            } // end for
            System.out.println("Report generated."); // notify report generation complete
        }
    }

    public List<TurnRecord> getRecords() { return records; } // end getRecords method
    public List<Player> getPlayers() { return players; } // end getPlayers method
    public String getCaseId() { return caseId; } // end getCaseId method
}
