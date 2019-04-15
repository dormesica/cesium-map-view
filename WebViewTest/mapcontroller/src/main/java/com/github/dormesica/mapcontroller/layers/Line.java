package com.github.dormesica.mapcontroller.layers;

import com.github.dormesica.mapcontroller.StyleEditor;
import com.github.dormesica.mapcontroller.location.Coordinates;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * This class represents a line on the map.
 * <p>
 * A line is represented by the path it follows. Between every 2 following sets of coordinates on the path
 * a straight line is drawn.
 *
 * @since 1.0.0
 */
public class Line extends Entity {

    private List<Coordinates> path;

    /**
     * Returns the i-th point along the line's path.
     *
     * @param i The required point index.
     * @return The i-th point along the line's path.
     */
    public Coordinates getPointAt(int i) {
        return path.get(i);
    }

    /**
     * Returns the length of the line in meters.
     *
     * @return The length of the line.
     */
    public double length() {
        double length = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            length += Coordinates.distanceBetween(path.get(i), path.get(i + 1));
        }

        return length;
    }

    /**
     * Return the number of points along the path.
     *
     * @return The number of point along the path.
     */
    public int size() {
        return path.size();
    }

    @Override
    public StyleEditor edit() {
        return new Editor();
    }

    /**
     * A class that represents the changes that can be made onto a {@link Line} on the map.
     *
     * @since 1.0.0
     */
    public class Editor extends Entity.Editor {
        private Integer width;

        /**
         * Creates a new {@code Line.Editor} object.
         */
        protected Editor() {
            super();
        }

        /**
         * Sets the width of the line.
         *
         * @param width The width of the line.
         * @return The {@code Point.Entity} for method Chaining.
         */
        public Editor setWidth(int width) {
            Preconditions.checkArgument(width > 0, "Line Width must be greater than 0.");

            this.width = width;
            return this;
        }
    }
}
