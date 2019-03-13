package com.example.mapcontroller.event

import com.example.mapcontroller.CesiumMapView

interface OnMapReadyListener {

    fun onMapReady(map: CesiumMapView)
}
