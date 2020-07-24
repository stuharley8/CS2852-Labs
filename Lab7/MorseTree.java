/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 7 - Morse Code Decoder
 * Name: Stuart Harley
 * Created: 4/22/19
 */

package harleys;

/**
 * This class represents a binary search tree that holds morse code symbols
 * @param <E> Generic representing the type of data stored in the tree
 */
public class MorseTree<E> {

    private Node<E> root;

    /**
     * Constructor that ensure that the content in the root node
     * is set to null
     */
    public MorseTree() {
        root = new Node(null, null);
    }

    /**
     * Adds a symbol to the tree, if a symbol exists with the same code as the
     * symbol being added, the new symbol with overwrite the old symbol
     * @param symbol the letter/digit/punctuation mark
     * @param code the Morse Code associated with the symbol
     * @return true if the symbol is added
     * @throws NullPointerException if symbol or code is null
     * @throws IllegalArgumentException if code contains an invalid character
     */
    public boolean add(E symbol, String code) throws NullPointerException,
            IllegalArgumentException {
        if(symbol==null || code.length()==0) {
            throw new NullPointerException("Tree does not support null");
        }
        return add(symbol, code, code, root);
    }

    /**
     * private recursive helper method for add
     * @param symbol the letter/digit/punctuation mark
     * @param code the Morse Code associated with the symbol
     * @param subroot the subroot Node
     * @return true if the symbol is added
     */
    private boolean add(E symbol, String code, String partialCode, Node<E> subroot) {
        boolean added = false;
        if(partialCode.startsWith(".")) {
            if(subroot.lKid == null) {
                subroot.lKid = new Node(null, null);
            }
            added = add(symbol, code, partialCode.substring(1), subroot.lKid);
        } else if(partialCode.startsWith("-")) {
            if(subroot.rKid == null) {
                subroot.rKid = new Node(null, null);
            }
            added = add(symbol, code, partialCode.substring(1), subroot.rKid);
        } else if(partialCode.length()==0){
            subroot.code = code;
            subroot.symbol = symbol;
            added = true;
        } else {
            throw new IllegalArgumentException("Invalid character in code");
        }
        return added;
    }

    /**
     * Calls a recursive method to decode the code entered
     * @param code the code for a symbol that is returned by the method
     * @return the symbol represented by the code, or null if none is found
     * @throws IllegalArgumentException if the code is not found in the tree
     */
    public E decode(String code) throws IllegalArgumentException {
        if(code.length()==0) {
            throw new IllegalArgumentException(code);
        }
        try {
            return decode(code, root);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(code);
        }
    }

    /**
     * private recursive helper method for decode
     * @param code the code for a symbol that is returned by the method
     * @param subroot the subroot Node
     * @return the symbol represented by the code, or null if none is found
     * @throws IllegalArgumentException if the code is not found in the tree
     */
    private E decode(String code, Node<E> subroot) throws IllegalArgumentException {
        E symbol;
        if(code.startsWith(".")) {
            symbol = decode(code.substring(1), subroot.lKid);
        } else if(code.startsWith("-")) {
            symbol = decode(code.substring(1), subroot.rKid);
        } else if(code.length()==0){
            symbol = subroot.symbol;
        } else {
            throw new IllegalArgumentException(code);
        }
        return symbol;
    }

    /**
     * Inner Class representing a Node of the tree
     * @param <E> Generic representing the type of data stored in the Node
     */
    private static class Node<E> {

        private Node<E> lKid;
        private Node<E> rKid;
        private E symbol;
        private String code;

        private Node(E symbol, String code) {
            this.symbol = symbol;
            this.code = code;
            lKid = null;
            rKid = null;
        }
    }
}
