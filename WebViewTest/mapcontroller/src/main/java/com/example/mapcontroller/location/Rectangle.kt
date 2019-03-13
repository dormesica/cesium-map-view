package com.example.mapcontroller.location

data class Rectangle(val north: Double, val south: Double, val east: Double, val west: Double) {

    override fun toString(): String {
        return "{ north: $north, south: $south, east: $east, west: $west }"
    }
}
