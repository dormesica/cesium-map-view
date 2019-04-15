package com.github.dormesica.mapcontroller.layers;

import com.github.dormesica.mapcontroller.graphics.Color;
import com.github.dormesica.mapcontroller.MapView;
import com.github.dormesica.mapcontroller.util.JsonConverter;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import java.net.URL;

/**
 * Layer descriptor for GeoJSON layers.
 *
 * @since 1.0.0
 */
public class GeoJsonLayerDescriptor {

    private String type;
    private String geoJson;
    private String url;
    private String color;
    private String outlineColor;
    private double opacity;
    private double outlineOpacity;
    private String pointIcon;
    private boolean zoom;

    /**
     * Creates a new <code>GeoJSON</code> from the builder.
     *
     * @param builder The builder from which to build the GeoJSON layer.
     * @throws IllegalArgumentException If an argument has an invalid value.
     */
    private GeoJsonLayerDescriptor(Builder builder) throws IllegalArgumentException {
        Preconditions.checkArgument(builder.url != null || builder.geoJson != null,
                "Either GeoJSON or a URL from which to retrieve a GeoJSON should be specified");
        Preconditions.checkArgument(0 <= builder.opacity && builder.opacity <= 1,
                "Opacity must be a value between 0 and 1.");
        Preconditions.checkArgument(0 <= builder.outlineOpacity && builder.outlineOpacity <= 1,
                "Opacity must be a value between 0 and 1.");

        url = null;
        geoJson = null;
        if (builder.url != null) {
            url = builder.url.toString();
        } else {
            geoJson = builder.geoJson;
        }

        type = LayerTypes.GeoJSON;

        color = builder.color;
        outlineColor = builder.outlineColor;
        opacity = builder.opacity;
        outlineOpacity = builder.outlineOpacity;
        pointIcon = builder.pointIcon;
        zoom = builder.zoom;
    }

    /**
     * <code>GeoJsonLayerDescriptor.Builder</code> is a helper for creating {@link GeoJsonLayerDescriptor} layers to be loaded onto
     * {@link MapView}.
     *
     * @since 1.0.0
     */
    public static final class Builder {

        private static final Gson sGson = JsonConverter.getConverter();

        private String geoJson;
        private URL url;
        private String color;
        private String outlineColor;
        private double opacity;
        private double outlineOpacity;
        private String pointIcon;
        private boolean zoom;

        /**
         * Creates a new <code>GeoJsonLayerDescriptor.Builder</code> instance.
         */
        private Builder() {
            geoJson = null;
            url = null;
            color = "#FFFFFF";
            outlineColor = "#FFFFFF";
            opacity = 0.65;
            outlineOpacity = 0.65;
            pointIcon = Point.DEFAULT_POINT_ICON;
            zoom = false;
        }

        /**
         * Creates a new <code>GeoJsonLayerDescriptor.Builder</code> with the given serialized GeoJSON as the layer
         * description.
         *
         * @param geoJsonString Serialized GeoJSON object.
         * @return A new <code>GeoJSON.Builder</code> instance.
         */
        public static Builder from(String geoJsonString) {
            Builder builder = new Builder();
            builder.geoJson = geoJsonString;

            return builder;
        }

        /**
         * Creates a new <code>GeoJsonLayerDescriptor.Builder</code> with the given GeoJSON as the layer description.
         *
         * @param geoJson GeoJSON object.
         * @return A new <code>GeoJSON.Builder</code> instance.
         */
        public static Builder from(Object geoJson) {
            Builder builder = new Builder();
            builder.geoJson = sGson.toJson(geoJson);

            return builder;
        }

        /**
         * Creates a new <code>GeoJsonLayerDescriptor.Builder</code> with the URL as the source from which the
         * GeoJSON will be retrieved.
         *
         * @param url The URL source of the GeoJSON.
         * @return A new <code>GeoJSON.Builder</code> instance.
         */
        public static Builder from(URL url) {
            Builder builder = new Builder();
            builder.url = url;

            return builder;
        }

        /**
         * Set the color of the geometries in the GeoJsonLayerDescriptor.
         * <p>
         * The provided color must be a valid CSS color String.
         *
         * @param color The color of the geometries.
         * @return The <code>GeoJsonLayerDescriptor.Builder</code> for method chaining.
         */
        public Builder setColor(Color color) {
            this.color = color.getColorString();
            this.opacity = color.alpha();
            return this;
        }

        /**
         * Set the color of the geometries' outline.
         * <p>
         * The provided color must be a valid CSS color string.
         *
         * @param outlineColor The color for the geometries' outline.
         * @return The <code>GeoJsonLayerDescriptor.Builder</code> for method chaining.
         */
        public Builder setOutlineColor(Color outlineColor) {
            this.outlineColor = outlineColor.getColorString();
            this.outlineOpacity = outlineColor.alpha();
            return this;
        }

        /**
         * Set the alpha value (opacity) for the geometries' color.
         * <p>
         * Must be a value between 0 and 1 inclusive. 0 being completely transparent and 1 being completely opaque.
         *
         * @param alpha The opacity of the geometry.
         * @return The <code>GeoJsonLayerDescriptor.Builder</code> for method chaining.
         */
        public Builder setAlpha(double alpha) {
            opacity = alpha;
            return this;
        }

        /**
         * Set the alpha value (opacity) for the geometries' outlines.
         * <p>
         * Must be a value between 0 and 1 inclusive.
         *
         * @param alpha The opacity of the geometry.
         * @return The <code>GeoJsonLayerDescriptor.Builder</code> for method chaining.
         */
        public Builder setOutlineAlpha(double alpha) {
            this.outlineOpacity = alpha;
            return this;
        }

        /**
         * Set the icon for point entities in the GeoJSON.
         * <p>
         * Can either be a URL to an online resource or a SVG uri e.g. {@code data:image/svg+xml,<svg>...</svg>}
         *
         * @param svg The SVG uri as a string.
         * @return The <code>GeoJsonLayerDescriptor.Builder</code> for method chaining.
         */
        public Builder setPointIcon(String svg) {
            pointIcon = svg;
            return this;
        }

        /**
         * Set if the map should zoom to the layer after it has been loaded.
         * <p>
         * By default, the map does not zoom to the layer.
         *
         * @param shouldFocus Should the map zoom to the layer after load has finished.
         * @return The <code>GeoJsonLayerDescriptor.Builder</code> for method chaining.
         */
        public Builder shouldFocus(boolean shouldFocus) {
            this.zoom = shouldFocus;
            return this;
        }

        /**
         * Creates the {@link GeoJsonLayerDescriptor} object.
         *
         * @return The newly created {@link GeoJsonLayerDescriptor}.
         * @throws IllegalArgumentException In case
         */
        public GeoJsonLayerDescriptor build() throws IllegalArgumentException {
            return new GeoJsonLayerDescriptor(this);
        }
    }
}
