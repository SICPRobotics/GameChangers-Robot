package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;
import frc.robot.truth.Minitrue;

public final class LeftWinch extends SubsystemBaseWrapper implements MotorSubsystem, ToggleSubsystem {
    // private final TalonSRX armMotor;
    private final VictorSPX leftWinchMotor;

    public LeftWinch(Minitrue minitrue) {
        super(minitrue);
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