package com.github.dormesica.mapcontroller;

import com.github.dormesica.mapcontroller.layers.*;
import com.github.dormesica.mapcontroller.location.Coordinates;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class VectorConversionTest {

    private static Gson sGson = JsonConverter.getConverter();

    @Test
    public void createPoint() {
        final String pointString = "{\"type\":\"point\",\"id\":\"1031744e-2a0a-4538-8230-b15f4bcdfeb0\",\"isVisible\":true,\"location\":{\"lon\":34.81548607349395,\"lat\":31.958445584725023,\"alt\":0}, \"properties\": {\"obj\": { \"number\": 2.5, \"integer\": 2 }, \"string\": \"string\"}}";
        Entity point = sGson.fromJson(pointString, Entity.class);

        basicEntityTests(point, "1031744e-2a0a-4538-8230-b15f4bcdfeb0");

        Assert.assertTrue(point instanceof Point);

        Coordinates location = ((Point) point).getLocation();
        Assert.assertNotNull(location);
        assertCoordinateEquals(34.815486, location.getLon());
        assertCoordinateEquals(31.958445, location.getLat());
        assertCoordinateEquals(0, location.getAlt());

        Properties props = point.getProperties();
        Assert.assertEquals("string", props.getString("string"));

        TestProp inner = props.getAs("obj", TestProp.class);
        Assert.assertEquals(2, inner.integer);
        Assert.assertEquals(2.5, inner.number, 0.001);
    }

    @Test
    public void createLine() {
        final String lineString = "{\"type\":\"line\",\"id\":\"1ab57c41-ad10-4067-afd2-7f66ff348802\",\"isVisible\":true,\"path\":[{\"lon\":34.815539717674255,\"lat\":31.95843875770725,\"alt\":0},{\"lon\":34.816033244132996,\"lat\":31.959057738588328,\"alt\":0},{\"lon\":34.81635510921478,\"lat\":31.958889339791128,\"alt\":0},{\"lon\":34.816827178001404,\"lat\":31.959503766342912,\"alt\":0},{\"lon\":34.816773533821106,\"lat\":31.959804151161002,\"alt\":9.313225746154785e-10},{\"lon\":34.817186594009385,\"lat\":31.95991338176036,\"alt\":0},{\"lon\":34.818050265312195,\"lat\":31.961096704925627,\"alt\":0},{\"lon\":34.81867790222167,\"lat\":31.961818069451187,\"alt\":0},{\"lon\":34.81929481029511,\"lat\":31.96248481458842,\"alt\":0},{\"lon\":34.81978833675385,\"lat\":31.962320973228596,\"alt\":0}]}";
        Entity entity = sGson.fromJson(lineString, Entity.class);

        basicEntityTests(entity, "1ab57c41-ad10-4067-afd2-7f66ff348802");

        Assert.assertTrue(entity instanceof Line);

        Line line = (Line) entity;
        Assert.assertEquals(line.size(), 10);

        Coordinates start = line.getPointAt(0);
        assertCoordinateEquals(34.815539, start.getLon());
        assertCoordinateEquals(31.958438, start.getLat());
        assertCoordinateEquals(0, start.getAlt());

        Coordinates end = line.getPointAt(line.size() - 1);
        assertCoordinateEquals(34.819788, end.getLon());
        assertCoordinateEquals(31.962320, end.getLat());
        assertCoordinateEquals(0, end.getAlt());
    }

    @Test
    public void createPolygon() {
        final String polygonString = "{\"type\":\"polygon\",\"id\":\"270551bf-12fb-4ecb-9241-0d043ae42047\",\"isVisible\":true,\"perimeter\":[{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9},{\"lon\":34.813635349273675,\"lat\":31.957660474355507,\"alt\":0},{\"lon\":34.81424689292908,\"lat\":31.957319119927604,\"alt\":0},{\"lon\":34.81527954339981,\"lat\":31.958625362676855,\"alt\":0},{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9}]}";
        Entity entity = sGson.fromJson(polygonString, Entity.class);

        basicEntityTests(entity, "270551bf-12fb-4ecb-9241-0d043ae42047");

        Assert.assertTrue(entity instanceof Polygon);

        // TODO finish
    }

    @Test
    public void createVectorLayer() {
        final String layerString = "{\"entities\":[{\"type\":\"point\",\"id\":\"1031744e-2a0a-4538-8230-b15f4bcdfeb0\",\"isVisible\":true,\"location\":{\"lon\":34.81548607349395,\"lat\":31.958445584725023,\"alt\":0}},{\"type\":\"line\",\"id\":\"1ab57c41-ad10-4067-afd2-7f66ff348802\",\"isVisible\":true,\"path\":[{\"lon\":34.815539717674255,\"lat\":31.95843875770725,\"alt\":0},{\"lon\":34.816033244132996,\"lat\":31.959057738588328,\"alt\":0},{\"lon\":34.81635510921478,\"lat\":31.958889339791128,\"alt\":0},{\"lon\":34.816827178001404,\"lat\":31.959503766342912,\"alt\":0},{\"lon\":34.816773533821106,\"lat\":31.959804151161002,\"alt\":9.313225746154785e-10},{\"lon\":34.817186594009385,\"lat\":31.95991338176036,\"alt\":0},{\"lon\":34.818050265312195,\"lat\":31.961096704925627,\"alt\":0},{\"lon\":34.81867790222167,\"lat\":31.961818069451187,\"alt\":0},{\"lon\":34.81929481029511,\"lat\":31.96248481458842,\"alt\":0},{\"lon\":34.81978833675385,\"lat\":31.962320973228596,\"alt\":0}]},{\"type\":\"polygon\",\"id\":\"270551bf-12fb-4ecb-9241-0d043ae42047\",\"isVisible\":true,\"perimeter\":[{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9},{\"lon\":34.813635349273675,\"lat\":31.957660474355507,\"alt\":0},{\"lon\":34.81424689292908,\"lat\":31.957319119927604,\"alt\":0},{\"lon\":34.81527954339981,\"lat\":31.958625362676855,\"alt\":0},{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9}]}],\"id\":\"99254066-460b-47b7-b434-c1c1fbbc70a7\"}";
        VectorLayer layer = sGson.fromJson(layerString, VectorLayer.class);

        Assert.assertEquals("99254066-460b-47b7-b434-c1c1fbbc70a7", layer.getId());

        Assert.assertTrue(layer.get(0) instanceof Point);
        Assert.assertTrue(layer.get(1) instanceof Line);
        Assert.assertTrue(layer.get(2) instanceof Polygon);

        // TODO finish
    }

    private void basicEntityTests(Entity entity, String expectedId) {
        Assert.assertNotNull(entity);
        Assert.assertEquals(expectedId, entity.getId());
        Assert.assertNull(entity.getName());
        Assert.assertTrue(entity.isVisible());
    }

    private void assertCoordinateEquals(double expected, double actual) {
        Assert.assertEquals(expected, actual, 0.001);
    }

    private class TestProp {
        double number;
        int integer;
    }
}
