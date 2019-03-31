package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.mapcontroller.widgets.MapView;

/**
 * Interface definition for a callback to be invoked when the map is ready to be interacted with.
 *
 * @since 1.0.0
 */
public interface OnMapReadyListener {

    /**
     * Called when a the map is ready for interaction.
     *
     * @param map The map that was clicked
     */
    void onMapReady(MapView map);
}
