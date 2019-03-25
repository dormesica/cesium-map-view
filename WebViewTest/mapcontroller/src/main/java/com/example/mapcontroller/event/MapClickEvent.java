package com.example.mapcontroller.event;

import com.example.location.Coordinates;

/**
 * Holds a bundle of data that is passed to a click event callback function.
 *
 * @since 1.0.0
 */
public class MapClickEvent {

    private Coordinates location;

    /**
     * Returns the location at which the click event occurred.
     *
     * @return The coordinates of the click event.
     */
    public Coordinates getLocation() {
        return location;
    }
}
