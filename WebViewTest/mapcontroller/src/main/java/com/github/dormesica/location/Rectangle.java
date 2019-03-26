package com.github.dormesica.location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Representation of a geographic rectangle.
 * <p>
 * The rectangle is represented by it's north-west and south-east corners (corresponding to top-left and bottom-right
 * corners respectively).
 * <p>
 * The north and south are latitude values. The east and west are longitude values.
 *
 * @since 1.0.0
 */
public class Rectangle {

    private Coordinates northWest;
    private Coordinates southEast;

    /**
     * Creates a new <code>Rectangle</code> instance.
     *
     * @param northWest The north-west (top-left) corner.
     * @param southEast The south-east (bottom-right) corner.
     */
    public Rectangle(Coordinates northWest, Coordinates southEast) {
        this.northWest = northWest;
        this.southEast = southEast;
    }

    /**
     * Creates a new <code>Rectangle</code> instance.
     *
     * @param north The north latitude of the rectangle.
     * @param west The west longitude of the rectangle.
     * @param south The south latitude of the rectangle.
     * @param east The east longitude of the rectangle.
     */
    public Rectangle(double north, double west, double south, double east) {
        this.northWest = new Coordinates(west, north);
        this.southEast = new Coordinates(east, south);
    }

    /**
     * Returns the north-west (top-left) corner of the rectangle.
     *
     * @return The north-west (top-left) corner of the rectangle.
     */
    public Coordinates getNorthWest() {
        return northWest;
    }

    /**
     * Returns the south-east (bottom-right) corner of the rectangle.
     *
     * @return The south-east (bottom-right) corner of the rectangle.
     */
    public Coordinates getSouthEast() {
        return southEast;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Rectangle other = (Rectangle) obj;
        return northWest.equals(other.northWest) && southEast.equals(other.southEast);
    }

    @Override
    @NonNull
    public String toString() {
        return String.format("Rectangle(%s, %s)", northWest, southEast);
    }
}
