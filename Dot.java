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

    /**
     * X coordinate of the Dot
     */
    private final double x;

    /**
     * Y coordinate of the Dot
     */
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

    /**
     * Calculates the distance between 2 dots
     * @param head the first dot
     * @param tail the second dot
     * @return the distance between the dots
     */
    private static double calculateDistance(Dot head, Dot tail) {
        return Math.sqrt(Math.pow(head.getX()-tail.getX(), 2) +
                Math.pow(head.getY()-tail.getY(), 2));
    }

    /**
     * Calculates the critical value for the dot based on the neighboring dots that are passed in
     * @param previous the previous neighboring dot
     * @param next the next neighboring dot
     * @return the critical value of the current dot
     */
    public double calculateCriticalValue(Dot previous, Dot next) {
        double distance12 = calculateDistance(previous, this);
        double distance23 = calculateDistance(this, next);
        double distance13 = calculateDistance(previous, next);
        return (distance12 + distance23 - distance13);
    }
}
