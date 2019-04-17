package com.github.dormesica.mapcontroller.layers;

import android.os.Parcel;
import android.os.Parcelable;
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
public abstract class Layer implements Parcelable {

    private String id;

    /**
     * Creates a new {@code Layer} object with default values.
     */
    protected Layer() {

    }

    /**
     * Creates a new {@code Layer} object from a {@link Parcel}.
     *
     * @param source The source Parcel.
     */
    protected Layer(Parcel source) {
        id = source.readString();
    }

    /**
     * Get the ID of the layer.
     *
     * @return The layer's ID.
     */
    public String getId() {
        return this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}
