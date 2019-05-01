package com.github.dormesica.webviewtest

import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.github.dormesica.mapcontroller.location.Rectangle
import com.github.dormesica.mapcontroller.MapView
import com.github.dormesica.mapcontroller.event.MapClickEvent
import com.github.dormesica.mapcontroller.event.OnMapReadyListener
import com.github.dormesica.mapcontroller.layers.GeoJsonLayerDescriptor
import com.github.dormesica.mapcontroller.layers.Layer
import com.github.dormesica.mapcontroller.location.Coordinates

class MainActivity : FragmentActivity(), OnMapReadyListener {

    private lateinit var mEventDisplayTextView: TextView
    private lateinit var mButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebView.setWebContentsDebuggingEnabled(true)

        mEventDisplayTextView = findViewById(R.id.event_display)
        mButton = findViewById(R.id.extent_button)
    }

    override fun onMapReady(mapView: MapView) {
        var layerId: Layer? = null
        mapView.setOnMapReadyListener {
            Toast.makeText(this, "Map Ready", Toast.LENGTH_SHORT).show()

            val geoJson = GeoJsonLayerDescriptor.Builder.from(
                "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"string\": \"string\"},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[34.81442660093307,31.95905091161606],[34.81363534927368,31.957660474355514],[34.81424689292908,31.957319119927604],[34.81527954339981,31.958625362676848],[34.81442660093307,31.95905091161606]]]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[34.815539717674255,31.95843875770726],[34.816033244132996,31.95905773858834],[34.81635510921478,31.95888933979113],[34.816827178001404,31.959503766342912],[34.8167735338211,31.959804151161013],[34.81718659400939,31.95991338176036],[34.818050265312195,31.961096704925627],[34.81867790222168,31.961818069451187],[34.819294810295105,31.96248481458842],[34.819788336753845,31.962320973228596]]}}]}"
            )
                .shouldFocus(false)
                .build()

            mapView.load(geoJson) {
                layerId = it
            }
        }
        mapView.setOnMapClickListener { map: MapView, data: MapClickEvent ->
            mEventDisplayTextView.text = getString(R.string.click_event_text, data.location.toString())
            map.focusOn(data.location)
        }
        mapView.setOnMapLongClickListener { map: MapView, data: MapClickEvent ->
            mEventDisplayTextView.text = getString(R.string.long_click_event_text, data.location.toString())
            map.focusOn(Rectangle(Coordinates(180.0, 90.0), Coordinates(0.0, 0.0)))
        }
//        cesiumMapView.setOnMapDragListener { mapView: CesiumMapView, data: MapDragEvent ->
//            eventsDisplay.text = getString(R.string.drag_event_text,
//                data.startLocation.toString(), data.endLocation.toString())
//        }
        mapView.setOnMapTouchListener { _, data ->
            mEventDisplayTextView.text = getString(R.string.touch_event_text, data.type, data.location)
        }

        mButton.setOnClickListener {
            mapView.getViewExtent {
                mEventDisplayTextView.text = it.toString()
            }
            val layer = layerId
            if (layer == null) return@setOnClickListener
//            mapView.remove(layer) {
//                Log.d("LayerRemoved", it.toString())
//            }
            mapView.focusOn(layer)
        }
    }
}
