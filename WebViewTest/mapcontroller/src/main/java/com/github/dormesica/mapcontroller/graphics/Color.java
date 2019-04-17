package com.github.dormesica.mapcontroller.graphics;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.common.base.Preconditions;

/**
 * A Class that represents a color.
 * <p>
 * Instances of this class are immutable, i.e. any change to a property of the class creates a new object where all the
 * values of the original object are copied save for the value that was meant to be changed.
 * <p>
 * Each color is represented using 3 integers as defined by the {@code RGB} format and a double value for the opacity.
 *
 * @since 1.0.0
 */
public class Color implements Parcelable {

    public static final Parcelable.Creator<Color> CREATOR = new Parcelable.Creator<Color>() {
        @Override
        public Color createFromParcel(Parcel source) {
            return new Color(source);
        }

        @Override
        public Color[] newArray(int size) {
            return new Color[0];
        }
    };

    /**
     * Constant value for transparent color.
     */
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    /**
     * Constant value for black color.
     */
    public static final Color BLACK = new Color(0, 0, 0, 1);
    /**
     * Constant value for white color.
     */
    public static final Color WHITE = new Color(255, 255, 255, 1);
    /**
     * Constant value for red color.
     */
    public static final Color RED = new Color(255, 0, 0, 1);
    /**
     * Constant value for green color.
     */
    public static final Color GREEN = new Color(0, 255, 0, 1);
    /**
     * Constant value for blue color.
     */
    public static final Color BLUE = new Color(0, 0, 255, 1);
    /**
     * Constant value for cyan color.
     */
    public static final Color CYAN = new Color(0, 255, 255, 1);
    /**
     * Constant value for gray color.
     */
    public static final Color GRAY = new Color(128, 128, 128, 1);
    /**
     * Constant value for magenta color.
     */
    public static final Color MAGENTA = new Color(255, 0, 255, 1);
    /**
     * Constant value for yellow color.
     */
    public static final Color YELLOW = new Color(255, 255, 0, 1);

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
    public Color(@NonNull String colorString, double alpha) throws IllegalArgumentException {
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
     * Creates a new {@code Color} from a {@link Parcel}.
     *
     * @param source The source Parcel.
     */
    private Color(Parcel source) {
        mRed = source.readInt();
        mGreen = source.readInt();
        mBlue = source.readInt();
        mAlpha = source.readDouble();
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
     * Replaces the red channel value with the given value.
     *
     * @param red The new red value.
     * @return A copy of the color with the red channel set to the given value.
     */
    public Color setRed(int red) {
        checkColorValue(red);
        return new Color(red, mGreen, mBlue, mAlpha);
    }

    /**
     * Replaces the green channel value with the given value.
     *
     * @param green The new red value.
     * @return A copy of the color with the green channel set to the given value.
     */
    public Color setGreen(int green) {
        checkColorValue(green);
        return new Color(mRed, green, mBlue, mAlpha);
    }

    /**
     * Replaces the blue channel value with the given value.
     *
     * @param blue The new red value.
     * @return A copy of the color with the blue channel set to the given value.
     */
    public Color setBlue(int blue) {
        checkColorValue(blue);
        return new Color(mRed, mGreen, blue, mAlpha);
    }

    /**
     * Replaces the opacity value of the color.
     *
     * @param alpha The new opacity value.
     * @return A copy of the color with the opacity value set to the given value.
     */
    public Color setAlpha(double alpha) {
        checkAlphaValue(alpha);
        return new Color(mRed, mGreen, mBlue, alpha);
    }

    /**
     * Get a CSS color string representation of the color.
     *
     * @return CSS color string representing the color.
     */
    public String getColorString() {
        return "#" + getChannelHexValue(mRed) + getChannelHexValue(mGreen) + getChannelHexValue(mBlue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mRed);
        dest.writeInt(mGreen);
        dest.writeInt(mBlue);
        dest.writeDouble(mAlpha);
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
    private static void checkColorString(@NonNull String color) throws IllegalArgumentException {
        Preconditions.checkArgument(color.matches(COLOR_REGEX), color + " is not a valid color string");
    }
}
