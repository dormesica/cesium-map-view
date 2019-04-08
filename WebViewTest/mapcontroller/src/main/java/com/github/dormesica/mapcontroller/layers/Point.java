package com.github.dormesica.mapcontroller.layers;

import com.github.dormesica.mapcontroller.location.Coordinates;

/**
 * This class represents a point on the map.
 * <p>
 * A point is represented by the set of coordinates of it's location.
 *
 * @since 1.0.0
 */
public class Point extends Entity {

    /**
     * The default icon for point.
     * <p>
     * Shapes as a circle with black stroke color and white fill.
     */
    public static final String DEFAULT_POINT_ICON = "data:image/svg+xml," +
            "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" " +
            "xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"20px\" height=\"20px\" " +
            "xml:space=\"preserve\">" +
            "<circle cx=\"10\" cy=\"10\" r=\"9\" stroke=\"black\" stroke-width=\"3\" fill=\"white\" />" +
            "</svg>";

    private Coordinates location;

    /**
     * Get the Coordinates of the point.
     *
     * @return The coordinates of the point.
     */
    public Coordinates getLocation() {
        return location;
    }
}
