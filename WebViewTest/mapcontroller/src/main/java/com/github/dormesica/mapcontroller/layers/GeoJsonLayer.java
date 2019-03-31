package com.github.dormesica.mapcontroller.layers;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import java.net.URL;

/**
 * Layer descriptor for GeoJSON layers.
 *
 * @since 1.0.0
 */
public class GeoJsonLayer {

    /**
     * A regular expression that matches CSS color strings.
     */
    private static final String COLOR_REGEX = "#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})";

    private String type;
    private String geoJson;
    private String url;
    private String color;
    private String outlineColor;
    private int outlineWidth;
    private double opacity;

    /**
     * Creates a new <code>GeoJSON</code> from the builder.
     *
     * @param builder The builder from which to build the GeoJSON layer.
     * @throws IllegalArgumentException If an argument has an invalid value.
     */
    private GeoJsonLayer(Builder builder) throws IllegalArgumentException {
        Preconditions.checkArgument(builder.url != null || builder.geoJson != null,
                "Either GeoJSON or a URL from which to retrieve a GeoJSON should be specified");
        Preconditions.checkArgument(builder.color.matches(COLOR_REGEX),
                color + " is not a valid color string");
        Preconditions.checkArgument(builder.outlineColor.matches(COLOR_REGEX),
                outlineColor + " is not a valid color string");
        Preconditions.checkArgument(0 <= opacity && opacity <= 1, "Opacity must be a value between 0 and 1.");
        Preconditions.checkArgument(outlineWidth > 0, "Outline width cannot be negative.");

        url = null;
        geoJson = null;
        if (builder.url != null) {
            url = builder.url.toString();
        } else if (builder.geoJson != null) {
            geoJson = builder.geoJson;
        }

        type = LayerTypes.GeoJSON;

        color = builder.color;
        outlineColor = builder.outlineColor;
        opacity = builder.opacity;
        outlineWidth = builder.outlineWidth;
    }

    /**
     * <code>GeoJsonLayer.Builder</code> is a helper for creating {@link GeoJsonLayer} layers to be loaded onto
     * {@link com.github.dormesica.mapcontroller.widgets.MapView}.
     *
     * @since 1.0.0
     */
    public static final class Builder {

        private static final Gson sGson = new Gson();

        private String geoJson;
        private URL url;
        private String color;
        private String outlineColor;
        private int outlineWidth;
        private double opacity;

        /**
         * Creates a new <code>GeoJsonLayer.Builder</code> instance.
         */
        private Builder() {
            geoJson = null;
            url = null;
            color = "#000000";
            outlineColor = "#000000";
            outlineWidth = 2;
            opacity = 0.35;
        }

        /**
         * Creates a new <code>GeoJsonLayer.Builder</code> with the given serialized GeoJSON as the layer description.
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
         * Creates a new <code>GeoJsonLayer.Builder</code> with the given GeoJSON as the layer description.
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
         * Creates a new <code>GeoJsonLayer.Builder</code> with the URL as the source from which the GeoJSON will be
         * retrieved.
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
         * Set the color of the geometries in the GeoJsonLayer.
         * <p>
         * The provided color must be a valid CSS color String.
         *
         * @param color The color of the geometries.
         * @return The <code>GeoJsonLayer.Builder</code> for method chaining.
         */
        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        /**
         * Set the color of the geometries' outline.
         * <p>
         * The provided color must be a valid CSS color string.
         *
         * @param outlineColor The color for the geometries' outline.
         * @return The <code>GeoJsonLayer.Builder</code> for method chaining.
         */
        public Builder setOutlineColor(String outlineColor) {
            this.outlineColor = outlineColor;
            return this;
        }

        /**
         * Set the pixel width of the geometries' outline.
         *
         * @param width Width in pixels.
         * @return The <code>GeoJsonLayer.Builder</code> for method chaining.
         */
        public Builder setOutlineWidth(int width) {
            outlineWidth = width;
            return this;
        }

        /**
         * Set the opacity for the geometries' color.
         * <p>
         * Must be a value between 0 and 1 inclusive.
         *
         * @param opacity The opacity of the geometry.
         * @return The <code>GeoJsonLayer.Builder</code> for method chaining.
         */
        public Builder setOpacity(double opacity) throws IllegalArgumentException {
            this.opacity = opacity;
            return this;
        }

        /**
         * Creates the {@link GeoJsonLayer} object.
         *
         * @return The newly created {@link GeoJsonLayer}.
         * @throws IllegalArgumentException In case
         */
        public GeoJsonLayer build() throws IllegalArgumentException {
            return new GeoJsonLayer(this);
        }
    }
}
