/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 6 - Recursion
 * Name: Stuart Harley
 * Created: 4/9/19
 */

package harleys;

import java.io.IOException;
import java.util.List;

/**
 * Interface dealing with Auto Complete
 */
public interface AutoCompleter {

    /**
     * Loads the dictionary file
     * @param filename the dictionary filename
     * @throws IOException if there is a problem reading the file
     */
    void initialize(String filename) throws IOException;

    /**
     * Returns a list of all prefix matches in the dictionary, a.k.a., all
     * entries in the dictionary that begin with prefix
     * @param prefix the prefix the to check the dictionary with
     * @return a list of all prefix matches in the dictionary
     * @throws IllegalStateException if initialize has not been called
     * prior to calling this method
     */
    List<String> allThatBeginWith(String prefix) throws IllegalStateException;

    /**
     * Returns the number of nanoseconds required by the last call to
     * initialize() or allThatBeginsWith()
     * @return the number of nanoseconds taken to run the last method call
     * @throws IllegalStateException if initialize has not been called
     * prior to calling this method
     */
    long getLastOperationTime() throws IllegalStateException;

    /**
     * Returns true if and only if target is contained in the auto completer object.
     * This method should throw an IllegalStateException with the message “Must call
     * initialize() prior to calling this method.” if initialize() has not been
     * called prior to the call to this method.
     * @param target the String being searched for
     * @throws IllegalStateException if initialize has not been called
     * prior to calling this method
     * @return a boolean indicating if the target is contained in the auto completer object
     */
    boolean contains(String target) throws IllegalStateException;
}
