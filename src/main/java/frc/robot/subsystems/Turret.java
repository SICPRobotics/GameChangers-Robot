package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class Turret extends SubsystemBaseWrapper implements MotorSubsystem {
    private final TalonSRX motor;

    public Turret() {
        super();
        
        motor = new TalonSRX(Constants.MotorSubsystems.TURRET_ID);
    }
    
    public void turnOn(double velocity) {
        System.out.println("Turret");
        setMotor(velocity);
    }
    public void turnOff(){
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(ControlMode.PercentOutput, value);
    }
}
