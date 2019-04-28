package com.github.dormesica.mapcontroller;

import android.os.Parcel;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.github.dormesica.mapcontroller.layers.Entity;
import com.github.dormesica.mapcontroller.layers.Line;
import com.github.dormesica.mapcontroller.layers.Point;
import com.github.dormesica.mapcontroller.layers.Polygon;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(AndroidJUnit4.class)
public class StyleEditorAndroidTest {

    private static final Gson converter = JsonConverter.getConverter();

    @Test
    public void writeToParcel() throws MalformedURLException {
        final String pointString = "{\"type\":\"point\",\"id\":\"1031744e-2a0a-4538-8230-b15f4bcdfeb0\",\"isVisible\":true,\"location\":{\"lon\":34.81548607349395,\"lat\":31.958445584725023,\"alt\":0}, \"properties\": {\"obj\": { \"number\": 2.5, \"integer\": 2 }, \"string\": \"string\"}}";
        final String lineString = "{\"type\":\"line\",\"id\":\"1ab57c41-ad10-4067-afd2-7f66ff348802\",\"isVisible\":true,\"path\":[{\"lon\":34.815539717674255,\"lat\":31.95843875770725,\"alt\":0},{\"lon\":34.816033244132996,\"lat\":31.959057738588328,\"alt\":0},{\"lon\":34.81635510921478,\"lat\":31.958889339791128,\"alt\":0},{\"lon\":34.816827178001404,\"lat\":31.959503766342912,\"alt\":0},{\"lon\":34.816773533821106,\"lat\":31.959804151161002,\"alt\":9.313225746154785e-10},{\"lon\":34.817186594009385,\"lat\":31.95991338176036,\"alt\":0},{\"lon\":34.818050265312195,\"lat\":31.961096704925627,\"alt\":0},{\"lon\":34.81867790222167,\"lat\":31.961818069451187,\"alt\":0},{\"lon\":34.81929481029511,\"lat\":31.96248481458842,\"alt\":0},{\"lon\":34.81978833675385,\"lat\":31.962320973228596,\"alt\":0}]}";
        final String polygonString = "{\"type\":\"polygon\",\"id\":\"270551bf-12fb-4ecb-9241-0d043ae42047\",\"isVisible\":true,\"perimeter\":[{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9},{\"lon\":34.813635349273675,\"lat\":31.957660474355507,\"alt\":0},{\"lon\":34.81424689292908,\"lat\":31.957319119927604,\"alt\":0},{\"lon\":34.81527954339981,\"lat\":31.958625362676855,\"alt\":0},{\"lon\":34.81442660093306,\"lat\":31.959050911616067,\"alt\":-1.3969838619232178e-9}]}";
        Parcel parcel = Parcel.obtain();

        Point point = converter.fromJson(pointString, Point.class);
        Point.Editor pointEditor = point.edit()
                .setMarker(new URL("http://www.google.com"));
        pointEditor.writeToParcel(parcel, pointEditor.describeContents());

        parcel.setDataPosition(0);
        Entity.Editor fromParcel = Point.Editor.CREATOR.createFromParcel(parcel);

        Assert.assertNotNull(fromParcel);
        Assert.assertEquals(converter.toJsonTree(pointEditor), converter.toJsonTree(fromParcel));

        parcel.recycle();
        parcel = Parcel.obtain();

        Line line = converter.fromJson(lineString, Line.class);
        Line.Editor lineEditor = line.edit()
                .setWidth(5);
        lineEditor.writeToParcel(parcel, line.describeContents());

        parcel.setDataPosition(0);
        fromParcel = Line.Editor.CREATOR.createFromParcel(parcel);

        Assert.assertNotNull(fromParcel);
        Assert.assertEquals(converter.toJsonTree(lineEditor), converter.toJsonTree(fromParcel));

        parcel.recycle();
        parcel = Parcel.obtain();

        Polygon polygon = converter.fromJson(polygonString, Polygon.class);
        Polygon.Editor polygonEditor = polygon.edit()
                .hasFill(false)
                .setHeight(5.5);
        polygonEditor.writeToParcel(parcel, polygon.describeContents());

        parcel.setDataPosition(0);
        fromParcel = Polygon.Editor.CREATOR.createFromParcel(parcel);

        Assert.assertNotNull(fromParcel);
        Assert.assertEquals(converter.toJsonTree(polygonEditor), converter.toJsonTree(fromParcel));
    }
}
