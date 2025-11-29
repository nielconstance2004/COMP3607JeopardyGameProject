package com.jeopardy.model; // package declaration

import java.time.ZonedDateTime; // import ZonedDateTime for timestamp

/**
 * Records details of a player's turn in the Jeopardy game.
 * Used for generating reports and tracking game history.
 * 
 * @author Group 33
 * @version 1.0
 */
// Class to record details of a player's turn
public class TurnRecord {
    // fields for turn record details
    private final String playerId; // player ID
    private final String playerName; // player name
    private final String category; // question category
    private final int value; // question value
    private final String questionText; // question text
    private final String choiceA; // multiple choice option A
    private final String choiceB; // multiple choice option B
    private final String choiceC; // multiple choice option C
    private final String choiceD; // multiple choice option D
    private final String answerGiven; // answer given by player
    private final boolean correct; // whether the answer was correct
    private final int pointsEarned; // points earned this turn
    private final int runningTotal; // player's running total points
    private final ZonedDateTime timestamp; // timestamp of the turn

    /**
     * Constructs a complete turn record with all details.
     * 
     * @param playerId the player's unique identifier
     * @param playerName the player's display name
     * @param category the question category
     * @param value the question point value
     * @param questionText the question text
     * @param choiceA multiple choice option A
     * @param choiceB multiple choice option B
     * @param choiceC multiple choice option C
     * @param choiceD multiple choice option D
     * @param answerGiven the answer provided by the player
     * @param correct whether the answer was correct
     * @param pointsEarned points earned this turn
     * @param runningTotal player's running total after this turn
     * @param timestamp when the turn occurred
     */
    public TurnRecord(String playerId, String playerName, String category, int value, String questionText,
                      String choiceA, String choiceB, String choiceC, String choiceD,
                      String answerGiven, boolean correct, int pointsEarned, int runningTotal, ZonedDateTime timestamp) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.category = category;
        this.value = value;
        this.questionText = questionText;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.answerGiven = answerGiven;
        this.correct = correct;
        this.pointsEarned = pointsEarned;
        this.runningTotal = runningTotal;
        this.timestamp = timestamp;
    }

    /**
     * Gets the player identifier.
     * 
     * @return player ID
     */
    public String getPlayerId() { return playerId; }
    
    /**
     * Gets the player name.
     * 
     * @return player name
     */
    public String getPlayerName() { return playerName; }
    
    /**
     * Gets the question category.
     * 
     * @return category
     */
    public String getCategory() { return category; }
    
    /**
     * Gets the question value.
     * 
     * @return point value
     */
    public int getValue() { return value; }
    
    /**
     * Gets the question text.
     * 
     * @return question text
     */
    public String getQuestionText() { return questionText; }
    
    /**
     * Gets choice A text.
     * 
     * @return choice A
     */
    public String getChoiceA() { return choiceA; }
    
    /**
     * Gets choice B text.
     * 
     * @return choice B
     */
    public String getChoiceB() { return choiceB; }
    
    /**
     * Gets choice C text.
     * 
     * @return choice C
     */
    public String getChoiceC() { return choiceC; }
    
    /**
     * Gets choice D text.
     * 
     * @return choice D
     */
    public String getChoiceD() { return choiceD; }
    
    /**
     * Gets the answer given by the player.
     * 
     * @return player's answer
     */
    public String getAnswerGiven() { return answerGiven; }
    
    /**
     * Checks if the answer was correct.
     * 
     * @return true if correct, false otherwise
     */
    public boolean isCorrect() { return correct; }
    
    /**
     * Gets points earned this turn.
     * 
     * @return points earned
     */
    public int getPointsEarned() { return pointsEarned; }
    
    /**
     * Gets the player's running total after this turn.
     * 
     * @return running total score
     */
    public int getRunningTotal() { return runningTotal; }
    
    /**
     * Gets the timestamp of the turn.
     * 
     * @return turn timestamp
     */
    public ZonedDateTime getTimestamp() { return timestamp; }
}
