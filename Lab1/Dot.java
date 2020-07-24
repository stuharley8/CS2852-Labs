/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 1 - Dot 2 Dot Generator
 * Name: Stuart Harley
 * Created: 3/5/19
 */

package harleys;

/**
 * Represents a dot in the picture
 */
public class Dot {

    private final double x;

    private final double y;

    /**
     * Constructor for a dot
     * @param x the x-coordinate of the dot
     * @param y the y-coordinate of the dot
     */
    public Dot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
