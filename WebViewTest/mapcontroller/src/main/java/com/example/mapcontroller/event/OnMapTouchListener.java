package com.example.mapcontroller.event;

import com.example.mapcontroller.CesiumMapView;

/**
 * Interface definition for a callback to be invoked when a location on the map
 * has been touched in some way.
 * <p>
 * A touch occurs either when a finger touches the map (<code>DOWN</code>),
 * or when a finger is lifted off the map (<code>UP</code>).
 *
 * @since 1.0.0
 */
public interface OnMapTouchListener {

    /**
     * Called when a location has been clicked.
     *
     * @param map The map that was clicked
     * @param touchEvent Additional data about the event.
     */
    void onTouch(CesiumMapView map, MapTouchEvent touchEvent);
}
