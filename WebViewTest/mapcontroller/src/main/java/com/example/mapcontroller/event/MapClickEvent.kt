package com.example.mapcontroller.event

import com.example.location.Coordinates

/**
 * Holds a bundle of data that is passed to a click event callback function.
 *
 * @property [location] The location where the click occurred.
 *
 * @since 1.0.0
 */
data class MapClickEvent(val location: Coordinates)
