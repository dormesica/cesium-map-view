package com.example.mapcontroller.event;

import com.example.mapcontroller.CesiumMapView;

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
    void onMapClick(CesiumMapView map, MapClickEvent clickEvent);
}
