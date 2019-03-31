import LayerManager from './LayerManager';
import MapError from '../utils/MapError';

/**
 * @typedef GeoJsonLayer
 * Describes the scheme for GeoJSON layers.
 * @param {String} type Layer type - should be set to GeoJSON.
 * @param {Object} json The GeoJSON to load.
 * @param {String} outlineColot CSS color string of the outline of geometries.
 * @param {String} color CSS color string of the fill of geometries.
 * @param {Number} alpha Opacity value betwween 0 and 1.
 */

class VectorLayerManager extends LayerManager {
    /**
     * Creates a new VectorLayerManager
     * @param {MapComponent} mapComponent The map component to which the layers are added.
     */
    constructor(mapComponent) {
        super(mapComponent);
    }

    _createLayer(layer) {
        switch (layer.type) {
            case VectorLayerManager.Types.GeoJSON:
                return loadGeoJSON(mapComponent._viewer, layer);
            default:
                throw MapError.invalidArgumentError('layer.type', 'VectorLayerManager._createLayer');
        }
    }

    _removeLayer(layer) {}
}

VectorLayerManager.Types = Object.freeze({
    GeoJSON: 'GeoJSON',
});

/**
 * Loads the given GeoJSON string onto the viewer.
 * @param {Cesium.Viewer} viewer The cesium viewer.
 * @param {Object} options layer options.
 * @returns {Promise<Cesium.GeoJsonDataSource} 
 */
function loadGeoJSON(viewer, options) {
    let source = null;
    try {
        source = options.url || JSON.parse(options.geoJson)
    } catch {
        return Promise.reject('Malformed JSON object');
    }

    const result = Cesium.GeoJsonDataSource.load(source, {
        stroke: Cesium.Color.fromCssColorString(options.outlineColor).withAlpha(options.opacity),
        fill: Cesium.Color.fromCssColorString(options.color).withAlpha(options.opacity),
    });
    viewer.dataSources.add(result);

    return result;
}

export default VectorLayerManager;
