package com.jeopardy.scoring; // package declaration

import com.jeopardy.model.Player; // import Player model
import com.jeopardy.model.Question; // import Question model

public interface ScoringStrategy { // start of ScoringStrategy interface
    int scoreForAnswer(Player player, Question question, String givenAnswer); // method to score an answer
} // end of ScoringStrategy interface