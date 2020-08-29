package frc.robot.game_elements;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

public enum ColorWheelColor {
    BLUE(0.143, 0.427, 0.429, "B"),
    YELLOW(0.361, 0.524, 0.113, "Y"),
    RED(0.561, 0.232, 0.114, "R"),
    GREEN(0.197, 0.561, 0.240, "G");

    public final Color targetColor;
    public final String string;
    private ColorWheelColor(Color targetColor, String string){
        this.targetColor = targetColor;
        this.string = string;
    }
    private ColorWheelColor(double red, double green, double blue, String string) {
        this(ColorMatch.makeColor(red, green, blue), string);
    }
}