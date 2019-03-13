import { zip, upperCase } from 'lodash';
import MapComponent from './MapComponent';

/**
 * @typedef {Object} Pixel
 * A pixel on the canvas.
 * @property {number} i The i of the pixel.
 * @property {number} j The j of the pixel.
 */

/**
 * A class that handles the conversion of cesium events to Android-like events.
 * Instances of this class should be interacted with directly.
 */
export default class EventsHandler {
    // Event handling Logic
    // --------------------
    // In order to determine if a click is in fact a long-click, the following mechanism is used:
    // An instance of EventsHandler contains a property: _lastLeftDownTimeStamp that stores the time stamp of
    // the last LEFT_DOWN event. 
    // When a click event is fired its handler calculates the duration of the press
    // by subtracting _lastLeftDownTimeStamp from the value of the current time stamp.
    // Since LEFT_DOWN event is the first event in any interation of the user with the map there is no need
    // to reset that value, as before each interaction it will be assign a new value.

    /**
     * Creates a new EventsHandler instance.
     * @param {MapComponent} mapComponent The map component whose events the instance handles.
     * @param {number} longClickDelay Number of milliseconds for a click to be defined as long click.
     */
    constructor(mapComponent, longClickDelay) {
        this.longClickDelay = longClickDelay;

        this._mapComponent = mapComponent;
        this._viewer = mapComponent._viewer;

        this._lastLeftDownTimeStamp = -1;

        this.handleLeftDown = this.handleLeftDown.bind(this);
        this.handleLeftClick = this.handleLeftClick.bind(this);
        this.handleLeftUp = this.handleLeftUp.bind(this);
        this.handleMouseMove = this.handleMouseMove.bind(this);

        this._registerListeners();
    }

    /**
     * handles LEFT_DOWN cesium events
     */
    handleLeftDown() {
        this._lastLeftDownTimeStamp = new Date().getTime();

        // TODO touch event
    }

    /**
     * handles LEFT_CLICK cesium events
     * @param {Object} eventData The data received from the event.
     * @param {Pixel} eventData.position
     */
    handleLeftClick({ position }) {
        const coordinates = this._mapComponent.convertPixelToCoordinates(position);
        if (!coordinates) {
            // TODO throw error seems harsh?
            return;
        }

        const pressLength = (new Date().getTime()) - this._lastLeftDownTimeStamp;
        if (pressLength >= this.longClickDelay) {
            // TODO long-click event
            EventsEmitter.fireLongClick({ coordinates: coordinates });
        } else {
            // TODO click event
            EventsEmitter.fireClick({ coordinates: coordinates });
        }
    }

    /**
     * handles LEFT_UP cesium events
     */
    handleLeftUp() {
        // TODO touch event
    }

    /**
     * handles MOUSE_MOVE cesium events
     * @param {Object} eventData The data received from the event.
     * @param {Pixel} eventData.startPosition The pixel at the beginning of the movement.
     * @param {Pixel} eventData.endPosition The pixele at the end of the movement.
     */
    handleMouseMove(eventData) {
        // TODO drag event
    }

    _registerListeners() {
        const events = ['LeftDown', 'LeftClick', 'LeftUp', 'MouseMove'];

        // Object.entries(Cesium.ScreenSpaceEventType).forEach(([key, val]) => {
        //     if (key === 'MOUSE_MOVE') return;
        //     this._viewer.screenSpaceEventHandler.setInputAction(() => console.log(key), val)
        // });

        zip(
            events
                .map(upperCase)
                .map(event => event.replace(/ /g, '_'))
                .map(cesiumEventName => Cesium.ScreenSpaceEventType[cesiumEventName]),
            events.map(event => this[`handle${event}`])
        ).forEach(([cesiumEventCode, eventHandler]) =>
            this._viewer.screenSpaceEventHandler.setInputAction(eventHandler, cesiumEventCode)
        );
    }
}
