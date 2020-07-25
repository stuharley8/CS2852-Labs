/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 9 - AutoComplete Revisited
 * Name: Stuart Harley
 * Created: 5/7/19
 */

package harleys;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Dictionary class that uses a Sorted ArrayList
 */
public class SortedDictionary extends Dictionary implements AutoCompleter {

    /**
     * Constructor for an Enhanced Dictionary
     * @param words the list of words
     */
    public SortedDictionary(SortedArrayList<String> words) {
        super(words);
    }

    /**
     * Returns a list of all prefix matches in the dictionary, a.k.a., all
     * entries in the dictionary that begin with prefix
     * @param prefix the prefix the to check the dictionary with
     * @return a list of all prefix matches in the dictionary
     * @throws IllegalStateException if initialize has not been called
     * prior to calling this method
     */
    @Override
    public List<String> allThatBeginWith(String prefix) throws IllegalStateException {
        if(!initialized) {
            throw new IllegalStateException("Must call initialize() prior to calling" +
                    "this method");
        }
        long startTime = System.nanoTime();
        List<String> matches = new LinkedList<>();
        int index = Collections.binarySearch(words, prefix);
        if(index<0) {
            index = index*-1 - 1;
        }
        for(int i = index; i<words.size() && words.get(i).startsWith(prefix); i++) {
            matches.add(words.get(i));
        }
        lastOperationTime = System.nanoTime()-startTime;
        return matches;
    }
}