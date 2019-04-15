package com.github.dormesica.mapcontroller;

import androidx.annotation.NonNull;

/**
 * A class that describes the changes to a {@link Styleable} object's style that should be made.
 * <p>
 * The object should provide an {@code onFinish} callback, to be invoked when the operation completes.
 * This callback should be used in cases where there are some operations that should be performed after the change to
 * the style has taken place. This callback may include an empty body in case no operation is required after the update.
 *
 * @since 1.0.0
 */
public abstract class StyleEditor {

    private String id;

    /**
     * Creates a new {@code StyleEditor} for the {@link Styleable} with the given ID.
     *
     * @param id The ID of the {@link Styleable} this editor changes.
     */
    public StyleEditor(@NonNull String id) {
        this.id = id;
    }

    /**
     * A callback to be invoked when the style update completes.
     * {@code success} indicates whether the change has taken place successfully.
     *
     * @param success {@code true} if the change was successful, otherwise {@code false}.
     */
    public abstract void onFinish(boolean success);
}
