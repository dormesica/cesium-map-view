package com.github.dormesica.mapcontroller.util;

import com.github.dormesica.mapcontroller.layers.Entity;
import com.github.dormesica.mapcontroller.layers.Line;
import com.github.dormesica.mapcontroller.layers.Point;
import com.github.dormesica.mapcontroller.layers.Polygon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

/**
 * This class holds a singleton instance of Gson used to convert classes from this library.
 * <p>
 * Supports converting Entity super class and returning instance of one of it's sub classes.
 * Subclass determined by the value of a type field in the JSON object. The following values are supported:
 * <ul>
 * <li>point - instance of {@link Point}</li>
 * <li>line - instance of {@link Line}</li>
 * <li>polygon - instance of {@link Polygon}</li>
 * </ul>
 *
 * @since 1.0.0
 */
public class JsonConverter {

    private static final Gson sConverter;

    static {
        TypeAdapterFactory entityAdapterFactory = RuntimeTypeAdapterFactory.of(Entity.class, "type")
                .registerSubtype(Point.class, "point")
                .registerSubtype(Line.class, "line")
                .registerSubtype(Polygon.class, "polygon");

        sConverter = new GsonBuilder()
                .registerTypeAdapterFactory(entityAdapterFactory)
                .create();
    }

    /**
     * Get the converter object.
     *
     * @return The converter object.
     */
    public static Gson getConverter() {
        return sConverter;
    }
}
