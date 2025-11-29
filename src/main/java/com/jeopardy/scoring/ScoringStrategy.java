package com.jeopardy.scoring; // package declaration

import com.jeopardy.model.Player; // import Player model
import com.jeopardy.model.Question; // import Question model

/**
 * Strategy interface for scoring answers in the Jeopardy game.
 * Allows different scoring algorithms to be implemented interchangeably.
 * 
 * @author Group 33
 * @version 1.0
 */

public interface ScoringStrategy { // start of ScoringStrategy interface
    
    /**
     * Calculates the score for a given answer to a question.
     * 
     * @param player the player who provided the answer
     * @param question the question being answered
     * @param givenAnswer the answer provided by the player
     * @return the score earned (positive for correct, negative or zero for incorrect)
     */

    int scoreForAnswer(Player player, Question question, String givenAnswer); // method to score an answer
} // end of ScoringStrategy interface