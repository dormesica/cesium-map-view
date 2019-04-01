package com.github.dormesica.webviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.dormesica.mapcontroller.location.Coordinates
import com.github.dormesica.mapcontroller.location.Rectangle
import com.github.dormesica.mapcontroller.widgets.MapView
import com.github.dormesica.mapcontroller.event.MapClickEvent
import com.github.dormesica.mapcontroller.event.MapTouchEvent
import com.github.dormesica.mapcontroller.layers.GeoJsonLayer
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebView.setWebContentsDebuggingEnabled(true)

        val mapView: MapView = findViewById(R.id.cesium_map_view)
        val eventsDisplay: TextView = findViewById(R.id.event_display)

        var layerId = ""
        mapView.setOnMapReadyListener {
            Toast.makeText(this, "Map Ready", Toast.LENGTH_SHORT).show()

            val geoJson = GeoJsonLayer.Builder.from(
                URL("https://cesiumjs.org/Cesium/Apps/SampleData/simplestyles.geojson")
            )
                .build()

            mapView.load(geoJson) {
                layerId = it
            }
        }
        mapView.setOnMapClickListener { map: MapView, data: MapClickEvent ->
            eventsDisplay.text = getString(R.string.click_event_text, data.location.toString())
            map.focusOn(data.location)
        }
        mapView.setOnMapLongClickListener { map: MapView, data: MapClickEvent ->
            eventsDisplay.text = getString(R.string.long_click_event_text, data.location.toString())
            map.focusOn(Rectangle(Coordinates(180.0, 90.0), Coordinates(0.0, 0.0)))
        }
//        cesiumMapView.setOnMapDragListener { map: CesiumMapView, data: MapDragEvent ->
//            eventsDisplay.text = getString(R.string.drag_event_text,
//                data.startLocation.toString(), data.endLocation.toString())
//        }
        mapView.setOnMapTouchListener { map: MapView, data: MapTouchEvent ->
            eventsDisplay.text = getString(R.string.touch_event_text, data.type, data.location)
        }

        val button: Button = findViewById(R.id.extent_button)
        button.setOnClickListener {
//            mapView.getViewExtent {
//                eventsDisplay.text = it.toString()
//            }
            mapView.remove(layerId) {
                Log.d("LayerRemoved", it.toString())
            }
        }
    }
}
