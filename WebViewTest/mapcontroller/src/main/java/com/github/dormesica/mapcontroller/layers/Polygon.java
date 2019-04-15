package com.github.dormesica.mapcontroller.layers;

import com.github.dormesica.mapcontroller.graphics.Color;
import com.github.dormesica.mapcontroller.location.Coordinates;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * This class represents a polygon on the map.
 * <p>
 * A polygon is represented by the set of coordinates on its perimeter in a clockwise order. The last set of
 * coordinates must equal the first (closing the polygon).
 *
 * @since 1.0.0
 */
public class Polygon extends Entity {

    private List<Coordinates> perimeter;
//    private List<Polygon> holes; TODO needed?

    /**
     * Calculates the perimeter of the polygon in meters. I.e. the total length of the line the surrounds the polygon.
     *
     * @return The perimeter of the polygon.
     */
    public double perimeter() {
        double length = 0;

        for (int i = 0; i < perimeter.size() - 1; i++) {
            length += Coordinates.distanceBetween(perimeter.get(i), perimeter.get(i + 1));
        }

        return length;
    }

    /**
     * Calculates the are of the polygon in squared meters.
     *
     * @return The area of the polygon.
     */
    public double area() {
        // TODO implement
        return 0;
    }

    @Override
    public Editor edit() {
        return new Editor();
    }

    /**
     * @since 1.0.0
     */
    public class Editor extends Entity.Editor {
        private Boolean hasFill;
        private Boolean hasOutline;
        private String outlineColor;
        private double outlineOpacity;
        private Double height;
        private Integer width;

        /**
         * Creates a new {@code Polygon.Editor} object.
         */
        protected Editor() {
            super();

            hasFill = null;
            hasOutline = null;
            outlineColor = null;
            outlineOpacity = -1;
            height = null;
            width = null;
        }

        public Editor hasFill(boolean hasFill) {
            this.hasFill = hasFill;
            return this;
        }

        public Editor hasOutline(boolean hasOutline) {
            this.hasOutline = hasOutline;
            return this;
        }

        public Editor setOutlineColor(Color color) {
            outlineColor = color.getColorString();
            outlineOpacity = color.alpha();
            return this;
        }

        public Editor setHeight(double height) {
            Preconditions.checkArgument(height >= 0, "Polygon height must be at least 0.");

            this.height = height;
            return this;
        }

        public Editor setWidth(int width) {
            Preconditions.checkArgument(width > 0, "Line Width must be greater than 0.");

            this.width = width;
            return this;
        }
    }
}
