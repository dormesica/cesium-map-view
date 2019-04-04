package com.github.dormesica.mapcontroller.location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Representation of a geographic location.
 *
 * @since 1.0.0
 */
public class Coordinates {

    /**
     * Calculates the distance between the 2 given points.
     *
     * @param from The origin location.
     * @param to   The destination.
     * @return The distance between from and to.
     */
    public static double distanceBetween(Coordinates from, Coordinates to) {
        return from.distance(to);
    }

    /**
     * Calculates the azimuth to the given point in degrees.
     * <p>
     * Azimuth is the angle between the a line from the current location to <code>to</code> and the north (a line from
     * the current location to the north pole where the meridians meet).
     *
     * @param from The origin location.
     * @param to   The destination.
     * @return The azimuth between from and to.
     */
    public static double azimuthBetween(Coordinates from, Coordinates to) {
        return from.azimuth(to);
    }

    private final double lon;
    private final double lat;
    private final double alt;

    /**
     * Creates a new <code>Coordinates</code> instance.
     * <p>
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

    /**
     * Calculates the distance to the given point in meters.
     *
     * @param to The point to which the distance is calculated
     * @return The distance to to
     */
    public double distance(Coordinates to) {
        // TODO implement
        return 0;
    }

    /**
     * Calculates the azimuth to the given point in degrees.
     * <p>
     * Azimuth is the angle between the a line from the current location to <code>to</code> and the north (a line from
     * the current location to the north pole where the meridians meet).
     *
     * @param to The location to which the azimuth is calculated.
     * @return The azimuth to to
     */
    public double azimuth(Coordinates to) {
        // TODO implement
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Coordinates other = (Coordinates) obj;
        return lon == other.lon && lat == other.lat && alt == other.alt;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Coordinates(%s, %s, %s)", lon, lat, lat);
    }
}
