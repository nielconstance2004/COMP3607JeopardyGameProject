package com.jeopardy.model; // package declaration

import java.util.UUID; // import UUID class for unique ID generation

/**
 * Builder class for constructing Question objects step by step.
 * Implements the Builder design pattern for flexible question creation.
 * 
 * @author Group 33
 * @version 1.0
 */
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

    /**
     * Sets the question identifier.
     * 
     * @param id the question ID
     * @return this builder for method chaining
     */
    public QuestionBuilder id(String id) { this.id = id; return this; }
    
    /**
     * Sets the question category.
     * 
     * @param category the category
     * @return this builder for method chaining
     */
    public QuestionBuilder category(String category) { this.category = category; return this; }
    
    /**
     * Sets the question point value.
     * 
     * @param value the point value
     * @return this builder for method chaining
     */
    public QuestionBuilder value(int value) { this.value = value; return this; }
    
    /**
     * Sets the question text.
     * 
     * @param text the question text
     * @return this builder for method chaining
     */
    public QuestionBuilder text(String text) { this.text = text; return this; }
    
    /**
     * Sets the correct answer.
     * 
     * @param answer the correct answer
     * @return this builder for method chaining
     */
    public QuestionBuilder answer(String answer) { this.answer = answer; return this; }
    
    /**
     * Sets choice A text.
     * 
     * @param choiceA choice A text
     * @return this builder for method chaining
     */
    public QuestionBuilder choiceA(String choiceA) { this.choiceA = choiceA; return this; }
    
    /**
     * Sets choice B text.
     * 
     * @param choiceB choice B text
     * @return this builder for method chaining
     */
    public QuestionBuilder choiceB(String choiceB) { this.choiceB = choiceB; return this; }
    
    /**
     * Sets choice C text.
     * 
     * @param choiceC choice C text
     * @return this builder for method chaining
     */
    public QuestionBuilder choiceC(String choiceC) { this.choiceC = choiceC; return this; }
    
    /**
     * Sets choice D text.
     * 
     * @param choiceD choice D text
     * @return this builder for method chaining
     */
    public QuestionBuilder choiceD(String choiceD) { this.choiceD = choiceD; return this; }

    /**
     * Builds and returns the Question object with all set properties.
     * 
     * @return the constructed Question object
     */
    public Question build() { // start of build method
        Question q = new Question(id, category, value, text, answer); // create new Question object
        q.setChoiceA(choiceA); // set choice A
        q.setChoiceB(choiceB); // set choice B
        q.setChoiceC(choiceC); // set choice C
        q.setChoiceD(choiceD); // set choice D
        return q; // return constructed Question
    } // end of build method
} // end of QuestionBuilder class
