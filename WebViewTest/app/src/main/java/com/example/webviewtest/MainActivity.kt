package com.example.webviewtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.location.Coordinates
import com.example.location.Rectangle
import com.example.mapcontroller.CesiumMapView
import com.example.mapcontroller.event.MapClickEvent

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
            map.flyTo(data.location)
        }
        cesiumMapView.setOnMapLongClickListener { map: CesiumMapView, data: MapClickEvent ->
            eventsDisplay.text = getString(R.string.long_click_event_text, data.location.toString())
            map.flyTo(Rectangle(Coordinates(180.0, 90.0), Coordinates(0.0, 0.0)))
        }

        val button: Button = findViewById(R.id.extent_button)
        button.setOnClickListener {
            cesiumMapView.getViewExtent(object : ValueCallback<Rectangle> {
                override fun onReceiveValue(value: Rectangle?) {
                    eventsDisplay.text = value.toString()
                }
            })
        }

    }
}
