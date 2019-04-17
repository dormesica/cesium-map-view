package com.github.dormesica.mapcontroller;

import android.os.Parcel;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.github.dormesica.mapcontroller.layers.Line;
import com.github.dormesica.mapcontroller.layers.Point;
import com.github.dormesica.mapcontroller.layers.Polygon;
import com.github.dormesica.mapcontroller.layers.VectorLayer;
import com.github.dormesica.mapcontroller.location.Coordinates;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VectorDataAndroidTest {

    @Test
    public void writePointToParcel() {
        final String pointString = "{\"type\":\"point\",\"id\":\"1031744e-2a0a-4538-8230-b15f4bcdfeb0\",\"isVisible\":true,\"location\":{\"lon\":34.81548607349395,\"lat\":31.958445584725023,\"alt\":0}, \"properties\": {\"obj\": { \"number\": 2.5, \"integer\": 2 }, \"string\": \"string\"}}";
        Parcel parcel = Parcel.obtain();

        Point point = JsonConverter.getConverter().fromJson(pointString, Point.class);
        point.writeToParcel(parcel, point.describeContents());

        parcel.setDataPosition(0);
        Point fromParcel = Point.CREATOR.createFromParcel(parcel);

        Assert.assertNotNull(fromParcel);
        Assert.assertEquals("1031744e-2a0a-4538-8230-b15f4bcdfeb0", fromParcel.getId());
        Assert.assertNull(fromParcel.getName());
        Assert.assertTrue(fromParcel.isVisible());

        Assert.assertNotNull(point.getLocation());
        Coordinates location = point.getLocation();
        assertCoordinateEquals(34.815486, location.getLon());
        assertCoordinateEquals(31.958445, location.getLat());
        assertCoordinateEquals(0, location.getAlt());

        Assert.assertEquals(point.getProperties(), fromParcel.getProperties());
    }

    @Test
    public void writeLineToParcel() {
        final String lineString = "{\"type\":\"line\",\"id\":\"1ab57c41-ad10-4067-afd2-7f66ff348802\",\"isVisible\":true,\"path\":[{\"lon\":34.815539717674255,\"lat\":31.95843875770725,\"alt\":0},{\"lon\":34.816033244132996,\"lat\":31.959057738588328,\"alt\":0},{\"lon\":34.81635510921478,\"lat\":31.958889339791128,\"alt\":0},{\"lon\":34.816827178001404,\"lat\":31.959503766342912,\"alt\":0},{\"lon\":34.816773533821106,\"lat\":31.959804151161002,\"alt\":9.313225746154785e-10},{\"lon\":34.817186594009385,\"lat\":31.95991338176036,\"alt\":0},{\"lon\":34.818050265312195,\"lat\":31.961096704925627,\"alt\":0},{\"lon\":34.81867790222167,\"lat\":31.961818069451187,\"alt\":0},{\"lon\":34.81929481029511,\"lat\":31.96248481458842,\"alt\":0},{\"lon\":34.81978833675385,\"lat\":31.962320973228596,\"alt\":0}]}";
        Parcel parcel = Parcel.obtain();

        Line line = JsonConverter.getConverter().fromJson(lineString, Line.class);
        line.writeToParcel(parcel, line.describeContents());

        parcel.setDataPosition(0);
        Line fromParcel = Line.CREATOR.createFromParcel(parcel);

        Assert.assertEquals("1ab57c41-ad10-4067-afd2-7f66ff348802", fromParcel.getId());
        Assert.assertNull(fromParcel.getName());
        Assert.assertTrue(fromParcel.isVisible());

        Assert.assertEquals(line.size(), fromParcel.size());
        Assert.assertEquals(line.getPointAt(0), fromParcel.getPointAt(0));
        Assert.assertEquals(line.getPointAt(line.size() - 1), fromParcel.getPointAt(line.size() - 1));
    }

    @Test
    public void writePolygonToParcel() {
        final String polygonString = "{\"type\":\"polygon\",\"id\":\"270551bf-12fb-4ecb-9241-0d043ae42047\",\"isVisible\":true,\"perimeter\":[{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9},{\"lon\":34.813635349273675,\"lat\":31.957660474355507,\"alt\":0},{\"lon\":34.81424689292908,\"lat\":31.957319119927604,\"alt\":0},{\"lon\":34.81527954339981,\"lat\":31.958625362676855,\"alt\":0},{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9}]}";
        Parcel parcel = Parcel.obtain();

        Polygon polygon = JsonConverter.getConverter().fromJson(polygonString, Polygon.class);
        polygon.writeToParcel(parcel, polygon.describeContents());

        parcel.setDataPosition(0);
        Polygon fromParcel = Polygon.CREATOR.createFromParcel(parcel);

        // TODO complete
    }

    @Test
    public void writeVectorLayerToParcel() {
        final String layerString = "{\"entities\":[{\"type\":\"point\",\"id\":\"1031744e-2a0a-4538-8230-b15f4bcdfeb0\",\"isVisible\":true,\"location\":{\"lon\":34.81548607349395,\"lat\":31.958445584725023,\"alt\":0}},{\"type\":\"line\",\"id\":\"1ab57c41-ad10-4067-afd2-7f66ff348802\",\"isVisible\":true,\"path\":[{\"lon\":34.815539717674255,\"lat\":31.95843875770725,\"alt\":0},{\"lon\":34.816033244132996,\"lat\":31.959057738588328,\"alt\":0},{\"lon\":34.81635510921478,\"lat\":31.958889339791128,\"alt\":0},{\"lon\":34.816827178001404,\"lat\":31.959503766342912,\"alt\":0},{\"lon\":34.816773533821106,\"lat\":31.959804151161002,\"alt\":9.313225746154785e-10},{\"lon\":34.817186594009385,\"lat\":31.95991338176036,\"alt\":0},{\"lon\":34.818050265312195,\"lat\":31.961096704925627,\"alt\":0},{\"lon\":34.81867790222167,\"lat\":31.961818069451187,\"alt\":0},{\"lon\":34.81929481029511,\"lat\":31.96248481458842,\"alt\":0},{\"lon\":34.81978833675385,\"lat\":31.962320973228596,\"alt\":0}]},{\"type\":\"polygon\",\"id\":\"270551bf-12fb-4ecb-9241-0d043ae42047\",\"isVisible\":true,\"perimeter\":[{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9},{\"lon\":34.813635349273675,\"lat\":31.957660474355507,\"alt\":0},{\"lon\":34.81424689292908,\"lat\":31.957319119927604,\"alt\":0},{\"lon\":34.81527954339981,\"lat\":31.958625362676855,\"alt\":0},{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9}]}],\"id\":\"99254066-460b-47b7-b434-c1c1fbbc70a7\"}";
        Parcel parcel = Parcel.obtain();

        VectorLayer layer = JsonConverter.getConverter().fromJson(layerString, VectorLayer.class);
        layer.writeToParcel(parcel, layer.describeContents());

        parcel.setDataPosition(0);
        VectorLayer fromParcel = VectorLayer.CREATOR.createFromParcel(parcel);

        Assert.assertEquals(layer.getId(), fromParcel.getId());

        Assert.assertTrue(layer.get(0) instanceof Point);
        Assert.assertTrue(layer.get(1) instanceof Line);
        Assert.assertTrue(layer.get(2) instanceof Polygon);
    }

    private void assertCoordinateEquals(double expected, double actual) {
        Assert.assertEquals(expected, actual, 0.001);
    }
}
