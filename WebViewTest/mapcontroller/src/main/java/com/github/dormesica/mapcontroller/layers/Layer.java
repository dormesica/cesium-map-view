package com.github.dormesica.mapcontroller.layers;

import com.github.dormesica.mapcontroller.MapView;

/**
 * This class represents a layer that was loaded on to a {@link MapView}.
 * <p>
 * Each layer is associated with a unique UUID.
 * <p>
 * <strong>Important:</strong> <code>Layer</code> subclasses represents layer that have been added to the map and
 * therefore cannot be created directly.
 *
 * @since 1.0.0
 */
public abstract class Layer {

    private String id;

    /**
     * Get the ID of the layer.
     *
     * @return The layer's ID.
     */
    public String getId() {
        return this.id;
    }
}
