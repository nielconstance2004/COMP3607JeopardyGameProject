package com.jeopardy.scoring;

import com.jeopardy.model.Player;
import com.jeopardy.model.Question;

public class SimpleScoringStrategy implements ScoringStrategy {
    private final boolean negativeOnWrong;

    public SimpleScoringStrategy(boolean negativeOnWrong) {
        this.negativeOnWrong = negativeOnWrong;
    }

    @Override
    public int scoreForAnswer(Player player, Question question, String givenAnswer) {
        if (question.getAnswer() == null) return 0;
        boolean correct = question.getAnswer().trim().equalsIgnoreCase(givenAnswer == null ? "" : givenAnswer.trim());
        if (correct) return question.getValue();
        return negativeOnWrong ? -question.getValue() : 0;
    }
}