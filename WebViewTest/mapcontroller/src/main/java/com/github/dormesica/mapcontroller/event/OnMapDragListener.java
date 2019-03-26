package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.mapcontroller.widgets.CesiumMapView;

/**
 * Interface definition for a callback to be invoked when the map has been dragged.
 *
 * @since 1.0.0
 */
public interface OnMapDragListener {

    /**
     * Called when a location has been clicked.
     *
     * @param map The map that was clicked
     * @param dragEvent Additional data about the event.
     */
    void onDrag(CesiumMapView map, MapDragEvent dragEvent);
}
