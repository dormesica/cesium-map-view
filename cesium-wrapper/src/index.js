import MapComponent from './MapComponent';

const mapDiv = document.getElementById('map');

window['mapComponent'] = new MapComponent(mapDiv);

// notify app that the map has been initialized
if (window.EventsEmitter) {
    EventsEmitter.fireOnMapReady();
}
