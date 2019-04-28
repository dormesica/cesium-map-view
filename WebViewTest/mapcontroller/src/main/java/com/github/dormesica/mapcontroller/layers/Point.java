package com.github.dormesica.mapcontroller.layers;

import android.os.Parcel;
import android.os.Parcelable;
import com.github.dormesica.mapcontroller.location.Coordinates;

import java.net.URL;

/**
 * This class represents a point on the map.
 * <p>
 * A point is represented by the set of coordinates of it's location.
 *
 * @since 1.0.0
 */
public class Point extends Entity {

    public static final Parcelable.Creator<Point> CREATOR = new Parcelable.Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel source) {
            return new Point(source);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[0];
        }
    };

    /**
     * The default icon for point.
     * <p>
     * Shapes as a circle with black stroke color and white fill.
     */
    public static final String DEFAULT_POINT_ICON = "data:image/svg+xml," +
            "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" " +
            "xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"20px\" height=\"20px\" " +
            "xml:space=\"preserve\">" +
            "<circle cx=\"10\" cy=\"10\" r=\"9\" stroke=\"black\" stroke-width=\"3\" fill=\"white\" />" +
            "</svg>";

    private Coordinates location;

    /**
     * Creates a new {@code Point} from a {@link Parcel}.
     *
     * @param source The source Parcel.
     */
    private Point(Parcel source) {
        super(source);
        location = source.readParcelable(Coordinates.class.getClassLoader());
    }

    @Override
    public Editor edit() {
        return new Editor(getId());
    }

    /**
     * Get the Coordinates of the point.
     *
     * @return The coordinates of the point.
     */
    public Coordinates getLocation() {
        return location;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(location, flags);
    }

    /**
     * A class that represents the changes that can be made onto a {@link Point} on the map.
     *
     * @since 1.0.0
     */
    public static class Editor extends Entity.Editor {
        public static final Parcelable.Creator<Editor> CREATOR = new Parcelable.Creator<Editor>() {
            @Override
            public Editor createFromParcel(Parcel source) {
                return new Editor(source);
            }

            @Override
            public Editor[] newArray(int size) {
                return new Editor[0];
            }
        };

        private String marker;

        /**
         * Creates a new {@code Point.Editor} object.
         */
        protected Editor(String id) {
            super(id);
        }

        /**
         * Creates a new {@code Point.Editor} object from a {@link Parcel}.
         *
         * @param source The source Parcel.
         */
        protected Editor(Parcel source) {
            super(source);

            marker = source.readString();
        }

        /**
         * Sets the display marker of the {@link Point} to the image located at the given URL.
         *
         * @param url The URL from which to retrieve the marker image.
         * @return The {@code Point.Entity} for method Chaining.
         */
        public Editor setMarker(URL url) {
            marker = url.toString();
            return this;
        }

        /**
         * Sets the display marker of the {@link Point} to the given SVG data URI.
         * <p>
         * The URI should be of the form: {@code data:image/svg+xml,<svg>...</svg>}
         *
         * @param svg SVG data URI for the marker.
         * @return The {@code Point.Entity} for method Chaining.
         */
        public Editor setMarker(String svg) {
            marker = svg;
            return this;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            dest.writeString(marker);
        }
    }
}
