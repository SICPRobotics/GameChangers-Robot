package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.SubsystemBaseWrapper;

public class Hood extends SubsystemBaseWrapper implements MotorSubsystem {

    TalonSRX motor = new TalonSRX(5);

    @Override
    public void turnOn(double velocity) {
        setMotor(velocity * 0.1);
    }
    @Override
    public void turnOff() {
       setMotor(0);
    }
        @Override
    public void setMotor(double value) {
       motor.set(ControlMode.PercentOutput, value);
    }
}
