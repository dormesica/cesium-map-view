package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.mapcontroller.MapView;

/**
 * Interface definition for a callback to be invoked when a location on the map
 * has been clicked and held.
 *
 * @since 1.0.0
 */
public interface OnMapLongClickListener {

    /**
     * Called when a location has been clicked and held.
     *
     * @param map The map that was clicked
     * @param clickEvent Additional data about the event.
     */
    void onLongClick(MapView map, MapClickEvent clickEvent);
}
