package com.example.webviewtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mapcontroller.CesiumMapView
import com.example.mapcontroller.event.OnMapReadyListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cesiumMapView: CesiumMapView = findViewById(R.id.cesium_map_view)
        cesiumMapView.setOnMapReadyListener(object: OnMapReadyListener {
            override fun onMapReady(map: CesiumMapView) {
                Toast.makeText(this@MainActivity, "Map Ready", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
