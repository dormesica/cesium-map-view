package com.github.dormesica.mapcontroller.layers;

import android.webkit.ValueCallback;

import java.util.List;

/**
 *
 * @since 1.0.0
 */
public class VectorLayer extends Layer {

    private List<Entity> entities;

    public Entity get(int i) {
        return entities.get(i);
    }

    @Override
    public void remove(ValueCallback<Boolean> callback) {
        // TODO implement
    }

    public void focus() {
        // TODO implement
    }

    public void show() {
        // TODO implement
    }

    public void hide() {
        // TODO implement
    }
}
