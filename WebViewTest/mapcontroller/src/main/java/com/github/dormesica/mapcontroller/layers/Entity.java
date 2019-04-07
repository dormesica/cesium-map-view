package com.github.dormesica.mapcontroller.layers;

/**
 * This class represents a basic entity in a vector layer.
 * <p>
 * An entity can be a {@link Point}, a {@link Line} or a {@link Polygon}.
 *
 * @since 1.0.0
 */
public abstract class Entity {

    private String id;
    private String name;
    private boolean isVisible;
    // TODO add properties

    /**
     * Get the ID of the entity.
     *
     * @return The entity's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the name of the entity.
     *
     * @return The entity's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the entity.
     *
     * @param name The new entity name.
     */
    public void setName(String name) {
        this.name = name;
        // TODO change name in Cesium?
    }

    /**
     * Focuses the map on the entity.
     */
    public void focus() {
        // TODO implement
    }

    /**
     * Get the isVisible of the marker.
     *
     * @return This entity's isVisible.
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
     * Removes the entity from the map.
     */
    public void remove() {}

    /**
     * Set the color for the entity.
     *
     * @param color The color of the entity.
     */
    public void setColor(String color) {}

    /**
     * Set the opacity of the entity.
     * <p>
     * Opacity values are between 0 and 1, where 0 means the entity is transparent and 1 means opaque.
     *
     * @param alpha The opacity value.
     */
    public void setAlpha(double alpha) {}
}
