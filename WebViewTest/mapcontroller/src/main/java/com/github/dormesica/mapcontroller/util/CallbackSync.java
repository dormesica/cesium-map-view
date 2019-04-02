package com.github.dormesica.mapcontroller.util;

import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A JavaScript interface that syncs JavaScript callbacks with Android framework callbacks.
 * <p>
 * <code>evaluateJavascript</code> calls are evaluated on a background thread (not the main/UI thread), these operation
 * require callbacks that are invoked with the returned value of the synchronous evaluation of the JavaScript code.
 * In cases the JavaScript code contains an async operation and the returned value depends on value of the async
 * operation (such as network requests) additional synchronization is required.
 * <p>
 * This class can be injected into the context of the {@link android.webkit.WebView} and be used in order to invoke
 * callbacks in those cases that evaluate asynchronous code. The callback should be registered with the
 * <code>CallbackSync</code> and is assigned an ID. This ID should then be used by the JavaScript code to invoked the
 * Specific callback with the required <code>String</code> value.
 *
 * @since 1.0.0
 */
public class CallbackSync {

    private static CallbackSync sInstance = null;

    /**
     * Returns the instance of the <code>CallbackSync</code> class.
     *
     * @return <code>CallbackSync</code> instance.
     */
    public static CallbackSync getInstance() {
        if (sInstance == null) {
            sInstance = new CallbackSync();
        }

        return sInstance;
    }

    private Map<String, ValueCallback<String>> mMap;

    private CallbackSync() {
        mMap = new HashMap<>();
    }

    /**
     * Registers a callback with the <code>CallbackSync</code>.
     *
     * @param callback The callback to be registered.
     * @return The ID of the callback.
     */
    public String register(@NonNull ValueCallback<String> callback) {
        String id = UUID.randomUUID().toString();
        mMap.put(id, callback);

        return id;
    }

    /**
     * Invokes the callback with the given ID on the the provided data.
     * @param id The ID of the callback that should be invoked.
     * @param data The data on which to invoked the callback.
     */
    @JavascriptInterface
    public void invoke(String id, @Nullable String data) {
        ValueCallback<String> callback = mMap.remove(id);
        if (callback == null) {
            return;
        }

        callback.onReceiveValue(data);
    }
}
