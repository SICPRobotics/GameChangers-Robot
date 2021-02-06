package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class LeftWinch extends SubsystemBaseWrapper implements MotorSubsystem, ToggleSubsystem {
    // private final TalonSRX armMotor;
    private final VictorSPX leftWinchMotor;

    public LeftWinch() {
        super();
        leftWinchMotor = new VictorSPX(Constants.Hanger.LEFT_WINCH_MOTOR_ID);
    }
    public void start() {
        setMotor(1);
    }

    public void stop() {
        setMotor(0);
    }

    public void setMotor(final double value) {
        leftWinchMotor.set(ControlMode.PercentOutput, value * Constants.Hanger.WINCH_SPEED_ADJUST);
    }
}