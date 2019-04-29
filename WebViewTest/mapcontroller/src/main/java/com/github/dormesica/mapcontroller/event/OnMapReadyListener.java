package com.github.dormesica.mapcontroller.event;

import androidx.annotation.NonNull;
import com.github.dormesica.mapcontroller.MapView;

/**
 * Interface definition for a callback to be invoked when the map is ready to be interacted with.
 *
 * @since 1.0.0
 */
public interface OnMapReadyListener {

    /**
     * Called when a the map is ready to be used.
     * <p>
     * Note that this does not guarantee that map is visible.
     *
     * @param map The map that was clicked
     */
    void onMapReady(@NonNull MapView map);
}
