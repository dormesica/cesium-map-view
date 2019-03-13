package com.example.mapcontroller.location

data class Coordinates(val lon: Double, val lat: Double) {

    override fun toString(): String {
        return "{ lon: $lon, lat: $lat }"
    }
}
