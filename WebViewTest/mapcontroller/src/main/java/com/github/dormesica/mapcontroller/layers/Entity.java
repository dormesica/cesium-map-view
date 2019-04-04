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
    private boolean show;

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
     * Show the entity on the map.
     */
    public void show() {
        show = true;

        // TODO implement
    }

    /**
     * Hides the entity from view.
     */
    public void hide() {
        show = false;

        // TODO implement
    }
}
