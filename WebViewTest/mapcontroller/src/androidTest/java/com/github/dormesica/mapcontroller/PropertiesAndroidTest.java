package com.github.dormesica.mapcontroller;

import android.os.Parcel;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.github.dormesica.mapcontroller.layers.Properties;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.gson.JsonElement;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class PropertiesAndroidTest {

    @Test
    public void writeToParcel() {
        final String JSON_STRING = "{ " +
                "\"string\": \"string\", " +
                "\"number\": 2.34, " +
                "\"boolean\": true, " +
                "\"inner\": { \"string\": \"inner\" } " +
                "}";
        Parcel parcel = Parcel.obtain();

        Properties properties = JsonConverter.getConverter().fromJson(JSON_STRING, Properties.class);
        properties.writeToParcel(parcel, properties.describeContents());

        parcel.setDataPosition(0);
        Properties fromParcel = Properties.CREATOR.createFromParcel(parcel);

        Assert.assertNotNull(fromParcel);
        Set<String> keys = properties.keySet();
        Assert.assertEquals(keys, fromParcel.keySet());

        for (String key : keys) {
            Assert.assertEquals(properties.getAs(key, JsonElement.class), fromParcel.getAs(key, JsonElement.class));
        }
    }
}
