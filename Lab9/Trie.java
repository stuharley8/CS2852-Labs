/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 9 - AutoComplete Revisited
 * Name: Stuart Harley
 * Created: 5/7/19
 */

package harleys;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Class represents a Trie that will contain the words in the dictionary
 * Does not support duplicate words
 */
public class Trie {

    private Node root;

    //a-z, 0-9, dots, dashes, and slashes
    private static final int NUM_POSSIBLE_CHARACTERS = 39;

    //Value between 'a' and '.'
    private static final int OFFSET = 52;

    private static final int NUM_EXTRA_CHARACTERS = 13;

    /**
     * Private Inner Node Class
     */
    private static class Node {
        Node[] subNodes;
        String letters;
        boolean isWord;

        /**
         * Constructor
         * @param letters the letters
         */
        private Node(String letters) {
            this.letters = letters;
            subNodes = new Node[NUM_POSSIBLE_CHARACTERS];
            isWord = false;
        }
    }

    /**
     * Constructor, creates the first node with a value of nothing
     */
    public Trie() {
        root = new Node("");
    }

    /**
     * private method that finds the node that represents a word/prefix
     * @param word the word
     * @return the node that represents the word or null if the word
     * is not contained in the trie
     */
    private Node findNode(String word) {
        Node node = root;
        for(int i = 0; node != null && i < word.length(); i++) {
            char character = word.charAt(i);
            int index;
            if(Character.isDigit(character) || character == '-' ||
                    character == '.' || character == '/') {
                index = character - 'a' + OFFSET;
            } else if(Character.isAlphabetic(character)){
                index = character - 'a' + NUM_EXTRA_CHARACTERS;
            } else {
                index = 0;
            }
            if(node.subNodes[index] != null) {
                node = node.subNodes[index];
            } else {
                node = null;
            }
        }
        return node == root ? null : node;
    }

    /**
     * Returns all words in the trie that start with the specified prefix
     * Calls findNode to get the node of the prefix, and then calls the
     * recursive method allThatBeginsWith
     * @param prefix the specified prefix
     * @return a list of all the words in the trie that start with prefix
     */
    public List<String> allThatBeginsWith(String prefix) {
        Node startingNode = findNode(prefix);
        return allThatBeginsWith(startingNode, new LinkedList<>());
    }

    /**
     * Private Recursive helper method for allThatBeginsWith
     * @param startingNode the starting Node
     * @param words the list of words
     * @return the list of found words
     */
    private List<String> allThatBeginsWith(Node startingNode, List<String> words) {
        if(startingNode != null) {
            if(startingNode.isWord) {
                words.add(startingNode.letters);
            }
            for(Node node : startingNode.subNodes) {
                if(node != null) {
                    allThatBeginsWith(node, words);
                }
            }
        }
        return words;
    }

    /**
     * Adds a word to the Trie
     * @param word the word to be added
     * @return true always
     */
    public boolean add(String word) {
        Node node = root;
        for(int i = 0; i<word.length(); i++) {
            char character = word.charAt(i);
            int index;
            if(Character.isDigit(character) || character == '-' ||
                    character == '.' || character == '/') {
                index = character - 'a' + OFFSET;
            } else if(Character.isAlphabetic(character)) {
                index = character - 'a' + NUM_EXTRA_CHARACTERS;
            } else {
                index = 0;
            }
            if(node.subNodes[index]==null) {
                Node tempNode = new Node(word.substring(0, i+1));
                node.subNodes[index] = tempNode;
                node = tempNode;
            } else {
                node = node.subNodes[index];
            }
        }
        node.isWord = true;
        return true;
    }

    /**
     * Clears the Trie
     */
    public void clear() {
        root = new Node("");
    }
}