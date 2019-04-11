package com.github.dormesica.mapcontroller;

import com.github.dormesica.mapcontroller.layers.Properties;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.gson.JsonElement;
import org.junit.Assert;
import org.junit.Test;

public class PropertiesTest {

    private static String JSON_STRING = "{ " +
            "\"string\": \"string\", " +
            "\"number\": 2.34, " +
            "\"boolean\": true, " +
            "\"inner\": { \"string\": \"inner\" } " +
            "}";

    @Test
    public void convertsJsonToProperties() {
        Properties properties = JsonConverter.getConverter().fromJson(JSON_STRING, Properties.class);

        Assert.assertNotNull(properties);
        Assert.assertEquals("string", properties.getString("string"));
        Assert.assertEquals(2.34, properties.getDouble("number"), 0.001);
        Assert.assertTrue(properties.getBoolean("boolean"));

        InnerJson inner = properties.getAs("inner", InnerJson.class);
        Assert.assertNotNull(inner);
        Assert.assertEquals("inner", inner.string);
    }

    @Test
    public void convertsPropertiesToJson() {
        Properties properties = JsonConverter.getConverter().fromJson(JSON_STRING, Properties.class);
        JsonElement serialized = JsonConverter.getConverter().toJsonTree(properties);

        Assert.assertEquals(JsonConverter.getConverter().fromJson(JSON_STRING, JsonElement.class), serialized);
    }

    private static class InnerJson {
        String string;
    }
}
