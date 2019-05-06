package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.mapcontroller.layers.Entity;
import com.github.dormesica.mapcontroller.location.Coordinates;

/**
 * Holds a bundle of data that is passed to a click event callback function.
 *
 * @since 1.0.0
 */
public class MapClickEvent extends StaticMapEvent {

    private Coordinates mLocation;

    /**
     * Create a new {@code MapClickEvent} object.
     *
     * @param location The coordinates that were clicked.
     * @param entities The entities that were clicked.
     */
    public MapClickEvent(Coordinates location, Entity[] entities) {
        super(entities);

        mLocation = location;
    }

    /**
     * Returns the geographic location at which the click event occurred.
     *
     * @return The coordinates where the click occurred.
     */
    public Coordinates getLocation() {
        return mLocation;
    }
}
