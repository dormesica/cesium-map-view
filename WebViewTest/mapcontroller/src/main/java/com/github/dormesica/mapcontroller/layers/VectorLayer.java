package com.github.dormesica.mapcontroller.layers;

import android.view.ViewGroup;
import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.dormesica.mapcontroller.MapView;
import com.github.dormesica.mapcontroller.Styleable;

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

    private List<Entity> entities;
    private boolean isVisible = true;

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

    /**
     *
     * @param <T>
     * @since 1.0.0
     */
    public static abstract class Adapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
        private List<Entity> entities;

        public Adapter(VectorLayer layer) {
            entities = layer.entities;
        }

        @Override
        public int getItemCount() {
            return entities.size();
        }
    }
}
