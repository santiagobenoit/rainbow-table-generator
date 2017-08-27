/*
 * Rainbow Table Generator
 * By Santiago Benoit
 */
package com.santiago.rtg;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Generates a rainbow table given a character set, a minimum and maximum number of characters, and an output file.
 * @author Santiago Benoit
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Check if arguments are valid
        if (args.length != 4) {
            System.out.println("Usage: java -jar RTG.jar [character set] [minimum number of charcters] [maximum number of characters] [output file]");
            System.exit(0);
        }
        charset = args[0];
        int counter = 0;
        for (int i = 0; i < charset.length(); i++) {
            for (int j = 0; j < charset.length(); j++) {
                if (charset.charAt(i) == charset.charAt(j)) {
                    counter++;
                }
            }
        }
        if (counter > charset.length()) {
            System.out.println("Character set has duplicate characters!");
            System.exit(0);
        }
        try {
            minchars = Integer.parseInt(args[1]);
            maxchars = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Minimum and maximum values must be integers!");
            System.exit(0);
        }
        if (maxchars < minchars) {
            System.out.println("Maximum cannot be smaller than minimum!");
            System.exit(0);
        }
        File file = new File(args[3]);
        if (file.exists()) {
            System.out.println("File already exists. Are you sure you want to overwrite it? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            if (!scanner.next().equalsIgnoreCase("Y")) {
                System.out.println("Existing file will not be overwritten.");
                System.exit(0);
            }
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating file. Check file path and try again.");
            System.exit(0);
        }
        
        // Generate rainbow table
        try {
            writer = new PrintWriter(file);
            for (int i = minchars; i <= maxchars; i++) {
                generateTable(i, "");
            }
            writer.close();
            System.out.println("Generation complete.");
        } catch (IOException e) {
            System.out.println("Error writing to file. Check file path and try again.");
            System.exit(0);
        }
    }
    
    public static void generateTable(int depth, String stringSoFar) {
        if (depth == 0) {
            writer.println(stringSoFar);
            return;
        }
        for (int i = 0; i < charset.length(); i++) {
            generateTable(depth - 1, stringSoFar + charset.charAt(i));
        }
    }
    
    // Variable declarations
    public static String charset, filepath;
    public static int minchars, maxchars;
    public static PrintWriter writer;
}
