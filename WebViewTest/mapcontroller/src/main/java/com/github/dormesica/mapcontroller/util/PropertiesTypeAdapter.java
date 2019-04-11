package com.github.dormesica.mapcontroller.util;

import com.github.dormesica.mapcontroller.layers.Properties;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class PropertiesTypeAdapter implements JsonDeserializer<Properties>, JsonSerializer<Properties> {

    @Override
    public Properties deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonNull()) {
            return null;
        }

        HashMap<String, JsonElement> propertiesMap = new HashMap<>();

        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            object.entrySet().forEach(entry ->
                    propertiesMap.put(entry.getKey(), entry.getValue()));
        }

        return new Properties(propertiesMap);
    }

    @Override
    public JsonElement serialize(Properties src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        src.forEach(entry -> json.add(entry.getKey(), entry.getValue()));

        return json;
    }
}
