package com.github.dormesica.mapcontroller;

import com.github.dormesica.mapcontroller.graphics.Color;
import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

    private static final int RED = 234;
    private static final int GREEN = 54;
    private static final int BLUE = 3;
    private static final double OPACITY = 0.5;
    private static final String COLOR_STRING = "#EA3603";

    @Test
    public void createColorFromChannels() {
        Color c = new Color(RED, GREEN, BLUE, OPACITY);

        Assert.assertEquals(RED, c.red());
        Assert.assertEquals(GREEN, c.green());
        Assert.assertEquals(BLUE, c.blue());
        Assert.assertEquals(OPACITY, c.alpha(), 0.001);

        Assert.assertTrue(c.getColorString().equalsIgnoreCase("#EA3603"));
    }

    @Test
    public void createColorFromColorString() {
        Color c = new Color(COLOR_STRING, OPACITY);

        Assert.assertEquals(RED, c.red());
        Assert.assertEquals(GREEN, c.green());
        Assert.assertEquals(BLUE, c.blue());
        Assert.assertEquals(OPACITY, c.alpha(), 0.001);

        Assert.assertTrue(c.getColorString().equalsIgnoreCase("#EA3603"));
    }
}
