package com.github.dormesica.mapcontroller;

import android.os.Parcel;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.github.dormesica.mapcontroller.location.Rectangle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RectangleAndroidTest {

    private static final double WEST = 31.23;
    private static final double SOUTH = 34.12;
    private static final double EAST = 30.5;
    private static final double NORTH = 35.47;
    private static final double TOLERANCE = 0.001;

    @Test
    public void writeToParcel() {
        Parcel parcel = Parcel.obtain();

        Rectangle rectangle = new Rectangle(NORTH, WEST, SOUTH, EAST);
        rectangle.writeToParcel(parcel, rectangle.describeContents());

        parcel.setDataPosition(0);
        Rectangle fromParcel = Rectangle.CREATOR.createFromParcel(parcel);

        Assert.assertNotNull(fromParcel);
        Assert.assertEquals(NORTH, fromParcel.getNorth(), TOLERANCE);
        Assert.assertEquals(SOUTH, fromParcel.getSouth(), TOLERANCE);
        Assert.assertEquals(EAST, fromParcel.getEast(), TOLERANCE);
        Assert.assertEquals(WEST, fromParcel.getWest(), TOLERANCE);
    }
}
