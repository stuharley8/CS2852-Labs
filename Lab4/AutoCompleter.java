/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 4 - AutoComplete
 * Name: Stuart Harley
 * Created: 3/22/19
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
     * @throws IllegalStateException if initialize has not not been called
     * prior to calling this method
     */
    List<String> allThatBeginWith(String prefix) throws IllegalStateException;

    /**
     * Returns the number of nanoseconds required by the last call to
     * initialize() or allThatBeginsWith()
     * @return the number of nanoseconds taken to run the last method call
     * @throws IllegalStateException if initialize has not not been called
     * prior to calling this method
     */
    long getLastOperationTime() throws IllegalStateException;
}

/*
Benchmarking Results for searching "f" with the words.txt file
ArrayList, Indexing Methods: 19.034 milliseconds
ArrayList, For-Each Loop: 19.634 milliseconds
LinkedList, Indexing Methods: 00:06.898
LinkedList, For-Each Loop: 22.958 milliseconds

Benchmarking Results for searching "f" with the top-1k.csv file
ArrayList, Indexing Methods: 499.579 microseconds
ArrayList, For-Each Loop: 436.929 microseconds
LinkedList, Indexing Methods: 1.868 milliseconds
LinkedList, For-Each Loop: 550.347 microseconds

I compared the performance of the different strategies by following a simple standardized
trial for each option, (searching for matches to f) and then I recorded the time it took to
perform each strategy. I did this for 2 different dictionary files of varying size. The times
recorded then allow me to analyze which strategies were the slowest/fastest. As expected, the
LinkedList Indexing Method was the slowest by far since it has a time complexity of O(n^2).
The other strategies all took very similar amounts of time since they all have time complexities
of O(n).
*/