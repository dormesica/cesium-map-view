import { isNumber } from './validation';
import MapError from './Error';

/**
 * Converts the given radians value to degrees.
 * @param {Number} radians The radians value to convert.
 */
export function convertRadiansToDegrees(radians) {
    if (!isNumber(radians)) {
        MapError.invalidArgumentError('radians', 'convertRadiansToDegrees');
    }

    return (radians * 180) / Math.PI;
}

/**
 * Converts the given degrees value to radians.
 * @param {Number} degrees The degrees value to convert to radians.
 */
export function convertDegreesToRadians(degrees) {
    if (!isNumber(degrees)) {
        MapError.invalidArgumentError('degrees', 'convertDegreesToRadians');
    }

    return (degrees * Math.PI) / 180;
}
