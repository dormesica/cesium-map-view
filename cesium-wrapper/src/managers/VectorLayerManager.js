import LayerManager from './LayerManager';
import MapError from '../utils/MapError';
import { createEntityDescriptor } from '../utils/cesium';

const SCALING_DEFINITIONS = new Cesium.NearFarScalar(1.5e2, 1.0, 1.5e7, 0.5);

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

        this.loadGeoJSON = this.loadGeoJSON.bind(this);
    }

    _createLayer(layer) {
        switch (layer.type) {
            case VectorLayerManager.Types.GeoJSON:
                return this.loadGeoJSON(layer);
            default:
                throw MapError.invalidArgumentError('layer.type', 'VectorLayerManager._createLayer');
        }
    }

    _removeLayer(layer) {
        return this._mapComponent._viewer.dataSources.remove(layer, true);
    }

    /**
     * Loads the given GeoJSON string onto the viewer.
     * @param {Object} options layer options.
     * @returns {Promise<Cesium.GeoJsonDataSource}
     */
    loadGeoJSON(options) {
        let source = null;
        try {
            source = options.url || JSON.parse(options.geoJson);
        } catch {
            return Promise.reject('Malformed JSON object');
        }

        const promise = Cesium.GeoJsonDataSource.load(source, {
            stroke: Cesium.Color.fromCssColorString(options.outlineColor).withAlpha(options.outlineOpacity),
            fill: Cesium.Color.fromCssColorString(options.color).withAlpha(options.opacity),
        });

        return promise
            .then(dataSource => {
                this._mapComponent._viewer.dataSources.add(dataSource);
                if (options.zoom) {
                    this._mapComponent._viewer.zoomTo(dataSource);
                }

                dataSource.entities.values.forEach(element => {
                    handleStyle(element, options);
                    this._mapComponent._featuresMap.set(element.id, element);
                });

                return dataSource;
            })
            .then(dataSource => ({
                dataSource,
                layer: {
                    entities: dataSource.entities.values.map(createEntityDescriptor),
                },
            }));
    }
}

VectorLayerManager.Types = Object.freeze({
    GeoJSON: 'GeoJSON',
});

function handleStyle(element, options) {
    if (element.billboard) {
        element.billboard.image = options.pointIcon;
        element.billboard.heightReference = Cesium.HeightReference.RELATIVE_TO_GROUND;
        element.billboard.scaleByDistance = SCALING_DEFINITIONS;
    } else if (element.polyline) {
    } else if (element.polygon) {
    }
}

export default VectorLayerManager;
