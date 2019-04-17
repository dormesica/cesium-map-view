package com.github.dormesica.mapcontroller;

import android.os.Parcel;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.github.dormesica.mapcontroller.location.Coordinates;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CoordinatesAndroidTest {

    @Test
    public void writeToParcel() {
        final double LON = 35.42;
        final double LAT = 31.21;
        final double ALT = 8.5;
        Parcel parcel = Parcel.obtain();

        Coordinates coordinates = new Coordinates(LON, LAT, ALT);
        coordinates.writeToParcel(parcel, coordinates.describeContents());

        parcel.setDataPosition(0);
        Coordinates fromParcel = Coordinates.CREATOR.createFromParcel(parcel);

        Assert.assertNotNull(fromParcel);
        Assert.assertEquals(LON, fromParcel.getLon(), 0.001);
        Assert.assertEquals(LAT, fromParcel.getLat(), 0.001);
        Assert.assertEquals(ALT, fromParcel.getAlt(), 0.001);
    }
}
