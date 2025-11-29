package com.jeopardy.model; // package declaration

/**
 * Represents a player in the Jeopardy game.
 * Tracks player identity and score throughout the game session.
 * 
 * @author Group 33
 * @version 1.0
 */
public class Player { // start of Player class
    private final String id; // player ID
    private final String name; // player name
    private int score = 0; // player score initialized to 0

    /**
     * Constructs a new player with the specified ID and name.
     * 
     * @param id the unique identifier for the player
     * @param name the display name of the player
     */
    public Player(String id, String name) { // start of constructor
        this.id = id; // set player ID
        this.name = name; // set player name
    } // end of constructor

    /**
     * Returns a string representation of the player.
     * 
     * @return string containing player ID, name, and score
     */
    @Override // override toString method
    public String toString() { return "Player{id='" + id + "', name='" + name + "', score=" + score + "}"; }
    
    /**
     * Gets the player's unique identifier.
     * 
     * @return the player ID
     */
    public String getId() { return id; } // get player ID
    
    /**
     * Gets the player's display name.
     * 
     * @return the player name
     */
    public String getName() { return name; } // get player name
    
    /**
     * Gets the player's current score.
     * 
     * @return the player score
     */
    public int getScore() { return score; } // get player score
    
    /**
     * Adds the specified amount to the player's score.
     * 
     * @param delta the amount to add to the score
     */
    public void addScore(int delta) { this.score += delta; } // add to player score
}
