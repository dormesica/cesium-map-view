package com.github.dormesica.mapcontroller.layers;

import androidx.annotation.NonNull;
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
     * A format for the SVG of the default point icon.
     * <p>
     * Contains 2 open slots for string values, the first being the outline color and the second being the fill color.
     */
    public static final String DEFAULT_POINT_ICON_FORMAT = "data:image/svg+xml," +
            "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" " +
            "xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"20px\" height=\"20px\" " +
            "xml:space=\"preserve\">" +
            "<circle cx=\"10\" cy=\"10\" r=\"9\" stroke=\"%s\" stroke-width=\"3\" fill=\"%s\" />" +
            "</svg>";
    /**
     * The default SVG for the point icon.
     */
    public static final String DEFAULT_POINT_ICON = String.format(DEFAULT_POINT_ICON_FORMAT, "black", "white");

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
