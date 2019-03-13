/**
 * Checks if the given value is a number. If so, return true.
 * @param {*} val
 */
export function isNumber(val) {
    return typeof val === 'number' && !Number.isNaN(val);
}

/**
 * Validates the give coordinates object.
 * Expects a number lon value between -180 and 180,
 * a number lat value between 90 and -90
 * and optinal alt number value.
 * @param {Coordinates} coordinates The coordiantes to validate
 */
export function areCoordiantesValid(coordinates) {
    const validLon = isNumber(coordinates.lon) && -180 <= coordinates.lon && coordinates.lon <= 180;

    const validLat = isNumber(coordinates.lat) && -90 <= coordinates.lat && coordinates.lat <= 90;

    const validAlt = !coordinates.alt || isNumber(coordinates.alt);

    return validLon && validLat && validAlt;
}
