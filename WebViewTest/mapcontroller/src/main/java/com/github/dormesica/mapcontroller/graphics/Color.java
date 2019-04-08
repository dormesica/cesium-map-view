package com.github.dormesica.mapcontroller.graphics;

import com.google.common.base.Preconditions;

/**
 * @since 1.0.0
 */
public class Color {

    public static final Color BLACK = new Color(0, 0, 0, 0.35);
    public static final Color WHITE = new Color(255, 255, 255, 0.35);
    public static final Color RED = new Color(255, 0, 0, 0.35);
    public static final Color GREEN = new Color(0, 255, 0, 0.35);
    public static final Color BLUE = new Color(0, 0, 255, 0.35);

    /**
     * A regular expression that matches CSS color strings.
     */
    private static final String COLOR_REGEX = "#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})";

    private int mRed;
    private int mGreen;
    private int mBlue;
    private double mAlpha;

    /**
     * Creates a new {@code Color} object with the given values for the RGB and alpha channels.
     * <p>
     * Each color channel (red, green or blue) must be a value between 0 and 255.
     * <p>
     * The opacity value must be between 0 and 1 where 0 mean completely transparent and 1 mean completely opaque.
     *
     * @param red   The red value of the color.
     * @param green The green value of the color.
     * @param blue  The blue value of the color.
     * @param alpha The opacity value.
     * @throws IllegalArgumentException In case a value does not meet the requirement above.
     */
    public Color(int red, int green, int blue, double alpha) throws IllegalArgumentException {
        checkColorValue(red);
        checkColorValue(green);
        checkColorValue(blue);
        checkAlphaValue(alpha);

        mRed = red;
        mGreen = green;
        mBlue = blue;
        mAlpha = alpha;
    }

    /**
     * Creates a new {@code Color} object from the given CSS color string and the given opacity value.
     * <p>
     * A CSS color string is a string that matches on of the following 2 formats:
     * <ul>
     * <li>
     * {@code #RRGGBB} - where every 2 characters represent a hexadecimal number between
     * 0 and 255 (0-FF in hexadecimal). The first 2 characters are the red channel, the second pair is the green channel
     * and the third pair is the blue channel.
     * </li>
     * <li>
     * {@code #RGB} - where every character is a represent a hexadecimal number between 0 and 15 (0-F in hexadecimal).
     * The first character is the red value, the second is the green value and the third is the blue value. The actual
     * value of the channel will be calculated using the formula {@code val*16+val}. E.g. the string {@code #F00} is
     * equivalent to the string {@code #FF0000}.
     * </li>
     * </ul>
     * <p>
     * The opacity value must be between 0 and 1 where 0 mean completely transparent and 1 mean completely opaque.
     *
     * @param colorString A CSS color string.
     * @param alpha       The opacity value.
     * @throws IllegalArgumentException In case a value does not meet the requirement above.
     */
    public Color(String colorString, double alpha) throws IllegalArgumentException {
        checkColorString(colorString);
        checkAlphaValue(alpha);

        if (colorString.length() == 7) {
            mRed = Integer.parseInt(colorString.substring(1, 3), 16);
            mGreen = Integer.parseInt(colorString.substring(3, 5), 16);
            mBlue = Integer.parseInt(colorString.substring(5, 7), 16);
        } else {
            mRed = Integer.parseInt("" + colorString.charAt(1) + colorString.charAt(1), 16);
            mGreen = Integer.parseInt("" + colorString.charAt(2) + colorString.charAt(2), 16);
            mBlue = Integer.parseInt("" + colorString.charAt(3) + colorString.charAt(3), 16);
        }

        mAlpha = alpha;
    }

    /**
     * Get the value of the red channel of the color.
     *
     * @return The red value.
     */
    public int red() {
        return mRed;
    }

    /**
     * Get the value of the green channel of the color.
     *
     * @return The green value.
     */
    public int green() {
        return mGreen;
    }

    /**
     * Get the value of the blue channel of the color.
     *
     * @return The blue value.
     */
    public int blue() {
        return mBlue;
    }

    /**
     * Get the opacity value of the color.
     *
     * @return The opacity value.
     */
    public double alpha() {
        return mAlpha;
    }

    /**
     * Get a CSS color string representation of the color.
     *
     * @return CSS color string representing the color.
     */
    public String getColorString() {
        return "#" + getChannelHexValue(mRed) + getChannelHexValue(mGreen) + getChannelHexValue(mBlue);
    }

    /**
     * Converts a single channel value to hexadecimal.
     *
     * @param value The value to convert
     * @return A 2 character long hexadecimal representation.
     */
    private static String getChannelHexValue(int value) {
        String hex = Integer.toHexString(value);

        if (value < 16) {
            return "0" + hex;
        }

        return hex;
    }

    /**
     * Validates the value of a color fits in the range {@code [0,255]}.
     *
     * @param val The value to validate.
     * @throws IllegalArgumentException In case the value is smaller than 0 or greater than 255.
     */
    private static void checkColorValue(int val) throws IllegalArgumentException {
        Preconditions.checkArgument(val >= 0 && val <= 255, "Color value must be between 0 and 255");
    }

    /**
     * Validates the value of a color fits in the range {@code [0,1]}.
     *
     * @param alpha The value to validate.
     * @throws IllegalArgumentException In case the value is smaller than 0 or greater than 1.
     */
    private static void checkAlphaValue(double alpha) throws IllegalArgumentException {
        Preconditions.checkArgument(0 <= alpha && alpha <= 1, "Opacity must be a value between 0 and 1.");
    }

    /**
     * Validates the value of color string is not of the format {@code #RGB} or {@code #RRGGBB}.
     *
     * @param color The color string to validate.
     * @throws IllegalArgumentException In case the value does not match the format.
     */
    private static void checkColorString(String color) throws IllegalArgumentException {
        Preconditions.checkArgument(color.matches(COLOR_REGEX), color + " is not a valid color string");
    }
}
