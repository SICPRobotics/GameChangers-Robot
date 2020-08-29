package frc.robot.subsystems;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.hal.sim.DriverStationSim;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;
import frc.robot.game_elements.ColorWheel;
import frc.robot.game_elements.ColorWheelColor;

public final class ColorWheelSpinner extends SubsystemBaseWrapper implements MotorSubsystem, ToggleSubsystem {
    private final TalonSRX spinnerMotor = new TalonSRX(Constants.ColorWheel.MOTOR_ID);
    private final ColorMatch colorMatcher = new ColorMatch();
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    public ColorWheelSpinner() {
        super();

        Arrays.stream(ColorWheelColor.values()).forEach(colorWheelColor -> {
            colorMatcher.addColorMatch(colorWheelColor.targetColor);
        });

        spinnerMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    }

    public void setMotor(final double val) {
        spinnerMotor.set(ControlMode.PercentOutput, val * Constants.ColorWheel.SPEED);
    }

    public void start() {
        setMotor(1);
    }

    public void stop() {
        setMotor(0);
    }

    /**
     * Gets the number of slices between the current color and the closest slice of
     * the target color. Returns a negative number if the closest slice is down (aka
     * behind).
     */
    public int getSlicesTo(final ColorWheelColor targetColor) {
        return ColorWheel.slicesToClosest(getCurrentColor(), targetColor);
    }

    /**
     * Gets the number of slices up (aka in front) from the current color to the
     * targetColor.
     * 
     * @return the number of slices up
     */
    public int getSlicesUpTo(final ColorWheelColor targetColor) {
        return ColorWheel.slicesUp(getCurrentColor(), targetColor);
    }

    /**
     * Gets the number of slices down (aka behind) from the current color to the
     * targetColor.
     * 
     * @return the number of slices down
     */
    public int getSlicesDownTo(final ColorWheelColor targetColor) {
        return ColorWheel.slicesUp(getCurrentColor(), targetColor);
    }

    /**
     * Gets the color that FMS wants us to spin to.
     */
    public ColorWheelColor getTargetColor() {
        String color = DriverStation.getInstance().getGameSpecificMessage();
        try {
            ColorWheelColor nextColor = 
                Arrays.stream(ColorWheelColor.values())
                .filter(c -> c.string.equals(color))
                .toArray(ColorWheelColor[]::new)[0];

            return nextColor;
        } catch (Exception e) {
            System.out.println("Couldn't fetch driver station color!");
            return ColorWheelColor.RED;
        }
    }

    /**
     * Gets the color that the field's sensor is on right now. Just treat this code like a black box pls
     * @return the color that the field's color sensor is on right now, based on our color sensor.
     */
    public ColorWheelColor getCurrentColor() {
        try {
            Color detectedColor = colorSensor.getColor();
            ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
            return ColorWheel.getRelativeColor(
                    Arrays.stream(ColorWheelColor.values())
                    .filter(colorWheelColor -> colorWheelColor.targetColor == match.color)
                    .toArray(ColorWheelColor[]::new)[0],
            3);
        } catch (Exception e) {
            System.out.println("Couldn't fetch current seen color!");
            return ColorWheelColor.RED;
        }
        
    }

    public double getConfidence() {
        Color detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        return match.confidence;
    }

    /**
     * Gets the distance the encoder has spun.
     */
    private double getEncoderDistance() {
        /*System.out.println(spinnerMotor.getSelectedSensorPosition()
                / Constants.Encoders.ONE_ENCODER_REVOLUTION / Constants.ColorWheel.SHAFT_REVOLUTIONS_PER_GEARED_MOTOR_REVOLUTION);*/
        //return this.encoder.getDistance();
        return spinnerMotor.getSelectedSensorPosition();
    }

    private double toSlices(final double distanceInEncoderUnits) {
        //Arc length of control panel is 4pi inches and wheel circumference is also 4pi inches!
        return distanceInEncoderUnits / Constants.Encoders.ONE_ENCODER_REVOLUTION
                / Constants.ColorWheel.SHAFT_REVOLUTIONS_PER_GEARED_MOTOR_REVOLUTION;
    }

    /**
     * Gets the distance the color wheel has spun, in slices.
     */
    public double getSlicesSpun() {
        return toSlices(getEncoderDistance());
    }

    /**
     * Resets the distance count on the encoder.
     */
    public void resetDistance() {
        //encoder.reset();
        spinnerMotor.setSelectedSensorPosition(0);
    }

}
