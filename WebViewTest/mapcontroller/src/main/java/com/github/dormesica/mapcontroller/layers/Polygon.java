package com.github.dormesica.mapcontroller.layers;

import com.github.dormesica.mapcontroller.location.Coordinates;

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
}
