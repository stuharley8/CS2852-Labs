/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 7 - Morse Code Decoder
 * Name: Stuart Harley
 * Created: 4/22/19
 */

package harleys;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class deals with creating and reading Morse Code Trees
 */
public class MorseDecoder {

    /**
     * Loads a MorseTree from a file containing the morse code symbols
     * This method calls add() from MorseTree multiple times in order to
     * populate the tree
     * @param path the path of the file
     * @return a populated MorseTree object
     * @throws IOException if there is a problem loading the file
     */
    public static MorseTree loadDecoder(Path path) throws IOException {
        MorseTree<String> morseTree = new MorseTree<>();
        try(Scanner in = new Scanner(Files.newInputStream(path))) {
            while(in.hasNextLine()) {
                String code = in.nextLine();
                String symbol;
                if(code.startsWith("\\n")) {
                    symbol = "\n";
                    code = code.substring(2);
                } else {
                    symbol = code.substring(0, 1);
                    code = code.substring(1);
                }
                morseTree.add(symbol, code);
            }
        }
        return morseTree;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the filename containing the morse code key: ");
        String filename = in.nextLine().trim();
        System.out.println("Enter an input filename: ");
        String inputFilename = in.nextLine().trim();
        System.out.println("Enter an output filename: ");
        String outputFilename = in.nextLine().trim();
        MorseTree<String> tree;
        PrintWriter out = null;
        ArrayList<String> skipped = new ArrayList();
        try(Scanner input = new Scanner(Files.newInputStream(Path.of(inputFilename)))) {
            out = new PrintWriter(Files.newOutputStream(Path.of(outputFilename)));
            tree = loadDecoder(Path.of(filename));
            while(input.hasNext()) {
                try {
                    out.write(tree.decode(input.next().trim()));
                } catch(IllegalArgumentException e) {
                    skipped.add(e.getMessage());
                }
            }
            System.out.print("Warning: skipping:");
            String skips = "  ";
            for(String string : skipped) {
                skips += (string + ", ");
            }
            System.out.println(skips.substring(0, skips.length()-2));
        } catch(IOException e) {
            System.out.println("Error loading file");
        } catch(NullPointerException e) {
            System.out.println("Null values contained in file");
        } finally {
            out.close();
        }
    }
}
