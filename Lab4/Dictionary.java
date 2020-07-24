/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 4 - AutoComplete
 * Name: Stuart Harley
 * Created: 3/22/19
 */

package harleys;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Class represents a dictionary with a List of Strings
 */
public abstract class Dictionary implements AutoCompleter {

    protected List<String> words;

    protected long lastOperationTime;

    /**
     * Constructor for a Dictionary
     * @param list the list
     */
    public Dictionary(List<String> list) {
        words = list;
        lastOperationTime = -1;
    }

    /**
     * Constructor that copies the words from original into list and uses it
     * to store the words for this dictionary
     * @param original dictionary to have words copied from
     * @param list list to store the words copied
     */
    public Dictionary(Dictionary original, List<String> list) {
        list.addAll(original.words);
        words = list;
    }

    /**
     * Loads the dictionary file
     *
     * @param filename the dictionary filename
     * @throws IOException if there is a problem reading the file
     */
    @Override
    public void initialize(String filename) throws IOException {
        long startTime = System.nanoTime();
        Path path = Path.of(filename);
        try(Scanner in = new Scanner(Files.newInputStream(path))) {
            words.clear();
            if(path.toFile().getName().endsWith("csv")) {
                while(in.hasNextLine()) {
                    String[] values = in.nextLine().split(",");
                    words.add(values[1] + ": " + values[0]);
                }
            } else {
                while(in.hasNextLine()) {
                    words.add(in.nextLine());
                }
            }
        }
        lastOperationTime = System.nanoTime()-startTime;
    }

    /**
     * Returns the number of nanoseconds required by the last call to
     * initialize() or allThatBeginsWith()
     *
     * @return the number of nanoseconds taken to run the last method call
     * @throws IllegalStateException if initialize has not been called
     * prior to calling this method
     */
    @Override
    public long getLastOperationTime() throws IllegalStateException {
        if(lastOperationTime == -1) {
            throw new IllegalStateException("Must call initialize() prior to calling" +
                    "this method");
        }
        return lastOperationTime;
    }
}