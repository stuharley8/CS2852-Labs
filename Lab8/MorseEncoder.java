/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 8 - Morse Code Encoder
 * Name: Stuart Harley
 * Created: 4/30/19
 */

package harleys;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Takes in a text file and encodes it into morse code
 */
public class MorseEncoder {

    /**
     * Loads a file containing the code and symbol pairings into a LookupTable
     * @param path the path of the file
     * @return a LookupTable containing the code and symbol pairs
     * @throws IOException if there is a problem reading the file
     */
    private static LookupTable loadEncoder(Path path) throws IOException {
        LookupTable<String, String> lookupTable = new LookupTable<>();
        try(Scanner in = new Scanner(Files.newInputStream(path))) {
            while(in.hasNextLine()) {
                String code = in.nextLine();
                String symbol;
                if(code.length()>1) {
                    if (code.startsWith("\\n")) {
                        symbol = "\n";
                        code = code.substring(2);
                    } else {
                        symbol = code.substring(0, 1);
                        code = code.substring(1);
                    }
                    lookupTable.put(symbol, code);
                }
            }
        }
        return lookupTable;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a filename containing the morse code key");
        String filename = in.nextLine().trim();
        System.out.println("Enter an input filename");
        String inputFilename = in.nextLine().trim();
        System.out.println("Enter an output filename");
        String outputFilename = in.nextLine().trim();
        PrintWriter out;
        try(Scanner input = new Scanner(Files.newInputStream(Path.of(inputFilename)))) {
            out = new PrintWriter(Files.newOutputStream(Path.of(outputFilename)));
            LookupTable<String, String> lookupTable = loadEncoder(Path.of(filename));
            String line;
            String[] words;
            while(input.hasNextLine()) {
                line = input.nextLine().toUpperCase();
                if(line.isEmpty()) {
                    String code = lookupTable.get("\n");
                    if(code != null) {
                        out.write(code);
                    }
                } else {
                    words = line.split(" ");
                    String[] letters;
                    for(String word : words) {
                        if (!word.trim().isEmpty()) {
                            letters = word.split("");
                            for (String letter : letters) {
                                String code = lookupTable.get(letter);
                                if (code != null) {
                                    out.write(code + " ");
                                } else {
                                    System.out.println("Warning: skipping " + letter);
                                }
                            }
                        }
                    }
                }
                out.println();
            }
            out.close();
        } catch(IOException e) {
            System.out.println("Error loading file");
        } catch(NullPointerException e) {
            System.out.println("Error with file");
        }
    }
}