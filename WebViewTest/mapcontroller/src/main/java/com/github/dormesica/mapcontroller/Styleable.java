package com.github.dormesica.mapcontroller;

/**
 * Interface representing a data type whose style can be edited.
 *
 * @since 1.0.0
 */
public interface Styleable {

    /**
     * Returns a {@link StyleEditor} for this {@code styleable} object.
     * <p>
     * The {@link StyleEditor} instance is used to specify the modifications that should be make on the
     * {@code Styleable} and passed to a {@link MapView} so that the changes can take place.
     *
     * @return A style editor object for the styleable.
     */
    StyleEditor edit();
}
