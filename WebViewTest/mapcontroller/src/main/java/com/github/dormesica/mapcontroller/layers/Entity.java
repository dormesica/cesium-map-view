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
    private Properties properties;

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
     * Get the isVisible of the marker.
     *
     * @return This entity's isVisible.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Return a {@link Properties} object with the metadata associated with the entity.
     *
     * @return The properties of the entity.
     */
    public Properties getProperties() {
        return this.properties;
    }
}
