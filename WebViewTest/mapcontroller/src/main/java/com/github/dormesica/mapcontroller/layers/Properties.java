package com.github.dormesica.mapcontroller.layers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import java.util.*;
import java.util.function.Consumer;

/**
 * This class represents properties that can be attached to an entity on the map.
 * <p>
 * {@code Properties} is a key value store that hold metadata attached to entities on the map.
 * Keys are {@link String} type values and the attached data can be of any type. <br>
 * Conversion to objects and arrays is based of the fields of the class same as deserialization works in
 * {@link com.google.gson.Gson}.
 *
 * @since 1.0.0
 */
public class Properties implements Iterable<Map.Entry<String, JsonElement>> {

    private Map<String, JsonElement> properties;

    /**
     * Creates a new {@code Properties} instance where the given {@link Map} represents the Key-Value data stored
     * in the object. If {@code null} is provided an empty map is created.
     *
     * @param properties Initial properties to add to the {@code Properties} instance.
     */
    public Properties(@Nullable Map<String, JsonElement> properties) {
        this.properties = Collections.unmodifiableMap(properties);
    }

    /**
     * Returns the amount of values stored in the properties object.
     *
     * @return The amount of values in the store.
     */
    public int size() {
        return properties.size();
    }

    /**
     * Returns {@code true} if there is a property value associates with the given key;
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if there is a value associated with the key.
     */
    public boolean has(@NonNull String key) {
        return properties.containsKey(key);
    }

    /**
     * Returns the double value associated with the given key, or 0.0 in case no mapping exists for {@code key}.
     *
     * @param key key for which to retrieve the value.
     * @return The double value associated with key.
     * @throws ClassCastException If the value associates with key cannot be converted to double.
     */
    public double getDouble(@NonNull String key) throws ClassCastException {
        JsonElement value = properties.get(key);
        if (value == null) {
            return 0;
        }

        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
            throw new ClassCastException();
        }
        return value.getAsJsonPrimitive().getAsDouble();
    }

    /**
     * Returns the boolean value associated with the given key, or {@code false} in case no mapping exists for
     * {@code key}.
     *
     * @param key key for which to retrieve the value.
     * @return The boolean value associated with key.
     * @throws ClassCastException If the value associates with key cannot be converted to boolean.
     */
    public boolean getBoolean(@NonNull String key) throws ClassCastException {
        JsonElement value = properties.get(key);
        if (value == null) {
            return false;
        }

        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isBoolean()) {
            throw new ClassCastException();
        }
        return value.getAsJsonPrimitive().getAsBoolean();
    }

    /**
     * Returns the string value associated with the given key, or {@code null} in case no mapping exists for
     * {@code key}.
     *
     * @param key key for which to retrieve the value.
     * @return The string value associated with key.
     * @throws ClassCastException If the value associates with key cannot be converted to string.
     */
    public String getString(@NonNull String key) throws ClassCastException {
        JsonElement value = properties.get(key);
        if (value == null) {
            return null;
        }

        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            throw new ClassCastException();
        }
        return value.getAsJsonPrimitive().getAsString();
    }

    /**
     * Attempts to convert the stored value to the given class type and returns the result. If no value is associated
     * with {@code key} returns {@code null}.
     * <p>
     * The conversion is performed based on the fields of the given type and the value, same as they are serialized
     * and deserialized to JSON objects by {@link com.google.gson.Gson}.
     *
     * @param key  key for which to retrieve the value.
     * @param type Type to which convert the value.
     * @param <T>  Return type.
     * @return The value associated with {@code key} converted to {@code type}.
     * @throws ClassCastException If the value associated wih {@code key} cannot be converted to the given type.
     */
    public <T> T getAs(@NonNull String key, @NonNull Class<T> type) throws ClassCastException {
        try {
            JsonElement value = properties.get(key);
            if (value == null) {
                return null;
            }

            return JsonConverter.getConverter().fromJson(value, type);
        } catch (JsonSyntaxException e) {
            throw new ClassCastException();
        }
    }

    /**
     * Returns a {@link Set} view of the keys that are contained in the {@code Properties} object.
     *
     * @return A set view of the keys contained in this map.
     */
    public Set<String> keySet() {
        return properties.keySet();
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     *
     * @return A collection view of the values contained in this map.
     */
    public Collection<JsonElement> values() {
        return properties.values();
    }

    @NonNull
    @Override
    public Iterator<Map.Entry<String, JsonElement>> iterator() {
        return properties.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, JsonElement>> action) {
        properties.entrySet().forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<String, JsonElement>> spliterator() {
        return properties.entrySet().spliterator();
    }
}
