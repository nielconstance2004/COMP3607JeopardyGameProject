package com.jeopardy.scoring; // package declaration

import com.jeopardy.model.Player; // import Player model
import com.jeopardy.model.Question; // import Question model

/**
 * Simple scoring strategy implementation.
 * Awards question value for correct answers, and optionally penalizes for wrong answers.
 * 
 * @author Group 33
 * @version 1.0
 */

// SimpleScoringStrategy class implementing ScoringStrategy interface
public class SimpleScoringStrategy implements ScoringStrategy {
    // field to determine if wrong answers incur negative scoring
    /** Flag indicating whether wrong answers result in negative scoring */
    private final boolean negativeOnWrong;
    
    /**
     * Constructs a SimpleScoringStrategy with specified penalty behavior.
     * 
     * @param negativeOnWrong true to deduct points for wrong answers, false for no penalty
     */

    // constructor to initialize negativeOnWrong field
    public SimpleScoringStrategy(boolean negativeOnWrong) { 
        this.negativeOnWrong = negativeOnWrong; // set negativeOnWrong field
    } // end of constructor

    /**
     * Scores an answer by comparing it to the correct answer.
     * Supports both letter answers (A-D) and full text answers.
     * 
     * @param player the player who provided the answer (not used in this implementation)
     * @param question the question being answered
     * @param givenAnswer the answer provided by the player
     * @return the question value if correct, negative value if wrong with penalties enabled, otherwise 0
     */

    @Override // override scoreForAnswer method
    public int scoreForAnswer(Player player, Question question, String givenAnswer) { // method to score an answer
        if (question.getAnswer() == null) return 0; // if no correct answer, return 0
        String correctAnswer = question.getAnswer().trim(); // get and trim correct answer
        String given = givenAnswer == null ? "" : givenAnswer.trim(); // get and trim given answer
        boolean correct = false; // flag to track if answer is correct
        // Normalize
        String stored = correctAnswer; // stored correct answer
        // If user gave a single-letter (A-D)
        if (given.length() == 1) { // user provided a single-letter answer
            // If the stored answer is also a single-letter, compare letters
            if (stored.length() == 1) { // stored answer is also a single letter
                correct = stored.equalsIgnoreCase(given); // compare letters
            } else { // stored answer is full text
                // resolve the user's letter to choice text and compare to stored answer
                String choiceText = question.getChoiceByLetter(given); // get choice text for given letter
                if (choiceText != null) { // valid choice letter
                    correct = choiceText.trim().equalsIgnoreCase(stored); // compare choice text to stored answer
                } else { // invalid choice letter
                    // fallback: compare the letter to stored
                    correct = stored.equalsIgnoreCase(given); // compare letters as fallback
                } // end choice letter check
            } // end stored answer check
        } else {
            // User entered full text. If stored answer is a letter, resolve stored letter's choice text
            if (stored.length() == 1) { // stored answer is a single letter
                String storedChoiceText = question.getChoiceByLetter(stored); // get choice text for stored letter
                if (storedChoiceText != null) { // valid stored choice letter
                    correct = storedChoiceText.equalsIgnoreCase(given); // compare stored choice text to given answer
                } else { // invalid stored choice letter
                    correct = stored.equalsIgnoreCase(given); // compare stored letter to given answer
                } // end stored choice letter check
            } else { // stored answer is full text
                // Accept either exact match of the stored answer or match against choice texts
                if (stored.equalsIgnoreCase(given)) correct = true; // exact match
                else { // check against choice texts
                    String a = question.getChoiceA(); // get choice A
                    String b = question.getChoiceB(); // get choice B
                    String c = question.getChoiceC(); // get choice C
                    String d = question.getChoiceD(); // get choice D
                    if (a != null && a.equalsIgnoreCase(given)) correct = true; // check choice A
                    if (b != null && b.equalsIgnoreCase(given)) correct = true; // check choice B
                    if (c != null && c.equalsIgnoreCase(given)) correct = true; // check choice C
                    if (d != null && d.equalsIgnoreCase(given)) correct = true; // check choice D
                } // end choice text check
            } // end stored answer check
        } // end given answer length check
        if (correct) return question.getValue(); // return question value if correct
        return negativeOnWrong ? -question.getValue() : 0; // return negative value or 0 if wrong
    }
}