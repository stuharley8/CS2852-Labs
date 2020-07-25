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
 * AutoCompleter implementation using a Trie
 */
public class TrieAutoCompleter implements AutoCompleter {

    protected Trie trie;
    protected long lastOperationTime;
    protected boolean initialized = false;

    /**
     * Default Constructor
     */
    public TrieAutoCompleter() {
        trie = new Trie();
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
            trie.clear();
            if(path.toFile().getName().endsWith("csv")) {
                while(in.hasNextLine()) {
                    String[] values = in.nextLine().split(",");
                    trie.add(values[1]);
                }
            } else {
                while(in.hasNextLine()) {
                    trie.add(in.nextLine().trim());
                }
            }
        }
        lastOperationTime = System.nanoTime()-startTime;
        initialized = true;
    }

    /**
     * Returns a list of all prefix matches in the dictionary, a.k.a., all
     * entries in the dictionary that begin with prefix
     *
     * @param prefix the prefix the to check the dictionary with
     * @return a list of all prefix matches in the dictionary
     * @throws IllegalStateException if initialize has not not been called
     *                               prior to calling this method
     */
    @Override
    public List<String> allThatBeginWith(String prefix) throws IllegalStateException {
        if(!initialized) {
            throw new IllegalStateException("Must call initialize() prior to calling" +
                    "this method");
        }
        long startTime = System.nanoTime();
        List<String> matches = trie.allThatBeginsWith(prefix);
        lastOperationTime = System.nanoTime()-startTime;
        return matches;
    }

    /**
     * Returns the number of nanoseconds required by the last call to
     * initialize() or allThatBeginsWith()
     *
     * @return the number of nanoseconds taken to run the last method call
     * @throws IllegalStateException if initialize has not not been called
     *                               prior to calling this method
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
