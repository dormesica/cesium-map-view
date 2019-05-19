package com.github.dormesica.mapcontroller.layers;

import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

/**
 * Manages the entities of a {@link com.github.dormesica.mapcontroller.MapView}.
 *
 * @since 1.0.0
 */
public abstract class EntityManager {

    protected HashMap<String, Entity> mEntities = new HashMap<>();

    /**
     * Creates a new {@link EntityTransaction} object.
     *
     * @return A new {@code EntityTransaction}.
     */
    public EntityTransaction beginTransaction() {
        return new EntityTransaction(this);
    }

    /**
     * Returns the entity associated with the given ID.
     *
     * @param id The ID of the entity
     * @return The entity associated with the given ID.
     */
    @Nullable
    public Entity getEntityById(@NonNull String id) {
        return mEntities.get(id);
    }

    /**
     * Commits the given transaction to the {@link com.github.dormesica.mapcontroller.MapView} with which the manager is
     * associated.
     *
     * @param transaction The transaction to commit.
     * @param callback Called when commit is finished.
     */
    protected abstract void commitTransaction(@NonNull EntityTransaction transaction,
                                              @Nullable ValueCallback<TransactionResult> callback);
}
