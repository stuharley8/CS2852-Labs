/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 9 - AutoComplete Revisited
 * Name: Stuart Harley
 * Created: 5/7/19
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
Benchmark Testing - I included the time it took to load (initialize) the strategy,
        as well as the time it took to search for a prefix,
        since that could affect which strategy is possibly the most effective

words.txt file, typed in "f"
ArrayList Indexed: load time - 136.500 milliseconds
                   search time - 5.969 milliseconds
ArrayList Enhanced: load time - 137.282 milliseconds
                    search time - 4.187 milliseconds
LinkedList Indexed: load time - 122.910 milliseconds
                    search time - 01:22.634
LinkedList Enhanced: load time - 141.910 milliseconds
                     search time - 5.721 milliseconds
Sorted ArrayList: load time - 653.303 milliseconds
                  search time - 2.388 milliseconds
Trie: load time - 193.771 milliseconds
      search time - 2.824 milliseconds

top-1m.csv file, typed in "v"
Sorted ArrayList: load time - 01:01.683
                  search time - 3.029 milliseconds
Trie: load time - 00:05.619
      search time - 17.471 milliseconds
*/