package com.github.dormesica.mapcontroller.layers;

import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EntityTransaction {

    private transient EntityManager mEntityManager;
    private transient ValueCallback<TransactionResult> mCallback;
    @SerializedName("removed")
    private ArrayList<String> mRemoveList = new ArrayList<>();
    @SerializedName("editors")
    private ArrayList<Entity.Editor> mEditList = new ArrayList<>();

    public EntityTransaction() {
        mEntityManager = null;
        mCallback = null;
    }

    protected EntityTransaction(EntityManager manager) {
        mEntityManager = manager;
        mCallback = null;
    }

    public void remove(@NonNull Entity entity) {
        mRemoveList.add(entity.getId());
    }

    public void changeStyle(@NonNull Entity.Editor editor) {
        mEditList.add(editor);
    }

    public void setCallback(@NonNull ValueCallback<TransactionResult> callback) {
        mCallback = callback;
    }

    public void commit() {
        if (mRemoveList.isEmpty() && mEditList.isEmpty()) {
            return;
        }

        mEntityManager.commitTransaction(this, mCallback);
    }
}
