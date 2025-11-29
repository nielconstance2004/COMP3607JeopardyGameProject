package com.jeopardy.model; // package declaration

/**
 * Represents a Jeopardy question with category, value, text, and answer options.
 * Tracks whether the question has been asked during the game.
 * 
 * @author Group 33
 * @version 1.0
 */
// Question model class
public class Question { // start of Question class
    private String id; // question ID
    private String category; // question category
    private int value; // question value
    private String text; // question text
    private String answer; // correct answer
    private String choiceA; // choice A
    private String choiceB; // choice B
    private String choiceC; // choice C
    private String choiceD; // choice D
    private boolean asked = false; // flag to indicate if question has been asked

    // default constructor
    public Question() {} // end of default constructor

    /**
     * Constructs a question with basic information.
     * 
     * @param id the unique question identifier
     * @param category the question category
     * @param value the point value of the question
     * @param text the question text
     * @param answer the correct answer
     */
    // parameterized constructor
    public Question(String id, String category, int value, String text, String answer) { // start of parameterized constructor
        this.id = id; // set question ID
        this.category = category; // set question category
        this.value = value; // set question value
        this.text = text; // set question text
        this.answer = answer; // set correct answer
    } // end of parameterized constructor

    /**
     * Gets the question ID.
     * @return the question ID
     */
    public String getId() { return id; } // get question ID
    
    /**
     * Sets the question Identifier.
     * @param id the question ID to set
     */
    public void setId(String id) { this.id = id; } // set question ID

    /**
     * Gets the question category.
     * @return the question category
     */
    public String getCategory() { return category; } // get question category
    
    /**
     * Sets the question category.
     * @param category the question category to set
     */
    public void setCategory(String category) { this.category = category; } // set question category
    
    /**
     * Gets the question value.
     * @return the question value
     */
    public int getValue() { return value; } // get question value
    
    /**
     * Sets the question point value.
     * 
     * @param value the point value to set
     */
    public void setValue(int value) { this.value = value; }

    /**
     * Gets the question text.
     * 
     * @return the question text
     */
    public String getText() { return text; }
    
    /**
     * Sets the question text.
     * 
     * @param text the question text to set
     */
    public void setText(String text) { this.text = text; }

    /**
     * Gets the correct answer.
     * 
     * @return the correct answer
     */
    public String getAnswer() { return answer; }
    
    /**
     * Sets the correct answer.
     * 
     * @param answer the correct answer to set
     */
    public void setAnswer(String answer) { this.answer = answer; }
    
    /**
     * Gets choice A text.
     * 
     * @return choice A text
     */
    public String getChoiceA() { return choiceA; }
    
    /**
     * Sets choice A text.
     * 
     * @param choiceA the choice A text to set
     */
    public void setChoiceA(String choiceA) { this.choiceA = choiceA; }
    
    /**
     * Gets choice B text.
     * 
     * @return choice B text
     */
    public String getChoiceB() { return choiceB; }
    
    /**
     * Sets choice B text.
     * 
     * @param choiceB the choice B text to set
     */
    public void setChoiceB(String choiceB) { this.choiceB = choiceB; }
    
    /**
     * Gets choice C text.
     * 
     * @return choice C text
     */
    public String getChoiceC() { return choiceC; }
    
    /**
     * Sets choice C text.
     * 
     * @param choiceC the choice C text to set
     */
    public void setChoiceC(String choiceC) { this.choiceC = choiceC; }
    
    /**
     * Gets choice D text.
     * 
     * @return choice D text
     */
    public String getChoiceD() { return choiceD; }
    
    /**
     * Sets choice D text.
     * 
     * @param choiceD the choice D text to set
     */
    public void setChoiceD(String choiceD) { this.choiceD = choiceD; }

    /**
     * Gets the choice text for a given letter (A-D).
     * 
     * @param letter the choice letter (A, B, C, or D)
     * @return the corresponding choice text, or null if invalid letter
     */
    public String getChoiceByLetter(String letter) {
        if (letter == null) return null;
        switch (letter.trim().toUpperCase()) {
            case "A": return choiceA;
            case "B": return choiceB;
            case "C": return choiceC;
            case "D": return choiceD;
            default: return null;
        }
    }

    /**
     * Checks if the question has been asked.
     * 
     * @return true if the question has been asked, false otherwise
     */
    public boolean isAsked() { return asked; }
    
    /**
     * Sets the asked status of the question.
     * 
     * @param asked true to mark as asked, false otherwise
     */
    public void setAsked(boolean asked) { this.asked = asked; }
}
