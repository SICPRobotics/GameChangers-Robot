package frc.robot.commands.color_wheel.by_color;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.game_elements.ColorWheel;
import frc.robot.game_elements.ColorWheel;
import frc.robot.game_elements.ColorWheelColor;
import frc.robot.subsystems.ColorWheelSpinner;

public class SpinNumberOfTimesByColor extends CommandBase {
    private final ColorWheelSpinner spinner;
    private final int minSpins = 3 * 8;
    private final int maxSpins = 5 * 8;
    private int currentSpins = 0;
    private ColorWheelColor colorLastTick;
    private ColorWheelColor colorThisTick;
    private ColorWheelColor lastKnownColor;
    private int consistentTimes = 0;

    public SpinNumberOfTimesByColor(ColorWheelSpinner spinner) {
        this.spinner = spinner;
    }

    @Override
    public void initialize() {
        System.out.println("Initialized color spin amount thing!");
        lastKnownColor = spinner.getCurrentColor();
        colorThisTick = lastKnownColor;
        colorLastTick = lastKnownColor;
        consistentTimes = 0;
        currentSpins = 0;
    }

    @Override
    public void execute() {
        //System.out.println("Color: " + spinner.getCurrentColor() + "  Confidence: " + spinner.getConfidence());
        /*ColorWheelColor currentColor = spinner.getCurrentColor();
        if (proposedNewLastColor != currentColor) {
            proposedNewLastColor = currentColor;
            System.out.println("ConsistentTimes: " + consistentTimes);
            consistentTimes = 0;
        } else {
            consistentTimes++;
        }
        if (consistentTimes > 10) {
            if (lastColor != proposedNewLastColor && lastColor != currentColor /*&& currentColor != ColorWheelColor.GREEN && currentColor() != ColorWheelColor.YELLOW*//*) {
                System.out.println("Color changed from " + lastColor + " to " + currentColor);
                currentSpins += ColorWheel.slicesUp(lastColor, currentColor);
                lastColor = currentColor;
                
           
            } lastColor = proposedNewLastColor;
        }*/

        colorThisTick = spinner.getCurrentColor();
       // System.out.println(colorThisTick);
        if (colorThisTick == colorLastTick) {
            //Color has remained the same
            consistentTimes++;

            //System.out.println(lastKnownColor + "  " + colorThisTick);
            if (consistentTimes > 5 && lastKnownColor != colorThisTick) {
                colorChange(lastKnownColor, colorThisTick);
                lastKnownColor = colorThisTick;
            }
        } else {
            //Color has changed
            consistentTimes = 0;
        }

        spinner.setMotor(((minSpins + 2) - currentSpins) / minSpins);
        //System.out.println("Wheel position: " + currentSpins);

        colorLastTick = colorThisTick;
    }

    private void colorChange(ColorWheelColor oldColor, ColorWheelColor newColor) {
        System.out.println("From " + oldColor + " to " + newColor + " (" + ColorWheel.slicesUp(oldColor, newColor) + ")");
    }

    @Override
    public boolean isFinished() {
        return false;//minSpins < currentSpins && currentSpins < maxSpins;
    }
}