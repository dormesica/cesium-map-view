package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.location.Coordinates;

/**
 * Holds a bundle of data that is passed to a touch event callback function.
 *
 * @since 1.0.0
 */
public class MapTouchEvent {

    /** Constant that indicates a press gesture has started. */
    public static final int ACTION_DOWN = 0;
    /** Constant that indicates a press gesture has ended. */
    public static final int ACTION_UP = 1;

    private Coordinates location;
    private int type;

    /**
     * Return the geographic location of the touch gesture.
     *
     * @return The coordinates where the touch occurred.
     */
    public Coordinates getLocation() {
        return location;
    }

    /**
     * Returns the type of the touch gesture.
     *
     * @return The type of the touch gesture.
     */
    public int getType() {
        return type;
    }
}
