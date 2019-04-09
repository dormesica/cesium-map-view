import { areCoordiantesValid } from './utils/validation';
import MapError from './utils/MapError';
import { convertRadiansToDegrees } from './utils/math';
import EventsHandler from './EventsHandler';
import VectorLayerManager from './managers/VectorLayerManager';

/**
 * @typedef {Object} Coordinates
 * A set of geographic coordinates with an optional altitude value
 * @property {number} lon the longiture
 * @property {number} lat the latitude
 * @property {number} [alt] the altitude
 */

/**
 * @typedef {Object} Rectangle
 * A geographic rectable where the north and south are latiture values and
 * the east and west are longiture values
 * @property {Coordinates} northWest The north-west corner.
 * @property {Coordinates} southEast The south-east corner.
 */

/**
 * Class that represents a map.
 */
export default class MapComponent {
    /**
     * Creates a new MapComponent instance inside the given container.
     * @param {HTMLElement} container The element in which the map will be placed.
     * @constructor
     */
    constructor(container) {
        this._container = container;

        this._initializeMap();
        this._eventsHandler = new EventsHandler(this, 500);
        this._vectorLayerManager = new VectorLayerManager(this);

        this._featuresMap = new Map();
    }

    get vectorLayerManager() {
        return this._vectorLayerManager;
    }

    /**
     * Gets the amount of milliseconds required for a click to become a long-click
     * @returns {number} The long click delay in milliseconds
     */
    get longClickDelay() {
        return this._eventsHandler.longClickDelay;
    }

    /**
     * Sets the amount of milliseconds for a click to become a long-click.
     * @param {number} longClickDelay New delay for long click in milliseconds
     */
    set longClickDelay(longClickDelay) {
        this._eventsHandler.longClickDelay = longClickDelay;
    }

    /**
     * Positions the camera above the given position
     * @param {Coordinates|Rectangle} location The location on which to focus
     */
    focusOn(location) {
        const options = { duration: 1 };
        const locationHasOwnProperty = Object.prototype.hasOwnProperty.bind(location);

        if (['lon', 'lat'].every(locationHasOwnProperty) && areCoordiantesValid(location)) {
            options.destination = Cesium.Cartesian3.fromDegrees(location.lon, location.lat, location.alt || 350);
        } else if (
            ['northWest', 'southEast'].every(locationHasOwnProperty) &&
            Object.values(location).every(areCoordiantesValid)
        ) {
            options.destination = Cesium.Rectangle.fromDegrees(
                location.northWest.lon,
                location.southEast.lat,
                location.southEast.lon,
                location.northWest.lat
            );
        } else {
            throw MapError.invalidArgumentError('location', 'MapComponent.flyTo');
        }

        this._viewer.camera.flyTo(options);
    }

    /**
     * Focuses the camera on the given data. 
     * Data can be a vector layer or an entity.
     * @param {string} dataId ID of the data
     * @param {*} dataType The type of the data
     */
    focusOnData(dataId, dataType) {
        let target = null;
        if (dataType === 'entity' && this._featuresMap.has(dataId)) {
           target = this._featuresMap.get(dataId); 
        } else if (dataType === 'vector-layer' && this.vectorLayerManager.has(dataId)) {
            target = this._vectorLayerManager.get(dataId);
        } else {
            throw MapError.invalidArgumentError('');
        }

        this._viewer.flyTo(target, { duration: 1 });
    }

    /**
     * Returns the extent of the current view.
     * @returns {Rectangle}
     */
    getViewExtent() {
        const cesiumExtent = this._viewer.camera.computeViewRectangle();

        const extent = {
            northWest: {
                lon: convertRadiansToDegrees(cesiumExtent.west),
                lat: convertRadiansToDegrees(cesiumExtent.north),
            },
            southEast: {
                lon: convertRadiansToDegrees(cesiumExtent.east),
                lat: convertRadiansToDegrees(cesiumExtent.south),
            },
        };

        return extent;
    }

    /**
     * Tries to convert the given screen pixel to coordinates on the ellipsoid.
     * If fails return null.
     * @param {Pixel} pixel The pixel on the screen.
     * @returns {Coordinates|null} The set of Coordinates under the given pixel or null.
     */
    convertPixelToCoordinates(pixel) {
        // TODO validate input

        const cartesianPosition = this._viewer.camera.pickEllipsoid(pixel, this._viewer.scene.globe.ellipsoid);
        if (!cartesianPosition) {
            return null;
        }

        const geographicPosition = Cesium.Cartographic.fromCartesian(cartesianPosition);
        return {
            lon: Cesium.Math.toDegrees(geographicPosition.longitude),
            lat: Cesium.Math.toDegrees(geographicPosition.latitude),
            alt: geographicPosition.height,
        };
    }

    /**
     * Initializes the cesium instance.
     */
    _initializeMap() {
        if (PRODUCTION) {
            viewerOptions.imageryProvider = new Cesium.UrlTemplateImageryProvider({
                url: '.',
                maximumLevel: 0,
            });
        }

        this._viewer = new Cesium.Viewer(this._container, viewerOptions);

        // remove the cesium button from the viewer window
        const cesiumViewerElement = this._container.getElementsByClassName('cesium-viewer')[0];
        const cesiumLogo = cesiumViewerElement.getElementsByClassName('cesium-viewer-bottom')[0];
        const cesiumToolbar = cesiumViewerElement.getElementsByClassName('cesium-viewer-toolbar')[0];
        cesiumViewerElement.removeChild(cesiumLogo);
        cesiumViewerElement.removeChild(cesiumToolbar);
    }
}

const viewerOptions = {
    // cesium widgets
    animation: false,
    baseLayerPicker: false,
    fullscreenButton: false,
    geocoder: false,
    homeButton: false,
    infoBox: false,
    sceneModePicker: false,
    selectionIndicator: false,
    timeline: false,
    navigationHelpButton: false,

    // performance
    requestRenderMode: false,
    maximumRenderTimeChange: Infinity,
    contextOptions: {
        alpha: false,
        depth: true,
        stencil: false,
        antialias: false,
        premultipliedAlpha: true,
        preserveDrawingBuffer: false,
        failIfMajorPerformanceCaveat: false,
    },
};
