/*
    Singleton object to hold options of game.

    Calling getInstance
*/
package ca.karlstewart.dogseeker.model;

import android.util.Pair;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeSet;

public class GameOptions {
    private static GameOptions instance;

    private int boardWidth;
    private int boardHeight;

    private int numDogs;
    private int totalPlays;

    private static final int maxScoresPerBoard = 5;
    private ArrayList<Pair<String, TreeSet<Integer>>> scoresByBoard;

    // Default initialization of member variables
    private GameOptions(){
        scoresByBoard = new ArrayList<Pair<String, TreeSet<Integer>>>();
        boardWidth = 4;
        boardHeight = 6;
        numDogs = 6;
        totalPlays = 0;
    }

    // Return the current instance, creating a new instance from json if instance is null
    public static GameOptions getInstance(String optionsJson) {
        if (instance == null) {
            // Create new GameOptions instance
            if (optionsJson.equals("")) {
                // No saved preferences, fill with defaults
                instance = new GameOptions();
            }
            else
            {
                // Deserialize gson into fields.
                Gson gson = new Gson();
                instance = gson.fromJson(optionsJson, instance.getClass());
            }
        }
        return instance;
    }

    // Wrapper for getInstance(String json). Uses default initialization if no instance exists yet
    public static GameOptions getInstance() {
        return getInstance("");
    }

    // Returns a JSON serialization of the current instance
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(instance);
    }

    // Add score to score list, returns true if newScore is a high score, false otherwise
    public boolean addScore(int newScore) {
        // Create board descriptor string for storing in highscore table
        String currBoardString = String.format(Locale.getDefault(), "%d x %d", boardWidth, boardHeight);

        // Search through scoresByBoard for matching game board size
        boolean foundMatchingBoard = false;
        for (Pair<String, TreeSet<Integer>> boardScores : scoresByBoard)
        {
            if (boardScores.first.equals(currBoardString)) {
                // Found matching scores for this board configuration
                //noinspection UnusedAssignment
                foundMatchingBoard = true;
                TreeSet<Integer> scores = boardScores.second;

                // Only add new score if there's room
                if (scores.size() < maxScoresPerBoard) {
                    // Score list for this board configuration has room
                    scores.add(newScore);
                    return true;
                }
                else
                // Score list for this board configuration is full, add if higher than lowest score
                {
                    // Remove worst score in scores, and compare with our new score
                    int worstHighScore = scores.pollLast();
                    if (newScore > worstHighScore) {
                        // New score is too high, return worstHighScore to scores
                        scores.add(worstHighScore);
                        return false;
                    }
                    else
                    {
                        // New score is a better score! Add to scores
                        scores.add(newScore);
                        return true;
                    }
                }
            }
        }

        if (!foundMatchingBoard) {
            // Add new board configuration to scoresByBoard
            TreeSet<Integer> newScoreTree = new TreeSet<Integer>();
            newScoreTree.add(newScore);
            scoresByBoard.add(new Pair<String, TreeSet<Integer>>(currBoardString, newScoreTree));
        }
        return true;
    }
    // Reset score list to an empty state
    public void resetScores() {
        scoresByBoard.clear();
    }

    // Getter for numDogs option
    public int getNumDogs() {
        return numDogs;
    }
    // Setter for numDogs option, numDogs must be larger than 0 and less than board size
    public void setNumDogs(int numDogs) {
        if (numDogs < 1){
            throw new IllegalArgumentException("Number of dogs must be a positive integer");
        }
        else if (numDogs > boardWidth*boardHeight){
            throw new IllegalArgumentException("Number of dogs must be less than number of tiles on board");
        }
        this.numDogs = numDogs;
    }

    // Getter for boardWidth option
    public int getBoardWidth() {
        return boardWidth;
    }
    // Setter for boardWidth option, boardWidth must be larger than 0
    public void setBoardWidth(int boardWidth) {
        if (boardWidth < 1){
            throw new IllegalArgumentException("boardWidth must be greater than 0");
        }
        this.boardWidth = boardWidth;
    }

    // Getter for boardHeight option
    public int getBoardHeight() {
        return boardHeight;
    }
    // Setter for boardHeight option, boardHeight must be larger than 0
    public void setBoardHeight(int boardHeight) {
        if (boardHeight < 1){
            throw new IllegalArgumentException("boardHeight must be greater than 0");
        }
        this.boardHeight = boardHeight;
    }

    // Getter for totalPlays
    public int getTotalPlays() {
        return totalPlays;
    }
    // Adds 1 to totalPlays
    public void addNewPlay() {
        this.totalPlays++;
    }
    // Reset totalPlays to 0
    public void resetTotalPlays() {
        totalPlays = 0;
    }
}
