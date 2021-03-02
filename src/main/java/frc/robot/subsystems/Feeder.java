package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public class Feeder extends SubsystemBaseWrapper implements MotorSubsystem {
    private final TalonSRX motor;

    public Feeder() {
        super();
        motor = new TalonSRX(Constants.MotorSubsystems.INDEXER_ID);
    }
    
    public void turnOn(double velocity) {
        setMotor(0.2);
    }
    public void turnOff(){
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(ControlMode.PercentOutput, value);
    }
}
