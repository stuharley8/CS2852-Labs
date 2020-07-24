/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 4 - AutoComplete
 * Name: Stuart Harley
 * Created: 4/9/19
 */

package harleys;

import java.util.LinkedList;
import java.util.List;

/**
 * Dictionary class that uses an enhanced for loop to navigate the list
 */
public class EnhancedDictionary extends Dictionary implements AutoCompleter {

    /**
     * Constructor for an Enhanced Dictionary
     * @param words the list of words
     */
    public EnhancedDictionary(List<String> words) {
        super(words);
    }

    /**
     * Constructor that copies the words from original into list and uses it
     * to store the words for this dictionary
     * @param original dictionary to have words copied from
     * @param list list to store the words copied
     */
    public EnhancedDictionary(Dictionary original, List<String> list) {
        super(original, list);
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
        for(String word : words) {
            if(word.startsWith(prefix)) {
                matches.add(word);
            }
        }
        lastOperationTime = System.nanoTime()-startTime;
        return matches;
    }
}
