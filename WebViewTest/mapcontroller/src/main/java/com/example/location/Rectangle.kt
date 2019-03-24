package com.example.location

/**
 * Representation of a rectangle on the earth.
 *
 * @property [northWest] The north-west corner of the rectangle.
 * @property [southEast] The south-east corner of the rectangle.
 *
 * @constructor Creates a new <code>Rectangle</code> instance.
 * @since 1.0.0
 */
data class Rectangle(
    val northWest: Coordinates,
    val southEast: Coordinates
)