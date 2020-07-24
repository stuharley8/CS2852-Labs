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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Holds a list of Dots that describe a picture
 */
public class Picture {

    private List<Dot> dots;

    private static final double DOT_WIDTH = 6;

    /**
     * Constructor for picture
     */
    public Picture() {
        dots = new ArrayList<>();
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
                if(!(x >= 0 && x <= 1) && !(y >= 0 && y <= 1)) {
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
}
