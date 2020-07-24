/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 6 - Recursion
 * Name: Stuart Harley
 * Created: 4/9/19
 */

package harleys;

import java.util.LinkedList;
import java.util.List;

/**
 * Dictionary class that uses indexing methods to navigate the list
 */
public class IndexDictionary extends Dictionary implements AutoCompleter {

    /**
     * Constructor for an Enhanced Dictionary
     * @param words the list of words
     */
    public IndexDictionary(List<String> words) {
        super(words);
    }

    /**
     * Returns a list of all prefix matches in the dictionary, a.k.a., all
     * entries in the dictionary that begin with prefix
     *
     * @param prefix the prefix the to check the dictionary with
     * @return a list of all prefix matches in the dictionary
     * @throws IllegalStateException if initialize has not been called
     * prior to calling this method
     */
    @Override
    public List<String> allThatBeginWith(String prefix) throws IllegalStateException {
        if(getLastOperationTime() == -1) {
            throw new IllegalStateException("Must call initialize() prior to calling" +
                    "this method");
        }
        long startTime = System.nanoTime();
        List<String> matches = new LinkedList<>();
        for(int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if(word.startsWith(prefix)) {
                matches.add(word);
            }
        }
        lastOperationTime = System.nanoTime()-startTime;
        return matches;
    }

    /**
     * Returns true if and only if target is contained in the auto completer object.
     * This method should throw an IllegalStateException with the message “Must call
     * initialize() prior to calling this method.” if initialize() has not been
     * called prior to the call to this method.
     *
     * @param target the String being searched for
     * @throws IllegalStateException if initialize has not been called
     * prior to calling this method
     * @return a boolean indicating if the target is contained in the auto completer object
     */
    @Override
    public boolean contains(String target) throws IllegalStateException {
        if(lastOperationTime == -1) {
            throw new IllegalStateException("Must call initialize() prior to calling" +
                    "this method");
        }
        boolean contains = false;
        for(int i = 0; i<words.size() && !contains; i++) {
            if(words.get(i).equals(target)) {
                contains = true;
            }
        }
        return contains;
    }
}