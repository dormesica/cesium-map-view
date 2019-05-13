package com.github.dormesica.mapcontroller;

import com.github.dormesica.mapcontroller.graphics.Color;
import com.github.dormesica.mapcontroller.layers.Line;
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

        final String lineString = "{\"type\":\"line\",\"id\":\"1ab57c41-ad10-4067-afd2-7f66ff348802\",\"isVisible\":true,\"path\":[{\"lon\":34.815539717674255,\"lat\":31.95843875770725,\"alt\":0},{\"lon\":34.816033244132996,\"lat\":31.959057738588328,\"alt\":0},{\"lon\":34.81635510921478,\"lat\":31.958889339791128,\"alt\":0},{\"lon\":34.816827178001404,\"lat\":31.959503766342912,\"alt\":0},{\"lon\":34.816773533821106,\"lat\":31.959804151161002,\"alt\":9.313225746154785e-10},{\"lon\":34.817186594009385,\"lat\":31.95991338176036,\"alt\":0},{\"lon\":34.818050265312195,\"lat\":31.961096704925627,\"alt\":0},{\"lon\":34.81867790222167,\"lat\":31.961818069451187,\"alt\":0},{\"lon\":34.81929481029511,\"lat\":31.96248481458842,\"alt\":0},{\"lon\":34.81978833675385,\"lat\":31.962320973228596,\"alt\":0}]}";
        Line line = JsonConverter.getConverter().fromJson(lineString, Line.class);

        expectedJson = new JsonObject();
        expectedJson.add("width", new JsonPrimitive(2));
        expectedJson.add("id", new JsonPrimitive("1ab57c41-ad10-4067-afd2-7f66ff348802"));

        Line.Editor lineEditor = line.edit();
        lineEditor.setWidth(2);

        Assert.assertEquals(expectedJson, JsonConverter.getConverter().toJsonTree(lineEditor));
    }
}
