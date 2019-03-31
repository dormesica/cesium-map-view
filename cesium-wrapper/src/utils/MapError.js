export default class MapError extends Error {
    constructor(message) {
        super(message);
    }

    get isMapError() {
        return true;
    }
}

/**
 * Creates a new MapError that indicates an argument of a function is invalid.
 * @param {String} variable The parameter name.
 * @param {String} functionName The function name.
 */
MapError.invalidArgumentError = function (variable, functionName) {
    return new MapError(`${variable} value is invalid in ${functionName}`);
};

/**
 * Creates a new error that indicates a function is not implemented in a class.
 * @param {*} instance The instance on which the function was invoked.
 * @param {Function} functionName The function name.
 */
MapError.notImplementedError = function (instance, functionName) {
    return new MapError(`${functionName} is not implemented in ${instance.constructor.name}`);
};
