package com.github.dormesica.mapcontroller.layers;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.gson.Gson;
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
public class Properties implements Iterable<Map.Entry<String, JsonElement>>, Parcelable {

    public static final Parcelable.Creator<Properties> CREATOR = new Parcelable.Creator<Properties>() {
        @Override
        public Properties createFromParcel(Parcel source) {
            return new Properties(source);
        }

        @Override
        public Properties[] newArray(int size) {
            return new Properties[0];
        }
    };

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
     * Creates a new {@code Properties} object from a {@link Parcel}.
     * @param source The source Parcel.
     */
    private Properties(Parcel source) {
        Gson converter = JsonConverter.getConverter();

        properties = new HashMap<>();
        int size = source.readInt();
        for (int i = 0; i < size; i++) {
            String key = source.readString();
            if (key == null) {
                break; // TODO or throw?
            }
            String value = source.readString();

            properties.put(key, converter.fromJson(value, JsonElement.class));
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(properties.size());

        properties.forEach((key, value) -> {
            dest.writeString(key);
            dest.writeString(value.toString());
        });
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Set<String> keys = keySet();
        Properties other = (Properties) obj;
        if (!other.keySet().equals(keySet())) {
            return false;
        }

        for (String key : keys) {
            JsonElement value = properties.get(key);
            JsonElement otherValue = other.properties.get(key);
            if (value == null && otherValue == null) {
                continue;
            }
            if (value == null || !value.equals(otherValue)) {
                return false;
            }
        }

        return true;
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
