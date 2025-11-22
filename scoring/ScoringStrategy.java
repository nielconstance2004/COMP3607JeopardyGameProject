package com.jeopardy.scoring;

import com.jeopardy.model.Player;
import com.jeopardy.model.Question;

public interface ScoringStrategy {

    int scoreForAnswer(Player player, Question question, String givenAnswer);
}