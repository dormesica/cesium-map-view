package com.github.dormesica.mapcontroller.layers;

import android.os.Parcel;
import android.os.Parcelable;
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
public abstract class Entity implements Styleable, Parcelable {

    private String id;
    private String name;
    private boolean isVisible;
    private Properties properties;

    /**
     * Creates a new {@code Entity} object with default values.
     */
    protected Entity() {
    }

    /**
     * Creates a new {@code Entity} from a {@link Parcel}.
     *
     * @param source The source Parcel.
     */
    protected Entity(Parcel source) {
        id = source.readString();
        name = source.readString();
        isVisible = source.readByte() != 0;
        properties = source.readParcelable(Properties.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (isVisible ? 1 : 0));
        dest.writeParcelable(properties, flags);
    }

    /**
     * An abstract class that represents the changes that can be made onto any {@link Entity} on the map.
     *
     * @since 1.0.0
     */
    public static abstract class Editor extends StyleEditor {
        private Boolean isVisible = null;
        private String color = null;
        private double opacity = Double.NaN;

        /**
         * Creates a new {@code Entity.Editor} object.
         */
        protected Editor(String id) {
            super(id);
        }

        /**
         * Creates a new {@code Editor} from a {@link Parcel}.
         *
         * @param source The source Parcel.
         */
        protected Editor(Parcel source) {
            super(source);

            int visibility = source.readInt();
            isVisible = visibility == -1 ? null : visibility != 0;
            color = source.readString();
            opacity = source.readDouble();
        }

        /**
         * Sets whether the entity should be visible after the update.
         *
         * @param visible The visibility of the entity.
         * @return The {@code Entity.Editor} for method chaining.
         */
        public Editor setVisibility(boolean visible) {
            isVisible = visible;
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

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            if (isVisible == null) {
                dest.writeInt(-1);
            } else {
                dest.writeInt(isVisible ? 1 : 0);
            }
            dest.writeString(color);
            dest.writeDouble(opacity);
        }
    }
}
