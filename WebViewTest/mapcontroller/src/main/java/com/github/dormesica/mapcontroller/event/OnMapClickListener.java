package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.mapcontroller.widgets.CesiumMapView;

/**
 * Interface definition for a callback to be invoked when a location on the map
 * has been clicked.
 *
 * @since 1.0.0
 */
public interface OnMapClickListener {

    /**
     * Called when a location has been clicked.
     *
     * @param map The map that was clicked
     * @param clickEvent Additional data about the event.
     */
    void onClick(CesiumMapView map, MapClickEvent clickEvent);
}
