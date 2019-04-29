package com.github.dormesica.mapcontroller;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents options for the initialization of a {@link MapFragment}.
 *
 * @since 1.0.0
 */
public class MapOptions implements Parcelable {

    public static final Parcelable.Creator<MapOptions> CREATOR = new Parcelable.Creator<MapOptions>() {
        @Override
        public MapOptions createFromParcel(Parcel source) {
            return new MapOptions(source);
        }

        @Override
        public MapOptions[] newArray(int size) {
            return new MapOptions[0];
        }
    };

    /**
     * Creates a new {@code MapOptions} object.
     */
    public MapOptions() {
    }

    /**
     * Creates a new {@code MapOptions} from a {@link Parcel}.
     *
     * @param source The source Parcel.
     */
    private MapOptions(Parcel source) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
