package com.github.dormesica.mapcontroller.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Representation of a geographic rectangle.
 * <p>
 * The rectangle is represented by it's north-west and south-east corners (corresponding to top-left and bottom-right
 * corners respectively).
 * <p>
 * The north and south are latitude values. The east and west are longitude values.
 *
 * @since 1.0.0
 */
public class Rectangle implements Parcelable {

    public static final Parcelable.Creator<Rectangle> CREATOR = new Parcelable.Creator<Rectangle>() {
        @Override
        public Rectangle createFromParcel(Parcel source) {
            return new Rectangle(source);
        }

        @Override
        public Rectangle[] newArray(int size) {
            return new Rectangle[0];
        }
    };

    private Coordinates northWest;
    private Coordinates southEast;

    /**
     * Creates a new <code>Rectangle</code> instance.
     *
     * @param northWest The north-west (top-left) corner.
     * @param southEast The south-east (bottom-right) corner.
     */
    public Rectangle(Coordinates northWest, Coordinates southEast) {
        this.northWest = northWest;
        this.southEast = southEast;
    }

    /**
     * Creates a new <code>Rectangle</code> instance.
     *
     * @param north The north latitude of the rectangle.
     * @param west The west longitude of the rectangle.
     * @param south The south latitude of the rectangle.
     * @param east The east longitude of the rectangle.
     */
    public Rectangle(double north, double west, double south, double east) {
        this.northWest = new Coordinates(west, north);
        this.southEast = new Coordinates(east, south);
    }

    /**
     * Creates a new {@code Rectangle} object from a {@link Parcel}.
     * @param source The source Parcel.
     */
    private Rectangle(Parcel source) {
        northWest = source.readParcelable(Coordinates.class.getClassLoader());
        southEast = source.readParcelable(Coordinates.class.getClassLoader());
    }

    /**
     * Returns the north-west (top-left) corner of the rectangle.
     *
     * @return The north-west (top-left) corner of the rectangle.
     */
    public Coordinates getNorthWest() {
        return northWest;
    }

    /**
     * Returns the south-east (bottom-right) corner of the rectangle.
     *
     * @return The south-east (bottom-right) corner of the rectangle.
     */
    public Coordinates getSouthEast() {
        return southEast;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(northWest, flags);
        dest.writeParcelable(southEast, flags);
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

        Rectangle other = (Rectangle) obj;
        return northWest.equals(other.northWest) && southEast.equals(other.southEast);
    }

    @Override
    @NonNull
    public String toString() {
        return String.format("Rectangle(%s, %s)", northWest, southEast);
    }
}
