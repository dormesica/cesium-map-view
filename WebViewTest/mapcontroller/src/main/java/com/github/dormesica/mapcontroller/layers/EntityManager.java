package com.github.dormesica.mapcontroller.layers;

import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public abstract class EntityManager {

    protected HashMap<String, Entity> mEntities = new HashMap<>();

    public EntityTransaction beginTransaction() {
        return new EntityTransaction(this);
    }

    @Nullable
    public Entity getEntityById(@NonNull String id) {
        return mEntities.get(id);
    }

    protected abstract void commitTransaction(@NonNull EntityTransaction transaction,
                                              @Nullable ValueCallback<TransactionResult> callback);
}
