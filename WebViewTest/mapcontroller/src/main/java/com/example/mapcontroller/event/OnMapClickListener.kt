package com.example.mapcontroller.event

import com.example.mapcontroller.CesiumMapView

/**
 * Interface definition for a callback to be invoked when a location on the map
 * has been clicked.
 *
 * @since 1.0.0
 */
interface OnMapClickListener {

    /**
     * Called when a location has been clicked.
     *
     * @param [map] The map that was clicked
     * @param [clickEvent] Additional data about the event.
     */
    fun onMapClick(map: CesiumMapView, clickEvent: MapClickEvent)
}
