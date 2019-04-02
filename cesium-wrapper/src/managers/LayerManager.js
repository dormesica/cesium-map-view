import uuid from 'uuid/v4';
import { checkAbstractConstructor } from '../utils/validation';
import MapError from '../utils/MapError';

class LayerManager {
    /**
     * Creates a new LayerManager
     * @param {MapComponent} mapComponent The map component to which the layers are added.
     */
    constructor(mapComponent) {
        if (checkAbstractConstructor(this, LayerManager)) {
            throw new MapError('Cannot instantiate abstract class LayerManager');
        }

        this._mapComponent = mapComponent;
        this._layers = new Map();

        this.addLayer = this.addLayer.bind(this);
        this.removeLayer = this.removeLayer.bind(this);
    }

    /**
     * Adds the given layer to the viewer.
     * layer should be a cesium layer descriptor.
     * @param {*} layer Layer descriptior
     * @param {String} callbackId The Android callback to invoke.
     */
    addLayer(layer, callbackId) {
        const layerId = uuid();

        // TODO can assume no collision
        const cesiumLayer = this._createLayer(layer);
        if (!cesiumLayer) {
            throw new MapError('Failed to create layer');
        }

        this._layers.set(layerId, cesiumLayer);

        // TODO cannot be done here
        cesiumLayer
            .then(() => CallbackSync.invoke(callbackId, layerId));
    }

    /**
     * Removes the layer with the given id.
     * @param {String} layerId The layer ID to remove.
     * @param {String} callbackId The Android callback to invoke.
     */
    removeLayer(layerId, callbackId) {
        if (!this._layers.has(layerId)) {
            return;
        }

        this._removeLayer(this._layers.get(layerId))
            .then(success => {
                let value = "false";
                if (success) {
                    this._layers.delete(layerId);
                    value = "true";
                }

                CallbackSync.invoke(callbackId, value);
            });
    }

    /**
     * Removes all layers from the viewer.
     */
    removeAllLayers() {
        this._layers.keys().array.forEach(this.removeLayer);
    }

    /**
     * Creates the layer in the view.
     * Should be implemented by any class that extends LayerManager.
     * @param {*} layer Layer descriptor
     * @returns {*} Description to be saves about the layer.
     */
    _createLayer(layer) {
        MapError.notImplementedError(this, '_createLayer');
    }

    /**
     * Removes the layer from the view.
     * Should be implemented by any class that extends LayerManager.
     * @param {*} layer A layer description that was returned from _createLayer.
     */
    _removeLayer(layer) {
        MapError.notImplementedError(this, '_removeLayer');
    }
}

export default LayerManager;
