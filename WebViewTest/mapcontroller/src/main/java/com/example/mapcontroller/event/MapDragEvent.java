package com.example.mapcontroller.event;

import com.example.location.Coordinates;

/**
 * Holds a bundle of data that is passed to a drag event callback function.
 *
 * @since 1.0.0
 */
public class MapDragEvent {

    private Coordinates startLocation;
    private Coordinates endLocation;

    /**
     * Returns the start location of the drag gesture.
     *
     * @return The start location of the drag.
     */
    public Coordinates getStartLocation() {
        return startLocation;
    }

    /**
     * Returns the end location of the drag gesture.
     *
     * @return The end location of the drag.
     */
    public Coordinates getEndLocation() {
        return endLocation;
    }

}
