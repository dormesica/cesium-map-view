package com.github.dormesica.mapcontroller.layers;

import android.webkit.ValueCallback;
import androidx.annotation.Nullable;

/**
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

    /**
     * Asynchronously removes a layer from the map on which it was loaded.
     * <code>callback</code> is invoked when the operation completes with a boolean value that indicates whether
     * the operation was successful.
     *
     * @param callback Called when the layer is removed or upon failure.
     */
    public abstract void remove(@Nullable ValueCallback<Boolean> callback);
}
