/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 6 - Recursion
 * Name: Stuart Harley
 * Created: 4/9/19
 */

package harleys;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the grid of letters
 */
public class GameBoard {

    private static final int MAX_WORD_LENGTH = 15;

    private String[][] letters;

    private int numRows;

    private int numCols;

    private AutoCompleter strategy;

    private List<String> words = new LinkedList<>();

    /**
     * Constructs a game board object that makes use of the strategy
     * to determine if a letter sequence is found in the word list.
     * @param strategy the AutoCompleter to be used to find the words
     * @throws IllegalArgumentException if strategy has not been initialized
     */
    public GameBoard(AutoCompleter strategy) throws IllegalArgumentException{
        try {
            strategy.getLastOperationTime();
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Strategy has not been initialized");
        }
        this.strategy = strategy;
    }

    /**
     * Loads a grid of letters that make up the game board.
     * @param path the path of the file containing the letters
     * @throws IOException if there is a problem reading the file
     * @throws IllegalArgumentException if all rows are not the same length
     */
    public void load(Path path) throws IOException, IllegalArgumentException {
        try(Scanner in = new Scanner(Files.newInputStream(path))) {
            if(in.hasNextLine()) {
                numCols = in.nextLine().trim().length();
                numRows = 1;
            }
            while(in.hasNextLine()) {
                numRows++;
                String line = in.nextLine().trim();
                if(line.length()!=numCols) {
                    throw new IllegalArgumentException("All rows are not the same length");
                }
                numCols = line.length();
            }
        }
        letters = new String[numRows][numCols];
        try(Scanner in = new Scanner(Files.newInputStream(path))) {
            int currentRow = 0;
            while(in.hasNextLine()) {
                String line = in.nextLine().trim();
                for (int currentCol = 0; currentCol < numCols; currentCol++) {
                    letters[currentRow][currentCol] = line.substring(0, 1);
                    line = line.substring(1);
                }
                currentRow++;
            }
        }
    }

    /**
     * returns a list of all words that begin with the letter at position (row, col)
     * that are found in the AutoCompleter
     * @param row the row of the letter being checked
     * @param col the column of the letter being checked
     * @param visitedFlags a 2D boolean array indicates whether a grid position
     * has already been visited
     * @param partialWord the partial word that has been built so far
     * @return the list of all words that begin with the letter at (row, col)
     */
    private List<String> recursiveSearch(int row, int col,
                                         boolean[][] visitedFlags, String partialWord) {
        visitedFlags[row][col] = true;
        partialWord += letters[row][col];
        if(partialWord.length() == MAX_WORD_LENGTH || allSurroundingUsed(row, col, visitedFlags)) {
            if(strategy.contains(partialWord.toLowerCase())) {
                words.add(partialWord.toLowerCase());
            }
        }
        if(row-1>=0 && col-1>=0 && !visitedFlags[row - 1][col - 1]) {
            recursiveSearch(row-1, col-1, visitedFlags, partialWord);
        }
        if(row-1>=0 && !visitedFlags[row - 1][col]) {
            recursiveSearch(row-1, col, visitedFlags, partialWord);
        }
        if(row-1>=0 && col+1<numCols && !visitedFlags[row - 1][col + 1]) {
            recursiveSearch(row-1, col+1, visitedFlags, partialWord);
        }
        if(col-1>=0 && !visitedFlags[row][col - 1]) {
            recursiveSearch(row, col-1, visitedFlags, partialWord);
        }
        if(col+1<numCols && !visitedFlags[row][col + 1]) {
            recursiveSearch(row, col+1, visitedFlags, partialWord);
        }
        if(row+1<numRows && col-1>=0 && !visitedFlags[row + 1][col - 1]) {
            recursiveSearch(row+1, col-1, visitedFlags, partialWord);
        }
        if(row+1<numRows && !visitedFlags[row + 1][col]) {
            recursiveSearch(row+1, col, visitedFlags, partialWord);
        }
        if(row+1<numRows && col+1<numCols && !visitedFlags[row + 1][col + 1]) {
            recursiveSearch(row+1, col+1, visitedFlags, partialWord);
        }
        visitedFlags[row][col] = false;
        partialWord = partialWord.substring(0, partialWord.length()-1);
        if(partialWord.length()>2 && !words.contains(partialWord.toLowerCase()) &&
                strategy.contains(partialWord.toLowerCase())) {
            words.add(partialWord.toLowerCase());
        }
        return words;
    }

    /**
     * Determines if all surrounding letters have already been used
     * @param row the row of the current letter
     * @param col the column of the current letter
     * @param visitedFlags a boolean[][] keeping track of which letters have been used
     * @return whether or not all the surrounding letters have been used
     */
    private boolean allSurroundingUsed(int row, int col, boolean[][] visitedFlags) {
        boolean allUsed = true;
        if(row-1>=0 && col-1>=0 && !visitedFlags[row - 1][col - 1]) {
            allUsed = false;
        } else if(row-1>=0 && !visitedFlags[row - 1][col]) {
            allUsed = false;
        } else if(row-1>=0 && col+1<numCols && !visitedFlags[row - 1][col + 1]) {
            allUsed = false;
        } else if(col-1>=0 && !visitedFlags[row][col - 1]) {
            allUsed = false;
        } else if(col+1<numCols && !visitedFlags[row][col + 1]) {
            allUsed = false;
        } else if(row+1<numRows && col-1>=0 && !visitedFlags[row + 1][col - 1]) {
            allUsed = false;
        } else if(row+1<numRows && !visitedFlags[row + 1][col]) {
            allUsed = false;
        } else if(row+1<numRows && col+1<numCols && !visitedFlags[row + 1][col + 1]) {
            allUsed = false;
        }
        return allUsed;
    }

    /**
     * Makes use of the private recursive method to generate and return a list of all of the
     * words in the word list that can be constructed from adjacent letters on the game board
     * @return a list of all of the valid words in the word list that can be constructed from
     * the letters on the game board
     */
    public List<String> findWords() {
        boolean[][] visitedFlags = new boolean[numRows][numCols];
        List<String> allWords = new LinkedList<>();
        for(int row = 0; row<numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                allWords.addAll(recursiveSearch(row, col, visitedFlags, ""));
                words.clear();
                visitedFlags = new boolean[numRows][numCols];
            }
        }
        return allWords;
    }
}