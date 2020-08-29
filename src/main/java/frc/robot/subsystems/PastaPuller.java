package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class PastaPuller extends SubsystemBaseWrapper implements MotorSubsystem {
    private final VictorSPX motor;

    public PastaPuller() {
        super();
        
        motor = new VictorSPX(Constants.PastaPuller.MOTOR_ID);
    }
    
    public void startPulling() {
        setMotor(-1);
    }

    public void stopPulling() {
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(ControlMode.PercentOutput, value * Constants.PastaPuller.SPEED * (value < 0 ? 0.20 : 1));
    }
}