package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class FlyWheel extends SubsystemBaseWrapper implements MotorSubsystem {
    private final TalonFX motor;

    public FlyWheel() {
        super();
        
        motor = new TalonFX(Constants.MotorSubsystems.FLYWHEEL_ID);
    }
    
    public void turnOn(double velocity) {
        setMotor(velocity);
        System.out.println("SetFly");
    }

    public void turnOff() {
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(ControlMode.PercentOutput, value);
    }
}
