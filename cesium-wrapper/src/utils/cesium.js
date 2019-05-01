import { mapRadianLocationToDegrees } from '../utils/math';

/**
 * Creates an entity descriptor from the given entity.
 * An entity descriptor is a JSON object that it passed to the Android framework for conversion to an Entity object.
 * @param {Cesium.Entity} entity
 */
export function createEntityDescriptor(entity) {
    const typeSpecificContent = {};

    if (entity.billboard) {
        typeSpecificContent.type = 'point';
        typeSpecificContent.location = mapRadianLocationToDegrees(
            Cesium.Cartographic.fromCartesian(entity.position.getValue())
        );
        typeSpecificContent.properties = entity.properties.getValue();
    } else if (entity.polyline) {
        typeSpecificContent.type = 'line';
        typeSpecificContent.path = entity.polyline.positions
            .getValue()
            .map(pos => Cesium.Cartographic.fromCartesian(pos))
            .map(mapRadianLocationToDegrees);
        typeSpecificContent.properties = entity.properties.getValue();
    } else if (entity.polygon) {
        typeSpecificContent.type = 'polygon';
        typeSpecificContent.perimeter = entity.polygon.hierarchy
            .getValue()
            .positions.map(pos => Cesium.Cartographic.fromCartesian(pos))
            .map(mapRadianLocationToDegrees);
        typeSpecificContent.properties = entity.properties.getValue();
    }

    return { ...typeSpecificContent, id: entity.id, name: entity.name, isVisible: entity.show };
}
