package com.jeopardy;

import com.jeopardy.model.Question;
import com.jeopardy.model.QuestionBuilder;
import com.jeopardy.scoring.SimpleScoringStrategy;
import com.jeopardy.loaders.CsvQuestionLoader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultipleChoiceTests {

    @Test
    void scoringByLetterResolvesToChoice() {
        Question q = new QuestionBuilder()
                .value(200)
                .answer("Paris")
                .choiceA("London")
                .choiceB("Paris")
                .choiceC("Rome")
                .choiceD("Berlin")
                .build();

        SimpleScoringStrategy strat = new SimpleScoringStrategy(true);
        int pts = strat.scoreForAnswer(null, q, "B");
        assertEquals(200, pts);
    }

    @Test
    void scoringByFullChoiceTextWorks() {
        Question q = new QuestionBuilder()
                .value(150)
                .answer("42")
                .choiceA("41")
                .choiceB("42")
                .choiceC("43")
                .choiceD("44")
                .build();

        SimpleScoringStrategy strat = new SimpleScoringStrategy(true);
        int pts = strat.scoreForAnswer(null, q, "42");
        assertEquals(150, pts);
    }

    @Test
    void csvLoaderParsesChoices() throws Exception {
        File csv = File.createTempFile("mcq", ".csv");
        try (FileWriter fw = new FileWriter(csv)) {
            fw.write("Category,Value,Question,Answer,ChoiceA,ChoiceB,ChoiceC,ChoiceD\n");
            fw.write("Geography,200,What is the capital of France?,Paris,London,Paris,Rome,Berlin\n");
        }

        CsvQuestionLoader loader = new CsvQuestionLoader();
        List<Question> list = loader.load(csv);
        assertEquals(1, list.size());
        Question q = list.get(0);
        assertEquals("Paris", q.getAnswer());
        assertEquals("London", q.getChoiceA());
        assertEquals("Paris", q.getChoiceB());
    }

    @Test
    void scoringWhenStoredAnswerIsLetter() {
        Question q = new QuestionBuilder()
                .value(100)
                .answer("B")
                .choiceA("Red")
                .choiceB("Blue")
                .choiceC("Green")
                .choiceD("Yellow")
                .build();

        SimpleScoringStrategy strat = new SimpleScoringStrategy(true);
        int pts = strat.scoreForAnswer(null, q, "B");
        assertEquals(100, pts);
        // also accept full text
        assertEquals(100, strat.scoreForAnswer(null, q, "Blue"));
    }
}
