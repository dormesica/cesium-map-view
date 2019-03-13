export default class MapError extends Error {
    constructor(message) {
        super(message);
    }

    get isMapError() {
        return true;
    }
}

MapError.invalidArgumentError = function (valiable, functionName) {
    return new Error(`${valiable} value is invalid in ${functionName}`);
};
