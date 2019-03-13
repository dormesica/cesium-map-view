package com.example.mapcontroller

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.FrameLayout
import com.example.mapcontroller.event.OnMapReadyListener
import com.example.mapcontroller.location.Coordinates

/**
 * A 3D map view for android applications.
 *
 * @constructor Creates a new CesiumMapView
 */
class CesiumMapView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    companion object {
        private const val INTERFACE_MAP_READY_LISTENER = "MapReadyListener"
    }

    private val mWebView: WebView

    private var mInitialized = false
    private val mMapReadyListenersList = ArrayList<(CesiumMapView) -> Unit>(10)

    init {
        inflate(context, R.layout.cesium_map_view, this)

        mWebView = findViewById(R.id.web_view)

        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.allowFileAccessFromFileURLs = true
        mWebView.loadUrl("file:///android_asset/index.html")

        mWebView.addJavascriptInterface(object {
            @JavascriptInterface
            fun onMapReady() {
                mInitialized = true
                invokeReadyListeners()
            }
        }, INTERFACE_MAP_READY_LISTENER)
    }

    // public methods
    // --------------

    /**
     * Sets a new event listener for the <code>map-ready</code> event.
     */
    fun setOnMapReadyListener(listener: OnMapReadyListener) {
        handleMapReady { map: CesiumMapView -> listener.onMapReady(map) }
    }

//    /**
//     * Sets a new event listener for the <code>map-ready</code> event.
//     */
//    fun setOnMapReadyListener(listener: (CesiumMapView) -> Unit) = ::handleMapReady

    // private methods
    // ---------------

    private fun handleMapReady(listener: (CesiumMapView) -> Unit) {
        if (mInitialized) {
            listener(this)
            return
        }

        mMapReadyListenersList.add(listener)
    }

    private fun invokeReadyListeners() {
        mMapReadyListenersList.forEach { listener -> listener(this) }
        mMapReadyListenersList.clear()
    }
}
