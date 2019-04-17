package com.github.dormesica.mapcontroller.layers;

import android.os.Parcel;
import android.os.Parcelable;
import com.github.dormesica.mapcontroller.graphics.Color;
import com.github.dormesica.mapcontroller.location.Coordinates;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
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

    public static final Parcelable.Creator<Polygon> CREATOR = new Parcelable.Creator<Polygon>() {
        @Override
        public Polygon createFromParcel(Parcel source) {
            return new Polygon(source);
        }

        @Override
        public Polygon[] newArray(int size) {
            return new Polygon[0];
        }
    };

    private List<Coordinates> perimeter;
//    private List<Polygon> holes; TODO needed?

    /**
     * Creates a new {@code Polygon} from a {@link Parcel}.
     *
     * @param source The source Parcel.
     */
    private Polygon(Parcel source) {
        super(source);

        int perimeterLength = source.readInt();
        perimeter = new ArrayList<>();
        for (int i = 0; i < perimeterLength; i++) {
            perimeter.add(source.readParcelable(Coordinates.class.getClassLoader()));
        }
    }

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
     * A class that represents the changes that can be made onto a {@link Polygon} on the map.
     *
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

        /**
         * Sets whether the polygon should contain fill color.
         *
         * @param hasFill Whether or not to contain fill color.
         * @return The {@code Polygon.Entity} for method Chaining.
         */
        public Editor hasFill(boolean hasFill) {
            this.hasFill = hasFill;
            return this;
        }

        /**
         * Sets whether or not the polygons outline should be visible.
         *
         * @param hasOutline Whether or not outline is visible.
         * @return The {@code Polygon.Entity} for method Chaining.
         */
        public Editor hasOutline(boolean hasOutline) {
            this.hasOutline = hasOutline;
            return this;
        }

        /**
         * Sets the color for the outline and it's opacity.
         *
         * @param color The color of the outline.
         * @return The {@code Polygon.Entity} for method Chaining.
         */
        public Editor setOutlineColor(Color color) {
            outlineColor = color.getColorString();
            outlineOpacity = color.alpha();
            return this;
        }

        /**
         * Sets the height of the polygon in meters.
         * <p>
         * I.e. polygons with {@code height > 0} are 3D shapes where the top face is {@code height} meters above the
         * bottom face.
         *
         * @param height The height of the polygon.
         * @return The {@code Polygon.Entity} for method Chaining.
         */
        public Editor setHeight(double height) {
            Preconditions.checkArgument(height >= 0, "Polygon height must be at least 0.");

            this.height = height;
            return this;
        }

        /**
         * Sets the width of the outline of the polygon.
         *
         * @param width The width of the polygon's outline.
         * @return The {@code Polygon.Entity} for method Chaining.
         */
        public Editor setWidth(int width) {
            Preconditions.checkArgument(width > 0, "Line Width must be greater than 0.");

            this.width = width;
            return this;
        }
    }
}
