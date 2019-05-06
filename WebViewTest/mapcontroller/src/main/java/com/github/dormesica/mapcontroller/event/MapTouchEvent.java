package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.mapcontroller.layers.Entity;
import com.github.dormesica.mapcontroller.location.Coordinates;

/**
 * Holds a bundle of data that is passed to a touch event callback function.
 *
 * @since 1.0.0
 */
public class MapTouchEvent extends StaticMapEvent {

    /** Constant that indicates a press gesture has started. */
    public static final int ACTION_DOWN = 0;
    /** Constant that indicates a press gesture has ended. */
    public static final int ACTION_UP = 1;

    private Coordinates mLocation;
    private int mType;

    /**
     * Create a new {@code MapTouchEvent} object.
     *
     * @param type The type of the touch.
     * @param location The coordinates that were clicked.
     * @param entities The entities that were clicked.
     */
    public MapTouchEvent(int type, Coordinates location, Entity[] entities) {
        super(entities);

        mType = type;
        mLocation = location;
    }

    /**
     * Return the geographic location of the touch gesture.
     *
     * @return The coordinates where the touch occurred.
     */
    public Coordinates getLocation() {
        return mLocation;
    }

    /**
     * Returns the type of the touch gesture.
     *
     * @return The type of the touch gesture.
     */
    public int getType() {
        return mType;
    }
}
