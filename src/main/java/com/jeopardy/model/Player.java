package com.jeopardy.model; // package declaration

public class Player { // start of Player class
    private final String id; // player ID
    private final String name; // player name
    private int score = 0; // player score initialized to 0

    // constructor to initialize player with ID and name
    public Player(String id, String name) { // start of constructor
        this.id = id; // set player ID
        this.name = name; // set player name
    } // end of constructor

    @Override // override toString method
    public String toString() { return "Player{id='" + id + "', name='" + name + "', score=" + score + "}"; }
    public String getId() { return id; } // get player ID
    public String getName() { return name; } // get player name
    public int getScore() { return score; } // get player score
    public void addScore(int delta) { this.score += delta; } // add to player score
}
