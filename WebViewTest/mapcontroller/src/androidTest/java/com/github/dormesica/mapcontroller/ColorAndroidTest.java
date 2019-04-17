package com.github.dormesica.mapcontroller;

import android.os.Parcel;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.github.dormesica.mapcontroller.graphics.Color;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ColorAndroidTest {

    @Test
    public void writeToParcel() {
        final int RED = 125;
        final int GREEN = 23;
        final int BLUE = 89;
        final double ALPHA = 0.44;
        Parcel parcel = Parcel.obtain();

        Color color = new Color(RED, GREEN, BLUE, ALPHA);
        color.writeToParcel(parcel, color.describeContents());

        parcel.setDataPosition(0);
        Color fromParcel = Color.CREATOR.createFromParcel(parcel);

        Assert.assertNotNull(fromParcel);
        Assert.assertEquals(RED, fromParcel.red());
        Assert.assertEquals(GREEN, fromParcel.green());
        Assert.assertEquals(BLUE, fromParcel.blue());
        Assert.assertEquals(ALPHA, fromParcel.alpha(), 0.001);
    }
}
