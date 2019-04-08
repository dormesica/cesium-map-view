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

    /**
     * Get the <code>i</code>-th entity of the layer.
     *
     * @param i Entity index.
     * @return The <code>i</code>-th entity.
     * @throws IndexOutOfBoundsException In case the index if greater than or equal to <code>VectorLayer.size()</code>.
     */
    public Entity get(int i) throws IndexOutOfBoundsException {
        if (i >= entities.size()) {
            throw new IndexOutOfBoundsException();
        }

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
