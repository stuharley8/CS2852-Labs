/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 1 - Dot 2 Dot Generator
 * Name: Stuart Harley
 * Created: 3/5/19
 */

package harleys;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Holds a list of Dots that describe a picture
 */
public class Picture {

    /**
     * List the holds the dots that represent the picture
     */
    private List<Dot> dots;

    private static final double DOT_WIDTH = 6;

    /**
     * Constructor that uses the list emptyList to store the dots for the picture
     * @param emptyList list that the dots will be stored in
     */
    public Picture(List<Dot> emptyList) {
        dots = emptyList;
    }

    /**
     * Constructor the copies the dots from original into emptyList and uses it
     * to store the dots for this picture
     * @param original picture to have dots copied from
     * @param emptyList list to store the dots copied
     */
    public Picture(Picture original, List<Dot> emptyList) {
        emptyList.addAll(original.dots);
        dots = emptyList;
    }

    /**
     * Returns the number of dots in the List dots
     * @return the number of dots
     */
    public int getNumDots() {
        return dots.size();
    }

    /**
     * Loads all of the dots from a .dot file
     * @param path the path of the file
     * @throws IOException if there is a problem with the path
     */
    public void load(Path path) throws IOException {
        try(Scanner in = new Scanner(Files.newInputStream(path))) {
            dots.clear();
            while(in.hasNextLine()) {
                String[] values = in.nextLine().split(",");
                double x = Double.parseDouble(values[0].trim());
                double y = Double.parseDouble(values[1].trim());
                if((x >= 0 && x <= 1) && (y >= 0 && y <= 1)) {
                    dots.add(new Dot(x, y));
                }
            }
        }
    }

    /**
     * Draws each dot in the picture onto the canvas
     * @param canvas the canvas to be draw upon
     */
    public void drawDots(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        double magnificationValue = canvas.getHeight();
        if(magnificationValue > canvas.getWidth()) {
            magnificationValue = canvas.getWidth();
        }
        for (Dot dot : dots) {
            graphicsContext.fillOval(dot.getX() * magnificationValue - DOT_WIDTH / 2,
                    Math.abs(dot.getY() * magnificationValue - canvas.getHeight()) - DOT_WIDTH / 2,
                    DOT_WIDTH, DOT_WIDTH);
        }
    }

    /**
     * Draws lines between all neighboring dots in the picture on the canvas
     * @param canvas the canvas to be draw upon
     */
    public void drawLines(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        double magnificationValue = canvas.getHeight();
        if(magnificationValue > canvas.getWidth()) {
            magnificationValue = canvas.getWidth();
        }
        if(dots.size()>0) {
            graphicsContext.beginPath();
            graphicsContext.moveTo(dots.get(0).getX() * magnificationValue,
                    Math.abs(dots.get(0).getY() * magnificationValue - canvas.getHeight()));
            for (int i = 1; i < dots.size(); i++) {
                graphicsContext.lineTo(dots.get(i).getX() * magnificationValue,
                        Math.abs(dots.get(i).getY() * magnificationValue - canvas.getHeight()));
            }
            graphicsContext.lineTo(dots.get(0).getX() * magnificationValue,
                    Math.abs(dots.get(0).getY() * magnificationValue - canvas.getHeight()));
            graphicsContext.closePath();
            graphicsContext.stroke();
        }
    }

    /**
     * Saves the picture to a .dot path
     * @param path the path
     * @throws IOException if there is a problem saving the file
     */
    public void save(Path path) throws IOException {
        try(PrintWriter out = new PrintWriter(Files.newOutputStream(path))) {
            for(Dot dot : dots) {
                out.println(dot.getX() + "," + dot.getY());
            }
        }
    }

    /**
     * Removes all but the numDesired most critical dots.
     * If the picture does not have more than numDesired dots, the methods returns
     * without changing the picture.
     * If numDesired < 3, and IllegalArgumentException is thrown.
     * @param numDesired the desired number of dots
     * @throws IllegalArgumentException if numDesired < 3
     */
    public void removeDots(int numDesired) throws IllegalArgumentException {
        if(numDesired < 3) {
            throw new IllegalArgumentException("NumDesired less than 3");
        }
        while(numDesired < dots.size()) {
            double lowCrit = dots.get(0).calculateCriticalValue(dots.get(dots.size()-1), dots.get(1));
            int indexOfLowCrit = 0;
            for(int i = 1; i<dots.size()-1; i++) {
                if(dots.get(i).calculateCriticalValue(dots.get(i+1), dots.get(i-1)) < lowCrit) {
                    lowCrit = dots.get(i).calculateCriticalValue(dots.get(i+1), dots.get(i-1));
                    indexOfLowCrit = i;
                }
            }
            if(dots.get(dots.size()-1).calculateCriticalValue(dots.get(dots.size()-2), dots.get(0)) < lowCrit) {
                indexOfLowCrit = dots.size()-1;
            }
            dots.remove(indexOfLowCrit);
        }
    }
}
