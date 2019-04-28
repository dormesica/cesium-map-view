package com.github.dormesica.mapcontroller.util;

import android.util.Log;
import com.github.dormesica.mapcontroller.layers.Entity;
import com.google.gson.*;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * Type Adapter for converting instances of {@link Entity.Editor} from and to JSON strings.
 * <p>
 * The Adapter expects fields of types: {@code int}, {@code double}, {@code boolean} or {@code String} and
 * behaves by the following rules:
 * <ul>
 * <li>Fields that enforce Java language access control are ignored and not serialized into the JSON string</li>
 * <li>When a field has the value {@code null} it is ignored.</li>
 * <li>For {@code double} type fields - if it contains the value {@code Double.NaN} it is ignored.</li>
 * <li>For {@code int} type fields - if it contains the value {@code Integer.MIN_VALUE} it is ignored.</li>
 * <li>For any other types (namely {@code boolean} or {@code String}) they are serialized as they are.</li>
 * </ul>
 * <p>
 * Subclasses of {@link Entity.Editor} that are serialized using {@link JsonConverter} should abide by these rules or
 * the behavior of {@link JsonConverter} is undefined.
 *
 * @since 1.0.0
 */
public class EntityEditorTypeAdapter implements JsonSerializer<Entity.Editor> {

    private static final String TAG = "EntityEditorTypeAdapter"; // TODO better tag

    @Override
    public JsonElement serialize(Entity.Editor src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        Class clazz = src.getClass();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                AccessibleObject.setAccessible(fields, true);
                String name = field.getName();
                try {
                    Object value = field.get(src);
                    if (value == null) {
                        continue;
                    }

                    if (value instanceof Double) {
                        double doubleValue = (double) value;
                        if (Double.isNaN(doubleValue)) {
                            continue;
                        }
                    } else if (value instanceof Integer) {
                        int intValue = (int) value;
                        if (intValue == Integer.MIN_VALUE) {
                            continue;
                        }
                    }
                    json.add(name, context.serialize(value));
                } catch (IllegalAccessException e) {
                    Log.w(TAG, String.format("Cannot access field %s. Skipping...", name));
                }
            }

            clazz = clazz.getSuperclass();
        }

        return json;
    }
}
