package com.github.dormesica.mapcontroller;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/**
 * A class that describes the changes to a {@link Styleable} object's style that should be made.
 * <p>
 * Classes that inherit from {@code StyleEditor} are serialized into JSON strings using {@code Gson}. Every
 * implementation of such class should take that into account.
 *
 * @since 1.0.0
 */
public abstract class StyleEditor implements Parcelable {

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
     * Creates a new {@code StyleEditor} from a {@link Parcel}.
     *
     * @param source The source Parcel.
     */
    protected StyleEditor(Parcel source) {
        id = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}
