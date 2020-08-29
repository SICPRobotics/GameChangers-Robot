package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class RightWinch extends SubsystemBaseWrapper implements MotorSubsystem, ToggleSubsystem {
    private final VictorSPX rightWinchMotor;

    public RightWinch() {
        super();
        rightWinchMotor = new VictorSPX(Constants.Hanger.RIGHT_WINCH_MOTOR_ID);
    }
    public void start() {
        setMotor(1);
    }

    public void stop() {
        setMotor(0);
    }

    public void setMotor(final double value) {
        rightWinchMotor.set(ControlMode.PercentOutput, -value * Constants.Hanger.WINCH_SPEED_ADJUST);
    }
}