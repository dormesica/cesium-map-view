import LayerManager from './LayerManager';
import MapError from '../utils/MapError';

const SCALING_DEFINIIONS = new Cesium.NearFarScalar(1.5e2, 1.0, 1.5e7, 0.5);

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

    _createLayer(layer, callbackId) {
        switch (layer.type) {
            case VectorLayerManager.Types.GeoJSON:
                return loadGeoJSON(this._mapComponent._viewer, layer);
            default:
                throw MapError.invalidArgumentError('layer.type', 'VectorLayerManager._createLayer');
        }
    }

    _removeLayer(layer) {
        return layer.then(dataSource => this._mapComponent._viewer.dataSources.remove(dataSource, true));
    }
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
        source = options.url || JSON.parse(options.geoJson);
    } catch {
        return Promise.reject('Malformed JSON object');
    }

    return Cesium.GeoJsonDataSource.load(source, {
        stroke: Cesium.Color.fromCssColorString(options.outlineColor).withAlpha(options.opacity),
        fill: Cesium.Color.fromCssColorString(options.color).withAlpha(options.opacity),
    })
        .then(dataSource => {
            viewer.dataSources.add(dataSource);
            if (options.zoom) {
                viewer.zoomTo(dataSource);
            }

            dataSource.entities.values.forEach(element => handleStyle(element, options))

            return dataSource.entities.values;
        })
        .then(entities => ({
            points: entities.filter(element => element.billboard),
            lines: entities.filter(element => element.polyline),
            polygons: entities.filter(element => element.polygon),
        }))
        .then(groupsEntities => {
            const layer = {
                entities: [],
            };

            groupsEntities.points.forEach(element => {
                const entity = {
                    type: 'point',
                    id: element.id,
                    name: element.name,
                    show: element.show,
                    location: {
                        lon: 34, lat: 35
                    }
                };

                layer.entities.push(entity);                
            });
            groupsEntities.lines.forEach(element => {
                const entity = {
                    type: 'line',
                    id: element.id,
                    name: element.name,
                    show: element.show,
                };

                layer.entities.push(entity);                
            });
            groupsEntities.polygons.forEach(element => {
                const entity = {
                    type: 'polygon',
                    id: element.id,
                    name: element.name,
                    show: element.show,
                };

                layer.entities.push(entity);                
            });

            return layer;
        });
}

function handleStyle(element, options) {
    if (element.billboard) {
        element.billboard.image = options.pointIcon;
        element.billboard.heightReference = Cesium.HeightReference.RELATIVE_TO_GROUND;
        element.billboard.scaleByDistance = SCALING_DEFINIIONS;
    } else if (element.polyline) {

    } else if (element.polygon) {

    }
}

export default VectorLayerManager;
