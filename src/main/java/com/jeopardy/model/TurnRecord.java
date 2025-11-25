package com.jeopardy.model; // package declaration

import java.time.ZonedDateTime; // import ZonedDateTime for timestamp

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

    // constructor to initialize all fields
    public TurnRecord(String playerId, String playerName, String category, int value, String questionText,
                      String choiceA, String choiceB, String choiceC, String choiceD,
                      String answerGiven, boolean correct, int pointsEarned, int runningTotal, ZonedDateTime timestamp) {
        this.playerId = playerId; // set player ID
        this.playerName = playerName; // set player name
        this.category = category; // set question category
        this.value = value; // set question value
        this.questionText = questionText; // set question text
        this.choiceA = choiceA; // set choice A
        this.choiceB = choiceB; // set choice B
        this.choiceC = choiceC; // set choice C
        this.choiceD = choiceD; // set choice D
        this.answerGiven = answerGiven; // set answer given by player
        this.correct = correct; // set whether the answer was correct
        this.pointsEarned = pointsEarned; // set points earned this turn
        this.runningTotal = runningTotal; // set player's running total points
        this.timestamp = timestamp; // set timestamp of the turn
    }

    // getters for all fields
    public String getPlayerId() { return playerId; } // get player ID
    public String getPlayerName() { return playerName; } // get player name
    public String getCategory() { return category; } // get question category
    public int getValue() { return value; } // get question value
    public String getQuestionText() { return questionText; } // get question text
    public String getChoiceA() { return choiceA; } // get choice A
    public String getChoiceB() { return choiceB; } // get choice B
    public String getChoiceC() { return choiceC; } // get choice C
    public String getChoiceD() { return choiceD; } // get choice D
    public String getAnswerGiven() { return answerGiven; } // get answer given by player
    public boolean isCorrect() { return correct; } // get whether the answer was correct
    public int getPointsEarned() { return pointsEarned; } // get points earned this turn
    public int getRunningTotal() { return runningTotal; } // get player's running total points
    public ZonedDateTime getTimestamp() { return timestamp; } // get timestamp of the turn
}
