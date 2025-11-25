package com.jeopardy.model; // package declaration

import java.util.UUID; // import UUID class for unique ID generation

public class QuestionBuilder { // start of QuestionBuilder class
    // fields for Question properties
    private String id = UUID.randomUUID().toString(); // unique question ID
    private String category = ""; // question category
    private int value = 0; // question value
    private String text = ""; // question text
    private String answer = ""; // correct answer
    private String choiceA = ""; // choice A
    private String choiceB = ""; // choice B
    private String choiceC = ""; // choice C
    private String choiceD = ""; // choice D

    // builder methods for setting fields
    public QuestionBuilder id(String id) { this.id = id; return this; } // set question ID
    public QuestionBuilder category(String category) { this.category = category; return this; } // set category
    public QuestionBuilder value(int value) { this.value = value; return this; } // set value
    public QuestionBuilder text(String text) { this.text = text; return this; } // set question text
    public QuestionBuilder answer(String answer) { this.answer = answer; return this; } // set correct answer
    public QuestionBuilder choiceA(String choiceA) { this.choiceA = choiceA; return this; } // set choice A
    public QuestionBuilder choiceB(String choiceB) { this.choiceB = choiceB; return this; } // set choice B
    public QuestionBuilder choiceC(String choiceC) { this.choiceC = choiceC; return this; } // set choice C
    public QuestionBuilder choiceD(String choiceD) { this.choiceD = choiceD; return this; } // set choice D

    // build and return Question object
    public Question build() { // start of build method
        Question q = new Question(id, category, value, text, answer); // create new Question object
        q.setChoiceA(choiceA); // set choice A
        q.setChoiceB(choiceB); // set choice B
        q.setChoiceC(choiceC); // set choice C
        q.setChoiceD(choiceD); // set choice D
        return q; // return constructed Question
    } // end of build method
} // end of QuestionBuilder class
