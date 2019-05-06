package com.github.dormesica.mapcontroller;

import com.github.dormesica.mapcontroller.location.Coordinates;

/**
 * Internal class the describes the JSON received from the {@link MapView} for a click event on the map.
 * Objects of this class are later parsed into {@link com.github.dormesica.mapcontroller.event.MapTouchEvent} objects.
 */
class MapTouchDescriptor {

    int type;
    Coordinates location;
    String[] entityIds;
}
