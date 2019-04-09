package com.github.dormesica.mapcontroller.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.*;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.github.dormesica.mapcontroller.R;
import com.github.dormesica.mapcontroller.layers.*;
import com.github.dormesica.mapcontroller.location.Coordinates;
import com.github.dormesica.mapcontroller.location.Rectangle;
import com.github.dormesica.mapcontroller.event.*;
import com.github.dormesica.mapcontroller.util.CallbackSync;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.gson.Gson;

/**
 * A view which displays a 3D map.
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
public class MapView extends FrameLayout {

    /**
     * The name of the events emitter interface in the JavaScript context.
     */
    private static final String JS_INTERFACE_EVENTS_EMITTER = "EventsEmitter";
    /**
     * The name of the CallbackSync interface in the JavaScript context.
     */
    private static final String JS_INTERFACE_CALLBACK_SYNC = "CallbackSync";
    /**
     * The name of the map component in the JavaScript context.
     */
    private static final String JS_MAP_NAME = "mapComponent";
    /**
     * The layer manager name of the JavaScript map component
     */
    private static final String JS_VECTOR_LAYER_MANAGER = "vectorLayerManager";

    /**
     * Script format for focusOn operations
     */
    private static final String SCRIPT_FOCUS_ON = JS_MAP_NAME + ".focusOn(%s);";
    /**
     * Script format for addLayer operations.
     * First string is the layer manger, second is the layer ID, third is the callback ID to invoke.
     */
    private static final String SCRIPT_ADD_LAYER = JS_MAP_NAME + ".%s.addLayer(%s, \"%s\");";
    /**
     * Script format for removeLayer operations.
     * First string is the layer manger, second is the layer ID, third is the callback ID to invoke.
     */
    private static final String SCRIPT_REMOVE_LAYER = JS_MAP_NAME + ".%s.removeLayer(\"%s\", \"%s\");";

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
    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGson = JsonConverter.getConverter();
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
    public void setOnMapTouchListener(@NonNull OnMapTouchListener listener) {
        mOnMapTouchListener = listener;
    }

    /**
     * Focuses the view on the given coordinates.
     * <p>
     * If height is not provided in the given <code>location</code>, the altitude is set to 350m.
     *
     * @param location The coordinates on which to focus.
     */
    public void focusOn(@NonNull Coordinates location) {
        mWebView.evaluateJavascript(String.format(SCRIPT_FOCUS_ON, location), null);
    }

    /**
     * Focuses the view on the given extent.
     *
     * @param extent The extent on which to focus
     */
    public void focusOn(@NonNull Rectangle extent) {
        mWebView.evaluateJavascript(String.format(SCRIPT_FOCUS_ON, extent), null);
    }

    /**
     * Asynchronously evaluates the extent of the current view. <code>callback</code> will be invoked
     * with a <code>Rectangle</code> that represents the current extent.
     *
     * @param callback Called when the evaluation completes.
     */
    public void getViewExtent(@NonNull ValueCallback<Rectangle> callback) {
        String script = String.format("%s.getViewExtent();", JS_MAP_NAME);

        mWebView.evaluateJavascript(script,
                (String result) -> callback.onReceiveValue(mGson.fromJson(result, Rectangle.class)));
    }

    /**
     * Asynchronously loads the given GeoJSON layer onto the map. <code>callback</code> is invoked with the layers ID
     * when the operation completes. If the layer failed to be added to the map the value passed to the callback is
     * <code>null</code>.
     * <p>
     * The layer ID should be used in future manipulations on the layer.
     *
     * @param layer    The layer to be loaded
     * @param callback a callback to be invoked with the layer ID when the operation completes.
     */
    public void load(@NonNull GeoJsonLayerDescriptor layer, @NonNull ValueCallback<VectorLayer> callback) {
        String callbackId = CallbackSync.getInstance().register((String layerJsonString) ->
                callback.onReceiveValue(mGson.fromJson(layerJsonString, VectorLayer.class)));
        String script = String.format(SCRIPT_ADD_LAYER, JS_VECTOR_LAYER_MANAGER, mGson.toJson(layer), callbackId);

        mWebView.evaluateJavascript(script, null);
    }

    // TODO test
    /**
     * Asynchronously removes a layer from the map. <code>callback</code> is invoked when the operation completes
     * with a boolean value that indicates whether the operation succeeded or not.
     *
     * @param layerId The ID of the layer to be removed.
     * @param callback Called when the layer is removed or upon failure.
     */
    public void remove(@NonNull Layer layerId, ValueCallback<Boolean> callback) {
        String callbackId = CallbackSync.getInstance()
                .register((String result) -> callback.onReceiveValue(result.equals("true")));
        String script = String.format(SCRIPT_REMOVE_LAYER, JS_VECTOR_LAYER_MANAGER, layerId.getId(), callbackId);

        mWebView.evaluateJavascript(script, null);
    }

    /**
     * Sets up the configuration of the WebView and initializes the Cesium map.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void setUpMap() {
        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowFileAccess(false);

        mWebView.setWebViewClient(new WebViewClient());

        mWebView.addJavascriptInterface(new EventsEmitter(), JS_INTERFACE_EVENTS_EMITTER);
        mWebView.addJavascriptInterface(CallbackSync.getInstance(), JS_INTERFACE_CALLBACK_SYNC);

        mWebView.loadUrl("file:///android_asset/index.html");
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
                mHandler.post(() -> mOnMapReadyListener.onMapReady(MapView.this));
            }
        }

        @JavascriptInterface
        public void fireOnClick(final String eventDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "CLICK");

            if (mOnMapClickListener != null) {
                mHandler.post(() -> mOnMapClickListener.onClick(
                        MapView.this, mGson.fromJson(eventDataString, MapClickEvent.class)));
            }
        }

        @JavascriptInterface
        public void fireOnLongClick(final String eventDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "LONG_CLICK");

            if (mOnMapLongClickListener != null) {
                mHandler.post(() -> mOnMapLongClickListener.onLongClick(
                        MapView.this, mGson.fromJson(eventDataString, MapClickEvent.class)));
            }
        }

        @JavascriptInterface
        public void fireOnDrag(final String eventDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "DRAG");

            if (mOnMapDragListener != null) {
                mHandler.post(() -> mOnMapDragListener.onDrag(
                        MapView.this, mGson.fromJson(eventDataString, MapDragEvent.class)));
            }
        }

        @JavascriptInterface
        public void fireOnTouch(final String eventDataString) {
            Log.d(TAG_MAP_VIEW_EVENT, "TOUCH");

            if (mOnMapTouchListener != null) {
                mHandler.post(() -> mOnMapTouchListener.onTouch(
                        MapView.this, mGson.fromJson(eventDataString, MapTouchEvent.class)));
            }
        }
    }
}
