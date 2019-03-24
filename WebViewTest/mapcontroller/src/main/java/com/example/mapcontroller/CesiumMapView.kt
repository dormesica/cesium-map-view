package com.example.mapcontroller

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.FrameLayout
import com.example.mapcontroller.event.*
import com.google.gson.Gson

/**
 * A 3D map view for android applications.
 *
 * The MapView is essentially a FrameLayout, so any operation that can be performed on a FrameLayout (or a View in
 * general) is possible. In particular, any android event, such as OnClick, are still emitted by the CesiumMapView.
 * For map specific events there are numerous methods that register listeners for such events.
 *
 * When the map is ready to be interacted with, an event is fired to notify the use. Any attempt to interact with the
 * map before this event is fired (e.g. by setting the focus to a specific location) will fail silently.
 *
 * @constructor Creates a new CesiumMapView
 * @since 1.0.0
 */
class CesiumMapView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    companion object {
        private const val JS_INTERFACE_EVENTS_EMITTER = "EventsEmitter"
        private const val JS_MAP_NAME = "mapComponent"

        private const val TAG_MAP_VIEW_EVENT = "CesiumMapView.Event"
    }

    private val mWebView: WebView

    private val mGson: Gson
    private val mHandler: Handler

    private var mInitialized = false

    // Events listeners
    private var mOnMapReadyListener: ((CesiumMapView) -> Unit)? = null
    private var mOnMapClickListener: ((CesiumMapView, MapClickEvent) -> Unit)? = null
    private var mOnMapLongClickListener: ((CesiumMapView, MapClickEvent) -> Unit)? = null

    init {
        inflate(context, R.layout.cesium_map_view, this)

        mGson = Gson()
        mHandler = Handler()

        mWebView = findViewById(R.id.web_view)

        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.allowFileAccessFromFileURLs = true

        // TODO send events emitter the value for a long press
        mWebView.addJavascriptInterface(EventsEmitter(), JS_INTERFACE_EVENTS_EMITTER)

        mWebView.loadUrl("file:///android_asset/index.html")
    }

    // public methods
    // --------------

    /**
     * Gets whether the map has been initialized.
     * @return <code>true</code> if the map is ready, otherwise <code>false</code>.
     */
    val isInitialized: Boolean
        get() = mInitialized

    /**
     * Registers a callback to be invoked when the map is ready and can be interacted with.
     *
     * @param [listener] The callback that will run.
     */
    fun setOnMapReadyListener(listener: OnMapReadyListener) {
        if (mInitialized) {
            listener.onMapReady(this)
            return
        }

        mOnMapReadyListener = listener::onMapReady
    }

    /**
     * Registers a callback to be invoked when a location on the map is clicked.
     * <strong>Note:</strong> This callback will not be invoked if the clicked location cannot be associated with a set of
     * coordinates (e.g. a clicked performed on space).
     *
     * @param [listener] The callback that will run.
     */
    fun setOnMapClickListener(listener: OnMapClickListener) {
        mOnMapClickListener = listener::onMapClick
    }

    /**
     * Registers a callback to be invoked when a location on the map is clicked and held.
     * <strong>Note:</strong> This callback will not be invoked if the clicked location cannot be associated with a set of
     * coordinates (e.g. a clicked performed on space).
     *
     * @param [listener] The callback that will run.
     */
    fun setOnMapLongClickListener(listener: OnMapLongClickListener) {
        mOnMapLongClickListener = listener::onMapLongClick
    }


    private inner class EventsEmitter {
        @JavascriptInterface
        fun fireOnMapReady() {
            Log.d(TAG_MAP_VIEW_EVENT, "MAP_READY")

            mInitialized = true

            mHandler.post {
                mOnMapReadyListener?.invoke(this@CesiumMapView)
            }
        }

        @JavascriptInterface
        fun fireOnClick(jsonDataString: String) {
            Log.d(TAG_MAP_VIEW_EVENT, "CLICK")

            mHandler.post {
                mOnMapClickListener?.invoke(
                    this@CesiumMapView,
                    mGson.fromJson(jsonDataString, MapClickEvent::class.java)
                )
            }
        }

        @JavascriptInterface
        fun fireOnLongClick(jsonDataString: String) {
            Log.d(TAG_MAP_VIEW_EVENT, "LONG_CLICK")

            mHandler.post {
                mOnMapLongClickListener?.invoke(
                    this@CesiumMapView,
                    mGson.fromJson(jsonDataString, MapClickEvent::class.java)
                )
            }
        }

        @JavascriptInterface
        fun fireOnTouch(jsonDataString: String) {
            Log.d(TAG_MAP_VIEW_EVENT, "TOUCH")

            TODO("Implement")
        }

        @JavascriptInterface
        fun fireOnDrag(jsonDataString: String) {
            Log.d(TAG_MAP_VIEW_EVENT, "DRAG")

            TODO("Implement")
        }
    }
}

/**
 * @param [listener]
 * @receiver CesiumMapView
 * @since 1.0.0
 */
fun CesiumMapView.setOnMapReadyListener(listener: (CesiumMapView) -> Unit) =
    setOnMapReadyListener(object : OnMapReadyListener {
        override fun onMapReady(map: CesiumMapView) = listener(map)
    })

/**
 * @param [listener]
 * @receiver CesiumMapView
 * @since 1.0.0
 */
fun CesiumMapView.setOnMapClickListener(listener: (CesiumMapView, MapClickEvent) -> Unit) =
    setOnMapClickListener(object : OnMapClickListener {
        override fun onMapClick(map: CesiumMapView, clickEvent: MapClickEvent) = listener(map, clickEvent)
    })

/**
 * @param [listener]
 * @receiver CesiumMapView
 * @since 1.0.0
 */
fun CesiumMapView.setOnMapLongClickListener(listener: (CesiumMapView, MapClickEvent) -> Unit) =
    setOnMapLongClickListener(object : OnMapLongClickListener {
        override fun onMapLongClick(map: CesiumMapView, clickEvent: MapClickEvent) = listener(map, clickEvent)
    })
