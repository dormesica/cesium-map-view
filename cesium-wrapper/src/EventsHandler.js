import { zip, upperCase } from 'lodash';

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
     * Handles LEFT_DOWN cesium events
     */
    handleLeftDown() {
        this._lastLeftDownTimeStamp = new Date().getTime();

        // TODO touch event
    }

    /**
     * Handles LEFT_CLICK cesium events
     * @param {Object} eventData The data received from the event.
     * @param {Pixel} eventData.position
     */
    handleLeftClick({ position }) {
        const location = this._mapComponent.convertPixelToCoordinates(position);
        if (!location) {
            return;
        }

        const data = JSON.stringify({
            location,
        });

        const pressLength = new Date().getTime() - this._lastLeftDownTimeStamp;
        if (pressLength >= this.longClickDelay) {
            EventsEmitter.fireOnLongClick(data);
        } else {
            EventsEmitter.fireOnClick(data);
        }
    }

    /**
     * Handles LEFT_UP cesium events
     */
    handleLeftUp() {
        // TODO touch event
    }

    /**
     * Handles MOUSE_MOVE cesium events
     * @param {Object} eventData The data received from the event.
     * @param {Pixel} eventData.startPosition The pixel at the beginning of the movement.
     * @param {Pixel} eventData.endPosition The pixele at the end of the movement.
     */
    handleMouseMove(eventData) {
        // TODO drag event
    }

    _registerListeners() {
        const events = ['LeftDown', 'LeftClick', 'LeftUp', 'MouseMove'];

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
