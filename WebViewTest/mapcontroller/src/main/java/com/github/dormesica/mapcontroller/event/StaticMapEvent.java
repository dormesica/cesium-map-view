package com.github.dormesica.mapcontroller.event;

import com.github.dormesica.mapcontroller.layers.Entity;

/**
 * A super class for static map events (events that do not contain camera movements).
 *
 * @since 1.0.0
 */
public abstract class StaticMapEvent {

    private Entity[] mEntities;

    /**
     * Initializes a new {@link StaticMapEvent} object.
     *
     * @param entities The entities that were clicked.
     */
    protected StaticMapEvent(Entity[] entities) {
        mEntities = entities;
    }

    /**
     * Returns the amount of entities clicked in the event.
     *
     * @return The amount of entities clicked.
     */
    public int entitiesAmount() {
        return mEntities != null ? mEntities.length : 0;
    }

    /**
     * Returns the i-th entity that was clicked in the event.
     *
     * @param i The index of the entity.
     * @return The i-th entity.
     * @throws IndexOutOfBoundsException If {@code i < 0} or {@code i >= entitiesAmount}.
     */
    public Entity entity(int i) throws IndexOutOfBoundsException {
        if (mEntities == null || i < 0 || i >= mEntities.length) {
            throw new IndexOutOfBoundsException();
        }

        return mEntities[i];
    }
}
