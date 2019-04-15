package com.github.dormesica.mapcontroller.layers;

import com.github.dormesica.mapcontroller.StyleEditor;
import com.github.dormesica.mapcontroller.Styleable;
import com.github.dormesica.mapcontroller.graphics.Color;

/**
 * This class represents a basic entity in a vector layer.
 * <p>
 * An entity can be a {@link Point}, a {@link Line} or a {@link Polygon}.
 *
 * @since 1.0.0
 */
public abstract class Entity implements Styleable {

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
     * Sets the name of the entity.
     *
     * @param name The new name for the entity.
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * A class that represents the changes that should be made to an {@link Entity} on the map.
     *
     * @since 1.0.0
     */
    public abstract class Editor extends StyleEditor {
        private Boolean isVisible;
        private String color;
        private double opacity;

        /**
         * Creates a new {@code Entity.Editor} object.
         */
        protected Editor() {
            super(id);

            isVisible = null;
            String color = null;
            opacity = -1;
        }

        /**
         * Sets whether the entity should be visible after the update.
         *
         * @param visible The visibility of the entity.
         * @return The {@code Entity.Editor} for method chaining.
         */
        public Editor setVisibility(boolean visible) {
            this.isVisible = visible;
            return this;
        }

        /**
         * Sets the main color of the entity and its opacity.
         *
         * @param color The main color of the entity.
         * @return The {@code Entity.Editor} for method chaining.
         */
        public Editor setColor(Color color) {
            this.color = color.getColorString();
            this.opacity = color.alpha();
            return this;
        }

        @Override
        public void onFinish(boolean success) {
            if (success) {
                Entity.this.isVisible = this.isVisible;
            }
        }
    }
}
