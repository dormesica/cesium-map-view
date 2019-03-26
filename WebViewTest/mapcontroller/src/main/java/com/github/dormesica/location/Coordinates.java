package com.github.dormesica.location;

import androidx.annotation.NonNull;

/**
 * Representation of a geographic location.
 *
 * @since 1.0.0
 */
public class Coordinates {

    private final double lon;
    private final double lat;
    private final double alt;

    /**
     * Creates a new <code>Coordinates</code> instance.
     *
     * The altitude is a assigned a default value of 0.
     *
     * @param lon The longitude value.
     * @param lat The latitude value.
     */
    public Coordinates(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
        this.alt = 0;
    }

    /**
     * Creates a new <code>Coordinates</code> instance.
     *
     * @param lon The longitude value.
     * @param lat The latitude value.
     * @param alt The altitude value.
     */
    public Coordinates(double lon, double lat, double alt) {
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
    }

    /**
     * Returns the longitude of the coordinates set.
     * <p>
     * Longitude values are <code>double</code> values between -180 and 180.
     *
     * @return Longitude value.
     */
    public double getLon() {
        return lon;
    }

    /**
     * Returns the latitude of the coordinates set.
     * <p>
     * Latitude values are <code>double</code> values between -90 and 90.
     *
     * @return Latitude value.
     */
    public double getLat() {
        return lat;
    }

    /**
     * Returns the height of the coordinates set.
     *
     * @return Altitude value.
     */
    public double getAlt() {
        return alt;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Coordinates(%s, %s, %s)", lon, lat, lat);
    }
}
