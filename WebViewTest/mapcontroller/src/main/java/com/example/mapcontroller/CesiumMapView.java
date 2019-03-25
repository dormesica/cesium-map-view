package com.example.mapcontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.example.mapcontroller.event.MapClickEvent;
import com.example.mapcontroller.event.OnMapClickListener;
import com.example.mapcontroller.event.OnMapLongClickListener;
import com.example.mapcontroller.event.OnMapReadyListener;
import com.google.gson.Gson;

/**
 * A 3D map view for android applications.
 * <p>
 * The MapView is essentially a FrameLayout, so any operation that can be performed on a FrameLayout (or a View in
 * general) is possible. In particular, any android event, such as OnClick, are still emitted by the CesiumMapView.
 * For map specific events there are numerous methods that register listeners for such events.
 * <p>
 * When the map is ready to be interacted with, an event is fired to notify the use. Any attempt to interact with the
 * map before this event is fired (e.g. by setting the focus to a specific location) will fail silently.
 *
 * @since 1.0.0
 */
public class CesiumMapView extends FrameLayout {

    private static final String JS_INTERFACE_EVENTS_EMITTER = "EventsEmitter";
    private static final String JS_MAP_NAME = "mapComponent";

    private static final String TAG_MAP_VIEW_EVENT = "CesiumMapView.Event";

    // properties
    private final WebView mWebView;

    private final Gson mGson;
    private final Handler mHandler;

    private boolean mIsInitialized;

    // Event listeners
    private OnMapReadyListener mOnMapReadyListener = null;
    private OnMapClickListener mOnMapClickListener = null;
    private OnMapLongClickListener mOnMapLongClickListener = null;

    /**
     * Creates a new <code>CesiumMapView</code> instance.
     *
     * @param context an Activity Context to access application assets
     * @param attrs   an AttributeSet passed to our parent
     */
    public CesiumMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGson = new Gson();
        mHandler = new Handler();

        inflate(context, R.layout.cesium_map_view, this);
        mWebView = findViewById(R.id.web_view);

        setUpMap();
    }

    /**
     * Gets whether the map has been initialized and ready to be interacted with.
     *
     * @return <code>true</code> if the map is ready, otherwise <code>false</code>.
     */
    public boolean isInitialized() {
        return mIsInitialized;
    }

    /**
     * Registers a callback to be invoked when the map is ready and can be interacted with.
     *
     * @param listener The callback that will run.
     */
    public void setOnMapReadyListener(OnMapReadyListener listener) {
        if (mIsInitialized) {
            listener.onMapReady(this);
            return;
        }

        mOnMapReadyListener = listener;
    }

    /**
     * Registers a callback to be invoked when a location on the map is clicked.
     * <strong>Note:</strong> This callback will not be invoked if the clicked location cannot be associated with a set of
     * coordinates (e.g. a clicked performed on space).
     *
     * @param listener The callback that will run.
     */
    public void setOnMapClickListener(OnMapClickListener listener) {
        mOnMapClickListener = listener;
    }

    /**
     * Registers a callback to be invoked when a location on the map is clicked and held.
     * <strong>Note:</strong> This callback will not be invoked if the clicked location cannot be associated with a set of
     * coordinates (e.g. a clicked performed on space).
     *
     * @param listener The callback that will run.
     */
    public void setOnMapLongClickListener(OnMapLongClickListener listener) {
        mOnMapLongClickListener = listener;
    }

    /**
     * Sets up the configuration of the WebView and initializes the Cesium map.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void setUpMap() {
        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);

        mWebView.addJavascriptInterface(new EventsEmitter(), JS_INTERFACE_EVENTS_EMITTER);

        mWebView.loadUrl("file:///android_asset/index.html");
    }

    private class EventsEmitter {
        @JavascriptInterface
        public void fireOnMapReady() {
            Log.d(TAG_MAP_VIEW_EVENT, "MAP_READY");

            mIsInitialized = true;

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mOnMapReadyListener.onMapReady(CesiumMapView.this);
                }
            });
        }

        @JavascriptInterface
        public void fireOnClick(final String jsonDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "CLICK");

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mOnMapClickListener.onMapClick(
                            CesiumMapView.this, mGson.fromJson(jsonDataString, MapClickEvent.class));
                }
            });
        }

        @JavascriptInterface
        public void fireOnLongClick(final String jsonDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "LONG_CLICK");

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mOnMapLongClickListener.onMapLongClick(
                            CesiumMapView.this, mGson.fromJson(jsonDataString, MapClickEvent.class));
                }
            });
        }
    }
}
