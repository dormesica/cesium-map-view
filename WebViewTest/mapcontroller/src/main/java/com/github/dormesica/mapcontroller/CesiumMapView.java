package com.github.dormesica.mapcontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.github.dormesica.location.Coordinates;
import com.github.dormesica.location.Rectangle;
import com.github.dormesica.mapcontroller.event.*;
import com.google.gson.Gson;

/**
 * A 3D map view for android applications.
 * <p>
 * The MapView is essentially a FrameLayout, and as such any operation that can be performed on a FrameLayout
 * (or a View in general) is possible. In particular, any android event, such as OnClick, are still emitted by the
 * CesiumMapView. For map specific events there are numerous methods that register listeners for such events.
 * <p>
 * When the map is ready to be interacted with, an event is fired to notify the use. Any attempt to interact with the
 * map before this event is fired (e.g. by setting the focus to a specific location) will fail silently.
 *
 * @since 1.0.0
 */
public class CesiumMapView extends FrameLayout {

    /**
     * The name of the events emitter interface in the JavaScript context.
     */
    private static final String JS_INTERFACE_EVENTS_EMITTER = "EventsEmitter";
    /**
     * The name of the map component in the JavaScript context.
     */
    private static final String JS_MAP_NAME = "mapComponent";

    /**
     * Tag for events log from JavaScript.
     */
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
    private OnMapDragListener mOnMapDragListener = null;
    private OnMapTouchListener mOnMapTouchListener = null;

    /**
     * Creates a new <code>CesiumMapView</code> instance.
     *
     * @param context An Activity Context to access application assets
     * @param attrs   An AttributeSet passed to our parent
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
     *
     * <strong>Note:</strong> This callback will not be invoked if the clicked location cannot be associated with
     * a set of coordinates (e.g. a clicked performed on space).
     *
     * @param listener The callback that will run.
     */
    public void setOnMapClickListener(OnMapClickListener listener) {
        mOnMapClickListener = listener;
    }

    /**
     * Registers a callback to be invoked when a location on the map is clicked and held.
     *
     * <strong>Note:</strong> This callback will not be invoked if the clicked location cannot be associated with
     * a set of coordinates (e.g. a clicked performed on space).
     *
     * @param listener The callback that will run.
     */
    public void setOnMapLongClickListener(OnMapLongClickListener listener) {
        mOnMapLongClickListener = listener;
    }

    /**
     * Registers a callback to be invoked when the map is dragged.
     * <p>
     * <strong>Note:</strong> This callback will not be invoked if the drag occurred somewhere in space.
     * <p>
     * <strong>Note:</strong> This callback may be invoked several times during a single drag gesture (before the finger
     * is lifted from the screen).
     *
     * @param listener The callback that will run.
     */
    public void setOnMapDragListener(OnMapDragListener listener) {
        mOnMapDragListener = listener;
    }

    /**
     * Registers a callback to be invoked when the map is dragged.
     * <p>
     * A touch occurs when a is either set on the screen (<code>DOWN</code>) or lifted off the screen (<code>UP</code>).
     *
     * <strong>NOTE:</strong> This callback will not be invoked if the clicked location cannot be associated with
     * a set of coordinates (e.g. a clicked performed on space).
     *
     * @param listener The callback that will run.
     */
    public void setOnMapTouchListener(OnMapTouchListener listener) {
        mOnMapTouchListener = listener;
    }

    /**
     * Focuses the view on the given coordinates.
     * <p>
     * If height is not provided in the given <code>location</code>, the altitude is set to 350m.
     *
     * @param location The coordinates on which to focus.
     */
    public void focusOn(Coordinates location) {
        mWebView.evaluateJavascript(createFocusScript(location), null);
    }

    /**
     * Focuses the view on the given extent.
     *
     * @param extent The extent on which to focus
     */
    public void focusOn(Rectangle extent) {
        mWebView.evaluateJavascript(createFocusScript(extent), null);
    }

    /**
     * Asynchronously evaluates the extent of the current view. <code>callback</code> will be invoked
     * with a <code>Rectangle</code> that represents the current extent.
     *
     * @param callback Called when the evaluation completes.
     */
    public void getViewExtent(ValueCallback<Rectangle> callback) {
        String script = String.format("%s.getViewExtent();", JS_MAP_NAME);

        mWebView.evaluateJavascript(script,
                (String result) -> callback.onReceiveValue(mGson.fromJson(result, Rectangle.class)));
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

    /**
     * Generates the JavaScript code that activates the focusOn function on the cesium view.
     *
     * @param location The location on which to focus.
     * @return The JavaScript code that focuses on <code>location</code>.
     */
    private String createFocusScript(Object location) {
        return String.format("%s.focusOn(%s);", JS_MAP_NAME, mGson.toJson(location));
    }

    /**
     * Inner class used as a bridge between JavaScript and Android.
     * Contains the logic that should be executed when receiving an event from the JavaScript map.
     */
    private class EventsEmitter {
        @JavascriptInterface
        public void fireOnMapReady() {
            Log.d(TAG_MAP_VIEW_EVENT, "MAP_READY");

            mIsInitialized = true;

            if (mOnMapReadyListener != null) {
                mHandler.post(() -> mOnMapReadyListener.onMapReady(CesiumMapView.this));
            }
        }

        @JavascriptInterface
        public void fireOnClick(final String eventDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "CLICK");

            if (mOnMapClickListener != null) {
                mHandler.post(() -> mOnMapClickListener.onClick(
                        CesiumMapView.this, mGson.fromJson(eventDataString, MapClickEvent.class)));
            }
        }

        @JavascriptInterface
        public void fireOnLongClick(final String eventDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "LONG_CLICK");

            if (mOnMapLongClickListener != null) {
                mHandler.post(() -> mOnMapLongClickListener.onLongClick(
                        CesiumMapView.this, mGson.fromJson(eventDataString, MapClickEvent.class)));
            }
        }

        @JavascriptInterface
        public void fireOnDrag(final String eventDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "DRAG");

            if (mOnMapDragListener != null) {
                mHandler.post(() -> mOnMapDragListener.onDrag(
                        CesiumMapView.this, mGson.fromJson(eventDataString, MapDragEvent.class)));
            }
        }

        @JavascriptInterface
        public void fireOnTouch(final String eventDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "TOUCH");

            if (mOnMapTouchListener != null) {
                mHandler.post(() -> mOnMapTouchListener.onTouch(
                        CesiumMapView.this, mGson.fromJson(eventDataString, MapTouchEvent.class)));
            }
        }
    }
}
