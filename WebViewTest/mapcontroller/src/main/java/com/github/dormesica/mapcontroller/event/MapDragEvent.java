package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.mapcontroller.location.Coordinates;

/**
 * Holds a bundle of data that is passed to a drag event callback function.
 *
 * @since 1.0.0
 */
public class MapDragEvent {

    private Coordinates startLocation;
    private Coordinates endLocation;

    /**
     * Returns the geographic location where the gesture started.
     *
     * @return The start location of the drag.
     */
    public Coordinates getStartLocation() {
        return startLocation;
    }

    /**
     * Returns the geographic location where the gesture ended.
     *
     * @return The end location of the drag.
     */
    public Coordinates getEndLocation() {
        return endLocation;
    }
}
