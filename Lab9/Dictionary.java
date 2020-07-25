/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 9 - AutoComplete Revisited
 * Name: Stuart Harley
 * Created: 5/7/19
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

    protected boolean initialized = false;

    /**
     * Constructor for a Dictionary
     * @param list the list
     */
    public Dictionary(List<String> list) {
        words = list;
        lastOperationTime = 0;
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
        initialized = true;
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
        if(!initialized) {
            throw new IllegalStateException("Must call initialize() prior to calling" +
                    "this method");
        }
        return lastOperationTime;
    }
}