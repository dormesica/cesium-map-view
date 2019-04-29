package com.github.dormesica.mapcontroller.layers;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.github.dormesica.mapcontroller.MapView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * This class represents a vector layer that has been loaded on to a
 * {@link MapView}.
 * <p>
 * Vector layers are essentially a collection of geographically pinned entities. An entity can be attached with an
 * attribute table which provides metadata on it. These attribute tables are key-value pairs stored for a specific
 * entity.
 *
 * @since 1.0.0
 */
public class VectorLayer extends Layer implements Iterable<Entity> { // TODO implement List<Entity>?

    public static final Parcelable.Creator<VectorLayer> CREATOR = new Parcelable.Creator<VectorLayer>() {
        @Override
        public VectorLayer createFromParcel(Parcel source) {
            return new VectorLayer(source);
        }

        @Override
        public VectorLayer[] newArray(int size) {
            return new VectorLayer[0];
        }
    };

    private List<Entity> entities;
    private boolean isVisible;

    /**
     * Creates a new {@code VectorLayer} from a {@link Parcel}.
     *
     * @param source The source Parcel.
     */
    private VectorLayer(Parcel source) {
        super(source);

        isVisible = source.readByte() != 0;
        entities = new ArrayList<>();
        int size = source.readInt();
        for (int i = 0; i < size; i++) {
            entities.add(source.readParcelable(Entity.class.getClassLoader()));
        }
    }

    /**
     * Get the <code>i</code>-th entity of the layer.
     *
     * @param i Entity index.
     * @return The <code>i</code>-th entity.
     * @throws IndexOutOfBoundsException In case the index if greater than or equal to <code>VectorLayer.size()</code>.
     */
    public Entity get(int i) throws IndexOutOfBoundsException {
        if (i >= entities.size()) {
            throw new IndexOutOfBoundsException();
        }

        return entities.get(i);
    }

    /**
     * Returns the amount of entities in the layer.
     *
     * @return The amount of entities in the layer.
     */
    public int size() {
        return entities.size();
    }

    @Override
    public void forEach(Consumer<? super Entity> action) {
        entities.forEach(action);
    }

    @Override
    public Spliterator<Entity> spliterator() {
        return entities.spliterator();
    }

    @NonNull
    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeByte((byte) (isVisible ? 1 : 0));

        int size = entities.size();
        dest.writeInt(size);

        for (int i = 0; i < size; i++) {
            Entity entity = entities.get(i);
            dest.writeParcelable(entity, entity.describeContents());
        }
    }
}
