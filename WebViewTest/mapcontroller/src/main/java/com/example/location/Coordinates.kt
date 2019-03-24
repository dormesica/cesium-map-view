package com.example.location

/**
 * Representation of a geographic location.
 *
 * @property [lon] The longitude.
 * @property [lat] The latitude.
 * @property [alt] the height, defaults to 0.
 *
 * @constructor Creates new <code>Coordinates</code> instance.
 * @since 1.0.0
 */
data class Coordinates(
    val lon: Double,
    val lat: Double,
    val alt: Double = 0.0
)
