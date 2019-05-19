package com.github.dormesica.mapcontroller.layers;

import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * A class that represents changes on the entities of a {@link com.github.dormesica.mapcontroller.MapView}.
 * <p>
 * Changes include adding entities, removing entities and changes the style of entities.
 *
 * @since 1.0.0
 */
public class EntityTransaction {

    private transient EntityManager mEntityManager;
    private transient ValueCallback<TransactionResult> mCallback;
    @SerializedName("removed")
    private ArrayList<String> mRemoveList = new ArrayList<>();
    @SerializedName("editors")
    private ArrayList<Entity.Editor> mEditList = new ArrayList<>();

    /**
     * Creates a new {@code EntityTransaction} object.
     *
     * @param manager The manager though which the transaction was created.
     */
    protected EntityTransaction(EntityManager manager) {
        mEntityManager = manager;
        mCallback = null;
    }

    /**
     * Removes the entity from the {@link com.github.dormesica.mapcontroller.MapView}.
     *
     * @param entity The entity to be removed.
     */
    public void remove(@NonNull Entity entity) {
        mRemoveList.add(entity.getId());
    }

    /**
     * Adds a change style descriptor to the transaction.
     *
     * @param editor An editor to add to the transaction.
     */
    public void changeStyle(@NonNull Entity.Editor editor) {
        mEditList.add(editor);
    }

    /**
     * Sets a callback to be invoked when the changes of the transaction have been applied.
     *
     * @param callback Called when the changes have been applies.
     */
    public void setCallback(@NonNull ValueCallback<TransactionResult> callback) {
        mCallback = callback;
    }

    /**
     * Commits the given transaction.
     * Applies the changes encoded in the transaction to the {@link com.github.dormesica.mapcontroller.MapView} with
     * which the transaction is associated.
     */
    public void commit() {
        if (containsChanges()) {
            return;
        }

        mEntityManager.commitTransaction(this, mCallback);
    }

    /**
     * Returns whether this transaction contains changes that can be applies to the
     * {@link com.github.dormesica.mapcontroller.MapView}.
     *
     * @return {@code true} if the transaction contains changes, {@code false} otherwise.
     */
    private boolean containsChanges() {
        return mRemoveList.isEmpty() && mEditList.isEmpty();
    }
}
