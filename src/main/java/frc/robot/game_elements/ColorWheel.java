package frc.robot.game_elements;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.util.Color;

public class ColorWheel {
    public static ArrayList<ColorWheelColor> order = new ArrayList<ColorWheelColor>(Arrays.asList(new ColorWheelColor[]{
        ColorWheelColor.BLUE,
        ColorWheelColor.YELLOW,
        ColorWheelColor.RED,
        ColorWheelColor.GREEN
    }));

    /*

    private static int distanceToWithoutWrapping(ColorWheelColor fromColor, ColorWheelColor toColor) {
        return ColorWheel.order.indexOf(toColor) - ColorWheel.order.indexOf(fromColor);
    }

    private static int distanceWrappedTo(ColorWheelColor fromColor, ColorWheelColor toColor) {
        //return ColorWheel.order.indexOf(fromColor) - (ColorWheel.order.indexOf(toColor) - ColorWheel.order.size());
        return (order.indexOf(toColor) < order.indexOf(fromColor) ? order.indexOf(toColor) - order.size() : order.indexOf(toColor) + order.size()) - order.indexOf(fromColor);
    }

    */

    /*public static int distanceTo(ColorWheelColor fromColor, ColorWheelColor toColor) {
        return Math.abs(distanceToWithoutWrapping(fromColor, toColor)) < Math.abs(distanceWrappedTo(fromColor, toColor)) ? distanceToWithoutWrapping(fromColor, toColor) : distanceWrappedTo(fromColor, toColor);
    }*/

    /**
     * Finds the number of slices to the closest toColor from the fromColor.
     * @param fromColor the color we start at
     * @param toColor the color we're going to
     * @return a positive (up) or negative (down) number that represents the number of slices from the fromColor
     * to the toColor
     */
    public static int slicesToClosest(ColorWheelColor fromColor, ColorWheelColor toColor) {
        return Math.min(slicesUp(fromColor, toColor),slicesDown(fromColor, toColor)) * (slicesUp(fromColor, toColor) < slicesDown(fromColor, toColor) ? 1 : -1);
    }

    public static ColorWheelColor getRelativeColor(ColorWheelColor currentColor, int distance) {
        return order.get((order.indexOf(currentColor) + distance) % order.size());
    }

    /**
     * Finds the number of slices away a color is one direction on the wheel
     * @param fromColor the color we want to start counting from
     * @param toColor the color we want to stop counting at
     * @return the number of slices (a positive number) that the color is away in one direction
     */
    public static int slicesUp(ColorWheelColor fromColor, ColorWheelColor toColor) {
        System.out.println(fromColor + " to " + toColor + ": " + (order.indexOf(fromColor) < order.indexOf(toColor) ? order.indexOf(toColor) - order.indexOf(fromColor) : order.size() + order.indexOf(toColor) - order.indexOf(fromColor)));
        return order.indexOf(fromColor) < order.indexOf(toColor) ? order.indexOf(toColor) - order.indexOf(fromColor) : order.size() + order.indexOf(toColor) - order.indexOf(fromColor);
    }

    /**
     * Finds the number of slices away a color is one direction on the wheel
     * @param fromColor the color we want to start counting from
     * @param toColor the color we want to stop counting at
     * @return the number of slices (a positive number) that the color is away in one direction
     */
    public static int slicesDown(ColorWheelColor fromColor, ColorWheelColor toColor) {
        return order.indexOf(fromColor) > order.indexOf(toColor) ? order.indexOf(fromColor) - order.indexOf(toColor) : order.indexOf(fromColor) + order.size() - order.indexOf(toColor);
    }
}