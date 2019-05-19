package com.github.dormesica.mapcontroller.util;

import android.os.AsyncTask;
import android.webkit.ValueCallback;
import com.github.dormesica.mapcontroller.layers.*;
import com.google.gson.*;

/**
 * This class holds a singleton instance of Gson used to convert classes from this library.
 * <p>
 * Supports converting Entity super class and returning instance of one of it's sub classes.
 * Subclass determined by the value of a type field in the JSON object. The following values are supported:
 * <ul>
 * <li>point - instance of {@link Point}</li>
 * <li>line - instance of {@link Line}</li>
 * <li>polygon - instance of {@link Polygon}</li>
 * </ul>
 * <p>
 * This class also supports converting a JSON to a {@link Properties} instance and vice-versa.
 *
 * @since 1.0.0
 */
public class JsonConverter {

    private static final Gson sConverter;

    static {
        TypeAdapterFactory entityAdapterFactory = RuntimeTypeAdapterFactory.of(Entity.class, "type")
                .registerSubtype(Point.class, "point")
                .registerSubtype(Line.class, "line")
                .registerSubtype(Polygon.class, "polygon");

        sConverter = new GsonBuilder()
                .registerTypeAdapterFactory(entityAdapterFactory)
                .registerTypeAdapter(Properties.class, new PropertiesTypeAdapter())
                .registerTypeHierarchyAdapter(Entity.Editor.class, new EntityEditorTypeAdapter())
                .create();
    }

    /**
     * Get the converter object.
     *
     * @return The converter object.
     */
    public static Gson getConverter() {
        return sConverter;
    }

    public static void convertInBackground(Object object, ValueCallback<String> onConversionFinished) {
        ConversionTask conversionTask = new ConversionTask(onConversionFinished);
        conversionTask.execute(object);
    }

    private static class ConversionTask extends AsyncTask<Object, Void, String> {
        private ValueCallback<String> mOnConversionFinished;

        ConversionTask(ValueCallback<String> onConversionFinished) {
            mOnConversionFinished = onConversionFinished;
        }

        @Override
        protected String doInBackground(Object... objects) {
            return JsonConverter.getConverter().toJson(objects[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mOnConversionFinished.onReceiveValue(s);
        }
    }
}
