package com.jeopardy.model; // package declaration

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

    // parameterized constructor
    public Question(String id, String category, int value, String text, String answer) { // start of parameterized constructor
        this.id = id; // set question ID
        this.category = category; // set question category
        this.value = value; // set question value
        this.text = text; // set question text
        this.answer = answer; // set correct answer
    } // end of parameterized constructor

    // getters and setters
    public String getId() { return id; } // get question ID
    public void setId(String id) { this.id = id; } // set question ID

    public String getCategory() { return category; } // get question category
    public void setCategory(String category) { this.category = category; } // set question category
    public int getValue() { return value; } // get question value
    public void setValue(int value) { this.value = value; } // set question value

    public String getText() { return text; } // get question text
    public void setText(String text) { this.text = text; } // set question text

    public String getAnswer() { return answer; } // get correct answer
    public void setAnswer(String answer) { this.answer = answer; } // set correct answer
    public String getChoiceA() { return choiceA; } // get choice A
    public void setChoiceA(String choiceA) { this.choiceA = choiceA; } // set choice A
    public String getChoiceB() { return choiceB; } // get choice B
    public void setChoiceB(String choiceB) { this.choiceB = choiceB; } // set choice B
    public String getChoiceC() { return choiceC; } // get choice C
    public void setChoiceC(String choiceC) { this.choiceC = choiceC; } // set choice C
    public String getChoiceD() { return choiceD; } // get choice D
    public void setChoiceD(String choiceD) { this.choiceD = choiceD; } // set choice D

    /** Return the choice text for a letter (A-D) or null if not present. */
    public String getChoiceByLetter(String letter) { // get choice text by letter
        if (letter == null) return null; // return null if letter is null
        switch (letter.trim().toUpperCase()) { // switch based on uppercase letter
            case "A": return choiceA; // return choice A
            case "B": return choiceB; // return choice B
            case "C": return choiceC; // return choice C
            case "D": return choiceD; // return choice D
            default: return null; // return null for invalid letter
        } // end switch
    } // end getChoiceByLetter method

    // check if question has been asked
    public boolean isAsked() { return asked; } // end isAsked method
    public void setAsked(boolean asked) { this.asked = asked; } // end setAsked method
}
