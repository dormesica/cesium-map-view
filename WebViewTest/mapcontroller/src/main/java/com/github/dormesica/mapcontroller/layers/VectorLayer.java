package com.github.dormesica.mapcontroller.layers;

import android.webkit.ValueCallback;

import java.util.List;

/**
 * This class represents a vector layer that has been loaded on to a
 * {@link com.github.dormesica.mapcontroller.widgets.MapView}.
 * <p>
 * Vector layers are essentially a collection of geographically pinned entities. An entity can be attached with an
 * attribute table which provides metadata on it. These attribute tables are key-value pairs stored for a specific
 * entity.
 *
 * @since 1.0.0
 */
public class VectorLayer extends Layer {

    private List<Entity> entities;
    private boolean isVisible = true;

    public Entity get(int i) {
        return entities.get(i);
    }

    @Override
    public void remove(ValueCallback<Boolean> callback) {
        // TODO implement
    }

    /**
     * Focuses the <code>MapView</code> on the layer.
     */
    public void focus() {
        // TODO implement
    }

    /**
     * Gets the isVisible of the marker.
     *
     * @return This layers's isVisible.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets the isVisible of the entity.
     */
    public void setVisible(boolean visible) {
        isVisible = visible;

        // TODO implement
    }

    /**
     * Returns the amount of entities in the layer.
     *
     * @return The amount of entities in the layer.
     */
    public int size() {
        return entities.size();
    }
}
