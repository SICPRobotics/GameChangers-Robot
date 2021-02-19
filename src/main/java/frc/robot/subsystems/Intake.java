package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class Intake extends SubsystemBaseWrapper implements MotorSubsystem {
    private final TalonSRX motor;

    public Intake() {
        super();
        
        motor = new TalonSRX(6);
    }
    
    public void turnOn(double velocity) {
        setMotor(velocity);
        System.out.println("SetIntake");
    }

    public void turnOff() {
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(ControlMode.PercentOutput, value);
    }
}