/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 6 - Recursion
 * Name: Stuart Harley
 * Created: 4/9/19
 */

package harleys;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Runs the command line UI
 */
public class WordSearchCLI {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        GameBoard gameBoard;
        AutoCompleter dictionary;
        List<String> list;
        System.out.println("Enter a Strategy (Enter Exactly as Shown) :");
        System.out.println("SortedArrayList\nArrayListIndexed\nLinkedListIndexed"
                + "\nArrayListIterated\nLinkedListIterated");
        String strategy = in.nextLine().trim();
        try {
            switch (strategy) {
                case "SortedArrayList":
                    list = new SortedArrayList<>();
                    dictionary = new SortedDictionary((SortedArrayList) list);
                    break;
                case "ArrayListIndexed":
                    list = new ArrayList<>();
                    dictionary = new IndexDictionary(list);
                    break;
                case "LinkedListIndexed":
                    list = new LinkedList<>();
                    dictionary = new IndexDictionary(list);
                    break;
                case "ArrayListIterated":
                    list = new ArrayList<>();
                    dictionary = new EnhancedDictionary(list);
                    break;
                case "LinkedListIterated":
                    list = new LinkedList<>();
                    dictionary = new EnhancedDictionary(list);
                    break;
                default:
                    throw new InputMismatchException();
            }
        } catch(InputMismatchException e) {
            System.out.println("Strategy entered is invalid. Goodbye.");
            return;
        }
        System.out.println("\nEnter a WordList Filename: ");
        String dictionaryFilename = in.nextLine().trim();
        try {
            dictionary.initialize(dictionaryFilename);
            gameBoard = new GameBoard(dictionary);
        } catch(IOException | IllegalArgumentException e) {
            System.out.println("Word List File entered is invalid. Goodbye.");
            return;
        }
        System.out.println("\nEnter a Grid Filename: ");
        String gridFilename = in.nextLine().trim();
        try {
            gameBoard.load(new File(gridFilename).toPath());
            long startTime = System.nanoTime();
            List<String> words = gameBoard.findWords();
            long searchTime = System.nanoTime()-startTime;
            System.out.println("Words Contained in Grid:");
            for(String word : words) {
                System.out.println(word);
            }
            System.out.println("\nNumber of Words Contained in Grid: " + words.size());
            System.out.println(formatTime(searchTime));
        } catch(IOException e) {
            System.out.println("Grid File entered is invalid. Goodbye");
        }
    }

    /**
     * Gets the amount of time taken to complete the search and formats it
     */
    private static String formatTime(long time) {
        long min = TimeUnit.NANOSECONDS.toMinutes(time);
        long sec = TimeUnit.NANOSECONDS.toSeconds(time - TimeUnit.MINUTES.toNanos(min));
        long millis = TimeUnit.NANOSECONDS.toMillis(time - TimeUnit.MINUTES.toNanos(min)
                - TimeUnit.SECONDS.toNanos(sec));
        long micros = TimeUnit.NANOSECONDS.toMicros(time - TimeUnit.MINUTES.toNanos(min)
                - TimeUnit.SECONDS.toNanos(sec) - TimeUnit.MILLISECONDS.toNanos(millis));
        long ns = (time - TimeUnit.MINUTES.toNanos(min) - TimeUnit.SECONDS.toNanos(sec)
                - TimeUnit.MILLISECONDS.toNanos(millis) - TimeUnit.MICROSECONDS.toNanos(micros));
        String timed;
        if(micros == 0) {
            timed = ("Time required: " + ns + " nanoseconds");
        } else if(millis == 0) {
            timed = (String.format("Time required: %3d.%03d", micros, ns) + " microseconds");
        } else if(sec == 0) {
            timed = (String.format("Time required: %3d.%03d", millis, micros) + " milliseconds");
        } else {
            timed = (String.format("Time required: %02d:%02d.%03d", min, sec, millis));
        }
        return timed;
    }
}
