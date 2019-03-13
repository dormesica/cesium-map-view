import MapComponent from './MapComponent';

const mapDiv = document.getElementById('map');

// TODO only for testing - remove!!
window.EventsEmitter = {
    fireClick: console.log.bind(null, 'click\t'),
    fireLongClick: console.log.bind(null, 'long-click\t')
};

window['mapComponent'] = new MapComponent(mapDiv);

// notify app that the map has been initialized
if (window.MapReadyListener && window.MapReadyListener.onMapReady) {
    MapReadyListener.onMapReady();
}
