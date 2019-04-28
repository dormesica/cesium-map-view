package com.github.dormesica.mapcontroller;

import com.github.dormesica.mapcontroller.graphics.Color;
import com.github.dormesica.mapcontroller.layers.Polygon;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Assert;
import org.junit.Test;

public class StyleEditorTest {

    @Test
    public void convertToJson() {
        final String polygonString = "{\"type\":\"polygon\",\"id\":\"270551bf-12fb-4ecb-9241-0d043ae42047\",\"isVisible\":true,\"perimeter\":[{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9},{\"lon\":34.813635349273675,\"lat\":31.957660474355507,\"alt\":0},{\"lon\":34.81424689292908,\"lat\":31.957319119927604,\"alt\":0},{\"lon\":34.81527954339981,\"lat\":31.958625362676855,\"alt\":0},{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9}]}";
        Polygon polygon = JsonConverter.getConverter().fromJson(polygonString, Polygon.class);
        Color color = new Color(203, 54, 2, 0.3);

        JsonObject expectedJson = new JsonObject();
        expectedJson.add("height", new JsonPrimitive(3.5));
        expectedJson.add("color", new JsonPrimitive(color.getColorString()));
        expectedJson.add("opacity", new JsonPrimitive(color.alpha()));
        expectedJson.add("id", new JsonPrimitive("270551bf-12fb-4ecb-9241-0d043ae42047"));

        Polygon.Editor editor = polygon.edit();
        editor.setHeight(3.5);
        editor.setColor(color);

        JsonElement json = JsonConverter.getConverter().toJsonTree(editor);
        Assert.assertEquals(expectedJson, json);
    }
}
