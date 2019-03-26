package com.github.dormesica.webviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.dormesica.location.Coordinates
import com.github.dormesica.location.Rectangle
import com.github.dormesica.mapcontroller.CesiumMapView
import com.github.dormesica.mapcontroller.event.MapClickEvent
import com.github.dormesica.mapcontroller.event.MapDragEvent
import com.github.dormesica.mapcontroller.event.MapTouchEvent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebView.setWebContentsDebuggingEnabled(true)

        val cesiumMapView: CesiumMapView = findViewById(R.id.cesium_map_view)
        val eventsDisplay: TextView = findViewById(R.id.event_display)

        cesiumMapView.setOnMapReadyListener {
            Toast.makeText(this, "Map Ready", Toast.LENGTH_SHORT).show()
        }
        cesiumMapView.setOnMapClickListener { map: CesiumMapView, data: MapClickEvent ->
            eventsDisplay.text = getString(R.string.click_event_text, data.location.toString())
            map.focusOn(data.location)
        }
        cesiumMapView.setOnMapLongClickListener { map: CesiumMapView, data: MapClickEvent ->
            eventsDisplay.text = getString(R.string.long_click_event_text, data.location.toString())
            map.focusOn(Rectangle(Coordinates(180.0, 90.0), Coordinates(0.0, 0.0)))
        }
        cesiumMapView.setOnMapDragListener { map: CesiumMapView, data: MapDragEvent ->
            eventsDisplay.text = getString(R.string.drag_event_text,
                data.startLocation.toString(), data.endLocation.toString())
        }
        cesiumMapView.setOnMapTouchListener { map: CesiumMapView, data: MapTouchEvent ->
            eventsDisplay.text = getString(R.string.touch_event_text, data.type, data.location)
        }

        val button: Button = findViewById(R.id.extent_button)
        button.setOnClickListener {
            cesiumMapView.getViewExtent {
                eventsDisplay.text = it.toString()
            }
        }

    }
}
