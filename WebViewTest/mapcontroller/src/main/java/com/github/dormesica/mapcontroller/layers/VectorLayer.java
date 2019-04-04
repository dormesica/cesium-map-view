package com.github.dormesica.mapcontroller.layers;

public class VectorLayer {

    private String id;
    private Entity[] entities;

    public String getId() {
        return id;
    }

    public Entity get(int i) {
        return entities[i];
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
